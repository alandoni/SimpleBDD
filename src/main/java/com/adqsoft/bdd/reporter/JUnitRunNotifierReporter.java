package com.adqsoft.bdd.reporter;

import com.adqsoft.bdd.story.*;
import com.adqsoft.bdd.story.Result;
import org.junit.runner.*;
import org.junit.runner.notification.RunNotifier;

import java.util.Map;

/**
 * Created by alanquintiliano on 16/02/19.
 */
public class JUnitRunNotifierReporter implements ReporterInterface {

    private final RunNotifier notifier;
    private final Map<String, Description>  descriptionMap;

    public JUnitRunNotifierReporter(RunNotifier notifier, Map<String, Description> descriptionMap) {
        this.notifier = notifier;
        this.descriptionMap = descriptionMap;
    }

    public void beforeStories() {
        this.notifier.fireTestRunStarted(descriptionMap.get("Stories"));
    }

    public void afterStories(Result result) {
        this.notifier.fireTestRunFinished(new org.junit.runner.Result());
    }

    public void beforeStory(Story story) {
        this.notifier.fireTestRunStarted(descriptionMap.get("Story: " + story.getName()));
    }

    public void afterStory(Story story, Result result) {
        this.notifier.fireTestRunFinished(new org.junit.runner.Result());
    }

    public void beforeScenario(Scenario scenario) {
        //this.notifier.fireTestStarted(descriptionMap.get("Scenario: " + scenario.getName()));
    }

    public void afterScenario(Scenario scenario, ScenarioResult result) {
        //this.notifier.fireTestFinished(descriptionMap.get("Scenario: " + scenario.getName()));
    }

    public void beforeStep(Step step) {
        //this.notifier.fireTestRunStarted(descriptionMap.get(step.getStepType() + " " + step.getDescriptor()));
    }

    public void afterStep(Step step, StepResult result) {
        //this.notifier.fireTestFinished(descriptionMap.get(step.getStepType() + " " + step.getDescriptor()));
    }
}
