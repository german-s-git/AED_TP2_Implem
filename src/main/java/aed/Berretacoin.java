package aed;

import java.util.ArrayList;

public class Berretacoin {
    private ListaEnlazada<BloqueTx>     blockchain;
    private BloqueTx                    ultimoBloque;
    private GestionUsuario              gestionadorUsuarios;

    // O(P)
    public Berretacoin(int n_usuarios){
        blockchain      = new ListaEnlazada<>();

        ultimoBloque        = new BloqueTx(); //O(1)
        gestionadorUsuarios = new GestionUsuario(n_usuarios); //O(P)
    }

    // O(n + n*logP) -> O(n*logP)
    public void agregarBloque(Transaccion[] transacciones){
        ultimoBloque = new BloqueTx(transacciones);          // O(n)

        gestionadorUsuarios.actualizarSaldos(transacciones); // O(n*logP)

        blockchain.agregarAtras(ultimoBloque);  //agregar al final de una lista enlazada -> O(1)
    }

    // O(1)
    public Transaccion txMayorValorUltimoBloque(){
        return ultimoBloque.txMayorValor(); //O(1)
    }

    // O(n)
    public Transaccion[] txUltimoBloque() {
        return ultimoBloque.toArray(); // O(n)
    }

    // O(1)
    public int maximoTenedor(){
        return gestionadorUsuarios.verMaximo(); // O(1)
    }

    // O(1)
    public int montoMedioUltimoBloque(){
        return ultimoBloque.verMontoMedio();
    }

    // O(log P + log n)
    public void hackearTx(){
        Transaccion txEliminar  = ultimoBloque.hackearTx(); // O(log n)

        gestionadorUsuarios.devolverSaldo(txEliminar);      // O(log P)
    }
}
