package fr.uca.unice.polytech.si3.ps5.year17.teams.strategy;

import fr.uca.unice.polytech.si3.ps5.year17.teams.engine.Network;

/**
 * Interface strategy
 * Pattern Strategy classique.
 */
public interface Strategy {

    void execute(Network network);
}
