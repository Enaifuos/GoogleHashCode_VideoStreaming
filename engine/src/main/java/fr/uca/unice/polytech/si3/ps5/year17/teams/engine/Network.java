package fr.uca.unice.polytech.si3.ps5.year17.teams.engine;

import fr.uca.unice.polytech.si3.ps5.year17.teams.exception.NetworkObjectException;
import fr.uca.unice.polytech.si3.ps5.year17.teams.exception.NotEnoughPlaceException;

import java.util.*;

/**
 * Objet Network représentant le réseau
 */
public class Network {
    private List<Cache> caches;
    private List<Video> datacenter;
    private List<EndPoint> endpoints;

    /**
     * Constructeur du network avec parametre
     */
    public Network(List<Video> datacenter,List<EndPoint> endpoints,List<Cache> caches){
        this.datacenter = datacenter;
        this.endpoints = endpoints;
        this.caches = caches;
    }

    /**
     * Constructeur du network
     */
    public Network(){
        this.datacenter=new ArrayList<>();
        this.caches=new ArrayList<>();
        this.endpoints=new ArrayList<>();
    }


    /**
     * Setter de la liste des caches
     * @param caches les caches
     */
    public void setCaches(List<Cache> caches) {
        this.caches = caches;
    }

    /**
     * Setter de la liste des vidéos
     * @param datacenter les vidéos
     */
    public void setDatacenter(List<Video> datacenter) {
        this.datacenter = datacenter;
    }

    /**
     * Setter de la list des endpoints
     * @param endpoints les endpoints
     */
    public void setEndpoints(List<EndPoint> endpoints) {
        this.endpoints = endpoints;
    }

    /**
     * Rajoute une video dans un cache
     * @param videoid l'identifiant de la video
     * @param cacheid l'identifiant du cache
     * @throws NotEnoughPlaceException si la tailla de la video est plus grande que l'espace libre du cache
     */
    public void addVideoToCache(int videoid,int cacheid)throws NotEnoughPlaceException {
        Video video = datacenter.get(videoid);
        Cache cache = caches.get(cacheid);
        cache.addVideo(video);
    }

    /**
     * Getter de la liste des caches
     * @return la liste des caches
     */
    public List<Cache> getCaches() {
        return caches;
    }

    public List<Cache> getCaches(int necessarysize){
        Collections.sort(caches);
        for (int i = 0 ; i < caches.size() ; i++ ){
            if (caches.get(i).getSizeLeft() > necessarysize){
                return caches.subList(i,caches.size());
            }
        }
        return Collections.emptyList();
    }

    /**
     * Getter de la liste des vidéos
     * @return la liste des vidéos
     */
    public List<Video> getDatacenter() {
        return datacenter;
    }

    /**
     * Getter de la liste des endpoints
     * @return la liste des endpoints
     */
    public List<EndPoint> getEndpoints() {
        return endpoints;
    }

    /**
     * Calcul le nombre totale de requête pour toutes les vidéos
     * @return la somme des requête
     */
    public int getNbTotalRequests() {
        int nb = 0;
        for (EndPoint endPoint : endpoints){
            Map<Video,Integer> requests = endPoint.getNbRequests();
            for (Map.Entry entry : requests.entrySet()){
                nb+=(int) entry.getValue();
            }
        }
        return nb;
    }

    /**
     * Retourne la description du réseau
     * @return la description
     */
    @Override
    public String toString(){
        return "Réseau principal, la base de données contient "
                +this.getDatacenter().size()
                +" vidéos différentes.";
    }

    public Deque<EndPoint> getBestEndpointsStack() {
        Deque<EndPoint> bestendpoints = new ArrayDeque<>();
        List<EndPoint> list = getEndpoints();
        Collections.sort(list);
        int size = list.size();
        for (int i = 0 ; i < size ; i++){
            bestendpoints.push(list.get(0));
        }
        return bestendpoints;
    }

    public Deque<Cache> getBestCachesNotCommon(EndPoint endPoint) {
        Deque<Cache> bestcachesnotcommon = new ArrayDeque<>();
        Set<Cache> commoncaches = new HashSet<>();
        for (EndPoint endpoint : endpoints){
            commoncaches.add(endpoint.getMostOptimizedCache());
        }
        List<Cache> list = new ArrayList<>(endPoint.getAccessibleCaches());
        for (Cache cache : commoncaches){
            if (list.contains(cache) && cache.getSizeLeft()>(cache.getTotalSize()/3)) list.remove(cache);
        }
        while (!list.isEmpty()){
            Cache cache = getBestCache(list,endPoint);
            bestcachesnotcommon.push(cache);
            list.remove(bestcachesnotcommon.peek());
        }
        return bestcachesnotcommon;
    }

    private Cache getBestCache(List<Cache> list,EndPoint endPoint) {
        int delaymin = Integer.MAX_VALUE;
        Cache bestcache = null;
        for (Cache cache : list){
            try {
                int delay = endPoint.getDelayCache(cache);
                if (delay<delaymin){
                    bestcache = cache;
                    delaymin = delay;
                }
            } catch (NetworkObjectException e) {
                e.printStackTrace();
            }
        }
        return bestcache;
    }

    public double getAmeliorateScore(Video video,Cache cache){
        double scorewin = 0;
        for (EndPoint endpoint : endpoints){
            double scorevideo = getScoreVideo(endpoint,video);
            double scoreafter = calculatescore(cache,endpoint,video);
            scorewin+=scoreafter-scorevideo;
        }
        return scorewin;
    }

    private double getScoreVideo(EndPoint e,Video v) {
        Cache cache = e.getMostOptimizedCache();
        return calculatescore(cache,e,v);
    }

    private double calculatescore(Cache cache,EndPoint e,Video video)  {
        int delaydata = e.getDelayData();
        int delaycache = 0;
        try {
            delaycache = e.getDelayCache(cache);
        } catch (NetworkObjectException e1) {
            e1.printStackTrace();
        }
        int nbrequest = e.getNbRequests(video);
        double prescore = (nbrequest * (delaydata - minimum(delaycache,delaydata)));

        prescore*=1000;
        prescore/=this.getNbTotalRequests();
        return prescore;
    }
    private int minimum(int delaycache,int delaydataserver){
        return  (delaycache < delaydataserver) ?  delaycache : delaydataserver;
    }


    public List<Video> videosbanned(int i) {
        List<Video> list = datacenter;
        Collections.sort(list);
        int sizelist = list.size();
        int maxind = (sizelist * i) /100;
        return list.subList(0,maxind);
    }

    /**
     * Donne la vidéo la plus demandé.
     * @return Video la vidéo la plus demandé
     */
    public Video getMostRequestedVideo() {
        Map<Video,Integer> totalrequests = new HashMap<>();
        for (EndPoint endpoint : endpoints){
            Map<Video,Integer> requests = endpoint.getNbRequests();
            for (Map.Entry<Video,Integer> entry : requests.entrySet()){
                Video video = entry.getKey();
                int nbrequests = entry.getValue();
                if (totalrequests.containsKey(video)){
                    totalrequests.put(video,nbrequests + totalrequests.get(video));
                }
            }
        }
        Video bestvideo = null;
        int maxnbrequest = -1;
        for (Map.Entry<Video,Integer> entry : totalrequests.entrySet()){
            Video video = entry.getKey();
            int nbrequests = entry.getValue();
            if (nbrequests>maxnbrequest){
                maxnbrequest = nbrequests;
                bestvideo = video;
            }
        }
        return bestvideo;
    }
}
