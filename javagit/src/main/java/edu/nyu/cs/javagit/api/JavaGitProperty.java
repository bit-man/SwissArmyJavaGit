package edu.nyu.cs.javagit.api;

import java.io.File;

/**
 * Description : JavaGit properties
 * Date: 5/19/13
 * Time: 12:56 AM
 */
public enum JavaGitProperty {
    LOG_COMMANDS_PATH("javagit.log.commands.path",
                       System.getProperty("java.io.tmpdir") + File.separator + "javagit.commands.log"),
    LOG_COMMANDS_DISABLE("javagit.log.commands.disable", "false"),
    LOG_COMMANDS_APPEND("javagit.log.commands.append", "true"),
    LOG_COMMANDS_FAIL_ON_INIT("javagit.log.commands.failoninit", "false");

    private final String propName;
    private final String propValue;

    JavaGitProperty(String propName, String defaultValue) {
        this.propName = propName;
        this.propValue = System.getProperty(this.propName, defaultValue);
    }

    public String getPropName() {
        return propName;
    }

    public String getPropValueString() {
        return propValue;
    }

    public boolean  getPropValueBoolean() {
        return Boolean.parseBoolean(propValue);
    }
}
