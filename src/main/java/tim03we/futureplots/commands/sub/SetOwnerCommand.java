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

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.CommandSender;
import tim03we.futureplots.FuturePlots;
import tim03we.futureplots.commands.BaseCommand;
import tim03we.futureplots.utils.*;

public class SetOwnerCommand extends BaseCommand {

    public SetOwnerCommand(String name, String description, String usage) {
        super(name, description, usage);
    }

    @Override
    public void execute(CommandSender sender, String command, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            Plot plot = new PlotPlayer(player).getPlot();
            if (plot != null) {
                String levelName = plot.getLevelName();
                if(args.length > 1) {
                    if (plot.canByPass(player)) {
                        Player target = Server.getInstance().getPlayerExact(args[1]);
                        String targetName = target == null ? args[1] : target.getName();
                        if(!FuturePlots.xuidProvider.exists(targetName)) {
                            player.sendMessage("Dieser Spieler existiert nicht§c!");
                            return;
                        }
                        String targetPlayerId = Utils.getPlayerId(targetName);
                        if(sender.isOp()) {
                            if(target != null) target.sendMessage(translate(true, "plot.setowner.target", plot.getX() + ";" + plot.getZ()));
                            if(!FuturePlots.provider.hasOwner(plot)) {
                                FuturePlots.provider.claimPlot(targetPlayerId, plot);
                                plot.changeBorder(PlotSettings.getWallBlockClaimed(levelName));
                            } else {
                                FuturePlots.provider.setOwner(targetPlayerId, plot);
                            }
                            if(FuturePlots.provider.getOriginPlot(plot) != null && FuturePlots.provider.getMerges(plot).size() == 0) {
                                plot = FuturePlots.provider.getOriginPlot(plot);
                            }
                            for (Plot mergePlot : FuturePlots.provider.getMerges(plot)) {
                                FuturePlots.provider.setOwner(targetPlayerId, mergePlot);
                            }
                            sender.sendMessage(translate(true, "plot.setowner", targetName));
                        } else {
                            if(target != null) {
                                if(FuturePlots.getInstance().canClaim(target)) {
                                    target.sendMessage(translate(true, "plot.setowner.target", plot.getX() + ";" + plot.getZ()));
                                    if(!FuturePlots.provider.hasOwner(plot)) {
                                        FuturePlots.provider.claimPlot(targetName, plot);
                                        plot.changeBorder(PlotSettings.getWallBlockClaimed(levelName));
                                    } else FuturePlots.provider.setOwner(targetName, plot);
                                    if(FuturePlots.provider.getOriginPlot(plot) != null && FuturePlots.provider.getMerges(plot).size() == 0) {
                                        plot = FuturePlots.provider.getOriginPlot(plot);
                                    }
                                    for (Plot mergePlot : FuturePlots.provider.getMerges(plot)) {
                                        FuturePlots.provider.setOwner(targetName, mergePlot);
                                    }
                                    sender.sendMessage(translate(true, "plot.setowner", target.getName()));
                                } else {
                                    sender.sendMessage(translate(true, "plot.setowner.target.max"));
                                }
                            } else {
                                sender.sendMessage(translate(true, "plot.setowner.target.not.online"));
                            }
                        }
                    } else {
                        sender.sendMessage(translate(true, "not.a.owner"));
                    }
                } else {
                    sender.sendMessage(getUsage());
                }
            } else {
                sender.sendMessage(translate(true, "not.in.plot"));
            }
        }
    }
}
