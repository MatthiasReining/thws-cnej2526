# Calculator App

The Calculator app contains some calucation functionalities for the _THWS Magic Platform_.

Furthermore it's based on Full JakartaEE infrastructure including servlets.
Therefore _Servlets_ examples are placed here, too.

# Inrastructrue

- Base generation from https://start.jakarta.ee/

## Server Wildfly 38.0.1.Final

- Download from https://www.wildfly.org/downloads/
- Extracted to ../srv/wildfly-38.0.1.Final

- Adapt `pom.xml` with `<jbossHome>${project.basedir}/../../srv/wildfly-38.0.1.Final</jbossHome>`

## Run

- Start server

  > ../../srv/bin/standalone.bat

- Server stop

  `Ctrl+C`

- Build and deploy  
   Server must be stopped
  _CalculatorApp_

  > mvnw clean compile wildfly:dev

# Development

## Average Servlet

http://localhost:8080/calculator/average?grades=2&grades=7

- Calculates the average of the _incoming_ grades.

- Includes also a basic error handling and 400 status code

## ThradSafeFailureServlet

http://localhost:8080/calculator/thread

- demostrate how to misuse class member variables
- A servlet is not thread safe.
- It initalized (and destroyed) by the container. There could be one instance or more of the servlet.
- The `GET` (or all other HTTP methods) are `served` by the same servlet.
- Member variables are shared between requests
