package fr.uca.unice.polytech.si3.ps5.year17.teams.strategy;

import fr.uca.unice.polytech.si3.ps5.year17.teams.engine.Cache;
import fr.uca.unice.polytech.si3.ps5.year17.teams.engine.EndPoint;
import fr.uca.unice.polytech.si3.ps5.year17.teams.engine.Network;
import fr.uca.unice.polytech.si3.ps5.year17.teams.engine.Video;
import fr.uca.unice.polytech.si3.ps5.year17.teams.exception.NetworkObjectException;
import fr.uca.unice.polytech.si3.ps5.year17.teams.exception.NotEnoughPlaceException;

import java.util.Deque;
import java.util.List;
import java.util.Stack;

/**
 *  Stratégie 5/6
 *  La stratégie va mettre les videos ( par ordre de demandes ) dans le meilleur serveur possible
 *  oû le delai de latence est faible tant qu'il peut les stocker
 *  Elle va utiliser le serveur suivant ( le meilleur parmis les serveurs restants ) pour stocker les vidéos restantes
 *  tant qu'il y a une vidéo à stocker
 */
public class Strategy5 implements Strategy{

    @Override
    public void execute(Network network)  {
        List<EndPoint> endPoints = network.getEndpoints();
        for ( EndPoint e : endPoints ) {

            Deque<Video> stackVideos = e.mostRequestedVideoStack();
            Video nextVideo = stackVideos.peek();
            int nextVideoSize = nextVideo.getSize();
            try {
                while (e.getMostOptimizedCache(nextVideo) != null && !stackVideos.isEmpty()) {

                    Cache cache = e.getMostOptimizedCache(nextVideo); // C'est une fonction qui retourne le meilleur cache pouvant stocker la video suivante
                    while (cache.getSizeLeft() > nextVideoSize ) {

                        try {
                            cache.addVideo(stackVideos.pop());
                        } catch (NotEnoughPlaceException exception) {
                            exception.printStackTrace();
                        }
                        if (stackVideos.isEmpty()){
                            break;
                        }
                        nextVideo = stackVideos.peek();
                        nextVideoSize = nextVideo.getSize();
                    }
                }
            }catch(NetworkObjectException ex){
                ex.printStackTrace();
            }

        }
    }
}
