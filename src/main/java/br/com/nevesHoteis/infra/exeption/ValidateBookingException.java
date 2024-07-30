package br.com.nevesHoteis.infra.exeption;

public class ValidateBookingException extends MyExceptions{
    public ValidateBookingException(String field, String message) {
        super(field, message);
    }
}
