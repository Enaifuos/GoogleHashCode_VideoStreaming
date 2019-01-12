package fr.uca.unice.polytech.si3.ps5.year17.teams.rcode.builder;

import fr.uca.unice.polytech.si3.ps5.year17.teams.DataCollector;
import org.junit.Before;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RcodeBuilderTest {
    private RcodeBuilder builder;

    @Before
    public void setUp() {
        this.builder = new RcodeBuilderCamembert(new DataCollector(),0);
    }

    @Test
    public void getCodeSuper() {
        assertEquals("png(\"./Camembert.png\")",builder.code[0]);
        assertEquals("garbage<-dev.off()",builder.code[builder.code.length-1]);
    }
}