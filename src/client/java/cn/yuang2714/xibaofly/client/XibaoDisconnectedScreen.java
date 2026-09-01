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
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class XibaoDisconnectedScreen extends DisconnectedScreen {
    private final Identifier background;
    private final Music bgm;
    public XibaoDisconnectedScreen(Screen parent, Component title, DisconnectionDetails details, Component buttonText) {
        super(parent, title, details, buttonText);
        background = Identifier.fromNamespaceAndPath(
                "xibao-fly",
                "textures/backgrounds/" + FileBasedConfig.get("stage", "xibao") + ".png"
        );
        bgm = switch (FileBasedConfig.get("stage", "xibao")) {
            case "beibao" -> XibaoFlyClient.beibao;
            case "xibao" -> XibaoFlyClient.xibao;
            default -> throw new NullPointerException("Cannot get BGM!");
        };
    }
    
    @Override
    protected void init() {
        super.init();
        initParticles(width, height);
    }
    
    @Override
    public void extractBackground(@NonNull GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
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
        
        //粒子
        ParticleManager.render(graphics);
        
        super.extractRenderState(graphics, mouseX, mouseY, a);
    }
    
    @Override
    public @Nullable Music getBackgroundMusic() {
        return bgm;
    }
    
    @Override
    public void onClose() {
        ParticleManager.clear();
    }
    
    @Override
    public void tick() {
        super.tick();
        ParticleManager.tick();
    }
    
    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        ParticleManager.clear();
        initParticles(width, height);
    }
    
    private static void initParticles(int width, int height) {
        Identifier[] particleTextures = switch (FileBasedConfig.get("stage", "xibao")) {
            case "xibao" -> new Identifier[] {
                    Identifier.fromNamespaceAndPath(XibaoFlyClient.MOD_ID, "textures/particles/red_snow.png"),
                    Identifier.fromNamespaceAndPath(XibaoFlyClient.MOD_ID, "textures/particles/yellow_snow.png"),
            };
            case "beibao" -> new Identifier[] {
                    Identifier.fromNamespaceAndPath(XibaoFlyClient.MOD_ID, "textures/particles/white_snow.png"),
            };
            default -> throw new NullPointerException("Cannot locate Particle!");
        };
        ParticleManager.init(width, height, particleTextures);
    }
}
