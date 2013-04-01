package edu.nyu.cs.javagit.utilities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JavaHelper {
    public static <T> List<T> copyIterator(Iterable<T> iterable) {
        Iterator<T> iterator = iterable.iterator();
        List<T> copy = new ArrayList<T>();
        while (iterator.hasNext())
            copy.add(iterator.next());
        return copy;
    }
}
