package com.smashingmods.chemlib.api;

import net.minecraft.util.StringRepresentable;

import javax.annotation.Nonnull;

public enum ChemicalBlockType implements StringRepresentable {
    METAL("metal"),
    LAMP("lamp");

    private final String type;

    ChemicalBlockType(String pType) {
        this.type = pType;
    }

    @Override
    @Nonnull
    public String getSerializedName() {
        return type;
    }

    public enum ChemicalFire implements StringRepresentable{
        FIRE("fire");

        private final String type;

        ChemicalFire(String pType) {
            this.type = pType;
        }

        @Override
        @Nonnull
        public String getSerializedName() {
            return type;
        }
    }
}
