package com.adqsoft.bdd.core.junit;

import com.adqsoft.bdd.core.Runner;
import com.adqsoft.bdd.reporter.JUnitRunNotifierReporter;
import com.adqsoft.bdd.story.Scenario;
import com.adqsoft.bdd.story.Step;
import com.adqsoft.bdd.story.Story;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by alanquintiliano on 15/02/19.
 */
public class JUnitRunner extends BlockJUnit4ClassRunner {

    private Map<String, Description> descriptionMap;
    private Description rootDescription;
    private int count;
    private Runner runner;

    public JUnitRunner(Class<? extends Runner> klass) throws InitializationError, IllegalAccessException, InstantiationException, NoSuchMethodException, IOException, InvocationTargetException {
        super(klass);

        runner = klass.newInstance();

        descriptionMap = new HashMap<String, Description>();

        rootDescription = Description.createSuiteDescription(getName(), null);
        descriptionMap.put("Stories", rootDescription);

        Story[] stories = runner.getStories();

        count = 0;
        for (Story story : stories) {
            Description storyDescription = createSuiteDescription("Story: " + story.getName());
            for (Scenario scenario : story.getScenarios()) {
                Description scenarioDescription = createSuiteDescription("Scenario: " + scenario.getName());
                for (Step step : scenario.getSteps()) {
                    Description stepDescription = createStepDescription(step.getStepType() + " " + step.getDescriptor());
                    scenarioDescription.addChild(stepDescription);
                }
                storyDescription.addChild(scenarioDescription);
                count++;
            }

            rootDescription.addChild(storyDescription);
        }
    }

    private Description createSuiteDescription(String name) {
        Description description = Description.createSuiteDescription(name, "");
        descriptionMap.put(name, description);
        return description;
    }

    private Description createStepDescription(String name) {
        Description description = Description.createTestDescription(name, "");
        descriptionMap.put(name, description);
        return description;
    }

    @Override
    protected String getName() {
        return "Simpler BDD Tests";
    }

    @Override
    public Description getDescription() {
        return rootDescription;
    }

    @Override
    public int testCount() {
        return count;
    }

    @Override
    protected Statement childrenInvoker(final RunNotifier notifier) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                runner.getConfiguration().addReporter(new JUnitRunNotifierReporter(notifier, descriptionMap));
                try {
                    runner.run();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }
}
