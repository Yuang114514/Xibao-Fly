package cn.yuang2714.xibaofly.client;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ParticleManager {
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
        particles.forEach(p -> p.tick(rng));
        if (!isSetup) {
            particles.add(new Particle(
                    textures[rng.nextInt(0, textures.length)],
                    rng,
                    width,
                    height
            ));
        }
    }
    
    public static void render(GuiGraphicsExtractor graphics) {
        particles.forEach(p -> p.render(graphics));
    }
    
    public static void clear() {
        particles.clear();
        particles = null;
        isSetup = false;
    }
    
    static class Particle {
        int x, y, center, range; //X坐标、Y坐标、正弦函数的中心、正弦函数的摆幅
        final Identifier texture;
        final int width, height;
        
        Particle(Identifier texture, Random rng, int width, int height) {
            this.texture = texture;
            this.width = width;
            this.height = height;
            
            y = rng.nextInt(-100, 100);
            x = rng.nextInt(0, width); //随机X值
            center = x;
            range = rng.nextInt(-2, 3);
        }
        
        public void tick(Random rng) {
            y++; //更新Y值
            
            x = (int) (center + range * Math.sin((double) y / 8)); //更新X值，应用正弦摆动
            
            center += rng.nextInt(-1, 2); //随机扰动正弦函数的参数
            range = rng.nextInt(-1, 2);
            
            if (y >= height) {
                isSetup = true;
                y = rng.nextInt(- 100, 100);
            }
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
    }
}
