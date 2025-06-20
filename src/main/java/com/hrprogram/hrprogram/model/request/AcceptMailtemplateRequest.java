package com.hrprogram.hrprogram.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AcceptMailtemplateRequest {

    private Long hrId;
    private List<String> emails;
    private String content;

    private MailRequest mailRequest;

}
