package tim03we.futureplots;

/*
 * This software is distributed under "GNU General Public License v3.0".
 * This license allows you to use it and/or modify it but you are not at
 * all allowed to sell this plugin at any cost. If found doing so the
 * necessary action required would be taken.
 *
 * GunGame is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License v3.0 for more details.
 *
 * You should have received a copy of the GNU General Public License v3.0
 * along with this program. If not, see
 * <https://opensource.org/licenses/GPL-3.0>.
 */

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.*;
import cn.nukkit.event.entity.*;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.event.player.PlayerMoveEvent;
import cn.nukkit.item.ItemEdible;
import tim03we.futureplots.events.PlotBlockEvent;
import tim03we.futureplots.events.PlotEnterEvent;
import tim03we.futureplots.events.PlotEvent;
import tim03we.futureplots.events.PlotLeaveEvent;
import tim03we.futureplots.utils.*;

import java.util.ArrayList;
import java.util.List;

public class EventListener extends Language implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if(Settings.levels != null && Settings.levels.contains(player.getLevel().getName())) {
            Plot plot = FuturePlots.getInstance().getPlotByPosition(event.getTo());
            Plot plotFrom = FuturePlots.getInstance().getPlotByPosition(event.getFrom());
            if(plot != null && plotFrom == null) {
                if(FuturePlots.provider.isDenied(player.getName(), plot) && !FuturePlots.provider.isOwner(player.getName(), plot) && !FuturePlots.provider.isHelper(player.getName(), plot)) {
                    event.setCancelled(true);
                } else {
                    new PlotEvent(new PlotEnterEvent(FuturePlots.getInstance(), plot, player));
                    if(Settings.popup) {
                        if(FuturePlots.provider.hasOwner(plot)) {
                            player.sendPopup(translate(false, "plot.enter.owned", plot.getX() + ";" + plot.getZ(), FuturePlots.provider.getPlotName(plot)));
                        } else {
                            player.sendPopup(translate(false, "plot.enter.free", plot.getX() + ";" + plot.getZ()));
                        }
                    }
                }
            } else if(plotFrom != null && plot == null) {
                new PlotEvent(new PlotLeaveEvent(FuturePlots.getInstance(), plot, player));
            }
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if(Settings.levels.contains(player.getLevel().getName())) {
            Plot plot = FuturePlots.getInstance().getPlotByPosition(event.getBlock().getLocation());
            new PlotEvent(new PlotBlockEvent(FuturePlots.getInstance(), event, plot));
            if(!player.isOp()) {
                if(plot != null) {
                    if(!plot.canInteract(player)) {
                        event.setCancelled(true);
                    }
                } else {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if(Settings.levels.contains(player.getLevel().getName())) {
            Plot plot = FuturePlots.getInstance().getPlotByPosition(event.getBlock().getLocation());
            new PlotEvent(new PlotBlockEvent(FuturePlots.getInstance(), event, plot));
            if(!player.isOp()) {
                if(plot != null) {
                    if(!plot.canInteract(player)) {
                        event.setCancelled(true);
                    }
                } else {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if(Settings.levels.contains(player.getLevel().getName())) {
            if(!player.isOp()) {
                Plot plot = FuturePlots.getInstance().getPlotByPosition(event.getBlock().getLocation());
                if(event.getItem() instanceof ItemEdible && event.getAction() == PlayerInteractEvent.Action.RIGHT_CLICK_AIR) {
                    return;
                }
                if(plot != null) {
                    if(!plot.canInteract(player)) {
                        if(event.getItem().canBeActivated() || event.getBlock().canBeActivated()) {
                            event.setCancelled(true);
                        } else {
                            plot = new PlotPlayer(player).getPlot();
                            if(plot == null || !plot.canInteract(player)) {
                                event.setCancelled(true);
                            }
                        }
                    }
                } else {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onShoot(EntityShootBowEvent event) {
        Entity entity = event.getEntity();
        if(entity instanceof Player) {
            if(Settings.levels.contains(entity.getLevel().getName())) {
                if(!((Player) entity).isOp()) {
                    Plot plot = new PlotPlayer((Player) entity).getPlot();
                    if(plot == null || !plot.canInteract((Player) entity)) {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onExplosion(EntityExplodeEvent event) {
        Entity entity = event.getEntity();
        if(event.isCancelled()) return;
        if(Settings.levels.contains(entity.getLevel().getName())) {
            ArrayList<Block> allowedBlocks = new ArrayList<>();
            for (Block block : event.getBlockList()) {
                Plot plot = FuturePlots.getInstance().getPlotByPosition(block.getLocation());
                if(plot != null) {
                    allowedBlocks.add(block);
                }
            }
            event.setBlockList(allowedBlocks);
        }
    }

    @EventHandler
    public void onLiquid(LiquidFlowEvent event) {
        if (event.isCancelled()) return;
        if(Settings.levels.contains(event.getBlock().getLevel().getName())) {
            Plot plot = FuturePlots.getInstance().getPlotByPosition(event.getBlock().getLocation());
            if(plot == null) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPiston(BlockPistonEvent event) {
        if(Settings.levels.contains(event.getBlock().getLevel().getName())) {
            List<Block> blocks = event.getBlocks();
            for (Block block : blocks) {
                Plot plot = FuturePlots.getInstance().getPlotByPosition(block.getLocation());
                if(plot == null) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
