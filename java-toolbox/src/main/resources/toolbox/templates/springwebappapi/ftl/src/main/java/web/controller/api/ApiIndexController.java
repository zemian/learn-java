package ${basePackage}.web.controller.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * The home and landing page of the application.
 */
@Controller
public class ApiIndexController {
    @GetMapping({"/", "/index"})
    public @ResponseBody ApiResult<Object> index() {
        return ApiResult.result(null, "Welcome to API portal for ${projectName}");
    }
}
