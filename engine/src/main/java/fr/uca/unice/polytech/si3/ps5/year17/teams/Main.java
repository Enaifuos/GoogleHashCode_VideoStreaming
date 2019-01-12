package fr.uca.unice.polytech.si3.ps5.year17.teams;

import fr.uca.unice.polytech.si3.ps5.year17.teams.strategy.*;

public class Main {
    public static void main(String[] args) throws Exception {
        String strategypackage = "fr.uca.unice.polytech.si3.ps5.year17.teams.strategy.";
        int idstrat = Integer.parseInt(args[0]);
        Strategy strategy = (Strategy) Class.forName(strategypackage+ "Strategy" + idstrat).newInstance();
        Engine engine = new Engine();
        engine.setNetwork(args[1]);
        engine.executeStrategy(strategy);
        DataWriter writer = new DataWriter(args[2],args[3]);
        writer.write(DataWriter.EnumNameFile.DATA,engine.collectData());
        writer.write(DataWriter.EnumNameFile.SCORE,engine.collectScore());
    }
}
