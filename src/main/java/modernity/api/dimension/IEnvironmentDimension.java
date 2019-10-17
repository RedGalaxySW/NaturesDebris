package modernity.api.dimension;

import modernity.client.environment.Fog;
import modernity.client.environment.Sky;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * Dimensions implementing this interface receive events when they need to update specific environment factors.
 */
public interface IEnvironmentDimension {
    @OnlyIn( Dist.CLIENT )
    void updateFog( Fog fog );
    void updateSky( Sky sky );
}
