package de.thws.cdi;

import java.util.Date;

public class SpecialLogger implements Logger {

    @Override
    public void log(String message) {
        System.out.println("Special logger: (" + new Date() + ") " + message);
    }

}
