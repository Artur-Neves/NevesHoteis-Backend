package br.com.nevesHoteis.infra.exeption;

public class ValidateHotelException extends MyExceptions {

    public ValidateHotelException(String field, String message) {
        super(field, message);
    }
}
