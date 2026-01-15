package de.thws.exceptionmanagement.boundary;

public record ErrorDTO(String message, int status, String path, String timestamp, String details) {

}
