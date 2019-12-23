/*
 * Copyright (c) 2019 RedGalaxy
 * All rights reserved. Do not distribute.
 *
 * Date:   12 - 23 - 2019
 * Author: rgsw
 */

package modernity.common.particle;

import com.google.common.reflect.TypeToken;
import modernity.client.particle.*;
import modernity.common.registry.RegistryEventHandler;
import modernity.common.registry.RegistryHandler;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * Holder class for Modernity particle types.
 */
public final class MDParticleTypes {
    private static final RegistryHandler<ParticleType<?>> ENTRIES = new RegistryHandler<>( "modernity" );

    public static final BasicParticleType SALT = register( "salt", new BasicParticleType( false ) );
    public static final BasicParticleType HUMUS = register( "humus", new BasicParticleType( false ) );
    public static final ParticleType<RgbParticleData> FALLING_LEAF = register( "falling_leaf", new ParticleType<>( false, RgbParticleData.DESERIALIZER ) );
    public static final BasicParticleType RAIN = register( "rain", new BasicParticleType( false ) );
    public static final BasicParticleType HAIL = register( "hail", new BasicParticleType( false ) );
    public static final ParticleType<SoulLightParticleData> SOUL_LIGHT = register( "soul_light", new ParticleType<>( true, SoulLightParticleData.DESERIALIZER ) );
    public static final ParticleType<SoulLightParticleData> SOUL_LIGHT_CLOUD = register( "soul_light_cloud", new ParticleType<>( true, SoulLightParticleData.DESERIALIZER ) );

    private static <T extends ParticleType<?>> T register( String id, T type, String... aliases ) {
        return ENTRIES.register( id, type, aliases );
    }

    @SuppressWarnings( "unchecked" )
    public static void setup( RegistryEventHandler handler ) {
        TypeToken<ParticleType<?>> token = new TypeToken<ParticleType<?>>() {
        };
        handler.addHandler( (Class<ParticleType<?>>) token.getRawType(), ENTRIES );
    }

    @OnlyIn( Dist.CLIENT )
    public static void setupFactories( ParticleManager manager ) {
        manager.registerFactory( SALT, SaltParticle.Factory::new );
        manager.registerFactory( HUMUS, LeafParticle.HumusFactory::new );
        manager.registerFactory( FALLING_LEAF, LeafParticle.FallingLeafFactory::new );
        manager.registerFactory( RAIN, PrecipitationParticle.DripFactory::new );
        manager.registerFactory( HAIL, PrecipitationParticle.HailFactory::new );
        manager.registerFactory( SOUL_LIGHT, new SoulLightParticle.BaseFactory() );
        manager.registerFactory( SOUL_LIGHT_CLOUD, new SoulLightParticle.CloudFactory() );
    }

    private MDParticleTypes() {
    }
}
