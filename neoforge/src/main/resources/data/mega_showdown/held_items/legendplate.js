{
    name: "legendplate",
    onTryMove(pokemon, target, move) {
        if (!(pokemon.hasItem("legendplate") && move.id === "judgment")) return;

          const targetTypes = target.getTypes();
          let bestType = "Normal";
          let highestEffectiveness = -99;

          const typeChart = {
              Normal: { superEffective: [], resistedBy: ["Rock","Steel"], immune: ["Ghost"] },
              Fire: { superEffective: ["Grass", "Bug", "Ice", "Steel"], resistedBy: ["Fire", "Water", "Rock", "Dragon"], immune: [] },
              Water: { superEffective: ["Fire", "Ground", "Rock"], resistedBy: ["Water", "Grass", "Dragon"], immune: [] },
              Electric: { superEffective: ["Water", "Flying"], resistedBy: ["Electric", "Grass", "Dragon"], immune: ["Ground"] },
              Grass: { superEffective: ["Water", "Ground", "Rock"], resistedBy: ["Fire", "Grass", "Poison", "Flying", "Bug", "Dragon", "Steel"], immune: [] },
              Ice: { superEffective: ["Grass", "Ground", "Flying", "Dragon"], resistedBy: ["Fire", "Water", "Ice", "Steel"], immune: [] },
              Fighting: { superEffective: ["Normal", "Ice", "Rock", "Dark", "Steel"], resistedBy: ["Flying", "Poison", "Bug", "Psychic", "Fairy"], immune: ["Ghost"] },
              Poison: { superEffective: ["Grass", "Fairy"], resistedBy: ["Poison", "Ground", "Rock", "Ghost"], immune: ["Steel"] },
              Ground: { superEffective: ["Fire", "Electric", "Poison", "Rock", "Steel"], resistedBy: ["Bug", "Grass"], immune: ["Flying"] },
              Flying: { superEffective: ["Fighting", "Bug", "Grass"], resistedBy: ["Electric", "Rock", "Steel"], immune: [] },
              Psychic: { superEffective: ["Fighting", "Poison"], resistedBy: ["Psychic", "Steel"], immune: ["Dark"] },
              Bug: { superEffective: ["Grass", "Psychic", "Dark"], resistedBy: ["Fire", "Fighting", "Poison", "Flying", "Ghost", "Steel", "Fairy"], immune: [] },
              Rock: { superEffective: ["Fire", "Ice", "Flying", "Bug"], resistedBy: ["Fighting", "Ground", "Steel"], immune: [] },
              Ghost: { superEffective: ["Ghost", "Psychic"], resistedBy: ["Dark"], immune: ["Normal"] },
              Dragon: { superEffective: ["Dragon"], resistedBy: ["Steel"], immune: ["Fairy"] },
              Dark: { superEffective: ["Psychic", "Ghost"], resistedBy: ["Fighting", "Dark", "Fairy"], immune: [] },
              Steel: { superEffective: ["Ice", "Rock", "Fairy"], resistedBy: ["Fire", "Water", "Electric", "Steel"], immune: [] },
              Fairy: { superEffective: ["Fighting", "Dragon", "Dark"], resistedBy: ["Fire", "Poison", "Steel"], immune: [] }
          };

          for (const [type, data] of Object.entries(typeChart)) {
              let effectiveness = 0;

              for (const targetType of targetTypes) {
                  if (data.immune.includes(targetType)) {
                      effectiveness = -99;
                      break;
                  }
                  if (data.superEffective.includes(targetType)) {
                      effectiveness += 2;
                  }
                  if (data.resistedBy.includes(targetType)) {
                      effectiveness -= 2;
                  }
              }

              if (effectiveness > highestEffectiveness) {
                  bestType = type;
                  highestEffectiveness = effectiveness;
              }
          }
          if (pokemon.name === "Arceus") {
              if (pokemon.species.name !== `Arceus-${bestType}`) {
                  pokemon.formeChange(`Arceus-${bestType}`, null, true);
              }
              move.type = bestType;
              move.ignoreAbility = true;
          }
      },
      onUse(pokemon) {
          if (pokemon.name === "Arceus") {

          }
      }
  }
