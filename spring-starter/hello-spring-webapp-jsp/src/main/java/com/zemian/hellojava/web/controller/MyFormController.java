package com.zemian.hellojava.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.UUID;

@Controller
public class MyFormController extends AbstractController {
    private static Logger LOG = LoggerFactory.getLogger(MyFormController.class);

    @GetMapping("/my-form")
    public ModelAndView form() {
        return getView("/my-form", "myForm", new MyForm());
    }

    @GetMapping("/my-form-complete")
    public ModelAndView formComplete() {
        return getView("/my-form-complete");
    }

    @PostMapping("/my-form")
    public ModelAndView formSubmit(@Validated @ModelAttribute MyForm myForm, RedirectAttributes redirectAttrs) {
        LOG.debug("Creating " + myForm);
        myForm.setId(UUID.randomUUID().toString());
        redirectAttrs.addFlashAttribute("myForm", myForm);
        return new ModelAndView("redirect:/my-form-complete");
    }

    public static class MyForm {
        private String id;
        private String path;
        private SubForm subForm = new SubForm();
        private LocalDateTime createdDt;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public LocalDateTime getCreatedDt() {
            return createdDt;
        }

        public void setCreatedDt(LocalDateTime createdDt) {
            this.createdDt = createdDt;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public SubForm getSubForm() {
            return subForm;
        }

        public void setSubForm(SubForm subForm) {
            this.subForm = subForm;
        }

        @Override
        public String toString() {
            return "MyForm{" +
                    "path='" + path + '\'' +
                    ", subForm=" + subForm +
                    '}';
        }
    }

    public static class SubForm {
        private String name;
        private String value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "SubForm{" +
                    "name='" + name + '\'' +
                    ", value='" + value + '\'' +
                    '}';
        }
    }
}
