<#assign keyField = domain.keyFields?first>
package ${controller.packageName};

import ${basePackage}.web.controller.AbstractController;
import ${domain.packageName}.*;
import ${dao.packageName}.*;
import ${service.packageName}.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * UI controller for ${domain.className}.
 */
@Controller
public class ${controller.className} extends AbstractController {
    @Autowired
    private ${service.className} ${service.classVar};

    @GetMapping({"${controller.webUiUrlMapping}/${domain.classNameUrl}/list", "${controller.webUiUrlMapping}/${domain.classNameUrl}"})
    public ModelAndView list(Paging paging) {
        PagingList<${domain.className}> plist = ${service.classVar}.find(paging);
        return getView("${controller.webUiUrlMapping}/${domain.classNameUrl}/list", "plist", plist);
    }

    @GetMapping("${controller.webUiUrlMapping}/${domain.classNameUrl}/detail/{${keyField.name}}")
    public ModelAndView detail(@PathVariable ${keyField.type} ${keyField.name}) {
        ${domain.className} ${domain.classVar} = ${service.classVar}.get(${keyField.name});
        return getView("${controller.webUiUrlMapping}/${domain.classNameUrl}/detail", "${domain.classVar}", ${domain.classVar});
    }

    private boolean valid(${domain.className} ${domain.classVar}, BindingResult bindingResult) {
        <#list domain.nonKeyFields as field>
        <#if !(field.dbField.nullable) && !(field.dbField.columnDef??)>
        ValidationUtils.rejectIfEmpty(bindingResult, "${field.name}", "${domain.classVar}.${field.name}", "${field.nameLabel} cannot be empty");
        </#if>
        </#list>
        return !bindingResult.hasErrors();
    }

    @Controller
    public class CreateForm {
        @GetMapping("${controller.webUiUrlMapping}/${domain.classNameUrl}/create")
        public ModelAndView create() {
            return getView("${controller.webUiUrlMapping}/${domain.classNameUrl}/create", "${domain.classVar}", new ${domain.className}());
        }

        @PostMapping("${controller.webUiUrlMapping}/${domain.classNameUrl}/create")
        public ModelAndView createSubmit(@ModelAttribute ${domain.className} ${domain.classVar},
                                         BindingResult bindingResult, RedirectAttributes redirectAttrs) {
            if (!valid(${domain.classVar}, bindingResult)) {
                return getErrorView("${controller.webUiUrlMapping}/${domain.classNameUrl}/create", bindingResult, "${domain.classVar}", ${domain.classVar});
            }

            ${service.classVar}.create(${domain.classVar});
            redirectAttrs.addFlashAttribute("message",
                    "${keyField.nameLabel} " + ${domain.classVar}.${keyField.getterMethodName}() + " created.");
            return getView("redirect:${controller.webUiUrlMapping}/${domain.classNameUrl}/list");
        }
    }

    @Controller
    public class EditForm {
        @GetMapping("${controller.webUiUrlMapping}/${domain.classNameUrl}/edit/{${keyField.name}}")
        public ModelAndView update(@PathVariable ${keyField.type} ${keyField.name}) {
            ${domain.className} ${domain.classVar} = ${service.classVar}.get(${keyField.name});
            return getView("${controller.webUiUrlMapping}/${domain.classNameUrl}/edit", "${domain.classVar}", ${domain.classVar});
        }

        @PostMapping("${controller.webUiUrlMapping}/${domain.classNameUrl}/edit")
        public ModelAndView updateSubmit(@ModelAttribute ${domain.className} ${domain.classVar},
                                         BindingResult bindingResult, RedirectAttributes redirectAttrs) {
            if (!valid(${domain.classVar}, bindingResult)) {
                return getErrorView("${controller.webUiUrlMapping}/${domain.classNameUrl}/edit", bindingResult, "${domain.classVar}", ${domain.classVar});
            }

            ${service.classVar}.update(${domain.classVar});
            redirectAttrs.addFlashAttribute("message",
                    "${keyField.nameLabel} " + ${domain.classVar}.${keyField.getterMethodName}() + " updated.");
            return getView("redirect:${controller.webUiUrlMapping}/${domain.classNameUrl}/list");
        }

    }

    @Controller
    public class DeleteForm {
        @GetMapping("${controller.webUiUrlMapping}/${domain.classNameUrl}/delete/{${keyField.name}}")
        public ModelAndView delete(@PathVariable ${keyField.type} ${keyField.name}) {
            ${domain.className} ${domain.classVar} = ${service.classVar}.get(${keyField.name});
            return getView("${controller.webUiUrlMapping}/${domain.classNameUrl}/delete", "${domain.classVar}", ${domain.classVar});
        }

        @PostMapping("${controller.webUiUrlMapping}/${domain.classNameUrl}/delete")
        public ModelAndView deleteSubmit(@RequestParam ${keyField.type} ${keyField.name}, RedirectAttributes redirectAttrs) {
            // Simple get will ensure the ID param is valid
            ${service.classVar}.get(${keyField.name});

            ${service.classVar}.delete(${keyField.name});
            redirectAttrs.addFlashAttribute("message",
                    "${keyField.nameLabel} " + ${keyField.name} + " deleted.");
            return getView("redirect:${controller.webUiUrlMapping}/${domain.classNameUrl}/list");
        }
    }
}
