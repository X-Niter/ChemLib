package com.smashingmods.chemlib.registry;

import com.smashingmods.chemlib.ChemLib;
import com.smashingmods.chemlib.api.ChemicalBlockType;
import com.smashingmods.chemlib.common.blocks.ChemicalBlock;
import com.smashingmods.chemlib.common.blocks.fire.ChemicalFireBlock;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class BlockRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ChemLib.MODID);
    public static final DeferredRegister<Block> FIRE_BLOCK = DeferredRegister.create(ForgeRegistries.BLOCKS, ChemLib.MODID);

    public static final List<ChemicalBlock> METAL_BLOCKS = new ArrayList<>();
    public static final List<ChemicalBlock> LAMP_BLOCKS = new ArrayList<>();
    public static final List<ChemicalFireBlock> FIRE_BLOCKS = new ArrayList<>();

    public static final BlockBehaviour.Properties FIRE_PROPERTIES = BlockBehaviour.Properties.of(Material.FIRE).noCollission().instabreak();
    public static final BlockBehaviour.Properties METAL_PROPERTIES = BlockBehaviour.Properties.of(Material.METAL).strength(5.0f, 12.0f).sound(SoundType.METAL);
    public static final BlockBehaviour.Properties LAMP_PROPERTIES = BlockBehaviour.Properties.of(Material.GLASS).strength(2.0f, 2.0f).sound(SoundType.GLASS).lightLevel(state -> state.getValue(BlockStateProperties.LIT) ? 15 : 0);

    public static void registerChemistFlame() {
        FIRE_BLOCK.register("chemist_fire_block", () -> new ChemicalFireBlock(new ResourceLocation(ChemLib.MODID, "chemist_fire_block"), ChemicalBlockType.ChemicalFire.FIRE, BlockRegistry.FIRE_BLOCKS, BlockRegistry.FIRE_PROPERTIES));
        getRegistryFireObjectByName("chemist_fire_block").ifPresent(fire -> ItemRegistry.fromChemicalFire(fire, new Item.Properties().tab(ItemRegistry.MISC_TAB)));
    }

    public static Optional<RegistryObject<Block>> getRegistryObjectByName(String pName) {
        return BLOCKS.getEntries().stream().filter(blockRegistryObject -> blockRegistryObject.getId().getPath().equals(pName)).findFirst();
    }

    public static Optional<RegistryObject<Block>> getRegistryFireObjectByName(String pName) {
        return FIRE_BLOCK.getEntries().stream().filter(blockRegistryObject -> blockRegistryObject.getId().getPath().equals(pName)).findFirst();
    }

    public static List<ChemicalBlock> getAllChemicalBlocks() {
        List<ChemicalBlock> all = new ArrayList<>();
        all.addAll(METAL_BLOCKS);
        all.addAll(LAMP_BLOCKS);
        return all;
    }

    public static List<ChemicalFireBlock> getAllChemicalFireBlocks() {
        List<ChemicalFireBlock> all = new ArrayList<>();
        all.addAll(FIRE_BLOCKS);
        return all;
    }

    public static List<ChemicalBlock> getChemicalBlocksByType(ChemicalBlockType pChemicalBlockType) {
        return switch (pChemicalBlockType) {
            case METAL -> METAL_BLOCKS;
            case LAMP -> LAMP_BLOCKS;
        };
    }

    public static List<ChemicalFireBlock> getChemicalFire(ChemicalBlockType.ChemicalFire pChemicalFire) {
        return switch (pChemicalFire) {
            case FIRE -> FIRE_BLOCKS;
        };
    }

    public static Stream<ChemicalFireBlock> getChemicalFiresStreamByType(ChemicalBlockType.ChemicalFire pChemicalFireType) {
        return getChemicalFire(pChemicalFireType)
                .stream().filter(fire -> fire.getFireType().equals(pChemicalFireType));
    }

    public static Stream<ChemicalBlock> getChemicalBlocksStreamByType(ChemicalBlockType pChemicalBlockType) {
        return getChemicalBlocksByType(pChemicalBlockType)
                .stream().filter(block -> block.getBlockType().equals(pChemicalBlockType));
    }

    public static Optional<ChemicalBlock> getChemicalBlockByNameAndType(String pName, ChemicalBlockType pChemicalBlockType) {
        return getChemicalBlocksStreamByType(pChemicalBlockType)
                .filter(block -> block.getChemical().getChemicalName().equals(pName))
                .findFirst();
    }

    public static void register(IEventBus eventBus) {
        registerChemistFlame();
        BLOCKS.register(eventBus);
        FIRE_BLOCK.register(eventBus);
    }
}
