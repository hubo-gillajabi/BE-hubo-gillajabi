package com.hubo.gillajabi.member.domain.util;

import com.hubo.gillajabi.member.infrastructure.util.GuestNickNameBuilderHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GuestNickNameBuilderHelperTest {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    @Test
    @DisplayName("게스트 닉네임 생성 테스트, guest_랜덤 문자")
    public void testBuildGuestNickName() {
        String nickname = GuestNickNameBuilderHelper.buildNickName();

        // 게스트 닉네임은 guest_로 시작해야 한다
        assertTrue(nickname.startsWith("guest_"), "Nickname should start with 'guest_'");

        // 게스트 닉네임은 14자리여야 한다
        assertEquals(14, nickname.length(), "Nickname should be 14 characters long");

        // guest_를 제외한 나머지 문자가 지정된 캐릭터 세트에 속하는지 확인
        String randomPart = nickname.substring(6);
        for (char c : randomPart.toCharArray()) {
            assertTrue(CHARACTERS.contains(String.valueOf(c)), "Character not in expected set: " + c);
        }
    }
}
