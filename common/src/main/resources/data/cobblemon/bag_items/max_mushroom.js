{
    use(battle, pokemon, itemId, data) {
    console.log(data);
        var stat1 = data[0];
        var stat2 = data[1];
        var stat3 = data[2];
        var stat4 = data[3];
        var stat5 = data[4];

        var boosts = {};
        boosts[stat1] = parseInt(data[5]);
        boosts[stat2] = parseInt(data[5]);
        boosts[stat3] = parseInt(data[5]);
        boosts[stat4] = parseInt(data[5]);
        boosts[stat5] = parseInt(data[5]);
        battle.boost(boosts, pokemon, null, { effectType: 'BagItem', name: itemId });
    }
}
