package com.github.yutak.aiming_to_become_a_legend;

import net.minecraft.advancements.Advancement;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = AimingToBecomeALegend.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class LegendEventHandler {

    @SubscribeEvent
    public static void onAdvancementEarn(AdvancementEvent.AdvancementEarnEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }

        Advancement advancement = event.getAdvancement();
        if (Config.SERVER.onlyVisibleAdvancements.get() && advancement.getDisplay() == null) {
            return;
        }

        if (LegendData.addAdvancement(player, advancement.getId())) {
            LegendData.updateBuffs(player, true);
        }
    }

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        LivingEntity victim = event.getEntity();
        if (victim instanceof Player) {
            // Only count non-player entities as mobs
            return;
        }

        Entity attacker = event.getSource().getEntity();
        ServerPlayer player = null;
        if (attacker instanceof ServerPlayer sp) {
            player = sp;
        } else if (attacker instanceof TamableAnimal tamable && tamable.getOwner() instanceof ServerPlayer sp) {
            player = sp;
        }

        if (player == null) {
            return;
        }

        ResourceLocation mobId = ForgeRegistries.ENTITY_TYPES.getKey(victim.getType());
        if (mobId != null) {
            if (LegendData.addKilledMob(player, mobId)) {
                LegendData.updateBuffs(player, true);
            }
        }
    }

    @SubscribeEvent
    public static void onItemUseFinish(LivingEntityUseItemEvent.Finish event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }

        ItemStack itemStack = event.getItem();
        if (!itemStack.isEmpty() && itemStack.getItem().isEdible()) {
            ResourceLocation itemId = ForgeRegistries.ITEMS.getKey(itemStack.getItem());
            if (itemId != null) {
                if (LegendData.addEatenItem(player, itemId)) {
                    LegendData.updateBuffs(player, true);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }

        MinecraftServer server = player.getServer();
        if (server != null) {
            boolean onlyVisible = Config.SERVER.onlyVisibleAdvancements.get();
            for (Advancement advancement : server.getAdvancements().getAllAdvancements()) {
                if (onlyVisible && advancement.getDisplay() == null) {
                    continue;
                }
                if (player.getAdvancements().getOrStartProgress(advancement).isDone()) {
                    LegendData.addAdvancement(player, advancement.getId());
                }
            }
        }

        // Apply attribute buffs on login
        LegendData.updateBuffs(player, false);
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (event.isWasDeath() && event.getOriginal() instanceof ServerPlayer oldPlayer && event.getEntity() instanceof ServerPlayer newPlayer) {
            CompoundTag oldTag = LegendData.getModData(oldPlayer);
            CompoundTag newTag = LegendData.getModData(newPlayer);
            newTag.merge(oldTag.copy());
        }
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            LegendData.updateBuffs(player, false);
        }
    }
}
