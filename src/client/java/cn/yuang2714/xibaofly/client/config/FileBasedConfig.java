package cn.yuang2714.xibaofly.client.config;

import cn.yuang2714.xibaofly.client.XibaoFlyClient;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;

public class FileBasedConfig {
    private static final Path configFile = FabricLoader.getInstance().getConfigDir().resolve("xibao-fly.json");
    
    private static void createFileIfNeed() throws IOException {
        if (!Files.exists(configFile, LinkOption.NOFOLLOW_LINKS)) {
            Files.createFile(configFile);
            Files.writeString(configFile, "{}");
        }
    }
    
    public static JsonObject read() throws IOException {
        createFileIfNeed();
        return JsonParser.parseString(Files.readString(configFile)).getAsJsonObject();
    }
    
    public static void write(JsonObject value) throws IOException {
        createFileIfNeed();
        Files.writeString(configFile, value.toString());
    }
    
    public static void set(String key, String value) {
        try {
            JsonObject current = read();
            if (current.has(key)) current.remove(key);
            current.addProperty(key, value);
            write(current);
        } catch (Exception e) {
            XibaoFlyClient.LOGGER.warn("write config failed.");
        }
    }
    
    public static String get(String key, String def) {
        try {
            return read().get(key).getAsString();
        } catch (Exception e) {
            XibaoFlyClient.LOGGER.warn("read config failed.");
            return def;
        }
    }
}
