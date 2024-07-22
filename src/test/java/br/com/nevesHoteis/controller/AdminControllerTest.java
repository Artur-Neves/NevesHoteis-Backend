package br.com.nevesHoteis.controller;

import br.com.nevesHoteis.domain.Address;
import br.com.nevesHoteis.domain.Admin;
import br.com.nevesHoteis.controller.dto.people.PeopleCompleteDto;
import br.com.nevesHoteis.controller.dto.people.PeopleDto;
import br.com.nevesHoteis.controller.dto.people.PeopleUpdateDto;
import br.com.nevesHoteis.domain.Role;
import br.com.nevesHoteis.domain.User;
import br.com.nevesHoteis.service.AdminService;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.conversions.FormattedDateConversion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WithMockUser(authorities = "ADMIN")
public class AdminControllerTest extends PeopleControllerTest<Admin, AdminService> {
    private Admin admin = new Admin();
    private Address address = new Address();
    private User user = new User();

    @BeforeEach
    void setUp() {
        user = new User(1L, "artur@gmail.com", true , "Ar606060", Role.ADMIN, null);
        address = new Address( "76854-245", "BA", "Jequié", "Beira rio", "Rua Portugual");
        admin = new Admin(1L, "Artur", LocalDate.now().plusYears(-18), "123.456.890-90", "73988888888", address, user);
    }
    @Test
    @DisplayName("Testando o selecionamento de todos os admin")
    void test01() throws Exception {
        Page<Admin> page = new PageImpl<>(List.of(admin));
        when(service.findAll(any())).thenReturn(page);
        mockMvc.perform(get("/admin"))
                .andExpectAll(status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json(pageCompleteDtoJacksonTester.write(page.map(PeopleCompleteDto::new)).getJson()));
    }


    @Test
    @DisplayName("Testando o salvamento de um admin")
    void test02() throws Exception {
        when(service.save(any())).thenReturn(admin);
        mockMvc.perform(creatingFormData(HttpMethod.POST,"/admin"))
                .andExpectAll(status().isCreated(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json(completeDtoJacksonTester.write(new PeopleCompleteDto(admin)).getJson()));

    }

    @Test
    @DisplayName("Testando a atualização de um admin")
    void test03() throws Exception {
        when(service.update(any(), any())).thenReturn(admin);
        mockMvc.perform(creatingFormData(HttpMethod.PUT,"/admin/" + admin.getId()))
                .andExpectAll(status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json(updateDtoJacksonTester.write(new PeopleUpdateDto(admin)).getJson()));
    }

    @Test
    @DisplayName("Testando o selecionamento de um admin pelo id")
    void test04() throws Exception {
        when(service.findById(anyLong())).thenReturn(admin);
        mockMvc.perform(
                        get("/admin/" + admin.getId()))
                .andExpectAll(status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json(completeDtoJacksonTester.write(new PeopleCompleteDto(admin)).getJson()));
    }

    @Test
    @DisplayName("Testando a exclusão de um admin")
    void test05() throws Exception {
        mockMvc.perform(
                        delete("/admin/" + 1))
                .andExpectAll(status().isNoContent());
        then(service).should().delete(anyLong());
    }

    @Test
    @DisplayName("Retornando erro ao testar uma entidade que não existe ")
    void test06() throws Exception {
        mockMvc.perform(get("/hotel/"))
                .andExpectAll(status().isNotFound());
    }
    MockMultipartHttpServletRequestBuilder creatingFormData(HttpMethod method, String endpoint){
        MockMultipartFile multipartFile = new MockMultipartFile("file", "image.jpg",
                "Image/jpg", "Spring Framework".getBytes());
        return   (MockMultipartHttpServletRequestBuilder) multipart(method, endpoint)
                .file(multipartFile)
                .param("name", admin.getName())
                .param("birthDay", admin.getBirthDay().toString())
                .param("cpf", admin.getCpf())
                .param("phone", admin.getPhone())
                .param("address.cep", admin.getAddress().getCep())
                .param("address.state", admin.getAddress().getState())
                .param("address.city", admin.getAddress().getCity())
                .param("address.neighborhood", admin.getAddress().getNeighborhood())
                .param("address.propertyLocation", admin.getAddress().getPropertyLocation())
                .param("user.login", admin.getUser().getLogin())
                .param("user.password", admin.getUser().getPassword());
    }

}
