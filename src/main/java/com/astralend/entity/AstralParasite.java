package com.astralend.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Endermite;
import net.minecraft.world.level.Level;

/** Мелкий быстрый астральный паразит. */
public class AstralParasite extends Endermite {
    public AstralParasite(EntityType<? extends Endermite> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAstralAttributes() {
        return Endermite.createAttributes()
                .add(Attributes.MAX_HEALTH, 14.0)
                .add(Attributes.ATTACK_DAMAGE, 4.0)
                .add(Attributes.MOVEMENT_SPEED, 0.35);
    }
}
