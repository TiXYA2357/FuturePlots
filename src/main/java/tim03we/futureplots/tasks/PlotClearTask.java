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

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import tim03we.futureplots.FuturePlots;
import tim03we.futureplots.utils.Plot;
import tim03we.futureplots.utils.PlotSettings;

import java.util.concurrent.CompletableFuture;

public class PlotClearTask implements Runnable {

    private World level;
    private int height;
    private int plotSize;
    private Material bottomBlock;
    private Material plotFillBlock;
    private Material plotFloorBlock;
    private Location plotBeginPos;
    private int xMax;
    private int zMax;
    private Location pos;

    public PlotClearTask(Plot plot) {
        PlotSettings plotSettings = new PlotSettings(plot.getLevelName());
        this.plotBeginPos = FuturePlots.getInstance().getPlotPosition(plot);
        this.level = plotBeginPos.getWorld();
        this.plotSize = plotSettings.getPlotSize();
        this.xMax = (int) (plotBeginPos.getX() + plotSize);
        this.zMax = (int) (plotBeginPos.getZ() + plotSize);
        this.height = plotSettings.getGroundHeight();
        this.bottomBlock = plotSettings.getBottomBlock();
        this.plotFillBlock = plotSettings.getPlotFillBlock();
        this.plotFloorBlock = plotSettings.getPlotFloorBlock();
        this.pos = new Location(Bukkit.getWorld(plot.getLevelName()), plotBeginPos.getX(), 0, plotBeginPos.getZ());
    }

    @Override
    public void run() {
        CompletableFuture.runAsync(() -> {
            try {
                Material block;
                while (pos.getX() < xMax) {
                    while (pos.getZ() < zMax) {
                        while (pos.getY() < 256) {
                            if (pos.getY() == 0) {
                                block = bottomBlock;
                            } else if (pos.getY() < height) {
                                block = plotFillBlock;
                            } else if (pos.getY() == height) {
                                block = plotFloorBlock;
                            } else {
                                block = Material.getMaterial("AIR");
                                //block = Block.get(0);

                            }
                            pos.getBlock().setType(block);
                            //level.setBlockData(pos, block);
                            pos.add(0, 1, 0);
                        }
                        pos.setY(0);
                        pos.add(0, 0, 1);
                    }
                    pos.setZ(plotBeginPos.getZ());
                    pos.add(1, 0, 0);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
}
