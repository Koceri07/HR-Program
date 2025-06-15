package com.hrprogram.hrprogram.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "mails")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mailFrom;

    @ElementCollection
    private List<String> mailTo;

    private String subject;
    private String text;

    @OneToOne
    private RejectMailTemplateEntity rejectMailTemplateEntity;

    @CreationTimestamp
    private LocalDateTime sentAt;
}
