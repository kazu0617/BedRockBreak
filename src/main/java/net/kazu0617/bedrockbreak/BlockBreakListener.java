
package net.kazu0617.bedrockbreak;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

/**
 * @author     kazu0617
 * @license    Mozilla Public License Version 2.0
 * @copyright  Copyright kazu0617 2015-2017
 */
class BlockBreakListener implements Listener{
     Main plugin;
     public BlockBreakListener(Main instance)
     {
         this.plugin = instance;
     }
     
     @EventHandler
     public void onBlockBreak(BlockBreakEvent e) {
         Player p = e.getPlayer();
         Block Block = e.getBlock();
         if(p.hasPermission("bedrockbreak.advance") && Block.getType() == Material.OBSIDIAN && Block.getY()<=5){
             e.setCancelled(true);
             Block.setType(Material.AIR);
             p.getWorld().dropItem(Block.getLocation(), new ItemStack(Material.BEDROCK, 1));
             for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.getName().equals(p.getName())) continue;
                player.playSound(Block.getLocation(), Sound.BLOCK_STONE_BREAK, 0, 10);
                
             }
         }
         else if(p.hasPermission("bedrockbreak.auto")) {
             Location CL = Block.getLocation();
             for(int x = -50 ; x <=50 ; x++ ) {
                 CL.setX(Block.getLocation().getY()+x);
                 for(int z = -50 ; x <=50 ; z++ ) {
                     CL.setZ(Block.getLocation().getY()+z);
                     for(int y = 1 ; y<= 5 ; y++ ) {
                        CL.setY(y);
                         if(Block.getLocation().getBlock().getType()==Material.BEDROCK)
                             Block.getLocation().getBlock().setType(Material.STONE);
                     }
                 }
             }
         }
     }
}
