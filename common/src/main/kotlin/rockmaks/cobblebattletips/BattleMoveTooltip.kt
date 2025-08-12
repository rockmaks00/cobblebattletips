package rockmaks.cobblebattletips

import com.cobblemon.mod.common.api.moves.MoveTemplate
import com.cobblemon.mod.common.api.moves.categories.DamageCategories
import com.cobblemon.mod.common.battles.Targetable
import com.cobblemon.mod.common.util.lang
import net.minecraft.ChatFormatting
import net.minecraft.client.Minecraft
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.network.chat.Style
import net.minecraft.network.chat.contents.PlainTextContents

object BattleMoveTooltip {
    @JvmStatic
    fun buildTooltip(move: MoveTemplate, targets: List<Targetable>?): List<Component> {
        val tooltipInfo = mutableListOf<Component>()
        addDescription(tooltipInfo, move)
        addEfficiencyDescription(tooltipInfo, move, targets)

        return tooltipInfo
    }

    private fun addDescription(tooltipInfo: MutableList<Component>, move: MoveTemplate) {
        val description = Minecraft.getInstance().font.splitter.splitLines(
            move.description,
            250,
            Style.EMPTY.withColor(move.elementalType.hue)
        )

        for (formattedText in description) {
            tooltipInfo.add(
                MutableComponent.create(
                    PlainTextContents.LiteralContents(formattedText.string)
                ).withStyle(ChatFormatting.GRAY)
            )
        }

        if (
            move.damageCategory != DamageCategories.STATUS ||
            move.accuracy != -1.0 ||
            move.effectChances.isNotEmpty()
        ) {
            tooltipInfo.add(Component.empty())
            if (move.power > 0) {
                tooltipInfo.add(
                    lang("ui.power").append(Component.literal(": ${move.power.toInt()}"))
                )}
            if (move.accuracy > 0) {
                tooltipInfo.add(
                    lang("ui.accuracy").append(Component.literal(": ${move.accuracy.toInt()}%"))
                )}
            if (move.effectChances.isNotEmpty()) {
                val amogus = move.effectChances
                val chancesText = amogus.joinToString(", ") { "${it.toInt()}%" }
                tooltipInfo.add(
                    lang("ui.effect").append(Component.literal(": $chancesText"))
                )}
        }
    }

    private fun addEfficiencyDescription(
        tooltipInfo: MutableList<Component>,
        move: MoveTemplate,
        targets: List<Targetable>?
    ) {
        if (targets.isNullOrEmpty() || move.damageCategory === DamageCategories.STATUS) {
            return
        }

        for (target in targets) {
            val effectiveness = MoveEffectiveness.getModifier(move, target)

            if (effectiveness == 0.0) {
                tooltipInfo.add(
                    Component.translatable("cobblebattletips.immune")
                        .withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY)
                )
            } else if (effectiveness > 1) {
                tooltipInfo.add(
                    Component.translatable("cobblebattletips.effective", effectiveness)
                        .withStyle(ChatFormatting.BOLD, ChatFormatting.GOLD)
                )
            } else if (effectiveness < 1) {
                tooltipInfo.add(Component.translatable("cobblebattletips.ineffective", effectiveness)
                    .withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY)
                )
            }
        }
    }
}