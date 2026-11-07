package com.github.yutak.aiming_to_become_a_legend;

import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.slf4j.Logger;

@Mod(AimingToBecomeALegend.MODID)
public class AimingToBecomeALegend {
    public static final String MODID = "aiming_to_become_a_legend";
    public static final Logger LOGGER = LogUtils.getLogger();

    public AimingToBecomeALegend() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.SERVER_SPEC);
        MinecraftForge.EVENT_BUS.register(this);
        LOGGER.info("Aiming to become a legend initialized!");
    }

    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
        LegendCommand.register(event.getDispatcher());
    }
}
