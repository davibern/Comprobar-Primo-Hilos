// Librerías
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Programa principal que ejecuta ComprobarPrimo.java
 * 
 * @author David Bernabé
 * @version 1.3
 */
public class EjecutarProgramaComprobarPrimo {
    
    // Variables
    public static FileReader fichero = null;
    public static BufferedReader b = null;
    public static List<Long> numerosPrimos = new ArrayList<>();
    
    public static void ingresarNumeroPrimo(Long numero) {
        numerosPrimos.add(numero);
    }
    
    /**
     * Método que lanza el programa para comprobar números primos
     * @param args Fichero de texto con los números a analizar
     */
    public static void main(String[] args) {
        
        // Variables
        String nombreFichero = null;
        List<Long> numeros = new ArrayList<>();
        List<Thread> hilos = new ArrayList<>();
        int numeroHilosFinalizados = 0;
        
        // Obtener fichero de argumentos de línea
        if (args.length == 0) {
            System.out.println("No se ha includo ningún fichero para analizar.");
            System.exit(0);
        } else {
            nombreFichero = args[0];
        }
        
        // Abrir fichero y almacenar en conjuntod de datos
        try {
            String cadena = null;
            fichero = new FileReader(nombreFichero);
            b = new BufferedReader(fichero);
            
            // Menú
            System.out.println("COMPROBACIÓN DE NÚMEROS PRIMOS");
            System.out.println("------------------------------");
            System.out.println("Leyendo archivo de entrada.\n");
            
            // Pasar el dato si es tipo Long a la lista de Longs, si no se obvia
            while ( (cadena = b.readLine()) != null) {
                try {
                    numeros.add(Long.parseLong(cadena));
                } catch (NumberFormatException e) {}   
            }
            
            // Logs por pantalla
            System.out.println("Números leídos: " + numeros + "\n");
            System.out.println("Lanzando comprobadores concurrentes de primalildad.\n");
            
            // Cada elemento de números se usará para crear un objeto de tipo ComprobadorPrimo y posteriormente se meterá en una lista de tipo Thread
            for (Long numero : numeros) {
                ComprobadorPrimo numeroComprobar = new ComprobadorPrimo(numero);
                hilos.add(numeroComprobar);
            }
            
            // Posteriormente se ejecutarán todos los hilos
            for (Thread hilo : hilos) {
                hilo.start();
            }
            
            // Cuando finalice la ejecución del hilo se sumará al contador de hilos finalizados
            for (Thread hilo : hilos) {
                try {
                    hilo.join();
                } catch (InterruptedException e) {
                    System.err.println(e.getMessage());
                }
            }

            // Salidas por pantalla del final de ejecución
            System.out.println(numerosPrimos);
            System.out.println("\nAnalizados todos los números. Fin del programa.");
            
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } finally {
            if (b != null) {
                try {
                    fichero.close();
                    b.close();
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
        
    }
    
}