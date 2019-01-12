package fr.uca.unice.polytech.si3.ps5.year17.teams.rcode.builder;

public enum GraphType {
    CAMEMBERT(),
    STRATEGY_SCORE_BARRE(),
    USED_CACHES_BARRE(),
    VIDEO_BARRE(), STRATEGY_TIME_BARRE;

    @Override
    public String toString(){
        String name = this.name();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(name.substring(0,1));
        int size = name.length();
        int i = 1;
        while (i<size){
            String str = name.substring(i,i+1);
            if (str.equals("_") && i!=size-1){
                i++;
                stringBuilder.append(name.substring(i,i+1));
            }
            else {
                stringBuilder.append(str.toLowerCase());
            }
            i++;
        }
        return stringBuilder.toString();
    }
}
