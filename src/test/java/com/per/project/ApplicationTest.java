package com.per.project;

import com.per.project.analysis.LogEntry;
import com.per.project.configuration.AppConfiguration;
import org.junit.Before;

import java.io.ByteArrayOutputStream;

public abstract class ApplicationTest {

    private static final float MIN_AVAILABILITY_RATIO = 95.0f;
    private static final int MAX_RESPONSE_TIME = 120;

    protected final static String FIRST_LOG_LINE = "192.168.32.181 - - [14/06/2017:16:47:02 +1000] \"PUT /rest/v1.4/documents?zone=default&_rid=15b50208 HTTP/1.1\" 200 2 40.573017 \"-\" \"@list-item-updater\" prio:0";
    private final static String BAD_LOG_LINE = "192.168.32.181 - - [14/06/2017:16:48:02 +1000] \"PUT /rest/v1.4/documents?zone=default&_rid=15b50208 HTTP/1.1\" 200 2 410.573017 \"-\" \"@list-item-updater\" prio:0";
    private final static String OTHER_BAD_LOG_LINE_ONE = "192.168.32.181 - - [14/06/2017:16:49:02 +1000] \"PUT /rest/v1.4/documents?zone=default&_rid=15b50208 HTTP/1.1\" 200 2 210.573017 \"-\" \"@list-item-updater\" prio:0";
    private final static String OTHER_BAD_LOG_LINE_TWO = "192.168.32.181 - - [14/06/2017:16:50:02 +1000] \"PUT /rest/v1.4/documents?zone=default&_rid=15b50208 HTTP/1.1\" 200 2 130.573017 \"-\" \"@list-item-updater\" prio:0";

    protected final static float EPS = 1e-6f;
    protected final static LogEntry FIRST_GOOD_LOG_ENTRY = new LogEntry(FIRST_LOG_LINE);
    protected final static LogEntry BAD_LOG_ENTRY = new LogEntry(BAD_LOG_LINE);
    protected final static LogEntry OTHER_BAD_LOG_ENTRY_ONE = new LogEntry(OTHER_BAD_LOG_LINE_ONE);
    protected final static LogEntry OTHER_BAD_LOG_ENTRY_TWO = new LogEntry(OTHER_BAD_LOG_LINE_TWO);

    protected ByteArrayOutputStream testCaseOutputStream = new ByteArrayOutputStream();


    @Before
    public void setUp() {
        AppConfiguration.setMinAvailabilityRatio(MIN_AVAILABILITY_RATIO);
        AppConfiguration.setMaxResponseTime(MAX_RESPONSE_TIME);
    }

}
