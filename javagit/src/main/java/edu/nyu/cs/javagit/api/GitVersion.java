package edu.nyu.cs.javagit.api;

import edu.nyu.cs.javagit.api.commands.CommandResponse;
import edu.nyu.cs.javagit.utilities.ExceptionMessageMap;

/**
 * Git version is composed by major.minor.releaseMajor{"." + releaseMinor|"-" + tag}  meaning that
 * release minor and tag are optional and mutually excluded.
 * <br/>
 * These rules are valid for Git v1.1.0 onwards. This object is not intended to manage Git versions previous to this one
 * <p/>
 * Valid values are : 1.7.0, 1.8.0.1 and 1.8.2-rc4 among others
 */
public class GitVersion implements CommandResponse, Comparable<GitVersion> {

    public static final int LATER = +1;
    public static final int PREVIOUS = -1;
    public static final int SAME = 0;

    private String tag;
    private Integer releaseMinor = null;
    private int releaseMajor;
    private int major;
    private int minor;

    // The version of git that gets passed into our constructor.
    private String version;

    /**
     * Constructor.
     *
     * @param version The version of git being used.
     */
    public GitVersion(String version) throws JavaGitException {
        this.version = version.trim();

        String[] versionNumber = this.version.split("\\.", 4);
        if (versionNumber.length > 0) {
            parseMajor(versionNumber[0]);
            if (versionNumber.length > 1) {
                parseMinor(versionNumber[1]);
                if (versionNumber.length > 2) {
                    String[] relDashSplit = versionNumber[2].split("-", 2);
                    if (relDashSplit.length == 2) {
                        parseMajorRelease(relDashSplit[0]);
                        this.tag = relDashSplit[1];
                    } else if (versionNumber.length == 4) {
                        parseMajorRelease(versionNumber[2]);
                        parseMinorRelease(versionNumber[3]);
                    } else {
                        parseMajorRelease(versionNumber[2]);
                    }
                } else {
                    throw new JavaGitException(442003, ExceptionMessageMap.getMessage("442003"));
                }

            } else {
                throw new JavaGitException(442002, ExceptionMessageMap.getMessage("442002"));
            }
        } else {
            throw new JavaGitException(442001, ExceptionMessageMap.getMessage("442001"));
        }
    }

    private void parseMajor(String s) throws JavaGitException {
        try {
            this.major = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            throw new JavaGitException(442001, ExceptionMessageMap.getMessage("442001"));
        }
    }

    private void parseMinor(String s) throws JavaGitException {
        try {
            this.minor = Integer.parseInt(s);
        } catch (NumberFormatException e) {
            throw new JavaGitException(442002, ExceptionMessageMap.getMessage("442002"));
        }
    }

    private void parseMinorRelease(String s) throws JavaGitException {
        try {
            this.releaseMinor = Integer.parseInt(s);

        } catch (NumberFormatException e) {
            throw new JavaGitException(442004, ExceptionMessageMap.getMessage("442004"));
        }
    }

    private void parseMajorRelease(String s) throws JavaGitException {
        try {
            this.releaseMajor = Integer.parseInt(s);

        } catch (NumberFormatException e) {
            throw new JavaGitException(442003, ExceptionMessageMap.getMessage("442003"));
        }
    }

    /**
     * Gets the version of git being used in <code>String</code> format.
     *
     * @return The git version string.
     */
    @Override
    public String toString() {
        return version;
    }

    public int getMinor() {
        return minor;
    }

    public int getMajor() {
        return major;
    }

    public int getReleaseMajor() {
        return releaseMajor;
    }

    public String getTag() {
        return tag;
    }

    public int getReleaseMinor() {
        return releaseMinor;
    }

    public boolean containsReleaseMinor() {
        return releaseMinor != null;
    }

    public boolean containsTag() {
        return getTag() != null;
    }

    public boolean equals(Object that) {
        if (!(that instanceof GitVersion))
            return super.equals(that);

        return( this.version.equals( ((GitVersion)that).version) );
    }

    public int compareTo(GitVersion that) {

        if (this.getMajor() > that.getMajor()) {
            return LATER;
        } else if (this.getMajor() < that.getMajor()) {
            return PREVIOUS;
        } else {
            if (this.getMinor() > that.getMinor()) {
                return LATER;
            } else if (this.getMinor() < that.getMinor()) {
                return PREVIOUS;
            } else {
                if (this.getReleaseMajor() > that.getReleaseMajor()) {
                    return LATER;
                } else if (this.getReleaseMajor() < that.getReleaseMajor()) {
                    return PREVIOUS;
                } else {
                    return compareToReleaseMinorAndTag(that);
                }
            }
        }
    }

    private int compareToReleaseMinorAndTag(GitVersion that) {
        if (!this.containsReleaseMinor() && !that.containsReleaseMinor())
            return compareToTag(that);
        else if (!this.containsTag() && !that.containsTag())
            return compareToReleaseMinor(that);
        else if (this.containsReleaseMinor() && !that.containsTag())
            return LATER;
        else
            return PREVIOUS;
    }

    /**
     * Compares minor releases values given that git version contains no tag
     * for none of GitVersion objects
     *
     * @param that
     * @return
     */
    private int compareToReleaseMinor(GitVersion that) {
        if (this.containsReleaseMinor() && that.containsReleaseMinor())
            return compareToInt(this.getReleaseMinor(), that.getReleaseMinor());
        else if (!this.containsReleaseMinor() && !that.containsReleaseMinor())
            return SAME;
        else if (this.containsReleaseMinor() && !that.containsReleaseMinor())
            return LATER;
        else
            return PREVIOUS;
    }

    /**
     * Compares tag values given that git version contains no minor release
     * for none of GitVersion objects
     * <p/>
     * When both contain a tag or not contain  it the comparison is easy but
     * if only one of them contains it the release that doesn't contains it
     * is the one released earlier
     * <p/>
     * e.g. v1.8.0-rc0 is previous to v1.8.0
     *
     * @param that
     * @return
     */
    private int compareToTag(GitVersion that) {
        if (this.containsTag() && that.containsTag())
            return this.getTag().compareTo(that.getTag());
        else if (!this.containsTag() && !that.containsTag())
            return SAME;
        else if (this.containsTag() && !that.containsTag())
            return PREVIOUS;
        else
            return LATER;
    }

    private int compareToInt(int iThis, int iThat) {
        if (iThis > iThat)
            return LATER;
        else if (iThis < iThat)
            return PREVIOUS;
        else return SAME;

    }
}
