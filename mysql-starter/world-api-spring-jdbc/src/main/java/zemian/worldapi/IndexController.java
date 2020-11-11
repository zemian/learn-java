package zemian.worldapi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
  @GetMapping("/")
  public String index() {
    return "<h1>Welcome to World API</h1>"
        + "<p>API Endpoints</p>"
        + "<ul>"
        + "<li><a href='/cities'>/cities</a></li>"
        + "</ul>";
  }
}
