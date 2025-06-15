package com.hrprogram.hrprogram.service;

import com.hrprogram.hrprogram.entity.AcceptMailtemplateEntity;
import com.hrprogram.hrprogram.entity.MailEntity;
import com.hrprogram.hrprogram.mapper.AcceptMailtemplateMapper;
import com.hrprogram.hrprogram.mapper.UserMapper;
import com.hrprogram.hrprogram.model.request.AcceptMailtemplateRequest;
import com.hrprogram.hrprogram.model.request.CvRequest;
import com.hrprogram.hrprogram.repository.AcceptMailtemplateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AcceptMailtemplateService {
    private final AcceptMailtemplateRepository repository;
    private final UserService userService;
    private final MailService mailService;

    public void saveRejectMailTemplate(Long hrId, String content, CvRequest cvRequest){
        log.info("Action.saveRejectMailTemplate.start for hr id {}", hrId);
        AcceptMailtemplateEntity acceptMailtemplate = new AcceptMailtemplateEntity();
        var user = userService.getUserResponseById(hrId);

        MailEntity mailEntity = new MailEntity();
        mailEntity.setMailTo(List.of(cvRequest.getEmail()));
        mailEntity.setMailFrom(user.getEmail());
        mailEntity.setText(content);

        acceptMailtemplate.setHrId(hrId);
        acceptMailtemplate.setContent(content);
        acceptMailtemplate.setCreateAt(LocalDateTime.now());
        acceptMailtemplate.setMailEntity(mailEntity);
        repository.save(acceptMailtemplate);

        log.info("Action.saveRejectMailTemplate.end for hr id {}", hrId);
    }


    public AcceptMailtemplateRequest getRejectMailTemplateByHrId(Long hrId){
        log.info("Action.getRejectMailTemplateByHrId.start for hr id {}", hrId);
        var mailEntity = repository.getAcceptMailtemplateEntityByHrId(hrId);

        if (mailEntity.getContent().isEmpty()){
            log.info("Action.getRejectMailTemplateByHrId.end for id {}", hrId);
            return null;
        }


        var mailRequset = AcceptMailtemplateMapper.INSTANCE.entityToRequest(mailEntity);
        log.info("Action.getRejectMailTemplateByHrId.end for id {}", hrId);
        return mailRequset;

    }

    public void sendRejectMail(Long hrId, CvRequest cvRequest){
        log.info("Action.sendRejectMail.start");
        var mailTemplate = getRejectMailTemplateByHrId(hrId);
        var mailRequest = mailTemplate.getMailRequest();
        var userResponse = userService.getUserResponseById(hrId);
        var userRequest = UserMapper.INSTANCE.responseToRequest(userResponse);
        mailService.sendMail(mailRequest, userRequest);
        log.info("Action.sendRejectMail.end");
    }


}
