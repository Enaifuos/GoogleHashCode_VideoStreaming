package fr.uca.unice.polytech.si3.ps5.year17.teams;

import fr.uca.unice.polytech.si3.ps5.year17.teams.exception.WrongFileException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Classe qui créer les fichiers de sorties: score.out et data.out
 */
public class DataWriter {

    public enum EnumNameFile{
        SCORE,
        DATA;

        @Override
        public String toString(){
            return this.name().toLowerCase();
        }
    }

    private File data;
    private File score;

    /**
     * Constructeur
     * @param datapath le chemin pour la creation du fichier data.out
     * @param scorepath le chemin pour al creation du fichier score.out
     */
    DataWriter(String datapath,String scorepath){
        data = new File(datapath);
        score = new File(scorepath);
    }

    public void write(EnumNameFile file,List<String> datas) throws WrongFileException {
        FileWriter writer = null;
        try {
            switch (file){
                case SCORE:
                    writer = new FileWriter(score);
                    break;
                case DATA:
                    writer = new FileWriter(data);
                    int size = datas.size();
                    writer.write(Integer.toString(size)+"\n");
                    break;
                default:
                    throw new WrongFileException("Le programme ne reconnait pas " +
                            "quel fichier" +
                            "de sortie est demandé, seul " + EnumNameFile.SCORE.toString() +
                            " et "+ EnumNameFile.DATA.toString() + " sont possibles.");
            }
            for (String str : datas){
                writer.write(str);
            }
        } catch (IOException e) {
            e.printStackTrace(); // I'd rather declare method with throws IOException and omit this catch.
        } finally {
            if (writer != null) try { writer.close(); } catch (IOException ignore) {}
        }
    }
}
