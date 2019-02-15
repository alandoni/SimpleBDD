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
        reporter.beforeStories();
        Result storiesResult = new Result(Result.ResultType.SUCCESS);
        for (Story story : stories) {
            Result storyResult = runStory(story, reporter);

            if (storyResult.getResult() != Result.ResultType.SUCCESS) {
                storiesResult = storyResult;
            }
        }
        reporter.afterStories(storiesResult);

        if (getConfiguration().isFailBuildOnFailure()) {
            Assertions.assertTrue(storiesResult.getResult() == Result.ResultType.SUCCESS);
        }
    }

    private ScenarioResult runScenario(Scenario scenario, ReporterController reporter) {
        ScenarioResult scenarioResult = new ScenarioResult(ScenarioResult.Result.SUCCESS);
        int times = 0;
        boolean flaky = false;
        do {
            reporter.beforeScenario(scenario);
            for (Step step : scenario.getSteps()) {
                reporter.beforeStep(step);
                StepResult result;
                if (step.getMethod() == null) {
                    result = new StepResult(StepResult.Result.PENDING);
                } else {
                    result = runMethod(step.getMethod());
                }
                reporter.afterStep(step, result);

                if (result.getResult() != StepResult.Result.SUCCESS) {
                    scenarioResult = new ScenarioResult(ScenarioResult.Result.FAIL, result.getThrowable());
                    break;
                } else if (times > 0) {
                    flaky = true;
                }
            }
            reporter.afterScenario(scenario, scenarioResult);
            times++;
        } while (scenarioResult.getResult() == ScenarioResult.Result.FAIL && times < getConfiguration().getRetries());

        if (flaky && !getConfiguration().isFlakyAsFailure()) {
            scenarioResult = new ScenarioResult(ScenarioResult.Result.FLAKY, scenarioResult.getThrowable());
        }

        return scenarioResult;
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

    private StepResult runMethod(Method method) {
        try {
            Object instance = Class.forName(method.getDeclaringClass().getName()).newInstance();
            method.invoke(instance);
            return new StepResult(StepResult.Result.SUCCESS);
        } catch (Throwable e) {
            return new StepResult(StepResult.Result.FAIL, e);
        }
    }

    public abstract Configuration getConfiguration();

    public abstract String getStoryPath();

    public abstract String getStepsPackage();
}
