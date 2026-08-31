package cn.yuang2714.xibaofly.client.mixin;

import cn.yuang2714.xibaofly.client.XibaoDisconnectedScreen;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.screens.DisconnectedScreen;
import net.minecraft.client.gui.screens.Screen;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public abstract class GuiMixin {
    @Shadow public abstract void setScreen(@Nullable Screen screen);
    
    @Inject(method = "setScreen", at = @At("HEAD"), cancellable = true)
    public void setScreen(Screen screen, CallbackInfo ci) {
        if (screen instanceof DisconnectedScreen dScreen && !(screen instanceof XibaoDisconnectedScreen)) {
            XibaoDisconnectedScreen xibaoScreen = new XibaoDisconnectedScreen(
                    dScreen.parent,
                    dScreen.getTitle(),
                    dScreen.details,
                    dScreen.buttonText
            );
            setScreen(xibaoScreen);
            ci.cancel();
        }
    }
}
