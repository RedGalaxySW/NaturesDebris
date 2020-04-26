/*
 * Copyright (c) 2020 RedGalaxy
 * All rights reserved. Do not distribute.
 *
 * Date:   02 - 29 - 2020
 * Author: rgsw
 */

package modernity.common.block.plant;

import modernity.common.block.MDBlockStateProperties;
import modernity.common.block.plant.growing.FertileCropGrowLogic;
import net.minecraft.block.BlockState;
import net.minecraft.state.IntegerProperty;
import net.minecraft.util.math.shapes.VoxelShape;

public class MurkRootsCropBlock extends CropBlock {
    private static final int[] HEIGHTS = {
        2, 2, 6, 6, 8, 8, 11, 11
    };

    private static final VoxelShape[] SHAPES = new VoxelShape[ 8 ];

    static {
        for( int i = 0; i < 8; i++ ) {
            SHAPES[ i ] = makePlantShape( 14, HEIGHTS[ i ] );
        }
    }

    public MurkRootsCropBlock( Properties properties ) {
        super( properties );
        setGrowLogic( new FertileCropGrowLogic( this ) );
    }

    @Override
    protected IntegerProperty getAgeProperty() {
        return MDBlockStateProperties.AGE_1_8;
    }

    @Override
    public OffsetType getOffsetType() {
        return super.getOffsetType();
    }

    @Override
    public VoxelShape getShape( BlockState state ) {
        return SHAPES[ state.get( age ) - 1 ];
    }
}
