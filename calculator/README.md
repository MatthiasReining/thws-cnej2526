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

See `servlet` package.
