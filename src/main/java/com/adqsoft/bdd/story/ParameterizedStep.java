package com.adqsoft.bdd.story;

public class ParameterizedStep extends Step {
    private StepParameter[] parameters;

    public ParameterizedStep(String stepType, String stepDescriptor, StepParameter[] parameters) {
        super(stepType, stepDescriptor);
        this.parameters = parameters;
    }

    public StepParameter[] getParameters() {
        return parameters;
    }

    public void setParameters(StepParameter[] parameters) {
        this.parameters = parameters;
    }
}
