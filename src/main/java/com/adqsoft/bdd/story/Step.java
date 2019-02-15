package com.adqsoft.bdd.story;

import java.lang.reflect.Method;

public class Step {
    private String stepType;
    private String descriptor;
    private Method method;

    public Step(String stepType, String stepDescriptor) {
        this.stepType = stepType;
        this.descriptor = stepDescriptor;
    }

    public String getStepType() {
        return stepType;
    }

    public void setStepType(String stepType) {
        this.stepType = stepType;
    }

    public String getDescriptor() {
        return descriptor;
    }

    public void setDescriptor(String descriptor) {
        this.descriptor = descriptor;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return getStepType() + " " + getDescriptor();
    }
}
