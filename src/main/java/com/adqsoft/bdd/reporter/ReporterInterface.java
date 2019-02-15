package com.adqsoft.bdd.reporter;

import com.adqsoft.bdd.story.*;

public interface ReporterInterface {

    void beforeStories();
    void afterStories(Result result);

    void beforeStory(Story story);
    void afterStory(Story story, Result result);

    void beforeScenario(Scenario scenario);
    void afterScenario(Scenario scenario, ScenarioResult result);

    void beforeStep(Step step);
    void afterStep(Step step, StepResult result);
}
