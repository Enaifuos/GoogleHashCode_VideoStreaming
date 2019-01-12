package fr.uca.unice.polytech.si3.ps5.year17.teams.rcode.builder;

import fr.uca.unice.polytech.si3.ps5.year17.teams.DataCollector;
import org.junit.Before;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * builder test
 */
public class RcodeBuilderVideoBarreTest {
    private RcodeBuilderVideoBarre builder1;

    @Before
    public void setUp() {
        DataCollector collector = new DataCollector();

        Map<Integer,List<Integer>> caches = new HashMap<>();
        List<Integer> videolist = new ArrayList<>();
        videolist.add(52);
        videolist.add(41);
        videolist.add(99);
        List<Integer> videolist2 = new ArrayList<>();
        videolist2.add(11);
        videolist2.add(43);
        videolist2.add(19);
        caches.put(1,videolist);
        caches.put(3,videolist2);
        collector.setCaches(caches);

        collector.setNbvideos(100);

        builder1 = new RcodeBuilderVideoBarre(collector,0);


    }

    @org.junit.Test
    public void superClass() {
        assertEquals(6,builder1.code.length);
    }

    @org.junit.Test
    public void build() {
        builder1.build();
        assertEquals("my_vector=c(100,6)",builder1.code[1]);
        assertEquals("names(my_vector)=c(\"Total videos dans dataCenter\",\"Videos stockees dans les caches\")",builder1.code[2]);
        assertEquals("barplot(my_vector, main=\"Stockage des videos dans les caches par rapport aux videos du DataCenter\")",builder1.code[3]);
        assertEquals("col=c(\"darkblue\",\"red\")",builder1.code[4]);
    }

    @org.junit.Test
    public void getData() {
        int rest[] = new int[2];
        rest[0] = 100;
        rest[1] = 6;
        assertEquals(rest[0],builder1.getData()[0]);
        assertEquals(rest[1],builder1.getData()[1]);
    }
}