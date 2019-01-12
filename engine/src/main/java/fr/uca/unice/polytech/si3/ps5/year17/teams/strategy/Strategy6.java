package fr.uca.unice.polytech.si3.ps5.year17.teams.strategy;

import fr.uca.unice.polytech.si3.ps5.year17.teams.engine.Cache;
import fr.uca.unice.polytech.si3.ps5.year17.teams.engine.EndPoint;
import fr.uca.unice.polytech.si3.ps5.year17.teams.engine.Network;
import fr.uca.unice.polytech.si3.ps5.year17.teams.engine.Video;
import fr.uca.unice.polytech.si3.ps5.year17.teams.exception.NetworkObjectException;
import fr.uca.unice.polytech.si3.ps5.year17.teams.exception.NotEnoughPlaceException;

import java.util.List;
import java.util.Queue;

/**
 *  Stratégie 6/6
 *  La stratégie va mettre les videos ( par ordre du ratio nombre Requetes / taille ) dans le meilleur serveur possible
 *  oû le delai de latence est faible tant qu'il peut les stocker
 *  Elle va utiliser le serveur suivant ( le meilleur parmis les serveurs restants ) pour stocker les vidéos restantes
 *  tant qu'il y a une vidéo à stocker
 */
public class Strategy6 implements  Strategy{

    @Override
    public void execute(Network network) {
        List<EndPoint> endPoints = network.getEndpoints();
        for ( EndPoint e : endPoints ) {

            Queue<Video> sortedVideos = e.getBestVideosWithRatio();
            Video nextVideo = sortedVideos.peek();
            int nextVideoSize = nextVideo.getSize();
            try {
                while (e.getMostOptimizedCache(nextVideo) != null && !sortedVideos.isEmpty() ) {
                    Cache cache = e.getMostOptimizedCache(nextVideo);
                    while (cache.getSizeLeft() > nextVideoSize) {
                        try {
                            cache.addVideo(sortedVideos.poll());

                        } catch (NotEnoughPlaceException exception) {
                            exception.printStackTrace();
                        }
                        if (sortedVideos.isEmpty()){
                            break;
                        }
                        nextVideo = sortedVideos.peek();
                        nextVideoSize = nextVideo.getSize();
                    }
                }
            }catch (NetworkObjectException ex) {
                ex.printStackTrace();
            }
        }
    }
}
