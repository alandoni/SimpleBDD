package com.adqsoft.bdd.story;

public class ParameterizedStep extends Step {
    private String[] parameters;

    public ParameterizedStep(String stepType, String stepDescriptor, String[] parameters) {
        super(stepType, stepDescriptor);
        this.parameters = parameters;
    }

    public String[] getParameters() {
        return parameters;
    }

    public void setParameters(String[] parameters) {
        this.parameters = parameters;
    }
}
