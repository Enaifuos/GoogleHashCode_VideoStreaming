package fr.uca.unice.polytech.si3.ps5.year17.teams;

import fr.uca.unice.polytech.si3.ps5.year17.teams.engine.Cache;
import fr.uca.unice.polytech.si3.ps5.year17.teams.engine.EndPoint;
import fr.uca.unice.polytech.si3.ps5.year17.teams.engine.Video;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Factory {

    public Factory(){

    }

    public List<Video> createVideos(int[] videossize){
        List<Video> list = new ArrayList<>();
        for (int i=0;i<videossize.length;i++){
            list.add(new Video(i,videossize[i]));
        }
        return list;
    }

    public List<Cache> createCaches(int[] caches){
        List<Cache> list = new ArrayList<>();
        for (int i=0;i<caches.length;i++){
            list.add(new Cache(i,caches[i]));
        }
        return list;
    }

    public List<EndPoint> createEndpoint(int[][] delaycaches,int[][] requests,int[] delaydata,List<Video> videos,List<Cache> caches){
        List<EndPoint> list = new ArrayList<>();
        int nbtotal = delaycaches[0].length;
        for (int i = 0 ; i < nbtotal ; i++ ){
            EndPoint endpoint = new EndPoint();
            endpoint.setId(i);
            Map<Video,Integer> nbrequests = new HashMap<>();
            for (Video v : videos){
                nbrequests.put(v,requests[v.getId()][i]);
            }
            endpoint.setRequests(nbrequests);
            Map<Cache,Integer> delaycache = new HashMap<>();
            for (Cache cache : caches){
                delaycache.put(cache,delaycaches[cache.getID()][i]);
            }
            endpoint.setDelayCache(delaycache);
            endpoint.setDelayData(delaydata[i]);
            list.add(endpoint);

        }
        return list;
    }

    private Video getVideoWithID(int id,List<Video> videos){
        for (Video video : videos){
            if (video.getId() == id) return video;
        }
        return null;
    }

    private Cache getCacheWithID(int id,List<Cache> caches){
        for (Cache cache: caches){
            if (cache.getID() == id) return cache;
        }
        return null;
    }

}
