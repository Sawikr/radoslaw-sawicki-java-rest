package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import com.crud.tasks.scheduler.service.EmailMessage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SimpleEmailServiceTest {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(SimpleEmailServiceTest.class);

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
        System.out.println("--------------------------");
    }

    @InjectMocks
    private SimpleEmailService simpleEmailService;

    @InjectMocks
    private EmailMessage emailMessage;

    @Mock
    private JavaMailSender javaMailSender;

    @Test
    public void shouldSendEmail() {
        //Given
        log.info("Starting test: shouldSendEmail");

        Mail mail = new Mail("sawikr10@gmail.com", "Test", "Test Message",
                "sawikr@op.pl");

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getMailTo());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getMessage());
        mailMessage.setCc(mail.getToCc());

        //When
        simpleEmailService.send(mail);

        //Then
        verify(javaMailSender, times(1)).send((MimeMessagePreparator) any());
    }

    @Test
    public void shouldSendEmailAndCheckTaskCounter() {
        //Given
        log.info("Starting test: shouldSendEmailAndCheckTaskCounter");

        long size = 2;
        Mail mail = new Mail("sawikr10@gmail.com", "Test",
                "Currently in database you got: " + size + " " + emailMessage.taskCounter(size),
                "sawikr@op.pl");

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getMailTo());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getMessage());
        mailMessage.setCc(mail.getToCc());

        //When
        simpleEmailService.send(mail);

        //Then
        verify(javaMailSender, times(1)).send((MimeMessagePreparator) any());
        assertEquals(2, size);
        assertEquals("tasks", emailMessage.taskCounter(size));
    }
}