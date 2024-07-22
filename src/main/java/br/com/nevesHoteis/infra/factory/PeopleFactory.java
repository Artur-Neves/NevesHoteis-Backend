package br.com.nevesHoteis.infra.factory;

import br.com.nevesHoteis.domain.Admin;
import br.com.nevesHoteis.controller.dto.people.PeopleDto;
import br.com.nevesHoteis.controller.dto.people.PeopleUpdateDto;
import br.com.nevesHoteis.domain.Employee;
import br.com.nevesHoteis.domain.People;
import br.com.nevesHoteis.domain.SimpleUser;

public class PeopleFactory {
    public static <T> People createdNewPeople(Class<T> a){
        if (a== SimpleUser.class){
            return new SimpleUser();

        } else if (a== Employee.class) {
            return new Employee();
        }
        else if (a== Admin.class){
            return new Admin();
        }
        else{
            throw new RuntimeException("Type Invalid");
        }
    }
    public static <T> People createdNewPeople(T a, PeopleDto peopleDto){

        if (a== SimpleUser.class){
            return new SimpleUser(peopleDto);

        } else if (a== Employee.class) {
            return new Employee(peopleDto);
        }
        else if (a== Admin.class){
            return new Admin(peopleDto);
        }
        else{
            throw new RuntimeException("Type Invalid");
        }
    }
    public static <T> People createdNewPeople(Class<T> a, PeopleUpdateDto peopleUpdateDto){
        if (a== SimpleUser.class){
            return new SimpleUser(peopleUpdateDto);

        } else if (a== Employee.class) {
            return new Employee(peopleUpdateDto);
        }
        else if (a== Admin.class){
            return new Admin(peopleUpdateDto);
        }
        else{
            throw new RuntimeException(""+a);
        }
    }
}
