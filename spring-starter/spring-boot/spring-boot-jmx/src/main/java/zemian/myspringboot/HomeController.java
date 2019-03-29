package zemian.myspringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {
    @Autowired
    private MyDatabase myDatabase;

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello World!\n" +
                "Using: " + myDatabase.getVersion();
    }
}
