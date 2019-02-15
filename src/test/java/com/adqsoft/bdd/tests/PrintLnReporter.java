package com.adqsoft.bdd.tests;

import com.adqsoft.bdd.reporter.ReporterInterface;
import com.adqsoft.bdd.story.*;

public class PrintLnReporter implements ReporterInterface {
    public void beforeStories() {
        System.out.println("Before Stories");
        System.out.println("");
    }

    public void afterStories(Result result) {
        System.out.println("After Stories: " + result.getResult());
    }

    public void beforeStory(Story story) {
        System.out.println("Before Story: " + story.getName());
        System.out.println("");
    }

    public void afterStory(Story story, Result result) {
        System.out.println("After Story: " + story.getName() + ": " + result.getResult());
        System.out.println("");
    }

    public void beforeScenario(Scenario scenario) {
        System.out.println("Before scenario: " + scenario.getName());
    }

    public void afterScenario(Scenario scenario, ScenarioResult result) {
        System.out.println("After scenario: " + scenario.getName() + ": " + result.getResult());
        System.out.println("");
        System.out.println("");
    }

    public void beforeStep(Step step) {
        System.out.println("Before step: " + step.toString());
    }

    public void afterStep(Step step, StepResult result) {
        System.out.println("After step: " + step.toString() + ": " + result.getResult());
        if (result.getThrowable() != null) {
            result.getThrowable().printStackTrace();
        }
    }
}
