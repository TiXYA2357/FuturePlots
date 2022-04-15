package tim03we.futureplots.utils;

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

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import tim03we.futureplots.FuturePlots;
import tim03we.futureplots.utils.config.YamlConfig;

public class PlotSettings {

    private String levelName;

    public PlotSettings(String levelName) {
        this.levelName = levelName;
    }

    public YamlConfig getDefaultConfig() {
        return new YamlConfig(FuturePlots.getInstance().getDataFolder() + "/config.yml");
    }

    public YamlConfig getWorldConfig() {
        return new YamlConfig(FuturePlots.getInstance().getDataFolder() + "/worlds/" + this.levelName + ".yml");
    }

    public World getLevel() {
        return Bukkit.getWorld(levelName);
    }

    public Material getWallBlockClaimed() {
        String ex = getWorldConfig().getString("settings.wall.claimed");
        return Material.getMaterial(ex);
    }

    public Material getWallBlockUnClaimed() {
        String ex = getWorldConfig().getString("settings.wall.unclaimed");
        return Material.getMaterial(ex);
    }

    public Material getRoadBlock() {
        String ex = getWorldConfig().getString("settings.roadBlock");
        return Material.getMaterial(ex);
    }

    public int getRoadWidth() {
        return getWorldConfig().getInt("settings.roadWidth");
    }

    public int getGroundHeight() {
        return getWorldConfig().getInt("settings.groundHeight");
    }

    public int getPlotSize() {
        return getWorldConfig().getInt("settings.plotSize");
    }

    public Material getBottomBlock() {
        String ex = getWorldConfig().getString("settings.bottomBlock");
        return Material.getMaterial(ex);
    }

    public Material getPlotFloorBlock() {
        String ex = getWorldConfig().getString("settings.plotFloorBlock");
        return Material.getMaterial(ex);
    }

    public Material getPlotFillBlock() {
        String ex = getWorldConfig().getString("settings.plotFillBlock");
        return Material.getMaterial(ex);
    }

    public int getClaimPrice() {
        return getWorldConfig().getInt("settings.price.claim");
    }

    public int getClearPrice() {
        return getWorldConfig().getInt("settings.price.clear");
    }

    public int getDeletePrice() {
        return getWorldConfig().getInt("settings.price.delete");
    }

    public int getDisposePrice() {
        return getWorldConfig().getInt("settings.price.dispose");
    }

    public int getErodePrice() {
        return getWorldConfig().getInt("settings.price.erode");
    }

    public int getMergePrice() {
        return getWorldConfig().getInt("settings.price.merge");
    }

    public void initWorld() {
        YamlConfig defaultConfig = getDefaultConfig();
        YamlConfig worldConfig = new YamlConfig(FuturePlots.getInstance().getDataFolder() + "/worlds/" + this.levelName + ".yml");
        if(!worldConfig.exists("settings.wall.unclaimed")) worldConfig.set("settings.wall.unclaimed", defaultConfig.getString("default-settings.wall.unclaimed"));
        if(!worldConfig.exists("settings.wall.claimed")) worldConfig.set("settings.wall.claimed", defaultConfig.getString("default-settings.wall.claimed"));
        if(!worldConfig.exists("settings.roadBlock")) worldConfig.set("settings.roadBlock", defaultConfig.getString("default-settings.roadBlock"));
        if(!worldConfig.exists("settings.roadWidth")) worldConfig.set("settings.roadWidth", defaultConfig.getInt("default-settings.roadWidth"));
        if(!worldConfig.exists("settings.groundHeight")) worldConfig.set("settings.groundHeight", defaultConfig.getInt("default-settings.groundHeight"));
        if(!worldConfig.exists("settings.plotSize")) worldConfig.set("settings.plotSize", defaultConfig.getInt("default-settings.plotSize"));
        if(!worldConfig.exists("settings.bottomBlock")) worldConfig.set("settings.bottomBlock", defaultConfig.getString("default-settings.bottomBlock"));
        if(!worldConfig.exists("settings.plotFloorBlock")) worldConfig.set("settings.plotFloorBlock", defaultConfig.getString("default-settings.plotFloorBlock"));
        if(!worldConfig.exists("settings.plotFillBlock")) worldConfig.set("settings.plotFillBlock", defaultConfig.getString("default-settings.plotFillBlock"));
        if(!worldConfig.exists("settings.price.claim")) worldConfig.set("settings.price.claim", defaultConfig.getInt("default-settings.price.claim"));
        if(!worldConfig.exists("settings.price.clear")) worldConfig.set("settings.price.clear", defaultConfig.getInt("default-settings.price.clear"));
        if(!worldConfig.exists("settings.price.delete")) worldConfig.set("settings.price.delete", defaultConfig.getInt("default-settings.price.delete"));
        if(!worldConfig.exists("settings.price.dispose")) worldConfig.set("settings.price.dispose", defaultConfig.getInt("default-settings.price.dispose"));
        if(!worldConfig.exists("settings.price.erode")) worldConfig.set("settings.price.erode", defaultConfig.getInt("default-settings.price.erode"));
        if(!worldConfig.exists("settings.price.merge")) worldConfig.set("settings.price.merge", defaultConfig.getInt("default-settings.price.merge"));
        worldConfig.save();
    }
}
