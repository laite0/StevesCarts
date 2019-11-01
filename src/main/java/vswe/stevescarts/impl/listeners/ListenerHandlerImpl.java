package vswe.stevescarts.impl.listeners;

import vswe.stevescarts.api.component.Component;
import vswe.stevescarts.api.listeners.ListenerHandler;
import vswe.stevescarts.api.listeners.ListenerRegister;
import vswe.stevescarts.api.util.QuadConsumer;
import vswe.stevescarts.api.util.TriConsumer;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public abstract class ListenerHandlerImpl<T extends Component> implements ListenerRegister<T>, ListenerHandler<T> {
	private final HashMap<Class<?>, List<Consumer<T>>> consumers = new HashMap<>();
	private final HashMap<Class<?>, List<BiConsumer<T, Object>>> biConsumers = new HashMap<>();
	private final HashMap<Class<?>, List<TriConsumer<T, Object, Object>>> triConsumers = new HashMap<>();
	private final HashMap<Class<?>, List<QuadConsumer<T, Object, Object, Object>>> quadConsumers = new HashMap<>();

	@Override
	public <L extends Runnable> void addListener(Class<L> listenerClass, Consumer<T> consumer) {
		consumers.computeIfAbsent(listenerClass, (s) -> new LinkedList<>()).add(consumer);
	}

	@Override
	public <A, L extends Consumer<A>> void addListener(Class<L> listenerClass, BiConsumer<T, A> biConsumer) {
		//noinspection unchecked
		biConsumers.computeIfAbsent(listenerClass, (s) -> new LinkedList<>()).add((BiConsumer<T, Object>) biConsumer);
	}

	@Override
	public <A, B, L extends BiConsumer<A, B>> void addListener(Class<L> listenerClass, TriConsumer<T, A, B> triConsumer) {
		//noinspection unchecked
		triConsumers.computeIfAbsent(listenerClass, (s) -> new LinkedList<>()).add((TriConsumer<T, Object, Object>) triConsumer);
	}

	@Override
	public <A, B, C, L extends TriConsumer<A, B, C>> void addListener(Class<L> listenerClass, QuadConsumer<T, A, B, C> quadConsumer) {
		//noinspection unchecked
		quadConsumers.computeIfAbsent(listenerClass, (s) -> new LinkedList<>()).add((QuadConsumer<T, Object, Object, Object>) quadConsumer);
	}

	@Override
	public <L extends Runnable> void fire(T component, Class<L> listerClass) {
		consumers.getOrDefault(listerClass, Collections.emptyList()).forEach(consumer -> consumer.accept(component));
	}

	@Override
	public <A, L extends Consumer<A>> void fire(T component, Class<L> ListerClass, A a) {
		biConsumers.getOrDefault(ListerClass, Collections.emptyList()).forEach(consumer -> consumer.accept(component, a));
	}

	@Override
	public <A, B, L extends BiConsumer<A, B>> void fire(T component, Class<L> ListerClass, A a, B b) {
		triConsumers.getOrDefault(ListerClass, Collections.emptyList()).forEach(consumer -> consumer.accept(component, a, b));
	}

	@Override
	public <A, B, C, L extends TriConsumer<A, B, C>> void fire(T component, Class<L> ListerClass, A a, B b, C c) {
		quadConsumers.getOrDefault(ListerClass, Collections.emptyList()).forEach(consumer -> consumer.accept(component, a, b, c));
	}
}
