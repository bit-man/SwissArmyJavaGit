package edu.nyu.cs.javagit.api.commands;

import edu.nyu.cs.javagit.TestBase;
import edu.nyu.cs.javagit.api.GitVersion;
import edu.nyu.cs.javagit.api.JavaGitException;
import org.junit.Test;

public class TestGitVersion extends TestBase {


    //--------------------------------------------- Constructor testing

    @Test
    public void testEmptyVersion() throws JavaGitException {
        try {
            new GitVersion("");
            fail("UNDETECTED wrong git version");
        }
        catch (JavaGitException e) {
            if (e.getCode() != 442001)
                throw  e;
        }
    }

    @Test
    public void testNonNumericMajor() throws JavaGitException {
        try {
            new GitVersion("AAAA");
            fail("UNDETECTED wrong git version");
        }
        catch (JavaGitException e) {
            if (e.getCode() != 442001)
                throw  e;
        }
    }

    @Test
    public void testNonNumericMinor() throws JavaGitException {
        try {
            new GitVersion("1.AAAA");
            fail("UNDETECTED wrong git version");
        }
        catch (JavaGitException e) {
            if (e.getCode() != 442002)
                throw  e;
        }
    }

    @Test
    public void testNonNumericReleaseMajor() throws JavaGitException {
        try {
            new GitVersion("1.1.AAAA");
            fail("UNDETECTED wrong git version");
        }
        catch (JavaGitException e) {
            if (e.getCode() != 442003)
                throw  e;
        }
    }

    @Test
    public void testNonNumericReleaseMajorWithTag() throws JavaGitException {
        try {
            new GitVersion("1.1.AAAA-BBB");
            fail("UNDETECTED wrong git version");
        }
        catch (JavaGitException e) {
            if (e.getCode() != 442003)
                throw  e;
        }
    }

    @Test
    public void testNonNumericReleaseMajorAndMinor() throws JavaGitException {
        try {
            new GitVersion("1.1.AAAA.333");
            fail("UNDETECTED wrong git version");
        }
        catch (JavaGitException e) {
            if (e.getCode() != 442003)
                throw  e;
        }
    }
    @Test
    public void testNonNumericReleaseMinor() throws JavaGitException {
        try {
            new GitVersion("1.1.1.AAAA");
            fail("UNDETECTED wrong git version");
        }
        catch (JavaGitException e) {
            if (e.getCode() != 442004)
                throw  e;
        }
    }

    //--------------------------------------------- Getters testing

    @Test
    public void testGetters() throws JavaGitException {
        GitVersion gitVersion = new GitVersion("1.2.9");

        assertEquals(1, gitVersion.getMajor());
        assertEquals(2, gitVersion.getMinor());
        assertEquals(9, gitVersion.getReleaseMajor());
        assertFalse( gitVersion.containsReleaseMinor() );
        assertFalse( gitVersion.containsTag() );
    }

    @Test
    public void testGettersReleaseMinor() throws JavaGitException {
        GitVersion gitVersion = new GitVersion("1.2.9.7");

        assertEquals(1, gitVersion.getMajor());
        assertEquals(2, gitVersion.getMinor());
        assertEquals(9, gitVersion.getReleaseMajor());
        assertEquals(7, gitVersion.getReleaseMinor());
        assertTrue(gitVersion.containsReleaseMinor());
        assertFalse( gitVersion.containsTag() );
    }

    @Test
    public void testGettersTag() throws JavaGitException {
        GitVersion gitVersion = new GitVersion("1.2.9-rc0");

        assertEquals(1, gitVersion.getMajor());
        assertEquals(2, gitVersion.getMinor());
        assertEquals(9, gitVersion.getReleaseMajor());
        assertFalse(gitVersion.containsReleaseMinor());
        assertTrue(gitVersion.containsTag());
        assertEquals("rc0", gitVersion.getTag());
    }


    //--------------------------------------------- Comparison testing

    @Test
    public void testSameVersion() throws JavaGitException {
        int comparison = new GitVersion("1.8.0").compareTo(new GitVersion("1.8.0"));
        assertEquals("Both are the same version, ", GitVersion.SAME, comparison);
    }

    @Test
    public void testSameVersionPlusTag() throws JavaGitException {
        int comparison = new GitVersion("1.8.0-rc0").compareTo(new GitVersion("1.8.0-rc0"));
        assertEquals("Both are the same version, ", GitVersion.SAME, comparison);
    }

    @Test
    public void testReleaseMinor0() throws JavaGitException {
        int comparison = new GitVersion("1.8.0.66").compareTo(new GitVersion("1.8.0.66"));
        assertEquals("Both are the same version, ", GitVersion.SAME, comparison);
    }
    @Test

    public void testReleaseMinor1() throws JavaGitException {
        int comparison = new GitVersion("1.8.0.66").compareTo(new GitVersion("1.8.0.67"));
        assertEquals("Both are the same version, ", GitVersion.PREVIOUS, comparison);
    }

    public void testReleaseMinor2() throws JavaGitException {
        int comparison = new GitVersion("1.8.0.67").compareTo(new GitVersion("1.8.0.66"));
        assertEquals("Both are the same version, ", GitVersion.LATER, comparison);
    }

    @Test
    public void testTagOnly1() throws JavaGitException {
        int comparison = new GitVersion("1.8.0").compareTo(new GitVersion("1.8.0-rc0"));
        assertEquals("1.8.0-rc0 is previous to 1.8.0", GitVersion.LATER, comparison);
    }

    @Test
    public void testTagOnly2() throws JavaGitException {
        int comparison = new GitVersion("1.8.0-rc0").compareTo(new GitVersion("1.8.0"));
        assertEquals("1.8.0-rc0 is previous to 1.8.0", GitVersion.PREVIOUS, comparison);
    }

    @Test
    public void testTagOnly3() throws JavaGitException {
        int comparison = new GitVersion("1.8.0-rc0").compareTo(new GitVersion("1.8.0-rc1"));
        assertEquals("1.8.0-rc0 is previous to 1.8.0-rc1", GitVersion.PREVIOUS, comparison);
    }

    @Test
    public void testTagOnly4() throws JavaGitException {
        int comparison = new GitVersion("1.8.0-rc1").compareTo(new GitVersion("1.8.0-rc2"));
        assertEquals("1.8.0-rc0 is previous to 1.8.0-rc1", GitVersion.PREVIOUS, comparison);
    }

    @Test
    public void testVersionOlderVsNewer() throws JavaGitException {
        int comparison = new GitVersion("1.6.8").compareTo(new GitVersion("1.8.0"));
        assertEquals("Both are the same version, ", GitVersion.PREVIOUS, comparison);
    }

    @Test
    public void testVersionNewerVsOlder() throws JavaGitException {
        int comparison = new GitVersion("1.8.0").compareTo(new GitVersion("1.1.0"));
        assertEquals("Both are the same version, ", GitVersion.LATER, comparison);
    }


    //--------------------------------------------- Equals testing


    @Test
    public void testEquals() throws JavaGitException {
        boolean comparison = new GitVersion("1.8.0-rc0").equals(new GitVersion("1.8.0-rc0"));
        assertTrue( comparison);
    }

    @Test
    public void testEqualsWithSpaces() throws JavaGitException {
        boolean comparison = new GitVersion("1.8.0-rc0").equals(new GitVersion("    1.8.0-rc0    "));
        assertTrue( comparison);
    }

    @Test
    public void testDifferent() throws JavaGitException {
        boolean comparison = new GitVersion("1.8.0-rc0").equals(new GitVersion("1.8.0-rc11"));
        assertFalse( comparison);
    }


}
