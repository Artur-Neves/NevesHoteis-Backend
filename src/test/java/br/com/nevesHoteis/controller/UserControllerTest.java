package br.com.nevesHoteis.controller;


import br.com.nevesHoteis.controller.Dto.LoginDto;
import br.com.nevesHoteis.controller.Dto.TokenDto;
import br.com.nevesHoteis.domain.Role;
import br.com.nevesHoteis.domain.User;
import br.com.nevesHoteis.service.TokenService;
import br.com.nevesHoteis.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends BaseControllerTest<UserService>{
    @Autowired
    private JacksonTester<LoginDto> loginDtoJacksonTester;
    @Autowired
    private JacksonTester<TokenDto> tokenDtoJacksonTester;
    @MockBean
    private TokenService tokenService;
    @MockBean
    private AuthenticationManager manager;
    @Mock
    private LoginDto loginDto;
    @Mock
    private UserDetails userDetails;
    @Mock
    private Authentication authentication;
    @Test
    @DisplayName("")
    void test01() throws Exception {
        User user = new User("Artur@gmail.com", "Ar882233", Role.USER);
        String token= "Test token";
        when(service.loadUserByUsername(any())).thenReturn(userDetails);
        when(manager.authenticate(any())).thenReturn(authentication);
        when(tokenService.createdToken(any())).thenReturn(token);
        mockMvc.perform(post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginDtoJacksonTester.write(new LoginDto(user)).getJson()))
                .andExpectAll(status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json(tokenDtoJacksonTester.write(new TokenDto(token)).getJson()));
    }

}