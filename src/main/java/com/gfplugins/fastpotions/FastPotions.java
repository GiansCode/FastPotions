package com.gfplugins.fastpotions;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public final class FastPotions extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
        if (event.getEntityType() != EntityType.SPLASH_POTION) {
            return;
        }

        final Projectile projectile = event.getEntity();

        if (!(projectile.getShooter() instanceof Player)) {
            return;
        }

        final Player player = (Player) event.getEntity().getShooter();
        final Vector velocity = projectile.getVelocity();

        if (player.isSprinting()) {
            velocity.setY(velocity.getY() - 1.0);
            projectile.setVelocity(velocity);
        }
    }

    @EventHandler
    public void onPotionSplash(PotionSplashEvent event) {
        if (!(event.getEntity().getShooter() instanceof Player)) {
            return;
        }

        final Player player = (Player) event.getEntity().getShooter();

        if (player.isSprinting() && event.getIntensity(player) > 0.5D) {
            event.setIntensity(player, 1.0D);
        }
    }
}