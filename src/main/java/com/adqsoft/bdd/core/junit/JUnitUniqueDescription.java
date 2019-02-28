package com.adqsoft.bdd.core.junit;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by alanquintiliano on 16/02/19.
 */
public class JUnitUniqueDescription {
    private final Set<String> uniqueDescriptions = new HashSet<String>();

    public String getUniqueDescription(String junitSafeString) {
        String uniqueDescription = junitSafeString;
        while (uniqueDescriptions.contains(uniqueDescription)) {
            uniqueDescription += '\u200B'; // zero-width-space
        }
        uniqueDescriptions.add(uniqueDescription);
        return uniqueDescription;
    }
}
