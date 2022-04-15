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
import tim03we.futureplots.FuturePlots;
import tim03we.futureplots.utils.Plot;
import tim03we.futureplots.utils.PlotSettings;

import java.util.concurrent.CompletableFuture;

public class PlotSetBorderTask implements Runnable {

    private World level;
    private int height;
    private Material plotWallBlock;
    private Location plotBeginPos;
    private double xMax, zMax;

    public PlotSetBorderTask(Plot plot, Material block) {
        PlotSettings plotSettings = new PlotSettings(plot.getLevelName());
        this.plotBeginPos = FuturePlots.getInstance().getPlotPosition(plot);
        this.level = plotBeginPos.getWorld();
        this.plotBeginPos = plotBeginPos.subtract(1,0,1);
        int plotSize = plotSettings.getPlotSize();
        this.xMax = plotBeginPos.getX() + plotSize + 1;
        this.zMax = plotBeginPos.getZ() + plotSize + 1;
        this.height = plotSettings.getGroundHeight();
        this.plotWallBlock = block;
    }

    @Override
    public void run() {
        try {
            double x;
            double z;

            for (x = plotBeginPos.getX(); x <= xMax; x++) {
                level.getBlockAt(new Location(plotBeginPos.getWorld(), x, height + 1, plotBeginPos.getZ())).setType(plotWallBlock);
                level.getBlockAt(new Location(plotBeginPos.getWorld(), x, height + 1, zMax)).setType(plotWallBlock);
            }
            for (z = plotBeginPos.getZ(); z <= zMax; z++) {
                level.getBlockAt(new Location(plotBeginPos.getWorld(), plotBeginPos.getX(), height + 1, z)).setType(plotWallBlock);
                level.getBlockAt(new Location(plotBeginPos.getWorld(), xMax, height + 1, z)).setType(plotWallBlock);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
