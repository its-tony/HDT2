import java.util.Arrays;

public class Vector<T> implements Stack<T> {

    private T[] data;
    private int size;

    public Vector() {
        data = (T[]) new Object[10]; // tamaño inicial
        size = 0;
    }

    @Override
    public void push(T item) {
        if (size == data.length) {
            grow();
        }
        data[size++] = item;
    }

    @Override
    public T pop() {
        if (size == 0) {
            throw new IllegalStateException("La pila está vacía");
        }
        T item = data[--size];
        data[size] = null; // evita memory leak
        return item;
    }

    @Override
    public T peek() {
        if (size == 0) {
            throw new IllegalStateException("La pila está vacía");
        }
        return data[size - 1];
    }

    @Override
    public int size() {
        return size;
    }

    private void grow() {
        data = Arrays.copyOf(data, data.length * 2);
    }
}
