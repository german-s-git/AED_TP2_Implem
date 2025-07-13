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
    void variosUsuarioSumarRestarSaldo(){
        GestionUsuario gestionadorUsuarios;

        Transaccion[] transacciones = new Transaccion[] {
            new Transaccion(0, 0, 2, 10),
            new Transaccion(1, 2, 3, 4),
            new Transaccion(2, 3, 4, 1)
            /*
            * saldo usuario 2 = 6
            * saldo usuario 3 = 3
            * saldo usuario 4 = 1
            */
        };
        
        gestionadorUsuarios = new GestionUsuario(4);

        gestionadorUsuarios.actualizarSaldos(transacciones);

        assertEquals(2, gestionadorUsuarios.verMaximo()); //el usuario que mas tiene es el 2

        transacciones = new Transaccion[] {
            new Transaccion(0, 2, 3, 5),
            new Transaccion(1, 4, 3, 1)
            /*
            * saldo usuario 2 = 1
            * saldo usuario 3 = 9
            * saldo usuario 4 = 0
            */
        };

        gestionadorUsuarios.actualizarSaldos(transacciones); //actualizo saldos

        assertEquals(3, gestionadorUsuarios.verMaximo()); // ahora el usuario que mas tiene es el 3

        gestionadorUsuarios.devolverSaldo(transacciones[0]); //se "hackea" manualmente, siendo transacciones[0] la de mayor monto

        assertEquals(2, gestionadorUsuarios.verMaximo()); // volvemos a que el usuario que mas tiene es el 2
    }
}
