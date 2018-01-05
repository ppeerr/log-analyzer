package com.per.project;

import com.per.project.analysis.AnalysisService;
import com.per.project.configuration.AppConfiguration;
import org.apache.commons.cli.*;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    private static AnalysisService analysisService = new AnalysisService();

    public static void main(String[] args) {
        try {
            Options options = getApplicationOptions();

            CommandLineParser commandLineParser = new DefaultParser();
            HelpFormatter helpFormatter = new HelpFormatter();
            CommandLine cmd;

            try {
                cmd = commandLineParser.parse(options, args);
            } catch (ParseException e) {
                helpFormatter.printHelp("log-analyzer", options);
                System.exit(1);
                return;
            }
            initConfiguration(cmd);

            analysisService.analyze();
        } catch (Exception e) {
            log.error("Exception while log-analyzer working", e);
        }
    }

    private static Options getApplicationOptions() {
        Options options = new Options();

        Option availability = new Option("a", "availability", true,
                "minimum acceptable level of availability in percents (required)");
        availability.setRequired(true);
        options.addOption(availability);

        Option time = new Option("t", "time", true,
                "acceptable response time in milliseconds (required)");
        time.setRequired(true);
        options.addOption(time);

        Option debug = new Option("d", "debug", false,
                "turns debug mode on");
        options.addOption(debug);
        return options;
    }

    private static void initConfiguration(CommandLine cmd) {
        AppConfiguration.setMinAvailabilityRatio(Float.parseFloat(cmd.getOptionValue("availability")));
        AppConfiguration.setMaxResponseTime(Integer.parseInt(cmd.getOptionValue("time")));
        AppConfiguration.setDebugMode(cmd.hasOption("debug"));

        if (AppConfiguration.isDebugMode()) {
            LogManager.getRootLogger().setLevel(Level.DEBUG);
        }
    }
}
