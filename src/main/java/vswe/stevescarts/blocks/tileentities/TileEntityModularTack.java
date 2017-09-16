package vswe.stevescarts.blocks.tileentities;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import vswe.stevescarts.api.tracks.TrackList;
import vswe.stevescarts.tracks.TrackManager;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class TileEntityModularTack extends TileEntity implements ITickable {

	public HashMap<TrackList.TrackModuleType, TrackList.TrackModule> modules = new HashMap<>();
	private List<TrackList.TrackModule> moduleQueue = new ArrayList<>();

	public IBlockState getExtraStates(IBlockState state){
		for(Map.Entry<TrackList.TrackModuleType, IProperty<?>> mapEntry : TrackManager.propertyList.entrySet()){
			IProperty property = mapEntry.getValue();
			TrackList.TrackModuleType moduleType = mapEntry.getKey();
			if(modules.containsKey(moduleType)){
				state = state.withProperty(property, modules.get(moduleType).name);
			}
		}
		return state;
	}

	@Override
	public void update() {
		if(!world.isRemote && !moduleQueue.isEmpty()){
			setModules(moduleQueue);
			moduleQueue.clear();
			markRenderUpdate();
			//TODO sync with client
		}
	}

	public void setModules(TrackList.TrackModule... modulelist){
		for(TrackList.TrackModule module : modulelist){
			if(modules.containsKey(module.type)){
				modules.remove(module.type);
			}
			modules.put(module.type, module);
		}
	}

	public void setModules(List<TrackList.TrackModule> modulelist){
		for(TrackList.TrackModule module : modulelist){
			if(modules.containsKey(module.type)){
				modules.remove(module.type);
			}
			modules.put(module.type, module);
		}
	}

	public void queueModuleUpdate(TrackList.TrackModule trackModule){
		moduleQueue.add(trackModule);
	}


	public void markRenderUpdate(){
		world.markBlockRangeForRenderUpdate(getPos(), getPos());
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
			moduleList.forEach(this::setModules);
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
