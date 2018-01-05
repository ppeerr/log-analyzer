package com.per.project.io;

import com.per.project.ApplicationTest;
import com.per.project.analysis.AnalysisPeriod;
import com.per.project.analysis.LogEntry;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.PrintStream;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class InputOutputServiceTest extends ApplicationTest {

    public static final String FIRST_GOOD_LOG_ENTRY_PERIOD = "2017-06-14 16:47:02 2017-06-14 16:47:02 100.0";

    @Test
    public void read() throws Exception {
        System.setIn(IOUtils.toInputStream(FIRST_LOG_LINE));

        LogEntry logEntry;
        try (InputOutputService ioService = new InputOutputService()) {
            logEntry = ioService.read();
        }

        assertNotNull(logEntry);
    }

    @Test
    public void readNull() throws Exception {
        System.setIn(IOUtils.toInputStream(""));

        LogEntry logEntry;
        try (InputOutputService ioService = new InputOutputService()) {
            logEntry = ioService.read();
        }

        assertNull(logEntry);
    }

    @Test
    public void write() throws Exception {
        System.setOut(new PrintStream(testCaseOutputStream));
        AnalysisPeriod period = new AnalysisPeriod(FIRST_GOOD_LOG_ENTRY);
        period.end();

        try (InputOutputService ioService = new InputOutputService()) {
            ioService.write(period);
        }

        String resultRaw = testCaseOutputStream.toString().trim();
        String result = Arrays.stream(resultRaw.split("\\t\\t")).reduce((s1, s2) -> s1 + " " + s2).orElse("");

        assertEquals(FIRST_GOOD_LOG_ENTRY_PERIOD, result);
    }
}