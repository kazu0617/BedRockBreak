/*
 * The MIT License
 *
 * Copyright 2016 kazu0617<kazuyagi19990617@hotmail.co.jp>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package net.kazu0617.bedrockbreak;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author kazu0617<kazuyagi19990617@hotmail.co.jp>
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
         Material B = e.getBlock().getType();
         Location L = e.getBlock().getLocation();
         if(p.hasPermission("bedrockbreak.advance") && B == Material.OBSIDIAN &&L.getBlockY()<=5){
             e.getPlayer().getWorld().dropItem(L, new ItemStack(Material.BEDROCK, 1));
             plugin.cLog.Message(p, "Obsidianも落ちるけどソレはまあ、おまけということで");
         }
     }
}
