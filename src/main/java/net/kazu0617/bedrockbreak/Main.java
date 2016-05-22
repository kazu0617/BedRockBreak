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
    public void onEnable()
    {
        getServer().getPluginManager().registerEvents(this, this);
        cLog.info("DebugMode is now ["+DebugMode+"].");
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
        Block loc_b = e.getClickedBlock();
        Location loc = loc_b.getLocation();
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
        if(!p.hasPermission("bedrockbreak.advance") && action == Action.LEFT_CLICK_BLOCK)
        {
            if(DebugMode){
                cLog.info("loc_b.getType="+loc_b.getType());
                cLog.info("loc_b.X ="+ loc_b.getX());
                cLog.info("loc_b.Y ="+ loc_b.getY());
                cLog.info("loc_b.Z ="+ loc_b.getZ());
            }
            if(loc.getBlockY()<=0){
                cLog.Message(p, ChatColor.DARK_RED+"高度0の岩盤は壊せません");
                return;
            }
            if(loc.getBlockY()>5){
                cLog.Message(p, "いやソレどう考えても手動で置いてるでしょ(ヽ´ω`)");
                return;
            }
            if(loc_b.getType()!=Material.BEDROCK){
                if(DebugMode)
                    cLog.debug("loc_b= "+loc_b.getType()+", return.");
                return;
            }
            loc_b.setType(Material.STONE);
            p.playSound(p.getLocation(), Sound.CLICK, 1, 10);
            return;
        }
        else if(p.hasPermission("bedrockbreak.advance") && action == Action.LEFT_CLICK_BLOCK){
            if(loc.getBlockY()<=0){
                cLog.Message(p, ChatColor.DARK_RED+"高度0の岩盤は壊せません");
                return;
            }
            if(loc_b.getType() == Material.BEDROCK){
                loc_b.setType(Material.OBSIDIAN);
                p.playSound(loc, Sound.DIG_STONE, 1, 10);
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
        
        return true;
            
    }
}
