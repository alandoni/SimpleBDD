package com.adqsoft.bdd.story;

public class Result {

    public enum ResultType {
        SUCCESS, FAIL
    }

    private ResultType result;
    private Throwable throwable;

    public Result(ResultType result) {
        this(result, null);
    }

    public Result(ResultType result, Throwable throwable) {
        this.result = result;
        this.throwable = throwable;
    }

    public ResultType getResult() {
        return result;
    }

    public void setResult(ResultType result) {
        this.result = result;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }
}
