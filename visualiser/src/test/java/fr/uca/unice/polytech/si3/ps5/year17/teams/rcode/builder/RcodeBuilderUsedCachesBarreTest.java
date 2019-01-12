package fr.uca.unice.polytech.si3.ps5.year17.teams.rcode.builder;

import fr.uca.unice.polytech.si3.ps5.year17.teams.DataCollector;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RcodeBuilderUsedCachesBarreTest {
    private RcodeBuilderUsedCachesBarre builder1;

    @Before
    public void setUp() {
        DataCollector collector = new DataCollector();

        collector.setLength(1);
        collector.setNbcache(100);
        collector.setCachesused(13,0);

        builder1 = new RcodeBuilderUsedCachesBarre(collector,0);
    }

    @Test
    public void build() {
        builder1.build();
        assertEquals("my_vector=c(100,13)",builder1.code[1]);
        assertEquals("names(my_vector)=c(\"Ensemble des caches dans le reseau\",\"Caches utilises par la strategie \")",builder1.code[2]);
        assertEquals("barplot(my_vector, main=\"Utilisation de caches\")",builder1.code[3]);
        assertEquals("col=c(\"deepskyblue\",\"green3\")",builder1.code[4]);
    }

    @Test
    public void getData() {
        int rest[] = new int[2];
        rest[0] = 100;
        rest[1] = 13;
        assertEquals(rest[0],builder1.getData()[0]);
        assertEquals(rest[1],builder1.getData()[1]);
    }
}