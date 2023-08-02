package org.apiweather.exceptions;

public class ApiWeatherException extends RuntimeException {
    // Constructor que recibe un mensaje de error para la excepción
    public ApiWeatherException(String message) {
        // Llama al constructor de la superclase (RuntimeException) con el mensaje de error proporcionado
        super(message);
    }
}
