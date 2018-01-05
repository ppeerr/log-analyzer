package com.per.project.io;

import com.per.project.analysis.AnalysisPeriod;
import com.per.project.analysis.LogEntry;

import java.io.*;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class InputOutputService implements AutoCloseable {

    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

    public LogEntry read() throws Exception {
        String line = reader.readLine();

        return isNotBlank(line) ? new LogEntry(line) : null;
    }

    public void write(AnalysisPeriod period) throws Exception {
        writer.write(period.toString());
        writer.newLine();

        writer.flush();
    }

    @Override
    public void close() throws Exception {
        reader.close();
        writer.close();
    }

}
