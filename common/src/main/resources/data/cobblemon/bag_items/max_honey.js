{
    use(battle, pokemon, itemId, data) {
        var healthRatio = parseFloat(1);
        if (pokemon.fainted) {
			if (pokemon.position < pokemon.side.active.length) {
				battle.queue.addChoice({
					choice: 'instaswitch',
					pokemon: pokemon,
					target: pokemon,
				});
			}
            pokemon.fainted = false;
            pokemon.side.pokemonLeft++;
            pokemon.faintQueued = false;
            pokemon.subFainted = false;
            pokemon.hp = 1;
            pokemon.cureStatus(false);
            pokemon.sethp(Math.floor(healthRatio * pokemon.maxhp));
            battle.add('-heal', pokemon, pokemon.getHealth, '[from] bagitem: ' + itemId);
        }
    }
}
