package com.per.project.analysis;

import com.per.project.ApplicationTest;
import org.junit.Test;

import static org.junit.Assert.*;

public class AnalysisPeriodTest extends ApplicationTest {

    @Test
    public void commonOneGoodLog() {
        AnalysisPeriod period = new AnalysisPeriod(FIRST_GOOD_LOG_ENTRY);

        assertEquals(FIRST_GOOD_LOG_ENTRY.getTimestamp(), period.getStartDate());
        assertEquals(FIRST_GOOD_LOG_ENTRY.getTimestamp(), period.getCurrentDate());
        assertEquals(1, period.getLogsCount().intValue());
        assertEquals(1, period.getGoodResponsesCount().intValue());
        assertTrue(period.isInProgress());
        assertFalse(period.isEmpty());

        assertNull(period.getEndDate());
    }

    @Test
    public void commonOneBadLog() {
        AnalysisPeriod period = new AnalysisPeriod(BAD_LOG_ENTRY);

        assertEquals(0, period.getGoodResponsesCount().intValue());
        assertTrue(period.isInProgress());
        assertFalse(period.isEmpty());

        assertNull(period.getEndDate());
    }

    @Test
    public void isBadRatioNowOneGoodLog() {
        AnalysisPeriod period = new AnalysisPeriod(FIRST_GOOD_LOG_ENTRY);

        assertFalse(period.isBadRatioNow());
    }

    @Test
    public void isBadRatioNowOneBadLog() {
        AnalysisPeriod period = new AnalysisPeriod(BAD_LOG_ENTRY);

        assertTrue(period.isBadRatioNow());
    }

    @Test
    public void getAvailabilityRatioOneGoodLog() {
        AnalysisPeriod period = new AnalysisPeriod(FIRST_GOOD_LOG_ENTRY);

        assertEquals(100., period.getAvailabilityRatio(), EPS);
    }

    @Test
    public void getAvailabilityRatioOneBadLog() {
        AnalysisPeriod period = new AnalysisPeriod(BAD_LOG_ENTRY);

        assertEquals(0., period.getAvailabilityRatio(), EPS);
    }

    @Test
    public void getAvailabilityRatio() {
        AnalysisPeriod period = new AnalysisPeriod(FIRST_GOOD_LOG_ENTRY);
        period.analyze(BAD_LOG_ENTRY);
        period.analyze(OTHER_BAD_LOG_ENTRY_ONE);
        period.analyze(OTHER_BAD_LOG_ENTRY_TWO);

        assertEquals(25., period.getAvailabilityRatio(), EPS);
    }

    @Test
    public void end() {
        AnalysisPeriod period = new AnalysisPeriod(FIRST_GOOD_LOG_ENTRY);
        period.analyze(BAD_LOG_ENTRY);
        period.end();

        assertEquals(FIRST_GOOD_LOG_ENTRY.getTimestamp(), period.getStartDate());
        assertEquals(BAD_LOG_ENTRY.getTimestamp(), period.getEndDate());
        assertFalse(period.isInProgress());
    }

    @Test
    public void isEmptyTrue() {
        AnalysisPeriod period = AnalysisPeriod.EMPTY_ANALYSIS_PERIOD;

        assertTrue(period.isEmpty());
        assertFalse(period.isInProgress());
    }

    @Test
    public void isEmptyFalse() {
        AnalysisPeriod period = new AnalysisPeriod(FIRST_GOOD_LOG_ENTRY);

        assertFalse(period.isEmpty());
        assertTrue(period.isInProgress());
    }

}
