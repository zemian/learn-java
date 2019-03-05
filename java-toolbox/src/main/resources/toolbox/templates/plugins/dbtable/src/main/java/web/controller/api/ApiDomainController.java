<#assign keyField = domain.keyFields?first>
package ${packageName};

import ${domain.packageName}.*;
import ${dao.packageName}.*;
import ${service.packageName}.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * API controller for ${domain.className}.
 */
@RestController
public class Api${domain.className}Controller {
    @Autowired
    private ${service.className} ${service.classVar};

    @GetMapping("${apiUrlMapping}/${domain.classNameUrl}/list")
    public @ResponseBody ApiResult<PagingList<${domain.className}>> list(Paging paging) {
        PagingList<${domain.className}> plist = ${service.classVar}.find(paging);
        return ApiResult.result(plist);
    }

    @GetMapping("${apiUrlMapping}/${domain.classNameUrl}/{${keyField.name}}")
    public @ResponseBody ApiResult<${domain.className}> get(@PathVariable ${keyField.type} ${keyField.name}) {
        return ApiResult.result(${service.classVar}.get(${keyField.name}));
    }

    @PostMapping("${apiUrlMapping}/${domain.classNameUrl}/create")
    public @ResponseBody ApiResult<${domain.className}> create(@RequestBody ${domain.className} ${domain.classVar}) {
        ${service.classVar}.create(${domain.classVar});
        return ApiResult.result(${domain.classVar},
            "${keyField.nameLabel} " + ${domain.classVar}.${keyField.getterMethodName}() + " created.");
    }

    @PostMapping("${apiUrlMapping}/${domain.classNameUrl}/update")
    public @ResponseBody ApiResult<${domain.className}> update(@RequestBody ${domain.className} ${domain.classVar}) {
        ${service.classVar}.update(${domain.classVar});
        return ApiResult.result(${domain.classVar},
            "${keyField.nameLabel} " + ${domain.classVar}.${keyField.getterMethodName}() + " updated.");
    }

    @PostMapping("${apiUrlMapping}/${domain.classNameUrl}/delete")
    public @ResponseBody ApiResult<${domain.className}> delete(@RequestBody ${domain.className} ${domain.classVar}) {
        ${domain.className} ret = ${service.classVar}.get(${domain.classVar}.${keyField.getterMethodName}());
        ${service.classVar}.delete(${domain.classVar}.${keyField.getterMethodName}());
        return ApiResult.result(ret,
            "${keyField.nameLabel} " + ret.${keyField.getterMethodName}() + " deleted.");
    }
}
