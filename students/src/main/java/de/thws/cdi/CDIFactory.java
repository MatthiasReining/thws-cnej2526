package de.thws.cdi;

import jakarta.enterprise.inject.Produces;

public class CDIFactory {

    @Produces
    String message = "Hello THWS";

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
        // You can switch between different Logger implementations here
        // beste case it depends on your configuration (e.g., dev vs. prod)
        return new SpecialLogger();
    }
}
