package br.com.nevesHoteis.infra.exeption;

public class MyExceptions extends RuntimeException{
protected final String field;

public MyExceptions(String field, String message) {
    super(message);
    this.field = field;
}

public String getField() {
    return field;
}
}
