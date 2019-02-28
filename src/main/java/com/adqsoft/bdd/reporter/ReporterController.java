package com.adqsoft.bdd.reporter;

import com.adqsoft.bdd.story.*;

import java.util.List;

public class ReporterController implements ReporterInterface {

    private final ReporterInterface[] reporters;

    public ReporterController(ReporterInterface[] reporters) {
        this.reporters = reporters;
    }

    public void beforeStories() {
        if (this.reporters == null) {
            return;
        }
        for (ReporterInterface reporter : this.reporters) {
            reporter.beforeStories();
        }
    }

    public void afterStories(Result result) {
        if (this.reporters == null) {
            return;
        }
        for (ReporterInterface reporter : this.reporters) {
            reporter.afterStories(result);
        }
    }

    public void beforeStory(Story story) {
        if (this.reporters == null) {
            return;
        }
        for (ReporterInterface reporter : this.reporters) {
            reporter.beforeStory(story);
        }
    }

    public void afterStory(Story story, Result result) {
        if (this.reporters == null) {
            return;
        }
        for (ReporterInterface reporter : this.reporters) {
            reporter.afterStory(story, result);
        }
    }

    public void beforeScenario(Scenario scenario) {
        if (this.reporters == null) {
            return;
        }
        for (ReporterInterface reporter : this.reporters) {
            reporter.beforeScenario(scenario);
        }
    }

    public void afterScenario(Scenario scenario, ScenarioResult result) {
        if (this.reporters == null) {
            return;
        }
        for (ReporterInterface reporter : this.reporters) {
            reporter.afterScenario(scenario, result);
        }
    }

    public void beforeStep(Step step) {
        if (this.reporters == null) {
            return;
        }
        for (ReporterInterface reporter : this.reporters) {
            reporter.beforeStep(step);
        }
    }

    public void afterStep(Step step, StepResult result) {
        if (this.reporters == null) {
            return;
        }
        for (ReporterInterface reporter : this.reporters) {
            reporter.afterStep(step, result);
        }
    }
}
