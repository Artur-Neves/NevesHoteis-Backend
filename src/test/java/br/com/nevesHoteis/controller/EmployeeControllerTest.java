package br.com.nevesHoteis.controller;

import br.com.nevesHoteis.domain.*;
import br.com.nevesHoteis.controller.dto.people.PeopleCompleteDto;
import br.com.nevesHoteis.controller.dto.people.PeopleDto;
import br.com.nevesHoteis.controller.dto.people.PeopleUpdateDto;
import br.com.nevesHoteis.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WithMockUser(authorities = "ADMIN")
public class EmployeeControllerTest extends PeopleControllerTest<Employee, EmployeeService>{
    private Employee employee = new Employee();
    private Address address = new Address();
    private User user = new User();
    @BeforeEach
    void setUp() {
        user = new User(1L, "artur@gmail.com", true ,"Ar606060", Role.EMPLOYEE, null);
        address = new Address( "76854-245", "BA", "Jequié", "Beira rio", "Rua Portugual");
        employee = new Employee(1L, "Artur", LocalDate.now().plusYears(-18), "123.456.890-90", "73988888888", address, user);
    }
    @Test
    @DisplayName("Testando o selecionamento de todos os funcionários")
    void test01() throws Exception {
        Page<Employee> page = new PageImpl<>(List.of(employee));
        when(service.findAll(any())).thenReturn(page);
        mockMvc.perform(get("/employee"))
                .andExpectAll(status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json(pageCompleteDtoJacksonTester.write(page.map(PeopleCompleteDto::new)).getJson()));
    }

    @Test
    @DisplayName("Testando o salvamento de um funcionário")
    void test02() throws Exception {
        when(service.save(any())).thenReturn(employee);
        mockMvc.perform(creatingFormData(HttpMethod.POST, "/employee"))
                .andExpectAll(status().isCreated(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json(completeDtoJacksonTester.write( new PeopleCompleteDto(employee)).getJson()));
    }
    @Test
    @DisplayName("Testando a atualização de um funcionário")
    void test03() throws Exception{
        when(service.update(any(), any())).thenReturn(employee);
        mockMvc.perform(creatingFormData(HttpMethod.PUT, "/employee/"+employee.getId()))
                .andExpectAll(status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json(updateDtoJacksonTester.write( new PeopleUpdateDto(employee)).getJson()));
    }

    @Test
    @DisplayName("Testando o selecionamento de um funcionário pelo id")
    void test04() throws Exception{
        when(service.findById(anyLong())).thenReturn(employee);
        mockMvc.perform(
                        get("/employee/"+employee.getId()))
                .andExpectAll(status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json(completeDtoJacksonTester.write(new PeopleCompleteDto(employee)).getJson()));
    }
    @Test
    @DisplayName("Testando a exclusão de um funcionário")
    void test05() throws Exception{
        mockMvc.perform(
                        delete("/employee/"+1))
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
                .param("name", employee.getName())
                .param("birthDay", employee.getBirthDay().toString())
                .param("cpf", employee.getCpf())
                .param("phone", employee.getPhone())
                .param("address.cep", employee.getAddress().getCep())
                .param("address.state", employee.getAddress().getState())
                .param("address.city", employee.getAddress().getCity())
                .param("address.neighborhood", employee.getAddress().getNeighborhood())
                .param("address.propertyLocation", employee.getAddress().getPropertyLocation())
                .param("user.login", employee.getUser().getLogin())
                .param("user.password", employee.getUser().getPassword());
    }



}
