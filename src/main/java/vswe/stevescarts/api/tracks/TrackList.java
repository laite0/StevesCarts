package vswe.stevescarts.api.tracks;

import java.util.HashMap;
import java.util.List;

public class TrackList {

	public List<TrackModule> modules;

	public static class TrackModule {
		public TrackModuleType type;
		public String name;
		public String textureLocation;
		public String textureLocationCorner;
		public HashMap<String, String> dataMap;
		public String moduleHandlerClass;
		public boolean isDummyModule = false; //Not to be set by any tracks in the json file
		public transient IModuleLogicHandler moduleLogicHandler;
	}

	public enum  TrackModuleType {
		RAIL(false),
		SLEEPER(false),
		MODULE(true);

		public boolean isPlayerAdded;

		TrackModuleType(boolean isPlayerAdded) {
			this.isPlayerAdded = isPlayerAdded;
		}
	}

}
