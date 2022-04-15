package tim03we.futureplots.listener;

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

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import tim03we.futureplots.FuturePlots;
import tim03we.futureplots.events.PlotBlockEvent;
import tim03we.futureplots.events.PlotEvent;
import tim03we.futureplots.utils.Plot;
import tim03we.futureplots.utils.Settings;

public class BlockPlace implements Listener {

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        if(Settings.levels.contains(player.getWorld().getName())) {
            Plot plot = FuturePlots.getInstance().getPlotByPosition(event.getBlock().getLocation());
            new PlotEvent(new PlotBlockEvent(FuturePlots.getInstance(), event, plot));
            if(!player.isOp()) {
                if(plot != null) {
                    if(!plot.canInteract(player)) {
                        event.setCancelled(true);
                    }
                } else {
                    Plot merge = FuturePlots.getInstance().isInMergeCheck(block.getLocation());
                    if(merge == null) {
                        event.setCancelled(true);
                    } else {
                        if(FuturePlots.provider.getOriginPlot(merge) != null && FuturePlots.provider.getMerges(merge).isEmpty()) {
                            merge = FuturePlots.provider.getOriginPlot(merge);
                        }
                        if(!merge.canInteract(player)) {
                            event.setCancelled(true);
                        }
                    }
                }
            }
        }
    }
}
