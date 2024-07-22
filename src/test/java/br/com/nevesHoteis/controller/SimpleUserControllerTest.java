package br.com.nevesHoteis.controller;

import br.com.nevesHoteis.controller.dto.people.SimpleUserDto;
import br.com.nevesHoteis.domain.*;
import br.com.nevesHoteis.controller.dto.people.PeopleCompleteDto;
import br.com.nevesHoteis.controller.dto.people.PeopleDto;
import br.com.nevesHoteis.controller.dto.people.PeopleUpdateDto;
import br.com.nevesHoteis.service.SimpleUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WithMockUser(authorities = "USER")
class SimpleUserControllerTest extends PeopleControllerTest<SimpleUser, SimpleUserService> {
    @Autowired
    JacksonTester<SimpleUserDto> simpleUserDtoJacksonTester;
    private SimpleUser simpleUser = new SimpleUser();
    private Address address = new Address();
    private User user = new User();
    @BeforeEach
    void setUp() {
        user = new User(1L, "artur@gmail.com",true , "Ar606060", Role.USER, null);
        address = new Address( "76854-245", "BA", "Jequié", "Beira rio", "Rua Portugual");
        simpleUser = new SimpleUser(1L, "Artur", LocalDate.now().plusYears(-18), "123.456.890-90", "73988888888", address, user);
    }

    @Test
    @DisplayName("Testando o selecionamento de todos os usuário simples")
    void test01() throws Exception {
        Page<SimpleUser> page = new PageImpl<>(List.of(simpleUser));
        when(service.findAll(any())).thenReturn(page);
        mockMvc.perform(get("/simple-user"))
                .andExpectAll(status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json(pageCompleteDtoJacksonTester.write(page.map(PeopleCompleteDto::new)).getJson()));
    }


    @WithMockUser
    @Test
    @DisplayName("Testando o salvamento de um usuário simples")
    void test02() throws Exception {
        when(service.save(any())).thenReturn(simpleUser);
        mockMvc.perform(creatingFormData(HttpMethod.POST, "/simple-user"))
                .andExpectAll(status().isCreated(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json(completeDtoJacksonTester.write( new PeopleCompleteDto(simpleUser)).getJson()));
    }
    @Test
    @DisplayName("Testando a atualização de um usuário simples")
    void test03() throws Exception{

        when(service.update(any(), any())).thenReturn(simpleUser);
        mockMvc.perform(creatingFormData(HttpMethod.PUT, "/simple-user/"+simpleUser.getId()))
                .andExpectAll(status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json(updateDtoJacksonTester.write( new PeopleUpdateDto(simpleUser)).getJson()));
    }

    @Test
    @DisplayName("Testando o selecionamento de um usuário simples pelo id")
    void test04() throws Exception{
        when(service.findById(anyLong())).thenReturn(simpleUser);
        mockMvc.perform(
                        get("/simple-user/"+simpleUser.getId()))
                .andExpectAll(status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json(completeDtoJacksonTester.write(new PeopleCompleteDto(simpleUser)).getJson()));
    }
    @Test
    @DisplayName("Testando a exclusão de um usuário simples")
    void test05() throws Exception{
        mockMvc.perform(
                        delete("/simple-user/"+1))
                .andExpectAll(status().isNoContent());
        then(service).should().delete(anyLong());
    }
    @Test
    @DisplayName("Testando a criação simples de um user")
    void test06() throws Exception{
        when(service.simpleSave(any())).thenReturn(simpleUser);
        mockMvc.perform(post("/simple-user/create-simple")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(simpleUserDtoJacksonTester.write( new SimpleUserDto(simpleUser)).getJson()))
                .andExpectAll(status().isCreated(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json(simpleUserDtoJacksonTester.write( new SimpleUserDto(simpleUser)).getJson()));
    }

    @Test
    @DisplayName("Retornando erro ao testar uma entidade que não existe ")
    void test07() throws Exception {
        mockMvc.perform(get("/hotel/"))
                .andExpectAll(status().isNotFound());
    }
    MockMultipartHttpServletRequestBuilder creatingFormData(HttpMethod method, String endpoint){
        MockMultipartFile multipartFile = new MockMultipartFile("file", "image.jpg",
                "Image/jpg", "Spring Framework".getBytes());
        return   (MockMultipartHttpServletRequestBuilder) multipart(method, endpoint)
                .file(multipartFile)
                .param("name", simpleUser.getName())
                .param("birthDay", simpleUser.getBirthDay().toString())
                .param("cpf", simpleUser.getCpf())
                .param("phone", simpleUser.getPhone())
                .param("address.cep", simpleUser.getAddress().getCep())
                .param("address.state", simpleUser.getAddress().getState())
                .param("address.city", simpleUser.getAddress().getCity())
                .param("address.neighborhood", simpleUser.getAddress().getNeighborhood())
                .param("address.propertyLocation", simpleUser.getAddress().getPropertyLocation())
                .param("user.login", simpleUser.getUser().getLogin())
                .param("user.password", simpleUser.getUser().getPassword());
    }
}