package com.zemian.hellojava.web.controller.admin;

import com.zemian.hellojava.data.dao.Paging;
import com.zemian.hellojava.data.dao.PagingList;
import com.zemian.hellojava.data.domain.User;
import com.zemian.hellojava.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {
    private static Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping("/admin/user/list")
    public ModelAndView list(Paging paging) {
        PagingList<User> users = userService.find(paging);
        ModelAndView result = new ModelAndView("/admin/user/list");
        result.addObject("users", users);
        return result;
    }

    // == Form Controller

    @Controller
    public static class FormController {
        public static class UserValidator implements Validator {
            @Override
            public boolean supports(Class<?> clazz) {
                return User.class.isAssignableFrom(clazz);
            }

            @Override
            public void validate(Object target, Errors errors) {
                ValidationUtils.rejectIfEmpty(errors, "username", "user.username");
                ValidationUtils.rejectIfEmpty(errors, "password", "user.username");

                User user = (User) target;
                if (user.getPassword().length() < 3) {
                    errors.rejectValue("username", "user.username", "Password too short");
                }
            }
        }

        // NOTE: You need a dedicated form controller to use @InitBinder since it affect all request
        // handler methods in this controller!
        @InitBinder
        protected void initBinder(WebDataBinder binder) {
            binder.setValidator(new UserValidator());
        }

        @GetMapping("/admin/user/create")
        public ModelAndView create(@ModelAttribute User user) {
            ModelAndView result = new ModelAndView("/admin/user/create");
            return result;
        }

        @PostMapping("/admin/user/create")
        public ModelAndView createPost(@Validated @ModelAttribute User user) {
            LOG.debug("Creating " + user);
            return new ModelAndView("redirect:/admin/user/list");
        }
    }
}
