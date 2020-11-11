<#assign keyField = domain.keyFields?first>
<#list domain.nonKeyFields as field>
<#if field.name != 'createdDt'>
<div class="form-group">
    <label for="${field.name}" class="col-sm-2 control-label">${field.nameLabel}</label>
    <div class="col-sm-10">
<#if field.enumValues??>
        <select class="form-control" id="${field.name}" name="${field.name}">
            <#list field.enumValues as enumVal>
            <#noparse><option <#if</#noparse> (${domain.classVar}.${field.name}!'') == '${enumVal}'>selected <#noparse></#if></#noparse>value="${enumVal}">${enumVal}</option>
            </#list>
        </select>
<#elseif field.type == 'boolean' || field.type == 'Boolean'>
        <div id="${field.name}" class="radio">
            <label><input type="radio" name="${field.name}" value="true" <#noparse><#if</#noparse> (${domain.classVar}.${field.name})>checked <#noparse></#if></#noparse>value="YES"> YES</label>
            <label><input type="radio" name="${field.name}" value="false" <#noparse><#if</#noparse> !(${domain.classVar}.${field.name})>checked <#noparse></#if></#noparse>value="NO"> NO</label>
        </div>
<#else>
        <input type="text" class="form-control" id="${field.name}" name="${field.name}" value="<#noparse>$</#noparse>{${domain.classVar}.${field.name}!''}">
</#if>
    </div>
</div>
</#if>
</#list>