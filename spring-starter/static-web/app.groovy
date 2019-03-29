/*
This simple empty impl class can bootstrap a SpringBoot app that serve
static files!

// How to Run:
// spring run app.groovy
// open http://localhost:8080/

NOTE: You must run `spring` command in the root for this project
directory in order to find these files!

Setup static files as follow:

    /META-INF/resources/
    /resources/
    /static/
    /public/

See https://spring.io/blog/2013/12/19/serving-static-web-content-with-spring-boot
*/

@Controller
class StaticWebApp {}