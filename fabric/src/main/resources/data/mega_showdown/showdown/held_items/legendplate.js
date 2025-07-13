{
    name: "legendplate",

    onTakeItem(item, pokemon, source) {
        return !((source?.baseSpecies.num === 493) || (pokemon.baseSpecies.num === 493));
    },

    onPrepareHit(source, target, move) {
        if (
            !source.hasItem('legendplate') ||
            move.id !== 'judgment' ||
            source.volatiles['legendplateused']
        ) return;

        let bestType = "Normal";
        let highestMod = -Infinity;

        for (const type of this.dex.types.names()) {
            const mod = this.dex.getEffectiveness(type, target);
            const modifier = 2 ** mod;
            if (modifier > highestMod) {
                bestType = type;
                highestMod = modifier;
            }
        }

        const targetForm = `Arceus-${bestType}`;
        if (source.species.name !== targetForm) {
            this.add('-start', source, 'typechange', bestType);
            source.formeChange(targetForm, this.effect);
        }

        move.type = bestType;
        move.ignoreAbility = true;

        source.addVolatile('legendplateused');
    },

    condition: {
        duration: 1
    }
}
