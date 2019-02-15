package com.adqsoft.bdd.story;

public class StepResult {

    public enum Result {
        SUCCESS, FAIL, PENDING
    }

    private Result result;
    private Throwable throwable;

    public StepResult(Result result) {
        this(result, null);
    }

    public StepResult(Result result, Throwable throwable) {
        this.result = result;
        this.throwable = throwable;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }
}
