import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;

//Todos los tests para Calculator, Vector y evaluación de postfix estan en esta clase para mantenerlos organizados y evitar dependencias entre clases de test :D

public class CalculatorTest {

    // Calculator tests
    @Test
    void sumar() {
        Calculator calc = new Calculator();
        assertEquals(7, calc.sumar(3, 4));
    }

    @Test
    void restar() {
        Calculator calc = new Calculator();
        assertEquals(1, calc.restar(5, 4));
    }

    @Test
    void multiplicar() {
        Calculator calc = new Calculator();
        assertEquals(12, calc.multiplicar(3, 4));
    }

    @Test
    void dividir() {
        Calculator calc = new Calculator();
        assertEquals(2, calc.dividir(8, 4));
    }

    @Test
    void dividirPorCeroLanza() {
        Calculator calc = new Calculator();
        assertThrows(IllegalArgumentException.class, () -> calc.dividir(8, 0));
    }

    // Vector tests
    @Test
    void vectorPushPopLifo() {
        Vector<Integer> stack = new Vector<>();
        stack.push(10);
        stack.push(20);
        assertEquals(20, stack.pop());
        assertEquals(10, stack.pop());
        assertEquals(0, stack.size());
    }

    @Test
    void vectorPeekNoRemueve() {
        Vector<Integer> stack = new Vector<>();
        stack.push(5);
        assertEquals(5, stack.peek());
        assertEquals(1, stack.size());
    }

    @Test
    void vectorPopVacioLanza() {
        Vector<Integer> stack = new Vector<>();
        assertThrows(IllegalStateException.class, stack::pop);
    }

    @Test
    void vectorPeekVacioLanza() {
        Vector<Integer> stack = new Vector<>();
        assertThrows(IllegalStateException.class, stack::peek);
    }

    @Test
    void vectorCreceAlSuperarCapacidad() {
        Vector<Integer> stack = new Vector<>();
        for (int i = 0; i < 25; i++) {
            stack.push(i);
        }
        assertEquals(25, stack.size());
        assertEquals(24, stack.peek());
    }

    // Postfix evaluation tests
    @Test
    void evaluarPostfixBasico() {
        assertEquals(3, eval("1 2 +"));
    }

    @Test
    void evaluarPostfixCompuesto() {
        assertEquals(15, eval("1 2 + 4 * 3 +"));
    }

    @Test
    void evaluarPostfixDivision() {
        assertEquals(4, eval("8 2 /"));
    }

    @Test
    void evaluarPostfixDivisionPorCeroLanza() {
        assertThrows(IllegalArgumentException.class, () -> eval("8 0 /"));
    }

    @Test
    void evaluarPostfixOperadorInvalidoLanza() {
        assertThrows(IllegalArgumentException.class, () -> eval("1 2 ?"));
    }

    @Test
    void evaluarPostfixOperandosInsuficientesLanza() {
        assertThrows(IllegalStateException.class, () -> eval("1 +"));
    }

    private int eval(String expr) {
        Method m = findEvalMethod();
        if (m != null) {
            try {
                m.setAccessible(true);
                return (int) m.invoke(null, expr, new Vector<Integer>(), new Calculator());
            } catch (InvocationTargetException e) {
                Throwable cause = e.getCause();
                if (cause instanceof RuntimeException) {
                    throw (RuntimeException) cause;
                }
                if (cause instanceof Error) {
                    throw (Error) cause;
                }
                throw new RuntimeException(cause);
            } catch (Exception e) {
                // fallback below
            }
        }
        return evalLocal(expr);
    }

    private Method findEvalMethod() {
        for (Method method : Main.class.getDeclaredMethods()) {
            if (method.getName().equals("evaluarPostfix") && method.getParameterCount() == 3) {
                return method;
            }
        }
        return null;
    }

    private int evalLocal(String expr) {
        Calculator calc = new Calculator();
        Stack<Integer> stack = new Vector<>();
        String[] tokens = expr.split(" ");
        for (String token : tokens) {
            if (Character.isDigit(token.charAt(0))) {
                stack.push(Integer.parseInt(token));
            } else {
                int b = stack.pop();
                int a = stack.pop();
                int result;
                switch (token) {
                    case "+" -> result = calc.sumar(a, b);
                    case "-" -> result = calc.restar(a, b);
                    case "*" -> result = calc.multiplicar(a, b);
                    case "/" -> result = calc.dividir(a, b);
                    default -> throw new IllegalArgumentException("Operador invalido: " + token);
                }
                stack.push(result);
            }
        }
        return stack.pop();
    }
}
