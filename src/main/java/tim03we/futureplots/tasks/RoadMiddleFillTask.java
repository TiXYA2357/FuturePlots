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

public class RoadMiddleFillTask implements Runnable {

    private FuturePlots plugin;
    private Plot start;
    private Plot end;
    private World level;
    private int height;
    private Location plotBeginPos;
    private int xMax;
    private int zMax;
    private Material roadBlock, groundBlock, bottomBlock;
    private int maxBlocksPerTick;
    private Location pos;
    private boolean fillCorner;
    private BlockFace cornerDirection;
    private Location startPos;
    private Location endPos;

    public RoadMiddleFillTask(FuturePlots plugin, Plot start, Plot end, boolean fillCorner, BlockFace cornerDirection, int maxBlocksPerTick) {
        this.plugin = plugin;
        this.start = start;
        this.end = end;
        this.fillCorner = fillCorner;
        this.cornerDirection = cornerDirection;

        PlotSettings plotSettings = new PlotSettings(start.getLevelName());
        int plotSize = plotSettings.getPlotSize();
        int roadWidth = plotSettings.getRoadWidth();

        this.startPos = plugin.getPlotPosition(start);
        this.endPos = plugin.getPlotPosition(end).add(plotSize - 1, 0, plotSize - 1);

        this.xMax = endPos.getBlockX();
        this.zMax = endPos.getBlockZ();
        this.plotBeginPos = plugin.getPlotPosition(start);
        this.level = plotBeginPos.getWorld();

        this.height = plotSettings.getGroundHeight();
        this.roadBlock = plotSettings.getPlotFloorBlock();
        this.groundBlock = plotSettings.getPlotFillBlock();
        this.bottomBlock = plotSettings.getBottomBlock();

        this.maxBlocksPerTick = 256;
        this.pos = new Location(this.level, this.plotBeginPos.getX(), 0, this.plotBeginPos.getZ());
    }

    @Override
    public void run() {
        while (this.pos.getX() > this.xMax) {
            while (this.pos.getZ() > this.zMax) {
                while (this.pos.getY() < 255) {
                    Material block;
                    if(this.pos.getY() == 0) {
                        block = this.bottomBlock;
                    } else if(this.pos.getY() < this.height) {
                        block = this.groundBlock;
                    } else if(this.pos.getY() == this.height) {
                        block = this.roadBlock;
                    } else {
                        block = Material.AIR;
                    }

                    this.level.getBlockAt(this.pos).setType(block);
                    this.pos.add(0, 1, 0);
                }
                this.pos.setY(0);
                this.pos.add(0, 0, -1);
            }
            this.pos.setZ(this.plotBeginPos.getZ());
            this.pos.add(-1, 0, 0);
        }
    }
}
