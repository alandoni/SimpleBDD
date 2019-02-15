package com.adqsoft.bdd.core;

import com.adqsoft.bdd.story.StepsScanner;
import com.adqsoft.bdd.story.Story;
import com.adqsoft.bdd.story.StoryReader;

import java.io.IOException;

public class Core {

    public static void main(String[] args) throws IOException {
        Story[] stories = new StoryReader().findStoriesByPath("**/resources/**/*.story");

        StepsScanner.scanStepsOnPackage("com.adqsoft.bdd.tests");

    }
}
