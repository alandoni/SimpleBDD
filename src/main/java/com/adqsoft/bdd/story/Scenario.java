package com.adqsoft.bdd.story;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scenario {

    private String name;
    private Map<String, String> meta = new HashMap<String, String>();
    private List<Step> steps = new ArrayList<Step>();
    private List<String> comments = new ArrayList<String>();
    private int timesToRepeatForTable;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getMeta() {
        return meta;
    }

    public void setMeta(Map<String, String> meta) {
        this.meta = meta;
    }

    public void putMeta(String name, String value) {
        meta.put(name, value);
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public void addStep(Step step) {
        steps.add(step);
    }

    public String[] getComments() {
        return comments.toArray(new String[comments.size()]);
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    public void addComment(String comment) {
        comments.add(comment);
    }

    public int getTimesToRepeatForTable() {
        return timesToRepeatForTable;
    }

    public void setTimesToRepeatForTable(int timesToRepeatForTable) {
        this.timesToRepeatForTable = timesToRepeatForTable;
    }

    @Override
    public int hashCode() {
        int hashCode = 1;
        hashCode = 31 * hashCode + getName().hashCode();
        hashCode = 31 * hashCode + getSteps().hashCode();
        hashCode = 31 * hashCode + getMeta().hashCode();
        hashCode = 31 * hashCode + getTimesToRepeatForTable();
        hashCode = 31 * hashCode + getComments().hashCode();
        return hashCode;
    }
}
