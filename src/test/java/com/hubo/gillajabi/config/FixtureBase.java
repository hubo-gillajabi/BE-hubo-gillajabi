package com.hubo.gillajabi.config;

import com.hubo.gillajabi.login.application.dto.response.TokenResponse;
import com.hubo.gillajabi.login.domain.constant.RoleStatus;
import com.hubo.gillajabi.login.domain.entity.MemberAuthentication;
import com.hubo.gillajabi.login.infrastructure.security.TokenProvider;
import com.hubo.gillajabi.member.domain.entity.Member;
import com.hubo.gillajabi.member.infrastructure.persistence.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;


/*
   repository 의존성을 넣는 abstract class 입니다
   ApplicationContext, TokenProvider를 함께 받습니다.
 */
@Profile("test")
@Configuration
@ComponentScan(basePackages = "com.hubo.gillajabi",
        useDefaultFilters = false,
        includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = FixtureBase.class))
public abstract class FixtureBase {

    @Autowired
    protected ApplicationContext context;

    @Autowired
    private TokenProvider tokenProvider;

    protected <T extends JpaRepository<?, ?>> T getRepository(Class<T> repositoryClass) {
        return context.getBean(repositoryClass);
    }

    public MemberAuthentication createAndLoadMember(String username, RoleStatus roleStatus) {
        MemberRepository memberRepository = getRepository(MemberRepository.class);

        Member member = Member.builder()
                .nickName(username)
                .profileImageUrl("profile.com")
                .lastLoginAt(LocalDateTime.now())
                .build();
        MemberAuthentication memberAuthentication = MemberAuthentication.builder()
                .member(member)
                .roleStatus(roleStatus)
                .build();

        memberRepository.save(member);

        return memberAuthentication;
    }

    public TokenResponse createAndLoadTokenResponse(MemberAuthentication memberAuthentication) {
        TokenProvider tokenProvider = context.getBean(TokenProvider.class);
        return tokenProvider.createToken(memberAuthentication);
    }
}