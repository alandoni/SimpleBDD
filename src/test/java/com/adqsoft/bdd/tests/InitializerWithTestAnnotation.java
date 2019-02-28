package com.adqsoft.bdd.tests;

import com.adqsoft.bdd.core.Configuration;
import com.adqsoft.bdd.core.MetafilterInterface;
import com.adqsoft.bdd.core.Runner;
import com.adqsoft.bdd.reporter.ReporterInterface;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class InitializerWithTestAnnotation {

    @Test
    public void test() throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        new Runner() {
            public Configuration configure() {
                return Configuration.mostUsefulConfiguration().setFailBuildOnFailure(true)
                        .setNumberOfRetries(3)
                        .addReporter(new PrintLnReporter())
                        .setMetafilterInterface(new MetafilterInterface() {
                            public boolean shouldRunScenario(String metaName, String value) {
                                return metaName.equals("only");
                            }
                        });
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
