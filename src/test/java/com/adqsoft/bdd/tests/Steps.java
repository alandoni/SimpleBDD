package com.adqsoft.bdd.tests;

import com.adqsoft.bdd.annotations.Given;
import com.adqsoft.bdd.annotations.Then;
import com.adqsoft.bdd.annotations.When;

public class Steps {

    @Given("this method works")
    private void methodWorks() {
        System.out.println("This method works!");
    }

    @When("we print the texts <methods>")
    private void printTexts(String[] texts) {
        for (String text : texts) {
            printText(text);
        }
    }

    @When("we print the text <method>")
    private void printText(String text) {
        System.out.println(text);
    }

    @Then("we assert this is <method>")
    private void assertText(String text) {
        if (!text.contains("method")) {
            throw new RuntimeException("Text doesn't contain \"method\"");
        }
    }

    @Then("we assert this is <methods>")
    private void assertTexts(String[] texts) {
        for (String text : texts) {
            assertText(text);
        }
    }

    @When("we call this one")
    private void callThisOne() {
        System.out.println("call this one");
    }

    @Then("we assert this")
    private void assertThis() {
        System.out.println("assert this");
    }
}
