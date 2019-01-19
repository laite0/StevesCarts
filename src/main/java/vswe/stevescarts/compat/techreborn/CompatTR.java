package vswe.stevescarts.compat.techreborn;

import vswe.stevescarts.api.ISCHelpers;
import vswe.stevescarts.api.ISCPlugin;
import vswe.stevescarts.api.SCLoadingPlugin;

@SCLoadingPlugin(dependentMod = "techreborn")
public class CompatTR implements ISCPlugin {

	@Override
	public void loadAddons(ISCHelpers plugins) {
		plugins.registerTree(new TRRubberTreeModule());
	}
}
