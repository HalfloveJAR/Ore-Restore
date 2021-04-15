package me.Halflove.OreMisc;

import me.Halflove.OreMain.SettingsManager;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OreCommands implements CommandExecutor {

    public void invalid(Player player){
        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 2.0F, 0.0F);
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4Invalid arguments."));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c/ore set (expended block) (reset time in secs)"));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c/ore delete"));
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(cmd.getName().equalsIgnoreCase("orerestore")){
            if(sender.hasPermission("orerestore.admin")){
                if(args.length == 0){
                    invalid((Player) sender);
                }else if(args.length == 1){
                    if(args[0].equalsIgnoreCase("delete")){

                        //delete ore
                        Player player = (Player) sender;
                        int distance = 4;
                        if(player.getGameMode().equals(GameMode.CREATIVE)){
                            distance = 5;
                        }
                        Block block = player.getTargetBlock(null, distance);
                        Location loc = block.getLocation();
                        int x = loc.getBlockX();
                        int y = loc.getBlockY();
                        int z = loc.getBlockZ();
                        String world = loc.getWorld().getName();
                        String code = world+ x + y + z;
                        if(player.getGameMode().equals(GameMode.SPECTATOR)||player.getGameMode().equals(GameMode.ADVENTURE)) {
                            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 2.0F, 0.0F);
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4Invalid gamemode."));
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou can't use OreRestore in gamemode " + player.getGameMode().toString().toLowerCase()));
                        }else if(SettingsManager.getData().getString("ores."+code+".world")!=null){
                            SettingsManager.getData().set("ores."+code, null);
                            SettingsManager.saveData();
                            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 2.0F, 2.0F);
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aRegenerating ore successfully deleted."));
                        }else{
                            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 2.0F, 0.0F);
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4Invalid block."));
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThe block you're looking at isn't a regenerating ore"));
                        }

                    }else{
                        invalid((Player) sender);
                    }
                }else if(args[0].equalsIgnoreCase("set")){
                    if(args.length != 3){
                        invalid((Player) sender);
                    }else{
                        if(SettingsManager.isMaterial(args[1].toUpperCase())){
                            if(SettingsManager.isInt(args[2])){

                                //set ore
                                Player player = (Player) sender;
                                int distance = 4;
                                if(player.getGameMode().equals(GameMode.CREATIVE)){
                                    distance = 5;
                                }
                                Block block = player.getTargetBlock(null, distance);
                                Location loc = block.getLocation();
                                String material = block.getType().toString();
                                int x = loc.getBlockX();
                                int y = loc.getBlockY();
                                int z = loc.getBlockZ();
                                String world = loc.getWorld().getName();
                                String code = world+ x + y + z;
                                String reset = args[1].toUpperCase();
                                int time = Integer.valueOf(args[2]);
                                if(player.getGameMode().equals(GameMode.SPECTATOR)||player.getGameMode().equals(GameMode.ADVENTURE)){
                                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 2.0F, 0.0F);
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4Invalid gamemode."));
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou can't use OreRestore in gamemode " + player.getGameMode().toString().toLowerCase()));
                                }else if(block.getType().equals(Material.AIR)) {
                                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 2.0F, 0.0F);
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4Invalid block."));
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cAir cannot become a registered block"));
                                }else if(SettingsManager.getData().getString("ores."+code+".world")==null) {
                                    SettingsManager.getData().set("ores." + code + ".world", world);
                                    SettingsManager.getData().set("ores." + code + ".x", x);
                                    SettingsManager.getData().set("ores." + code + ".y", y);
                                    SettingsManager.getData().set("ores." + code + ".z", z);
                                    SettingsManager.getData().set("ores." + code + ".block", material);
                                    SettingsManager.getData().set("ores." + code + ".reset-block", reset);
                                    SettingsManager.getData().set("ores." + code + ".time", time);
                                    SettingsManager.getData().set("ores." + code + ".cooldown", null);
                                    SettingsManager.saveData();
                                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 2.0F, 2.0F);
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aRegenerating ore successfully created."));
                                }else{
                                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 2.0F, 0.0F);
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4Invalid block."));
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThat is already a registered regenerating block"));
                                }

                            }else{
                                Player player = (Player) sender;
                                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 2.0F, 0.0F);
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4Invalid arguments."));
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThe reset time must be numerical (in seconds)"));
                            }
                        }else{
                            Player player = (Player) sender;
                            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 2.0F, 0.0F);
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4Invalid arguments."));
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cExpended Block value must be a Bukkit material"));
                        }
                    }
                }
            }else{
                Player player = (Player) sender;
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 2.0F, 0.0F);
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', SettingsManager.getConfig().getString("messages.no-permission")));
            }
        }

        return true;
    }
}

