package com.per.project;

import com.per.project.calc.CalculationService;
import com.per.project.calc.CalculationServiceBean;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    private static CalculationService calculationService;

    public static void main(String[] args) {
        try {
            Options options = new Options();

            Option availability = new Option("a", "availability", true, "minimum acceptable level of availability in percent");
            availability.setRequired(true);
            options.addOption(availability);

            Option time = new Option("t", "time", true, "acceptable response time in milliseconds");
            time.setRequired(true);
            options.addOption(time);

            CommandLineParser commandLineParser = new DefaultParser();
            HelpFormatter helpFormatter = new HelpFormatter();
            CommandLine cmd;

            try {
                cmd = commandLineParser.parse(options, args);
            } catch (ParseException e) {
                helpFormatter.printHelp("log-analyzer", options); //TODO get from maven property
                System.exit(1);
                return;
            }

            Float minValidAvailabilityPercentage = Float.parseFloat(cmd.getOptionValue("availability"));
            Integer maxValidResponseTime = Integer.parseInt(cmd.getOptionValue("time"));

            calculationService = getCalculationService(minValidAvailabilityPercentage, maxValidResponseTime);


            log.info("start calculation");
            calculationService.startCalculating();
            log.info("end calculation");
        } catch (Exception e) {
            log.error("Exception while log-reader working", e);
        }
    }

    static CalculationService getCalculationService(Float minValidAvailabilityPercentage, Integer maxValidResponseTime) {
        return new CalculationServiceBean(minValidAvailabilityPercentage, maxValidResponseTime);
    }

}
