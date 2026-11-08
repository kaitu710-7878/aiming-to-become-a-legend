package dev.legend.aiming_to_become_a_legend;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModAttributes {
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, AimingToBecomeALegend.MODID);

    public static final RegistryObject<Attribute> EXPERIENCE_MULTIPLIER = ATTRIBUTES.register(
            "experience_multiplier",
            () -> new RangedAttribute("attribute.name.aiming_to_become_a_legend.experience_multiplier", 0.0, 0.0, 1000.0).setSyncable(true)
    );

    public static final RegistryObject<Attribute> ENCHANT_DAMAGE_REDUCTION = ATTRIBUTES.register(
            "enchant_damage_reduction",
            () -> new RangedAttribute("attribute.name.aiming_to_become_a_legend.enchant_damage_reduction", 0.0, 0.0, 0.80).setSyncable(true)
    );

    public static final RegistryObject<Attribute> LEGEND_DAMAGE_REDUCTION = ATTRIBUTES.register(
            "legend_damage_reduction",
            () -> new RangedAttribute("attribute.name.aiming_to_become_a_legend.legend_damage_reduction", 0.0, 0.0, 0.80).setSyncable(true)
    );
}
