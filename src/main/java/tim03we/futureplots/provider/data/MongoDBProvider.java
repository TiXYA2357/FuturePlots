package tim03we.futureplots.provider.data;

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

import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import tim03we.futureplots.provider.DataProvider;
import tim03we.futureplots.provider.mongodb.MongoAPI;
import tim03we.futureplots.utils.Plot;

import java.util.ArrayList;
import java.util.List;

public class MongoDBProvider implements DataProvider {

    @Override
    public void connect() {
        MongoAPI.load();
    }

    private void checkData() {
        /*for (String key : yaml.getAll().keySet()) {
            if(!yaml.exists(key + ".owner")) yaml.set(key + ".owner", "");
            if(!yaml.exists(key + ".helpers")) yaml.set(key + ".helpers", new ArrayList<String>());
            if(!yaml.exists(key + ".members")) yaml.set(key + ".members", new ArrayList<String>());
            if(!yaml.exists(key + ".denied")) yaml.set(key + ".denied", new ArrayList<String>());
            if(!yaml.exists(key + ".flags")) yaml.set(key + ".flags", new ArrayList<String>());
            if(!yaml.exists(key + ".home")) yaml.set(key + ".home", "");
            if(!yaml.exists(key + ".merge")) yaml.set(key + ".merge", "");
            if(!yaml.exists(key + ".merges")) yaml.set(key + ".merges", new ArrayList<String>());
            if(!yaml.exists(key + ".merge_check")) yaml.set(key + ".merge_check", new ArrayList<String>());
        }*/
    }

    @Override
    public void save() {
    }

    @Override
    public void claimPlot(String username, Plot plot) {
        Document insert = new Document("owner", username)
                        .append("helpers", new ArrayList<>())
                        .append("members", new ArrayList<>())
                        .append("denied", new ArrayList<>())
                        .append("flags", new ArrayList<>())
                        .append("home", null)
                        .append("merge", null)
                        .append("merges", new ArrayList<>())
                        .append("merge_check", new ArrayList<>());
        insert.put("_id", plot.getLevelName() + ";" + plot.getFullID());
        MongoAPI.insert(insert);
    }

    @Override
    public void deletePlot(Plot plot) {
        MongoAPI.delete(new Document("_id", plot.getLevelName() + ";" + plot.getFullID()));
    }

    @Override
    public List<String> getHelpers(Plot plot) {
        if(hasOwner(plot)) {
            return MongoAPI.find(new Document("_id", plot.getLevelName() + ";" + plot.getFullID())).getList("helpers", String.class);
        }
        return new ArrayList<>();
    }

    @Override
    public List<String> getMembers(Plot plot) {
        if(hasOwner(plot)) {
            return MongoAPI.find(new Document("_id", plot.getLevelName() + ";" + plot.getFullID())).getList("members", String.class);
        }
        return new ArrayList<>();
    }

    @Override
    public List<String> getDenied(Plot plot) {
        if(hasOwner(plot)) {
            return MongoAPI.find(new Document("_id", plot.getLevelName() + ";" + plot.getFullID())).getList("denied", String.class);
        }
        return new ArrayList<>();
    }

    @Override
    public boolean isHelper(String name, Plot plot) {
        return getHelpers(plot).contains(name.toLowerCase());
    }

    @Override
    public boolean isDenied(String name, Plot plot) {
        return getDenied(plot).contains(name.toLowerCase());
    }

    @Override
    public boolean isMember(String name, Plot plot) {
        return getMembers(plot).contains(name.toLowerCase());
    }

    @Override
    public boolean hasOwner(Plot plot) {
        return !getOwner(plot).equals("none");
    }

    @Override
    public void setOwner(String name, Plot plot) {
        MongoAPI.change(new Document("_id", plot.getLevelName() + ";" + plot.getFullID()), "owner", name);
    }

    @Override
    public String getOwner(Plot plot) {
        Document find = MongoAPI.find(new Document("_id", plot.getLevelName() + ";" + plot.getFullID()));
        if(find == null) return "none";
        return find.getString("owner");
    }

    @Override
    public void addHelper(String name, Plot plot) {
        List<String> helpers = getHelpers(plot);
        helpers.add(name.toLowerCase());
        MongoAPI.change(new Document("_id", plot.getLevelName() + ";" + plot.getFullID()), "helpers", helpers);
    }

    @Override
    public void addMember(String name, Plot plot) {
        List<String> members = getMembers(plot);
        members.add(name.toLowerCase());
        MongoAPI.change(new Document("_id", plot.getLevelName() + ";" + plot.getFullID()), "members", members);
    }

    @Override
    public void addDenied(String name, Plot plot) {
        List<String> denied = getDenied(plot);
        denied.add(name.toLowerCase());
        MongoAPI.change(new Document("_id", plot.getLevelName() + ";" + plot.getFullID()), "denied", denied);
    }

    @Override
    public void removeMember(String name, Plot plot) {
        List<String> members = getMembers(plot);
        members.remove(name.toLowerCase());
        MongoAPI.change(new Document("_id", plot.getLevelName() + ";" + plot.getFullID()), "members", members);
    }

    @Override
    public void removeHelper(String name, Plot plot) {
        List<String> helpers = getHelpers(plot);
        helpers.remove(name.toLowerCase());
        MongoAPI.change(new Document("_id", plot.getLevelName() + ";" + plot.getFullID()), "helpers", helpers);
    }

    @Override
    public void removeDenied(String name, Plot plot) {
        List<String> denied = getDenied(plot);
        denied.remove(name.toLowerCase());
        MongoAPI.change(new Document("_id", plot.getLevelName() + ";" + plot.getFullID()), "denied", denied);
    }

    @Override
    public void setHome(Plot plot, Location location) {
        MongoAPI.change(new Document("_id", plot.getLevelName() + ";" + plot.getFullID()), "home", location.getX() + ":" + location.getY() + ":" + location.getZ() + ":" + location.getYaw() + ":" + location.getPitch());
    }

    @Override
    public void deleteHome(Plot plot) {
        MongoAPI.change(new Document("_id", plot.getLevelName() + ";" + plot.getFullID()), "home", "");
    }

    @Override
    public Location getHome(Plot plot) {
        Document find = MongoAPI.find(new Document("_id", plot.getLevelName() + ";" + plot.getFullID()));
        if(find == null) return null;
        if(find.getString("home").isEmpty()) return null;
        String[] ex = find.getString("home").split(":");
        return new Location(Bukkit.getWorld(plot.getLevelName()), Double.parseDouble(ex[0]), Double.parseDouble(ex[1]), Double.parseDouble(ex[2]), Float.parseFloat(ex[3]), Float.parseFloat(ex[4]));
    }

    @Override
    public Plot getPlot(String name, Object number, Object level) {
        int i = 1;
        if(number != null && (int) number > 0) {
            i = (int) number;
        }
        ArrayList<String> plots = new ArrayList<>();
        if(level != null) {
            for (Document find : MongoAPI.find()) {
                String[] ex = find.getString("_id").split(";");
                if(ex[0].equals(level) && find.getString("owner").equals(name)) plots.add(find.getString("_id"));
            }
        } else {
            for (Document find : MongoAPI.find()) {
                if(find.getString("owner").equals(name)) plots.add(find.getString("_id"));
            }
        }
        if((i - 1) >= plots.size()) return null;
        if(plots.size() > 0) {
            String[] ex = plots.get(i - 1).split(";");
            return new Plot(Integer.parseInt(ex[1]), Integer.parseInt(ex[2]), ex[0]);
        }
        return null;
    }

    @Override
    public List<String> getPlots(String name, Object level) {
        List<String> plots = new ArrayList<>();
        if(level != null) {
            for (Document find : MongoAPI.find()) {
                String[] ex = find.getString("_id").split(";");
                if(ex[0].equals(level) && find.getString("owner").equals(name)) plots.add(find.getString("_id"));
            }
        } else {
            for (Document find : MongoAPI.find()) {
                if(find.getString("owner").equals(name)) plots.add(find.getString("_id"));
            }
        }
        return plots;
    }

    @Override
    public Plot getNextFreePlot(String level) {
        List<Plot> plots = new ArrayList<>();
        for (Document find : MongoAPI.find()) {
            String[] ex = find.getString("_id").split(";");
            if(ex[0].equals(level)) {
                plots.add(new Plot(Integer.parseInt(ex[1]), Integer.parseInt(ex[2]), ex[0]));
            }
        }
        if (plots.size() == 0) return new Plot(0, 0, level);
        int lastX = 0;
        int lastZ = 0;

        for (Plot plot : plots) {
            int x = plot.getX() - lastX;
            int y = plot.getZ() - lastZ;
            int diff = Math.abs(x * y);
            if (diff < 4) {
                lastX = plot.getX();
                lastZ = plot.getZ();

                Plot find = new Plot(plot.getX() + 1, plot.getZ(), level);
                if (!hasOwner(find)) return find;
                find = new Plot(plot.getX(), plot.getZ() + 1, level);
                if (!hasOwner(find)) return find;
                find = new Plot(plot.getX() - 1, plot.getZ(), level);
                if (!hasOwner(find)) return find;
                find = new Plot(plot.getX(), plot.getZ() - 1, level);
                if (!hasOwner(find)) return find;
            }
        }
        return getNextFreePlot(level);
    }

    @Override
    public void setMergeCheck(Plot plot, Plot mergePlot) {
        List<String> mergeList = new ArrayList<>();
        for (Plot plots : getMergeCheck(plot)) {
            mergeList.add(plots.getX() + ";" + plots.getZ());
        }
        mergeList.add(mergePlot.getX() + ";" + mergePlot.getZ());
        MongoAPI.change(new Document("_id", plot.getLevelName() + ";" + plot.getFullID()), "merge_check", mergeList);
    }

    @Override
    public List<Plot> getMergeCheck(Plot plot) {
        List<Plot> plots = new ArrayList<>();
        if(!hasOwner(plot)) return plots;
        for (String plotId : MongoAPI.find(new Document("_id", plot.getLevelName() + ";" + plot.getFullID())).getList("merge_check", String.class)) {
            String[] ex = plotId.split(";");
            plots.add(new Plot(Integer.parseInt(ex[0]), Integer.parseInt(ex[1]), plot.getLevelName()));
        }
        return plots;
    }

    @Override
    public Plot getOriginPlot(Plot plot) {
        if(!hasOwner(plot)) return null;
        String originString = MongoAPI.find(new Document("_id", plot.getLevelName() + ";" + plot.getFullID())).getString("merge");
        if(originString.isEmpty()) return null;
        String[] ex = originString.split(";");
        return new Plot(Integer.parseInt(ex[0]), Integer.parseInt(ex[1]), plot.getLevelName());
    }

    @Override
    public void setOriginPlot(Plot mergePlot, Plot originPlot) {
        MongoAPI.change(new Document("_id", mergePlot.getLevelName() + ";" + mergePlot.getFullID()), "merge", originPlot.getFullID());
    }


    @Override
    public void addMerge(Plot originPlot, Plot mergePlot) {
        List<String> mergeList = new ArrayList<>();
        for (Plot plots : getMerges(originPlot)) {
            mergeList.add(plots.getX() + ";" + plots.getZ());
        }
        mergeList.add(mergePlot.getX() + ";" + mergePlot.getZ());
        MongoAPI.change(new Document("_id", originPlot.getLevelName() + ";" + originPlot.getFullID()), "merges", mergeList);
    }

    @Override
    public List<Plot> getMerges(Plot plot) {
        List<Plot> plots = new ArrayList<>();
        if(!hasOwner(plot)) return plots;
        for (String plotId : MongoAPI.find(new Document("_id", plot.getLevelName() + ";" + plot.getFullID())).getList("merges", String.class)) {
            String[] ex = plotId.split(";");
            plots.add(new Plot(Integer.parseInt(ex[0]), Integer.parseInt(ex[1]), plot.getLevelName()));
        }
        return plots;
    }

    @Override
    public void resetMerges(Plot plot) {
        MongoAPI.change(new Document("_id", plot.getLevelName() + ";" + plot.getFullID()), "home", "");
        MongoAPI.change(new Document("_id", plot.getLevelName() + ";" + plot.getFullID()), "merge", "");
        MongoAPI.change(new Document("_id", plot.getLevelName() + ";" + plot.getFullID()), "merges", new ArrayList<>());
        MongoAPI.change(new Document("_id", plot.getLevelName() + ";" + plot.getFullID()), "merge_check", new ArrayList<>());
    }

    @Override
    public void deleteMergeList(Plot plot) {
        MongoAPI.change(new Document("_id", plot.getLevelName() + ";" + plot.getFullID()), "merges", new ArrayList<>());
    }
}
