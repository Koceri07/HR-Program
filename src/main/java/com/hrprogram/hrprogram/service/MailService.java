package com.hrprogram.hrprogram.service;

import com.hrprogram.hrprogram.mapper.MailMapper;
import com.hrprogram.hrprogram.model.request.MailRequest;
import com.hrprogram.hrprogram.model.request.UserRequest;
import com.hrprogram.hrprogram.repository.MailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailService {
    private final MailRepository mailRepository;
    private final JavaMailSender javaMailSender;


    public void sendMail(MailRequest mailRequest, UserRequest userRequest){
        log.info("Action.sendMail.start for id {}", mailRequest.getId());
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setFrom(userRequest.getEmail());
        simpleMailMessage.setTo(mailRequest.getMailTo().toArray(new String[0]));
        simpleMailMessage.setSubject(mailRequest.getSubject());
        simpleMailMessage.setText(mailRequest.getText());

        javaMailSender.send(simpleMailMessage);
        log.info("Action.sendMail.end for id {}", mailRequest.getId());
    }



    public void createMail(MailRequest mailRequest, UserRequest userRequest){
        log.info("Action.createMail.start for id {}", mailRequest.getId());

        sendMail(mailRequest, userRequest);
        var mailEntity = MailMapper.INSTANCE.toEntity(mailRequest);
        mailRepository.save(mailEntity);

        log.info("Action.createMail.end for id {}", mailRequest.getId());
    }
}
