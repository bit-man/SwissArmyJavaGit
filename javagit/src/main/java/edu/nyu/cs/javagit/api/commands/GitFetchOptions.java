package edu.nyu.cs.javagit.api.commands;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * git-fetch options management
 */
public class GitFetchOptions extends OptionsBase {


    public void setAll() {
        setOption(Option.ALL, Boolean.TRUE);
    }

    public boolean getAll() {
        return (Boolean) getOption(Option.ALL);
    }

    public void setAppend() {
        setOption(Option.APPEND, Boolean.TRUE);
    }

    public boolean getAppend() {
        return (Boolean) getOption(Option.APPEND);
    }

    public void setDepth(int depth) {
        setOption(Option.DEPTH, depth);
    }

    public int getDepth() {
        return (Integer) getOption(Option.DEPTH);
    }

    public void setUnshallow() {
        setOption(Option.UNSHALLOW, Boolean.TRUE);
    }

    public boolean getUnshallow() {
        return (Boolean) getOption(Option.UNSHALLOW);
    }

    public void setDryRun() {
        setOption(Option.DRY_RUN, Boolean.TRUE);
    }

    public boolean getDryRun() {
        return (Boolean) getOption(Option.DRY_RUN);
    }

    public void setForce() {
        setOption(Option.FORCE, Boolean.TRUE);
    }

    public boolean getForce() {
        return (Boolean) getOption(Option.FORCE);
    }

    public void setMultiple() {
        setOption(Option.MULTIPLE, Boolean.TRUE);
    }

    public boolean getMultiple() {
        return (Boolean) getOption(Option.MULTIPLE);
    }

    public void setPrune() {
        setOption(Option.PRUNE, Boolean.TRUE);
    }

    public boolean getPrune() {
        return (Boolean) getOption(Option.PRUNE);
    }

    public void setNoTags() {
        setOption(Option.NO_TAGS, Boolean.TRUE);
    }

    public boolean getNoTags() {
        return (Boolean) getOption(Option.NO_TAGS);
    }

    public void setTags() {
        setOption(Option.TAGS, Boolean.TRUE);
    }

    public boolean getTags() {
        return (Boolean) getOption(Option.TAGS);
    }

    public void setRecurseSubmodules(RecurseSubmodules value) {
        setOption(Option.RECURSE_SUBS, value);
    }

    public RecurseSubmodules getRecurseSubmodules() {
        return (RecurseSubmodules) getOption(Option.RECURSE_SUBS);
    }

    public void setNoRecurseSubmodules() {
        setOption(Option.NO_RECURSE_SUBS, Boolean.TRUE);
    }

    public boolean getNoRecurseSubmodules() {
        return (Boolean) getOption(Option.NO_RECURSE_SUBS);
    }

    public void setSubmodulePrefix(File path) {
        setOption(Option.SUB_MODULE_PREFIX, path);
    }

    public File getSubmodulePrefix() {
        return (File) getOption(Option.SUB_MODULE_PREFIX);
    }

    public void setRecurseSubmodulesDefault(RecurseSubmodulesDefault value) {
        setOption(Option.RECURSE_SUB_DEFAULT, value);
    }

    public RecurseSubmodulesDefault getRecurseSubmodulesDefault() {
        return (RecurseSubmodulesDefault) getOption(Option.RECURSE_SUB_DEFAULT);
    }

    public void setUpdateHeadOk() {
        setOption(Option.UPDATE_HEAD_OK, Boolean.TRUE);
    }

    public boolean getUpdateHeadOk() {
        return (Boolean) getOption(Option.UPDATE_HEAD_OK);
    }

    public void setUploadPack(File uploadPackPath) {
        setOption(Option.UPLOAD_PACK, uploadPackPath);
    }

    public File getUploadPack() {
        return (File) getOption(Option.UPLOAD_PACK);
    }

    public void setQuiet() {
        setOption(Option.QUIET, Boolean.TRUE);
    }

    public boolean getQuiet() {
        return (Boolean) getOption(Option.QUIET);
    }

    public void setVerbose() {
        setOption(Option.VERBOSE, Boolean.TRUE);
    }

    public boolean getVerbose() {
        return (Boolean) getOption(Option.VERBOSE);
    }

    public void setProgress() {
        setOption(Option.PROGRESS, Boolean.TRUE);
    }

    public boolean getProgress() {
        return (Boolean) getOption(Option.PROGRESS);
    }

    public List<String> getOptionArgs() {
        List<ICommandOption> avoid = new ArrayList<ICommandOption>();
        avoid.add(Option.ALL);
        avoid.add(Option.MULTIPLE);
        List<String> optionArgs = getOptionArgs(avoid);

        // Put special options before any switch
        if (getAll())
            optionArgs.add(0, Option.ALL.getOption( getAll() ).get(0) );

        if (getMultiple())
            optionArgs.add(0, Option.MULTIPLE.getOption( getMultiple() ).get(0) );

        return optionArgs;
    }

    public boolean isMultiple() {
        return getMultiple();
    }

    public enum RecurseSubmodules  {
        YES("yes"),
        ON_DEMAND("on-demand"),
        NO("no");

        private final String toString;

        RecurseSubmodules(String toString) {
            this.toString = toString;
        }

        public String toString() {
            return toString;
        }
    }

    public enum RecurseSubmodulesDefault {
        YES("yes"),
        ON_DEMAND("on-demand");


        private final String toString;

        RecurseSubmodulesDefault(String toString) {
            this.toString = toString;
        }

        public String toString() {
            return toString;
        }
    }

    private enum Option implements ICommandOption {
        ALL("--all", Option.HAS_EQUALS, Boolean.FALSE),
        APPEND("--append",  Option.HAS_EQUALS, Boolean.FALSE),
        DEPTH("--depth", Option.HAS_EQUALS, new Integer(0)),
        UNSHALLOW("--unshallow", Option.HAS_EQUALS, Boolean.FALSE),
        DRY_RUN("--dry-run", Option.HAS_EQUALS, Boolean.FALSE),
        FORCE("--force", Option.HAS_EQUALS, Boolean.FALSE),
        KEEP("--keep", Option.HAS_EQUALS, Boolean.FALSE),
        MULTIPLE("--multiple", Option.HAS_EQUALS, Boolean.FALSE),
        PRUNE("--prune", Option.HAS_EQUALS, Boolean.FALSE),
        NO_TAGS("--no-tags", Option.HAS_EQUALS, Boolean.FALSE),
        TAGS("--tags", Option.HAS_EQUALS, Boolean.FALSE),
        RECURSE_SUBS("--recurse-submodules", Option.HAS_EQUALS, RecurseSubmodules.YES),
        NO_RECURSE_SUBS("--no-recurse-submodules", Option.HAS_EQUALS,Boolean.TRUE),
        SUB_MODULE_PREFIX("--submodule-prefix", Option.HAS_EQUALS,new File("")),
        RECURSE_SUB_DEFAULT("--recurse-submodules-default", Option.HAS_EQUALS, RecurseSubmodulesDefault.YES),
        UPDATE_HEAD_OK("--update-head-ok", Option.HAS_EQUALS, Boolean.FALSE),
        UPLOAD_PACK("--upload-pack",! Option.HAS_EQUALS, new File("")),
        QUIET("--quiet", Option.HAS_EQUALS, Boolean.FALSE),
        VERBOSE("--verbose", Option.HAS_EQUALS, Boolean.FALSE),
        PROGRESS("--progress", Option.HAS_EQUALS, Boolean.FALSE);
        public static final String NO_OPTION_SET = "";

        public static final boolean HAS_EQUALS = true;
        private final String option;
        private final boolean hasEquals;


        private Object deflt;


        Option(String option, boolean hasEquals, Object deflt) {
            this.option = option;
            this.hasEquals = hasEquals;
            this.deflt = deflt;
        }

        public List<String> getOption(Object value) {

            List<String> ret = new ArrayList<String>();

            if (value instanceof Boolean)
                ret.add((Boolean) value ? option : NO_OPTION_SET);
            else {
                if (hasEquals)
                    ret.add(option + "=" + value.toString());
                else {
                    ret.add(option );
                    ret.add( value.toString());
                }
            }

            return ret;
        }

        public Object getDefault() {
            return deflt;
        }

    }
}
