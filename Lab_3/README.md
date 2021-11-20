--- 3.1 Accessing databases in SpringBoot

The Java Persistence API (JPA) defines a standard interface to manage data over relational databases.
(Example: Hibernate framework implements the JPA specification.)

It is concerned with persistence, which loosely means any mechanism by which Java objects outlive the application process that created them.
The JPA specification lets you define which objects should be persisted, and how those objects should be persisted in your Java applications.
JPA is not a tool or framework; rather, it defines a set of concepts that can be implemented by any tool or framework.

JPA offers a specification for Object-Relational Mapping (ORM). Spring Data uses and enhances the JPA. 
With Spring Data your Java code is independent of the specific database implementation.

IMPORTANT 
Needed “starter” dependencies to the project:
→ Spring Web, Thymeleaf, Spring Data JPA, H2 database, and Validation.

---

The Domain Layer
A collection of entity objects and related business logic that is designed to represent the enterprise business model. 
The major scope of this layer is to create a standardized and federated set of objects, that could be potentially reused within different projects.

Domain class with @Entity annotation:
The JPA implementation will be able to perform CRUD operations on the domain entities.

@NotBlank constraint:
We can use Hibernate Validator for validating the constrained fields before persisting or updating an entity in the database.

--- 

The Repository Layer

Spring Data JPA is a key component that makes it easy to add CRUD functionality through a powerful layer of abstraction placed on top of a JPA implementation. 
This abstraction layer allows us to access the persistence layer without having to provide our own DAO implementations from scratch.

@Repository
public interface ExampleRepository extends CrudRepository<User, Long> {}

By extending the CrudRepository interface, Spring Data JPA will provide implementations for the repository's CRUD methods

---

The Controller Layer

Thanks to the layer of abstraction that spring-boot-starter-data-jpa places on top of the underlying JPA implementation, 
we can easily add some CRUD functionality to our web application through a basic web tier.

A controller class will handle all the GET and POST HTTP requests and then map them to calls to our ExampleRepository implementation

@Autowired annotation:
(With Constructors) An instance of ExampleInput is injected by Spring as an argument to the ExampleService constructor.

The View Layer

Thymeleaf is used as the underlying template engine for parsing the template files.

The @{/url} URL expression is used to specify the form's action attribute and the ${object}/*{field} variable expressions for 
Embedding dynamic content in the template, such as the values of the name and email fields and the post-validation errors.

---

Questions (concerning exercise GuidedExample)

1. The “UserController” class gets an instance of “userRepository” through its constructor; how is this new repository instantiated?
This new repository is instantiated by annotating the @Autowired annotation to the UserController constructor.
This means that the value of the constructor argument ("UserRepository") is passed automatically while the instance class is created. 
In the spring boot, @Autowired assigns values to the constructor. The repository is injected by Spring as an argument to the "UserController" constructor.

2. List the methods invoked in the “userRepository” object by the “UserController”. Where are these methods defined?
The methods invoked are: save(User user); findAll(); findById(long id); delete(User user).
These methods are defined in the CrudRepository interface, the same interface "userRepository" extends.
By extending this interface, Spring Data JPA provides implementations for the repository's CRUD methods.

3. Where is the data being saved?
H2 is being used as an in-memory database, which means it stores the data in memory and will not persist data on disk.

4. Where is the rule for the “not empty” email address defined?
The rule is defined by annotating a @NotBlank constrain to the value email (in entity User).

--- Multilayer applications: exposingdata with RESTinterface

Create an instance of MySQLserver (version 5.7)) with docker container:
$ docker run --name mysql5 -e MYSQL_ROOT_PASSWORD=secret1 -e MYSQL_DATABASE=demo -e MYSQL_USER=demo -e MYSQL_PASSWORD=secret2 -p 33060:3306 -d mysql/mysql-server:5.7

We need to define the database connection properties with the application.properties resource file:

# MySQL
spring.datasource.url=jdbc:mysql://127.0.0.1:33060/demo
spring.datasource.username=demo
spring.datasource.password=secret2
spring.jpa.database-platform=org.hibernate.dialect.MySQL5InnoDBDialect

# Strategy to auto update the schemas (create, create-drop, validate, update)
spring.jpa.hibernate.ddl-auto = update

---

Configuring DataSource

Spring boot allows defining datasource configuration in both ways i.e. Java config and properties config. 

DataSourceAutoConfiguration checks for DataSource.class (or EmbeddedDatabaseType.class) on the classpath and few other things before configuring a DataSource bean for us.

1. Include spring-boot-starter-data-jpa to project

2. Configure application.properties

DataSource configuration is provided by external configuration properties ( spring.datasource.* ) in application.properties file

The properties configuration decouple the configuration from application code. 
This way, we can import the datasource configurations from even configuration provider systems.

We often do not need to specify the driver-class-name, since Spring Boot can deduce it for most databases from the url

---

@RequestParam(name = "email", required = false) Optional<String>

--- 3.3 Wrapping-up and integrating concepts

Spring Boot Project Three-Layer Architecture

Presentation layer/API Layer: This is the user interface of the application that presents the application’s features and data to the user.

Business/Service layer: This layer contains the business logic that drives the application’s core functionalities. Like making decisions, calculations, evaluations, and processing the data passing between the other two layers.

Data access object (DAO) layer: This layer is responsible for interacting with databases to save and restore application data.

---

Deploy the application to a Docker container (dockerize your Spring Boot application)

A Spring Boot application is easy to convert into an executable JAR file. 
With Maven, you run ./mvnw install, With Gradle, you run ./gradlew build. 

A basic Dockerfile to run that JAR would then look like this, at the top level of your project:

FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

1. You can pass in the JAR_FILE as part of the docker command.

For Maven, the following command works:
docker build --build-arg JAR_FILE=target/*.jar -t myorg/myapp .COPY

For Gradle, the following command works:
docker build --build-arg JAR_FILE=build/libs/*.jar -t myorg/myapp .

2. You can hard code the JAR location. For Maven, that would be as follows:

FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

Build an image with the following command:
docker build -t myorg/myapp .

Run it by running the following command:
docker run -p 8080:8080 myorg/myapp

Peek into a running container:

docker run --name myapp -ti --entrypoint /bin/sh myorg/myapp
docker exec -ti myapp /bin/sh

(Where myapp is the --name passed to the docker run command)

---

Build Plugins

You can use the Spring Boot build plugins for Maven and Gradle to create container images. 
The plugins create an OCI image (the same format as one created by docker build) by using Cloud Native Buildpacks. 

You do not need a Dockerfile, but you do need a Docker daemon, either locally (same as build with docker) or remotely through the DOCKER_HOST environment variable. 
The default builder is optimized for Spring Boot applications, and the image is layered efficiently.

The following example works with Maven without changing the pom.xml file:
./mvnw spring-boot:build-image -Dspring-boot.build-image.imageName=myorg/myapp
[WINDOWS]
./mvnw spring-boot:build-image -D"spring-boot.build-image.imageName"=myorg/myapp

The following example works with Gradle, without changing the build.gradle file:
./gradlew bootBuildImage --imageName=myorg/myapp

Run the image:
docker run -p 8080:8080 -t myorg/myapp

Note: Add to <java.version>11</java.version> to <java.version>8</java.version>

--- REVIEW QUESTIONS

A. Explain the differences between the RestController and Controller components used in different parts of this lab.

@Controller annotation is a specialization of the @Component class, which allows us to auto-detect implementation classes through the classpath scanning.
We typically use @Controller in combination with a @RequestMapping annotation for request handling methods.

On other hand, @RestController is a specialized version of the controller. It includes the @Controller and @ResponseBody annotations.
This simplifies the controller implementation, as every request handling method of the controller class automatically serializes return objects into HttpResponse.

---

B. Create a visualization of the Spring Boot layers (UML diagramor similar), displaying the key abstractions in the solution of 3.3, in particular: entities, repositories, servicesand REST controllers.

Describe the role of the elements modeled in the diagram.

[Check /uml in project folder MovieQuotes]

(Movie/Quote)

Entity: Specifies that the class is an entity and is mapped to a database table.
Table: Specifies the primary table in the database for entity.
Data: Bundles the features of @ToString, @EqualsAndHashCode, @Getter / @Setter and @RequiredArgsConstructor together.
AllArgsConstructor: Generates a constructor with no parameters.
NoArgsConstructor: Generates a constructor with 1 parameter for each field in your class. 

(MovieController)

RestController: Defines a controller with ResponseBody implied on the handler methods.

(MovieRepository/QuoteRepository)

JpaRepository: Extends CrudRepository and exposes the capabilities of the underlying persistence technology in addition to the rather generic persistence technology-agnostic interfaces such as CrudRepository.

---

C. Explain the annotations @Table, @Column, @Id found in the Employee entity.

@Table:
Specifies the primary table in the database for the Employee entity. 
We can specify the table name using the @Table annotation (In most cases, the name of the table in the database and the name of the entity will not be the same).

@Column
Specifies the details of a column in the table created for the Employee entity. In other words, it specifies the fields of Employee entity.
If we don't include this annotation, the name of the field will be considered the name of the column.
(The @Column annotation has many attributes such as name, length, nullable, and unique.)

@Id:
Each JPA entity must have a primary key which uniquely identifies it. 
The @Id annotation defines the primary key of Employee entity. 
(We can generate the identifiers in different ways with the @GeneratedValue annotation.)

---

D. Explain the use of the annotation @AutoWired (in the Rest Controller class).

Spring provides a way to automatically detect the relationships between various beans by using the @Autowired annotation.
The @Autowired annotation is used to auto wire a bean on the setter method, constructor or a field, while telling Spring where an injection needs to occur. 

This is particularly useful in the Rest Controller class in order to allow the Rest Controller and the Service to communicate and collaborate with each other.
By anotating @Autowired to the constructor of Rest Controller (with Service as a parameter), the Service will be automatically injected.