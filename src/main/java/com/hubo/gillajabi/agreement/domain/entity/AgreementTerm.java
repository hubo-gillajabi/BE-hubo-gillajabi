package com.hubo.gillajabi.agreement.domain.entity;

import com.hubo.gillajabi.global.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLRestriction;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@SQLRestriction("status = 'ENABLE'")
public class AgreementTerm extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 100)
    private String termName;

    @Column(columnDefinition = "TEXT")
    private String termContent;
}
