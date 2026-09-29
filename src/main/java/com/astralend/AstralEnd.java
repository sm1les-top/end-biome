package com.astralend;

import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.Identifier;

public class AstralEnd implements ModInitializer {
    public static final String MOD_ID = "astral_end";

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }

    @Override
    public void onInitialize() {
        ModBlocks.init();
        ModEntities.init();
        ModBiomes.init();
    }
}
