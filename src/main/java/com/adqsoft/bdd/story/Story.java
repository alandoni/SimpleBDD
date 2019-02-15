package com.adqsoft.bdd.story;

import java.util.ArrayList;
import java.util.List;

public class Story {
    private String name;
    private List<Scenario> scenarios;
    private List<String> comments;

    public Story() {
        scenarios = new ArrayList<Scenario>();
        comments = new ArrayList<String>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Scenario[] getScenarios() {
        return scenarios.toArray(new Scenario[scenarios.size()]);
    }

    public void addScenario(Scenario scenario) {
        scenarios.add(scenario);
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
}
