package vswe.stevescarts.tracks.modules;

import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import vswe.stevescarts.api.tracks.IModuleLogicHandler;
import vswe.stevescarts.api.tracks.TrackList;
import vswe.stevescarts.blocks.tileentities.TileEntityModularTack;
import vswe.stevescarts.tracks.TrackManager;

public class ModuleDetector implements IModuleLogicHandler {

	@Override
	public void load(TrackList.TrackModule module) {
		System.out.println("hello " + module.name);
	}

	@Override
	public void onMinecartPass(World world, EntityMinecart cart, BlockPos pos) {
		TileEntityModularTack modularTack = (TileEntityModularTack) world.getTileEntity(pos);
		modularTack.queueModuleUpdate(TrackManager.getModuleFromName("stevescarts:detector_module_on"));
		System.out.println("pass!");
	}
}
