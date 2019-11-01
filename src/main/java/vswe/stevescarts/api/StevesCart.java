package vswe.stevescarts.api;

import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public interface StevesCart {

	World getWorld();

	Vec3d getLocation();

}
