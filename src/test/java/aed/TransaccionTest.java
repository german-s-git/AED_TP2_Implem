package aed;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class TransaccionTest {
    @Test
    void igualdad(){
        Transaccion t1 = new Transaccion(0, 0, 1, 1);
        Transaccion t2 = new Transaccion(0, 0, 1, 1);

        assertEquals(t1, t2);
    }

    @Test
    void comparacion(){
        Transaccion t1 = new Transaccion(0, 0, 1, 1);
        Transaccion t2 = new Transaccion(1, 0, 1, 2); //monto mayor
        Transaccion t3 = new Transaccion(2, 0, 1, 1); //monto igual pero id mayor

        assertTrue(t1.compareTo(t2) < 0);
        assertTrue(t1.compareTo(t3) < 0);

    }
}
