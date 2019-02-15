package com.adqsoft.bdd.tests;

import com.adqsoft.bdd.core.Configuration;
import com.adqsoft.bdd.core.Runner;
import com.adqsoft.bdd.reporter.ReporterInterface;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Initializer {

    @Test
    public void test() throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        new Runner() {
            public Configuration getConfiguration() {
                return Configuration.mostUsefulConfiguration().setFailBuildOnFailure(true)
                        .setNumberOfRetries(3)
                        .setReporters(new ReporterInterface[] { new PrintLnReporter() } );
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
