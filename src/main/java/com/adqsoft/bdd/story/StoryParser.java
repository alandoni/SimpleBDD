package com.adqsoft.bdd.story;

import com.adqsoft.bdd.exceptions.StoryParserException;

public class StoryParser {

    private final static String TAG_STORY_NAME = "StoryName: ";
    private final static String TAG_SCENARIO_NAME = "Scenario: ";
    private final static String TAG_COM0MENT = "!-- ";
    private final static String TAG_META = "@";
    private final static String TAG_AND = "And";
    public final static String TAG_GIVEN = "Given";
    public final static String TAG_WHEN = "When";
    public final static String TAG_THEN = "Then";

    private Story story;

    private static String removeExtensionFromFile(String file) {
        String extension = "";

        int index = file.lastIndexOf('.');
        if (index > 0) {
            extension = file.substring(index);
        }

        return file.substring(0, file.length() - extension.length());
    }

    public Story parse(String fileName, String[] contentsOfFile) {
        story = new Story();
        story.setName(removeExtensionFromFile(fileName));

        Scenario currentScenario = null;
        String lastStepType = null;
        for (String line : contentsOfFile) {
            if (line.startsWith(TAG_STORY_NAME)) {
                story.setName(line.substring(TAG_STORY_NAME.length()));
            }

            Scenario scenario = parseScenario(line);
            if (scenario != null) {
                currentScenario = scenario;
            }
            parseMeta(currentScenario, line);
            parseComments(currentScenario, line);
            lastStepType = parseSteps(currentScenario, line, lastStepType);
        }
        return story;
    }

    private Scenario parseScenario(String line) {
        Scenario scenario = null;
        if (line.startsWith(TAG_SCENARIO_NAME)) {
            scenario = new Scenario();
            scenario.setName(line.substring(TAG_SCENARIO_NAME.length()));
            story.addScenario(scenario);
        }
        return scenario;
    }

    private void parseMeta(Scenario scenario, String line) {
        if (line.startsWith(TAG_META)) {

            if (scenario == null) {
                throw new StoryParserException("Meta tag out of any scenario");
            }

            String metaName, metaValue;

            if (line.contains(" ")) {
                metaName = line.substring(TAG_META.length(), line.indexOf(" "));
                metaValue = line.substring(TAG_META.length() + metaName.length());
            } else {
                metaName = line.substring(TAG_META.length());
                metaValue = "";
            }
            if (metaName == null) {
                throw new StoryParserException("Invalid meta tag");
            }

            scenario.putMeta(metaName, metaValue);
        }
    }

    private void parseComments(Scenario scenario, String line) {
        if (line.startsWith(TAG_COM0MENT)) {
            String comment = line.substring(TAG_COM0MENT.length());
            if (scenario == null) {
                story.addComment(comment);
            } else {
                scenario.addComment(comment);
            }
        }
    }
    
    private String parseSteps(Scenario scenario, String line, String lastStepType) {
        String stepType = lastStepType;
        boolean shouldAddStep = false;

        if (line.startsWith(TAG_GIVEN)) {
            stepType = TAG_GIVEN;
            shouldAddStep = true;
        }

        if (line.startsWith(TAG_WHEN)) {
            stepType = TAG_WHEN;
            shouldAddStep = true;
        }

        if (line.startsWith(TAG_THEN)) {
            stepType = TAG_THEN;
            shouldAddStep = true;
        }

        if (line.startsWith(TAG_AND)) {
            shouldAddStep = true;
        }

        if (shouldAddStep) {
            scenario.addStep(createStep(stepType, line));
        }

        return stepType;
    }

    private Step createStep(String stepType, String line) {
        return new Step(stepType, line.substring(stepType.length() + 1));
    }
}
