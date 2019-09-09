/*
 * Copyright (c) 2019 RedGalaxy & co.
 * Licensed under the Apache Licence v2.0.
 * Do not redistribute.
 *
 * By  : RGSW
 * Date: 6 - 11 - 2019
 */

package modernity.common.item.block;

import modernity.common.block.base.BlockBranch;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemBlock;

public class ItemBranch extends ItemBlock {
    private final BlockBranch branch;

    public ItemBranch( BlockBranch block, Properties builder ) {
        super( block, builder );
        branch = block;
    }

    @Override
    protected boolean placeBlock( BlockItemUseContext ctx, IBlockState state ) {
        boolean sneaking = false;
        if( ctx.getPlayer() != null ) sneaking = ctx.getPlayer().isSneaking();
        return branch.place( ctx.getFace(), ctx.getWorld(), ctx.getPos(), state, ! sneaking );
    }
}
