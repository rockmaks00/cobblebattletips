plugins {
    id("cobblebattletips.base-conventions")
    alias(libs.plugins.kotlin.serialization)
}
architectury {
    common("neoforge")
}
dependencies {
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlin.reflect)
    implementation(libs.kotlin.json)
    modApi(libs.architectury)
    modImplementation(libs.cobblemon.common)
}