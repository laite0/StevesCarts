package vswe.stevescarts.api.listeners;

import vswe.stevescarts.api.component.Component;
import vswe.stevescarts.api.util.TriConsumer;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface ListenerHandler<T extends Component> {

	<L extends Runnable> void listen(T component, Class<L> listerClass);

	<A, L extends Consumer<A>> void listen(T component, Class<L> ListerClass, A a);

	<A, B, L extends BiConsumer<A, B>> void listen(T component, Class<L> ListerClass, A a, B b);

	<A, B, C, L extends TriConsumer<A, B, C>> void listen(T component, Class<L> ListerClass, A a, B b, C c);


}
