package de.magic_lou.challengespluginv2.challengemanagment.GUI.managment;

import de.magic_lou.challengespluginv2.challengemanagment.GUI.guis.MainGui;
import org.bukkit.entity.Player;

public class GUIManager {

    private final MainGui mainGUI;
    private final ChallengeManagerGUI challengeManagerGUI;


    public GUIManager(MainGui mainGUI, ChallengeManagerGUI challengeManagerGUI) {
        this.mainGUI = mainGUI;
        this.challengeManagerGUI = challengeManagerGUI;
    }


    public void openInv(Player player, boolean setup) {
        if (setup) {
            if (challengeManagerGUI.challengeManager.isRunning()) {
                mainGUI.createActive(player, challengeManagerGUI.challengeManager);
            } else {
                mainGUI.creatOverview(player, challengeManagerGUI.challengeManager);
            }
        } else
            mainGUI.createInv(player);
    }


    public void nextPage(Player player) {
        mainGUI.nextPage();
        mainGUI.createInv(player);
    }

    public void backPage(Player player) {
        mainGUI.backPage();
        mainGUI.createInv(player);
    }


}
