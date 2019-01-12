package fr.uca.unice.polytech.si3.ps5.year17.teams.strategy;

import fr.uca.unice.polytech.si3.ps5.year17.teams.engine.Cache;
import fr.uca.unice.polytech.si3.ps5.year17.teams.engine.EndPoint;
import fr.uca.unice.polytech.si3.ps5.year17.teams.engine.Network;
import fr.uca.unice.polytech.si3.ps5.year17.teams.engine.Video;
import fr.uca.unice.polytech.si3.ps5.year17.teams.exception.NotEnoughPlaceException;

import java.util.List;

/**
 * Stratégie de niveau 3/6
 * La stratégie va mettre la vidéo la plus demandé de chaque endpoint dans le cache
 * où le délai de latence est le plus faible (si il n'y a pas assez de place il ne
 * le fait pas).
 */
public class Strategy3 implements Strategy{

    @Override
    public void execute(Network network){
        List<EndPoint> endpoints = network.getEndpoints();
        for (EndPoint endpoint : endpoints) {
            Video video = endpoint.getMostRequestedVideo();
            Cache cache = endpoint.getMostOptimizedCache();
            if (video.getSize() < cache.getSizeLeft()) {
                try {
                    cache.addVideo(video);
                } catch (NotEnoughPlaceException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
