package com.adqsoft.bdd.core;

import com.adqsoft.bdd.annotations.Given;
import com.adqsoft.bdd.annotations.Then;
import com.adqsoft.bdd.annotations.When;
import com.adqsoft.bdd.reporter.ReporterController;
import com.adqsoft.bdd.reporter.ReporterInterface;
import com.adqsoft.bdd.story.*;
import org.junit.jupiter.api.Assertions;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class Runner {

    public void run() throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Story[] stories = getStories();

        Configuration configuration = getConfiguration();

        ReporterController reporter = new ReporterController(configuration.getReporters());

        Result storiesResult = runStories(stories, reporter);

        if (configuration.isFailBuildOnFailure()) {
            Assertions.assertTrue(storiesResult.getResult() == Result.ResultType.SUCCESS);
        }
    }

    private Result runStories(Story[] stories, ReporterController reporter) {
        Result storiesResult = new Result(Result.ResultType.SUCCESS);
        reporter.beforeStories();
        for (Story story : stories) {
            Result storyResult = runStory(story, reporter);

            if (storyResult.getResult() != Result.ResultType.SUCCESS) {
                storiesResult = storyResult;
            }
        }
        reporter.afterStories(storiesResult);
        return storiesResult;
    }

    private Result runStory(Story story, ReporterController reporter) {
        Result storyResult = new Result(Result.ResultType.SUCCESS);;
        reporter.beforeStory(story);
        for (Scenario scenario : story.getScenarios()) {
            ScenarioResult scenarioResult = runScenario(scenario, reporter);

            if (scenarioResult.getResult() != ScenarioResult.Result.SUCCESS) {
                storyResult = new Result(Result.ResultType.FAIL, scenarioResult.getThrowable());
            }
        }
        reporter.afterStory(story, storyResult);
        return storyResult;
    }

    private ScenarioResult runScenario(Scenario scenario, ReporterController reporter) {
        ScenarioResult scenarioResult = new ScenarioResult(ScenarioResult.Result.SUCCESS);
        Configuration configuration = getConfiguration();
        int times = 0;
        boolean flaky = false;
        do {
            reporter.beforeScenario(scenario);
            for (Step step : scenario.getSteps()) {
                StepResult result = runStep(reporter, scenarioResult, step);

                if (result.getResult() != StepResult.Result.SUCCESS) {
                    scenarioResult = new ScenarioResult(ScenarioResult.Result.FAIL, result.getThrowable());
                } else if (times > 0) {
                    flaky = true;
                }
            }
            reporter.afterScenario(scenario, scenarioResult);
            times++;
        } while (scenarioResult.getResult() == ScenarioResult.Result.FAIL && times < configuration.getRetries());

        if (flaky && !configuration.isFlakyAsFailure()) {
            scenarioResult = new ScenarioResult(ScenarioResult.Result.FLAKY, scenarioResult.getThrowable());
        }

        return scenarioResult;
    }

    private StepResult runStep(ReporterController reporter, ScenarioResult scenarioResult, Step step) {
        reporter.beforeStep(step);
        StepResult result;

        if (step.getMethod() == null || (scenarioResult.getResult() != ScenarioResult.Result.SUCCESS && getConfiguration().isSkipPendingStepsOnFailure())) {
            result = new StepResult(StepResult.Result.PENDING);
        } else {
            result = runMethod(step);
        }

        reporter.afterStep(step, result);
        return result;
    }

    private Story[] getStories() throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Story[] stories = StoryReader.findStoriesByPath(getStoryPath());
        Method[] methods = StepsScanner.scanStepsOnPackage(getStepsPackage());

        setMethodsOnSteps(stories, methods);
        return stories;
    }

    private void setMethodsOnSteps(Story[] stories, Method[] methods) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        for (Story story : stories) {
            for (Scenario scenario : story.getScenarios()) {
                for (Step step : scenario.getSteps()) {
                    setMethodForStep(step, methods);
                }
            }
        }
    }

    private void setMethodForStep(Step step, Method[] methods) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        for (Method method : methods) {
            setMethodForStepAccordingToType(step, method, Given.class, StoryParser.TAG_GIVEN);
            setMethodForStepAccordingToType(step, method, Then.class, StoryParser.TAG_THEN);
            setMethodForStepAccordingToType(step, method, When.class, StoryParser.TAG_WHEN);
        }
    }

    private <T extends Annotation> void setMethodForStepAccordingToType(Step step, Method method, Class<? extends Annotation> type, String tag) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        T annotation = (T) method.getAnnotation(type);
        if (annotation != null) {
            String value = (String) annotation.getClass().getMethod("value").invoke(annotation);
            if (step.getDescriptor().equals(value) && step.getStepType().equals(tag)) {
                method.setAccessible(true);
                step.setMethod(method);
            }
        }
    }

    private StepResult runMethod(Step step) {
        try {
            Method method = step.getMethod();
            Object instance = Class.forName(method.getDeclaringClass().getName()).newInstance();

            if (step instanceof ParameterizedStep) {
                method.invoke(instance, ((ParameterizedStep) step).getParameters());
            } else {
                method.invoke(instance);
            }
            return new StepResult(StepResult.Result.SUCCESS);
        } catch (Throwable e) {
            return new StepResult(StepResult.Result.FAIL, e);
        }
    }

    public abstract Configuration getConfiguration();

    public abstract String getStoryPath();

    public abstract String getStepsPackage();
}
