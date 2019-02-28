package com.adqsoft.bdd.core;

import com.adqsoft.bdd.reporter.ReporterInterface;
import com.adqsoft.bdd.story.Scenario;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Configuration {

    private int retries;
    private boolean flakyAsFailure;
    private boolean failBuildOnFailure;
    private List<ReporterInterface> reporters = new ArrayList<ReporterInterface>();
    private boolean skipPendingStepsOnFailure;
    private MetafilterInterface metafilterInterface;

    public Configuration setNumberOfRetries(int retries) {
        this.retries = retries;
        return this;
    }

    public Configuration setFlakyAsFailure(boolean flakyAsFailure) {
        this.flakyAsFailure = flakyAsFailure;
        return this;
    }

    public Configuration setFailBuildOnFailure(boolean failBuildOnFailure) {
        this.failBuildOnFailure = failBuildOnFailure;
        return this;
    }

    public Configuration setReporters(List<ReporterInterface> reporters) {
        this.reporters = reporters;
        return this;
    }

    public Configuration addReporter(ReporterInterface reporter) {
        reporters.add(reporter);
        return this;
    }

    public Configuration setSkipPendingStepsOnFailure(boolean skipPendingStepsOnFailure) {
        this.skipPendingStepsOnFailure = skipPendingStepsOnFailure;
        return this;
    }

    public int getRetries() {
        return retries;
    }

    public boolean isFlakyAsFailure() {
        return flakyAsFailure;
    }

    public boolean isFailBuildOnFailure() {
        return failBuildOnFailure;
    }

    public List<ReporterInterface> getReporters() {
        return reporters;
    }

    public static Configuration mostUsefulConfiguration() {
        return new Configuration()
                .setFailBuildOnFailure(true)
                .setFlakyAsFailure(false)
                .setNumberOfRetries(1);
    }

    public boolean isSkipPendingStepsOnFailure() {
        return skipPendingStepsOnFailure;
    }

    protected boolean shouldRunScenario(Scenario scenario) {
        for (Map.Entry<String, String> entry : scenario.getMeta().entrySet()) {
            boolean shouldRun = metafilterInterface.shouldRunScenario(entry.getKey(), entry.getValue());
            if (shouldRun) {
                return true;
            }
        }
        return false;
    }

    public MetafilterInterface getMetafilterInterface() {
        return metafilterInterface;
    }

    public Configuration setMetafilterInterface(MetafilterInterface metafilterInterface) {
        this.metafilterInterface = metafilterInterface;
        return this;
    }
}
