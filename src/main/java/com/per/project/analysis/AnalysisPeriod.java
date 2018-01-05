package com.per.project.analysis;

import com.per.project.configuration.AppConfiguration;

import java.time.LocalDateTime;

public class AnalysisPeriod {

    public static final AnalysisPeriod EMPTY_ANALYSIS_PERIOD = new AnalysisPeriod();

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime currentDate;
    private Integer logsCount;
    private Integer goodResponsesCount;
    private Boolean inProgress;

    public AnalysisPeriod(LogEntry firstLogEntry) {
        this.startDate = firstLogEntry.getTimestamp();
        this.currentDate = firstLogEntry.getTimestamp();

        this.goodResponsesCount = 0;
        if (isGoodResponse(firstLogEntry)) {
            this.goodResponsesCount++;
        }

        this.logsCount = 1;
        this.inProgress = true;
    }

    public void analyze(LogEntry logEntry) {
        this.currentDate = logEntry.getTimestamp();

        if (isGoodResponse(logEntry)) {
            this.goodResponsesCount++;
        }

        this.logsCount++;
    }

    public boolean isBadRatioNow() {
        return getAvailabilityRatio() < AppConfiguration.getMinAvailabilityRatio();
    }

    public float getAvailabilityRatio() {
        return 100.0F * (float) this.goodResponsesCount / (float) this.logsCount;
    }

    public void end() {
        this.endDate = this.currentDate;
        this.inProgress = false;
    }

    public boolean isEmpty() {
        return this == EMPTY_ANALYSIS_PERIOD;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public LocalDateTime getCurrentDate() {
        return currentDate;
    }

    public Integer getLogsCount() {
        return logsCount;
    }

    public Integer getGoodResponsesCount() {
        return goodResponsesCount;
    }

    public Boolean isInProgress() {
        return inProgress;
    }

    private AnalysisPeriod() {
        this.inProgress = false;
    }

    private boolean isGoodResponse(LogEntry logEntry) {
        int responseCode = logEntry.getResponseCode();
        float responseTime = logEntry.getResponseTime();

        return ((responseCode < 500) || (responseCode > 599))
                && (responseTime <= AppConfiguration.getMaxResponseTime());
    }

    @Override
    public String toString() {
        return startDate.toString().replace("T", " ") + "\t\t" +
                endDate.toString().replace("T", " ") + "\t\t" +
                String.format("%.01f", getAvailabilityRatio());
    }
}
