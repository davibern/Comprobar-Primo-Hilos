// Librerías
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Programa principal que ejecuta ComprobarPrimo.java
 * 
 * @author David Bernabé
 * @version 1.2
 */
public class EjecutarProgramaComprobarPrimo {
 
    public static void main(String[] args) {
        
        // Variables
        String nombreFichero = null;
        List<Long> numeros = new ArrayList<>();
        List<Thread> hilos = new ArrayList<>();
        int numeroHilos;
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
            FileReader fichero = new FileReader(nombreFichero);
            BufferedReader b = new BufferedReader(fichero);
            
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
            
            // Cerrar el fichero una vez terminado el trabajo
            fichero.close();
            
            // Logs por pantalla
            System.out.println("Lanzando comprobadores concurrentes de primalildad.\n");
            
            // Cada elemento de números se usará para crear un objeto de tipo ComprobadorPrimo y posteriormente se meterá en una lista de tipo Thread
            for (Long numero : numeros) {
                ComprobadorPrimo numeroComprobar = new ComprobadorPrimo(numero);
                hilos.add(numeroComprobar);
            }
            
            // Guardamos los números de hilos para saber cuántos tienen que terminar
            numeroHilos = hilos.size();
            
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
                } finally {
                   numeroHilosFinalizados++;
                }
            }
            
            // Si todos los hilos han finalizado se mostrará por pantalla que el programa ha terminado
            if (numeroHilos == numeroHilosFinalizados) {
                System.out.println("Analizados todos los números. Fin del programa.");
            } 
            
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        
    }
    
}