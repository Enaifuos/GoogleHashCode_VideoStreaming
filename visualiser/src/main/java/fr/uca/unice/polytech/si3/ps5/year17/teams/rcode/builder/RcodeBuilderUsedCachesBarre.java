package fr.uca.unice.polytech.si3.ps5.year17.teams.rcode.builder;

import fr.uca.unice.polytech.si3.ps5.year17.teams.DataCollector;

/**
 *
 * Une presenation graphique en barres du nombre de caches utilises
 * parmis tous les caches du reseau
 *
 */
public class RcodeBuilderUsedCachesBarre extends RcodeBuilder {
    private static final GraphType type = GraphType.USED_CACHES_BARRE;
    private int nbstrat;

    public RcodeBuilderUsedCachesBarre(DataCollector collector,int nbstrat) {
        super(collector,6,type);
        this.nbstrat = nbstrat;
    }

    @Override
    public void build() {
        int[] data = getData();
        String graph3 =  "my_vector=c(";
        graph3 += data[0];
        graph3+= ",";
        graph3 += data[1];
        graph3 += ")";
        code[1] = graph3;
        code[2] = "names(my_vector)=c(\"Ensemble des caches dans le reseau\",\"Caches utilises par la strategie \")";
        code[3] = "barplot(my_vector, main=\"Utilisation de caches\")";
        code[4] = "col=c(\"deepskyblue\",\"green3\")";
    }

    @Override
    public int[] getData() {
        int[] data = new int[2];
        data[0] = collector.getNbcache();
        data[1] = collector.getCachesused(nbstrat);
        return data;
    }

}