package edu.nyu.cs.javagit.api.commands;

import edu.nyu.cs.javagit.TestBase;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Git Fetch options testing
 * Created by bit-man on 12/21/13.
 */
public class TestGitFetchOptions
        extends TestBase {

    GitFetchOptions options = new GitFetchOptions();

    @Test
    public void testBooleanOption() {
        assertFalse(options.getAll());
        options.setAll();
        assertTrue(options.getAll());
    }

    @Test
    public void testDepth() {
        assertEquals(0, options.getDepth()) ;
        options.setDepth(3);
        assertEquals(3, options.getDepth()) ;
    }

    @Test
    public void testRecurseSubModules() {
        assertEquals(GitFetchOptions.RecurseSubmodules.YES, options.getRecurseSubmodules()) ;
        options.setRecurseSubmodules(GitFetchOptions.RecurseSubmodules.NO);
        assertEquals(GitFetchOptions.RecurseSubmodules.NO, options.getRecurseSubmodules()) ;
    }

    @Test
    public void testRecurseSubModulesDefault() {
        assertEquals(GitFetchOptions.RecurseSubmodulesDefault.YES, options.getRecurseSubmodulesDefault()) ;
        options.setRecurseSubmodulesDefault(GitFetchOptions.RecurseSubmodulesDefault.ON_DEMAND);
        assertEquals(GitFetchOptions.RecurseSubmodulesDefault.ON_DEMAND, options.getRecurseSubmodulesDefault()) ;
    }

    @Test
    public void testSubModulesPrefix() {
        File path = new File("/tmp");
        options.setSubmodulePrefix(path);
        assertEquals(path, options.getSubmodulePrefix()) ;
    }

    @Test
    public void testUploadPack() {
        File uploadPackPath = new File("/tmp");
        options.setUploadPack(uploadPackPath);
        assertEquals(uploadPackPath, options.getUploadPack()) ;
    }

    @Test
    public void testArgsNo() {
        List<String> noOptions = new ArrayList<String>();
        assertEquals(noOptions, options.getOptionArgs());
    }

    @Test
    public void testArgsSingleBoolean() {
        options.setAppend();

        List<String> options = new ArrayList<String>();
        options.add("--append");

        assertEquals(options, this.options.getOptionArgs());
    }

    @Test
    public void testArgsMultipleBoolean() {
        options.setAppend();
        options.setDryRun();
        options.setForce();

        Set<String> options = new HashSet<String>();
        options.add("--append");
        options.add("--dry-run");
        options.add("--force");

        assertEquals(options, new HashSet<String>(this.options.getOptionArgs()));
    }

    @Test
    public void testArgsSingleFile() {
        File uploadPackPath = new File("/tmp");
        options.setUploadPack(uploadPackPath);

        Set<String> options = new HashSet<String>();
        options.add("--upload-pack");
        options.add(uploadPackPath.toString());

        assertEquals(options, new HashSet<String>( this.options.getOptionArgs() ) );
    }

    @Test
    public void testArgsSingleWithEquals() {
        options.setRecurseSubmodules(GitFetchOptions.RecurseSubmodules.ON_DEMAND);

        Set<String> options = new HashSet<String>();
        options.add("--recurse-submodules=on-demand");

        assertEquals(options, new HashSet<String>( this.options.getOptionArgs() ) );
    }

    @Test
    public void testArgsSingleRecurseSubmodulesDefault() {
        options.setRecurseSubmodulesDefault(GitFetchOptions.RecurseSubmodulesDefault.ON_DEMAND);

        Set<String> options = new HashSet<String>();
        options.add("--recurse-submodules-default=on-demand");

        assertEquals(options, new HashSet<String>( this.options.getOptionArgs() ) );
    }


    @Test
    public void testArgsSingleInteger() {
        options.setDepth(3);

        Set<String> options = new HashSet<String>();
        options.add("--depth=3");
        assertEquals(options, new HashSet<String>( this.options.getOptionArgs() ) );
    }

    @Test
    public void testAllMustBeFirst() {
        options.setForce();
        options.setAll();
        options.setAppend();

        assertEquals( "--all", options.getOptionArgs().get(0));
    }

    @Test
    public void testMultipleMustBeFirst() {
        options.setForce();
        options.setAppend();
        options.setMultiple();

        assertEquals( "--multiple", options.getOptionArgs().get(0));
    }

}
