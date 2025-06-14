package com.hrprogram.hrprogram.service;

import com.hrprogram.hrprogram.entity.RejectMailTemplateEntity;
//import com.hrprogram.hrprogram.model.request.RejectCvRequest;
import com.hrprogram.hrprogram.model.request.RejectMailTemplateRequest;
import com.hrprogram.hrprogram.repository.RejectMailTemplateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RejectMailTemplateService {
    private final RejectMailTemplateRepository repository;

    public void saveRejectMailTemplate(Long hrId, String content){
        log.info("Action.saveRejectMailTemplate.start for hr id {}", hrId);
        RejectMailTemplateEntity rejectMailTemplate = new RejectMailTemplateEntity();

        rejectMailTemplate.setHrId(hrId);
        rejectMailTemplate.setContent(content);
        rejectMailTemplate.setCreateAt(LocalDateTime.now());
        repository.save(rejectMailTemplate);

        log.info("Action.saveRejectMailTemplate.end for hr id {}", hrId);
    }

    public String getRejectMailTemplateByHrId(Long hrId){
        log.info("Action.getRejectMailTemplateByHrId.start for hr id {}", hrId);
        List<RejectMailTemplateEntity> mails = repository.findAll();

        if (mails.isEmpty()){
            log.info("Action.getRejectMailTemplateByHrId.end for id {}", hrId);
            return "Default Mail";
        }
        log.info("Action.getRejectMailTemplateByHrId.end for id {}", hrId);
        return mails.get(0).getContent();
    }

}
