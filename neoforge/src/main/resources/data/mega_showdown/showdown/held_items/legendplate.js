{
name: "Legend Plate",
    onTryMove(p, t, m) {
      if (!(p.hasItem('legendplate') && m.id === 'judgment')) return;
      const tt = t.getTypes().length ? t.getTypes() : t.species.types, tn = this.dex.types.names();
      let bt = [], hs = -Infinity;
      for (const at of tn) {
        if (tt.some(dt => !this.dex.getImmunity(at, dt))) continue;
        const s = tt.reduce((a, dt) => a + (e = this.dex.getEffectiveness(at, dt), e === 2 ? 4 : e === 1 ? 2 : e === -1 ? -1 : e === -2 ? -2 : 0), 0);
        if (s > hs) bt = [at], hs = s;
        else if (s === hs) bt.push(at);
      }
      const btFinal = bt.includes('Normal') ? 'Normal' : this.sample(bt);
      if (p.name !== 'Arceus') return;
      const f = `Arceus-${btFinal}`, sp = this.dex.species.get(`arceus${btFinal.toLowerCase()}`);
      if (sp.exists && this.dex.species.get(f).exists && p.species.name !== f) p.formeChange(f, null, true);
      m.type = btFinal, m.ignoreAbility = true;
    },
    num: 2000,
    gen: 9,
  }