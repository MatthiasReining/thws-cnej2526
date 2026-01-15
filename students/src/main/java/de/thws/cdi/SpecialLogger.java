package de.thws.cdi;

import java.util.Date;

public class SpecialLogger implements Logger {

    @Override
    public void log(String message) {
        System.out.println("special log: (" + new Date() + ") " + message);
    }

}
