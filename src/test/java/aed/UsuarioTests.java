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
        u.setSaldo(10);
        assertEquals(10, u.getSaldo());

        u.sumarSaldo(5);
        assertEquals(15, u.getSaldo());
        u.restarSaldo(2);
        assertEquals(13, u.getSaldo());
    }

    @Test
    void comparacionUsuarios(){
        Usuario u1 = new Usuario(0, 0);
        Usuario u2 = new Usuario(1, 1);

        assertTrue(u1.compareTo(u2) < 0);
        u1.setSaldo(2);
        assertTrue(u1.compareTo(u2) > 0);
        u1.setSaldo(1);
        assertTrue(u1.compareTo(u2) > 0); //pues el mayor es el de menor id

    }
}
