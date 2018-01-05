package com.per.project.analysis;

import com.per.project.ApplicationTest;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;

public class LogEntryTest extends ApplicationTest {

    private final static DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy:HH:mm:ss");

    @Test
    public void getTimestamp() {
        assertEquals(LocalDateTime.parse("14/06/2017:16:47:02", DATE_FORMAT), FIRST_GOOD_LOG_ENTRY.getTimestamp());
    }

    @Test
    public void getResponseCode() {
        assertEquals(200, FIRST_GOOD_LOG_ENTRY.getResponseCode().intValue());
    }

    @Test
    public void getResponseTime() {
        assertEquals(40.573017f, FIRST_GOOD_LOG_ENTRY.getResponseTime(), EPS);
    }
}
