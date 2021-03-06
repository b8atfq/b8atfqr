package com.transfers.payments.Util;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PasswordTest {

    @Test
    void checkGeneratePassword() {
        String password = "password";
        String hexPassword = DigestUtils.md5Hex(password);
        for (int i = 0; i < 100; i++) {
            assertEquals(hexPassword, DigestUtils.md5Hex(password));
        }
    }
}