package de.thws.cdi;

import java.util.Random;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;

@ApplicationScoped
public class BusinessLogic {

    @Inject
    Event<String> logic2Event;

    @THWSUpperCase
    @THWSPerformanceMeasurement
    public String process(String input) {
        return input;
    }

    public String logic2(String data) {
        // Implementation for triggering an event

        var value = data + ":" + new Random().nextInt(1000);
        System.out.println("heavy work on logic2 with value: " + value);

        logic2Event.fire(value);

        return value;

    }
}
