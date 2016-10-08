package wings;
import net.canarymod.BlockIterator;
import net.canarymod.LineTracer;
import net.canarymod.api.DamageSource;
import net.canarymod.api.DamageType;
import net.canarymod.api.entity.Entity;
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
import net.canarymod.hook.entity.DamageHook;
import net.canarymod.hook.player.ItemUseHook;
import net.canarymod.hook.player.PlayerMoveHook;
import net.canarymod.plugin.Plugin;
import net.canarymod.logger.Logman;
import net.canarymod.Canary;
import net.canarymod.commandsys.*;
import net.canarymod.chat.MessageReceiver;
import net.canarymod.api.entity.living.humanoid.Player;
import com.pragprog.ahmine.ez.EZPlugin;
import net.canarymod.plugin.PluginListener;
import net.minecraft.potion.PotionEffect;

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
    World world = me.getWorld();

    if (me.getItemHeld().getType() == ItemType.Feather) {
      me.setMotionY(me.getMotionY()+.6);
    }

  }

  @HookHandler
  public void playerMove(PlayerMoveHook event) {
    Player me = event.getPlayer();
    if(me.getInventory().hasItem(ItemType.GhastTear) && me.getMotionY() < (-.3)) {
      me.setMotionY(0);
    }
    if(me.getItemHeld().getType() == ItemType.Feather){
      me.addPotionEffect(PotionEffectType.JUMP, 10, 10);
      me.addPotionEffect(PotionEffectType.MOVESPEED, 10, 10);
    }
  }

  @HookHandler
  public void onDamage(DamageHook event) {
    //check if player
    //check if fall damage
      //cancel fall damage
    Entity thing = event.getDefender();
    if(thing instanceof Player) {
      //Player me = thing;
      if (((Player) thing).getItemHeld().getType() == ItemType.Feather) {
        event.setDamageDealt(0);
      }
      if (((Player) thing).getInventory().hasItem(ItemType.GhastTear)) {
        event.setDamageDealt(0);
      }
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
