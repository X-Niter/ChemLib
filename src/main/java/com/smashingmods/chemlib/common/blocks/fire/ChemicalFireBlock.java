package com.smashingmods.chemlib.common.blocks.fire;

import com.smashingmods.chemlib.api.Chemical;
import com.smashingmods.chemlib.api.ChemicalBlockType;
import com.smashingmods.chemlib.api.MatterState;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;

public class ChemicalFireBlock extends AbstractFireBlock implements Chemical, {

    private final ResourceLocation chemical;

    private final ChemicalBlockType.ChemicalFire fireType;

    protected ChemicalFireBlock(BiFunction<BlockPos, BlockState, BlockEntity> pBlockEntity, float pFireDamage) {
        super(pBlockEntity, pFireDamage);
    }

    public ChemicalFireBlock(ResourceLocation pChemical, ChemicalBlockType.ChemicalFire pFireType, List<ChemicalFireBlock> pList, Properties pProperties) {
        super(pProperties);
        this.chemical = pChemical;
        this.fireType = pFireType;
        pList.add(this);
    }

    public Chemical getChemical() {
        return (Chemical) Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(chemical));
    }

    public ChemicalBlockType.ChemicalFire getFireType() {
        return fireType;
    }

    @Override
    public @NotNull String getChemicalName() {
        return getChemical().getChemicalName();
    }

    @Override
    public @NotNull String getAbbreviation() {
        return getChemical().getAbbreviation();
    }

    @Override
    public @NotNull MatterState getMatterState() {
        return MatterState.SOLID;
    }

    @Override
    public @NotNull String getChemicalDescription() {
        return "";
    }

    @Override
    public @NotNull List<MobEffectInstance> getEffects() {
        return getChemical().getEffects();
    }

    @Override
    public int getColor() {
        return clampMinColorValue(getChemical().getColor(), 0x44);
    }

    public BlockColor getBlockColor(ItemStack pItemStack, int pTintIndex) {
        return (pState, pLevel, pPos, pTintIndex1) -> getChemical().getColor();
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return new ChemicalFireBlockEntity(this, pPos, pState);
    }
}
