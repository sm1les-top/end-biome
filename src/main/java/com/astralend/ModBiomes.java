package com.astralend;

import net.fabricmc.fabric.api.biome.v1.TheEndBiomes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

public class ModBiomes {
    public static final ResourceKey<Biome> ASTRAL_INFECTION = key("astral_infection");
    public static final ResourceKey<Biome> CRYSTAL_FIELDS = key("crystal_fields");
    public static final ResourceKey<Biome> VOID_MEADOW = key("void_meadow");

    private static ResourceKey<Biome> key(String name) {
        return ResourceKey.create(Registries.BIOME, AstralEnd.id(name));
    }

    public static void init() {
        // Астральная Инфекция: острова-возвышенности (и их склоны)
        TheEndBiomes.addHighlandsBiome(ASTRAL_INFECTION, 1.0);
        TheEndBiomes.addMidlandsBiome(ASTRAL_INFECTION, ASTRAL_INFECTION, 1.0);
        TheEndBiomes.addBarrensBiome(ASTRAL_INFECTION, ASTRAL_INFECTION, 1.0);

        // Кристальные поля: средние земли внутри ванильных возвышенностей
        TheEndBiomes.addMidlandsBiome(Biomes.END_HIGHLANDS, CRYSTAL_FIELDS, 1.0);

        // Пустотный луг: маленькие острова во внешнем Крае
        TheEndBiomes.addSmallIslandsBiome(VOID_MEADOW, 1.0);
    }
}
