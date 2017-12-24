package com.per.project;

import com.per.project.calc.CalculationService;
import com.per.project.calc.CalculationServiceBean;
import org.apache.commons.cli.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    static CalculationService calculationService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        //TODO make --help
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
            System.out.println(e.getMessage());
            helpFormatter.printHelp("log-reader", options);

            System.exit(1);
            return;
        }

        Float minValidAvailabilityPercentage = Float.parseFloat(cmd.getOptionValue("availability"));
        Integer maxValidResponseTime = Integer.parseInt(cmd.getOptionValue("time"));

        calculationService = getCalculationService(minValidAvailabilityPercentage, maxValidResponseTime);

        try {
            System.out.println("start calculation");
            calculationService.startCalculating();
            System.out.println("end calculation");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    static CalculationService getCalculationService(Float minValidAvailabilityPercentage, Integer maxValidResponseTime) {
        return new CalculationServiceBean(minValidAvailabilityPercentage, maxValidResponseTime);
    }

}
