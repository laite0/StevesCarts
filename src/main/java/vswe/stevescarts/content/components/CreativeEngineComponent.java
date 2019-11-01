package vswe.stevescarts.content.components;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import vswe.stevescarts.api.component.Component;

public class CreativeEngineComponent extends Component {

	public CreativeEngineComponent(InitData data) {
		super(data);
	}

	public void tick() {
		World world = getCart().getWorld();

	}

	public void use(PlayerEntity playerEntity) {

	}
}
