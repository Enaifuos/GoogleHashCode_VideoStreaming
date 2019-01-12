package fr.uca.unice.polytech.si3.ps5.year17.teams.parser;

import java.io.BufferedReader;
import java.io.IOException;

public abstract class Parser {

    public abstract void execute() throws IOException;

    String[] nextLine(BufferedReader buffer) throws IOException {
        String line = buffer.readLine();
        return line.split(" ");
    }
}
