package com.adqsoft.bdd.story;

import org.apache.tools.ant.DirectoryScanner;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StoryReader {

    public static Story[] findStoriesByPath(String path) throws IOException {
        String folder = new File(".").getCanonicalPath();
        DirectoryScanner scanner = new DirectoryScanner();
        scanner.setIncludes(new String[]{ path });
        scanner.setBasedir(folder);
        scanner.setCaseSensitive(false);
        scanner.scan();
        String[] files = scanner.getIncludedFiles();

        List<Story> stories = new ArrayList<Story>();
        for (String fileName : files) {
            stories.add(convertFileToStory(fileName));
        }
        return stories.toArray(new Story[stories.size()]);
    }

    private static String[] readFile(File file) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            List<String> lines = new ArrayList<String>();
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
            return lines.toArray(new String[lines.size()]);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Story convertFileToStory(String fileName) {
        File file = new File(fileName);
        String[] content = readFile(file);
        String fileNameAndExtension = file.getAbsoluteFile().getName();

        return new StoryParser().parse(fileNameAndExtension, content);
    }
}
