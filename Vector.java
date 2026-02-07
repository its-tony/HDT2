import java.util.Arrays;
/**
 * Implementación de una pila (Stack) utilizando un vector de tamaño dinámico.
 * <p>
 * Esta clase implementa el ADT Stack usando un arreglo genérico que
 * crece automáticamente cuando se alcanza su capacidad.
 * </p>
 */
public class Vector<T> implements Stack<T> {

    private T[] data;
    private int size;

/** Tamaño inicial*/
    public Vector() {
        data = (T[]) new Object[10];
        size = 0;
    }

/** Agrega un dato a la pila */
    @Override
    public void push(T item) {
        if (size == data.length) {
            grow();
        }
        data[size++] = item;
    }
/** Elimina y devuelve el elemento superior de la pila */
    @Override
    public T pop() {
        if (size == 0) {
            throw new IllegalStateException("La pila está vacía");
        }
        T item = data[--size];
        data[size] = null; // evita memory leak
        return item;
    }
/** Devuelve el elemento superior de la pila*/
    @Override
    public T peek() {
        if (size == 0) {
            throw new IllegalStateException("La pila está vacía");
        }
        return data[size - 1];
    }
/** Da el tamaño de la pila */
    @Override
    public int size() {
        return size;
    }

    private void grow() {
        data = Arrays.copyOf(data, data.length * 2);
    }
}
