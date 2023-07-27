package de.magic_lou.challengespluginv2;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

public interface Challenge {

    void start();

    void pause();

    void resume();

    void restart();

    void stop();

    List<String> getMethods();

    void execMeth(String methode, Player player);

    void description(Player player);

    List<String> getDescription();

    ChallengeType getType();

    List<ChallengeDiff> getDiffs();

    ChallengeDiff getDiff();

    void setDiff(ChallengeDiff diff);

    Material getItem();

    enum ChallengeType {
        RANDOM, FORCE, OTHER, NOTWORKING, BALLTE, GENERATOR
    }

    enum ChallengeDiff {
        EASY, NORMAL, HARD
    }

}
