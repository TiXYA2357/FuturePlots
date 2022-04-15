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
import org.bukkit.World;
import tim03we.futureplots.FuturePlots;
import tim03we.futureplots.utils.Plot;
import tim03we.futureplots.utils.PlotSettings;

import java.util.concurrent.CompletableFuture;

public class PlotErodeTask implements Runnable {

    private World level;
    private int plotSize;
    private Location plotBeginPos;
    private int xMax;
    private int zMax;
    private Location pos;

    public PlotErodeTask(Plot plot) {
        /*PlotSettings plotSettings = new PlotSettings(plot.getLevelName());
        this.plotBeginPos = FuturePlots.getInstance().getPlotPosition(plot);
        this.level = plotBeginPos.getLevel();
        this.plotSize = plotSettings.getPlotSize();
        this.xMax = (int) (plotBeginPos.x + plotSize);
        this.zMax = (int) (plotBeginPos.z + plotSize);
        this.pos = new Position(plotBeginPos.x, 0, plotBeginPos.z, Server.getInstance().getLevelByName(plot.getLevelName()));*/
    }

    @Override
    public void run() {
        /*CompletableFuture.runAsync(() -> {
            try {
                while (pos.x < xMax) {
                    while (pos.z < zMax) {
                        while (pos.y < 256) {
                            if (pos.y > 0) {
                                level.setBlock(pos, Block.get(BlockID.AIR));
                            }
                            pos.y++;
                        }
                        pos.y = 0;
                        pos.z++;
                    }
                    pos.z = plotBeginPos.z;
                    pos.x++;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });*/
    }
}
