package fr.uca.unice.polytech.si3.ps5.year17.teams.rcode;


import java.io.*;


/**
 * PENSER A CREER UNE HASHMAP <VIDEO,SIZE> , HASHMAP <CACHE,SIZE> ...
 * POUR LES UTILISER AVEC LA FONCTION R ET GENERER UN GRAPHE
 */


public class RcodeWriter {
    private int nbfichier;

    public RcodeWriter(){
        this.nbfichier = 1;
    }

    public void write(String[] code) {
        File file = new File("graph"+nbfichier+".R");
        FileWriter fw = null ;
        try {
            fw = new FileWriter(file);
            for (String str : code){
                writeLine(fw,str);
            }
        }catch (IOException e){
            System.out.print(e.getMessage());
        }
        finally {
            if( fw != null) try { fw.close(); } catch ( IOException ignore) {}
        }
        nbfichier++;
    }

    private void writeLine(FileWriter fw,String str) throws IOException {
        fw.write(str + "\n");
    }
}









