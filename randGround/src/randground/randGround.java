package randground;
import net.canarymod.api.world.blocks.BlockType;
import net.canarymod.plugin.Plugin;
import net.canarymod.logger.Logman;
import net.canarymod.api.world.*;
import net.canarymod.Canary;
import net.canarymod.commandsys.*;
import net.canarymod.chat.MessageReceiver;
import net.canarymod.api.entity.living.humanoid.Player;
import com.pragprog.ahmine.ez.EZPlugin;

import java.time.Year;
import java.util.Random;

public class randGround extends EZPlugin {

  private void parseArgs(Player me, String[] args) {
    if (args.length != 2) {
      me.chat("Only one height!");
      return; // leaves function
    }
    World world = me.getWorld();
    Random rand = new Random();
    int h = 50;
    int w = 50;
    int[][] earth = new int[h][w]; //filled with zeroes

    me.chat("Generating maze...");
    for (int j = 0; j < 4; j++) {
      int curX = rand.nextInt(w);
      int curY = rand.nextInt(h);
      earth[curY][curX] = 1;
      int maxX = w - 2;
      int maxY = h - 2;
      int tries = h + w + (h * w);
      while (tries > 0) {
        int dir = rand.nextInt(4) + 1;
        if (dir == 1 && curY >= 1) {
          curY--;
          earth[curY][curX] = 1;
          tries--;
        } else if (dir == 2 && curX <= maxX) {
          curX++;
          earth[curY][curX] = 1;
          tries--;
        } else if (dir == 3 && curY <= maxY) {
          curY++;
          earth[curY][curX] = 1;
          tries--;
        } else if (dir == 4 && curX >= 1) {
          curX--;
          earth[curY][curX] = 1;
          tries--;
        }
      }

    }
    me.chat("Maze Generated...");

    int X = (int) me.getX();
    int Z = (int) me.getZ();
    for (int t = Integer.valueOf(args[1]); t < Integer.valueOf(args[1]) + 3; t++) {
      for (int y = 0; y < h; y++) {
        for (int x = 0; x < w; x++) {
          if (earth[y][x] == 0) {
            world.setBlockAt(x+X, t, y+Z, BlockType.MossyStoneBrick);
          } else {
            world.setBlockAt(x+X, t, y+Z, BlockType.Air);
          }
          world.setBlockAt(x+X, Integer.valueOf(args[1]) - 1, y+ Z, BlockType.MossyStoneBrick);
          if (y % 7 == 0 && x % 7 == 0) {
            world.setBlockAt(x+X, Integer.valueOf(args[1]) + 3, y+Z, BlockType.GlowStone);
          } else {
            world.setBlockAt(x+X, Integer.valueOf(args[1]) + 3, y+Z, BlockType.MossyStoneBrick);
          }
        }
      }
    }
    me.chat("Maze built...");
    world.setBlockAt((int) me.getX()-1, Integer.valueOf(args[1]) + 1, (int) me.getZ(), BlockType.MossyStoneBrick);
    me.teleportTo((int) me.getX(), Integer.valueOf(args[1]) + 2, (int) me.getZ());
  }



  @Command(aliases = {"randground"},
          description = "random ground pattern",
          permissions = {"*"},
          toolTip = "/randground height")
  public void randgroundCommand(MessageReceiver caller, String[] parameters) {
    if (caller instanceof Player) {
      Player me = (Player) caller;
      parseArgs(me, parameters);
    }
  }
}