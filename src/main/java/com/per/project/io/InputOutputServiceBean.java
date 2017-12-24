package com.per.project.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

//TODO bean
public class InputOutputServiceBean implements InputOutputService {

    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));


    @Override
    public String getLine() throws Exception {
        return reader.readLine();
    }

    @Override
    public void putLine(String line) throws Exception {
        writer.write(line);
        writer.newLine();
    }
}
