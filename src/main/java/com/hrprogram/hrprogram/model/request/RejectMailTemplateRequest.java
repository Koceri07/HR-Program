package com.hrprogram.hrprogram.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RejectMailTemplateRequest{

    private Long hrId;
    private List<String> emails;
    private String content;

    private MailRequest mailRequest;

}
