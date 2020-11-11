package zemian.worldapi;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CityController {
  private DataService dataService;

  public CityController(DataService dataService) {
    this.dataService = dataService;
  }

  @GetMapping("/cities")
  public List<City> cities() {
    return dataService.findCities();
  }
}
