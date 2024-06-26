package br.com.nevesHoteis.service;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendConfirmEmail(String recipient, String code) throws MessagingException, IOException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(recipient);
        helper.setSubject("Código de Confirmação de Email");
        String htmlContent = buildHtmlContent(code);
        helper.setText(htmlContent, true);
        ClassPathResource logoImageOriginal = new ClassPathResource("static/Logo_Original-Cortado.png");
        ClassPathResource logoImageMedia = new ClassPathResource("static/Logo_Original-Cortado-Tamanho-Medio.png");
        helper.addInline("logoImageOriginal", logoImageOriginal);
        helper.addInline("logoImageMedia", logoImageMedia);

        mailSender.send(message);
    }

    private String buildHtmlContent(String code) {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"pt-br\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Document</title>\n" +
                "    <style>\n" +
                "        body{\n" +
                "            height: 97vh;\n" +
                "            margin: 0px;\n" +
                "        }\n" +
                "        section{\n" +
                "            height: 100%;\n" +
                "            width: 100%;\n" +
                "         \n" +
                "        \n" +
                "        }\n" +
                "      \n" +
                "        .menssage{\n" +
                "            width: clamp(500px, 40%, 700px); ;\n" +
                "            background-color: #EEF5B2;\n" +
                "            text-align: center; \n" +
                "            border-top: 0px;\n" +
                "        }\n" +
                "        .codigo{\n" +
                "            background-color: #6DECB9;\n" +
                "            color: #EEF5B2;\n" +
                "            padding: 10px;\n" +
                "            font-size: 2rem;\n" +
                "            text-align: center;\n" +
                "            display:inline-block;\n" +
                "            font-weight: bold;\n" +
                "            }\n" +
                "            .container-codigo{\n" +
                "                width: 100%;\n" +
                "                text-align: center;\n" +
                "            }\n" +
                "        .title{\n" +
                "            text-align: center;\n" +
                "            font-size: 3rem;\n" +
                "          \n" +
                "        }\n" +
                "        .text-menssage{\n" +
                "            font-family: Georgia, 'Times New Roman', Times, serif;\n" +
                "            font-size: 1.2rem;\n" +
                "            text-align: justify;\n" +
                "            padding: 0  50px;\n" +
                "            align-items: center;\n" +
                "        }\n" +
                "        .imagem{\n" +
                "            border-bottom: 0px; \n" +
                "            height: 350px;    \n" +
                "            text-align: center;\n" +
                "        }\n" +
                "        .imagem2{\n" +
                "            margin-top: 150px;\n" +
                "            display: none;\n" +
                "        }\n" +
                "        table{\n" +
                "              width: 100%;\n" +
                "            align-items: center;\n" +
                "            text-align: center;\n" +
                "            display: flex;\n" +
                "            justify-content: center;\n" +
                "        }\n" +
                "        td{\n" +
                "            align-items: center;\n" +
                "            text-align: center;\n" +
                "            display: flex;\n" +
                "            justify-content: center;\n" +
                "        }\n" +
                "      \n" +
                "\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <section>\n" +
                "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">\n" +
                "            <tr> \n" +
                "                <td align=\"center\" valign=\"top\" style=\"padding: 10px 0;\">\n" +
                "                    <div class=\"imagem\"> \n" +
                "                        <img  class=\"imagem1\" src=\"cid:logoImageOriginal\" alt=\"Logo NevesHoteis\">\n" +
                "                        <img  class=\"imagem2\" src=\"cid:logoImageMedia\" alt=\"Logo NevesHoteis\">\n" +
                "                    </div>\n" +
                "                 </td>\n" +
                "            </tr>\n" +
                "        <tr>       \n" +
                "                        <td align=\"center\" valign=\"top\" style=\"background-color: #ffffff; padding: 20px; border-radius: 10px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\">\n" +
                "            <div class=\"menssage\">\n" +
                "            <h1 class=\"title\">Valide seu email</h1>\n" +
                "            <p class=\"text-menssage\">Ola, segue o seu código de segurança abaixo. \n O código de segurança expira em 10 minutos. \n Não compartilhe este código nem encaminhe este e-mail. Se você não fez essa solicitação, pode ignorar este e-mail. </p>\n" +
                "            <div class=\"container-codigo\"><p class=\"codigo\">"+code+"</p></div>\n" +
                "        </div>\n" +
                "        </td>\n" +
                "      </tr>\n" +
                " \n" +
                "    </table>\n" +
                "\n" +
                "</section>\n" +
                "</body>\n" +
                "</html>";
    }

}