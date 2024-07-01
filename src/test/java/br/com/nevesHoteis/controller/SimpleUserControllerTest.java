package br.com.nevesHoteis.controller;

import br.com.nevesHoteis.controller.Dto.SimpleUserDto;
import br.com.nevesHoteis.domain.*;
import br.com.nevesHoteis.controller.Dto.PeopleCompleteDto;
import br.com.nevesHoteis.controller.Dto.PeopleDto;
import br.com.nevesHoteis.controller.Dto.PeopleUpdateDto;
import br.com.nevesHoteis.service.SimpleUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        mockMvc.perform(post("/simple-user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoJacksonTester.write( new PeopleDto(simpleUser)).getJson()))
                .andExpectAll(status().isCreated(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json(completeDtoJacksonTester.write( new PeopleCompleteDto(simpleUser)).getJson()));
    }
    @Test
    @DisplayName("Testando a atualização de um usuário simples")
    void test03() throws Exception{

        when(service.update(anyLong(), any())).thenReturn(simpleUser);
        mockMvc.perform(put("/simple-user/"+simpleUser.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoJacksonTester.write(new PeopleDto(simpleUser)).getJson()))
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

}