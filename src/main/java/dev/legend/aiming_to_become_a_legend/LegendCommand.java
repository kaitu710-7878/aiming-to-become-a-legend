package dev.legend.aiming_to_become_a_legend;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class LegendCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("legend")
                        .executes(context -> showStatus(context.getSource(), context.getSource().getPlayerOrException()))
                        .then(Commands.literal("status")
                                .executes(context -> showStatus(context.getSource(), context.getSource().getPlayerOrException()))
                                .then(Commands.argument("target", EntityArgument.player())
                                        .requires(source -> source.hasPermission(2))
                                        .executes(context -> showStatus(context.getSource(), EntityArgument.getPlayer(context, "target"))))
                        )
                        .then(Commands.literal("sync")
                                .executes(context -> syncPlayer(context.getSource(), context.getSource().getPlayerOrException()))
                                .then(Commands.argument("target", EntityArgument.player())
                                        .requires(source -> source.hasPermission(2))
                                        .executes(context -> syncPlayer(context.getSource(), EntityArgument.getPlayer(context, "target"))))
                        )
        );
    }

    private static int showStatus(CommandSourceStack source, ServerPlayer player) {
        int advancements = LegendData.getAdvancementCount(player);
        int mobs = LegendData.getKilledMobCount(player);
        int items = LegendData.getEatenItemCount(player);
        int total = advancements + mobs + items;
        int tier = LegendData.getAppliedTier(player);

        double healthBonus = tier * Config.SERVER.healthPerTier.get();
        double damageBonus = tier * Config.SERVER.damagePerTier.get();
        double armorBonus = tier * Config.SERVER.armorPerTier.get();
        double toughnessBonus = tier * Config.SERVER.armorToughnessPerTier.get();
        double xpBonus = tier * Config.SERVER.experienceMultiplierPerTier.get() * 100.0;
        double enchantReduction = Math.min(80.0, tier * Config.SERVER.enchantDamageReductionPerTier.get() * 100.0);
        double legendReduction = Math.min(Config.SERVER.maxLegendDamageReduction.get() * 100.0, tier * Config.SERVER.legendDamageReductionPerTier.get() * 100.0);

        source.sendSuccess(() -> Component.translatable("commands.aiming_to_become_a_legend.status.header"), false);
        source.sendSuccess(() -> Component.translatable("commands.aiming_to_become_a_legend.status.tier", tier, total), false);
        source.sendSuccess(() -> Component.translatable("commands.aiming_to_become_a_legend.status.advancements", advancements), false);
        source.sendSuccess(() -> Component.translatable("commands.aiming_to_become_a_legend.status.mobs", mobs), false);
        source.sendSuccess(() -> Component.translatable("commands.aiming_to_become_a_legend.status.items", items), false);
        source.sendSuccess(() -> Component.translatable("commands.aiming_to_become_a_legend.status.buffs_1",
                String.format("%.1f", healthBonus),
                String.format("%.2f", damageBonus),
                String.format("%.1f", armorBonus),
                String.format("%.1f", toughnessBonus)), false);
        source.sendSuccess(() -> Component.translatable("commands.aiming_to_become_a_legend.status.buffs_2",
                String.format("%.1f%%", xpBonus),
                String.format("%.1f%%", enchantReduction),
                String.format("%.2f%%", legendReduction)), false);

        return 1;
    }

    private static int syncPlayer(CommandSourceStack source, ServerPlayer player) {
        LegendData.updateBuffs(player, false);
        source.sendSuccess(() -> Component.literal("Synchronized legend buffs for " + player.getScoreboardName()), true);
        return 1;
    }
}
