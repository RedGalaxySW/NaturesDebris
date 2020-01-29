/*
 * Copyright (c) 2020 RedGalaxy
 * All rights reserved. Do not distribute.
 *
 * Date:   01 - 29 - 2020
 * Author: rgsw
 */

package modernity.common.block.plant;

import modernity.common.block.MDBlockTags;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;

import java.util.Random;

public class FlyFlowerStalkBlock extends PlantBlock implements IWaterPlant {
    private static final VoxelShape SHAPE = makeCuboidShape( 6, 0, 6, 10, 16, 10 );

    public FlyFlowerStalkBlock( Properties properties ) {
        super( properties );
    }

    public boolean canBlockSustain( IWorldReader world, BlockPos pos, BlockState state ) {
        return state.isIn( MDBlockTags.SOIL ) || state.getBlock() == this;
    }

    @Override
    public boolean canRemain( IWorldReader world, BlockPos pos, BlockState state, Direction dir, BlockPos adj, BlockState adjState ) {
        return dir != Direction.DOWN || canBlockSustain( world, adj, adjState );
    }

    @Override
    public boolean provide( IWorld world, BlockPos pos, Random rand ) {
        return false;
    }

    @Override
    @SuppressWarnings( "deprecation" )
    public VoxelShape getShape( BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context ) {
        return SHAPE;
    }

    @Override
    public OffsetType getOffsetType() {
        return OffsetType.NONE;
    }
}
