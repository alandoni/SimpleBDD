package com.adqsoft.bdd.tests;

import com.adqsoft.bdd.core.Configuration;
import com.adqsoft.bdd.core.junit.JUnitRunner;
import com.adqsoft.bdd.core.MetafilterInterface;
import com.adqsoft.bdd.core.Runner;
import org.junit.runner.RunWith;

@RunWith(JUnitRunner.class)
public class InitializeWithJUnitRunner extends Runner {

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
}
