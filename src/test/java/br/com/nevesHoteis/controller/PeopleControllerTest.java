package br.com.nevesHoteis.controller;

import br.com.nevesHoteis.domain.*;
import br.com.nevesHoteis.controller.dto.people.PeopleCompleteDto;
import br.com.nevesHoteis.controller.dto.people.PeopleDto;
import br.com.nevesHoteis.controller.dto.people.PeopleUpdateDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.data.domain.Page;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


public abstract class PeopleControllerTest<T extends People, S > extends BaseControllerTest<S>  {
    @Autowired
    protected JacksonTester<PeopleCompleteDto> completeDtoJacksonTester;
    @Autowired
    protected JacksonTester<PeopleUpdateDto> updateDtoJacksonTester;
    @Autowired
    protected JacksonTester<PeopleDto> dtoJacksonTester;
    @Autowired
    protected JacksonTester<Page<PeopleCompleteDto>> pageCompleteDtoJacksonTester;



}
