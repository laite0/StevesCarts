package vswe.stevescarts.blocks.tileentities;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import vswe.stevescarts.tracks.TrackList;
import vswe.stevescarts.tracks.TrackManager;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class TileEntityModularTack extends TileEntity {

	public HashMap<TrackList.TrackModuleType, TrackList.TrackModule> modules = new HashMap<>();

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

	public void setModule(TrackList.TrackModule module){
		if(modules.containsKey(module.type)){
			modules.remove(module.type);
		}
		modules.put(module.type, module);
	}

	public TrackList.TrackModule getModule(TrackList.TrackModuleType trackModuleType){
		return modules.get(trackModuleType);
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
		return false;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		if(compound.hasKey("moduleData")){
			List<TrackList.TrackModule> moduleList = TrackManager.fromNBT(compound.getCompoundTag("moduleData"));
			moduleList.forEach(this::setModule);
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		List<TrackList.TrackModule> moduleList = new ArrayList<>(modules.values());
		compound.setTag("moduleData", TrackManager.toNBT(moduleList));
		return super.writeToNBT(compound);
	}


	@Nullable
	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(getPos(), 0, writeToNBT(new NBTTagCompound()));
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		super.onDataPacket(net, pkt);
		readFromNBT(pkt.getNbtCompound());
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		return writeToNBT(new NBTTagCompound());
	}
}
