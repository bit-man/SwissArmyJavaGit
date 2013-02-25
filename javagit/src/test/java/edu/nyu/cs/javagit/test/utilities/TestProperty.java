package edu.nyu.cs.javagit.test.utilities;

import java.io.File;

/**
 * Description : Properties to adapt test to different environments
 * Date: 2/24/13
 * Time: 9:06 PM
 */
public enum TestProperty {
    GIT_PATH("javagit.test.gitpath","/usr/bin");

    private final String value;
    private final String property;

    TestProperty(String property, String defaultValue) {
        this.property = property;
        this.value = System.getProperty(property, defaultValue);
    }

    public String asString() {
        return value;
    }

    public String getName() {
        return property;
    }
}
