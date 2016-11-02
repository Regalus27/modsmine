package blink;
import net.canarymod.BlockIterator;
import net.canarymod.LineTracer;
import net.canarymod.api.world.World;
import net.canarymod.api.world.blocks.Block;
import net.canarymod.api.world.blocks.BlockType;
import net.canarymod.api.world.effects.SoundEffect;
import net.canarymod.api.world.position.Direction;
import net.canarymod.hook.player.TeleportHook;
import net.canarymod.plugin.Plugin;
import net.canarymod.logger.Logman;
import net.canarymod.Canary;
import net.canarymod.commandsys.*;
import net.canarymod.chat.MessageReceiver;
import net.canarymod.api.entity.living.humanoid.Player;
import com.pragprog.ahmine.ez.EZPlugin;
import net.canarymod.api.world.position.Location;

public class Blink extends EZPlugin {
  
  @Command(aliases = { "b" },
            description = "blink plugin",
            permissions = { "*" },
            toolTip = "/b")
  public void blinkCommand(MessageReceiver caller, String[] parameters) {
    if (caller instanceof Player) { 
      Player me = (Player)caller;

      int onID = me.getWorld().getBlockAt((int) me.getX(), (int) me.getY()-1, (int) me.getZ()).getTypeId();
      //int dir = me.getCardinalDirection().getIntValue();


      int index = 0;
      BlockIterator sightItr = new BlockIterator(new LineTracer(me), true);
      while (sightItr.hasNext()) {
        Block b = sightItr.next();
        if (b.getType() != BlockType.Air || index > 50) {
          int teleX = (int) b.getX();
          int teleY = (int) b.getY() + 1;
          int teleZ = (int) b.getZ();
          float pitch = me.getPitch();
          float hRot = me.getHeadRotation();
          float rot = me.getRotation();
          me.teleportTo(teleX, teleY, teleZ);
          me.setPitch(pitch);

          me.setHeadRotation(hRot);

          me.setRotation(rot);

          playSound(b.getLocation(), SoundEffect.Type.LAVA_POP);
          playSound(b.getLocation(), SoundEffect.Type.NOTE_BASS);
          break;
        }
        index++;
      }

      //see mD for dirs and xz values
    }//End if player
  }//End blinkCommand
}//end Blink class
