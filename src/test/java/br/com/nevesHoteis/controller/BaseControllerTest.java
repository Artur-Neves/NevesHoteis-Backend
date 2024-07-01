package br.com.nevesHoteis.controller;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureJsonTesters
@AutoConfigureMockMvc
public class BaseControllerTest <S>{
    @Autowired
    protected MockMvc mockMvc;
    @MockBean
    protected S service;
}
