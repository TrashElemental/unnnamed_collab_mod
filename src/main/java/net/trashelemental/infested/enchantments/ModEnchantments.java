package net.trashelemental.infested.enchantments;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.trashelemental.infested.infested;

public class ModEnchantments {
    public static final DeferredRegister<Enchantment> REGISTRY = DeferredRegister.create(Registries.ENCHANTMENT, infested.MOD_ID);

    public static final RegistryObject<Enchantment> INFESTED = REGISTRY.register("infested", InfestedEnchantment::new);
    public static final RegistryObject<Enchantment> PARASITOID = REGISTRY.register("parasitoid", ParasitoidEnchantment::new);
    public static final RegistryObject<Enchantment> ENSNARING_STRIKE = REGISTRY.register("ensnaring_strike", EnsnaringStrikeEnchantment::new);


    public static void register(IEventBus eventBus) {
        REGISTRY.register(eventBus);
    }
}
