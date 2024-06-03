package br.com.nevesHoteis.domain;

import org.hibernate.validator.internal.constraintvalidators.bv.notempty.NotEmptyValidatorForArray;
import org.springframework.security.core.GrantedAuthority;

import java.util.Arrays;

public enum Roles implements GrantedAuthority {
    ADMIN("admin"),
    EMPLOYEE("employee"),
    USER("user");
    private String role;
    Roles(String role) {
        this.role= role;
    }

    @Override
    public String getAuthority() {
        return role;
    }
}
