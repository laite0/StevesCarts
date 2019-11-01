package vswe.stevescarts.api.listeners;

import vswe.stevescarts.api.component.Component;
import vswe.stevescarts.api.util.QuadConsumer;
import vswe.stevescarts.api.util.TriConsumer;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface ListenerRegister<T extends Component> {

	<L extends Runnable> void addListener(Class<L> listenerClass, Consumer<T> consumer);
	<A, L extends Consumer<A>> void addListener(Class<L> listenerClass, BiConsumer<T, A> biConsumer);
	<A, B, L extends BiConsumer<A, B>> void addListener(Class<L> listenerClass, TriConsumer<T, A, B> biConsumer);
	<A, B, C, L extends TriConsumer<A, B, C>> void addListener(Class<L> listenerClass, QuadConsumer<T, A, B, C> biConsumer);


}
