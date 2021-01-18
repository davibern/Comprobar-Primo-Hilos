/**
 * Clase realizada por el profesor que comprueba si un número es primo.
 * 
 * @author profesor
 * @version 1.0
 */
public class ComprobadorPrimo extends Thread {
    
    // Atributos
    private final long numero;
    private long divisor=0;

    /**
     * Constructor que pide un número de tipo Long
     * @param numero Número de tipo Long que se usará para comprobar si es primo
     */
    public ComprobadorPrimo(long numero) {
        this.numero = numero;
    }
    
    /**
     * Método sobreescrito run de Thread que implementa el método esPrimo
     */
    @Override
    public void run() {
        if (esPrimo(numero)) {
            System.out.printf ("%10d es primo.\n", this.numero);
        } else {
            System.out.printf ("%10d no es primo. Es divisible entre %d.\n",
                    this.numero, this.divisor);
        }        
    }
    
    /**
     * Método que comprueba si un número es tipo primo
     * @param numero El número a comprobar
     * @return Verdadero o Falso si el número es o no primo
     */
    boolean esPrimo(long numero) {
        boolean primo= true;
        long candidatoDivisor= 2;
        while (candidatoDivisor < numero && primo) {
            try {
                Thread.sleep (0, 2);
            } catch (InterruptedException ex) {
                System.out.printf ("Error en sleep: %s.\n", ex.getMessage());
            }
            if (numero % candidatoDivisor == 0) {
                primo= false;
                divisor= candidatoDivisor;
            } else
                candidatoDivisor++;                       
        }        
        return primo;
    }

    // Una forma más óptima de llevar a cabo la comprobación
    // (el problema es que es demasiado rápida y para este ejericio interesa que sea más "lenta"
/*    boolean esPrimo(long numero) {
        boolean primo= true;
        long candidatoDivisor= 3;
        if (numero % 2 == 0) {
            primo= false;
        }
        while (candidatoDivisor < (int) Math.sqrt(numero) && !primo) {
            if (numero % candidatoDivisor == 0)
                primo= false;
            else
                candidatoDivisor +=2;                       
        }        
        return primo;
    }
*/    
    
}