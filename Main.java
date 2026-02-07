import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        Calculator calc = new Calculator();
        Stack<Integer> stack = new Vector<>();

        try (BufferedReader br = new BufferedReader(new FileReader("datos.txt"))) {

            String line;
            while ((line = br.readLine()) != null) {

                System.out.println("Expresión: " + line);
                int result = evaluarPostfix(line, stack, calc);
                System.out.println("Resultado: " + result);
                System.out.println("--------------------");
            }

        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    private static int evaluarPostfix(String expr, Stack<Integer> stack, Calculator calc) {

        // limpiar la pila por si se reutiliza
        while (stack.size() > 0) {
            stack.pop();
        }

        String[] tokens = expr.split(" ");

        for (String token : tokens) {

            // operando
            if (Character.isDigit(token.charAt(0))) {
                stack.push(Integer.parseInt(token));
            }
            // operador
            else {
                int b = stack.pop();
                int a = stack.pop();

                int result;
                switch (token) {
                    case "+" -> result = calc.sumar(a, b);
                    case "-" -> result = calc.restar(a, b);
                    case "*" -> result = calc.multiplicar(a, b);
                    case "/" -> result = calc.dividir(a, b);
                    default -> throw new IllegalArgumentException("Operador inválido: " + token);
                }

                stack.push(result);
            }
        }

        return stack.pop();
    }
}