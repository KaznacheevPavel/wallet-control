package ru.kaznacheev.walletControl.service;

public interface MailSenderService {
    void send(String to, String subject, String text);
}
