package fr.uca.unice.polytech.si3.ps5.year17.teams.strategy;

import fr.uca.unice.polytech.si3.ps5.year17.teams.engine.Cache;
import fr.uca.unice.polytech.si3.ps5.year17.teams.engine.Network;
import fr.uca.unice.polytech.si3.ps5.year17.teams.engine.Video;
import fr.uca.unice.polytech.si3.ps5.year17.teams.exception.NotEnoughPlaceException;


import java.util.ArrayList;
import java.util.List;

/**
 * Stratégie de niveau 1/6
 * La stratégie va prendre une vidéo aléatoire et la mettre dans un cache qui a assez de place
 * pour l'acceuillir (choisi lui aussi aléatoirement)
 * Si aucun cache n'a suffisament de place pour acceuillir la vidéo,la stratégie ne fait rien.
 */
public class Strategy1 implements Strategy{
    @Override
    public void execute(Network network) {
        List<Video> videos = network.getDatacenter();
        Video video = getAleaVideo(videos);
        List<Cache> enoughplacecaches = new ArrayList<>();
        List<Cache> allcaches = network.getCaches();
        for (Cache cache : allcaches){
            if (video.getSize()<cache.getSizeLeft()) enoughplacecaches.add(cache);
        }
        try {
            putVideoOnAleaCache(enoughplacecaches,video);
        } catch (NotEnoughPlaceException e) {
            e.printStackTrace();
        }
    }

    private Video getAleaVideo(List<Video> videos){
        int random = (int) (Math.random()*videos.size());
        return videos.get(random);
    }

    private void putVideoOnAleaCache(List<Cache> caches,Video video) throws NotEnoughPlaceException{
        int nbcacheok = caches.size();
        if (nbcacheok!=0) {
            int random = (int) (Math.random() * caches.size());
            Cache cache = caches.get(random);
            cache.addVideo(video);
        }
    }
}
