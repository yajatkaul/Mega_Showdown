import { Prism as SyntaxHighlighter } from "react-syntax-highlighter";
import { oneDark } from "react-syntax-highlighter/dist/esm/styles/prism";

function App() {
  const dataSections = [
    {
      title: "Battle Form Change",
      data: {
        pokemons: ["charizard"],
        apply_aspects: ["form:gmax"],
        revert_aspects: ["form:normal"],
        effects: {
          minecraft: {
            particle_apply: "minecraft:flame",
            particle_revert: "minecraft:smoke",
            sound_apply: "minecraft:entity.blaze.shoot",
            sound_revert: "minecraft:block.fire.extinguish",
            particle_apply_amplifier: 2,
            particle_revert_amplifier: 1,
          },
          snowstorm: {
            locator_apply: [],
            locator_revert: [],
            particle_apply: null,
            apply_after: 0.0,
            particle_revert: null,
            revert_after: 0.0,
            sound_apply: null,
            sound_revert: null,
            particle_apply_amplifier: 1,
            particle_revert_amplifier: 1,
          },
        },
        showdown_form_id_apply: "charizard-gmax",
        showdown_form_id_revert: "charizard",
      },
      details: []
    },
    {
      title: "Fusion Data",
      data: {
        msd_id: "fusion_item_1",
        item_id: "fusion_gem",
        item_name: "Fusion Gem",
        item_description: ["Fuses two Pokémon into one."],
        tradable_form: true,
        custom_model_data: 10,
        effects: {
          minecraft: {
            particle_apply: "minecraft:explosion",
            particle_revert: "minecraft:smoke",
            sound_apply: "minecraft:entity.generic.explode",
            sound_revert: "minecraft:block.fire.extinguish",
            particle_apply_amplifier: 4,
            particle_revert_amplifier: 2,
          },
          snowstorm: {
            locator_apply: [],
            locator_revert: [],
            particle_apply: null,
            apply_after: 0.0,
            particle_revert: null,
            revert_after: 0.0,
            sound_apply: null,
            sound_revert: null,
            particle_apply_amplifier: 1,
            particle_revert_amplifier: 1,
          },
        },
        fuse_if: ["aspect.genetic_match"],
        fusion_aspects: ["fusion:combined"],
        revert_if: ["aspect.separation"],
        revert_aspects: ["fusion:reverted"],
        fusion_mons: ["fusion_charizardvenasaur"],
        fuser_fuse_if: ["aspect.sync"],
        fuser_mons: ["charizard"],
      },
    },
    {
      title: "Gmax Data",
      data: {
        pokemon: "charizard",
        gmaxMove: "gmax-wildfire",
      },
    },
    {
      title: "Held Item Data",
      data: {
        msd_id: "helditem_1",
        item_id: "charcoal",
        item_name: "Charcoal",
        tradable_form: true,
        item_description: ["Boosts Fire-type moves."],
        pokemons: ["charmander", "charizard"],
        apply_if: ["aspect.fire"],
        apply_aspects: ["boost:fire"],
        revert_if: [],
        revert_aspects: ["boost:none"],
        blacklist_aspects: ["aspect.water"],
        custom_model_data: 1,
        effects: {
          minecraft: {
            particle_apply: "minecraft:flame",
            particle_revert: "minecraft:smoke",
            sound_apply: "minecraft:block.fire.ambient",
            sound_revert: "minecraft:block.fire.extinguish",
            particle_apply_amplifier: 1,
            particle_revert_amplifier: 1,
          },
          snowstorm: {
            locator_apply: [],
            locator_revert: [],
            particle_apply: null,
            apply_after: 0.0,
            particle_revert: null,
            revert_after: 0.0,
            sound_apply: null,
            sound_revert: null,
            particle_apply_amplifier: 1,
            particle_revert_amplifier: 1,
          },
        },
      },
    },
    {
      title: "Key Item Data",
      data: {
        msd_id: "keyitem_1",
        item_id: "mystic_key",
        item_name: "Mystic Key",
        item_description: ["Unlocks ancient doors."],
        consume: false,
        pokemons: ["mew", "mewtwo"],
        apply_if: ["aspect.psychic"],
        apply_aspects: ["unlock:door"],
        revert_if: [],
        revert_aspects: ["unlock:none"],
        toggle_cycle: [["aspect.psychic"], ["aspect.dark"]],
        toggle_aspects: [["form:alpha"], ["form:omega"]],
        custom_model_data: 5,
        tradable_form: false,
        effects: {
          minecraft: {
            particle_apply: "minecraft:enchant",
            particle_revert: "minecraft:smoke",
            sound_apply: "minecraft:block.enchantment_table.use",
            sound_revert: "minecraft:block.fire.extinguish",
            particle_apply_amplifier: 2,
            particle_revert_amplifier: 1,
          },
          snowstorm: {
            locator_apply: [],
            locator_revert: [],
            particle_apply: null,
            apply_after: 0.0,
            particle_revert: null,
            revert_after: 0.0,
            sound_apply: null,
            sound_revert: null,
            particle_apply_amplifier: 1,
            particle_revert_amplifier: 1,
          },
        },
      },
    },
    {
      title: "Mega Data",
      data: {
        msd_id: "mega_charizard_x",
        showdown_id: "charizard-mega-x",
        item_id: "charizardite_x",
        item_name: "Charizardite X",
        pokemon: "charizard",
        required_aspects: ["aspect.fire"],
        blacklist_aspects: ["aspect.water"],
        item_description: ["Mega Evolves Charizard into Mega Charizard X."],
        apply_aspects: ["mega:x"],
        custom_model_data: 101,
      },
    },
    {
      title: "Showdown Item Data",
      data: {
        msd_id: "showdown_item_1",
        item_id: "leftovers",
        item_name: "Leftovers",
        custom_model_data: 20,
        item_description: ["Restores HP each turn."],
        showdown_item_id: "leftovers",
      },
    },
  ];

  return (
    <div className="min-h-screen bg-gradient-to-br from-gray-900 via-slate-900 to-black">
      <div className="container mx-auto px-4 py-12 max-w-6xl">
        {/* Header */}
        <div className="text-center mb-12">
          <div className="inline-flex items-center justify-center w-16 h-16 bg-gradient-to-r from-blue-500 to-purple-600 rounded-2xl mb-6 shadow-lg shadow-blue-500/25">
            <svg
              className="w-8 h-8 text-white"
              fill="none"
              stroke="currentColor"
              viewBox="0 0 24 24"
            >
              <path
                strokeLinecap="round"
                strokeLinejoin="round"
                strokeWidth={2}
                d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z"
              />
            </svg>
          </div>
          <h1 className="text-4xl md:text-5xl font-bold bg-gradient-to-r from-white via-blue-200 to-purple-200 bg-clip-text text-transparent mb-4">
            MSD Datapack Documentation
          </h1>
          <p className="text-lg text-gray-400 max-w-2xl mx-auto">
            Comprehensive documentation for the MSD Datapack configuration
          </p>
        </div>

        {/* Documentation Sections */}
        <div className="grid gap-8 md:gap-10">
          {dataSections.map((section, index) => (
            <div
              key={index}
              className="group relative bg-gray-800/50 backdrop-blur-sm border border-gray-700/50 rounded-2xl shadow-2xl transition-all duration-300 overflow-hidden"
            >
              {/* Section Header */}
              <div className="bg-gradient-to-r from-blue-600 to-purple-600 px-6 py-4">
                <div className="flex items-center gap-3">
                  <div className="w-2 h-2 bg-white/90 rounded-full animate-pulse"></div>
                  <h2 className="text-xl md:text-2xl font-semibold text-white">
                    {section.title}
                  </h2>
                </div>
              </div>

              {/* JSON Content */}
              <div className="p-6">
                <div className="relative">
                  <div className="absolute top-4 right-4 flex gap-2 z-10">
                    <div className="w-3 h-3 bg-red-500 rounded-full"></div>
                    <div className="w-3 h-3 bg-yellow-500 rounded-full"></div>
                    <div className="w-3 h-3 bg-green-500 rounded-full"></div>
                  </div>

                  <div className="bg-[#292c35] rounded-xl p-2 overflow-hidden">
                    <pre className="text-sm text-gray-100 whitespace-pre-wrap break-words overflow-auto max-h-[500px] scrollbar-thin scrollbar-thumb-gray-600 scrollbar-track-gray-900">
                      <SyntaxHighlighter language="json" style={oneDark}>
                        {JSON.stringify(section.data, null, 2)}
                      </SyntaxHighlighter>
                    </pre>
                  </div>
                </div>
              </div>

              {/* Decorative Elements */}
              <div className="absolute top-0 left-0 w-full h-1 bg-gradient-to-r from-blue-500 via-purple-500 to-pink-500 opacity-0 transition-opacity duration-300"></div>

              {/* Glow effect */}
              <div className="absolute inset-0 bg-gradient-to-r from-blue-500/5 to-purple-500/5 opacity-0 transition-opacity duration-300 pointer-events-none"></div>
            </div>
          ))}
        </div>

        {/* Footer */}
        <div className="text-center mt-16 pt-8 border-t border-gray-800">
          <p className="text-gray-500 text-sm">
            Generated documentation for MSD Datapack • Last updated:{" "}
            {new Date().toLocaleDateString()}
          </p>
        </div>
      </div>

      {/* Background Decorations */}
      <div className="fixed inset-0 -z-10 overflow-hidden pointer-events-none">
        <div className="absolute -top-40 -right-40 w-80 h-80 bg-blue-600/10 rounded-full blur-3xl"></div>
        <div className="absolute -bottom-40 -left-40 w-80 h-80 bg-purple-600/10 rounded-full blur-3xl"></div>
        <div className="absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 w-96 h-96 bg-indigo-600/5 rounded-full blur-3xl"></div>
      </div>
    </div>
  );
}

export default App;
