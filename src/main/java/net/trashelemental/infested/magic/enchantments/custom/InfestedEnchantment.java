package net.trashelemental.infested.magic.enchantments.custom;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.trashelemental.infested.item.ModItems;

public class InfestedEnchantment extends Enchantment {
    public InfestedEnchantment(EquipmentSlot... slots) {
        super(Enchantment.Rarity.UNCOMMON, EnchantmentCategory.ARMOR, slots);
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack itemstack) {
        return isApplicable(itemstack);
    }

    public boolean canApply(ItemStack stack) {
        return isApplicable(stack);
    }

    private boolean isApplicable(ItemStack stack) {
        return stack.is(Items.SHIELD) ||
                stack.is(Items.LEATHER_CHESTPLATE) ||
                stack.is(Items.CHAINMAIL_CHESTPLATE) ||
                stack.is(Items.IRON_CHESTPLATE) ||
                stack.is(Items.GOLDEN_CHESTPLATE) ||
                stack.is(Items.DIAMOND_CHESTPLATE) ||
                stack.is(Items.NETHERITE_CHESTPLATE) ||
                stack.is(ModItems.CHITIN_CHESTPLATE.get()) ||
                stack.is(ModItems.SPIDER_CHESTPLATE.get()) ||
                stack.is(ItemTags.create(new ResourceLocation("minecraft:chest_armor"))) ||
                stack.is(ItemTags.create(new ResourceLocation("forge:chest_armor"))) ||
                stack.is(ItemTags.create(new ResourceLocation("neoforge:chest_armor"))) ||
                stack.is(ItemTags.create(new ResourceLocation("forge:chestplates"))) ||
                stack.is(ItemTags.create(new ResourceLocation("neoforge:chestplates"))) ||
                stack.is(ItemTags.create(new ResourceLocation("forge:shields"))) ||
                stack.is(ItemTags.create(new ResourceLocation("neoforge:shields")));
    }
}

