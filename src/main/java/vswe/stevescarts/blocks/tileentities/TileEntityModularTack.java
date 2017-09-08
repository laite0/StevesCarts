package vswe.stevescarts.blocks.tileentities;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import vswe.stevescarts.tracks.TrackList;
import vswe.stevescarts.tracks.TrackManager;

import java.util.HashMap;
import java.util.Map;

public class TileEntityModularTack extends TileEntity {

	HashMap<TrackList.TrackModuleType, TrackList.TrackModule> modules = new HashMap<>();

	public TileEntityModularTack() {
		modules.put(TrackList.TrackModuleType.RAIL, TrackManager.getModuleFromName("stevescarts:diamond_rail"));
		modules.put(TrackList.TrackModuleType.SLEEPER, TrackManager.getModuleFromName("stevescarts:iron_sleeper"));
	}

	public IBlockState getExtraStates(IBlockState state){
		for(Map.Entry<TrackList.TrackModuleType, IProperty<?>> mapEntry : TrackManager.propertyList.entrySet()){
			IProperty property = mapEntry.getValue();
			TrackList.TrackModuleType moduleType = mapEntry.getKey();
			state = state.withProperty(property, modules.get(moduleType).name);
		}
		return state;
	}

}
