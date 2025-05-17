package com.cobblemon.yajatkaul.mega_showdown.megaevo;

import com.cobblemon.mod.common.api.pokemon.feature.StringSpeciesFeature;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.yajatkaul.mega_showdown.config.Config;
import com.cobblemon.yajatkaul.mega_showdown.advancement.AdvancementHelper;
import com.cobblemon.yajatkaul.mega_showdown.datamanage.DataManage;
import com.cobblemon.yajatkaul.mega_showdown.datamanage.PokeHandler;
import com.cobblemon.yajatkaul.mega_showdown.datapack.data.MegaData;
import com.cobblemon.yajatkaul.mega_showdown.item.MegaStones;
import com.cobblemon.yajatkaul.mega_showdown.sound.ModSounds;
import com.cobblemon.yajatkaul.mega_showdown.utility.LazyLib;
import com.cobblemon.yajatkaul.mega_showdown.utility.ModTags;
import com.cobblemon.yajatkaul.mega_showdown.utility.Utils;
import kotlin.Unit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.cobblemon.yajatkaul.mega_showdown.utility.Utils.setTradable;

public class MegaLogic {
    private static final Map<UUID, Long> cooldowns = new HashMap<>();
    private static final long COOLDOWN_TIME = 2000; // 2 sec

    public static boolean Possible(ServerPlayer player, boolean fromBattle) {
        UUID playerId = player.getUUID();
        long currentTime = System.currentTimeMillis();

        if (cooldowns.containsKey(playerId) && currentTime < cooldowns.get(playerId) && !fromBattle) {
            player.displayClientMessage(Component.translatable("message.mega_showdown.not_so_fast")
                    .withColor(0xFF0000), true);
            return false;
        }

        boolean hasMegaItemCurios = CuriosApi.getCuriosInventory(player)
                .map(inventory -> inventory.isEquipped(stack -> (stack.is(ModTags.Items.MEGA_BRACELETS))))
                .orElse(false);

        boolean hasOffhandMegaItem = player.getOffhandItem().is(ModTags.Items.MEGA_BRACELETS);
        boolean hasMainhandMegaItem = player.getMainHandItem().is(ModTags.Items.MEGA_BRACELETS);

        if(fromBattle){
            if (!hasMegaItemCurios && !hasOffhandMegaItem) {
                return false;
            }
        }else {
            if (!hasMegaItemCurios && !hasOffhandMegaItem && !hasMainhandMegaItem) {
                return false;
            }
        }


        // Apply cooldown
        cooldowns.put(playerId, currentTime + COOLDOWN_TIME);
        return true;
    }

    public static void EvoLogic(Player playerContext){
        ServerPlayer player = (ServerPlayer) playerContext;

        if(Config.battleModeOnly){
            return;
        }

        double range = 5.0;

        // Check for entities first
        Vec3 startPos = player.getEyePosition();
        Vec3 lookVec = player.getViewVector(1.0F);
        Vec3 endPos = startPos.add(lookVec.multiply(range, range, range));

        AABB searchBox = new AABB(startPos, endPos).inflate(1.0);

        EntityHitResult entityHit = ProjectileUtil.getEntityHitResult(
                player.level(),
                player,
                startPos,
                endPos,
                searchBox,
                entity -> !entity.isSpectator() && entity.isPickable(),
                0.3f
        );

        if (entityHit == null) {
            return;
        }

        if(entityHit.getEntity() instanceof PokemonEntity pk){
            if(pk.level().isClientSide){
                return;
            }

            if(!Config.mega || pk.getPokemon().getOwnerPlayer() != player || (!Utils.MEGA_POKEMONS.contains(pk.getPokemon().getSpecies().getName()) && !pk.getPokemon().getSpecies().getName().equals("Rayquaza")) || !Possible(player, false)){
                return;
            }

            boolean isMega = pk.getAspects().stream()
                    .anyMatch(aspect -> aspect.startsWith("mega"));

            if(isMega){
                Devolve(pk.getPokemon(), false);
            }else {
                megaEvolve(pk, false);
            }
        }
    }

    public static void Evolve(PokemonEntity context, Player player, Boolean fromBattle){
        if(context.getPokemon().getOwnerPlayer() != player || player.level().isClientSide){
            return;
        }

        Pokemon pokemon = context.getPokemon();
        String species = Utils.MEGA_STONE_IDS.get(pokemon.heldItem().getItem());

        if(context instanceof PokemonEntity pk && (pk.isBattling() && !fromBattle)){
            player.displayClientMessage(Component.translatable("message.mega_showdown.battle_not_allowed")
                    .withColor(0xFF0000), true);
            return;
        }

        if(pokemon.getSpecies().getName().equals("Rayquaza")){
            if(Config.friendshipMode && pokemon.getFriendship() < 200 && !pokemon.getEntity().isBattling()){
                player.displayClientMessage(Component.translatable("message.mega_showdown.bond_not_close_mega")
                        .withColor(0xFF0000), true);
                return;
            }

            boolean found = false;
            for (int i = 0; i < 4; i++){
                if (pokemon.getMoveSet().getMoves().get(i).getName().equals("dragonascent")) {
                    player.setData(DataManage.MEGA_POKEMON, new PokeHandler(pokemon)); // ✅ Wrap in PokemonRef
                    player.setData(DataManage.MEGA_DATA, true);

                    AdvancementHelper.grantAdvancement(context.getPokemon().getOwnerPlayer(), "mega/mega_evolve");

                    new StringSpeciesFeature("mega_evolution", "mega").apply(pokemon);
                    setTradable(pokemon, false);
                    found = true;
                }
            }
            if(!found){
                player.displayClientMessage(Component.translatable("message.mega_showdown.rayquaza_no_dragonascent")
                        .withColor(0xFF0000), true);
            }
            return;
        }

        if(species == null){
            for(MegaData megaPok: Utils.megaRegistry){
                String[] parts = megaPok.item_id().split(":");
                ResourceLocation paperId = ResourceLocation.fromNamespaceAndPath(parts[0], parts[1]);
                Item paperItem = BuiltInRegistries.ITEM.get(paperId);
                if(paperItem == pokemon.heldItem().getItem()
                        && pokemon.heldItem().get(DataComponents.CUSTOM_MODEL_DATA).value()
                        == megaPok.custom_model_data()){
                    species = megaPok.pokemon();
                }
                if(species == null){
                    continue;
                }
                if(species.equals(pokemon.getSpecies().getName())){
                    player.setData(DataManage.MEGA_DATA, true);
                    player.setData(DataManage.MEGA_POKEMON, new PokeHandler(pokemon));

                    AdvancementHelper.grantAdvancement(context.getPokemon().getOwnerPlayer(), "mega/mega_evolve");

                    for(String aspect: megaPok.aspects()){
                        String[] aspectDiv = aspect.split("=");
                        new StringSpeciesFeature(aspectDiv[0], aspectDiv[1]).apply(pokemon);
                    }
                    setTradable(pokemon, false);

                    return;
                }else{
                    player.displayClientMessage(Component.translatable( "message.mega_showdown.incorrect_mega_stone")
                            .withColor(0xFF0000), true);
                    return;
                }
            }
            return;
        }

        if(Config.friendshipMode && pokemon.getFriendship() < 200 && !pokemon.getEntity().isBattling()){
            player.displayClientMessage(Component.translatable("message.mega_showdown.bond_not_close_mega")
                    .withColor(0xFF0000), true);
            return;
        }

        if(species.equals(pokemon.getSpecies().getName())){
            if(species.equals("Charizard")){
                if(pokemon.heldItem().is(MegaStones.CHARIZARDITE_X)){
                    player.setData(DataManage.MEGA_DATA, true);
                    player.setData(DataManage.MEGA_POKEMON, new PokeHandler(pokemon));

                    AdvancementHelper.grantAdvancement(context.getPokemon().getOwnerPlayer(), "mega/mega_evolve");

                    new StringSpeciesFeature("mega_evolution", "mega_x").apply(pokemon);
                    setTradable(pokemon, false);

                }else if(pokemon.heldItem().is(MegaStones.CHARIZARDITE_Y)){
                    player.setData(DataManage.MEGA_DATA, true);
                    player.setData(DataManage.MEGA_POKEMON, new PokeHandler(pokemon));

                    AdvancementHelper.grantAdvancement(context.getPokemon().getOwnerPlayer(), "mega/mega_evolve");

                    new StringSpeciesFeature("mega_evolution", "mega_y").apply(pokemon);
                    setTradable(pokemon, false);

                }
            }
            else if(species.equals("Mewtwo")){
                if(pokemon.heldItem().is(MegaStones.MEWTWONITE_X)){
                    player.setData(DataManage.MEGA_DATA, true);
                    player.setData(DataManage.MEGA_POKEMON, new PokeHandler(pokemon));

                    AdvancementHelper.grantAdvancement(context.getPokemon().getOwnerPlayer(), "mega/mega_evolve");

                    new StringSpeciesFeature("mega_evolution", "mega_x").apply(pokemon);
                    setTradable(pokemon, false);

                }else if(pokemon.heldItem().is(MegaStones.MEWTWONITE_Y)){
                    player.setData(DataManage.MEGA_DATA, true);
                    player.setData(DataManage.MEGA_POKEMON, new PokeHandler(pokemon));

                    AdvancementHelper.grantAdvancement(context.getPokemon().getOwnerPlayer(), "mega/mega_evolve");

                    new StringSpeciesFeature("mega_evolution", "mega_y").apply(pokemon);
                    setTradable(pokemon, false);
                }
            }
            else{
                player.setData(DataManage.MEGA_DATA, true);
                player.setData(DataManage.MEGA_POKEMON, new PokeHandler(pokemon));

                new StringSpeciesFeature("mega_evolution", "mega").apply(pokemon);

                setTradable(pokemon, false);

                AdvancementHelper.grantAdvancement(context.getPokemon().getOwnerPlayer(), "mega/mega_evolve");
            }
        }else{
            player.displayClientMessage(Component.translatable( "message.mega_showdown.incorrect_mega_stone")
                    .withColor(0xFF0000), true);
        }

    }

    public static void Devolve(Pokemon context, Boolean fromBattle){
        ServerPlayer player = context.getOwnerPlayer();

        if(context instanceof Pokemon pk){
            if(pk.getOwnerPlayer() != player){
                return;
            }

            if(pk.getEntity() != null && pk.getEntity().isBattling() && !fromBattle){
                player.displayClientMessage(Component.translatable("message.mega_showdown.battle_not_allowed")
                        .withColor(0xFF0000), true);
                return;
            }


            player.setData(DataManage.MEGA_DATA, false);
            player.removeData(DataManage.MEGA_POKEMON);

            if(context.getEntity() != null){
                playDevolveAnimation(context.getEntity());
            }

            new StringSpeciesFeature("mega_evolution", "none").apply(pk);
            setTradable(pk, true);
        }
    }
    public static void megaEvolve(PokemonEntity context, boolean fromBattle) {
        ServerPlayer player = context.getPokemon().getOwnerPlayer();
        if(!player.getData(DataManage.MEGA_DATA) || Config.multipleMegas){
            context.getEntityData().set(PokemonEntity.getEVOLUTION_STARTED(), true);
            LazyLib.Companion.snowStormPartileSpawner(context, "mega_evolution");

            BlockPos entityPos = context.getOnPos();
            context.level().playSound(
                    null, entityPos.getX(), entityPos.getY(), entityPos.getZ(),
                    ModSounds.MEGA.get(),
                    SoundSource.PLAYERS, 0.2f, 0.8f
            );

            context.after(4.8F, () ->{
                LazyLib.Companion.cryAnimation(context);
                MegaLogic.Evolve(context, player, fromBattle);
                context.getEntityData().set(PokemonEntity.getEVOLUTION_STARTED(), false);
                return Unit.INSTANCE;
            });
        }else {
            player.displayClientMessage(Component.translatable("message.mega_showdown.mega_limit")
                    .withColor(0xFF0000), true);
        }
    }

    public static void playDevolveAnimation(LivingEntity context){
        if (context.level() instanceof ServerLevel serverLevel) {
            Vec3 entityPos = context.position(); // Get entity position

            // Get entity's size
            double entityWidth = context.getBbWidth();
            double entityHeight = context.getBbHeight();

            // Play sound effect
            serverLevel.playSound(
                    null, entityPos.x, entityPos.y, entityPos.z,
                    SoundEvents.BEACON_DEACTIVATE, // Change this if needed
                    SoundSource.PLAYERS, 1.5f, 0.5f + (float) Math.random() * 0.5f
            );

            // Adjust particle effect based on entity size
            int particleCount = (int) (100 * entityWidth * entityHeight); // Scale particle amount
            double radius = entityWidth * 0.8; // Adjust radius based on width

            for (int i = 0; i < particleCount; i++) {
                double angle = Math.random() * 2 * Math.PI;
                double xOffset = Math.cos(angle) * radius;
                double zOffset = Math.sin(angle) * radius;
                double yOffset = Math.random() * entityHeight; // Spread particles vertically

                serverLevel.sendParticles(
                        ParticleTypes.END_ROD, // Change this to any particle type
                        entityPos.x + xOffset,
                        entityPos.y + yOffset,
                        entityPos.z + zOffset,
                        1, // One particle per call for better spread
                        0, 0, 0, // No movement velocity
                        0.1 // Slight motion
                );
            }
        }
    }
}
