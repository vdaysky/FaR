package HoldMyAppleJuice;

import HoldMyAppleJuice.IO.ConfigManager;
import HoldMyAppleJuice.comamnds.*;
import HoldMyAppleJuice.raid.managers.RaidManager;
import HoldMyAppleJuice.raid.raiders.traits.*;
import HoldMyAppleJuice.raid.villagers.traits.Trader;
import net.citizensnpcs.api.CitizensAPI;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import HoldMyAppleJuice.raid.villagers.traits.RaidParticipant;

import java.io.File;

public class CustomRaids extends JavaPlugin
{
    public static CustomRaids plugin;
    public static ConfigManager config;
    public final static RaidManager listener = new RaidManager();

    public CustomRaids()
    {
        plugin = this;
    }

    @Override
    public void onEnable()
    {
        File file = new File(getDataFolder(), "PlayerKarma.yml");
        FileConfiguration conf = YamlConfiguration.loadConfiguration(file);
        if (!file.exists())
        {
            saveResource("PlayerKarma.yml", false);
        }

        config = new ConfigManager(file, conf);

        // OOF
        CitizensAPI.getTraitFactory().registerTrait(net.citizensnpcs.api.trait.TraitInfo.create(RaidParticipant.class).withName("trader_raid_participant"));
        CitizensAPI.getTraitFactory().registerTrait(net.citizensnpcs.api.trait.TraitInfo.create(Trader.class).withName("trader"));
        CitizensAPI.getTraitFactory().registerTrait(net.citizensnpcs.api.trait.TraitInfo.create(ArcherTrait.class).withName("archer"));
        CitizensAPI.getTraitFactory().registerTrait(net.citizensnpcs.api.trait.TraitInfo.create(SupportTrait.class).withName("support"));
        CitizensAPI.getTraitFactory().registerTrait(net.citizensnpcs.api.trait.TraitInfo.create(DamagerTrait.class).withName("damager"));
        CitizensAPI.getTraitFactory().registerTrait(net.citizensnpcs.api.trait.TraitInfo.create(TankTrait.class).withName("tank"));
        CitizensAPI.getTraitFactory().registerTrait(net.citizensnpcs.api.trait.TraitInfo.create(MarksmanTrait.class).withName("marksman"));

        getCommand("craid").setExecutor(new CRaid());
        getCommand("raider").setExecutor(new CRaider());
        getCommand("participant").setExecutor(new CParticipant());

        getCommand("trader").setExecutor(new CTrader());
        getCommand("trader").setTabCompleter(new CTraiderAutocomplete());

        // actually crunch
        getServer().getPluginManager().registerEvents(listener, this);
    }

}
