package vswe.stevescarts.tracks;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.block.properties.IProperty;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import org.apache.commons.io.IOUtils;
import reborncore.common.blocks.PropertyString;
import vswe.stevescarts.StevesCarts;
import vswe.stevescarts.api.tracks.IModuleLogicHandler;
import vswe.stevescarts.api.tracks.TrackList;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class TrackManager {

	public static Gson GSON = new GsonBuilder().setPrettyPrinting().create();

	public static TrackList trackList;
	public static HashMap<TrackList.TrackModuleType, IProperty<?>> propertyList = new HashMap<>();
	public static HashMap<IProperty<?>, String> defaultSateMap = new HashMap<>();

	public static void loadTracks() {
		trackList = new TrackList();
		trackList.modules = new ArrayList<>();
		Loader.instance().getActiveModList().forEach(TrackManager::loadTrackForMod);
		buildStates();
	}

	private static void buildStates() {
		propertyList.clear();
		defaultSateMap.clear();
		Arrays.stream(TrackList.TrackModuleType.values()).forEach(moduleType -> {
			List<TrackList.TrackModule> modules = getAllModulesForType(moduleType);
			TrackList.TrackModule dummyModule = null;
			if(moduleType.isPlayerAdded){
				dummyModule = new TrackList.TrackModule();
				dummyModule.isDummyModule = true;
				dummyModule.name = "empty";
				dummyModule.type = moduleType;
				dummyModule.moduleLogicHandler = new IModuleLogicHandler(){};
				modules.add(dummyModule);
			}
			PropertyString property = new PropertyString(moduleType.name().toLowerCase(), modules.stream()
				.filter(trackModule -> trackModule.type == moduleType)
				.map(trackModule -> trackModule.name)
				.collect(Collectors.toList()));
			propertyList.put(moduleType, property);
			if(dummyModule != null){
				defaultSateMap.put(property, dummyModule.name);
			} else {
				defaultSateMap.put(property, modules.get(0).name);
			}
		});
	}

	public static List<TrackList.TrackModule> getAllModulesForType(TrackList.TrackModuleType type) {
		return trackList.modules.stream().filter(trackModule -> trackModule.type == type).collect(Collectors.toList());
	}

	public static TrackList.TrackModule getModuleFromName(String name) {
		name = name.replace(":", "_");
		for (TrackList.TrackModule module : trackList.modules) {
			if (module.name.equalsIgnoreCase(name)) {
				return module;
			}
		}
		return null;
	}

	private static void loadTrackForMod(ModContainer modContainer) {
		CraftingHelper.findFiles(modContainer, "assets/" + modContainer.getModId() + "/tracks", root -> {
			Path tracksPath = root.resolve("tracks.json");
			if (tracksPath != null && Files.exists(tracksPath)) {
				StevesCarts.logger.info("Reading tracks.json for " + modContainer.getName());
				BufferedReader bufferedReader = null;
				try {
					bufferedReader = Files.newBufferedReader(tracksPath);
					TrackList trackList = GSON.fromJson(bufferedReader, TrackList.class);
					trackList.modules.forEach(trackModule -> {
						trackModule.name = trackModule.name.toLowerCase().replace(":", "_");
						if(trackModule.moduleHandlerClass != null && !trackModule.moduleHandlerClass.isEmpty()){
							try {
								Class<? extends IModuleLogicHandler> clazz = (Class<? extends IModuleLogicHandler>) Class.forName(trackModule.moduleHandlerClass);
								IModuleLogicHandler logicHandler = clazz.newInstance();
								trackModule.moduleLogicHandler = logicHandler;
								trackModule.moduleLogicHandler.load(trackModule);
							} catch (Exception e) {
								e.printStackTrace();
							}
						} else {
							trackModule.moduleLogicHandler = new IModuleLogicHandler(){};
						}
					});
					TrackManager.trackList.modules.addAll(trackList.modules);
				} catch (IOException e) {
					StevesCarts.logger.error("Error reading tracks.json: ", e);
					return false;
				} finally {
					IOUtils.closeQuietly(bufferedReader);
				}
			}
			return true;
		}, (path, path2) -> false, false, false);
	}

	public static void addTrack(TrackList.TrackModule trackModule){
		TrackManager.trackList.modules.add(trackModule);
	}


	public static NBTTagCompound toNBT(List<TrackList.TrackModule> modules){
		NBTTagCompound tagCompound = new NBTTagCompound();
		for (TrackList.TrackModule module : modules){
			tagCompound.setString(module.type.name(), module.name);
		}
		return tagCompound;
	}

	public static List<TrackList.TrackModule> fromNBT(NBTTagCompound tagCompound){
		if(tagCompound == null){
			return Collections.emptyList();
		}
		List<TrackList.TrackModule> list = new ArrayList<>();
		for(String key : tagCompound.getKeySet()){
			list.add(getModuleFromName(tagCompound.getString(key)));
		}
		return list;
	}

}
