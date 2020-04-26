/*
 * Copyright (c) 2020 RedGalaxy
 * All rights reserved. Do not distribute.
 *
 * Date:   02 - 29 - 2020
 * Author: rgsw
 */

package modernity.common.block.plant;

import modernity.common.block.plant.growing.FertilityGrowLogic;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IWorldReader;

public class RedwoldBlock extends SimplePlantBlock {
    public static final VoxelShape REDWOLD_SHAPE = makePlantShape( 16, 1 );

    public RedwoldBlock( Properties properties ) {
        super( properties, REDWOLD_SHAPE );
        setGrowLogic( new FertilityGrowLogic( this ) );
    }

    @Override
    public boolean canBlockSustain( IWorldReader world, BlockPos pos, BlockState state ) {
        return isBlockSideSustainable( state, world, pos, Direction.UP );
    }

    @Override
    public OffsetType getOffsetType() {
        return OffsetType.NONE;
    }

    @Override
    public boolean isReplaceable( BlockState state, BlockItemUseContext ctx ) {
        return true;
    }
}
