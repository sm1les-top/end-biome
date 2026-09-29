package com.astralend;

import com.astralend.entity.AstralDevourer;
import com.astralend.entity.AstralParasite;
import com.astralend.entity.CrystalHusk;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;

public class ModEntities {
    public static final EntityType<AstralParasite> ASTRAL_PARASITE = register("astral_parasite",
            EntityType.Builder.of(AstralParasite::new, MobCategory.MONSTER)
                    .sized(0.5f, 0.35f).clientTrackingRange(8));

    public static final EntityType<CrystalHusk> CRYSTAL_HUSK = register("crystal_husk",
            EntityType.Builder.of(CrystalHusk::new, MobCategory.MONSTER)
                    .sized(0.6f, 1.95f).clientTrackingRange(8));

    public static final EntityType<AstralDevourer> ASTRAL_DEVOURER = register("astral_devourer",
            EntityType.Builder.of(AstralDevourer::new, MobCategory.MONSTER)
                    .sized(3.6f, 12.0f).clientTrackingRange(16).fireImmune());

    private static <T extends Entity> EntityType<T> register(String name, EntityType.Builder<T> builder) {
        ResourceKey<EntityType<?>> key = ResourceKey.create(Registries.ENTITY_TYPE, AstralEnd.id(name));
        return Registry.register(BuiltInRegistries.ENTITY_TYPE, key, builder.build(key));
    }

    public static void init() {
        FabricDefaultAttributeRegistry.register(ASTRAL_PARASITE, AstralParasite.createAstralAttributes());
        FabricDefaultAttributeRegistry.register(CRYSTAL_HUSK, CrystalHusk.createCrystalAttributes());
        FabricDefaultAttributeRegistry.register(ASTRAL_DEVOURER, AstralDevourer.createBossAttributes());

        SpawnPlacements.register(ASTRAL_PARASITE, SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
        SpawnPlacements.register(CRYSTAL_HUSK, SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules);
    }
}
