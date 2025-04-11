{
    use(battle, pokemon, itemId, data) {
        var amount = pokemon.heal(pokemon.maxhp - pokemon.hp);
        if (amount) {
            battle.add('-heal', pokemon, pokemon.getHealth, '[from] bagitem: ' + itemId);
        }
        pokemon.cureStatus(true);
        pokemon.removeVolatile('confusion');
    }
}
