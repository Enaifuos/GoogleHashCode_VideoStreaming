package fr.uca.unice.polytech.si3.ps5.year17.teams.engine;


import java.util.Objects;

/**
 * Objet Video représentant une video sur le reseau
 */
public class Video implements Comparable<Video>{
    private int id;
    private int size;

    /**
     * Constructeur d'une video
     * @param id l'identifiant
     * @param size la taille
     */
    public Video(int id,int size){
        this.id = id;
        this.size = size;
    }

    /**
     * Getter de la taille de la video
     * @return la taille
     */
    public int getSize() {
        return size;
    }

    /**
     * Getter de l'identifiant de la video
     * @return l'id
     */
    public int getId() {
        return id;
    }

    /**
     * Retourne la description de l'objet
     * @return la description
     */
    @Override
    public String toString(){
        return "Vidéo de taille : "+size+"MB et d'ID "+id+".";
    }

    @Override
    public int compareTo(Video o) {
        return this.getSize()-o.getSize();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Video video = (Video) o;
        return id == video.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}