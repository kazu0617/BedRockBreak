/**
 * @author     kazu0617
 * @license    LGPLv3
 * @copyright  Copyright kazu0617 2015
 */
package net.kazu0617.bedrockbreak;

import java.io.File;
import org.bukkit.ChatColor;
import org.bukkit.Location;
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
 * @version 0.1
 * @author kazu0617
 * @copyright Copyright kazu0617 2015
 */
public class Main extends JavaPlugin implements Listener
{
    //Todo 黒曜石ブロックに置き換え(これに関しては権限がない方は今までどおり通常の石に変更でいいかと)→高度4以下に位置する黒曜石ブロックを壊したタイミングに指定の権限がある方のみドロップを岩盤ブロックに変更する
    //Todo 高度5以上にある岩盤ブロックに関しては、指定の権限がある方のみ黒曜石ブロックにかえれるようにする
    String Pluginprefix = "[" + ChatColor.GREEN + getDescription().getName() + ChatColor.RESET + "] ";
    String Pluginname = "[" + getDescription().getName() +"] ";
    public ConsoleLog cLog = new ConsoleLog(this);
    public BlockBreakListener BreakListener = new BlockBreakListener(this);
    String folder = getDataFolder() + File.separator;
 
    boolean DebugMode = false;
    
    @Override
    public void onEnable(){
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(this, this);
        pm.registerEvents(this.BreakListener, this);
        cLog.info("DebugMode is now ["+DebugMode+"].");
    }
    @Override
    public void onDisable()
    {
        
    }
    @EventHandler
    public void onPlayerInspect(PlayerInteractEvent e){
        Player p = e.getPlayer();
        if(p.getInventory().getItemInHand()==null) return;
        Material Hand_m = p.getItemInHand().getType();
        Material[] Tools = {Material.STONE_PICKAXE,Material.IRON_PICKAXE,Material.GOLD_PICKAXE,Material.DIAMOND_PICKAXE};
        Block loc_b = e.getClickedBlock();
        Location loc = loc_b.getLocation();
        boolean containflag = false;
        if (DebugMode) {
            cLog.info("loc_b.getType=" + loc_b.getType());
            cLog.info("loc_b.X =" + loc_b.getX());
            cLog.info("loc_b.Y =" + loc_b.getY());
            cLog.info("loc_b.Z =" + loc_b.getZ());
        }

        if ( e.getAction() != Action.LEFT_CLICK_BLOCK || loc_b.getType() != Material.BEDROCK || loc.getBlockY()<=0)
            return;
        for (Material Tool : Tools) {
            if (Hand_m == Tool) {
                containflag = true;
                break;
            }
        }
        if (!containflag)
            return;

        if(p.hasPermission("bedrockbreak.advance")){

            loc_b.setType(Material.OBSIDIAN);
            p.playSound(loc, Sound.DIG_STONE, 1, 10);
            return;
        }
        else if(!p.hasPermission("bedrockbreak.advance")){
            if(loc.getBlockY()>5)
                return;
            loc_b.setType(Material.STONE);
            p.playSound(p.getLocation(), Sound.DIG_STONE, 1, 10);
            return;
        }
        else
        {
            if(DebugMode)
                cLog.debug("return");
            return;
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
