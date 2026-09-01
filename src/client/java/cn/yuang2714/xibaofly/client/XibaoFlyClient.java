package cn.yuang2714.xibaofly.client;

import cn.yuang2714.xibaofly.client.config.FileBasedConfig;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.Music;
import net.minecraft.sounds.SoundEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class XibaoFlyClient implements ClientModInitializer {
    public static final String MOD_ID = "xibao-fly";
    public static final Logger LOGGER = LoggerFactory.getLogger("XibaoFly");
    public static Music xibao;
    public static Music beibao;
    
	@Override
	public void onInitializeClient() {
		LOGGER.info("initializing xibao-fly");
        try {
            FileBasedConfig.write(FileBasedConfig.read());
        } catch (IOException e) {
            throw new IllegalStateException("Failed to initialize config system.", e);
        }
        
        
        SoundEvent xibaoEvent = Registry.register(
                BuiltInRegistries.SOUND_EVENT,
                Identifier.fromNamespaceAndPath(MOD_ID, "bgm.xibao"),
                SoundEvent.createVariableRangeEvent(
                        Identifier.fromNamespaceAndPath(MOD_ID, "bgm.xibao")
                )
        );
        XibaoFlyClient.xibao = new Music(
                Holder.direct(xibaoEvent),
                0,
                0,
                true
        );
        
        SoundEvent beibaoEvent = Registry.register(
                BuiltInRegistries.SOUND_EVENT,
                Identifier.fromNamespaceAndPath(MOD_ID, "bgm.beibao"),
                SoundEvent.createVariableRangeEvent(
                        Identifier.fromNamespaceAndPath(MOD_ID, "bgm.beibao")
                )
        );
        XibaoFlyClient.beibao = new Music(
                Holder.direct(beibaoEvent),
                0,
                0,
                true
        );
    }
}