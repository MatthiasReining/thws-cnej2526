package de.thws.log.control;

import de.thws.chat.boundary.LogMessage;
import de.thws.log.entity.LogData;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.ObservesAsync;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class LogService {

    @Transactional(value = Transactional.TxType.REQUIRES_NEW)
    public void log(String message) {

        LogData logData = new LogData();
        logData.message = message;
        logData.persist();

        // Exception is thrown to simulate failure
        System.out.println("Fehler: " + 42 / 0);
    }

    @Transactional(value = Transactional.TxType.REQUIRES_NEW)
    public void logCorrect(String message) {

        LogData logData = new LogData();
        logData.message = message;
        logData.persist();

    }

    public void chatNews(@ObservesAsync @LogMessage String message) {
        System.out.println("Logging chat message: " + message);
        logCorrect("Chat message sent: " + message);
    }
}
