
package net.kazu0617.bedrockbreak;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

/**
 * @author     kazu0617
 * @license    MIT
 * @copyright  Copyright kazu0617 2015-2016
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
         if(p.hasPermission("bedrockbreak.advance") && Block.getType() == Material.OBSIDIAN && e.getBlock().getY()<=5){
             e.getPlayer().getWorld().dropItem(Block.getLocation(), new ItemStack(Material.BEDROCK, 1));
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
