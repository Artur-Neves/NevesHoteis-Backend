package br.com.nevesHoteis.controller;


import br.com.nevesHoteis.controller.Dto.*;
import br.com.nevesHoteis.domain.*;
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
import org.springframework.security.test.context.support.WithMockUser;


import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

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
    @Autowired
    private JacksonTester<Map<String, String>> mapJacksonTester;
    @Autowired
    private JacksonTester<PeopleCompleteDto> peopleCompleteDtoJacksonTester;
    @Autowired
    private JacksonTester<PeoplePersonalDataDto> peoplePersonalDataDtoJacksonTester;
    @Autowired
    private JacksonTester<PeopleAddressDataDto> peopleAddressDataDtoJacksonTester;
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
    private Address address;
    private People people;
    @BeforeEach
    void setUp() {
        user = new User(1L, "artur@gmail.com", true, "Ar606060", Role.USER, null);
        address = new Address( "76854-245", "BA", "Jequié", "Beira rio", "Rua Portugual");
        people = new SimpleUser(1L, "Artur", LocalDate.now().plusYears(-18), "123.456.890-90", "73988888888", address, user);

    }

    @Test
    @DisplayName("Testando o login do usuário")
    void test01() throws Exception {
        String token= "Test token";
        when(service.loadUserByUsername(any())).thenReturn(userDetails);
        when(manager.authenticate(any())).thenReturn(authentication);
        Map<String, String> map = new HashMap<>();
        when(tokenService.tokensAfterLoginToken(any())).thenReturn(map);
        mockMvc.perform(post("/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginDtoJacksonTester.write(new LoginDto(user)).getJson()))
                .andExpectAll(status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json(mapJacksonTester.write(map).getJson()));
    }
    @Test
    @DisplayName("Testando o refresh do token")
    void test02() throws Exception {
        String token= "Test token";
        when(tokenService.refreshToken(any())).thenReturn(token);
        mockMvc.perform(post("/user/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(tokenDtoJacksonTester.write(new TokenDto(token)).getJson()))
                .andExpectAll(status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json(tokenDtoJacksonTester.write(new TokenDto(token)).getJson()));
    }
    @Test
    @DisplayName("Testando a busca do usuário pelo login")
    void test3() throws Exception {
        when(service.findByLogin(any())).thenReturn(user);
        mockMvc.perform(get("/user/"+user.getLogin()))
                .andExpectAll(status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json(userDiscretDtoJacksonTester.write(new UserDiscretDto(user)).getJson()));
    }
    @Test
    @DisplayName("Testando a redefinição da senha do usuário")
    void test4() throws Exception {
        RedefinePasswordDto redefinePasswordDto = new RedefinePasswordDto(user.getLogin(), user.getPassword(), user.getPassword(), "TOKENS");
        doNothing().when(service).redefinePassword(any());
        mockMvc.perform(put("/user/redefine-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(redefinePasswordDtoJacksonTester.write(redefinePasswordDto).getJson()))
                .andExpectAll(status().isNoContent());
        then(service).should().redefinePassword(any());
    }
    @Test
    @DisplayName("Testando o retorno das informações da conta")
    void test05() throws Exception {
        when(service.findPeopleByLogin()).thenReturn(people);
        mockMvc.perform(get("/user/myAccount"))
                .andExpectAll(status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json(peopleCompleteDtoJacksonTester.write(new PeopleCompleteDto(people)).getJson()));
    }
    @WithMockUser
    @Test
    @DisplayName("Testando a atualização dos dados pessoais da conta")
    void test06() throws Exception {
        when(service.updateMyAccount(any(People.class))).thenReturn(people);
        mockMvc.perform(put("/user/updatePersonalDataAccount")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(peoplePersonalDataDtoJacksonTester.write(new PeoplePersonalDataDto(people)).getJson()))
                .andExpectAll(status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json(peopleCompleteDtoJacksonTester.write(new PeopleCompleteDto(people)).getJson()));
    }
    @WithMockUser
    @Test
    @DisplayName("Testando a atualização dos dados relacionados ao endereço da conta")
    void test07() throws Exception {
        when(service.updateMyAccount(any(People.class))).thenReturn(people);
        mockMvc.perform(put("/user/updateAddressAccount")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(peopleAddressDataDtoJacksonTester.write(new PeopleAddressDataDto(people)).getJson()))
                .andExpectAll(status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json(peopleCompleteDtoJacksonTester.write(new PeopleCompleteDto(people)).getJson()));
    }
}