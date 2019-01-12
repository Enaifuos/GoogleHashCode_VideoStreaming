package fr.uca.unice.polytech.si3.ps5.year17.teams.rcode.builder;

import fr.uca.unice.polytech.si3.ps5.year17.teams.DataCollector;

public class RcodeBuilderStrategyScoreBarre extends RcodeBuilder {
    private static final GraphType type = GraphType.STRATEGY_SCORE_BARRE;

    public RcodeBuilderStrategyScoreBarre(DataCollector collector) {
        super(collector, 6,type);
    }

    @Override
    public void build() {
        int[] data = getData();
        StringBuilder str = new StringBuilder("my_vector = c(");
        for ( int i : data ) {
            str.append(i).append(",");
        }
        str = new StringBuilder(str.substring(0, str.length() - 1 ));
        str.append(")");
        code[1]= str.toString();
        code[2]= "names(my_vector)=c(\"strategy1\",\"strategy2\",\"strategy3\",\"strategy4\",\"strategy5\")";
        code[3]= "barplot(my_vector, main=\"Evolution du score selon la strategie\",col=\"white\")";
        code[4]= "lines(my_vector, col=\"black\", lwd=2)";
    }

    @Override
    public int[] getData() {
        return collector.getStratScores();
    }
}