package cn.yuang2714.xibaofly.client;

import cn.yuang2714.xibaofly.client.config.FileBasedConfig;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.DisconnectedScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.DisconnectionDetails;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.Music;
import org.jspecify.annotations.Nullable;

public class XibaoDisconnectedScreen extends DisconnectedScreen {
    private final Identifier background;
    private final Music bgm;
    public XibaoDisconnectedScreen(Screen parent, Component title, DisconnectionDetails details, Component buttonText) {
        super(parent, title, details, buttonText);
        background = Identifier.fromNamespaceAndPath(
                "xibao-fly",
                "textures/backgrounds/" + FileBasedConfig.get("background", "xibao") + ".png"
        );
        bgm = switch (FileBasedConfig.get("bgm", "xibao")) {
            case "beibao" -> XibaoFlyClient.beibao;
            case "xibao" -> XibaoFlyClient.xibao;
            default -> throw new NullPointerException("Cannot get BGM!");
        };
    }
    
    @Override
    protected void init() {
        super.init();
    }
    
    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        //Do nothing
    }
    
    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        //背景图片
        graphics.blit(
                RenderPipelines.GUI_TEXTURED,
                background,
                0,
                0,
                0,
                0,
                width,
                height,
                width,
                height
        );
        
        super.extractRenderState(graphics, mouseX, mouseY, a);
    }
    
    @Override
    public @Nullable Music getBackgroundMusic() {
        return bgm;
    }
}
