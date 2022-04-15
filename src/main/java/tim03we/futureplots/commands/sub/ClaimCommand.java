package tim03we.futureplots.commands.sub;

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
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tim03we.futureplots.FuturePlots;
import tim03we.futureplots.commands.BaseCommand;
import tim03we.futureplots.utils.Plot;
import tim03we.futureplots.utils.PlotPlayer;
import tim03we.futureplots.utils.PlotSettings;
import tim03we.futureplots.utils.Settings;

import static tim03we.futureplots.utils.Settings.plotSize;

public class ClaimCommand extends BaseCommand {

    public ClaimCommand(String name, String description, String usage) {
        super(name, description, usage);
    }

    @Override
    public void execute(CommandSender sender, String command, String[] args) {
        if(sender instanceof Player) {
            PlotPlayer plotPlayer = new PlotPlayer((Player) sender);
            Plot plot = plotPlayer.getPlot();
            if(plot != null) {
                if(FuturePlots.getInstance().claimAvailable((Player) sender) == -1 || FuturePlots.provider.getPlots(sender.getName(), null).size() <= Settings.max_plots) {
                    if (!FuturePlots.provider.hasOwner(plot)) {
                        if(Settings.economy) {
                            if(!plotPlayer.bypassEco()) {
                                if((FuturePlots.economyProvider.getMoney(sender.getName()) - new PlotSettings(((Player) sender).getWorld().getName()).getClaimPrice()) >= 0) {
                                    FuturePlots.economyProvider.reduceMoney(sender.getName(), new PlotSettings(((Player) sender).getWorld().getName()).getClaimPrice());
                                } else {
                                    sender.sendMessage(translate(true, "economy.no.money"));
                                    return;
                                }
                            }
                        }
                        plot.changeBorder(new PlotSettings(((Player) sender).getWorld().getName()).getWallBlockClaimed());
                        FuturePlots.provider.claimPlot(sender.getName(), plot);
                        if(Settings.claim_tp) {
                            double x = plot.getPosition().getX();
                            double y = plot.getPosition().getY();
                            double z = plot.getPosition().getZ();
                            ((Player) sender).teleport(new Location(plot.getPosition().getWorld(), x += Math.floor(plotSize / 2), y += 1.5, z -= 1));
                        }
                        sender.sendMessage(translate(true, "plot.claim"));
                    } else {
                        sender.sendMessage(translate(true, "plot.claim.already"));
                    }
                } else {
                    sender.sendMessage(translate(true, "plot.max"));
                }
            } else {
                sender.sendMessage(translate(true, "not.in.plot"));
            }
        }
    }
}
