public class Calculator {
    int sumar(int a, int b) {
        return a + b;
    }
    int multiplicar(int a, int b) {
        return a * b;
    }
    int restar(int a, int b) {
        return a - b;
    }
    int dividir(int a, int b) {
        if (b == 0) {
            throw new IllegalArgumentException("No se puede dividir por cero");
        }
        return a / b;
    }
}
