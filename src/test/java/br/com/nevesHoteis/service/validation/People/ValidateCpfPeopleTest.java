package br.com.nevesHoteis.service.validation.People;

import br.com.nevesHoteis.domain.Address;
import br.com.nevesHoteis.domain.People;
import br.com.nevesHoteis.domain.SimpleUser;
import br.com.nevesHoteis.domain.User;
import br.com.nevesHoteis.infra.exeption.ValidateUserException;
import br.com.nevesHoteis.service.validation.People.ValidateCpfPeople;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class ValidateCpfPeopleTest {
    @InjectMocks
    private ValidateCpfPeople validate;

    @Test
    public void testValidCpfs() {
        People people = new People();
        String[] validCpfs = {
                "521.869.610-24", "743.787.010-06", "980.429.060-05", "631.508.180-01",
                "473.727.980-35", "491.448.800-06", "356.090.680-61", "215.013.690-24",
                "913.026.540-12", "549.136.100-31", "086.526.470-89", "544.201.290-42",
                "866.392.030-06", "700.418.780-27", "062.152.860-95", "295.057.520-08",
                "723.594.400-55", "330.411.460-29", "878.840.510-95", "971.111.120-91",
                "382.383.220-49", "347.716.990-54", "506.494.350-44", "203.994.650-06",
                "491.926.530-14", "886.241.130-83", "515.585.120-39", "727.760.900-05",
                "581.878.270-09", "040.584.260-04", "305.619.600-03",
                "868.087.270-90", "705.272.160-03", "538.999.560-01", "143.489.110-02",
                "746.044.420-02", "171.780.990-11", "886.822.700-21", "690.116.520-02",
        };

        for (String cpf : validCpfs) {
            people.setCpf(cpf);
            assertDoesNotThrow(() -> validate.validate(people));
        }
    }

    @Test
    public void testInvalidCpfs() {
        People people = new People();
        String[] invalidCpfs = {
                "123.456.789-00", "111.111.111-11", "222.222.222-22", "333.333.333-33",
                "444.444.444-44", "555.555.555-55", "666.666.666-66", "777.777.777-77",
                "888.888.888-88", "999.999.999-99", "000.000.000-00", "123.456.789-01",
                "234.567.890-12", "345.678.901-23", "456.789.012-34", "567.890.123-45",
                "678.901.234-56", "789.012.345-67", "890.123.456-78", "901.234.567-89",
                "913.026.540-02", "549.136.100-91", "086.526.470-80", "544.201.290-59",
                "866.392.030-00", "700.418.780-97", "062.152.860-98", "295.057.520-01",
                "723.594.400-89", "330.411.460-20", "878.840.510-99", "971.111.120-99",
                "521.869.610-14", "743.787.010-96", "980.429.060-25", "631.508.180-51",
                "473.727.980-38", "491.448.800-01", "356.090.680-11", "215.013.690-22",
                "158.759.650-49", "347.720.620-00", "818.238.300-50", "444.955.160-59",
                "472.221.370-70", "268.123.470-90", "487.947.890-01", "036.797.520-39",
                "719.842.495-19", "824.359.160-01", "472.943.210-90", "823.999.810-87",
                "995.188.530-17", "345.813.227-14", "172.277.440-10", "872.626.210-08",
                "468.372.376-17", "953.525.750-10", "478.408.770-99", "349.930.150-00"

        };

        for (String cpf : invalidCpfs) {
            people.setCpf(cpf);
            assertThrows(ValidateUserException.class, () -> validate.validate(people));
        }
    }
}