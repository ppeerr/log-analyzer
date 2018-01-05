package com.per.project.analysis;

import com.per.project.ApplicationTest;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.PrintStream;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class AnalysisServiceTest extends ApplicationTest {

    private AnalysisService analysisService = new AnalysisService();

    @Before
    @Override
    public void setUp() {
        super.setUp();
        System.setOut(new PrintStream(testCaseOutputStream));
    }

    @Test
    public void analyzeSmall() throws Exception {
        System.setIn(getClass().getClassLoader().getResourceAsStream("accessSmall.log"));
        String expectedRaw = IOUtils.toString(getClass().getClassLoader().getResourceAsStream("resultSmall.txt"));
        String expected = Arrays.stream(expectedRaw.split("\\s+")).reduce((s1, s2) -> s1 + " " + s2).orElse("");

        analysisService.analyze();
        String resultRaw = testCaseOutputStream.toString();
        String result = Arrays.stream(resultRaw.split("\\s+")).reduce((s1, s2) -> s1 + " " + s2).orElse("");

        assertEquals(expected, result);
    }

    @Test
    public void analyze() throws Exception {
        System.setIn(getClass().getClassLoader().getResourceAsStream("access.log"));
        String expectedRaw = IOUtils.toString(getClass().getClassLoader().getResourceAsStream("result.txt"));
        String expected = Arrays.stream(expectedRaw.split("\\s+")).reduce("", (s1, s2) -> s1 + " " + s2);

        analysisService.analyze();
        String resultRaw = testCaseOutputStream.toString();
        String result = Arrays.stream(resultRaw.split("\\s+")).reduce("", (s1, s2) -> s1 + " " + s2);

        assertEquals(expected, result);
    }
}
