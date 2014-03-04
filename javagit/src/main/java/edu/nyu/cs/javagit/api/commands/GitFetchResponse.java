package edu.nyu.cs.javagit.api.commands;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Response for git-fetch command
 */
public class GitFetchResponse implements CommandResponse {

    private boolean noUpdate = true;

    private int objsTrnsf = 0;

    private Map<String, Set<String>> sourceNews = new HashMap<String, Set<String>>();

    public boolean isNoUpdate() {
        return noUpdate;
    }

    public int getObjectsTransfered() {
        return objsTrnsf;
    }

    public void setObjectsTransfered(int i) {
        objsTrnsf = i;
        noUpdate = false;
    }

    public void addSource(String source) {
        sourceNews.put(source, new HashSet<String>());
    }

    public void addNews(String source, String news) {
        sourceNews.get(source).add(news);
    }

    public Map<String, Set<String>> getSourceNews() {
        return sourceNews;
    }
}
