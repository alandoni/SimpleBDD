package com.adqsoft.bdd.story;

public class ScenarioResult {

    public enum Result {
        SUCCESS, FAIL, FLAKY
    }

    private ScenarioResult.Result result;
    private Throwable throwable;

    public ScenarioResult(ScenarioResult.Result result) {
        this(result, null);
    }

    public ScenarioResult(ScenarioResult.Result result, Throwable throwable) {
        this.result = result;
        this.throwable = throwable;
    }

    public ScenarioResult.Result getResult() {
        return result;
    }

    public void setResult(ScenarioResult.Result result) {
        this.result = result;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }
}
