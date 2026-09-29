package com.astralend;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.renderer.entity.EndermiteRenderer;
import net.minecraft.client.renderer.entity.GiantMobRenderer;
import net.minecraft.client.renderer.entity.ZombieRenderer;

public class AstralEndClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Пока используем ванильные модели (Эндермит / Зомби / Гигант).
        EntityRendererRegistry.register(ModEntities.ASTRAL_PARASITE, ctx -> new EndermiteRenderer(ctx));
        EntityRendererRegistry.register(ModEntities.CRYSTAL_HUSK, ctx -> new ZombieRenderer(ctx));
        EntityRendererRegistry.register(ModEntities.ASTRAL_DEVOURER, ctx -> new GiantMobRenderer(ctx, 6.0F));
    }
}
