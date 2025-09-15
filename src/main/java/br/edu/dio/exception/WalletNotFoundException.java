package br.edu.dio.exception;

public class WalletNotFoundException extends RuntimeException{

    public WalletNotFoundException(String message) {
        super(message);
    }
}
