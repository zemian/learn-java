// How to Run:
// spring run app.groovy
// open http://localhost:8080/

@RestController
class HelloApp {

    @RequestMapping("/")
    String home() {
        return "Hello World!"
    }

}
