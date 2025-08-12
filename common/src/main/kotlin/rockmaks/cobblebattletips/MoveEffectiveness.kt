package rockmaks.cobblebattletips

import com.cobblemon.mod.common.api.moves.MoveTemplate
import com.cobblemon.mod.common.api.pokemon.PokemonSpecies
import com.cobblemon.mod.common.api.types.ElementalType
import com.cobblemon.mod.common.battles.Targetable
import com.cobblemon.mod.common.battles.ai.getDamageMultiplier
import com.cobblemon.mod.common.client.battle.ActiveClientBattlePokemon

object MoveEffectiveness {
    fun getModifier(move: MoveTemplate, target: Targetable): Double {
        val moveType = move.elementalType
        val (primaryType, secondaryType) = getElementalTypes(target)

        var damageMultiplier = 1.0
        // getDamageMultiplier will be deprecated in Cobblemon 1.7, check AIUtility
        if (primaryType != null) {
            damageMultiplier *= getDamageMultiplier(moveType, primaryType)
        }
        if (secondaryType != null) {
            damageMultiplier *= getDamageMultiplier(moveType, secondaryType)
        }

        return damageMultiplier
    }

    /**
     * Returns accurate types that consider alternate Pokémon forms
     */
    private fun getElementalTypes(target: Targetable): Pair<ElementalType?, ElementalType?> {
        if (target is ActiveClientBattlePokemon) {
            val props = target.battlePokemon?.properties
            val species = PokemonSpecies.getByName(props?.species ?: return null to null)
            val formData = species?.forms
                ?.firstOrNull { it.name.equals(props.form, true) }
            return formData?.primaryType to formData?.secondaryType
        }
        return null to null
    }
}