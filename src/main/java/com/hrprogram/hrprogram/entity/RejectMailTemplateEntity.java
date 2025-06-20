package com.hrprogram.hrprogram.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "reject_mails")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RejectMailTemplateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long hrId;
    private String content;

    private String mailFrom;
    private String mailTo;

    @OneToOne
    private MailEntity mailEntity;

    @CreationTimestamp
    private LocalDateTime createAt;

}
