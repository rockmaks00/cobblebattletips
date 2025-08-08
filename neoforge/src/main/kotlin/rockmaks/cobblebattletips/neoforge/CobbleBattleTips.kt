package rockmaks.cobblebattletips.neoforge

import net.minecraft.network.chat.Component
import net.minecraft.server.packs.PackLocationInfo
import net.minecraft.server.packs.PackResources
import net.minecraft.server.packs.PackSelectionConfig
import net.minecraft.server.packs.PackType
import net.minecraft.server.packs.PathPackResources
import net.minecraft.server.packs.repository.Pack
import net.minecraft.server.packs.repository.PackSource
import rockmaks.cobblebattletips.CobbleBattleTips.MOD_ID
import rockmaks.cobblebattletips.CobbleBattleTips.init
import net.neoforged.fml.common.Mod
import net.neoforged.fml.ModList
import net.neoforged.neoforge.event.AddPackFindersEvent
import thedarkcolour.kotlinforforge.neoforge.forge.MOD_BUS
import java.util.Optional


@Mod(MOD_ID)
object CobbleBattleTips {
    init {
        init()

        with(MOD_BUS)
        {
            addListener(this@CobbleBattleTips::onAddPackFindersEvent)
        }
    }

    fun onAddPackFindersEvent(event: AddPackFindersEvent) {
        if (event.packType != PackType.CLIENT_RESOURCES) {
            return
        }

        val modFile = ModList.get().getModFileById(MOD_ID).file
        val path = modFile.findResource("pasturefix")

        val factory = object: Pack.ResourcesSupplier {
            override fun openPrimary(info: PackLocationInfo): PackResources {
                return PathPackResources(info, path)
            }

            override fun openFull(info: PackLocationInfo, meta: Pack.Metadata): PackResources {
                return PathPackResources(info, path)
            }
        }

        val profile = Pack.readMetaAndCreate(
            PackLocationInfo(
                path.toString(),
                Component.literal("Pasture Block Fix"),
                PackSource.BUILT_IN,
                Optional.empty()
            ),
            factory,
            PackType.CLIENT_RESOURCES,
            PackSelectionConfig(true, Pack.Position.TOP, true)
        )
        event.addRepositorySource { consumer -> consumer.accept(profile) }
    }
}
