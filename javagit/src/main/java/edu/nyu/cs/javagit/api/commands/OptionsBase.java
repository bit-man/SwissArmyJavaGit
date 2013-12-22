package edu.nyu.cs.javagit.api.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Base class for command options processing
 */
public abstract class OptionsBase {

    protected HashMap<ICommandOption, Object> options = new HashMap<ICommandOption, Object>();

    protected <T> T getOption(ICommandOption option) {
        if ( options.containsKey(option))
            return (T) options.get(option);
        else
            return (T) option.getDefault();
    }

    protected void setOption(ICommandOption option, Object value) {
        if ( options.containsKey(option)) {
            Object v = options.get(option);
            v = value;
        } else
            options.put(option, value);
    }

    public List<String> getOptionArgs() {
        List<ICommandOption> avoid = new ArrayList<ICommandOption>();
        return  getOptionArgs(avoid);
    }

    protected List<String> getOptionArgs(List<ICommandOption> avoid) {
        List<String> ret = new ArrayList<String>();
        for (ICommandOption opt : options.keySet())
            if ( ! avoid.contains(opt))
                for ( String o : opt.getOption( options.get(opt) ))
                    ret.add(o);

        return  ret;
    }
}
