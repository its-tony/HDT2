package HDT2;

public interface Stack<T> {
    void push(T item);
    T pop();
    T peek();
    int size();
}