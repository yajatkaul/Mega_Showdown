package com.github.yajatkaul.mega_showdown.mixin;

import com.cobblemon.mod.common.battles.ShowdownMoveset;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.github.yajatkaul.mega_showdown.codec.ZCrystal;
import com.github.yajatkaul.mega_showdown.gimmick.GimmickTurnCheck;
import com.github.yajatkaul.mega_showdown.gimmick.MegaGimmick;
import com.github.yajatkaul.mega_showdown.networking.client.packet.InteractionWheelPacket;
import com.github.yajatkaul.mega_showdown.tag.MegaShowdownTags;
import com.github.yajatkaul.mega_showdown.utils.AccessoriesUtils;
import com.github.yajatkaul.mega_showdown.utils.RegistryLocator;
import dev.architectury.networking.NetworkManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.concurrent.CompletableFuture;

@Mixin(value = PokemonEntity.class, remap = false)
public abstract class PokemonEntityMixin {
    @Inject(
            method = "recallWithAnimation",
            at = @At("HEAD"),
            cancellable = true
    )
    private void cancelRecallDuringEvolution(CallbackInfoReturnable<CompletableFuture<Pokemon>> cir) {
        PokemonEntity self = (PokemonEntity) (Object) this;

        boolean form_changing = self.getPokemon().getPersistentData().getBoolean("form_changing");
        if (form_changing) {
            CompletableFuture<Pokemon> future = new CompletableFuture<>();
            future.complete(self.getPokemon());
            cir.setReturnValue(future);
        }
    }

    @Inject(
            method = "showInteractionWheel",
            at = @At("HEAD")
    )
    private void showInteractionWheelInject(ServerPlayer player, ItemStack itemStack, CallbackInfo ci) {
        PokemonEntity self = (PokemonEntity) (Object) this;
        Pokemon pokemon = self.getPokemon();

        if (pokemon.getOwnerPlayer() == player) {
            ItemStack heldItem = pokemon.heldItem();
            boolean shouldPokemonMega = pokemon.getSpecies().getForms().stream()
                    .anyMatch(formData -> formData.getLabels().stream()
                            .anyMatch(MegaGimmick.getMegaAspects()::contains));
            boolean shouldPokemonUltra = pokemon.getSpecies().getName().equals("Necrozma");

            boolean hasMegaAccessory = AccessoriesUtils.checkTagInAccessories(player, MegaShowdownTags.Items.MEGA_BRACELET) || AccessoriesUtils.checkTagInAccessories(player, MegaShowdownTags.Items.OMNI_RING);
            boolean hasUltraAccessory = GimmickTurnCheck.hasGimmick(ShowdownMoveset.Gimmick.Z_POWER, player);

            boolean canPokemonMega = mega_showdown$canMegaToggle(pokemon, heldItem, player) && hasMegaAccessory;
            boolean canPokemonUltra = mega_showdown$canUltraToggle(pokemon, heldItem) && hasUltraAccessory;

            NetworkManager.sendToPlayer(player, new InteractionWheelPacket(shouldPokemonMega, shouldPokemonUltra, canPokemonMega, canPokemonUltra));
        }
    }

    @Unique
    private boolean mega_showdown$canMegaToggle(Pokemon pokemon, ItemStack heldItem, ServerPlayer player) {
        if (pokemon.getAspects().stream().anyMatch(MegaGimmick.getMegaAspects()::contains)) {
            return true;
        }
        if (pokemon.getSpecies().getName().equals("Rayquaza")) {
            for (int i = 0; i < 4; i++) {
                if (pokemon.getMoveSet().getMoves().get(i).getName().equals("dragonascent")) {
                    return true;
                }
            }
            return false;
        }
        MegaGimmick megaGimmick = RegistryLocator.getComponent(MegaGimmick.class, heldItem);

        if (megaGimmick == null || MegaGimmick.hasMega(player)) {
            return false;
        }

        if (!megaGimmick.aspect_conditions().validate_apply(pokemon)) {
            return false;
        }

        return megaGimmick.pokemons().contains(pokemon.getSpecies().getName());
    }

    @Unique
    private boolean mega_showdown$canUltraToggle(Pokemon pokemon, ItemStack heldItem) {
        ZCrystal zCrystal = RegistryLocator.getComponent(ZCrystal.class, heldItem);

        if (zCrystal == null) {
            return false;
        }

        if (pokemon.getSpecies().getName().equals("Necrozma") && zCrystal.showdown_item_id().equals("ultranecroziumz")) {
            return pokemon.getAspects().contains("dawn-fusion") || pokemon.getAspects().contains("dusk-fusion") || pokemon.getAspects().contains("ultra-fusion");
        }

        return false;
    }
}