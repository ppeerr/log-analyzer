package com.per.project;

import com.per.project.analysis.LogEntry;
import com.per.project.configuration.AppConfiguration;
import org.junit.Before;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class ApplicationTest {

    private final static DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy:HH:mm:ss");
    protected static final LocalDateTime GOOD_LOG_DATE = LocalDateTime.parse("14/06/2017:16:47:02", DATE_FORMAT);
    protected static final LocalDateTime BAD_LOG_DATE = LocalDateTime.parse("14/06/2017:16:48:02", DATE_FORMAT);
    private static final LocalDateTime OTHER_BAD_LOG_DATE_ONE = LocalDateTime.parse("14/06/2017:16:49:02", DATE_FORMAT);
    private static final LocalDateTime OTHER_BAD_LOG_DATE_TWO = LocalDateTime.parse("14/06/2017:16:50:02", DATE_FORMAT);

    private static final float MIN_AVAILABILITY_RATIO = 95.0f;
    private static final int MAX_RESPONSE_TIME = 120;

    protected final static String FIRST_LOG_LINE = "192.168.32.181 - - [14/06/2017:16:47:02 +1000] \"PUT /rest/v1.4/documents?zone=default&_rid=15b50208 HTTP/1.1\" 200 2 40.573017 \"-\" \"@list-item-updater\" prio:0";

    protected final static float EPS = 1e-6f;

    protected final static LogEntry FIRST_GOOD_LOG_ENTRY = new LogEntry(
            GOOD_LOG_DATE,
            200,
            40.573017F
    );
    protected final static LogEntry BAD_LOG_ENTRY = new LogEntry(
            BAD_LOG_DATE,
            200,
            410.573017F
    );
    protected final static LogEntry OTHER_BAD_LOG_ENTRY_ONE = new LogEntry(
            OTHER_BAD_LOG_DATE_ONE,
            200,
            210.573017F
    );
    protected final static LogEntry OTHER_BAD_LOG_ENTRY_TWO = new LogEntry(
            OTHER_BAD_LOG_DATE_TWO,
            200,
            130.573017F
    );

    protected ByteArrayOutputStream testCaseOutputStream = new ByteArrayOutputStream();


    @Before
    public void setUp() {
        AppConfiguration.setMinAvailabilityRatio(MIN_AVAILABILITY_RATIO);
        AppConfiguration.setMaxResponseTime(MAX_RESPONSE_TIME);
    }

}
