package br.com.nevesHoteis.service.validation.People;

import br.com.nevesHoteis.domain.People;
import br.com.nevesHoteis.infra.exeption.ValidateUserException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class ValidateCpfPeople implements ValidatePeople{

    @Override
    public void validate(People people) {
        String cpf=  people.getCpf().replace(".","").replace("-", "");
        if(!(verificateTenthDigit(cpf) && verificateEleventhDigit(cpf)) || verificateCpfsInvalids(cpf)){
            throw new ValidateUserException("CPF", "Cpf inválido");
        }
    }

    private boolean verificateEleventhDigit(String cpf) {
        int sum=0;
        String[] cpfArray = cpf.split("");
        for (int index=11; index>1; index--){
            sum+= Integer.parseInt(cpfArray[11-index])*index;
        }
        int restResult = sum%11;
        int thenDigit = Integer.parseInt( cpfArray[10]);
        boolean b1= (restResult<2 && thenDigit==0);
        boolean b2= (restResult>=2 && thenDigit== (11-restResult));
        return b1 != b2;
    }

    private boolean verificateTenthDigit(String cpf){
      int sum=0;
      String[] cpfArray = cpf.split("");
      for (int index=10; index>1; index--){
          sum+= Integer.parseInt(cpfArray[10-index])*index;
      }
      int restResult = sum%11;
      int thenDigit = Integer.parseInt( cpfArray[9]);
        boolean b1= (restResult<2 && thenDigit==0);
        boolean b2= (restResult>=2 && thenDigit== (11-restResult));
        return b1 != b2;
    }

    private  boolean verificateCpfsInvalids(String cpf){
        String[] invalids ={
                "00000000000", "11111111111", "22222222222",
                "33333333333", "44444444444", "55555555555",
                "66666666666", "77777777777", "88888888888",
                "99999999999"};
        return List.of(invalids).contains(cpf);
    }


}
