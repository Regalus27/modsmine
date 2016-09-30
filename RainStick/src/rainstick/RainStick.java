package rainstick;

import java.util.ArrayList;
import java.util.List;

import net.canarymod.api.world.TreeType;
import net.canarymod.plugin.Plugin;
import net.canarymod.logger.Logman;
import net.canarymod.Canary;
import net.canarymod.commandsys.*;
import net.canarymod.chat.MessageReceiver;
import net.canarymod.api.entity.Entity;
import net.canarymod.api.entity.living.humanoid.Player;
import net.canarymod.api.world.World;
import net.canarymod.api.world.position.Location;
import net.canarymod.api.world.position.Vector3D;
import net.canarymod.hook.HookHandler;
import net.canarymod.api.inventory.ItemType;
import net.canarymod.api.world.blocks.Block;
import net.canarymod.api.world.blocks.BlockType;
import net.canarymod.api.entity.EntityType;
import net.canarymod.api.entity.living.LivingBase;
import net.canarymod.api.entity.living.animal.Cow;
import net.canarymod.api.entity.living.monster.*;
import net.canarymod.hook.player.ItemUseHook;
import net.canarymod.plugin.PluginListener;
import com.pragprog.ahmine.ez.EZPlugin;
import net.canarymod.BlockIterator;
import net.canarymod.LineTracer;
import net.canarymod.api.world.position.Location;
import net.canarymod.api.entity.living.EntityLiving;





public class RainStick extends EZPlugin implements PluginListener {

    @Override
    public boolean enable() {
        Canary.hooks().registerListener(this, this);
        return super.enable(); // Call parent class's version too.
    }

    @HookHandler
    public void onInteract(ItemUseHook event) {
        Player me = event.getPlayer();
        List<EntityLiving> list = me.getWorld().getEntityLivingList();

        if (me.getItemHeld().getType() == ItemType.Stick) {

            me.getWorld().setRaining(true);
            me.getWorld().setRainTime(200);//10 seconds

            BlockIterator sightItr = new BlockIterator(new LineTracer(me), true);
            while (sightItr.hasNext()) {

                Block b = sightItr.next();
                if (b.getType() != BlockType.Air) {
                    b.getWorld().makeLightningBolt(b.getLocation());
                    b.getWorld().generateTree(b.getPosition(), TreeType.MEGAJUNGLE);
                    break;
                }
            }
        }
        if (me.getItemHeld().getType() == ItemType.Bone) {
            for (EntityLiving target : list) {
                World world = target.getWorld();
                if (target instanceof EntityMob) {
                    Location loc = target.getLocation();
                    me.getWorld().makeLightningBolt(loc);
                }
            }
        }
        if (me.getItemHeld().getType() == ItemType.OakLeaves) {
            for (EntityLiving target : list) {
                World world = target.getWorld();
                if (!(target instanceof Player)) {
                    Location loc = target.getLocation();
                    me.getWorld().makeExplosion(target, loc, 1.0f, true);
                }
            }
        }
    }
}