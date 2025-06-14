package com.hrprogram.hrprogram.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RejectMailTemplateRequest {

    private LocalDateTime hrId;

    private List<String> emails;

}
