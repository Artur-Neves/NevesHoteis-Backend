package br.com.nevesHoteis.infra.utils;

import br.com.nevesHoteis.infra.exeption.ConvertException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class Conversions {
    public static byte[] convertMultiPartFileInByte(MultipartFile multipartFile) {
        if (multipartFile != null) {
            try {
                return multipartFile.getBytes();
            } catch (IOException e) {
                throw new ConvertException("MultipartFile in Byte[]");
            }
        }
        return null;
    }
    public static List<byte[]> convertListMultiPartFileInListByte(List<MultipartFile> multipartFile) {
        if (multipartFile != null) {
                return multipartFile.stream().map(m-> {
                    try {
                        return m.getBytes();
                    } catch (IOException e) {
                        throw new ConvertException("List<MultipartFile> in List<Byte[]>");
                    }
                }).toList();
            }
        return null;

    }
    public static LocalDateTime convertStringInLocalDateTime(String local)   {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        try {
            return LocalDateTime.parse(local, formatter);
        }
        catch (NullPointerException DateTimeParseException){
            throw new ConvertException("String in LocalDateTime");
        }

    }
}
