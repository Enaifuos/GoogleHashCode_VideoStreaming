package fr.uca.unice.polytech.si3.ps5.year17.teams;
import fr.uca.unice.polytech.si3.ps5.year17.teams.engine.Network;
import fr.uca.unice.polytech.si3.ps5.year17.teams.strategy.*;
import org.openjdk.jmh.annotations.*;
import java.util.concurrent.TimeUnit;

/**
 * Benchmark de l'engine
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(1)
@Warmup(iterations = 3)
@Measurement(iterations = 5)
@State(Scope.Benchmark)
public class BenchmarkStrategie {

    @Param({"C:\\Users\\PC-AUDIER-CALVIN\\Desktop\\Projet\\teams\\ressourcesin\\me_at_the_zoo.in"})
    private String filename;
    private Strategy0 strategy0;
    private Strategy1 strategy1;
    private Strategy2 strategy2;
    private Strategy3 strategy3;
    private Strategy4 strategy4;
    private Strategy5 strategy5;
    private Network network;
    private Engine engine;

    @Setup(Level.Iteration)
    public void setupAllStrategie(){
        engine=new Engine();
        engine.setNetwork(filename);
        network=engine.getNetwork();
        strategy0=new Strategy0();
        strategy1=new Strategy1();
        strategy2=new Strategy2();
        strategy3=new Strategy3();
        strategy4=new Strategy4();
        strategy5=new Strategy5();
    }

    @Benchmark
    public void strategie0(){
        strategy0.execute(network);
    }
    @Benchmark
    public void strategie1(){
        strategy1.execute(network);
    }
    @Benchmark
    public void strategie2(){
        strategy2.execute(network);
    }
    @Benchmark
    public void strategie3(){
        strategy3.execute(network);
    }
    @Benchmark
    public void strategie4(){
        strategy4.execute(network);
    }
    @Benchmark
    public void strategie5(){
        strategy5.execute(network);
    }
}
