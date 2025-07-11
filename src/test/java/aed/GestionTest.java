package aed;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class GestionTest {
    @Test
    void unUsuario(){
        GestionUsuario gestionadorUsuarios;
        
        gestionadorUsuarios = new GestionUsuario(1);

        assertEquals(1, gestionadorUsuarios.verMaximo());
    }

    @Test
    void dosUsuario(){
        GestionUsuario gestionadorUsuarios;
        
        gestionadorUsuarios = new GestionUsuario(2);

        assertEquals(1, gestionadorUsuarios.verMaximo()); //tiene que ser el de menor id
    }

    @Test
    void dosUsuarioSumarSaldo(){
        GestionUsuario gestionadorUsuarios;
        
        gestionadorUsuarios = new GestionUsuario(2);

        gestionadorUsuarios.sumarSaldoUsuario(2, 10);

        assertEquals(2, gestionadorUsuarios.verMaximo()); 
    }

    @Test
    void dosUsuarioSumarRestarSaldo(){
        GestionUsuario gestionadorUsuarios;
        
        gestionadorUsuarios = new GestionUsuario(2);

        gestionadorUsuarios.sumarSaldoUsuario(2, 10);
        gestionadorUsuarios.sumarSaldoUsuario(1, 6);

        assertEquals(2, gestionadorUsuarios.verMaximo());

        gestionadorUsuarios.restarSaldoUsuario(2, 8);

        assertEquals(1, gestionadorUsuarios.verMaximo());
    }
}
