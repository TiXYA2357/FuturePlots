package tim03we.futureplots.generator;

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
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.generator.ChunkGenerator;
import org.jetbrains.annotations.NotNull;
import tim03we.futureplots.utils.PlotSettings;
import tim03we.futureplots.utils.Settings;

import java.util.HashMap;
import java.util.Random;

public class PlotGenerator extends ChunkGenerator {

    protected World level;
    protected Material roadBlock;
    protected Material bottomBlock;
    protected Material plotFillBlock;
    protected Material plotFloorBlock;
    protected Material wallBlock;
    protected int roadWidth;
    protected int groundHeight;
    protected int plotSize;
    static int PLOT = 0;
    static int ROAD = 1;
    static int WALL = 2;
    private boolean check = false;

    public PlotGenerator() {
    }

    private void checkData(World world) {
        PlotSettings plotSettings = new PlotSettings(world.getName());
        roadBlock = plotSettings.getRoadBlock();
        wallBlock = plotSettings.getWallBlockUnClaimed();
        plotFloorBlock = plotSettings.getPlotFloorBlock();
        plotFillBlock = plotSettings.getPlotFillBlock();
        bottomBlock = plotSettings.getBottomBlock();
        roadWidth = plotSettings.getRoadWidth();
        plotSize = plotSettings.getPlotSize();
        groundHeight = plotSettings.getGroundHeight();
    }

    @Override
    public @NotNull ChunkData generateChunkData(@NotNull World world, @NotNull Random random, int chunkX, int chunkZ, @NotNull BiomeGrid biome) {
        if(!check) {
            if(!Settings.levels.contains(world.getName())) {
                new PlotSettings(world.getName()).initWorld();
                checkData(world);
                Settings.levels.add(world.getName());
            } else checkData(world);
            check = true;
        }
        HashMap<Integer, Integer> shape = getShape(chunkX << 4, chunkZ << 4);
        ChunkData chunk = createChunkData(world);
        int groundH = groundHeight;
        for (int Z = 0; Z < 16; ++Z) {
            for(int X = 0; X < 16; ++X) {
                chunk.setBlock(X, 0, Z, bottomBlock);
                for(int y = 1; y < groundH; ++y) {
                    chunk.setBlock(X, y, Z, plotFillBlock);
                }
                int type = shape.get((Z << 4) | X);
                if(type == PLOT) {
                    chunk.setBlock(X, groundH, Z, plotFloorBlock);
                } else if(type == ROAD) {
                    chunk.setBlock(X, groundH, Z, roadBlock);
                } else {
                    chunk.setBlock(X, groundH, Z, roadBlock);
                    chunk.setBlock(X, groundH + 1, Z, wallBlock);
                }
            }
        }
        return chunk;
    }

    public HashMap<Integer, Integer> getShape(int x, int z) {
        int totalSize = plotSize + roadWidth;
        int X;
        int Z;
        if(x >= 0) {
            X = x % totalSize;
        } else {
            X = totalSize - Math.abs(x % totalSize);
        }
        if(z >= 0) {
            Z = z % totalSize;
        } else {
            Z = totalSize - Math.abs(z % totalSize);
        }
        HashMap<Integer, Integer> shape = new HashMap<>();
        int typeZ;
        int typeX;
        int type;
        int startX = X;
        for (z = 0; z < 16; z++, Z++) {
            if(Z == totalSize) {
                Z = 0;
            }
            if(Z < plotSize) {
                typeZ = PLOT;
            } else if(Z == plotSize || Z == (totalSize - 1)) {
                typeZ = WALL;
            } else {
                typeZ = ROAD;
            }
            for (x = 0, X = startX; x < 16; x++, X++) {
                if(X == totalSize) {
                    X = 0;
                }
                if(X < plotSize) {
                    typeX = PLOT;
                } else if(X == plotSize || X == (totalSize - 1)) {
                    typeX = WALL;
                } else {
                    typeX = ROAD;
                }
                if(typeX == typeZ) {
                    type = typeX;
                } else if(typeX == PLOT) {
                    type = typeZ;
                } else if(typeZ == PLOT) {
                    type = typeX;
                } else {
                    type = ROAD;
                }
                shape.put((z << 4) | x, type);
            }
        }
        return shape;
    }
}
