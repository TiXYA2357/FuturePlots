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

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import tim03we.futureplots.FuturePlots;
import tim03we.futureplots.utils.Plot;
import tim03we.futureplots.utils.Settings;

public class LiquidFlow implements Listener {
/*
    @EventHandler
    public void onLiquid( event) {
        if (event.isCancelled()) return;
        Block block = event.getBlock();
        if(Settings.levels.contains(block.getLevel().getName())) {
            Plot plot = FuturePlots.getInstance().getPlotByPosition(block.getLocation());
            if(plot == null) {
                Plot merge = FuturePlots.getInstance().isInMergeCheck(block.getLocation());
                if(merge == null) {
                    event.setCancelled(true);
                }
            }
        }
    }*/
}
