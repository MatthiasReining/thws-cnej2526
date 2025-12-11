package de.thws.jakarta.hello;

import java.time.ZonedDateTime;

public class Hello {

    private String name;
    private ZonedDateTime timestamp = ZonedDateTime.now();

    public Hello(String name) {
        this.name = name;
    }

    public String getHello() {
        return name;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }
}
