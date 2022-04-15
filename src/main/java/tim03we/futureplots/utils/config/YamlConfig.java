package tim03we.futureplots.utils.config;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Map;

public class YamlConfig extends Configuration {
    private static final Yaml yaml = new Yaml();

    public YamlConfig(String file) {
        super(file);
    }

    public YamlConfig(Path path) {
        super(path);
    }

    public YamlConfig(File saveFile) {
        super(saveFile);
    }

    public YamlConfig(File saveFile, InputStream inputStream) {
        super(saveFile, inputStream);
    }

    protected Map<String, Object> deserialize(InputStream inputStream) {
        return (Map)yaml.loadAs(inputStream, Map.class);
    }

    protected String serialize(Map<String, Object> values) {
        return yaml.dump(values);
    }
}
