package modernity.common.environment.satellite;

import modernity.common.Modernity;
import modernity.common.net.SSatellitePacket;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.util.SharedConstants;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldSavedData;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Holds, updates and stores data about the satellite (moon).
 */
public class SatelliteData extends WorldSavedData {
    public static final String NAME = "md/satellite";

    private int phase;
    private int tick;
    private int updateTicks;
    private final int updateInterval;
    private final World world;

    /**
     * Creates a satellite data instance.
     *
     * @param updateInterval The amount of ticks before sending to the client. When -1, no packets are sent.
     * @param world          The world the satellite is in orbit around.
     */
    public SatelliteData( int updateInterval, World world ) {
        super( NAME );
        this.updateInterval = updateInterval;
        this.world = world;
    }

    /**
     * Returns the current phase of the satellite.
     */
    public int getPhase() {
        return phase;
    }

    /**
     * Returns the current angle tick of the satellite.
     */
    public int getTick() {
        return tick;
    }

    /**
     * Returns the amount of ticks before sending to the client. When -1, no packets are sent.
     */
    public int getUpdateInterval() {
        return updateInterval;
    }

    /**
     * Sets the phase of the satellite.
     *
     * @return Current instance for convenience.
     */
    public SatelliteData setPhase( int phase ) {
        this.phase = phase & 7;
        markDirty();
        return this;
    }

    /**
     * Sets the angle tick of the satellite.
     *
     * @return Current instance for convenience.
     */
    public SatelliteData setTick( int tick ) {
        this.tick = tick % 24000 + ( tick < 0 ? 24000 : 0 );
        markDirty();
        return this;
    }

    /**
     * Sets the phase and angle tick of the satellite.
     *
     * @return Current instance for convenience.
     */
    public SatelliteData set( int phase, int tick ) {
        return setPhase( phase ).setTick( tick );
    }

    /**
     * Ticks the satellite, updating its phase and angle. Each {@linkplain #getUpdateInterval() update interval}, a
     * packet is sent to the client.
     */
    public void tick() {
        updateTicks++;

        int nextTick = tick + 1;

        setTick( nextTick );

        if( nextTick >= 24000 ) {
            setPhase( phase + 1 );
        }

        if( updateInterval > 0 && updateTicks >= updateInterval ) {
            updateTicks = 0;
            update();
        }
    }

    /**
     * Sends a {@link SSatellitePacket} to the client.
     */
    public void update() {
        if( world.isRemote ) return; // Don't send packet on client

        Modernity.network().sendToDimen( new SSatellitePacket( tick, phase ), world.dimension.getType() );
    }

    @Override
    public void read( CompoundNBT nbt ) {
        int mixed = nbt.getInt( "tick" );
        tick = mixed >>> 3;
        phase = mixed & 7;
    }

    @Override
    public CompoundNBT write( CompoundNBT nbt ) {
        nbt.putInt( "tick", tick << 3 | phase );
        return nbt;
    }

    @Override
    public void save( File file ) {
        if( ! file.exists() ) {
            file.getParentFile().mkdirs();
        }
        super.save( file );
    }
}
