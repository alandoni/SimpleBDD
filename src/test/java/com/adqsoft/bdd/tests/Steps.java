package com.adqsoft.bdd.tests;

import com.adqsoft.bdd.annotations.Given;
import com.adqsoft.bdd.annotations.Then;
import com.adqsoft.bdd.annotations.When;

public class Steps {

    private int secondsWait = 1;

    private void wait(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Given("this method works")
    private void methodWorks() {
        wait(secondsWait);
        System.out.println("This method works!");
    }

    @When("we print the texts <methods>")
    private void printTexts(String[] texts) {
        wait(secondsWait);
        for (String text : texts) {
            printText(text);
        }
    }

    @When("we print the text <method>")
    private void printText(String text) {
        wait(secondsWait);
        System.out.println(text);
    }

    @Then("we assert this is <method>")
    private void assertText(String text) {
        wait(secondsWait);
        if (!text.contains("method")) {
            throw new RuntimeException("Text doesn't contain \"method\"");
        }
    }

    @Then("we assert these are <methods>")
    private void assertTexts(String[] texts) {
        wait(secondsWait);
        for (String text : texts) {
            assertText(text);
        }
    }

    @When("we call this one")
    private void callThisOne() {
        wait(secondsWait);
        System.out.println("call this one");
    }

    @Then("we assert this")
    private void assertThis() {
        wait(secondsWait);
        System.out.println("assert this");
    }

    @When("we print the text <methods> but <method>")
    private void printTwoParams(String[] texts, String text) {
        wait(secondsWait);
        System.out.println("Print two texts");
    }
}
