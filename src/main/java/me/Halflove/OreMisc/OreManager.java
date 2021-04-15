package me.Halflove.OreMisc;

import me.Halflove.OreMain.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class OreManager {

    public static ItemStack smeltFilter(String material){
        ItemStack item = new ItemStack(Material.valueOf(material));
        if(item.getType().equals(Material.IRON_ORE)){
            item = new ItemStack(Material.IRON_INGOT, 1);
        }else if(item.getType().equals(Material.GOLD_ORE)){
            item = new ItemStack(Material.GOLD_INGOT, 1);
        }else if(item.getType().equals(Material.LAPIS_ORE)){
            item = new ItemStack(Material.LAPIS_LAZULI, 4);
        }else if(item.getType().equals(Material.COAL_ORE)){
            item = new ItemStack(Material.COAL, 1);
        }else if(item.getType().equals(Material.REDSTONE_ORE)){
            item = new ItemStack(Material.REDSTONE, 4);
        }else if(item.getType().equals(Material.DIAMOND_ORE)){
            item = new ItemStack(Material.DIAMOND, 1);
        }else if(item.getType().equals(Material.EMERALD_ORE)){
            item = new ItemStack(Material.EMERALD, 1);
        }else if(item.getType().equals(Material.NETHER_QUARTZ_ORE)){
            item = new ItemStack(Material.QUARTZ, 1);
        }
        return item;
    }

    public static void payOre(Player player, String material){
        int amount = 0;
        Random random = new Random();
        ItemStack item = new ItemStack(Material.valueOf(material));
        if(item.getType().equals(Material.IRON_ORE)){
            amount = random.nextInt((80 + 30) +30);
        }else if(item.getType().equals(Material.GOLD_ORE)){
            amount = random.nextInt((100 + 50) +50);
        }else if(item.getType().equals(Material.LAPIS_ORE)){
            amount = random.nextInt((120 + 50) +50);
        }else if(item.getType().equals(Material.COAL_ORE)){
            amount = random.nextInt((70 + 30) +30);
        }else if(item.getType().equals(Material.REDSTONE_ORE)){
            amount = random.nextInt((120 + 50) +50);
        }else if(item.getType().equals(Material.DIAMOND_ORE)){
            amount = random.nextInt((250 + 150) +50);
        }else if(item.getType().equals(Material.EMERALD_ORE)){
            amount = random.nextInt((300 + 150) +50);
        }else if(item.getType().equals(Material.NETHER_QUARTZ_ORE)){
            amount = random.nextInt((70 + 30) +30);
        }
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a+$"+amount));
        Main.econ.depositPlayer(player, amount);
    }

}

