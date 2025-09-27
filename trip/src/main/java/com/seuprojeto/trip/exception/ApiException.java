package com.seuprojeto.trip.exception;

public class ApiException extends RuntimeException {
    private final String code;  // Código para distinguir tipos de erro

    // Construtor para exceção genérica com apenas a mensagem
    public ApiException(String message) {
        super(message);  // Passa a mensagem para a superclasse (RuntimeException)
        this.code = "GENERIC_ERROR";  // Código genérico para exceções não específicas
    }

    // Construtor com código de erro específico
    public ApiException(String message, String code) {
        super(message);  // Passa a mensagem para a superclasse (RuntimeException)
        this.code = code;  // Código para distinguir tipos específicos de erro
    }

    // Método para obter o código do erro
    public String getCode() {
        return code;
    }
}
