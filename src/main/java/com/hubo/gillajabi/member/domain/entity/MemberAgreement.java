package com.hubo.gillajabi.member.domain.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table
public class MemberAgreement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinTable(name = "member_id")
    private Member member;

    @Column(nullable = false, length = 50)
    private String consentType;

    @Column(nullable = false)
    private Boolean isAgree;

    @Column
    private LocalDateTime agreedAt;
}
