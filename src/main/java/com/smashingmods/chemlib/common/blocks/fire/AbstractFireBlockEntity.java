package com.smashingmods.chemlib.common.blocks.fire;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiFunction;

public abstract class AbstractFireBlockEntity extends BaseEntityBlock {
    private static final int SECONDS_ON_FIRE = 8;
    private final float fireDamage;
    private final BiFunction<BlockPos, BlockState, BlockEntity> blockEntityFunction;
    protected static final float AABB_OFFSET = 1.0F;
    protected static final VoxelShape DOWN_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1.0D, 16.0D);

    protected AbstractFireBlockEntity(BiFunction<BlockPos, BlockState, BlockEntity> pBlockEntity, float pFireDamage) {
        super(Properties.of(Material.FIRE).noCollission().instabreak().sound(SoundType.WOOL));
        this.blockEntityFunction = pBlockEntity;
        this.fireDamage = pFireDamage;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return getState(pContext.getLevel(), pContext.getClickedPos());
    }

    public static BlockState getState(BlockGetter pReader, BlockPos pPos) {
        BlockPos blockpos = pPos.below();
        BlockState blockstate = pReader.getBlockState(blockpos);
        return SoulFireBlock.canSurviveOnBlock(blockstate) ? Blocks.SOUL_FIRE.defaultBlockState() : ((FireBlock)Blocks.FIRE).getStateForPlacement(pReader, pPos);
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState pState, @NotNull BlockGetter pLevel, @NotNull BlockPos pPos, @NotNull CollisionContext pContext) {
        return DOWN_AABB;
    }

    @Override
    public void animateTick(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, RandomSource pRandom) {
        if (pRandom.nextInt(24) == 0) {
            pLevel.playLocalSound((double)pPos.getX() + 0.5D, (double)pPos.getY() + 0.5D, (double)pPos.getZ() + 0.5D, SoundEvents.FIRE_AMBIENT, SoundSource.BLOCKS, 1.0F + pRandom.nextFloat(), pRandom.nextFloat() * 0.7F + 0.3F, false);
        }

        BlockPos blockpos = pPos.below();
        BlockState blockstate = pLevel.getBlockState(blockpos);
        if (!this.canBurn(blockstate) && !blockstate.isFaceSturdy(pLevel, blockpos, Direction.UP)) {
            if (this.canBurn(pLevel.getBlockState(pPos.west()))) {
                for(int j = 0; j < 2; ++j) {
                    double d3 = (double)pPos.getX() + pRandom.nextDouble() * (double)0.1F;
                    double d8 = (double)pPos.getY() + pRandom.nextDouble();
                    double d13 = (double)pPos.getZ() + pRandom.nextDouble();
                    pLevel.addParticle(ParticleTypes.LARGE_SMOKE, d3, d8, d13, 0.0D, 0.0D, 0.0D);
                }
            }

            if (this.canBurn(pLevel.getBlockState(pPos.east()))) {
                for(int k = 0; k < 2; ++k) {
                    double d4 = (double)(pPos.getX() + 1) - pRandom.nextDouble() * (double)0.1F;
                    double d9 = (double)pPos.getY() + pRandom.nextDouble();
                    double d14 = (double)pPos.getZ() + pRandom.nextDouble();
                    pLevel.addParticle(ParticleTypes.LARGE_SMOKE, d4, d9, d14, 0.0D, 0.0D, 0.0D);
                }
            }

            if (this.canBurn(pLevel.getBlockState(pPos.north()))) {
                for(int l = 0; l < 2; ++l) {
                    double d5 = (double)pPos.getX() + pRandom.nextDouble();
                    double d10 = (double)pPos.getY() + pRandom.nextDouble();
                    double d15 = (double)pPos.getZ() + pRandom.nextDouble() * (double)0.1F;
                    pLevel.addParticle(ParticleTypes.LARGE_SMOKE, d5, d10, d15, 0.0D, 0.0D, 0.0D);
                }
            }

            if (this.canBurn(pLevel.getBlockState(pPos.south()))) {
                for(int i1 = 0; i1 < 2; ++i1) {
                    double d6 = (double)pPos.getX() + pRandom.nextDouble();
                    double d11 = (double)pPos.getY() + pRandom.nextDouble();
                    double d16 = (double)(pPos.getZ() + 1) - pRandom.nextDouble() * (double)0.1F;
                    pLevel.addParticle(ParticleTypes.LARGE_SMOKE, d6, d11, d16, 0.0D, 0.0D, 0.0D);
                }
            }

            if (this.canBurn(pLevel.getBlockState(pPos.above()))) {
                for(int j1 = 0; j1 < 2; ++j1) {
                    double d7 = (double)pPos.getX() + pRandom.nextDouble();
                    double d12 = (double)(pPos.getY() + 1) - pRandom.nextDouble() * (double)0.1F;
                    double d17 = (double)pPos.getZ() + pRandom.nextDouble();
                    pLevel.addParticle(ParticleTypes.LARGE_SMOKE, d7, d12, d17, 0.0D, 0.0D, 0.0D);
                }
            }
        } else {
            for(int i = 0; i < 3; ++i) {
                double d0 = (double)pPos.getX() + pRandom.nextDouble();
                double d1 = (double)pPos.getY() + pRandom.nextDouble() * 0.5D + 0.5D;
                double d2 = (double)pPos.getZ() + pRandom.nextDouble();
                pLevel.addParticle(ParticleTypes.LARGE_SMOKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
            }
        }

    }

    protected abstract boolean canBurn(BlockState pState);

    @Override
    public void entityInside(@NotNull BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, Entity pEntity) {
        if (!pEntity.fireImmune()) {
            pEntity.setRemainingFireTicks(pEntity.getRemainingFireTicks() + 1);
            if (pEntity.getRemainingFireTicks() == 0) {
                pEntity.setSecondsOnFire(8);
            }
        }

        pEntity.hurt(DamageSource.IN_FIRE, this.fireDamage);
        super.entityInside(pState, pLevel, pPos, pEntity);
    }

    @Override
    public void onPlace(BlockState pState, @NotNull Level pLevel, @NotNull BlockPos pPos, BlockState pOldState, boolean pIsMoving) {
        if (!pOldState.is(pState.getBlock())) {
            if (!pState.canSurvive(pLevel, pPos)) {
                pLevel.removeBlock(pPos, false);
            }
        }
    }

    @Override
    protected void spawnDestroyParticles(@NotNull Level pLevel, @NotNull Player pPlayer, @NotNull BlockPos pPos, @NotNull BlockState pState) {
    }

    @Override
    public void playerWillDestroy(Level pLevel, @NotNull BlockPos pPos, @NotNull BlockState pState, @NotNull Player pPlayer) {
        if (!pLevel.isClientSide()) {
            pLevel.levelEvent((Player)null, 1009, pPos, 0);
        }

        super.playerWillDestroy(pLevel, pPos, pState, pPlayer);
    }

    public static boolean canBePlacedAt(Level pLevel, BlockPos pPos) {
        BlockState blockstate = pLevel.getBlockState(pPos);
        if (!blockstate.isAir()) {
            return false;
        } else {
            return getState(pLevel, pPos).canSurvive(pLevel, pPos);
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pPos, @NotNull BlockState pState) {
        return null;
    }
}
