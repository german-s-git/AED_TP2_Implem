package aed;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class BloqueTxTest {
    @Test
    public void unBloque(){
        Transaccion[] transacciones = new Transaccion[] {
            new Transaccion(0, 1, 2, 1), 
        };

        BloqueTx bloque = new BloqueTx(transacciones);

        assertEquals(transacciones[0], bloque.txMayorValor());
        assertEquals(1, bloque.verMontoMedio());
        assertTrue(Arrays.equals(transacciones, bloque.toArray()));

        bloque.hackearTx();

        assertEquals(null, bloque.txMayorValor());
        assertEquals(0, bloque.verMontoMedio());
    }
 
    @Test
    public void variosBloques(){
        Transaccion[] transacciones = new Transaccion[] {
            new Transaccion(0, 1, 2, 6),
            new Transaccion(1, 2, 3, 5),
            new Transaccion(2, 3, 4, 1) 
        };

        Transaccion[] transacciones1 = new Transaccion[] {
            new Transaccion(1, 2, 3, 5),
            new Transaccion(2, 3, 4, 1) 
        };

        BloqueTx bloque = new BloqueTx(transacciones);

        assertEquals(transacciones[0], bloque.txMayorValor()); //tx de mayor monto es la de monto = 6
        assertEquals(4, bloque.verMontoMedio()); // 6+5+1 = 12/3 = 4
        assertTrue(Arrays.equals(transacciones, bloque.toArray()));

        bloque.hackearTx(); //saco la de monto = 6 ya que es la mayor

        assertEquals(transacciones[1], bloque.txMayorValor()); //la siguiente mayor es la de monto = 5
        assertEquals(3, bloque.verMontoMedio()); // 5+1 = 6/2 = 3
        assertTrue(Arrays.equals(transacciones1, bloque.toArray()));

    }
        
}
