package com.thinkitive.cruddemo.shared.utils;

import org.apache.commons.lang3.RandomStringUtils;

public class RandomIdUtils {

    public static String getId(Integer length) {
        return RandomStringUtils.random(length, true, true);
    }

    public static String getId() {
        return RandomStringUtils.random(10, true, true);
    }

}
