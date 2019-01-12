package fr.uca.unice.polytech.si3.ps5.year17.teams.engine;

import fr.uca.unice.polytech.si3.ps5.year17.teams.exception.NotEnoughPlaceException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Object Cache représentant un cache du réseau;
 */
public class Cache implements Comparable<Cache>{
    private int totalsize;
    private int id;
    private List<Video> videos;

    /**
     * Constructeur du cache
     * @param id l'identifiant
     * @param size la taille maximum du cache
     */
    public Cache(int id,int size){
        this.id = id;
        this.totalsize = size;
        this.videos = new ArrayList<>();
    }

    /**
     * Calcul l'espace libre d'un cache
     * @return la taille de l'espace libre
     */
    public int getSizeLeft(){
        int occupedsize = 0;
        for (Video video: videos){
            occupedsize+=video.getSize();
        }
        return (this.totalsize - occupedsize);
    }

    /**
     * Ajout d'une vidéo au cache
     * @param video la video ajouté
     * @throws NotEnoughPlaceException si la tailla de la video est plus grande que l'espace libre du cache
     */
    public void addVideo(Video video) throws NotEnoughPlaceException {

        if (video.getSize()>this.getSizeLeft()){
            String sb = "Le cache :"+
                    this.toString()+
                    "ne peut pas acceuillir la vidéo :"+
                    video.toString();
            throw new NotEnoughPlaceException(sb);
        }
        videos.add(video);
    }

    /**
     * Verifie si la video se trouve dans le cache
     * @param video la video rechercher
     * @return true si la video est trouve dans le cache, false si elle ne s'y trouve pas
     */
    public boolean containsVideo(Video video) {
        return videos.contains(video);
    }

    /**
     * Verifie si le cache est vide
     * @return true si le cache est vide, false s'il n'est pas vide
     */
    public boolean isEmpty(){
        return videos.isEmpty();
    }

    /**
     * Getter de l'id du cache
     * @return l'id
     */
    public int getID() {
        return id;
    }

    /**
     * Getter de la liste des video sur le cache
     * @return la liste des video
     */
    public List<Video> getAllVideos() {
        return videos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cache cache = (Cache) o;
        return id == cache.id;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    @Override
    public String toString(){
        return "Cache d'ID "
                +this.id
                +", il a une taille initiale de "
                +this.totalsize
                +"MB."
                +" Il ne lui reste plus que "
                +getSizeLeft()

                +"MB"
                +" de libre.";
    }

    public int getTotalSize() {
        return totalsize;
    }

    @Override
    public int compareTo(Cache c) {
        return this.getSizeLeft() - c.getSizeLeft();
    }
}
