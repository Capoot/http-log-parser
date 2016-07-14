package de.jheise.log;

import java.util.Collection;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public class CommunicationLog implements Iterable<Communication> {

    public int size() {
        throw new RuntimeException("todo");
    }

    public Iterator<Communication> iterator() {
        throw new RuntimeException("todo");
    }

    public void forEach(Consumer<? super Communication> action) {
        throw new RuntimeException("todo");
    }

    public Spliterator<Communication> spliterator() {
        throw new RuntimeException("todo");
    }

    public void add(Communication c) {
        throw new RuntimeException("todo");
    }
}
