package system.system_cinema.service;

import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

public interface IMailService {
    void sendEmailChangePassword(String recipients) throws MessagingException, UnsupportedEncodingException;

    void sendEmailOrderTicket(String recipients) throws MessagingException, UnsupportedEncodingException;
}
