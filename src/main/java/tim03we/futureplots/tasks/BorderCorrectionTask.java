package tim03we.futureplots.tasks;

/*
 * This software is distributed under "GNU General Public License v3.0".
 * This license allows you to use it and/or modify it but you are not at
 * all allowed to sell this plugin at any cost. If found doing so the
 * necessary action required would be taken.
 *
 * FuturePlots is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License v3.0 for more details.
 *
 * You should have received a copy of the GNU General Public License v3.0
 * along with this program. If not, see
 * <https://opensource.org/licenses/GPL-3.0>.
 */

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import tim03we.futureplots.FuturePlots;
import tim03we.futureplots.utils.Plot;
import tim03we.futureplots.utils.PlotSettings;

public class BorderCorrectionTask implements Runnable {

    private FuturePlots plugin;
    private Plot start;
    private World level;
    private int height;
    private Material plotWallBlock;
    private Location plotBeginPos;
    private int xMax;
    private int zMax;
    private BlockFace direction;
    private Material roadBlock;
    private Material groundBlock;
    private Material bottomBlock;
    private Plot end;
    private Location pos;

    public BorderCorrectionTask(FuturePlots plugin, Plot start, Plot end) {
        /*this.plugin = plugin;
        this.start = start;
        this.end = end;

        this.plotBeginPos = plugin.getPlotPosition(start);
        this.level = plotBeginPos.getWorld();

        PlotSettings plotSettings = new PlotSettings(start.getLevelName());
        int plotSize = plotSettings.getPlotSize();
        int roadWidth = plotSettings.getRoadWidth();
        this.height = plotSettings.getGroundHeight();
        this.plotWallBlock = plotSettings.getWallBlockClaimed();
        this.roadBlock = plotSettings.getRoadBlock();
        this.groundBlock = plotSettings.getPlotFillBlock();
        this.bottomBlock = plotSettings.getBottomBlock();

        if((start.getZ() - end.getZ()) == 1) {
            this.plotBeginPos = this.plotBeginPos.subtract(0, 0, roadWidth);
            this.xMax = this.plotBeginPos.getBlockX() + plotSize;
            this.zMax = this.plotBeginPos.getBlockZ() + roadWidth;
            this.direction = BlockFace.NORTH;
        } else if((start.getX() - end.getX()) == -1) {
            this.plotBeginPos = this.plotBeginPos.add(plotSize, 0, 0);
            this.xMax = this.plotBeginPos.getBlockX() + roadWidth;
            this.zMax = this.plotBeginPos.getBlockZ() + plotSize;
            this.direction = BlockFace.EAST;
        } else if((start.getZ() - end.getZ()) == -1) {
            this.plotBeginPos = this.plotBeginPos.add(0, 0, plotSize);
            this.xMax = this.plotBeginPos.getBlockX() + plotSize;
            this.zMax = this.plotBeginPos.getBlockZ() + roadWidth;
            this.direction = BlockFace.SOUTH;
        } else if((start.getX() - end.getX()) == 1) {
            this.plotBeginPos = this.plotBeginPos.subtract(roadWidth, 0,0);
            this.xMax = this.plotBeginPos.getBlockX() + roadWidth;
            this.zMax = this.plotBeginPos.getBlockZ() + plotSize;
            this.direction = BlockFace.WEST;
        }
        this.pos = new Location(this.plotBeginPos.getWorld(), this.plotBeginPos.getX(), 0, this.plotBeginPos.getZ());*/
    }

    @Override
    public void run() {
       /* if(this.direction == BlockFace.NORTH || this.direction == BlockFace.SOUTH) {
            while(this.pos.getZ() < this.zMax) {
                while(this.pos.getY() < 255) {
                    Material block;
                    if(this.pos.getY() > this.height + 1) {
                        block = Material.AIR;
                    } else if(this.pos.getY() == this.height + 1) {
                        //block = this.plotWallBlock;
                        block = this.plotWallBlock;
                    } else if(this.pos.getY() == this.height) {
                        block = this.roadBlock;
                    } else if(this.pos.getY() == 0) {
                        block = this.bottomBlock;
                    } else {
                        block = this.groundBlock;
                    }
                    this.level.getBlockAt(new Location(this.level, this.pos.getX() - 1, this.pos.getY(), this.pos.getZ())).setType(block);
                    this.level.getBlockAt(new Location(this.level, this.xMax, this.pos.getY(), this.pos.getZ())).setType(block);
                    this.pos.add(0, 1, 0);
                }
                this.pos.setY(0);
                this.pos.add(0, 0, 1);
            }
        } else {
            while (this.pos.x < this.xMax) {
                while (this.pos.y < 255) {
                    Block block;
                    if(this.pos.y > this.height + 1) {
                        block = Block.get(BlockID.AIR);
                    } else if(this.pos.y == this.height + 1) {
                        block = this.plotWallBlock;
                    } else if(this.pos.y == this.height) {
                        block = this.roadBlock;
                    } else if(this.pos.y == 0) {
                        block = this.bottomBlock;
                    } else {
                        block = this.groundBlock;
                    }
                    this.level.setBlock(new Vector3(this.pos.x, this.pos.y, this.pos.z - 1), block, false);
                    this.level.setBlock(new Vector3(this.pos.x, this.pos.y, this.zMax), block, false);
                    this.pos.y++;
                }
                this.pos.y = 0;
                this.pos.x++;
            }
        }*/
    }
}
