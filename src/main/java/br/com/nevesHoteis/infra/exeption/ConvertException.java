package br.com.nevesHoteis.infra.exeption;

public class ConvertException extends MyExceptions{
    public ConvertException( String message) {
        super("Error in conversion", message);
    }
}
