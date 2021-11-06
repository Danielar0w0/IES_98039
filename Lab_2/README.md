--- 2_1 Server-side programming with servlets

https://www.baeldung.com/java-servers

Java Servlet is the foundation web specification in the Java Enterprise environment. 
A Servlet is a Java class that runs at the server, handles (client) requests, processes them,and reply with a response.
A servlet must be deployed into a (multithreaded) Servlet Containerto be usable.

Servlet Containers vs Docker Containers
You use Docker Containers to deploy virtualized runtimes, for any kind of services;
Servlet Containers provide a runtime to execute server-side web-related Java code(no virtualization).

Servlet is a generic interface and the HttpServletis an extension of that interface(the most used type of Servlets)
https://javaee.github.io/javaee-spec/javadocs/javax/servlet/Servlet.html

<dependency>
  <groupId>javax.servlet</groupId>
  <artifactId>javax.servlet-api</artifactId>
  <version>4.0.0</version>
  <scope>provided</scope>
</dependency>

When an application running in a web server receives a request, the Server hands the request to the Servlet Container which in turn passes it to the target Servlet.

---

There are several production-ready application servers you can choose from (e.g.: Apache Tomcat, RedHat WildFly, Payara, Glassfish,...). 

(For a production environment, you would install Tomcat as a system service/daemon or, even better, as a “dockerized” service. 
In this case, we will just start and stop from the command line, in the installation folder.)

Run the application server: Use the startup script inside <path to Tomcat>/bin folder

Confirm that Tomcat server is running (alternatives):
i) Curl tool$ curl -I 127.0.0.1:8080
ii) Access the default page in the browser: http://localhost:8080
iii) Observe the server log: $ tail logs/catalina.out

i)
curl -I 127.0.0.1:8080
HTTP/1.1 200
Content-Type: text/html;charset=UTF-8
Transfer-Encoding: chunked
Date: Thu, 28 Oct 2021 10:28:48 GMT

iii) [WINDOWS] (PowerShell)
cd C:\Tomcat
Get-Content logs/catalina.2021-10-28.log -Wait -Tail 30

---

http://localhost:8080/manager
Tomcat management environment (“Managerapp”) you can use to control the server, including deploying and un-deploying applications you develop

Register at least one role in conf/tomcat-users.xml. Restart tomcat.
<role rolename="manager-gui"/>
<role rolename="manager-script"/>
<user username="admin" password="secret" roles="manager-gui,manager-script"/>

Create a suitable Maven archetype (for Tomcat):
archetypeGroupId=org.codehaus.mojo.archetypes
archetypeArtifactId=webapp-javaee7
archetypeVersion=1.1

mvn archetype:generate -DgroupId=pt.ua.deti.ies.lab2.firstProject -DartifactId=FirstProject -DarchetypeGroupId=org.codehaus.mojo.archetypes -DarchetypeArtifactId=webapp-javaee7 -DarchetypeVersion=1.1 -DinteractiveMode=false
[WINDOWS]
mvn archetype:generate -D"groupId"=pt.ua.deti.ies.lab2.firstProject -D"artifactId"=FirstProject -D"archetypeGroupId"=org.codehaus.mojo.archetypes -D"archetypeArtifactId"=webapp-javaee7 -D"archetypeVersion"=1.1 -D"interactiveMode"=false

(After mvn install)
.warfile in<project folder>/target - Your application packaged as a Web Archive (you may inspect its contents in a regular Zip tool)

---

Deploy a packed application (.war) into the application server (alternatives):
i) Use the Tomcat management interface (Tomcat manager application) to upload and deploy a .war file (http://localhost:8080/manager)
ii) copy the .war file into <Tomcat root>/webapps

Confirm that your application was successfully deployer in Tomcat:
http://localhost:8080/your-web-app-name/

Deploying using the Tomcat Manager page has some disadvantages:it is not “connected” with the IDEand is specific to Tomcat.
The productive alternative is to use the IDE integrated deployment support such as IntelliJ
(The artifact to deploy is the <your project>.war file)

Important: when deploying from IDEfor development purposes, it is better that Tomcat has not beenalready started from outside the IDE

---

In older versions of the Servlet Container specifications, the developer neededto write a web .xml file with the configuration descriptors, including the mapping of Servlet classes and URL paths. The current implementations, however, use annotations (check for the presence of @WebServlet annotation)

Example:
@WebServlet(name = "MyFirstServlet", urlPatterns = {"/MyFirstServlet"})

Add a basic servlet to your project that takes the name of the user, passed as a parameter in the HTTP request and prints a customized message:

import javax.servlet.http.HttpServletRequest;
String username = request.getParameter("username");

Given that Java application containers are based on open specifications, you may run your project using a different server, without code modifications. The deployment methods, however, will be different. You may try other popular servers, e.g.: PayaraServer (web profile), instead of Tomcat.


--- 2_2 Server-side programming with embedded servers

Run the web container from within your app: Use “embedded server”, since its lifecycle (start, stop) and the deployment of the artifacts is controlled by the application code
(Example of an embedded server: Jetty server)

--- 2_3 Introduction to web apps with a full-featured framework (Spring Boot)

https://spring.io/projects/spring-boot
Spring Boot: A rapid application development platform built on top of the popular Spring Framework.
    - Assumes common configuration options without the need to specify everything (convention-over-configuration addition to the Spring platform)
    - Useful to get started with minimum effort and create stand-alone, production-grade applications

Spring Initializr templates contain a collection of all the relevant transitive dependencies that are needed to start a particular functionality and will simplify the setup of the POM.

The Maven Wrapper is an excellent choice for projects that need a specific version of Maven (or for users that don't want to install Maven at all)
Instead of installing many versions of it in the operating system, we can just use the project-specific wrapper script

Commands to build the application created with String Initializr (alternatives):
$ mvn install -DskipTests && java -jar target\webapp1-0.0.1-SNAPSHOT.jar
$ ./mvnw spring-boot:run

[WINDOWS]
$ mvn install -D"skipTests"
$ java -jar target\webapp1-0.0.1-SNAPSHOT.jar

(Preferred!)
$ ./mvnw spring-boot:run

---

Create a Web Controller

In Spring’s approach to building websites, HTTP requests are handled by a controller (@Controller)
A View is responsible for rendering the HTML content

@GetMapping("/example")
public String example(...)

The @GetMapping annotation ensures that HTTP GET requests to /example are mapped to the example() method

The implementation of the method body relies on a view technology (in this case, Thymeleaf) to perform server-side rendering of the HTML
    - Make sure you have Thymeleaf on your classpath (artifact co-ordinates: org.springframework.boot:spring-boot-starter-thymeleaf)

---

Spring Boot Devtools

A common feature of developing web applications is coding a change, restarting your application, and refreshing the browser to view the change. 
This entire process can eat up a lot of time. To speed up this refresh cycle, Spring Boot offers with a handy module known as spring-boot-devtools.

@SpringBootApplication is a convenience annotation that adds all of the following:
    - @Configuration
    - @EnableAutoConfiguration
    - @ComponentScan

@Configuration
Tags the class as a source of bean definitions for the application context.

@EnableAutoConfiguration
Tells Spring Boot to start adding beans based on classpath settings, other beans, and various property settings
For example, if spring-webmvc is on the classpath, this annotation flags the application as a web application and activates key behaviors, such as setting up a DispatcherServlet.

@ComponentScan
Tells Spring to look for other components, configurations, and services in the com/example package, letting it find the controllers

With Spring Initializr, the web application is 100% pure Java and you do not have to deal with configuring any plumbing or infrastructure.
There is not a single line of XML. There is no web.xml file, either.

Run the application:
    - Run the application from the command line with Gradle or Maven
    - Build a single executable JAR file that contains all the necessary dependencies, classes, and resources and run that. 

Pros of building an executable jar:
Easy to ship, version, and deploy the service as an application throughout the development lifecycle, across different environments, and so forth.

Build an executable JAR:

(Gradle)
./gradlew build
java -jar build/libs/gs-serving-web-content-0.1.0.jar
[OR]
./gradlew bootRun

(Maven)
./mvnw clean package
java -jar target/gs-serving-web-content-0.1.0.jar
[OR] 
(Preferred!)
./mvnw spring-boot:run

Changing the default port of the embedded server: application.properties.
server.port = 8089

---

Extend your project to create a REST endpoint, which listens to the HTTP request, and answers with a JSON result:

A key difference between a traditional MVC controller and the RESTful web service controller is the way that the HTTP response body is created. 
Rather than relying on a view technology to perform server-side rendering of the greeting data to HTML, a RESTful web service controller populates and returns an object. 
The object data will be written directly to the HTTP response as JSON.

Spring @RestController annotation marks the class as a controller where every method returns a domain object instead of a view.
It is shorthand for including both @Controller and @ResponseBody.


--- FINAL Review questions

A) What are the responsibilities/services of a “servlet container”?

A "servlet container" is a component of a web server that interacts with Jakarta Servlets. It is responsible for:
    - Managing the lifecycle of servlets (create servlet instances, load and unload servlets, managing request and response objects, etc.)
    - Mapping a URL to a particular servlet
    - Ensuring that the URL requester has the correct access-rights
    - Handling requests to servlets, Jakarta Server Pages (JSP) files, and other types of files that include server-side code

---

B) Explain, in brief, the “dynamics” of Model-View-Controller approach used in Spring Boot to serve web content. 
(You may exemplify with the context of the previous exercises.)

The Spring Web Model-View-Controller (MVC) framework is designed around a DispatcherServlet that dispatches requests to handlers, with configurable handler mappings, view resolution, locale and theme resolution as well as support for uploading files. 
The default handler is based on the @Controller and @RequestMapping annotations, offering a wide range of flexible handling methods. 
(The @Controller mechanism also allows you to create RESTful Web sites and applications.)

In summary, the DispatcherServlet acts as the main Controller to route requests to their intended destination. 
Model is the data of our application, and the view is represented by any of the various template engines.

---

C) Inspect the POM.xml for the previous SpringBoot projects. What is the role of the “starters” dependencies?

"Starters" dependencies were created to reduce the amount of time spent on Dependency management by developers.
With these dependencies:
    - We can increase productivity by decreasing the Configuration time for developers.
    - We make managing the POM an easier task by decreasing the number of dependencies to be added.
    - We have tested, Production-ready, and supported dependency configurations.
    - We have no need to remember the name and version of the dependencies.

---

D) Which annotations are transitively included in the @SpringBootApplication?

@SpringBootApplication is a convenience annotation that includes all the following:
    - @Configuration: Tags the class as a source of bean definitions for the application context.
    - @EnableAutoConfiguration: Tells Spring Boot to start adding beans based on classpath settings, other beans, and various property settings.
    - @ComponentScan: Tells Spring to look for other components, configurations, and services in the com/example package, letting it find the controllers.

---

E) Search online for the topic “Best practices for REST API design”. 
From what you could learn, select your “top 5” practices, and briefly explain them in you own words.

1. Use JSON as the Format for Sending and Receiving Data 
   JSON is the better alternative to sending/receiving data, in comparison to XML. 
   With XML, for example, it's often a bit of a hassle to decode and encode data – so XML isn’t widely supported by frameworks anymore.

2. Use Nouns Instead of Verbs in Endpoints 
   When you're designing a REST API, the endpoints should use nouns, signifying what each of them does. 
   This is because HTTP methods such as GET, POST, PUT, PATCH, and DELETE are already in verb form for performing basic CRUD (Create, Read, Update, Delete) operations.

3. Name Collections with Plural Nouns
   A group of resources is called a collection. When you have to develop collections in REST API, you should name them with plural nouns.
   It prevents ambiguity, and it makes it easier to understand the meaning of collection.

4. Use Status Codes in Error Handling
   You should always use regular HTTP status codes in responses to requests made to your API.
   This will help your users to know what is going on – whether the request is successful, or if it fails, or something else.

5. Use Nesting on Endpoints to Show Relationships
   Oftentimes, different endpoints can be interlinked, so you should nest them to make them easier to understand.
   You should avoid nesting that is more than 3 levels deep as this can make the API less elegant and readable.