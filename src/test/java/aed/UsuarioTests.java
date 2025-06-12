package aed;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class UsuarioTests {
    @Test
    void nuevoUsuario(){
        Usuario u = new Usuario(0, 0);

        assertEquals(0, u.getId());
        assertEquals(0, u.getSaldo());
    }

    @Test
    void modificarSaldo(){
        Usuario u = new Usuario(0, 0);

        assertEquals(0, u.getSaldo());

        u = u.sumarSaldo(5);
        assertEquals(5, u.getSaldo());
        u = u.restarSaldo(2);
        assertEquals(3, u.getSaldo());
    }

    @Test
    void comparacionUsuarios(){
        Usuario u1 = new Usuario(0, 0);
        Usuario u2 = new Usuario(1, 1);

        assertTrue(u1.compareTo(u2) < 0);
        u1 = u1.sumarSaldo(2);
        assertTrue(u1.compareTo(u2) > 0);
        u1 = u1.restarSaldo(1);
        assertTrue(u1.compareTo(u2) > 0); //pues el mayor es el de menor id

    }
}
