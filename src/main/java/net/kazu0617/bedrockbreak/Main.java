package net.kazu0617.bedrockbreak;

import java.io.File;
import java.util.HashSet;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author     kazu0617
 * @license    MIT
 * @copyright  Copyright kazu0617 2015-2017
 */
public class Main extends JavaPlugin implements Listener
{
    //Todo Auto機能を実装。
    String Pluginprefix = "[" + ChatColor.GREEN + getDescription().getName() + ChatColor.RESET + "] ";
    String Pluginname = "[" + getDescription().getName() +"] ";
    public ConsoleLog cLog = new ConsoleLog(this);
    public BlockBreakListener BreakListener = new BlockBreakListener(this);
    String folder = getDataFolder() + File.separator;
 
    boolean DebugMode = false;
    
    @Override
    public void onEnable() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(this, this);
        pm.registerEvents(this.BreakListener, this);
        cLog.info("DebugMode is now [" + DebugMode + "].");
    }

    @Override
    public void onDisable() {

    }

    @EventHandler
    public void onPlayerInspect(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if(p.getInventory().getItemInMainHand()==null) return;
        HashSet <Material> Tools = new HashSet<>();
        Block Location = e.getClickedBlock();
        
        Tools.add(Material.STONE_PICKAXE);
        Tools.add(Material.IRON_PICKAXE);
        Tools.add(Material.GOLD_PICKAXE);
        Tools.add(Material.DIAMOND_PICKAXE);
        
        if ( p.getGameMode()!=GameMode.SURVIVAL 
                || e.getAction() != Action.LEFT_CLICK_BLOCK 
                || Location.getType() != Material.BEDROCK 
                || Location.getY()<=0
                || !Tools.contains(p.getInventory().getItemInMainHand().getType()))
            return;

        if(p.hasPermission("bedrockbreak.advance")){
            Location.setType(Material.OBSIDIAN);
            p.playSound(Location.getLocation(), Sound.BLOCK_LEVER_CLICK, 0, 10);
        }
        else if(!p.hasPermission("bedrockbreak.advance")){
            if(Location.getLocation().getBlockY()>5)
                return;
            Location.setType(Material.STONE);
            p.playSound(Location.getLocation(), Sound.BLOCK_LEVER_CLICK, 0, 10);
        }
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if((args.length == 1) && "DebugMode".equalsIgnoreCase(args[0]))
        {
            if(DebugMode) DebugMode = false;
            else if(!DebugMode) DebugMode = true;
            cLog.Message(sender,"DebugMode is now ["+DebugMode+"].");
        }
        
        return true;
            
    }
}
