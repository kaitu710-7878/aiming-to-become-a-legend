package com.github.yutak.aiming_to_become_a_legend;

import com.mojang.logging.LogUtils;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(AimingToBecomeALegend.MODID)
public class AimingToBecomeALegend {
    public static final String MODID = "aiming_to_become_a_legend";
    public static final Logger LOGGER = LogUtils.getLogger();

    public AimingToBecomeALegend() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.SERVER_SPEC);

        ModAttributes.ATTRIBUTES.register(modEventBus);
        modEventBus.addListener(this::onEntityAttributeModification);

        MinecraftForge.EVENT_BUS.register(this);
        LOGGER.info("Aiming to become a legend initialized!");
    }

    private void onEntityAttributeModification(EntityAttributeModificationEvent event) {
        event.add(EntityType.PLAYER, ModAttributes.EXPERIENCE_MULTIPLIER.get());
        event.add(EntityType.PLAYER, ModAttributes.ENCHANT_DAMAGE_REDUCTION.get());
        event.add(EntityType.PLAYER, ModAttributes.LEGEND_DAMAGE_REDUCTION.get());
    }

    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
        LegendCommand.register(event.getDispatcher());
    }
}
