package wings;
import net.canarymod.BlockIterator;
import net.canarymod.LineTracer;
import net.canarymod.api.entity.living.EntityLiving;
import net.canarymod.api.entity.living.LivingBase;
import net.canarymod.api.entity.living.monster.EntityMob;
import net.canarymod.api.inventory.ItemType;
import net.canarymod.api.potion.PotionEffectType;
import net.canarymod.api.world.TreeType;
import net.canarymod.api.world.World;
import net.canarymod.api.world.blocks.Block;
import net.canarymod.api.world.blocks.BlockType;
import net.canarymod.api.world.position.Location;
import net.canarymod.hook.HookHandler;
import net.canarymod.hook.player.ItemUseHook;
import net.canarymod.plugin.Plugin;
import net.canarymod.logger.Logman;
import net.canarymod.Canary;
import net.canarymod.commandsys.*;
import net.canarymod.chat.MessageReceiver;
import net.canarymod.api.entity.living.humanoid.Player;
import com.pragprog.ahmine.ez.EZPlugin;
import net.canarymod.plugin.PluginListener;

import java.util.List;

public class Wings extends EZPlugin implements PluginListener{

  @Override
  public boolean enable() {
    Canary.hooks().registerListener(this, this);
    return super.enable(); // Call parent class's version too.
  }

  @HookHandler
  public void onInteract(ItemUseHook event) {
    Player me = event.getPlayer();
    BlockIterator blockIterator = new BlockIterator(new LineTracer(me), true);

    if (me.getItemHeld().getType() == ItemType.Feather) {
      int dist = 50;
      double posX, posY, posZ;
      Block b = null;
      while (blockIterator.hasNext() && dist > 0){
        dist--;
        b = blockIterator.next();
      }
      posX = b.getX()-me.getX();
      posY = b.getY()-me.getY();
      posZ = b.getZ()-me.getZ();

      me.setMotionX(posX);
      me.setMotionY(posY);
      me.setMotionZ(posZ);
    }
  }















  /*public static void toss(LivingBase player, double factor) {
    double pitch = (player.getPitch() + 90.0F) * Math.PI / 180.0D;
    double rot = (player.getRotation() + 90.0F) * Math.PI / 180.0D;
    double x = Math.sin(pitch) * Math.cos(rot);
    double z = Math.sin(pitch) * Math.sin(rot);
    double y = Math.cos(pitch);

  }*/
}
