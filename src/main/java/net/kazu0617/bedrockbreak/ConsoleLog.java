package net.kazu0617.bedrockbreak;

import java.util.logging.Logger;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author     kazu0617
 * @license    MIT
 * @copyright  Copyright kazu0617 2015-2016
 */
public class ConsoleLog
{
    @SuppressWarnings("unused")
    private Main plugin;
    public Logger log = Logger.getLogger("Minecraft");
    public String cPrefix = "[BedRockBreak] ";

    public ConsoleLog(Main plugin) {
        this.plugin = plugin;
    }

    public void info(String Mess) {
        log.info(this.cPrefix + Mess);
    }
    public void debug(String Mess) {
        log.info(this.cPrefix + "[Debug] " +Mess);
    }

    public void warn(String Mess) {
        log.warning(this.cPrefix + Mess);
    }
    public void Message(CommandSender sender, String Text) {
        sender.sendMessage(plugin.Pluginprefix + Text);
    }
    public void Message(Player p, String Text) {
        p.sendMessage(plugin.Pluginprefix + Text);
    }
}
