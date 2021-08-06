package com.ms.msemail.services;

import com.ms.msemail.dtos.EmailDTO;
import com.ms.msemail.enums.StatusEmail;
import com.ms.msemail.model.EmailModel;
import com.ms.msemail.repositories.EmailRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class EmailService {

    @Autowired
    private EmailRepository repository;

    @Autowired
    private JavaMailSender emailSender;

    @Transactional
    public EmailModel sendEmail(EmailDTO dto) {
        EmailModel entity = new EmailModel();
        BeanUtils.copyProperties(dto, entity);
        entity.setSendDateEmail(LocalDateTime.now());
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(entity.getEmailFrom());
            message.setTo(entity.getEmailTo());
            message.setSubject(entity.getSubject());
            message.setText(entity.getText());
            emailSender.send(message);

            entity.setStatusEmail(StatusEmail.SENT);
        } catch (MailException e) {
            entity.setStatusEmail(StatusEmail.ERROR);
        } finally {
            return repository.save(entity);
        }
    }
}
