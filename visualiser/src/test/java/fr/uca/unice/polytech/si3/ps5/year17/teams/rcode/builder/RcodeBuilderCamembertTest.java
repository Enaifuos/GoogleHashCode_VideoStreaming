package fr.uca.unice.polytech.si3.ps5.year17.teams.rcode.builder;

import fr.uca.unice.polytech.si3.ps5.year17.teams.DataCollector;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;


public class RcodeBuilderCamembertTest{
    private RcodeBuilderCamembert builder1;

    @Before
    public void setUp() {
        DataCollector collector = new DataCollector();

        Map<Integer,List<Integer>> caches = new HashMap<>();
        List<Integer> videolist = new ArrayList<>();
        videolist.add(52);
        videolist.add(41);
        videolist.add(99);
        List<Integer> videolist2 = new ArrayList<>();
        videolist2.add(101);
        videolist2.add(43);
        videolist2.add(19);
        caches.put(1,videolist);
        caches.put(3,videolist2);
        collector.setCaches(caches);
        collector.setLength(1);

        collector.setCachesused(2,0);

        Map<Integer,Integer> videosize = new HashMap<>();
        videosize.put(52,40);
        videosize.put(41,30);
        videosize.put(99,50);
        videosize.put(101,22);
        videosize.put(43,99);
        videosize.put(19,11);
        collector.setVideosize(videosize);

        collector.setCachesize(200);

        collector.setNbcache(10);

        builder1 = new RcodeBuilderCamembert(collector,0);
    }

    @Test
    public void superClass() {
        assertEquals(6,builder1.code.length);
    }

    @Test
    public void build() {
        builder1.build();
        assertEquals("slices <- c(8,0,2,0)",builder1.code[1]);
        assertEquals("lbls <- paste(slices) ",builder1.code[2]);
        assertEquals("pie(slices,labels = lbls, col=c(\"red\",\"blue\",\"green\",\"brown\"),main=\"Representation des caches serveurs \n" +
                " selon le pourcentage d'utilisation\" )",builder1.code[3]);
        assertEquals("legend(x=\"bottomleft\", legend=c(\"moins de 25%\",\"entre 25%-50%\",\"entre 50%-75%\",\"75% ou plus\"), cex=0.75,fill=c(\"red\",\"blue\",\"green\",\"brown\"))",builder1.code[4]);
    }

    @Test
    public void getData() {
        int rest[] = new int[4];
        rest[0] = 8;
        rest[1] = 0;
        rest[2] = 2;
        rest[3] = 0;
        assertEquals(rest[0],builder1.getData()[0]);
        assertEquals(rest[1],builder1.getData()[1]);
        assertEquals(rest[2],builder1.getData()[2]);
        assertEquals(rest[3],builder1.getData()[3]);
    }
}