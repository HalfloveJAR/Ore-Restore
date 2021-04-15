package me.Halflove.OreMisc;

import me.Halflove.OreMain.Main;
import me.Halflove.OreMain.SettingsManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class OreListener implements Listener {

    @EventHandler
    public void onMine(BlockBreakEvent event) {

        Player player = event.getPlayer();
        Block block = event.getBlock();
        Location loc = block.getLocation();
        int x = loc.getBlockX();
        int y = loc.getBlockY();
        int z = loc.getBlockZ();
        String world = loc.getWorld().getName();
        String code = world+ x + y + z;
        if(SettingsManager.getData().getString("ores."+code+".world")!=null){
            int cooldown = SettingsManager.getData().getInt("ores."+code+".time");
            String original = SettingsManager.getData().getString("ores."+code+".block");
            String restored = SettingsManager.getData().getString("ores."+code+".reset-block");
            Location centerOfBlock = loc.add(0.5, 0.5, 0.5);

            event.setCancelled(true);
            if(SettingsManager.isMaterial(original)) {
                if (block.getType().equals(Material.valueOf(original))) {
                    block.getWorld().dropItemNaturally(centerOfBlock, OreManager.smeltFilter(original));
                    if(Bukkit.getPluginManager().getPlugin("Vault") != null)
                        OreManager.payOre(player, original);
                    block.setType(Material.valueOf(restored));
                    player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2.0F, 2.0F);
                    (new BukkitRunnable() {
                        public void run() {
                            block.setType(Material.valueOf(original));
                        }
                    }).runTaskLater(Main.plugin, cooldown * 20);
                }
            }

        }else{
            if(player.getLocation().getWorld().getName().contains("dungeon")){
                if(!player.hasPermission("orerestore.admin")){
                    event.setCancelled(true);
                }
            }
        }

    }
}

