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

    //hacer metodo aca
}