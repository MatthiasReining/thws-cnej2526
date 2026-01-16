package de.thws.cdi;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;

public class CDIFactory {

    @Produces
    String message = "Hello THWS";

    @Inject
    @ConfigProperty(name = "thws.logger", defaultValue = "CONSOLE")
    String loggerType;

    @Produces
    @MessageWorld
    public String produceMessage2() {
        return "Hello World!";
    }

    public String upperCase(String input) {
        return input.toUpperCase();
    }

    @Produces
    public Logger produceLogger() {

        if ("SPECIAL".equalsIgnoreCase(loggerType)) {
            return new SpecialLogger();

        }

        // You can switch between different Logger implementations here
        // beste case it depends on your configuration (e.g., dev vs. prod)
        return new ConsoleLogger();
    }
}
