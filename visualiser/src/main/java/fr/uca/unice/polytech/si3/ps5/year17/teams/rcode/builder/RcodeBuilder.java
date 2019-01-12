package fr.uca.unice.polytech.si3.ps5.year17.teams.rcode.builder;

import fr.uca.unice.polytech.si3.ps5.year17.teams.DataCollector;

public abstract class RcodeBuilder {
    final DataCollector collector;
    final String[] code;

    RcodeBuilder(DataCollector collector,int size,GraphType type){
        this.collector = collector;
        this.code = new String[size];
        code[0] = "png(\"./"+type.toString()+".png\")";
        code[size-1]= "garbage<-dev.off()";
    }

    public abstract void build();

    public String[] getCode() {
        return code;
    }

    public abstract int[] getData();
}
