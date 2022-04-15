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

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import tim03we.futureplots.FuturePlots;
import tim03we.futureplots.events.PlotEnterEvent;
import tim03we.futureplots.events.PlotEvent;
import tim03we.futureplots.events.PlotLeaveEvent;
import tim03we.futureplots.utils.Language;
import tim03we.futureplots.utils.Plot;
import tim03we.futureplots.utils.Settings;

import java.util.HashMap;

public class PlayerMove extends Language implements Listener {

    // Username, FullID
    private static HashMap<String, String> plotId = new HashMap<>();

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if(Settings.levels != null && Settings.levels.contains(player.getWorld().getName())) {
            Plot plot = FuturePlots.getInstance().getPlotByPosition(event.getTo());
            Plot plotFrom = FuturePlots.getInstance().getPlotByPosition(event.getFrom());
            if(plot != null && plotFrom == null) {
                if(FuturePlots.provider.isDenied(player.getName(), plot) && !FuturePlots.provider.getOwner(plot).equals(player.getName()) && !FuturePlots.provider.isHelper(player.getName(), plot)) {
                    event.setCancelled(true);
                } else {
                    //new PlotEvent(new PlotEnterEvent(FuturePlots.getInstance(), plot, player));
                    if(Settings.popup) {
                        if(FuturePlots.provider.getOriginPlot(plot) != null && FuturePlots.provider.getMerges(plot).size() == 0) {
                            plot = FuturePlots.provider.getOriginPlot(plot);
                        } else if(FuturePlots.provider.getOriginPlot(plot) == null && FuturePlots.provider.getMerges(plot).size() > 0) {
                            plot = plot;
                        }
                        if(plotId.get(player.getName()) == null || !plotId.get(player.getName()).equalsIgnoreCase(plot.getFullID())) {
                            plotId.put(player.getName(), plot.getFullID());
                            if(FuturePlots.provider.hasOwner(plot)) {
                                player.sendActionBar(translate(false, "plot.enter.owned", plot.getX() + ";" + plot.getZ(), FuturePlots.provider.getOwner(plot)));
                            } else {
                                player.sendActionBar(translate(false, "plot.enter.free", plot.getX() + ";" + plot.getZ()));
                            }
                        }

                    }
                }
            } else if(plotFrom != null && plot == null) {
                //new PlotEvent(new PlotLeaveEvent(FuturePlots.getInstance(), plotFrom, player));
            }
        }
    }
}
