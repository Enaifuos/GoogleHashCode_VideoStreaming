package fr.uca.unice.polytech.si3.ps5.year17.teams.parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class ParserDatain extends Parser{

    private final String filename;
    private final HashMap<Integer,Integer> videosize;
    private int nbcache;
    private int cachesize;
    private int nbvideos;

    public ParserDatain (String filename){
        this.filename = filename;
        this.videosize = new HashMap<>();

    }

    public void execute() throws IOException {
        FileReader reader = new FileReader(filename);
        BufferedReader buffer = new BufferedReader(reader);
        String[] datatype1 = nextLine(buffer);
        this.nbvideos = Integer.parseInt(datatype1[0]);
        this.nbcache = Integer.parseInt(datatype1[3]);
        this.cachesize = Integer.parseInt(datatype1[4]);
        datatype1 = nextLine(buffer);
        for (int i = 0 ; i < nbvideos ; i++){
            videosize.put(i,Integer.parseInt(datatype1[i]));
        }
    }

    public HashMap<Integer, Integer> getVideosize() {
        return videosize;
    }

    public int getNbcache() {
        return nbcache;
    }

    public int getCachesize() {
        return cachesize;
    }

    public int getNbvideos() {
        return nbvideos;
    }
}
