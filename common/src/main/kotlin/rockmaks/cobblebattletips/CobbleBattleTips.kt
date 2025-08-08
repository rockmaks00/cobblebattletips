package rockmaks.cobblebattletips

import com.mojang.logging.LogUtils.getLogger
import org.slf4j.Logger

object CobbleBattleTips {
    const val MOD_ID = "cobblebattletips"

    private const val CONFIG_PATH = "config/cobblebattletips/main.json"

    @JvmField
    val LOGGER: Logger = getLogger()

    fun init() {
        LOGGER.info(MOD_ID)
    }
}
