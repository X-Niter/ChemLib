package com.smashingmods.chemlib.datagen;

import com.smashingmods.chemlib.ChemLib;
import com.smashingmods.chemlib.registry.BlockRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeRegistryTagsProvider;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockTagGenerator extends ForgeRegistryTagsProvider<Block> {

    public BlockTagGenerator(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, ForgeRegistries.BLOCKS, ChemLib.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        BlockRegistry.BLOCKS.getEntries().forEach(blockRegistryObject -> {
            tag(BlockTags.MINEABLE_WITH_PICKAXE).add(blockRegistryObject.get());
            tag(BlockTags.NEEDS_STONE_TOOL).add(blockRegistryObject.get());
        });
        BlockRegistry.FIRE_BLOCK.getEntries().forEach(blockRegistryObject -> {
            tag(BlockTags.FIRE).add(blockRegistryObject.get());
        });
    }
}
