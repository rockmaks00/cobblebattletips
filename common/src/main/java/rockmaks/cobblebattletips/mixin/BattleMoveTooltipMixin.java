package rockmaks.cobblebattletips.mixin;

import com.cobblemon.mod.common.battles.Targetable;
import rockmaks.cobblebattletips.BattleMoveTooltip;
import com.cobblemon.mod.common.api.moves.MoveTemplate;
import com.cobblemon.mod.common.client.gui.battle.subscreen.BattleMoveSelection.MoveTile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(MoveTile.class)
public abstract class BattleMoveTooltipMixin {
    @Shadow
    public abstract boolean isHovered(double mouseX, double mouseY);

    @Shadow
    public abstract List<Targetable> getTargetList();

    @Shadow
    private MoveTemplate moveTemplate;

    @Inject(method = "render", at = @At("TAIL"))
    public void renderTooltip(GuiGraphics context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (this.isHovered(mouseX, mouseY)) {
            List<Component> tooltipInfo = BattleMoveTooltip.buildTooltip(moveTemplate, this.getTargetList());
            context.renderComponentTooltip(Minecraft.getInstance().font, tooltipInfo, mouseX, mouseY);
        }
    }
}