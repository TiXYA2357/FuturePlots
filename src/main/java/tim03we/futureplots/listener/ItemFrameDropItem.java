package tim03we.futureplots.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonEvent;
import tim03we.futureplots.FuturePlots;
import tim03we.futureplots.utils.Plot;
import tim03we.futureplots.utils.Settings;

public class ItemFrameDropItem implements Listener {

    @EventHandler
    public void on(BlockPistonEvent event) {
        /*Player player = event.getPlayer();
        if(Settings.levels.contains(player.getLevel().getName())) {
            Plot plot = FuturePlots.getInstance().getPlotByPosition(event.getItemFrame().getLocation());
            if(!player.isOp()) {
                if(plot != null) {
                    if(!plot.canInteract(player)) {
                        event.setCancelled(true);
                    }
                } else {
                    event.setCancelled(true);
                }
            }
        }*/
    }
}
