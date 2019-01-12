package fr.uca.unice.polytech.si3.ps5.year17.teams;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Parser {
    private String filename;
    private int[] videossize;
    private int[] cachessize;
    private int[][] delaycache;
    private int[][] requests;
    private int[] delaydata;

    public Parser(String filename){
        this.filename = filename;
    }

    private void initialize(int nbvideos,int nbcaches,int nbendpoints){
        this.videossize = new int[nbvideos];
        this.cachessize = new int[nbcaches];
        this.delaycache = new int[nbcaches][nbendpoints];
        for (int i = 0 ; i < nbcaches ; i++){
            for (int j = 0 ; j < nbendpoints ; j++){
                delaycache[i][j] = -1;
            }
        }
        this.requests   = new int[nbvideos][nbendpoints];
        this.delaydata  = new int[nbendpoints];
    }

    private void initializeData(String[] line1){
        int nbofvideos  = Integer.parseInt(line1[0]);
        int nbendpoints = Integer.parseInt(line1[1]);
        int nbcaches    = Integer.parseInt(line1[3]);
        initialize(nbofvideos,nbcaches,nbendpoints);
    }

    private void configureCaches(int cachesize){
        for (int i = 0 ; i < cachessize.length ; i++){
            cachessize[i] = cachesize;
        }
    }

    private void configureVideosSize(String[] line2){
        for (int i = 0 ; i < videossize.length ; i++){
            videossize[i] = Integer.parseInt(line2[i]);
        }
    }


    private void configureRequests(String[] line){
        int idvideo = Integer.parseInt(line[0]);
        int idendpoint = Integer.parseInt(line[1]);
        int nbrequests = Integer.parseInt(line[2]);
        requests[idvideo][idendpoint] = nbrequests;

    }

    public void execute() throws IOException{
        FileReader reader = new FileReader(filename);
        BufferedReader buffer = new BufferedReader(reader);
        String[] datatype1 = nextLine(buffer);
        int nbrequests = Integer.parseInt(datatype1[2]);
        int nbofendpoint = Integer.parseInt(datatype1[1]);
        initializeData(datatype1);
        configureCaches(Integer.parseInt(datatype1[4]));
        datatype1 = nextLine(buffer);
        configureVideosSize(datatype1);
        for (int i = 0 ; i < nbofendpoint ; i++){
            datatype1 = nextLine(buffer);
            int nbcachesconnected = Integer.parseInt(datatype1[1]);
            delaydata[i] = Integer.parseInt(datatype1[0]);
            for (int j = 0 ; j < nbcachesconnected ; j++ ){
                datatype1 = nextLine(buffer);
                delaycache[j][i] = delaycache[Integer.parseInt(datatype1[0])][i] = Integer.parseInt(datatype1[1]);
            }
        }
        for (int i = 0;i < nbrequests;i++){
            datatype1 = nextLine(buffer);
            configureRequests(datatype1);
        }
    }

    private String[] nextLine(BufferedReader buffer) throws IOException{
        String line = buffer.readLine();
        return line.split(" ");
    }

    public int[] getVideossize() {
        return videossize;
    }

    public int[] getCachessize() {
        return cachessize;
    }

    public int[][] getDelaycache() {
        return delaycache;
    }

    public int[][] getRequests() {
        return requests;
    }

    public int[] getDelaydata() {
        return delaydata;
    }
}
