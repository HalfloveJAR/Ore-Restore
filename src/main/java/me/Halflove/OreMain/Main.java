package me.Halflove.OreMain;

import me.Halflove.OreMisc.OreCommands;
import me.Halflove.OreMisc.OreListener;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public SettingsManager settings = SettingsManager.getInstance();
    public static Plugin plugin;
    public static Economy econ;

    public void onEnable() {
        plugin = (Plugin)this;
        getLogger().info("Version " + getDescription().getVersion() + " has been enabled.");
        this.settings.setup((Plugin)this);
        registerEvents();
        registerCommands();
        if (!setupEconomy()) {
            this.getLogger().warning("No Vault dependency found!");
            return;
        }
    }

    public void onDisable() {
        getLogger().info("Version " + getDescription().getVersion() + " has been disabled.");
    }

    private void registerEvents() {
        Bukkit.getPluginManager().registerEvents((Listener)new OreListener(), (Plugin)this);
    }

    public void registerCommands() {
        getCommand("orerestore").setExecutor((CommandExecutor)new OreCommands());
    }

    private boolean setupEconomy() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            return false;
        }

        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public static Economy getEconomy() {
        return econ;
    }

}
