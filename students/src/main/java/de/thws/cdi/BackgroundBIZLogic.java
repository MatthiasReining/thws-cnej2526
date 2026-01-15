package de.thws.cdi;

import jakarta.enterprise.event.Observes;

public class BackgroundBIZLogic {

    public void logicListener(@Observes String data) {
        System.out.println("Background logicListener received data: " + data);
    }
}
