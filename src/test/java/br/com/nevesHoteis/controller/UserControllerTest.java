package br.com.nevesHoteis.controller;


import br.com.nevesHoteis.controller.Dto.LoginDto;
import br.com.nevesHoteis.controller.Dto.RedefinePasswordDto;
import br.com.nevesHoteis.controller.Dto.TokenDto;
import br.com.nevesHoteis.controller.Dto.UserDiscretDto;
import br.com.nevesHoteis.domain.Role;
import br.com.nevesHoteis.domain.User;
import br.com.nevesHoteis.service.TokenService;
import br.com.nevesHoteis.service.UserService;
import org.junit.jupiter.api.BeforeEach;
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
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends BaseControllerTest<UserService>{
    @Autowired
    private JacksonTester<LoginDto> loginDtoJacksonTester;
    @Autowired
    private JacksonTester<TokenDto> tokenDtoJacksonTester;
    @Autowired
    private JacksonTester<UserDiscretDto> userDiscretDtoJacksonTester;
    @Autowired
    private JacksonTester<RedefinePasswordDto> redefinePasswordDtoJacksonTester;
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
    private User user;
    @BeforeEach
    void setUp() {
        user = new User(1L, "artur@gmail.com", true, "Ar606060", Role.USER, null);
    }

    @Test
    @DisplayName("Testando o login do usuário")
    void test01() throws Exception {
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
    @Test
    @DisplayName("Testando a busca do usuário pelo login")
    void test2() throws Exception {
        when(service.findByLogin(any())).thenReturn(user);
        mockMvc.perform(get("/user/"+user.getLogin()))
                .andExpectAll(status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json(userDiscretDtoJacksonTester.write(new UserDiscretDto(user)).getJson()));
    }
    @Test
    @DisplayName("Testando a redefinição da senha do usuário")
    void test3() throws Exception {
        RedefinePasswordDto redefinePasswordDto = new RedefinePasswordDto(user.getLogin(), user.getPassword(), user.getPassword(), "TOKENS");
        doNothing().when(service).redefinePassword(any());
        mockMvc.perform(put("/user/redefine-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(redefinePasswordDtoJacksonTester.write(redefinePasswordDto).getJson()))
                .andExpectAll(status().isNoContent());
        then(service).should().redefinePassword(any());
    }
}