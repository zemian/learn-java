The Spring Framework provides a comprehensive programming and configuration model for modern Java-based enterprise applications - on any kind of deployment platform.

A key element of Spring is infrastructural support at the application level: Spring focuses on the "plumbing" of enterprise applications so that teams can focus on application-level business logic, without unnecessary ties to specific deployment environments.

https://spring.io/projects/spring-framework

== Download and Setup

There is an esier way to setup spring app!

  curl http://start.spring.io/starter.zip

To see help:

  curl https://start.spring.io

=== Using Spring Boot CLI starter

One easy way to get started with Spring is to setup Spring project is using
Spring Boot CLI command tool.

----
curl -s "https://get.sdkman.io" | bash
sdk install springboot
----

==== Manual Download

Get zip from https://repo.spring.io/release/org/springframework/boot/spring-boot-cli/2.1.3.RELEASE/spring-boot-cli-2.1.3.RELEASE-bin.zip

See https://docs.spring.io/spring-boot/docs/current/reference/html/getting-started-installing-spring-boot.html#getting-started-installing-the-cli

NOTE: We have a copy of `spring-boot-cli-2.0.5.RELEASE-bin.zip` in this repository.

2. Now unzip and setup PATH to use the `spring` command.
----
unzip spring-boot-cli-2.0.5.RELEASE-bin.zip
export PATH=`pwd`/spring-2.0.5.RELEASE/bin:$PATH
spring init --help
----

== Create a Single Groovy Spring Application

1. Create a simple groovy app script
----
// file: app.groovy
@RestController
class HelloApp {
    @RequestMapping("/")
    String home() {
        return "Hello World!"
    }
}
----

2. Now run it and test it with a browser:
----
spring run app.groovy
open http://localhost:8080
----


== Create a Maven Spring Application

Create a full SpringBoot project with Maven build:
----
spring init -d=web --package-name zemian.springbootstarter.hello hello-spring-boot
cd hello-spring-boot
./mvnw spring-boot:run
open http://localhost:8080
----

NOTE: The default Java version used is 1.8.

== Create a Gradle Spring Application

  spring init --build=gradle hello

== Create a Java 11 project

  spring init --build=gradle -j 11 hello

== Spring Boot Admin

An admin interface for Spring Boot applications.

https://github.com/codecentric/spring-boot-admin

== JHipster Dev

JHipster is a development platform to generate, develop and deploy Spring Boot + Angular/React Web applications and Spring microservices. 

https://www.jhipster.tech/
