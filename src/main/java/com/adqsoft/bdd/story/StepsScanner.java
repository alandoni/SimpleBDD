package com.adqsoft.bdd.story;

import com.adqsoft.bdd.annotations.Given;
import com.adqsoft.bdd.annotations.Then;
import com.adqsoft.bdd.annotations.When;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class StepsScanner {

    public static Method[] scanStepsOnPackage(String packageName) {
        Reflections reflections = new Reflections(packageName, new MethodAnnotationsScanner());

        Set<Method> annotated = new HashSet<Method>();
        annotated.addAll(reflections.getMethodsAnnotatedWith(Given.class));
        annotated.addAll(reflections.getMethodsAnnotatedWith(When.class));
        annotated.addAll(reflections.getMethodsAnnotatedWith(Then.class));

        return annotated.toArray(new Method[annotated.size()]);
    }
}
