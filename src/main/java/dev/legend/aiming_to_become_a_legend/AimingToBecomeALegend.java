package dev.legend.aiming_to_become_a_legend;

import com.mojang.logging.LogUtils;
import net.minecraft.world.entity.EntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import org.slf4j.Logger;

@Mod(AimingToBecomeALegend.MODID)
public class AimingToBecomeALegend {
    public static final String MODID = "aiming_to_become_a_legend";
    public static final Logger LOGGER = LogUtils.getLogger();

    public AimingToBecomeALegend(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.SERVER, Config.SERVER_SPEC);

        ModAttributes.ATTRIBUTES.register(modEventBus);
        modEventBus.addListener(this::onEntityAttributeModification);

        NeoForge.EVENT_BUS.register(this);
        LOGGER.info("Aiming to become a legend (NeoForge 1.21.1) initialized!");
    }

    private void onEntityAttributeModification(EntityAttributeModificationEvent event) {
        event.add(EntityType.PLAYER, ModAttributes.EXPERIENCE_MULTIPLIER);
        event.add(EntityType.PLAYER, ModAttributes.ENCHANT_DAMAGE_REDUCTION);
        event.add(EntityType.PLAYER, ModAttributes.LEGEND_DAMAGE_REDUCTION);
    }

    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
        LegendCommand.register(event.getDispatcher());
    }
}
