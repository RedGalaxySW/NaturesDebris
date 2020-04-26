/*
 * Copyright (c) 2020 RedGalaxy
 * All rights reserved. Do not distribute.
 *
 * Date:   03 - 04 - 2020
 * Author: rgsw
 */

package modernity.common.generator.region.layer;

import modernity.common.generator.region.IRegion;
import modernity.common.generator.region.IRegionRNG;

public class VoronoiZoomLayer implements ITransformerLayer {
    public static final VoronoiZoomLayer INSTANCE = new VoronoiZoomLayer();

    protected VoronoiZoomLayer() {
    }

    @Override
    public int generate( IRegionRNG rng, IRegion region, int x, int z ) {
        int ox = x - 2;
        int oz = z - 2;

        int lx = ox >> 2 << 2;
        int lz = oz >> 2 << 2;

        int ux = lx + 4;
        int uz = lz + 4;

        rng.setPosition( lx, lz );
        double point00X = randomPointCoord( rng );
        double point00Z = randomPointCoord( rng );

        rng.setPosition( ux, lz );
        double point10X = randomPointCoord( rng ) + 4;
        double point10Z = randomPointCoord( rng );

        rng.setPosition( lx, uz );
        double point01X = randomPointCoord( rng );
        double point01Z = randomPointCoord( rng ) + 4;

        rng.setPosition( ux, uz );
        double point11X = randomPointCoord( rng ) + 4;
        double point11Z = randomPointCoord( rng ) + 4;

        int sx = ox & 3;
        int sz = oz & 3;

        double dist00 = distSq( sx, sz, point00X, point00Z );
        double dist10 = distSq( sx, sz, point10X, point10Z );
        double dist01 = distSq( sx, sz, point01X, point01Z );
        double dist11 = distSq( sx, sz, point11X, point11Z );

        if( dist00 < dist01 && dist00 < dist10 && dist00 < dist11 ) {
            // Closest to 00
            return region.getValue( lx >> 2, lz >> 2 );
        } else if( dist01 < dist10 && dist01 < dist11 ) {
            // Closest to 01
            return region.getValue( lx >> 2, uz >> 2 );
        } else if( dist10 < dist11 ) {
            // Closest to 10
            return region.getValue( ux >> 2, lz >> 2 );
        } else {
            // Closest to 11
            return region.getValue( ux >> 2, uz >> 2 );
        }
    }

    private static double distSq( double x, double z, double px, double pz ) {
        double rx = x - px;
        double rz = z - pz;
        return rx * rx + rz * rz;
    }

    private static double randomPointCoord( IRegionRNG rng ) {
        return ( rng.random( 1024 ) / 1024D - 0.5 ) * 3.6;
    }
}
