package fr.uca.unice.polytech.si3.ps5.year17.teams.engine;

import fr.uca.unice.polytech.si3.ps5.year17.teams.exception.NetworkObjectException;

import java.util.*;

/**
 * Object EndPoint représentant un endpoint du réseau;
 */
public class EndPoint implements Comparable<EndPoint> {

    /*	attributs	*/
    private int id; /* son id */
    private int delayData; /* délai par rapport à la data center */
    private Map<Cache,Integer> delayCache; /*délai par rapport à tout les caches (-1 si non connecté */
    private Map<Video,Integer> nbRequests; /*nombres de requêtes pour chaque vidéo */

    /**
     * Constructeur d'un endpoint avec paramatre
     */
    public EndPoint(int id, int delayData, Map<Cache,Integer> delayCache, Map<Video,Integer> nbRequests ) {
        this.id = id;
        this.delayData = delayData;
        this.delayCache = delayCache;
        this.nbRequests = nbRequests;
    }

    /**
     * Constructeur d'un endpoint
     */
    public EndPoint(){
        this.delayCache = new HashMap<>();
        this.nbRequests = new HashMap<>();
    }

    /**
     * Setter des délais entre l'endpoints et les différents caches
     * @param delayCache le délai
     */
    public void setDelayCache(Map<Cache,Integer> delayCache){
        this.delayCache = delayCache;
    }

    /**
     * Setter du nombre de requête par video
     * @param requests la map associé.
     */
    public void setRequests(Map<Video,Integer> requests){
        this.nbRequests = requests;
    }

    /**
     * Setter de l'id du endpoints
     * @param id l'identifiant
     */
    public void setId(int id){
        this.id = id;
    }

    /**
     * Setter du délai entre l'endpoint et la dataBase
     * @param delay le délai
     *
     */
    public void setDelayData(int delay){
        this.delayData = delay;
    }
    /**
     * Fonction qui retourne le delai entre ce EndPoint et le cache server passe
     * en parametre
     * @param cache : le cache server pour lequel on calcule le delai avec ce EndPoint
     * @return Integer : le delai entre ce EndPoint et cache passe en parametre
     */
    public Integer getDelayCache(Cache cache) throws NetworkObjectException {
        Optional<Integer> optional = Optional.ofNullable(delayCache.get(cache));
        if (optional.isPresent()) return delayCache.get(cache);
        throw new NetworkObjectException("Le cache :"+cache.toString()+", n'existe pas");
    }

    /**
     * Fonction qui retourne le nombre de requetes pour la video passee en parametre
     * @param video : la video dont on cherche le nombre de requetes
     * @return Integer : le nombre de requetes pour la video passee en parametre
     */
    public Integer getNbRequests(Video video) {
        return nbRequests.get(video);
    }

    /**
     * Fonction qui retourne le cache le plus proche à ce EndPoint
     * @return le cache le plus proche à ce EndPoint
     */
    public Cache theNearestCache() {
        int time = Integer.MAX_VALUE;
        Cache cache = null;
        for (Map.Entry<Cache,Integer> entry : delayCache.entrySet()){
            int cachetime = entry.getValue();
            if (cachetime<time){
                time = cachetime;
                cache = entry.getKey();
            }
        }
        return cache;
    }

    /**
     * Getter du délai entre l'endpoint et la database
     * @return le délai
     */
    public int getDelayData() {
        return delayData;
    }

    /**
     * Getter du nombre de requête par video dans l'endpoint
     * @return les requêtes par video
     */
    public Map<Video, Integer> getNbRequests() {
        return nbRequests;
    }

    /**
     * Fonction pour ajouter un cache serveur
     * @param cache : l'id du cache à ajouter
     * @param delay : la latence du cache avec ce EndPoint
     */
    public void addCache(Cache cache, Integer delay) {
        this.delayCache.put(cache, delay);
    }

    /**
     * Donne le délai du cache avec l'id du cache
     * @param id l'ID du cache
     * @return le délai (int),-1 si le cache n'existe pas.
     */
    public int getDelayCache(int id) {

        for (Map.Entry<Cache,Integer> entry : delayCache.entrySet()){
            if (entry.getKey().getID() == id) return entry.getValue();
        }
        return -1;
    }

    /**
     * Donne le nombre de requête pour cette vidéo
     * @param id l'ID de la vidéo
     * @return le nombre de requête (int)
     */
    public int getNbRequestVideo(int id) {

        for (Map.Entry<Video,Integer> entry : nbRequests.entrySet()){
            if (entry.getKey().getId() == id) return entry.getValue();
        }
        return -1;
    }

    /**
     * Recupere la liste des vidéo demander
     * @return la liste des vidéo
     */
    private List<Video> getVideoRequested(){
        List<Video> videos  = new ArrayList<>();
        for (Map.Entry<Video,Integer> entry : nbRequests.entrySet()){
            videos.add(entry.getKey());
        }
        return videos;
    }

    /**
     * Recupere la liste des videos dans l'ordre des videos les plus demander
     * @return la pile de video dans l'ordre
     */
    public Deque<Video> mostRequestedVideoStack(){
        return mostRequestedVideoStack(new ArrayList<>());
    }

    /**
     * Détermine le cache avec le plus petit délai
     * @return le cache
     */
    public Cache getMostOptimizedCache() {
        int delay = Integer.MAX_VALUE;
        Cache cache = null;
        for (Map.Entry<Cache,Integer> entry : delayCache.entrySet()){
            int cachedelay = entry.getValue();
            if (cachedelay < delay ){
                delay = cachedelay;
                cache = entry.getKey();
            }
        }
        return cache;
    }

    /**
     * Fonction qui retourne une queue de caches par ordre de latence avec ce EndPoint
     * @return Une queue de caches
     * @throws NetworkObjectException Exception du network
     */
    public Queue<Cache> getMostOptimizedCacheInQueue() throws NetworkObjectException {
        Queue<Cache> cachesInOrder = new LinkedList<>();
        List<Cache> allCaches =  this.getAccessibleCaches();

        while ( ! allCaches.isEmpty() ) {
            Cache bestCache = allCaches.get(0);
            for ( Cache c : allCaches ) {
                if ( this.getDelayCache(c) < this.getDelayCache(bestCache) ) {
                    bestCache = c;
                }
            }
            cachesInOrder.add(bestCache);
            allCaches.remove(bestCache);
        }

        return cachesInOrder;
    }


    /**
     * Fonction qui retourne le meilleur cache pouvant stocker la video
     * @param videoToAdd la video a ajouter
     * @return le cache
     * @throws NetworkObjectException Exception du network
     */
    public Cache getMostOptimizedCache(Video videoToAdd) throws NetworkObjectException {
        Cache theCache = null;
        Queue<Cache> cachesSort = this.getMostOptimizedCacheInQueue();  // Tous les caches accessibles ordonne par latence
        Cache cacheResult =  cachesSort.peek(); // Le meilleur cache

        while ( cacheResult.getSizeLeft() < videoToAdd.getSize() && !cachesSort.isEmpty() ) {
            cacheResult = cachesSort.poll();
        }
        if ( cacheResult.getSizeLeft() > videoToAdd.getSize() ) {
            theCache = cacheResult;
        }
        return theCache;
    }




    /**
     * Détermine le cache avec le plus petit délai selon la video
     * @param video la video
     * @return le cache
     */
    public Cache getMostOptimizedCacheWithVideo(Video video) {
        int delay = Integer.MAX_VALUE;
        Cache cache = null;
        for (Map.Entry<Cache,Integer> entry : delayCache.entrySet()){
            int cachedelay = entry.getValue();
            Cache currentcache = entry.getKey();
            if (cachedelay < delay && currentcache.containsVideo(video)){
                delay = cachedelay;
                cache = currentcache;
            }
        }
        return cache;
    }

    /**
     * Détermine la video la plus demander
     * @return la video
     */
    public Video getMostRequestedVideo() {
        int requested = 0;
        Video video = null;
        for (Map.Entry<Video,Integer> entry : nbRequests.entrySet()){
            int request = entry.getValue();
            Video currentvideo = entry.getKey();
            if (request > requested){
                requested = request;
                video = currentvideo;
            }
        }
        return video;
    }

    public List<Cache> getAccessibleCaches() {
        List<Cache> accessiblecaches = new ArrayList<>();
        for(Map.Entry<Cache,Integer> entry : delayCache.entrySet()){
            if (entry.getValue()!=-1){
                accessiblecaches.add(entry.getKey());
            }
        }
        return accessiblecaches;
    }


    private int getTotalNbRequests() {
        int nb = 0;
        for (Map.Entry<Video,Integer> entry : nbRequests.entrySet()){
            nb+=entry.getValue();
        }
        return nb;
    }

    private Deque<Video> mostRequestedVideoStack(List<Video> videosbanned) {
        Deque<Video> stack = new ArrayDeque<>();
        Deque<Video> tmpinverstack = new ArrayDeque<>();
        List<Video> list = getVideoRequested();
        while (!list.isEmpty()){
            int nbrequest = 0;
            Video video = null;
            for (Map.Entry<Video,Integer> entry : nbRequests.entrySet()){
                int nbrequestvideo = entry.getValue();
                Video vid = entry.getKey();
                if (nbrequestvideo> nbrequest && list.contains(entry.getKey()) && !videosbanned.contains(vid)){
                    nbrequest = nbrequestvideo;
                    video =  vid;
                }
            }
            if (video == null){
                break;
            }
            tmpinverstack.push(video);
            list.remove(video);
        }
        while (!tmpinverstack.isEmpty()){
            stack.add(tmpinverstack.pop());
        }
        return stack;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EndPoint endPoint = (EndPoint) o;
        return id == endPoint.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override

    public int compareTo(EndPoint o) {
        return this.getTotalNbRequests() - o.getTotalNbRequests();
    }

    /**
     *  Fonction qui calcule le rapport nombre de requetes / taille d'une video
     *  retourne 0 si une video n'a pas de taille ( taille = 0 )
     */
    public double ratio(Video v) {
        if ( v.getSize() > 0 ) return ((double)this.getNbRequests(v)) / v.getSize();
        return 0;
    }

    /**
     * Fonction qui retourne une queue de videos par ordre du rapport nombre des requetes / taille
     */
    public Deque<Video> getBestVideosWithRatio() {
        Deque<Video> sortedVideos = new ArrayDeque<>();
        Deque<Video> desortedVideos = new ArrayDeque<>();
        List<Video> allVideos = this.getVideoRequested();

        while ( ! allVideos.isEmpty() ) {
            Video bestVideo = allVideos.get(0);
            double bestVideoRatio = ratio(bestVideo);

            for ( Video v : allVideos ) {
                if ( ratio(v) > bestVideoRatio ) {
                    bestVideo = v;
                    bestVideoRatio = ratio(v);
                }
            }
            desortedVideos.push(bestVideo);
            allVideos.remove(bestVideo);
        }

        while (!desortedVideos.isEmpty())
            sortedVideos.push(desortedVideos.pop());
        return sortedVideos;
    }
}
