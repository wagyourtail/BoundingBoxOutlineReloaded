package com.irtimaled.bbor.client.providers;

import com.irtimaled.bbor.ReflectionHelper;
import com.irtimaled.bbor.client.interop.TileEntitiesHelper;
import com.irtimaled.bbor.client.models.BoundingBoxConduit;
import com.irtimaled.bbor.common.models.Coords;
import net.minecraft.tileentity.TileEntityConduit;
import net.minecraft.util.math.BlockPos;

import java.util.List;
import java.util.function.Function;

public class ConduitProvider implements IBoundingBoxProvider<BoundingBoxConduit> {
    private static final Function<TileEntityConduit, List<BlockPos>> blocksFetcher =
            ReflectionHelper.getPrivateFieldGetter(TileEntityConduit.class, List.class, BlockPos.class);

    @Override
    public Iterable<BoundingBoxConduit> get(int dimensionId) {
        return TileEntitiesHelper.map(TileEntityConduit.class, conduit -> {
            List<BlockPos> blocks = blocksFetcher.apply(conduit);
            if (blocks == null) return null;

            Coords coords = new Coords(conduit.getPos());
            return BoundingBoxConduit.from(coords, conduit.isActive() ? blocks.size() / 7 : 0);
        });
    }
}