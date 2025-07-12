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
        assertTrue(Arrays.equals(transacciones, bloque.toArray()));

        bloque.hackearTx();

        assertEquals(null, bloque.txMayorValor());
    }
 
    @Test
    public void variosBloques(){
        Transaccion[] transacciones = new Transaccion[] {
            new Transaccion(0, 0, 2, 1),
            new Transaccion(1, 2, 3, 1),
            new Transaccion(2, 3, 4, 1) 
        };

        Transaccion[] transacciones1 = new Transaccion[] {
            new Transaccion(0, 0, 2, 1),
            new Transaccion(1, 2, 3, 1)
        };

        BloqueTx bloque = new BloqueTx(transacciones);

        assertEquals(transacciones[2], bloque.txMayorValor());
        assertTrue(Arrays.equals(transacciones, bloque.toArray()));

        bloque.hackearTx();

        assertEquals(transacciones[1], bloque.txMayorValor());
        assertTrue(Arrays.equals(transacciones1, bloque.toArray()));

    }
        
}
