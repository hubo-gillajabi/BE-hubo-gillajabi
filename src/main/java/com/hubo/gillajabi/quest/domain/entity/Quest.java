package com.hubo.gillajabi.quest.domain.entity;

import com.hubo.gillajabi.global.BaseEntity;
import com.hubo.gillajabi.quest.domain.constant.QuestType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.Type;

import static jakarta.persistence.EnumType.STRING;

@Entity
@NoArgsConstructor
@Getter
@Builder
@Table(name = "quest")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SQLRestriction(value = "status != 'DELETED'")
public class Quest extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(value = STRING)
    private QuestType type;

    @Column(nullable = false, length = 40)
    private String name;

    @Column(nullable = false, length = 100)
    private String description;

    @Column(nullable = false, length = 500)
    private String imageUrl;

    @Column(columnDefinition = "json")
    private String details;
}
