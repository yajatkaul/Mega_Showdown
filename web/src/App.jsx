import { Prism as SyntaxHighlighter } from "react-syntax-highlighter";
import { oneDark } from "react-syntax-highlighter/dist/esm/styles/prism";
import Markdown from "react-markdown";

function App() {
  const dataSections = [
    {
      title: "Battle Form Change",
      data: {
        pokemons: ["Charizard"],
        apply_aspects: ["dance_style=balie"],
        revert_aspects: ["dance_style=base"],
        effects: {
          minecraft: {
            particle_apply: "minecraft:flame",
            particle_revert: "minecraft:smoke",
            sound_apply: "minecraft:entity.blaze.shoot",
            sound_revert: "minecraft:block.fire.extinguish",
            particle_apply_amplifier: 1,
            particle_revert_amplifier: 1,
            animations: {
              animations_apply: ["cry"],
              expressions_apply: [],
              animations_revert: ["cry"],
              expressions_revert: [],
            },
          },
          snowstorm: {
            source_apply: ["target"],
            target_apply: ["target"],
            source_revert: ["target"],
            target_revert: ["target"],
            particle_apply: "cobblemon:mega",
            apply_after: 4.0,
            particle_revert: "cobblemon:unmega",
            revert_after: 2.0,
            sound_apply: "minecraft:block.fire.extinguish",
            sound_revert: "minecraft:block.fire.extinguish",
            animations: {
              animations_apply: ["cry"],
              expressions_apply: [],
              animations_revert: ["cry"],
              expressions_revert: [],
            },
          },
        },
        showdown_form_id_apply: "charizard-gmax",
        showdown_form_id_revert: "charizard",
      },
      details: [
        "# This is used to add mid battle form change",
        "## Filepath - `/<packname>/mega_showdown/battle_form`",
        "`pokemons*:` List of pokemons this battle form change should apply",
        "`apply_aspects*:` List of aspects you want to apply",
        "`revert_aspects*:` List of aspects you want to revert",
      ],
    },
    {
      title: "Fusion Data",
      data: {
        msd_id: "fusion_item_1",
        item_id: "minecraft:paper",
        item_name: "Fusion Gem",
        item_description: ["Fuses two Pokémon into one."],
        tradable_form: true,
        custom_model_data: 10,
        effects: {
          minecraft: {
            particle_apply: "minecraft:flame",
            particle_revert: "minecraft:smoke",
            sound_apply: "minecraft:entity.blaze.shoot",
            sound_revert: "minecraft:block.fire.extinguish",
            particle_apply_amplifier: 1,
            particle_revert_amplifier: 1,
            animations: {
              animations_apply: ["cry"],
              expressions_apply: [],
              animations_revert: ["cry"],
              expressions_revert: [],
            },
          },
          snowstorm: {
            source_apply: ["target"],
            target_apply: ["target"],
            source_revert: ["target"],
            target_revert: ["target"],
            particle_apply: "cobblemon:mega",
            apply_after: 4.0,
            particle_revert: "cobblemon:unmega",
            revert_after: 2.0,
            sound_apply: "minecraft:block.fire.extinguish",
            sound_revert: "minecraft:block.fire.extinguish",
            animations: {
              animations_apply: ["cry"],
              expressions_apply: [],
              animations_revert: ["cry"],
              expressions_revert: [],
            },
          },
        },
        fuse_if: [["galar"]],
        fusion_blacklist_aspects: [["orange"]],
        fusion_aspects: ["absofusion=true"],
        revert_if: [["absofusion"]],
        revert_aspects: ["absofusion=false"],
        fusion_mons: ["Charizard"],
        fuser_fuse_if: [["galar"]],
        fuser_blacklist_aspects: [["shiny"]],
        fuser_mons: ["Venasaur"],
      },
      details: [
        "# This is used to add custom fusions",
        "## Filepath - `/<packname>/mega_showdown/fusions`",
        "`pokemons*:` List of pokemons this battle form change should apply",
        "`tradable_form:` If the pokemon should tradable after fusion, `default: false`",
        "`fusion_aspects*:` List of aspects to be applied on fusion",
        "`revert_if*:` List of aspects need to be applied on the pokemon inorder to unfuse. You can have multiple options like if `[['galar']['shiny']]` then it will work if u have any of the above, if u want something such that it needs both of them then u can do `[['galar','shiny']]`",
        "`revert_aspects*:` List of aspects to be applied on unfuse",
        "`fuse_if*:` List of aspects required on fusion_mons to be used. You can have multiple options like if `[['galar']['shiny']]` then it will work if u have any of the above, if u want something such that it needs both of them then u can do `[['galar','shiny']]`",
        "`fusion_mons*:` List of pokemons which can be fuse",
        "`fuser_mons*:` List of pokemons which can be used to fuse",
        "`fuser_fuse_if*:` List of aspects required on fuser_mons to be used. You can have multiple options like if `[['galar']['shiny']]` then it will work if u have any of the above, if u want something such that it needs both of them then u can do `[['galar','shiny']]`",
      ],
    },
    {
      title: "Gmax Data",
      data: {
        pokemon: "Charizard",
        pokemonShowdownId: "charizard",
        gmaxMove: "gmax-wildfire",
        blacklist_aspects: [["shiny"], ["galarian"]],
        required_aspects: [["male"]],
      },
      details: [
        "# This is used to add custom gmax to pokemons",
        "## Filepath - `/<packname>/mega_showdown/gmax`",
        "`pokemon*:` Pokemon that can gmax",
        "`gmaxMove*:` Gmax move for that mon",
      ],
    },
    {
      title: "Held Item Data",
      data: {
        msd_id: "helditem_1",
        item_id: "minecraft:charcoal",
        item_name: "Charcoal",
        tradable_form: true,
        item_description: ["Boosts Fire-type moves."],
        pokemons: ["Charmander", "Charizard"],
        apply_if: [["shiny"]],
        apply_aspects: ["color=black"],
        revert_if: [["black"]],
        revert_aspects: ["color=green"],
        blacklist_aspects: [["shiny"], ["galarian"]],
        custom_model_data: 1,
        effects: {
          minecraft: {
            particle_apply: "minecraft:flame",
            particle_revert: "minecraft:smoke",
            sound_apply: "minecraft:entity.blaze.shoot",
            sound_revert: "minecraft:block.fire.extinguish",
            particle_apply_amplifier: 1,
            particle_revert_amplifier: 1,
            animations: {
              animations_apply: ["cry"],
              expressions_apply: [],
              animations_revert: ["cry"],
              expressions_revert: [],
            },
          },
          snowstorm: {
            source_apply: ["target"],
            target_apply: ["target"],
            source_revert: ["target"],
            target_revert: ["target"],
            particle_apply: "cobblemon:mega",
            apply_after: 4.0,
            particle_revert: "cobblemon:unmega",
            revert_after: 2.0,
            sound_apply: "minecraft:block.fire.extinguish",
            sound_revert: "minecraft:block.fire.extinguish",
            animations: {
              animations_apply: ["cry"],
              expressions_apply: [],
              animations_revert: ["cry"],
              expressions_revert: [],
            },
          },
        },
      },
      details: [
        "# This is used to add form changes based on held item change",
        "## Filepath - `/<packname>/mega_showdown/held_items`",
        "`msd_id*:` Mega showdown id to get the item using /msd give",
        "`item_id*:` Which item should be used",
        "`item_name*:` Item name",
        "`tradable_form*:` Can the mon be traded after form change",
        "`item_description*:` Lore on item, `default: []`",
        "`pokemons:` Pokemons which can change form using this",
        "`apply_if:` Aspects required before applying form change. You can have multiple options like if `[['galar']['shiny']]` then it will work if u have any of the above, if u want something such that it needs both of them then u can do `[['galar','shiny']]`",
        "`apply_aspects:` Aspects to apply on form change",
        "`revert_if:` Aspects required before applying form change. You can have multiple options like if `[['galar']['shiny']]` then it will work if u have any of the above, if u want something such that it needs both of them then u can do `[['galar','shiny']]`",
        "`revert_aspects:` Aspects required before reverting form change",
        "`custom_model_data:` Custom model data component for special item `default: 0`, 0 meaning even base item without any datacomponent can be used",
      ],
    },
    {
      title: "Key Item Data",
      data: {
        msd_id: "keyitem_1",
        item_id: "mega_showdown:mystic_key",
        item_name: "Mystic Key",
        item_description: ["Unlocks ancient doors."],
        consume: 1,
        pokemons: ["Mew", "Mewtwo"],
        blacklist_aspects: [["shiny"], ["galarian"]],
        apply_if: [["male"]],
        apply_aspects: ["galar=true"],
        revert_if: [["galar"]],
        revert_aspects: ["galar=false"],
        toggle_cycle: [["baile"], ["pom_pom"], ["pa'u"], ["sensu"]],
        toggle_aspects: [
          ["dance_style=baile"],
          ["dance_style=pom_pom"],
          ["dance_style=pa'u"],
          ["dance_style=sensu"],
        ],
        custom_model_data: 5,
        tradable_form: false,
        effects: {
          minecraft: {
            particle_apply: "minecraft:flame",
            particle_revert: "minecraft:smoke",
            sound_apply: "minecraft:entity.blaze.shoot",
            sound_revert: "minecraft:block.fire.extinguish",
            particle_apply_amplifier: 1,
            particle_revert_amplifier: 1,
            animations: {
              animations_apply: ["cry"],
              expressions_apply: [],
              animations_revert: ["cry"],
              expressions_revert: [],
            },
          },
          snowstorm: {
            source_apply: ["target"],
            target_apply: ["target"],
            source_revert: ["target"],
            target_revert: ["target"],
            particle_apply: "cobblemon:mega",
            apply_after: 4.0,
            particle_revert: "cobblemon:unmega",
            revert_after: 2.0,
            sound_apply: "minecraft:block.fire.extinguish",
            sound_revert: "minecraft:block.fire.extinguish",
            animations: {
              animations_apply: ["cry"],
              expressions_apply: [],
              animations_revert: ["cry"],
              expressions_revert: [],
            },
          },
        },
      },
      details: [
        "# This is used to add form changes when right clicking a pokemons",
        "## Filepath - `/<packname>/mega_showdown/key_items`",
        "`msd_id*:` Mega showdown id to get the item using /msd give",
        "`item_id*:` Which item should be used",
        "`item_name*:` Item name",
        "`custom_model_data:` Custom model data component for special item `default: 0`, 0 meaning even base item without any datacomponent can be used",
        "`item_description*:` Lore on item, `default: []`",
        "`showdown_id*:` Showdown mega stone id to attach with this item",
        "`pokemon*:` Pokemon on which u want to add a mega",
        "`consume:` If the itemStack should decrement the item on use `default: 0`",
        "`apply_if:` Aspects on pokemon requied to change form. You can have multiple options like if `[['galar']['shiny']]` then it will work if u have any of the above, if u want something such that it needs both of them then u can do `[['galar','shiny']]`",
        "`apply_aspects*:` Aspects to apply on form change",
        "`revert_if:` Aspects on pokemon requied to revert form. You can have multiple options like if `[['galar']['shiny']]` then it will work if u have any of the above, if u want something such that it needs both of them then u can do `[['galar','shiny']]`",
        "`revert_aspects:` Aspects to remove on reverting",
        "`toggle_cycle:` Aspects to toggle like how deoxys cycles in all its forms",
        "`toggle_aspects:` Aspects to apply on toggle like how deoxys cycles in all its forms",
        "`tradable_form:` Should it be able to be traded on form change `default: false`",
        "**IMPORTANT**: If toggle_aspects is not empty it will always use toggle aspects, so if you want to use normal apply, revert keep toggle_aspects empty",
      ],
    },
    {
      title: "Mega Data",
      data: {
        msd_id: "mega_charizard_x",
        showdown_id: "charizardite-z",
        item_id: "mega_showdown:pedistal",
        item_name: "Charizardite X",
        pokemon: "Charizard",
        required_aspects: [["galar"]],
        blacklist_aspects: [["shiny"]],
        item_description: ["Mega Evolves Charizard into Mega Charizard X."],
        apply_aspect: "mega_z",
        custom_model_data: 101,
      },
      details: [
        "# This is used to add custom mega pokemons",
        "## Filepath - `/<packname>/mega_showdown/mega`",
        "`msd_id*:` Mega showdown id to get the item using /msd give",
        "`item_id*:` Which item should be used",
        "`item_name*:` Item name",
        "`custom_model_data:` Custom model data component for special item `default: 0`, 0 meaning even base item without any datacomponent can be used",
        "`item_description*:` Lore on item, `default: []`",
        "`showdown_id*:` Showdown mega stone id to attach with this item",
        "`required_aspects:` If pokemon has this aspect only then it can mega, `default: []`. You can have multiple options like if `[['galar']['shiny']]` then it will work if u have any of the above, if u want something such that it needs both of them then u can do `[['galar','shiny']]`",
        "`blacklist_aspects:` If pokemon has this aspect it cant mega, `default: []`. You can have multiple options like if `[['galar']['shiny']]` then it will work if u have any of the above, if u want something such that it needs both of them then u can do `[['galar','shiny']]`",
        "`apply_aspect*:` the mega form u want mega, mega_x, mega_y, if u want something else u need to edit the species feature file",
        "`pokemon*:` Pokemon on which u want to add a mega",
      ],
    },
    {
      title: "Showdown Item Data",
      data: {
        msd_id: "showdown_item_1",
        item_id: "minecraft:wheat",
        item_name: "Leftovers",
        custom_model_data: 20,
        item_description: ["Restores HP each turn."],
        showdown_item_id: "magical_box",
      },
      details: [
        "# This is used to add showdown items like focus band to cobblemon",
        "## Filepath - `/<packname>/mega_showdown/showdown_item`",
        "`msd_id*:` Mega showdown id to get the item using /msd give",
        "`item_id*:` Which item should be used",
        "`item_name*:` Item name",
        "`custom_model_data:` Custom model data component for special item `default: 0`, 0 meaning even base item without any datacomponent can be used",
        "`item_description*:` Lore on item, `default: []`",
        "`showdown_item_id:` Showdown item id to attach with this item",
      ],
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
              <div className="p-2">
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

              <div className="ml-[30px] text-white mb-[15px] flex flex-col gap-2 mr-[30px]">
                {section.details?.map((detail, index) => {
                  return (
                    <Markdown
                      key={index}
                      components={{
                        code: ({
                          node,
                          inline,
                          className,
                          children,
                          ...props
                        }) => {
                          return inline ? (
                            <code {...props}>{children}</code>
                          ) : (
                            <code
                              className="bg-gray-600 px-1.5 py-0.5 rounded text-sm font-mono"
                              {...props}
                            >
                              {children}
                            </code>
                          );
                        },
                        h1: ({ node, ...props }) => (
                          <h1 className="text-3xl font-bold my-2" {...props} />
                        ),
                        h2: ({ node, ...props }) => (
                          <h2
                            className="text-2xl font-semibold my-2"
                            {...props}
                          />
                        ),
                        h3: ({ node, ...props }) => (
                          <h3 className="text-xl font-medium my-2" {...props} />
                        ),
                        // You can continue for h4-h6 if needed
                      }}
                    >
                      {detail}
                    </Markdown>
                  );
                })}
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
            Documentation for MSD Datapack • With Love {"<3"}
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
