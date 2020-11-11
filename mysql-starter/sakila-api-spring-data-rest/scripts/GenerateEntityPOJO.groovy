// This script is based on
// https://gist.github.com/virtualadrian/519fe0dc6658fe60500067bee3134c97

import com.intellij.database.model.DasTable
import com.intellij.database.model.ObjectKind
import com.intellij.database.util.Case
import com.intellij.database.util.DasUtil

/*
 * Available context bindings:
 *   SELECTION   Iterable<DasObject>
 *   PROJECT     project
 *   FILES       files helper
 */

packageName = "com.sample;"
typeMapping = [
        (~/(?i)bigint/)                   : "Long",
        (~/(?i)int/)                      : "Integer",
        (~/(?i)float|double|decimal|real/): "Double",
        (~/(?i)datetime|timestamp/)       : "java.sql.Timestamp",
        (~/(?i)date/)                     : "java.sql.Date",
        (~/(?i)time/)                     : "java.sql.Time",
        (~/(?i)/)                         : "String"
]

FILES.chooseDirectoryAndSave("Choose directory", "Choose where to store generated files") { dir ->
    SELECTION.filter { it instanceof DasTable && it.getKind() == ObjectKind.TABLE }.each { generate(it, dir) }
}

def generate(table, dir) {
    def className = javaName(table.getName(), true)
    def fields = calcFields(table)
    new File(dir, className + ".java").withPrintWriter { out -> generate(out, className, fields, table.getName()) }
}

def generate(out, className, fields, tableName) {

    // Determine if this table has composite ID of not. If yes, we will simply use it self
    // as the IdClass for now.
    def isCid = fields.count{ it.primary } > 1
    def serializableCls = ""
    if (isCid)
        serializableCls = "implements java.io.Serializable "

    out.println "package $packageName"
    out.println ""
    out.println ""
    out.println "import javax.persistence.*;"
    out.println ""
    out.println "@Entity"
    out.println "@Table(name=\"${tableName}\")"
    if (isCid)
        out.println "@IdClass(${underscoreToCamelCase(className)}.class)"
    out.println "public class ${underscoreToCamelCase(className)} ${serializableCls}{"
    out.println ""
    fields.each() {
        if (it.annos != "") out.println "  ${it.annos}"
        out.println ""
        if (it.primary) out.println "  @Id"
        out.println "  @Column(name=\"${it.colName}\")"
        out.println "  private ${it.type} ${underscoreToCamelCase(it.name)};"
    }
    out.println ""
    fields.each() {
        out.println ""
        out.println "  public ${it.type} get${underscoreToCamelCase(it.name.capitalize())}() {"
        out.println "    return this.${underscoreToCamelCase(it.name)};"
        out.println "  }"
        out.println ""
        out.println "  public void set${it.name.capitalize()}(${it.type} ${it.name}) {"
        out.println "    this.${underscoreToCamelCase(it.name)} = ${underscoreToCamelCase(it.name)};"
        out.println "  }"
    }
    out.println "}"

}

def calcFields(table) {
    DasUtil.getColumns(table).reduce([]) { fields, col ->
        def spec = Case.LOWER.apply(col.getDataType().getSpecification())
        def typeStr = typeMapping.find { p, t -> p.matcher(spec).find() }.value
        fields += [[
                           name : javaName(col.getName(), false),
                           type : typeStr,
                           annos: "",
                           colName: col.getName(),
                           primary: DasUtil.isPrimary(col)
                   ]]
    }
}

def javaName(str, capitalize) {
    def s = com.intellij.psi.codeStyle.NameUtil.splitNameIntoWords(str)
            .collect { Case.LOWER.apply(it).capitalize() }
            .join("")
            .replaceAll(/[^\p{javaJavaIdentifierPart}[_]]/, "_")
    capitalize || s.length() == 1? s : Case.LOWER.apply(s[0]) + s[1..-1]
}

String underscoreToCamelCase(String underscore){
    if(!underscore || underscore.isAllWhitespace()){
        return ''
    }
    return underscore.replaceAll(/_\w/){ it[1].toUpperCase() }
}
