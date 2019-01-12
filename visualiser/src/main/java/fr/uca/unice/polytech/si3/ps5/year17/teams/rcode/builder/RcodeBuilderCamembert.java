package fr.uca.unice.polytech.si3.ps5.year17.teams.rcode.builder;

import fr.uca.unice.polytech.si3.ps5.year17.teams.DataCollector;

import java.util.List;
import java.util.Map;



/**
 * 
 * Une presentation graphique en camembert de l'utilisation de l'espace du cache
 * par categories de ( 0%-25%, 25%-50%, 50%-75%, 75%-100% )
 *
 */
public class RcodeBuilderCamembert extends RcodeBuilder {
    private static final GraphType type = GraphType.CAMEMBERT;
    private int nbstrat;

    public RcodeBuilderCamembert(DataCollector collector,int nbstrat){
        super(collector,6,type);
        this.nbstrat = nbstrat;
    }
    @Override
    public void build() {
        int[] data = getData();
        StringBuilder str = new StringBuilder("slices <- c(");
        for ( int i  : data) {
            str.append(i).append(",");
        }
        str = new StringBuilder(str.substring(0, str.length() - 1));
        str.append(")");
        code[1] = str.toString();
        code[2] = "lbls <- paste(slices) ";
        code[3] = "pie(slices,labels = lbls, col=c(\"red\",\"blue\",\"green\",\"brown\"),main=\"Representation des caches serveurs \n selon le pourcentage d'utilisation\" )";
        code[4] = "legend(x=\"bottomleft\", legend=c(\"moins de 25%\",\"entre 25%-50%\",\"entre 50%-75%\",\"75% ou plus\"), cex=0.75,fill=c(\"red\",\"blue\",\"green\",\"brown\"))";
    }

    @Override
    public int[] getData() {
        return getIntervals(getCacheUsedSpace(collector.getCaches(nbstrat),collector.getCachesused(nbstrat),collector.getVideosize()),collector.getCachesize(),collector.getNbcache());
    }

    /**
     * Fonction pour créer une hashMap avec les caches en key, le pourcentage d'utilisation en valeur
     * @param cacheVideos : HashMap pour chaque cache , la liste des vidéos associée
     * @param videoSize : HashMap pour chaque video, la taille associée
     * @return HashMap<IntegerInteger> : une Hashmap associant chaque cache son espace utilisé
     */
    private int[] getCacheUsedSpace(Map<Integer,List<Integer>> cacheVideos, int nbcachesused, Map<Integer,Integer> videoSize ) {
        int[] caches = new int[nbcachesused];
        int sumSizes;

        /*	Calculer la taille utilisé par cache	*/

        int i = 0;
        for ( Map.Entry<Integer, List<Integer>> entry : cacheVideos.entrySet() ) {
            sumSizes = 0;
            /*	Calculer la somme de taille des videos par cache	*/
            for ( Map.Entry<Integer, Integer> entry2 : videoSize.entrySet() ) {
                if ( entry.getValue().contains(entry2.getKey()) ) {
                    sumSizes += entry2.getValue();
                }
            }
            caches[i]=sumSizes;
            i++;
        }

        return caches;
    }

    /**
     * Fonction qui retourne un tabelau d'intervals
     * La première case correspond à <25%
     * La deuxième à 25-50%
     * etc..
     * @param caches la taille utilisé pour tout les caches non-vides
     * @param cachesize la taille initiale du cache
     * @param nbtotalcache le nombre total de cache
     * @return un table de pourcentage utilisé du cache pour tout les caches
     */
    private int[] getIntervals(int[] caches, int cachesize,int nbtotalcache) {
        int[] interval = new int[4];
        for (int cache : caches) {
            double percent = ((double) (cache)) / cachesize;
            percent *= 100;
            int part = (int) (percent / 25);
            if (part == 4) { //cas 100%
                part--;
            }
            interval[part]++;
        }
        int cacherestant = nbtotalcache-caches.length;
        interval[0]+=cacherestant;
        return interval;
    }
}
