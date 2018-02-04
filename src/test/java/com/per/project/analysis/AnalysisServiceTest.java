package com.per.project.analysis;

import com.per.project.ApplicationTest;
import com.per.project.io.InputOutputService;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class AnalysisServiceTest extends ApplicationTest {

    private AnalysisServiceForTest analysisService;

    @Test
    public void analyzeSmall() throws Exception {
        analysisService = new AnalysisServiceForTest(
                getClass().getClassLoader().getResourceAsStream("accessSmall.log"),
                new PrintStream(testCaseOutputStream)
        );
        String expectedRaw = IOUtils.toString(getClass().getClassLoader().getResourceAsStream("resultSmall.txt"));
        String expected = Arrays.stream(expectedRaw.split("\\s+")).reduce((s1, s2) -> s1 + " " + s2).orElse("");

        analysisService.analyze();
        String resultRaw = testCaseOutputStream.toString();
        String result = Arrays.stream(resultRaw.split("\\s+")).reduce((s1, s2) -> s1 + " " + s2).orElse("");

        assertEquals(expected, result);
    }

    @Test
    public void analyze() throws Exception {
        analysisService = new AnalysisServiceForTest(
                getClass().getClassLoader().getResourceAsStream("access.log"),
                new PrintStream(testCaseOutputStream)
        );
        String expectedRaw = IOUtils.toString(getClass().getClassLoader().getResourceAsStream("result.txt"));
        String expected = Arrays.stream(expectedRaw.split("\\s+")).reduce("", (s1, s2) -> s1 + " " + s2);

        analysisService.analyze();
        String resultRaw = testCaseOutputStream.toString();
        String result = Arrays.stream(resultRaw.split("\\s+")).reduce("", (s1, s2) -> s1 + " " + s2);

        assertEquals(expected, result);
    }

    private class AnalysisServiceForTest extends AnalysisService {

        private final InputStream inputStream;
        private final OutputStream outputStream;

        AnalysisServiceForTest(InputStream inputStream, OutputStream outputStream) {
            this.inputStream = inputStream;
            this.outputStream = outputStream;
        }

        @Override
        protected InputOutputService getInputOutputService() {
            return new InputOutputService(this.inputStream, this.outputStream);
        }
    }
}
