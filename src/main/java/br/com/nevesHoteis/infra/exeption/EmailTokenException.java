package br.com.nevesHoteis.infra.exeption;

public class EmailTokenException extends MyExceptions {

    public EmailTokenException(String field, String message) {
        super(field, message);
    }
}
