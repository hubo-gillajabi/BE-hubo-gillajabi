package com.hubo.gillajabi.agreement.domain.entity;

import com.hubo.gillajabi.member.domain.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"member_id", "agreement_term_id"})
})
public class MemberAgreement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agreement_term_id")
    private AgreementTerm agreementTerm;

    @Column(nullable = false)
    private Boolean isAgreed;

    @Column
    private LocalDateTime modifiedTime;

    public static MemberAgreement create(Member user, AgreementTerm term) {
        return new MemberAgreement(null, user, term, false, null);
    }

    public void updateAgreement(boolean isAgreed) {
        this.isAgreed = isAgreed;
        this.modifiedTime = LocalDateTime.now();
    }
}
