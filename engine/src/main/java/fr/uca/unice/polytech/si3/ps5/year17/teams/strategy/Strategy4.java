package fr.uca.unice.polytech.si3.ps5.year17.teams.strategy;

import fr.uca.unice.polytech.si3.ps5.year17.teams.engine.Cache;
import fr.uca.unice.polytech.si3.ps5.year17.teams.engine.EndPoint;
import fr.uca.unice.polytech.si3.ps5.year17.teams.engine.Network;
import fr.uca.unice.polytech.si3.ps5.year17.teams.engine.Video;
import fr.uca.unice.polytech.si3.ps5.year17.teams.exception.NotEnoughPlaceException;

import java.util.Deque;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 * Stratégie de niveau 4/6
 * La stratégie va mettre les vidéos les plus demandés de chaque endpoint dans le cache
 * où le délai de latence est le plus faible (il va s'arrêter dés qu'il essaye encore
 * d'ajouter une vidéo dans le cache proche mais qu'il n'a plus la place de la faire)
 */
public class Strategy4 implements Strategy{

    @Override
    public void execute(Network network) {
        List<EndPoint> endpoints = network.getEndpoints();
        for (EndPoint endpoint : endpoints){
            Deque<Video> stack = endpoint.mostRequestedVideoStack();
            Cache cache = endpoint.getMostOptimizedCache();
            int nextvideosize = stack.peek().getSize();
            while (cache.getSizeLeft() > nextvideosize){
                try {
                    cache.addVideo(stack.pop());
                } catch (NotEnoughPlaceException e) {
                    e.printStackTrace();
                }
                nextvideosize = stack.peek().getSize();
            }
        }
    }
}
