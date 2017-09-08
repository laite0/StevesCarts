package vswe.stevescarts.tracks;

import java.util.List;

public class TrackList {

	public List<TrackModule> modules;

	public class TrackModule {
		public TrackModuleType type;
		public String name;
		public String textureLocation;
		public String textureLocationCorner;
	}

	public enum  TrackModuleType {
		RAIL,
		SLEEPER
	}

}
