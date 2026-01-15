package de.thws.cdi;

import jakarta.enterprise.event.Observes;

public class BackgroundFarAwayLogic {

    public void logicListener(@Observes String data) {
        System.out.println("BackgroundFarAwayLogic also works on data: " + data);
    }
}
