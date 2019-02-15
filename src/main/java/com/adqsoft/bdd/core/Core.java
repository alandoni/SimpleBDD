package com.adqsoft.bdd.core;

import com.adqsoft.bdd.reporter.ReporterInterface;
import com.adqsoft.bdd.story.StepsScanner;
import com.adqsoft.bdd.story.Story;
import com.adqsoft.bdd.story.StoryReader;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Core {

    public static void main(String[] args) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        new Runner() {
            public Configuration getConfiguration() {
                return Configuration.mostUsefulConfiguration().setFailBuildOnFailure(true)
                        .setNumberOfRetries(3)
                        .setReporters(null);
            }

            public String getStoryPath() {
                return "**/resources/**/*.story";
            }

            public String getStepsPackage() {
                return "com.adqsoft.bdd.tests";
            }
        }.run();
    }
}
