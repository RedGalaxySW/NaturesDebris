/*
 * Copyright (c) 2019 RedGalaxy & contributors
 * Licensed under the Apache Licence v2.0.
 * Do not redistribute.
 *
 * By  : RGSW
 * Date: 9 - 2 - 2019
 */

package modernity.common.world.gen.surface;

import modernity.api.util.EcoBlockPos;
import modernity.common.biome.BiomeBase;
import modernity.common.block.MDBlocks;
import modernity.common.world.gen.ModernityGenSettings;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.chunk.IChunk;
import net.rgsw.noise.FractalOpenSimplex3D;

import java.util.Random;

public class GrassSurfaceGenerator implements ISurfaceGenerator<ModernityGenSettings> {

    private static final IBlockState GRASS = MDBlocks.DARK_GRASS.getDefaultState();
    private static final IBlockState DIRT = MDBlocks.DARK_DIRT.getDefaultState();
    private static final IBlockState MUD = MDBlocks.MUD.getDefaultState();

    @Override
    public void generateSurface( IChunk chunk, int cx, int cz, int x, int z, Random rand, BiomeBase biome, FractalOpenSimplex3D surfaceNoise, EcoBlockPos rpos, ModernityGenSettings settings ) {
        int ctrl = 0;
        IBlockState secondLayers = null;
        for( int y = 255; y >= 0; y-- ) {
            rpos.setPos( x, y, z );
            if( ctrl >= 0 && ! chunk.getBlockState( rpos ).getMaterial().blocksMovement() ) {
                ctrl = - 1;
            } else if( ctrl == - 1 && chunk.getBlockState( rpos ).getMaterial().blocksMovement() ) {
                ctrl = (int) ( 3 + 2 * surfaceNoise.generate( x + cx * 16, y, z + cz * 16 ) );
                boolean underwater = y < settings.getWaterLevel() - 1;
                chunk.setBlockState( rpos, underwater ? MUD : GRASS, false );
                secondLayers = underwater ? MUD : DIRT;
            } else if( ctrl > 0 ) {
                ctrl--;
                chunk.setBlockState( rpos, secondLayers, false );
            }
        }
    }
}
