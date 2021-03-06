package wings;
import net.canarymod.BlockIterator;
import net.canarymod.LineTracer;
import net.canarymod.api.DamageSource;
import net.canarymod.api.DamageType;
import net.canarymod.api.entity.Entity;
import net.canarymod.api.entity.living.EntityLiving;
import net.canarymod.api.entity.living.LivingBase;
import net.canarymod.api.entity.living.monster.EntityMob;
import net.canarymod.api.inventory.Item;
import net.canarymod.api.inventory.ItemType;
import net.canarymod.api.potion.PotionEffectType;
import net.canarymod.api.world.TreeType;
import net.canarymod.api.world.World;
import net.canarymod.api.world.blocks.Block;
import net.canarymod.api.world.blocks.BlockType;
import net.canarymod.api.world.effects.Particle;
import net.canarymod.api.world.position.Location;
import net.canarymod.hook.HookHandler;
import net.canarymod.hook.entity.DamageHook;
import net.canarymod.hook.player.ItemUseHook;
import net.canarymod.hook.player.PlayerMoveHook;
import net.canarymod.hook.world.TimeChangeHook;
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
  boolean invisible = false;

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

    if (me.getItemHeld().getType() == ItemType.BlazeRod){
      List<EntityMob> mob = me.getWorld().getMobList();
      for (EntityMob monster:mob) {
        if(Math.sqrt(Math.pow((monster.getX() - me.getX()),2) + Math.pow((monster.getY() - me.getY()),2) + Math.pow((monster.getZ() - me.getZ()), 2))<10) {
          spawnParticle(monster.getLocation(), Particle.Type.LAVA);
          monster.destroy();
        }
      }
    }

  }
  //find a way to udpate invisibility continuously
  //create potion effect for invisibility Integer.MAX_VALUE time, then cancel it when far enough away from blocks of that type
  @HookHandler
  public void playerMove(PlayerMoveHook event) {
    Player me = event.getPlayer();
    World world = me.getWorld();
    if(me.getInventory().hasItem(ItemType.GhastTear)) {
      me.addPotionEffect(PotionEffectType.JUMP, 60, 4);
      me.addPotionEffect(PotionEffectType.MOVESPEED, 60, 5);
    }
    //me.chat("Item held:");
    //me.chat(java.lang.String.valueOf(me.getItemHeld()));
    //me.chat(String.valueOf(me.getItemHeld().getType().getId()));
    //me.chat(String.valueOf(me.getItemHeld().getId()));
    int[] area = new int[125];
    int index = 0;
    for(int x = -2; x < 3; x++){
      for(int y = -2; y < 3; y++){
        for(int z = -2; z < 3; z++){
          area[index] = world.getBlockAt((int) me.getX()+x, (int) me.getY()+y, (int) me.getZ()+z).getType().getId();
          index++;
        }
      }
    }
    invisible = false;
    for(int id:area){
      if (me.getItemHeld().getId() == id){
        invisible = true;
        //me.chat(java.lang.String.valueOf(id));
        //me.chat(java.lang.String.valueOf(invisible));
      }
    }
    //me.chat(java.lang.String.valueOf(invisible));
    if(invisible){
      me.addPotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 5);
    }
    else{
      me.removePotionEffect(PotionEffectType.INVISIBILITY);
    }
  }

  @HookHandler
  public void onDamage(DamageHook event) {
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
