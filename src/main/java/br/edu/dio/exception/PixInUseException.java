package br.edu.dio.exception;

public class AccountNotFoundException extends RuntimeException{

    public AccountNotFoundException(String message) {
        super(message);
    }
}
