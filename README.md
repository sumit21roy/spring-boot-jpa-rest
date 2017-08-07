# Spring Boot "Microservice" Example Project

This is a sample Java / Maven / Spring Boot application that can be used as a starter for creating a microservice complete with built-in user detail.

## How to Run 

This application is packaged as a war which has Tomcat 7 embedded. No Tomcat or JBoss installation is necessary. You run it using the ```java -jar``` command.

* Clone this repository 
* Make sure you are using JDK 1.7 and Maven 3.x
* You can build the project and run the tests by running ```mvn clean package```
* Once successfully built, you can run the service by one of these two methods:
```
        java -jar -Dspring.profiles.active=test target/spring-boot-rest-example-0.3.0.war
or
        mvn spring-boot:run -Drun.arguments="spring.profiles.active=test"
```
* Check the stdout to make sure no exceptions are thrown

Once the application runs you should see something like this

```
2014-10-04 18:24:58.870  INFO 10190 --- [           main] s.b.c.e.t.TomcatEmbeddedServletContainer : Tomcat started on port(s): 8090/http
2014-10-04 18:24:58.872  INFO 10190 --- [           main] com.sargent.task.Application         : Started Application in 6.764 seconds (JVM running for 7.06)
```

## About the Service

The service is just a simple user detail REST service. It uses an in-memory database to store the data. You can also do with a relational database like MySQL or PostgreSQL. If your database connection properties work, you can call some REST endpoints defined in ```com.sargent.task.api.rest.UserDetailController``` on **port 8090**. (see below)

You can use this sample service to understand the conventions and configurations that allow you to create a DB-backed RESTful service. Once you understand and get comfortable with the sample app you can add your own services following the same patterns as the sample service.
 
Here is what this little application demonstrates: 

* Full integration with the latest **Spring** Framework: inversion of control, dependency injection, etc.
* Packaging as a single war with embedded container (tomcat 7): No need to install a container separately on the host just run using the ``java -jar`` command
* Demonstrates how to set up user detail, info, environment, etc. endpoints automatically on a configured port.
* Writing a RESTful service using annotation: supports both XML and JSON request / response; simply use desired ``Accept`` header in your request
* Exception mapping from application exceptions to the right HTTP response with exception details in the body
* *Spring Data* Integration with JPA/Hibernate with just a few lines of configuration and familiar annotations. 
* Automatic CRUD functionality against the data source using Spring *Repository* pattern
* Demonstrates MockMVC test framework with associated libraries
* All APIs are "self-documented" by Swagger using annotations 

Here are some endpoints you can call:

### Get information about the environment.

```
http://localhost:8091/env

```
### Create a User details resource

```
POST /example/v1/userDetails
Accept: application/json
Content-Type: application/json

Here id is auto generated, so Id will be generate based on the data is created in datasourse

{
"name" : "Thomas",
"location" : "London",
"text" : "Lorem,ipsum,dolor,sit,amet,duo cu meis latine atomorum, anperfecto mnesarchum mel. Ad nam agam legendos, ne facilisi perpetua mel.Cu nam duis iudico pertinacia, ad alterum suscipit vel, eos te cibo mutat. Sit an alia facer efficiantur. Ex,vel,porro,fabellas,dignissim, in facer dolorem deleniti eam."
}

RESPONSE: HTTP 201 (Created)
Location header: http://localhost:8090/example/v1/userDetails/1
```

# Running the project with MySQL

This project uses an in-memory database so that you don't have to install a database in order to run it. However, converting it to run with another relational database such as MySQL or PostgreSQL is very easy. Since the project uses Spring Data and the Repository pattern, it's even fairly easy to back the same service with MongoDB. 

Here is what you would do to back the services with MySQL, for example: 

### In pom.xml add: 

```
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>
```

### Append this to the end of application.yml: 

```
---
spring:
  profiles: mysql

  datasource:
    driverClassName: com.mysql.jdbc.Driver
    url: jdbc:mysql://<your_mysql_host_or_ip>/bootexample
    username: <your_mysql_username>
    password: <your_mysql_password>

  jpa:
    hibernate:
      dialect: org.hibernate.dialect.MySQLInnoDBDialect
      ddl-auto: update # todo: in non-dev environments, comment this out:


userDetail.service:
  name: 'test profile:'
```

### Then run is using the 'mysql' profile:

```
        java -jar -Dspring.profiles.active=mysql target/spring-boot-rest-example-0.3.0.war
or
        mvn spring-boot:run -Drun.arguments="spring.profiles.active=mysql"
```

# Attaching to the app remotely from your IDE

Run the service with these command line options:

```
mvn spring-boot:run -Drun.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=5005"
```
and then you can connect to it remotely using your IDE. For example, from IntelliJ You have to add remote debug configuration: Edit configuration -> Remote.






