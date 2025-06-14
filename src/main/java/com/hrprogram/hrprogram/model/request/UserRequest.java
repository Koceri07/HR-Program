package com.hrprogram.hrprogram.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDateTime birthDate;
    private String email;
    private boolean isActive;

}
