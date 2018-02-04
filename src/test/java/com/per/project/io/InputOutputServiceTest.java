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
        LogEntry logEntry;
        try (InputOutputService ioService = new InputOutputService(IOUtils.toInputStream(FIRST_LOG_LINE), System.out)) {
            logEntry = ioService.read();
        }

        assertNotNull(logEntry);
    }

    @Test
    public void readNull() throws Exception {
        LogEntry logEntry;
        try (InputOutputService ioService = new InputOutputService(IOUtils.toInputStream(""), System.out)) {
            logEntry = ioService.read();
        }

        assertNull(logEntry);
    }

    @Test
    public void write() throws Exception {
        AnalysisPeriod period = new AnalysisPeriod(FIRST_GOOD_LOG_ENTRY);
        period.end();

        try (InputOutputService ioService = new InputOutputService(System.in, new PrintStream(testCaseOutputStream))) {
            ioService.write(period);
        }

        String resultRaw = testCaseOutputStream.toString().trim();
        String result = Arrays.stream(resultRaw.split("\\t\\t")).reduce((s1, s2) -> s1 + " " + s2).orElse("");

        assertEquals(FIRST_GOOD_LOG_ENTRY_PERIOD, result);
    }
}