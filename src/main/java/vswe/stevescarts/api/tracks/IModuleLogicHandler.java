package vswe.stevescarts.api.tracks;

import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IModuleLogicHandler {

	public default void load(TrackList.TrackModule module){}


	public default void onMinecartPass(World world, EntityMinecart cart, BlockPos pos){}

}
