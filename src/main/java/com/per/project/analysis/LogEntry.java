package com.per.project.analysis;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Log example
 * 192.168.32.181 - - [14/06/2017:16:47:02 +1000] "PUT /rest/v1.4/documents?zone=default&_rid=15b50208 HTTP/1.1" 200 2 107.573017 "-" "@list-item-updater" prio:0
 */
public class LogEntry {

    private final static DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy:HH:mm:ss");

    private final static String ipInfoRegex = "(?<ip>\\d+\\.\\d+\\.\\d+\\.\\d+)";
    private final static String timestampRegex = "\\[(?<timestamp>\\d{2}/\\d{2}/\\d{4}:\\d{2}:\\d{2}:\\d{2})\\s+\\+\\d+\\]";
    private final static String requestInfo = "(?<request>\".*\")";
    private final static String responseCodeRegex = "(?<code>\\d{3})";
    private final static String someDigitRegex = "(?<digit>\\d{1})";
    private final static String responseTimeRegex = "(?<time>\\d+\\.?\\d*)";

    private static Pattern logEntryPattern = Pattern.compile(ipInfoRegex + " - - " + timestampRegex + "\\s+" +
            requestInfo + "\\s+" + responseCodeRegex + "\\s+" + someDigitRegex + "\\s+" + responseTimeRegex + "\\s.*$");

    private LocalDateTime timestamp;
    private Integer responseCode;
    private Float responseTime;

    public LogEntry(String logLine) {
        Matcher matcher = logEntryPattern.matcher(logLine);

        if (matcher.find()) {
            String timestampStr = matcher.group("timestamp");
            timestamp = LocalDateTime.parse(timestampStr, DATE_FORMAT);

            String responseCodeStr = matcher.group("code");
            responseCode = Integer.parseInt(responseCodeStr);

            String responseTimeStr = matcher.group("time");
            responseTime = Float.parseFloat(responseTimeStr);
        }
    }

    public LogEntry(LocalDateTime timestamp, Integer responseCode, Float responseTime) {
        this.timestamp = timestamp;
        this.responseCode = responseCode;
        this.responseTime = responseTime;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public Float getResponseTime() {
        return responseTime;
    }
}
