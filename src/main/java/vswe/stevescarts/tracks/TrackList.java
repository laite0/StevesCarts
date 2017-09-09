package vswe.stevescarts.tracks;

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
	}

	public enum  TrackModuleType {
		RAIL,
		SLEEPER
	}

}
