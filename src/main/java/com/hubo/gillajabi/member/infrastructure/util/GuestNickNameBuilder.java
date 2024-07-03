package com.hubo.gillajabi.member.infrastructure.util;

import java.security.SecureRandom;

public class GuestNickNameBuilder {

    private static final String GUEST_DEFAULT = "guest_";
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int NICKNAME_LENGTH = 8;
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String buildNickName() {
        StringBuilder nickname = new StringBuilder(GUEST_DEFAULT);
        for (int i = 0; i < NICKNAME_LENGTH; i++) {
            int index = RANDOM.nextInt(CHARACTERS.length());
            nickname.append(CHARACTERS.charAt(index));
        }
        return nickname.toString();
    }
}
