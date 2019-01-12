package fr.uca.unice.polytech.si3.ps5.year17.teams.parser;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParserResultatjson extends Parser {

    private final Map<String,Double> strategietime;
    private final String filename;

    public ParserResultatjson (String filename){
        this.filename = filename;
        this.strategietime= new HashMap<>();
    }

    public void execute() throws IOException {
        JSONParser parser=new JSONParser();
        try {
            JSONArray jsonArray=(JSONArray) parser.parse(new FileReader(filename));

            for(Object obj: jsonArray){
                JSONObject jsonObject=(JSONObject)obj;

                String benchmark=(String) jsonObject.get("benchmark");
                String type=benchmark.substring(62,benchmark.length());
               if(type.contains("strategie"))strategieinfo(jsonObject,type);

            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void strategieinfo(JSONObject jsonObject, String type){
        JSONObject info=(JSONObject) jsonObject.get("primaryMetric");
        String mode=(String) jsonObject.get("mode");

        switch (mode){
            case "avgt":
                Double time=(Double) info.get("score");
                strategietime.put(type,time);
                break;
        }
    }



    public Map<String, Double> getStrategietime() {
        return strategietime;
    }

}
