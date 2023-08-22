package com.smashingmods.chemlib.common.events;

import com.mojang.datafixers.util.Either;
import com.smashingmods.chemlib.ChemLib;
import com.smashingmods.chemlib.api.ChemicalItemType;
import com.smashingmods.chemlib.api.utility.FluidEffectsTooltipUtility;
import com.smashingmods.chemlib.common.items.ChemicalItem;
import com.smashingmods.chemlib.registry.BlockRegistry;
import com.smashingmods.chemlib.registry.ChemicalRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.SoulFireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.event.entity.EntityLeaveLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;
import java.util.function.Function;

@Mod.EventBusSubscriber(modid = ChemLib.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEventHandler {
    @SubscribeEvent
    public static void onEntityLeaveLevel(EntityLeaveLevelEvent event) {
        if (event.getEntity() instanceof ItemEntity pItemEntity) {
            BlockPos fBlockPos = pItemEntity.getOnPos();
            Level fLevel = event.getLevel();
            Block fBlock = fLevel.getBlockState(fBlockPos).getBlock();
            BlockState fBlockState = fBlock.defaultBlockState();

            if (fBlock.equals(Blocks.FIRE)) {
                if (pItemEntity.getItem().getItem() instanceof ChemicalItem pChemicalItem) {
                    // bucket.getFluid()).get().location().getNamespace().equals(ChemLib.MODID)
                   // BlockState fNewBlockState = BlockRegistry.getRegistryFireObjectByName(pChemicalItem.getChemicalName()).get().get().defaultBlockState();
                    //BlockState fNewBlockState = Objects.requireNonNull(ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryParse(ChemLib.MODID + pChemicalItem.getChemical().getChemicalName()))).defaultBlockState();
                    ChemLib.LOGGER.info("Chemical Name" + pChemicalItem.getChemical().getChemicalName());
                    ChemLib.LOGGER.info("Chemical" + pChemicalItem.getChemical());

                    //fLevel.setBlockAndUpdate(fBlockPos, fNewBlockState);
                }
            }
        }
    }
}
