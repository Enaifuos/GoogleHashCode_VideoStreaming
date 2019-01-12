package fr.uca.unice.polytech.si3.ps5.year17.teams.rcode.builder;

import fr.uca.unice.polytech.si3.ps5.year17.teams.DataCollector;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RcodeBuilderVideoBarre extends RcodeBuilder {
    private static final GraphType type = GraphType.VIDEO_BARRE;
    private int nbstrat;

    public RcodeBuilderVideoBarre(DataCollector collector,int nbstrat) {
        super(collector, 6,type);
        this.nbstrat = nbstrat;
    }

    @Override
    public void build() {
        int[] data = getData();
        String graph2 = "my_vector=c(";
        graph2 += data[0];
        graph2 += ",";
        graph2 += data[1];
        graph2 += ")";
        code[1] = graph2;
        code[2] = "names(my_vector)=c(\"Total videos dans dataCenter\",\"Videos stockees dans les caches\")";
        code[3] = "barplot(my_vector, main=\"Stockage des videos dans les caches par rapport aux videos du DataCenter\")";
        code[4] = "col=c(\"darkblue\",\"red\")";
    }

    @Override
    public int[] getData() {
        int[] data = new int[2];
        data[0] = collector.getNbvideos();
        data[1] = getNbVideosStored(collector.getCaches(nbstrat));
        return data;
    }
    /**
     * Fonction qui prend une HashMap d'association ( cache, liste videos qui y sont stockées )
     * et retourne le nombre de videos ( sans doublons ) stockées
     * @param cacheListVideos La liste des vidéos présentes dans chaque cache (si le cache n'est pas présent, c'est qu'il n'a pas de vidéo).
     * @return nombre de videos stockees dans tous les caches ( sans compter les doublons )
     */
    private int getNbVideosStored(Map<Integer,List<Integer>> cacheListVideos) {
        Set<Integer> ensembleVideos = new HashSet<>();
        for ( Map.Entry<Integer, List<Integer>> entry : cacheListVideos.entrySet() ) {
            ensembleVideos.addAll(entry.getValue());
        }

        return ensembleVideos.size();
    }



}
