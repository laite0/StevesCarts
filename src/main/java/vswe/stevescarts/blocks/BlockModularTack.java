package vswe.stevescarts.blocks;

import net.minecraft.block.BlockRail;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import sun.applet.Main;
import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.api.tracks.IModuleLogicHandler;
import vswe.stevescarts.blocks.tileentities.TileEntityModularTack;
import vswe.stevescarts.api.tracks.TrackList;
import vswe.stevescarts.tracks.TrackManager;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class BlockModularTack extends BlockRail implements ITileEntityProvider {

    public BlockModularTack() {
        super();
	    setCreativeTab(StevesCarts.tabsSC2Blocks);
        this.setDefaultState(createDefaultSate());
    }

	@Override
	protected BlockStateContainer createBlockState() {
		IProperty[] properties = TrackManager.propertyList.values().toArray(new IProperty[TrackManager.propertyList.values().size() + 1]);
		properties[TrackManager.propertyList.values().size()] = SHAPE;
		return new BlockStateContainer(this, properties);
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		TileEntityModularTack tileEntityModularTack = (TileEntityModularTack) getTileEntitySafely(worldIn, pos);
		return tileEntityModularTack.getExtraStates(state);
	}

	//see for more info https://www.reddit.com/r/feedthebeast/comments/5mxwq9/psa_mod_devs_do_you_call_worldgettileentity_from/
	public TileEntity getTileEntitySafely(IBlockAccess blockAccess, BlockPos pos) {
		if (blockAccess instanceof ChunkCache) {
			return ((ChunkCache) blockAccess).getTileEntity(pos, Chunk.EnumCreateEntityType.CHECK);
		} else {
			return blockAccess.getTileEntity(pos);
		}
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    	//TODO debugcode
    	ItemStack stack = playerIn.getHeldItem(EnumHand.MAIN_HAND);
    	if(!stack.isEmpty() && stack.getItem() == Items.REDSTONE){
		    TileEntityModularTack modularTack = (TileEntityModularTack) world.getTileEntity(pos);
		    modularTack.setModules(TrackManager.getModuleFromName("stevescarts:detector_module_off"));
		    modularTack.markRenderUpdate();
	    }
		return super.onBlockActivated(world, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return super.getStateFromMeta(meta);
	}

	public IBlockState createDefaultSate() {
		IBlockState state = super.getDefaultState();
		for(Map.Entry<IProperty<?>, String> mapEntry : TrackManager.defaultSateMap.entrySet()){
			state = state.withProperty((IProperty)mapEntry.getKey(), mapEntry.getValue());
		}
		return state;
	}

	@Nullable
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityModularTack();
	}

	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
		for(TrackList.TrackModule slepper : TrackManager.getAllModulesForType(TrackList.TrackModuleType.SLEEPER)){
			for(TrackList.TrackModule rail : TrackManager.getAllModulesForType(TrackList.TrackModuleType.RAIL)){
				ItemStack stack = new ItemStack(this, 1);
				List<TrackList.TrackModule> moduleList = new ArrayList<>();
				moduleList.add(slepper);
				moduleList.add(rail);
				stack.setTagCompound(TrackManager.toNBT(moduleList));
				items.add(stack);
			}
		}
	}

	@Override
	public void addInformation(ItemStack stack,
	                           @Nullable
		                           World player, List<String> tooltip, ITooltipFlag advanced) {
		super.addInformation(stack, player, tooltip, advanced);
		List<TrackList.TrackModule> moduleList = TrackManager.fromNBT(stack.getTagCompound());
		for(TrackList.TrackModule module : moduleList){
			tooltip.add(TextFormatting.BLUE + module.type.name() + TextFormatting.RED +  " : " +TextFormatting.GOLD  + module.name);
		}
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		List<TrackList.TrackModule> moduleList = TrackManager.fromNBT(stack.getTagCompound());
		TileEntityModularTack modularTack = (TileEntityModularTack) worldIn.getTileEntity(pos);
		for(TrackList.TrackModule module : moduleList){
			modularTack.setModules(module);
		}
	}

	@Override
	public float getRailMaxSpeed(World world, EntityMinecart cart, BlockPos pos) {
		TileEntityModularTack modularTack = (TileEntityModularTack) world.getTileEntity(pos);
		TrackList.TrackModule trackModule = modularTack.getModule(TrackList.TrackModuleType.RAIL);
		if(trackModule != null && trackModule.dataMap.containsKey("speed")){
			float speed = Float.parseFloat(trackModule.dataMap.get("speed"));
			return speed;
		}
		return 0.4F;
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
    	ItemStack stack = new ItemStack(this, 1);
		TileEntityModularTack modularTack = (TileEntityModularTack) world.getTileEntity(pos);
		stack.setTagCompound(TrackManager.toNBT(new ArrayList<>(modularTack.modules.values())));
		return stack;
	}

	public void handleModuleEvent(World world, BlockPos pos, Consumer<IModuleLogicHandler> consumer){
		TileEntityModularTack modularTack = (TileEntityModularTack) world.getTileEntity(pos);
		modularTack.modules.forEach((key, value) -> consumer.accept(value.moduleLogicHandler));
	}

	@Override
	public void onMinecartPass(World world, EntityMinecart cart, BlockPos pos) {
		super.onMinecartPass(world, cart, pos);
		handleModuleEvent(world, pos, iModuleLogicHandler -> iModuleLogicHandler.onMinecartPass(world, cart, pos));
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		super.breakBlock(worldIn, pos, state);
		worldIn.removeTileEntity(pos);
	}

	@Override
	public boolean eventReceived(IBlockState state, World worldIn, BlockPos pos, int id, int param) {
		super.eventReceived(state, worldIn, pos, id, param);
		TileEntity tileentity = worldIn.getTileEntity(pos);
		return tileentity == null ? false : tileentity.receiveClientEvent(id, param);
	}

}
