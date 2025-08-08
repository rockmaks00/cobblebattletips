package rockmaks.cobblebattletips

import com.cobblemon.mod.common.api.moves.MoveTemplate
import com.cobblemon.mod.common.api.moves.categories.DamageCategories
import com.cobblemon.mod.common.util.lang
import net.minecraft.ChatFormatting
import net.minecraft.client.Minecraft
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.network.chat.Style
import net.minecraft.network.chat.contents.PlainTextContents

object BattleMoveTooltip {
    @JvmStatic
    fun buildTooltip(moveTemplate: MoveTemplate): List<Component> {
        val tooltipInfo = mutableListOf<Component>()
        addDescription(tooltipInfo, moveTemplate)
        return tooltipInfo
    }

    private fun addDescription(tooltipInfo: MutableList<Component>, moveTemplate: MoveTemplate) {
        val description = Minecraft.getInstance().font.getSplitter().splitLines(
            moveTemplate.description,
            250,
            Style.EMPTY.withColor(moveTemplate.elementalType.hue)
        )

        for (formattedText in description) {
            tooltipInfo.add(
                MutableComponent.create(
                    PlainTextContents.LiteralContents(formattedText.string)
                ).withStyle(ChatFormatting.GRAY)
            )
        }

        if (
            moveTemplate.damageCategory != DamageCategories.STATUS ||
            moveTemplate.accuracy != -1.0 ||
            moveTemplate.effectChances.isNotEmpty()
        ) {
            tooltipInfo.add(Component.empty())
            if (moveTemplate.power > 0)
                tooltipInfo.add(lang("ui.power").append(Component.literal(": ${moveTemplate.power.toInt()}")))
            if (moveTemplate.accuracy > 0)
                tooltipInfo.add(lang("ui.accuracy").append(Component.literal(": ${moveTemplate.accuracy.toInt()}%")))
            if (moveTemplate.effectChances.isNotEmpty())
                tooltipInfo.add(lang("ui.effect").append(Component.literal(": ${moveTemplate.effectChances[0].toInt()}%")))
        }
    }
}