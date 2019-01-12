package fr.uca.unice.polytech.si3.ps5.year17.teams;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataCollector {
    private Map<Integer,Integer> videosize;
    private int nbcache;
    private int cachesize;
    private int nbvideos;
    private List<Map<Integer,List<Integer>>> caches;
    private int[] cachesused;
    private Map<String,Double> time;
    private int[] stratScore;


    public DataCollector(){
        caches = new ArrayList<>();
    }

    public Map<String, Double> getTime() {
        return time;
    }

    public void setTime(Map<String, Double> time) {
        this.time = time;
    }

    public Map<Integer, Integer> getVideosize() {
        return videosize;
    }

    public void setVideosize(Map<Integer, Integer> videosize) {
        this.videosize = videosize;
    }

    public int getNbcache() {
        return nbcache;
    }

    public void setNbcache(int nbcache) {
        this.nbcache = nbcache;
    }

    public int getCachesize() {
        return cachesize;
    }

    public void setCachesize(int cachesize) {
        this.cachesize = cachesize;
    }

    public int getNbvideos() {
        return nbvideos;
    }

    public void setNbvideos(int nbvideos) {
        this.nbvideos = nbvideos;
    }

    public void setCaches(Map<Integer, List<Integer>> caches) {
        this.caches.add(caches);
    }

    public int getCachesused(int i) {
        return cachesused[i];
    }

    public void setCachesused(int cachesused,int pos) {
        this.cachesused[pos] = cachesused;
    }

    public Map<Integer,List<Integer>> getCaches(int i) {
        return caches.get(i);
    }

    public int[] getStratScores() { return this.stratScore; }

    public void setStratScores(int[] stratScore) { this.stratScore = stratScore; }

    public void setLength(int length) {
        this.cachesused = new int[length];
    }
}
