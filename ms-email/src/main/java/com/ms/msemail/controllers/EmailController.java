package com.ms.msemail.controllers;

import com.ms.msemail.dtos.EmailDTO;
import com.ms.msemail.model.EmailModel;
import com.ms.msemail.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class EmailController {

    @Autowired
    private EmailService service;

    @PostMapping("/sending-email")
    public ResponseEntity<EmailModel> sendEmail(@RequestBody @Valid EmailDTO dto) {
        EmailModel entity = service.sendEmail(dto);
        return new ResponseEntity<>(entity, HttpStatus.CREATED);
    }
}
