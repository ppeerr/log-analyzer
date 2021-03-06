package com.per.project.analysis;

import com.per.project.io.InputOutputService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.per.project.analysis.AnalysisPeriod.EMPTY_ANALYSIS_PERIOD;

public class AnalysisService {

    private static final Logger log = LoggerFactory.getLogger(AnalysisService.class);

    public void analyze() throws Exception {
        log.debug("start analysis");

        try (InputOutputService ioService = getInputOutputService()) {
            LogEntry firstLogEntry = ioService.read();
            if (firstLogEntry == null) {
                log.debug("couldn't read any log entry");
                return;
            }
            AnalysisPeriod globalPeriod = new AnalysisPeriod(firstLogEntry);
            AnalysisPeriod currentBadPeriod = EMPTY_ANALYSIS_PERIOD;

            for (LogEntry currentLogEntry = ioService.read(); currentLogEntry != null; currentLogEntry = ioService.read()) {
                globalPeriod.analyze(currentLogEntry);

                if (globalPeriod.isBadRatioNow()) {
                    if (currentBadPeriod.isEmpty()) {
                        currentBadPeriod = new AnalysisPeriod(currentLogEntry);
                        log.debug("bad period started at {}", currentBadPeriod.getStartDate());
                    } else {
                        currentBadPeriod.analyze(currentLogEntry);
                    }
                } else {
                    if (!currentBadPeriod.isEmpty()) {
                        endAndWriteBadPeriod(currentBadPeriod, ioService);
                        currentBadPeriod = EMPTY_ANALYSIS_PERIOD;
                    }
                }
            }
            if (!currentBadPeriod.isEmpty()) {
                endAndWriteBadPeriod(currentBadPeriod, ioService);
            }

            log.debug("global availability ratio = {}", globalPeriod.getAvailabilityRatio());
        }

        log.debug("end analysis");
    }

    protected InputOutputService getInputOutputService() {
        return new InputOutputService();
    }

    private void endAndWriteBadPeriod(AnalysisPeriod period, InputOutputService ioService) throws Exception {
        period.end();
        log.debug("Bad period ended at {}", period.getEndDate());

        ioService.write(period);
    }

}
