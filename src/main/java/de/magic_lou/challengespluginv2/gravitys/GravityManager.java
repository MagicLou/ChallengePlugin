package de.magic_lou.challengespluginv2.gravitys;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.HashMap;
import java.util.Random;

public class GravityManager {

    private static final Random random = new Random();
    private final AntiGravity antiGravity;
    private final HardGravity hardGravity;
    private final LowGravity lowGravity;
    private final NoGravity noGravity;
    private final NormalGravity normalGravity;
    private final HashMap<String, Gravity> gravitys = new HashMap<>();
    private Gravity selectedGravity;
    private boolean effectEntity = false;

    public GravityManager(AntiGravity antiGravity, HardGravity hardGravity, LowGravity lowGravity, NoGravity noGravity, NormalGravity normalGravity) {
        this.antiGravity = antiGravity;
        this.hardGravity = hardGravity;
        this.lowGravity = lowGravity;
        this.noGravity = noGravity;
        this.normalGravity = normalGravity;
        selectedGravity = this.normalGravity;
        setUpGravity();
    }

    public Gravity getSelectedGravity() {
        return selectedGravity;
    }

    public boolean isEffectEntity() {
        return effectEntity;
    }

    public void toggleEffectEntity() {
        effectEntity = !effectEntity;
        for (Gravity value : gravitys.values()) {
            value.setEffectEntity(effectEntity);
        }
        if (effectEntity)
            Bukkit.broadcast(Component.text("Entity's haben jetzt auch " + ChatColor.GREEN + "andere Gravity"));
        else Bukkit.broadcast(Component.text("Entity's haben jetzt immer " + ChatColor.RED + "normale Gravity"));
    }

    private void setUpGravity() {
        gravitys.put("ANTI", antiGravity);
        gravitys.put("HARD", hardGravity);
        gravitys.put("LOW", lowGravity);
        gravitys.put("NO", noGravity);
        gravitys.put("NORMAL", normalGravity);
    }

    public void setGravity(String gravity) {
        gravity = gravity.toUpperCase();
        if (gravitys.containsKey(gravity)) {
            selectedGravity.stop();
            selectedGravity = gravitys.get(gravity);
            selectedGravity.start();
        }
        Bukkit.broadcast(Component.text(ChatColor.WHITE + "The Gravity is now " + ChatColor.GREEN + gravity));
    }


    public void start() {
        selectedGravity.start();
    }

    public void stop() {
        selectedGravity.stop();
    }

    public void pause() {
        selectedGravity.pause();
    }

    public void resume() {
        selectedGravity.resume();
    }

    public void reset() {
        selectedGravity.stop();
        selectedGravity.start();
    }

    public void selectRandom() {
        setGravity(gravitys.keySet().stream().toList().get(random.nextInt(gravitys.size())));
    }


}
