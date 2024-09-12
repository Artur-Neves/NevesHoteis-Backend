package br.com.nevesHoteis.infra.exeption;

import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.security.sasl.AuthenticationException;
import java.nio.file.AccessDeniedException;
import java.sql.SQLException;

@ControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler(EntityNotFoundException.class)
    ResponseEntity<?> notFoundException (Exception e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<?> methordArgument(MethodArgumentNotValidException e){
        return ResponseEntity.badRequest().body( new ErrorFormation(e.getFieldError().getField(), e.getFieldError().getDefaultMessage()));
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    ResponseEntity<?> methordArgument(HttpMessageNotReadableException e){
        return ResponseEntity.badRequest().body( new ErrorFormation("Error HTTP", e.getMessage() ));
    }
    @ExceptionHandler(SQLException.class)
    ResponseEntity<?> sqlException(SQLException e){
        return ResponseEntity.badRequest().body( new ErrorFormation("Error SQL", e.getMessage() ));
    }
    @ExceptionHandler(MyExceptions.class)
    ResponseEntity<?> validateUserException(MyExceptions e){
        return ResponseEntity.badRequest().body(new ErrorFormation(e.getField(), e.getMessage()));
    }
    @ExceptionHandler(AuthenticationException.class)
    ResponseEntity<?> validateUserException(AuthenticationException e){
        return ResponseEntity.badRequest().body(new ErrorFormation("Authenticação falhou", e.getMessage()));
    }
    @ExceptionHandler({AccessDeniedException.class, DisabledException.class})
    ResponseEntity<?> accessDenied(Exception e){
        return ResponseEntity.status(403).body(new ErrorFormation("error", e.getMessage()));
    }
    @ExceptionHandler({BadCredentialsException.class, UsernameNotFoundException.class, InternalAuthenticationServiceException.class})
    ResponseEntity<?> invalidCredentials(Exception e){
        return ResponseEntity.badRequest().body(new ErrorFormation("Login", "Credenciais inválidas"));
    }
    @ExceptionHandler(MessagingException.class)
    ResponseEntity<?> notSendEmail (Exception e){
        return ResponseEntity.badRequest().body(new ErrorFormation("Email", "Erro ao tentar enviar o email"));
    }
//    @ExceptionHandler(Exception.class)
//    ResponseEntity<?> jokerResponseError(Exception e){
//        return ResponseEntity.badRequest().body(new ErrorFormation("Joker", e.getMessage()));
//    }


    private record ErrorFormation(
            String field,
            String message
    ){

    }

}
