package cn.yuang2714.xibaofly.client;

import cn.yuang2714.xibaofly.client.config.FileBasedConfig;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.DisconnectedScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.DisconnectionDetails;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.Music;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class XibaoDisconnectedScreen extends DisconnectedScreen {
    private Identifier background;
    private Music bgm;
    private boolean isMusicPlaying = true;
    private boolean showParticles;
    private Button clearButton, renderParticleButton;
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
        showParticles = FileBasedConfig.get("particles", "false").equals("true");
    }
    
    @Override
    protected void init() {
        super.init();
        addRenderableWidget(Button
                .builder(
                        Component.translatable("gui.xibao-fly.stop_music"),
                        this::stopOrPlayMusic
                ).bounds(
                        width / 2 - 200,
                        height - 20,
                        100,
                        20
                ).build());
        clearButton = Button
                .builder(
                        Component.translatable("gui.xibao-fly.clear", snowsOrFlowers()),
                        this::clearParticles
                ).bounds(
                        width / 2 - 100,
                        height - 20,
                        100,
                        20
                ).build();
        addRenderableWidget(clearButton);
        renderParticleButton = Button
                .builder(
                        Component.translatable("gui.xibao-fly.render", snowsOrFlowers()),
                        this::renderOrHideParticles
                ).bounds(
                        width / 2,
                        height - 20,
                        100,
                        20
                ).build();
        addRenderableWidget(renderParticleButton);
        addRenderableWidget(Button
                .builder(
                        Component.translatable("gui.xibao-fly.change_stage"),
                        this::changeStage
                ).bounds(
                        width / 2 + 100,
                        height - 20,
                        100,
                        20
                ).build());
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
        return isMusicPlaying ? bgm : null;
    }
    
    @Override
    public void tick() {
        super.tick();
        ParticleManager.tick();
    }
    
    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        if (showParticles) {
            ParticleManager.clear();
            initParticles(width, height);
        }
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
    
    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
        ParticleManager.onMouseClick((int) event.x(), (int) event.y());
        return super.mouseClicked(event, doubleClick);
    }
    
    private Component snowsOrFlowers() {
        return switch (FileBasedConfig.get("stage", "xibao")) {
            case "xibao" -> Component.translatable("gui.xibao-fly.flowers");
            case "beibao" -> Component.translatable("gui.xibao-fly.snows");
            default -> throw new NullPointerException("Failed to get translation key flowers/snows");
        };
    }
    
    void stopOrPlayMusic(Button btn) {
        if (isMusicPlaying) {
            btn.setMessage(Component.translatable("gui.xibao-fly.play_music"));
            isMusicPlaying = false;
        } else {
            btn.setMessage(Component.translatable("gui.xibao-fly.stop_music"));
            isMusicPlaying = true;
        }
    }
    
    void clearParticles(Button btn) {
        ParticleManager.clearParticles();
    }
    
    void renderOrHideParticles(Button btn) {
        if (showParticles) {
            btn.setMessage(Component.translatable("gui.xibao-fly.render", snowsOrFlowers()));
            showParticles = false;
            ParticleManager.clear();
        } else {
            btn.setMessage(Component.translatable("gui.xibao-fly.hide", snowsOrFlowers()));
            showParticles = true;
            initParticles(width, height);
        }
    }
    
    void changeStage(Button btn) {
        if (FileBasedConfig.get("stage", "xibao").equals("xibao")) {
            FileBasedConfig.set("stage", "beibao");
        } else if (FileBasedConfig.get("stage", "xibao").equals("beibao")) {
            FileBasedConfig.set("stage", "xibao");
        }
        
        background = Identifier.fromNamespaceAndPath(
                "xibao-fly",
                "textures/backgrounds/" + FileBasedConfig.get("stage", "xibao") + ".png"
        );
        bgm = switch (FileBasedConfig.get("stage", "xibao")) {
            case "beibao" -> XibaoFlyClient.beibao;
            case "xibao" -> XibaoFlyClient.xibao;
            default -> throw new NullPointerException("Cannot get BGM!");
        };
        
        clearButton.setMessage(Component.translatable("gui.xibao-fly.clear", snowsOrFlowers()));
        renderParticleButton.setMessage(Component.translatable(
                showParticles ? "gui.xibao-fly.hide" : "gui.xibao-fly.render",
                snowsOrFlowers()
        ));
        
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
        ParticleManager.changeTexture(particleTextures);
    }
    
    @Override
    public void removed() {
        ParticleManager.clear();
        FileBasedConfig.set("particles", String.valueOf(showParticles));
        super.removed();
    }
}
