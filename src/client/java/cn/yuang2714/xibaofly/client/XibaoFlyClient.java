package cn.yuang2714.xibaofly.client;

import cn.yuang2714.xibaofly.client.config.FileBasedConfig;
import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class XibaoFlyClient implements ClientModInitializer {
    public static final String MOD_ID = "xibao-fly";
    public static final Logger LOGGER = LoggerFactory.getLogger("XibaoFly");
    
	@Override
	public void onInitializeClient() {
		LOGGER.info("initializing xibao-fly");
        try {
            FileBasedConfig.write(FileBasedConfig.read());
        } catch (IOException e) {
            throw new IllegalStateException("Failed to initialize config system.", e);
        }
    }
}