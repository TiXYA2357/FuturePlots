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

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import tim03we.futureplots.FuturePlots;
import tim03we.futureplots.utils.Plot;
import tim03we.futureplots.utils.PlotPlayer;
import tim03we.futureplots.utils.Settings;

public class PlayerInteract implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if(Settings.levels.contains(player.getWorld().getName())) {
            if(!player.isOp()) {
                if(event.getMaterial().isEdible() && event.getAction() == Action.RIGHT_CLICK_AIR) {
                    return;
                }
                Plot plot = null;
                if(event.getClickedBlock() != null) {
                    plot = FuturePlots.getInstance().getPlotByPosition(event.getClickedBlock().getLocation());
                }
                if(plot != null) {
                    if(!plot.canInteract(player)) {
                        if(event.getAction() == Action.PHYSICAL) {
                            event.setCancelled(true);
                            return;
                        }
                        /*if(event.getItem().canBeActivated() || event.getClickedBlock().canBeActivated()) {
                            event.setCancelled(true);
                        } else {
                            plot = new PlotPlayer(player).getPlot();
                            if(plot == null || !plot.canInteract(player)) {
                                Plot merge = FuturePlots.getInstance().isInMergeCheck(event.getBlock().getLocation());
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
                        }*/
                        plot = new PlotPlayer(player).getPlot();
                        if(plot == null || !plot.canInteract(player)) {
                            Plot merge = FuturePlots.getInstance().isInMergeCheck(event.getClickedBlock().getLocation());
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
                    } else {
                        if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                            if(event.getMaterial() == Material.DRAGON_EGG) {
                                event.setCancelled(true);
                            }
                        }
                    }
                } else {
                    if(event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_BLOCK) {
                        player.sendMessage(event.getClickedBlock().getLocation().toString());
                        Plot merge = FuturePlots.getInstance().isInMergeCheck(event.getClickedBlock().getLocation());
                        if(merge == null) {
                            System.out.println("LOL");
                            event.setCancelled(true);
                        } else {
                            if(FuturePlots.provider.getOriginPlot(merge) != null && FuturePlots.provider.getMerges(merge).isEmpty()) {
                                merge = FuturePlots.provider.getOriginPlot(merge);
                            }
                            if(!merge.canInteract(player)) {
                                System.out.println("LOL 2");
                                event.setCancelled(true);
                            }
                        }
                    }
                }
            }
        }
    }
}
