package cn.yuang2714.xibaofly.client;

import cn.yuang2714.xibaofly.client.config.FileBasedConfig;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.DisconnectedScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.DisconnectionDetails;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public class XibaoDisconnectedScreen extends DisconnectedScreen {
    public XibaoDisconnectedScreen(Screen parent, Component title, DisconnectionDetails details, Component buttonText) {
        super(parent, title, details, buttonText);
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
                Identifier.fromNamespaceAndPath(
                        "xibao-fly",
                        "textures/backgrounds/" + FileBasedConfig.get("background", "xibao") + ".png"
                ),
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
}
