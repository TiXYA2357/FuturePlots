package tim03we.futureplots.commands;

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
import cn.nukkit.command.CommandSender;
import tim03we.futureplots.FuturePlots;
import tim03we.futureplots.utils.PlotPlayer;
import tim03we.futureplots.utils.Settings;

import static tim03we.futureplots.utils.Settings.max_plots;

public class ClaimCommand extends BaseCommand {

    public ClaimCommand(String name, String description, String usage) {
        super(name, description, usage);
    }

    @Override
    public void execute(CommandSender sender, String command, String[] args) {
        if(sender instanceof Player) {
            if(new PlotPlayer((Player) sender).onPlot()) {
                if(FuturePlots.getInstance().claimAvailable((Player) sender) == -1 || FuturePlots.getInstance().claimAvailable((Player) sender) >= max_plots) {
                    if (FuturePlots.provider.getHomes(sender.getName()).size() != Settings.max_plots) {
                        if (!FuturePlots.provider.hasOwner(FuturePlots.getInstance().getPlotByPosition(((Player) sender).getPosition()))) {
                            if (!FuturePlots.provider.isOwner(sender.getName(), FuturePlots.getInstance().getPlotByPosition(((Player) sender).getPosition()))) {
                                FuturePlots.provider.claimPlot(sender.getName(), FuturePlots.getInstance().getPlotByPosition(((Player) sender).getPosition()));
                                sender.sendMessage(translate(true, "plot-claimed"));
                            } else {
                                sender.sendMessage(translate(true, "plot-already-claimed"));
                            }
                        } else {
                            sender.sendMessage(translate(true, "plot-already-claimed"));
                        }
                    } else {
                        sender.sendMessage(translate(true, "max-plots"));
                    }
                } else {
                    sender.sendMessage(translate(true, "max-plots"));
                }
            } else {
                sender.sendMessage(translate(true, "not-in-plot", null));
            }
        }
    }
}
