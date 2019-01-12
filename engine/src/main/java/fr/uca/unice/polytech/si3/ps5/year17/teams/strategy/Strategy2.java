package fr.uca.unice.polytech.si3.ps5.year17.teams.strategy;

import fr.uca.unice.polytech.si3.ps5.year17.teams.engine.Cache;
import fr.uca.unice.polytech.si3.ps5.year17.teams.engine.EndPoint;
import fr.uca.unice.polytech.si3.ps5.year17.teams.engine.Network;
import fr.uca.unice.polytech.si3.ps5.year17.teams.engine.Video;
import fr.uca.unice.polytech.si3.ps5.year17.teams.exception.NotEnoughPlaceException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Stratégie de niveau 2/6
 * La stratégie va mettre dans tout les caches qui ont suffisanment de place
 * la vidéo la plus demandé de tout les endpoints.
 */
public class Strategy2 implements Strategy {

    /**
     * Execute la stratégie
     * @param network le réseau où la stratégie est appliqué
     */
    public void execute(Network network){
        Video video = network.getMostRequestedVideo();
        List<Cache> caches  = network.getCaches(video.getSize());
        for (Cache cache : caches){
            try {
                cache.addVideo(video);
            } catch (NotEnoughPlaceException e) {
                e.printStackTrace();
            }
        }
    }

}
