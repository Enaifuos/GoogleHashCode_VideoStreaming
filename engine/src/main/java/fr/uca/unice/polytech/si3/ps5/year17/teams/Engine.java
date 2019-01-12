package fr.uca.unice.polytech.si3.ps5.year17.teams;

import fr.uca.unice.polytech.si3.ps5.year17.teams.engine.Cache;
import fr.uca.unice.polytech.si3.ps5.year17.teams.engine.EndPoint;
import fr.uca.unice.polytech.si3.ps5.year17.teams.engine.Network;
import fr.uca.unice.polytech.si3.ps5.year17.teams.engine.Video;
import fr.uca.unice.polytech.si3.ps5.year17.teams.exception.NetworkObjectException;
import fr.uca.unice.polytech.si3.ps5.year17.teams.strategy.Strategy;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Setup;

import java.io.IOException;
import java.util.*;

public class Engine {
    private Network network;

    public void setNetwork(String filename) {
        try {
            Parser parser = new Parser(filename);
            parser.execute();
            Factory factory = new Factory();
            this.network = new Network();
            network.setCaches(factory.createCaches(parser.getCachessize()));
            network.setDatacenter(factory.createVideos(parser.getVideossize()));
            network.setEndpoints(factory.createEndpoint(parser.getDelaycache(),parser.getRequests(),parser.getDelaydata(),network.getDatacenter(),network.getCaches()));
        }
        catch (IOException e){
            System.out.println("Fichier non trouvé");
        }

    }

    public void executeStrategy(Strategy strategy){
        strategy.execute(this.network);
    }

    public List<String> collectData(){
        List<String> dataout = new ArrayList<>();
        List<Cache> caches = network.getCaches();
        for (Cache cache : caches){
            if (!cache.isEmpty()){
                int size = cache.getAllVideos().size();
                List<Video> videolist = cache.getAllVideos();
                StringBuilder cachestr = new StringBuilder(cache.getID() + " ");
                for (int i = 0 ; i < size -1 ; i++){
                    cachestr.append(videolist.get(i).getId()).append(" ");
                }
                cachestr.append(videolist.get(size - 1).getId()).append("\n");
                dataout.add(cachestr.toString());
            }
        }
        return dataout;
    }

    public Network getNetwork() {
        return network;
    }

    public List<String> collectScore() throws NetworkObjectException {
        List<String> scoreout = new ArrayList<>();
        double score = 0;
        int nbtotalrequest = network.getNbTotalRequests();
        List<EndPoint> endpoints = network.getEndpoints();
        for (EndPoint endpoint : endpoints){
            int delaytodataserver = endpoint.getDelayData();
            Map<Video,Integer> requests = endpoint.getNbRequests();
            for (Map.Entry<Video,Integer> entry : requests.entrySet()){
                Video video   = entry.getKey();
                int nbrequest = entry.getValue();
                Cache optimumcache = endpoint.getMostOptimizedCacheWithVideo(video);
                int delaycache = (optimumcache == null ) ? delaytodataserver : endpoint.getDelayCache(optimumcache);
                score += (nbrequest * (delaytodataserver - minimum(delaycache,delaytodataserver)));
            }
        }
        score /= nbtotalrequest;
        score *= 1000;
        scoreout.add(Integer.toString((int) score));
        return scoreout;
    }

    private int minimum(int delaycache,int delaydataserver){
        return  (delaycache < delaydataserver) ?  delaycache : delaydataserver;
    }
}
