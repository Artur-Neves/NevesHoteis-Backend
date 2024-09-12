package br.com.nevesHoteis.infra.exeption;

public class ValidatePromotionException extends MyExceptions{
    public ValidatePromotionException(String field, String message) {
        super(field, message);
    }
}
