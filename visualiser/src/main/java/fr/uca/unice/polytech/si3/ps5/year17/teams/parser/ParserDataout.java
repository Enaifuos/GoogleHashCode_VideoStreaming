package fr.uca.unice.polytech.si3.ps5.year17.teams.parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParserDataout extends Parser{
    private final Map<Integer,List<Integer>> caches;
    private int cachesused;

    private final String filename;

    public ParserDataout (String filename){
        this.filename = filename;
        this.caches = new HashMap<>();
    }

    public void execute() throws IOException{
        FileReader reader = new FileReader(filename);
        BufferedReader buffer = new BufferedReader(reader);
        String[] datatype1 = nextLine(buffer);
        this.cachesused = Integer.parseInt(datatype1[0]);
        for (int i=0;i<cachesused;i++){
            datatype1 = nextLine(buffer);
            List<Integer> videos = new ArrayList<>();
            for (int j = 1 ; j < datatype1.length ; j++){
                videos.add(Integer.parseInt(datatype1[j]));
            }
            this.caches.put(i,videos);
        }
    }

    public Map<Integer, List<Integer>> getCaches() {
        return caches;
    }

    public int getCachesused() {
        return cachesused;
    }
}
