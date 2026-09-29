package com.astralend;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class ModBlocks {
    public static final Block ASTRAL_STONE = register("astral_stone", 3.0f, 0);
    public static final Block CRYSTAL_END_STONE = register("crystal_end_stone", 3.0f, 10);
    public static final Block VOID_MOSS = register("void_moss", 2.0f, 4);

    private static Block register(String name, float strength, int light) {
        Identifier id = AstralEnd.id(name);
        ResourceKey<Block> blockKey = ResourceKey.create(Registries.BLOCK, id);
        Block block = new Block(BlockBehaviour.Properties.of()
                .setId(blockKey)
                .strength(strength, 9.0f)
                .sound(SoundType.STONE)
                .lightLevel(state -> light));
        Registry.register(BuiltInRegistries.BLOCK, blockKey, block);

        ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, id);
        BlockItem item = new BlockItem(block, new Item.Properties().setId(itemKey).useBlockDescriptionPrefix());
        Registry.register(BuiltInRegistries.ITEM, itemKey, item);

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.BUILDING_BLOCKS)
                .register(entries -> entries.accept(item));
        return block;
    }

    public static void init() {
    }
}
