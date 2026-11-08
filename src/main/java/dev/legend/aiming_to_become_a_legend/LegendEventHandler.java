package dev.legend.aiming_to_become_a_legend;

import net.minecraft.advancements.Advancement;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = AimingToBecomeALegend.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class LegendEventHandler {

    public static boolean isValidAdvancement(Advancement advancement) {
        if (Config.SERVER.onlyVisibleAdvancements.get() && advancement.getDisplay() == null) {
            return false;
        }
        if (Config.SERVER.ignoreRootAdvancements.get() && advancement.getParent() == null) {
            return false;
        }
        return true;
    }

    @SubscribeEvent
    public static void onAdvancementEarn(AdvancementEvent.AdvancementEarnEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }

        Advancement advancement = event.getAdvancement();
        if (!isValidAdvancement(advancement)) {
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
    public static void onXpChange(PlayerXpEvent.XpChange event) {
        // If Apothic Attributes is present, it handles experience amplification via attributeslib:experience_gained.
        // Fallback to our event-based scaling only when Apothic Attributes is absent.
        if (LegendData.isUsingApothicXp()) {
            return;
        }

        if (event.getEntity() instanceof ServerPlayer player) {
            int originalAmount = event.getAmount();
            if (originalAmount > 0) {
                AttributeInstance xpAttr = player.getAttribute(ModAttributes.EXPERIENCE_MULTIPLIER.get());
                if (xpAttr != null && xpAttr.getValue() > 0.0) {
                    double mult = xpAttr.getValue();
                    double bonus = originalAmount * mult;
                    int intBonus = (int) bonus;
                    if (player.getRandom().nextDouble() < (bonus - intBonus)) {
                        intBonus++;
                    }
                    event.setAmount(originalAmount + intBonus);
                }
            }
        }
    }

    /**
     * Universal damage reduction pipeline.
     * Priority is set to LOW so all vanilla & modded armor/resistance/piercing calculations have taken effect,
     * allowing pure universal reduction on the final incoming damage value without hardcoding any specific mod or damage type.
     */
    @SubscribeEvent(priority = EventPriority.LOW)
    public static void onLivingDamage(LivingDamageEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }

        DamageSource source = event.getSource();
        float damage = event.getAmount();
        if (damage <= 0.0F) {
            return;
        }

        // 1. Enchantment-like damage reduction (+1% per tier, capped at 80% total with armor, ineffective if armor already hits the cap)
        // Respects vanilla enchantment bypasses.
        if (!source.is(DamageTypeTags.BYPASSES_ENCHANTMENTS)) {
            int epf = EnchantmentHelper.getDamageProtection(player.getArmorSlots(), source);
            float armorProtFraction = Math.min(20, epf) / 25.0F; // vanilla enchantment protection: 0.0 to 0.80

            if (armorProtFraction < 0.80F) {
                AttributeInstance enchantAttr = player.getAttribute(ModAttributes.ENCHANT_DAMAGE_REDUCTION.get());
                if (enchantAttr != null && enchantAttr.getValue() > 0.0) {
                    float totalProt = (float) Math.min(0.80, armorProtFraction + enchantAttr.getValue());
                    if (totalProt > armorProtFraction) {
                        float factor = (1.0F - totalProt) / (1.0F - armorProtFraction);
                        damage *= factor;
                    }
                }
            }
        }

        // 2. Pure Universal Non-Enchantment Damage Reduction (+0.1% per tier, capped at 80%)
        // Applies to ALL damage sources unconditionally (including armor-piercing and enchantment-piercing damage).
        // No ad-hoc workarounds or hardcoded damage types: purely universal.
        AttributeInstance legendAttr = player.getAttribute(ModAttributes.LEGEND_DAMAGE_REDUCTION.get());
        if (legendAttr != null && legendAttr.getValue() > 0.0) {
            float legendReduction = (float) Math.min(Config.SERVER.maxLegendDamageReduction.get(), legendAttr.getValue());
            damage *= (1.0F - legendReduction);
        }

        event.setAmount(damage);
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }

        MinecraftServer server = player.getServer();
        if (server != null) {
            for (Advancement advancement : server.getAdvancements().getAllAdvancements()) {
                if (!isValidAdvancement(advancement)) {
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
