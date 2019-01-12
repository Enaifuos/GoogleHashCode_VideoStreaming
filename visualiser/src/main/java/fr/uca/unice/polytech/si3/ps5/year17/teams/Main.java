package fr.uca.unice.polytech.si3.ps5.year17.teams;

import fr.uca.unice.polytech.si3.ps5.year17.teams.parser.Parser;
import fr.uca.unice.polytech.si3.ps5.year17.teams.parser.ParserDatain;
import fr.uca.unice.polytech.si3.ps5.year17.teams.parser.ParserDataout;
import fr.uca.unice.polytech.si3.ps5.year17.teams.parser.ParserResultatjson;
import fr.uca.unice.polytech.si3.ps5.year17.teams.parser.ParserScoreout;
import fr.uca.unice.polytech.si3.ps5.year17.teams.rcode.builder.*;
import fr.uca.unice.polytech.si3.ps5.year17.teams.rcode.RcodeWriter;


public class Main {

    public static void main(String[] args) throws Exception{
        DataCollector collector = new DataCollector();
        ParserDatain in = new ParserDatain(args[0]);
        in.execute();

        collector.setNbcache(in.getNbcache());
        collector.setCachesize(in.getCachesize());
        collector.setNbvideos(in.getNbvideos());
        collector.setVideosize(in.getVideosize());

        String[] dataout = args[1].split(";");
        String[] scoreout = args[2].split(";");
        int[] scores = new int[scoreout.length];
        collector.setLength(dataout.length);
        for (int i = 0 ; i < dataout.length ; i++){
            ParserDataout out = new ParserDataout(dataout[i]);
            out.execute();
            collector.setCaches(out.getCaches());
            collector.setCachesused(out.getCachesused(),i);
            ParserScoreout score = new ParserScoreout(scoreout[i],i);
            scores[i] = score.getScore();
        }
        collector.setStratScores(scores);

//        ParserResultatjson resultat=new ParserResultatjson(args[3]);
//        resultat.execute();

        RcodeWriter writer = new RcodeWriter();
        RcodeBuilder builder;

        for (int i = 0 ; i<dataout.length ; i++){
            builder = new RcodeBuilderCamembert(collector,i);
            builder.build();
            writer.write(builder.getCode());

            builder = new RcodeBuilderVideoBarre(collector,i);
            builder.build();
            writer.write(builder.getCode());

            builder = new RcodeBuilderUsedCachesBarre(collector,i);
            builder.build();writer.write(builder.getCode());
        }

        builder = new RcodeBuilderStrategyScoreBarre(collector);
        builder.build();
        writer.write(builder.getCode());

        builder = new RcodeBuilderStrategyTimeBarre(collector);
        builder.build();
        writer.write(builder.getCode());
    }
}
