package br.com.nevesHoteis.controller;

import br.com.nevesHoteis.controller.dto.token.TimeTokenEmailDto;
import br.com.nevesHoteis.controller.dto.token.TokenDto;
import br.com.nevesHoteis.controller.dto.token.TokenEmailDto;
import br.com.nevesHoteis.controller.dto.user.LoginDto;
import br.com.nevesHoteis.domain.User;
import br.com.nevesHoteis.domain.VerificationEmailToken;
import br.com.nevesHoteis.service.VerificationEmailTokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VerificationEmailTokenControllerTest extends BaseControllerTest<VerificationEmailTokenService> {
    @Autowired
    private JacksonTester<LoginDto> loginDtoJacksonTester;
    @Autowired
    private JacksonTester<TokenDto> tokenDtoJacksonTester;
    @Autowired
    private JacksonTester<TimeTokenEmailDto> timeTokenEmailDtoJacksonTester;
    @Autowired
    private JacksonTester<TokenEmailDto> tokenEmailDtoJacksonTester;
    VerificationEmailToken verificationEmailToken;

    @BeforeEach
    void setUp() {
        verificationEmailToken = new VerificationEmailToken(1L, new User(), "TOKENS", LocalDateTime.now(), 30,  LocalDateTime.now().plusSeconds(30) );
    }

    @Test
    @DisplayName("Testando a criação ou reenvio do token")
    void test01() throws Exception {
        when(service.createdOrResend(any(TokenEmailDto.class)))
                .thenReturn(verificationEmailToken);
        mockMvc.perform(post("/email-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tokenEmailDtoJacksonTester.write( new TokenEmailDto("fulano@gmail.com", "TOKENS")).getJson()))
                .andExpectAll(status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json(timeTokenEmailDtoJacksonTester.write(new TimeTokenEmailDto(verificationEmailToken.getResendIntervalSeconds())).getJson()));

    }
    @Test
    @DisplayName("Testando a verificação do token")
    void test02() throws Exception {
        mockMvc.perform(post("/email-token/verify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tokenEmailDtoJacksonTester.write( new TokenEmailDto("fulano@gmail.com", "TOKENS")).getJson()))
                .andExpectAll(status().isNoContent());
        then(service).should().verify(any());

    }

}