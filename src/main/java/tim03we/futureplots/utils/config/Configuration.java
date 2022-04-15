package tim03we.futureplots.utils.config;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class Configuration {
    protected File file;
    protected Map<String, Object> values;

    public Configuration(String saveFile) {
        this(new File(saveFile));
    }

    public Configuration(Path path) {
        this(path.toFile());
    }

    public Configuration(File saveFile) {
        this(saveFile, (InputStream)null);
    }

    public Configuration(File saveFile, InputStream inputStream) {
        this.values = new LinkedHashMap();
        this.file = saveFile;

        try {
            if (!this.file.exists()) {
                this.save();
            }

            if (inputStream == null) {
                inputStream = Files.newInputStream(this.file.toPath());
            }

            this.load(inputStream);
        } catch (IOException var4) {
            System.out.println("Unable to initialize Config " + this.file.toString());
        }

    }

    protected abstract Map<String, Object> deserialize(InputStream var1);

    protected abstract String serialize(Map<String, Object> var1);

    public void load(InputStream inputStream) {
        try {
            this.values = this.deserialize(inputStream);
        } catch (Exception var3) {
            System.out.println("Unable to load Config " + this.file.toString());
        }

    }

    public void save() {
        this.save(this.serialize(this.values));
    }

    protected void save(String content) {
        try {
            File parentFile = this.file.getParentFile();
            if (parentFile != null) {
                parentFile.mkdirs();
            }

            FileWriter myWriter = new FileWriter(this.file);
            myWriter.write(content);
            myWriter.close();
        } catch (IOException var4) {
            System.out.println("Unable to save Config " + this.file.toString());
        }

    }

    public void loadFrom(Map<String, Object> values) {
        this.values = values;
    }

    public Set<String> getKeys() {
        return this.values.keySet();
    }

    public void remove(String key) {
        LastMap lastMap = this.getLastMap(key);
        if (lastMap != null) {
            /*for (String allKey : getAll().keySet()) {
                System.out.println("All: " + allKey);
                if(allKey.startsWith(lastMap.key)) {
                    lastMap.map.remove(allKey);
                }
            }*/
            for (String keys : getKeys()) {
                System.out.println("Keys: " + keys);
            }
            System.out.println(key);
            set(key, null);
            lastMap.map.remove(lastMap.key);
        }
    }

    public void set(String key, Object value) {
        LastMap lastMap = this.getLastMap(key);
        if (lastMap != null) {
            lastMap.map.put(lastMap.key, value);
        }
    }

    private LastMap getLastMap(String key) {
        if (key != null && !key.isEmpty()) {
            Map<String, Object> values = this.values;
            String[] keys = key.split("\\.");
            String currentKey = null;

            for(int i = 0; i < keys.length; ++i) {
                currentKey = keys[i];
                if (i + 1 < keys.length && values.get(currentKey) == null) {
                    values.put(currentKey, new LinkedHashMap());
                }

                if (!(values.get(currentKey) instanceof Map)) {
                    break;
                }

                values = (Map)values.get(currentKey);
            }

            return new LastMap(currentKey, values);
        } else {
            return null;
        }
    }

    public Object get(String key) {
        return this.get(key, (Object)null);
    }

    public Object get(String key, Object defaultValue) {
        if (key != null && !key.isEmpty()) {
            Map<String, Object> values = this.values;
            String[] keys = key.split("\\.");
            if (this.values.containsKey(key)) {
                return this.values.get(key);
            } else {
                for(int i = 0; i < keys.length; ++i) {
                    Object currentValue = values.get(keys[i]);
                    if (currentValue == null) {
                        return defaultValue;
                    }

                    if (i + 1 == keys.length) {
                        return currentValue;
                    }

                    values = (Map)currentValue;
                }

                return defaultValue;
            }
        } else {
            return defaultValue;
        }
    }

    public boolean exists(String key) {
        return this.get(key) != null;
    }

    public Map<String, Object> getAll() {
        return this.values;
    }

    public void setString(String key, String value) {
        this.set(key, value);
    }

    public String getString(String key) {
        return this.getString(key, (String)null);
    }

    public String getString(String key, String defaultValue) {
        return String.valueOf(this.get(key, defaultValue));
    }

    public void setInt(String key, Integer value) {
        this.set(key, value);
    }

    public Integer getInt(String key) {
        return this.getInt(key, (Integer)null);
    }

    public Integer getInt(String key, Integer defaultValue) {
        return Integer.valueOf(String.valueOf(this.get(key, defaultValue)));
    }

    public void setLong(String key, Long value) {
        this.set(key, value);
    }

    public Long getLong(String key) {
        return this.getLong(key, (Long)null);
    }

    public Long getLong(String key, Long defaultValue) {
        return Long.valueOf(String.valueOf(this.get(key, defaultValue)));
    }

    public void setDouble(String key, Double value) {
        this.set(key, value);
    }

    public Double getDouble(String key) {
        return this.getDouble(key, (Double)null);
    }

    public Double getDouble(String key, Double defaultValue) {
        return Double.valueOf(String.valueOf(this.get(key, defaultValue)));
    }

    public void setBoolean(String key, Boolean value) {
        this.set(key, value);
    }

    public Boolean getBoolean(String key) {
        return this.getBoolean(key, (Boolean)null);
    }

    public Boolean getBoolean(String key, Boolean defaultValue) {
        return (Boolean)this.get(key, defaultValue);
    }

    public <T> void setList(String key, List<T> value) {
        this.set(key, value);
    }

    public <T> List<T> getList(String key) {
        return this.getList(key, (List)null);
    }

    public <T> List<T> getList(String key, List<T> defaultValue) {
        return (List)this.get(key, defaultValue);
    }

    public void setStringList(String key, List<String> value) {
        this.set(key, value);
    }

    public List<String> getStringList(String key) {
        return this.getStringList(key, (List)null);
    }

    public List<String> getStringList(String key, List<String> defaultValue) {
        return (List)this.get(key, defaultValue);
    }

    private static class LastMap {
        public final String key;
        public final Map<String, Object> map;

        public LastMap(String key, Map<String, Object> map) {
            this.key = key;
            this.map = map;
        }
    }
}
