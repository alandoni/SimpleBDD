package com.adqsoft.bdd.story;

public class StepParameter {

    public enum ParameterType {
        SIMPLE, ARRAY, TABLE
    }

    private String[] parameters;
    private ParameterType parameterType;

    public StepParameter(ParameterType parameterType, String[] parameters) {
        this.parameters = parameters;
        this.parameterType = parameterType;
    }

    public String[] getParameters() {
        return parameters;
    }

    public ParameterType getParameterType() {
        return parameterType;
    }
}
