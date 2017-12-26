package com.per.project.calc;

import com.per.project.io.InputOutputService;
import com.per.project.io.InputOutputServiceBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

//TODO bean
public class CalculationServiceBean implements CalculationService {

    private static final Logger log = LoggerFactory.getLogger(CalculationServiceBean.class);

    private Float minValidAvailabilityPercentage;
    private Integer maxValidResponseTime;

    private float availabilityPercentage = 1.0f;
    private int checkedLogs = 0;
    private int goodResponses = 0;
    private InputOutputService inputOutputService;

    private String curStartTime = "";
    private String curEndTime = "";
    private int curCheckedLogs = -1;
    private int curGoodResponses = -1;

    boolean isInsideInBadPeriod = false;

    public CalculationServiceBean() {
        this(99.0F, 100);
    }

    public CalculationServiceBean(Float minValidAvailabilityPercentage, Integer maxValidResponseTime) {
        this.minValidAvailabilityPercentage = minValidAvailabilityPercentage;
        this.maxValidResponseTime = maxValidResponseTime;

        inputOutputService = new InputOutputServiceBean();
    }

    //TODO replace the 'Calculating' word with 'Обработка'
    public void startCalculating() throws Exception {
        String currentString;
        for (currentString = inputOutputService.getLine(); currentString != null; currentString = inputOutputService.getLine()) {
            checkedLogs++;

            List<String> logParts = Arrays.asList(currentString.split(" ")); //TODO regex
            Integer responseCode = Integer.parseInt(logParts.get(8));
            Float responseTime = Float.parseFloat(logParts.get(10));

            if (((responseCode >= 500) && (responseCode <= 599)) || (responseTime > maxValidResponseTime)) {

            } else {
                goodResponses++;
            }

            isInsideInBadPeriod = (getAvailabilityPercentage(goodResponses, checkedLogs) < minValidAvailabilityPercentage);

            //log.info("aR-" + checkedLogs + " = " + getAvailabilityPercentage(goodResponses, checkedLogs));

            if (isInsideInBadPeriod) {
                if (curCheckedLogs == -1) {
                    curStartTime = logParts.get(3);
                    curCheckedLogs = 1;
                    curGoodResponses = 0;
                } else {
                    curCheckedLogs++;
                }

                curEndTime = logParts.get(3);
                if ((responseCode >= 500 && responseCode <= 599) || responseTime > maxValidResponseTime) {

                } else {
                    curGoodResponses++;
                }
            } else {
                if (curCheckedLogs != -1) {
                    String outPut = curStartTime + " " + curEndTime + " " + getAvailabilityPercentage(curGoodResponses, curCheckedLogs);
                    //inputOutputService.putLine(outPut);
                    log.info(outPut);

                    curStartTime = "";
                    curEndTime = "";
                    curCheckedLogs = -1;
                    curGoodResponses = -1;
                }
            }

//            log.info("goodResponses = " + goodResponses
//                    + " isInsideInBadPeriod = " + isInsideInBadPeriod
//                    + " AvailabilityPercentage = " + getAvailabilityPercentage(goodResponses, checkedLogs)
//                    + " curStartTime = " + curStartTime
//                    + " curEndTime = " + curEndTime
//                    + " curCheckedLogs = " + curCheckedLogs
//                    + " curGoodResponses = " + curGoodResponses
//                    + " Response = " + responseCode
//                    + " responseTime = " + responseTime);
        }

        if (isInsideInBadPeriod) {
            String outPut = curStartTime + " " + curEndTime + " " + getAvailabilityPercentage(curGoodResponses, curCheckedLogs);
            //inputOutputService.putLine(outPut);
            log.info(outPut);

            curStartTime = "";
            curEndTime = "";
            curCheckedLogs = -1;
            curGoodResponses = -1;
        }

        log.info(minValidAvailabilityPercentage + "---" + maxValidResponseTime);
    }

    //TODO maybe double
    private float getAvailabilityPercentage(int good, int all) {
        return 100.0F * (float)good / (float)all;
    }


}
