package br.com.nevesHoteis.infra.exeption;

public class ValidateUserException extends MyExceptions {

    public ValidateUserException(String field, String message) {
        super(field, message);
    }
}
