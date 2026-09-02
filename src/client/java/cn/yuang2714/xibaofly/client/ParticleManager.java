package cn.yuang2714.xibaofly.client;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ParticleManager {
    private static final int[] THROWING_TARGETS = new int[]
            {-100, -97, -90, -85, -70, -50, -20, -5, 5, 20, 50, 70, 85, 90, 97, 100};
    private static final Random rng = new Random();
    private static List<Particle> particles;
    private static boolean isSetup;
    private static int width, height;
    private static Identifier[] textures;
    
    public static void init(int width, int height, Identifier[] textures) {
        ParticleManager.width = width;
        ParticleManager.height = height;
        ParticleManager.textures = textures;
        
        particles = new ArrayList<>();
    }
    
    public static void tick() {
        if (particles == null) return;
        
        particles.forEach(p -> p.tick(rng));
        
        //控制雪花的数量
        if (!isSetup) {
            particles.add(new Particle(
                    textures[rng.nextInt(0, textures.length)],
                    rng,
                    width,
                    height
            ));
        }
    }
    
    public static void onMouseClick(int eventX, int eventY) {
        if (particles == null) return;
        
        for (int i = 0; i < 16; i++) {
            Particle chosen = new Particle(textures[rng.nextInt(0, textures.length)], rng, width, height);
            chosen.onMouseClick(eventX, eventY, (THROWING_TARGETS[i] / 3) + rng.nextInt(-5, 6));
            particles.add(chosen);
        }
    }
    
    public static void render(GuiGraphicsExtractor graphics) {
        if (particles == null) return;
        
        particles.forEach(p -> p.render(graphics));
    }
    
    public static void clear() {
        if (particles != null) particles.clear();
        particles = null;
        isSetup = false;
    }
    
    public static void clearParticles() {
        if (particles == null) return;
        
        particles.clear();
    }
    
    public static void changeTexture(Identifier[] textures) {
        if (particles == null) return;
        
        ParticleManager.textures = textures;
        particles.forEach(p -> p.changeTexture(textures[rng.nextInt(0, textures.length)]));
    }
    
    static class Particle {
        int x, y, center, range, target, clickY; //X坐标、Y坐标、正弦函数的中心、正弦函数的摆幅，被掷出时的目标x增量
        boolean isCreatedViaClick;
        Identifier texture;
        final int width, height;
        
        Particle(Identifier texture, Random rng, int width, int height) {
            this.texture = texture;
            this.width = width;
            this.height = height;
            
            y = rng.nextInt(-200, 0);
            x = rng.nextInt(0, width); //随机X值
            center = x;
            range = rng.nextInt(-2, 3);
        }
        
        public void tick(Random rng) {
            //更新Y值
            if (target == 0){
                y++;
            } else {
                y += rng.nextInt(0, 3);
            }
            
            //更新X值，应用正弦摆动，应用指数函数模拟的抛物
            x = (int) (center + range * Math.sin((double) y / 8))
                    + ( y > 0 ? (target * (y - clickY) / ((y - clickY) + 8)) : 0);
            
            center += rng.nextInt(-1, 2); //随机扰动正弦函数的参数
            range = rng.nextInt(-1, 2);
            
            if (y >= height) { //越界
                if (!isCreatedViaClick) isSetup = true;
                y = rng.nextInt(-200, 0);
                center = rng.nextInt(0, width);
                target = 0;
                clickY = 0;
                isCreatedViaClick = false;
            }
        }
        
        public void onMouseClick(int eventX, int eventY, int targetX) {
            x = eventX;
            y = eventY;
            clickY = eventY;
            center = eventX;
            target = targetX;
            isCreatedViaClick = true;
        }
        
        public void render(GuiGraphicsExtractor graphics) {
            graphics.blit(
                    RenderPipelines.GUI_TEXTURED,
                    texture,
                    x,
                    y,
                    0,
                    0,
                    4,
                    4,
                    4,
                    4
            );
        }
        
        public void changeTexture(Identifier texture1) {
            texture = texture1;
        }
    }
}
