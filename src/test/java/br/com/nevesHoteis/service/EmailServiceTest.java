package br.com.nevesHoteis.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {
    @InjectMocks
    private EmailService service;
    @Mock
    private JavaMailSender mailSenderMock;
    @Mock
    private MimeMessage mimeMock;
    @Mock
    private MimeMessageHelper helper;

    @Test
    @DisplayName("Testando o envio do email")
    void test01() throws MessagingException {
        assertDoesNotThrow(()->service.sendConfirmEmail("Artur", "token"));
        then(mailSenderMock).should().send(any(MimeMessage.class));
        then(helper).should().setTo("Artur");
        then(helper).should().setSubject(any());
        then(helper).should().setText(any(), eq(true));
        then(helper).should(times(2)).addInline(any(), any(ClassPathResource.class));
    }
    @Test
    @DisplayName("Testando o retorno da String")
    void test02(){
        assertNotEquals("" , service.buildHtmlContent("TEST"));
    }
}