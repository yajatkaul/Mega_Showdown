{
    name: "flygonite",
    spritenum: 32988,
    megaStone: "Flygon-Mega",
    megaEvolves: "Flygon",
    itemUser: ["Flygon"],
    onTakeItem(item, source) {
      if (item.megaEvolves === source.baseSpecies.baseSpecies)
        return false;
      return true;
    },
    num: 999999,
    gen: 3,
    isNonstandard: "Past"
}