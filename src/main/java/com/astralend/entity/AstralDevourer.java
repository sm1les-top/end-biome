package com.astralend.entity;

import com.astralend.ModEntities;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.BossEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Giant;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

/** Босс Края: Астральный Пожиратель (12 блоков ростом). */
public class AstralDevourer extends Giant {
    private final ServerBossEvent bossEvent = new ServerBossEvent(
            Component.translatable("entity.astral_end.astral_devourer"),
            BossEvent.BossBarColor.PURPLE,
            BossEvent.BossBarOverlay.PROGRESS);

    private int abilityTicks = 0;

    public AstralDevourer(EntityType<? extends Giant> type, Level level) {
        super(type, level);
        this.setPersistenceRequired();
    }

    public static AttributeSupplier.Builder createBossAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 400.0)
                .add(Attributes.ATTACK_DAMAGE, 14.0)
                .add(Attributes.MOVEMENT_SPEED, 0.32)
                .add(Attributes.FOLLOW_RANGE, 48.0)
                .add(Attributes.ARMOR, 8.0)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0, false));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 0.8));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 32.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    @Override
    public void startSeenByPlayer(ServerPlayer player) {
        super.startSeenByPlayer(player);
        this.bossEvent.addPlayer(player);
    }

    @Override
    public void stopSeenByPlayer(ServerPlayer player) {
        super.stopSeenByPlayer(player);
        this.bossEvent.removePlayer(player);
    }

    @Override
    protected void customServerAiStep(ServerLevel level) {
        super.customServerAiStep(level);
        this.bossEvent.setProgress(this.getHealth() / this.getMaxHealth());

        this.abilityTicks++;
        LivingEntity target = this.getTarget();
        if (target == null) {
            return;
        }

        boolean enraged = this.getHealth() < this.getMaxHealth() / 2.0F;

        // Астральный импульс: ослепление + слабость для всех игроков рядом
        if (this.abilityTicks % (enraged ? 100 : 160) == 0) {
            astralPulse(level);
        }
        // Призыв паразитов
        if (this.abilityTicks % (enraged ? 200 : 400) == 0) {
            summonParasites(level, enraged ? 4 : 2);
        }
    }

    private void astralPulse(ServerLevel level) {
        level.playSound(null, this.blockPosition(), SoundEvents.ENDER_DRAGON_GROWL, SoundSource.HOSTILE, 3.0F, 0.7F);
        level.sendParticles(ParticleTypes.REVERSE_PORTAL, this.getX(), this.getY() + 2.0, this.getZ(),
                200, 6.0, 4.0, 6.0, 0.3);
        for (Player player : level.getEntitiesOfClass(Player.class, this.getBoundingBox().inflate(14.0))) {
            player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 60, 0));
            player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 200, 0));
        }
    }

    private void summonParasites(ServerLevel level, int count) {
        for (int i = 0; i < count; i++) {
            AstralParasite parasite = ModEntities.ASTRAL_PARASITE.create(level, EntitySpawnReason.MOB_SUMMONED);
            if (parasite != null) {
                parasite.setPos(
                        this.getX() + (this.random.nextDouble() - 0.5) * 6.0,
                        this.getY(),
                        this.getZ() + (this.random.nextDouble() - 0.5) * 6.0);
                level.addFreshEntity(parasite);
            }
        }
    }
}
