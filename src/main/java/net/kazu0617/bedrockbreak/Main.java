/**
 * @author     kazu0617
 * @license    LGPLv3
 * @copyright  Copyright kazu0617 2015
 */
package net.kazu0617.bedrockbreak;

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
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * @version 0.1
 * @author kazu0617
 * @copyright Copyright kazu0617 2015
 */
public class Main extends JavaPlugin implements Listener
{
    String Pluginprefix = "[" + ChatColor.GREEN + getDescription().getName() + ChatColor.RESET + "] ";
    String Pluginname = "[" + getDescription().getName() +"] ";
    public ConsoleLog cLog = new ConsoleLog(this);
 
    boolean DebugMode = false;
    boolean PreMode = false;
    
    @Override
    public void onEnable()
    {
        getServer().getPluginManager().registerEvents(this, this);
        cLog.info("DebugMode is now ["+DebugMode+"].");
        cLog.info("PreMode is now ["+PreMode+"].");
    }
    @Override
    public void onDisable()
    {
        
    }
    @EventHandler
    public void PlayerInspectMenu(PlayerInteractEvent e)
    {
        Player p = e.getPlayer();
        Material Hand_m = p.getItemInHand().getType();
        Material[][] Tools = new Material[5][4];
        Tools[0][0] = Material.WOOD_AXE;
        Tools[0][1] = Material.WOOD_PICKAXE;
        Tools[0][2] = Material.WOOD_SPADE;
        Tools[0][3] = Material.WOOD_SWORD;
        Tools[1][0] = Material.STONE_AXE;
        Tools[1][1] = Material.STONE_PICKAXE;
        Tools[1][2] = Material.STONE_SPADE;
        Tools[1][3] = Material.STONE_SWORD;
        Tools[2][0] = Material.IRON_AXE;
        Tools[2][1] = Material.IRON_PICKAXE;
        Tools[2][2] = Material.IRON_SPADE;
        Tools[2][3] = Material.IRON_SWORD;
        Tools[3][0] = Material.GOLD_AXE;
        Tools[3][1] = Material.GOLD_PICKAXE;
        Tools[3][2] = Material.GOLD_SPADE;
        Tools[3][3] = Material.GOLD_SWORD;
        Tools[4][0] = Material.DIAMOND_AXE;
        Tools[4][1] = Material.DIAMOND_PICKAXE;
        Tools[4][2] = Material.DIAMOND_SPADE;
        Tools[4][3] = Material.DIAMOND_SWORD;
        Action action = e.getAction();
        boolean containflag = false;
        for(int i = 1; i <= 4; i++)
        {
            for(int i2 = 1; i2 <= 2; i2++)
            {
                 if(Hand_m == Tools[i][i2])
                {
                    containflag = true;
                    break;
                }
            }
            if(containflag == true)
                break;
        }
        if (!containflag)
        {
            if (DebugMode) cLog.debug("containflag is " + containflag + ",so return.");
            return;
        }
        if(action == Action.LEFT_CLICK_BLOCK )
        {
            Block loc_b = e.getClickedBlock();
            if(DebugMode)
            {
                cLog.info("loc_b.getType="+loc_b.getType());
                cLog.info("loc_b.X ="+ loc_b.getX());
                cLog.info("loc_b.Y ="+ loc_b.getY());
                cLog.info("loc_b.Z ="+ loc_b.getZ());
            }
            Location loc = loc_b.getLocation();
            if(loc.getBlockY()<=0)
            {
                cLog.Message(p, ChatColor.DARK_RED+"高度0の岩盤は壊せません");
                    return;
            }
            Block loc2b = loc.getBlock();
            if(DebugMode)
            {
                cLog.info("loc2b.getType="+loc2b.getType());
                cLog.info("loc2b.X ="+ loc2b.getX());
                cLog.info("loc2b.Y ="+ loc2b.getY());
                cLog.info("loc2b.Z ="+ loc2b.getZ());

            }
            if(loc2b.getType()!=Material.BEDROCK)
            {
                if(DebugMode)
                    cLog.debug("loc2b= "+loc2b.getType()+", return.");
                return;
            }
            if(PreMode)
            {
               p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 1, Integer.MAX_VALUE));
               return;
            }
            else if(!PreMode)
            {
                loc_b.setType(Material.STONE);
                p.playSound(p.getLocation(), Sound.CLICK, 1, 1);
                return;
            }
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
        else if((args.length == 1) && "PreMode".equalsIgnoreCase(args[0]))
        {
            if(PreMode) PreMode = false;
            else if(!DebugMode) PreMode = true;
            cLog.Message(sender,"PreMode is now ["+PreMode+"].");
        }
        
        return true;
            
    }
}
