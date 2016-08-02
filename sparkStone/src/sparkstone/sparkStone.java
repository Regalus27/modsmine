package sparkstone;

import java.util.List;

import net.canarymod.api.entity.Entity;
import net.canarymod.api.entity.living.monster.EntityMob;
import net.canarymod.api.world.position.Location;
import net.canarymod.api.entity.living.EntityLiving;
import net.canarymod.api.world.blocks.BlockType;
import net.canarymod.hook.HookHandler;
import net.canarymod.hook.entity.EntityMoveHook;
import net.canarymod.plugin.Plugin;
import net.canarymod.logger.Logman;
import net.canarymod.Canary;
import net.canarymod.commandsys.*;
import net.canarymod.chat.MessageReceiver;
import net.canarymod.api.entity.living.humanoid.Player;
import com.pragprog.ahmine.ez.EZPlugin;
import net.canarymod.plugin.PluginListener;
import net.canarymod.api.world.World;

public class sparkStone extends EZPlugin implements PluginListener {
  static double x = 0;
  static double y = 0;
  static double z = 0;

  @Override
  public boolean enable() {
    Canary.hooks().registerListener(this, this);
    return super.enable();
  }

  @HookHandler
  public void onMove(EntityMoveHook event) {
    Entity target = event.getEntity();
    World world = target.getWorld();
    if (target instanceof EntityMob) {
        Location loc = target.getLocation();
        if (Math.sqrt(Math.pow((victim.getX() - x), 2) + Math.pow((victim.getY() - y), 2) + Math.pow((victim.getZ() - z), 2)) <= 10) {
          target.getWorld().makeLightningBolt(loc);
        }
    }
  }
}


  @Command(aliases = { "sparkstoneset" },
          description = "Set SparkStone location to 1 block over your head, attacks any monsters that move within 10 blocks",
          permissions = { "" },
          toolTip = "/sparkstoneset")
  public void sparkstoneCommand(MessageReceiver caller, String[] args) {
    if (caller instanceof Player) {
      Player me = (Player)caller;
      Location place = me.getLocation();
      x = me.getX();
      y = me.getY()+1;
      z = me.getZ();
      me.getWorld().setBlockAt(place.getBlockX(), place.getBlockY()-1, place.getBlockZ(), BlockType.Obsidian);
    }
  }