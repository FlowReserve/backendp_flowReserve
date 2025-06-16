package com.flowreserve.demo1.exceptions;

public class CustomExceptions {

    //Aqui se crean excepciones personalizadas para posteriormente ser capturadas en el CustomExceptionHandler

    public static class MedicoNotFoundException extends RuntimeException {
        public MedicoNotFoundException(String message) {
            super(message);
        }
    }

    public static class UnmodifiableRequestException extends RuntimeException {
        public UnmodifiableRequestException(String message) {
            super(message);
        }
    }

}
