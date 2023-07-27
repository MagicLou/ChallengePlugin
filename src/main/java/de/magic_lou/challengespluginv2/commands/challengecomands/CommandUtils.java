package de.magic_lou.challengespluginv2.commands.challengecomands;

import de.magic_lou.challengespluginv2.Challenge;
import de.magic_lou.challengespluginv2.challengemanagment.ChallengeManager;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandUtils {
    private CommandUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static boolean exec(@NotNull String[] args, ChallengeManager challengeManager, String shuffle, Player player) {
        if (args.length < 1) {
            for (Challenge aktiveChallenge : challengeManager.aktiveChallenges) {
                if (aktiveChallenge.getMethods().contains(shuffle)) aktiveChallenge.execMeth(shuffle, player);
            }
        } else {
            Challenge challenge = challengeManager.challenges.get(args[0]);
            challenge.execMeth(shuffle, player);
        }

        return false;
    }

}
