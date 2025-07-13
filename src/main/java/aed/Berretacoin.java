package aed;

import java.util.ArrayList;

public class Berretacoin {
    private ListaEnlazada<BloqueTx>     blockchain;
    private BloqueTx                    ultimoBloque;
    private GestionUsuario              gestionadorUsuarios;

    private int montoMedio;
    private int sumatoriaMontos;
    private int cantTxUltBloque;
    private int cantTxSinCreacion;

    public Berretacoin(int n_usuarios){ // O(P)
        blockchain      = new ListaEnlazada<>();

        montoMedio          = 0;
        sumatoriaMontos     = 0;
        cantTxUltBloque     = 0;
        cantTxSinCreacion   = 0;

        ultimoBloque        = new BloqueTx(); //O(1)
        gestionadorUsuarios = new GestionUsuario(n_usuarios); //O(P)
    }

    public void agregarBloque(Transaccion[] transacciones){ // O(n + n*logP) -> O(n*logP)
        ultimoBloque = new BloqueTx(transacciones); //O(n)

        //como es un nuevo bloque, reseteo todas las variables
        montoMedio          = 0;
        sumatoriaMontos     = 0;
        cantTxSinCreacion   = 0;

        cantTxUltBloque = transacciones.length;

        gestionadorUsuarios.actualizarSaldos(transacciones);    //O(n*logP)

        for(int i = 0; i < cantTxUltBloque; i++){ // O(n)
            int monto       = transacciones[i].monto();
            int id_c        = transacciones[i].id_comprador();

            if(id_c != 0){ //txActual.id_comprador()
                sumatoriaMontos += monto;       //O(1)
                cantTxSinCreacion++;            //O(1)
            }
        }

        if(cantTxSinCreacion != 0)
            montoMedio = sumatoriaMontos/cantTxSinCreacion;
        else
            montoMedio = 0;

        blockchain.agregarAtras(ultimoBloque);  //agregar al final de una lista enlazada -> O(1)
    }

    public Transaccion txMayorValorUltimoBloque(){ // O(1)
        return ultimoBloque.txMayorValor(); //O(1)
    }

    public Transaccion[] txUltimoBloque() { // O(n)
        return ultimoBloque.toArray(); // O(n)
    }


    public int maximoTenedor(){ // O(1)
        return gestionadorUsuarios.verMaximo(); // O(1)
    }

    public int montoMedioUltimoBloque(){ // O(1)
        return montoMedio;
    }

    public void hackearTx(){ // O(log n + 2*logP) -> O(log P + log n)
        Transaccion txEliminar  = ultimoBloque.hackearTx(); //O(log n)

        int     id_c    = txEliminar.id_comprador();  //comprador id
        int     monto   = txEliminar.monto();;

        gestionadorUsuarios.devolverSaldo(txEliminar); //O(log P)

        if(id_c != 0){
            cantTxSinCreacion   -= 1;
            sumatoriaMontos     -= monto;
        }

        cantTxUltBloque -= 1;
        if(cantTxSinCreacion != 0)
            montoMedio = sumatoriaMontos/cantTxSinCreacion;
        else
            montoMedio = 0;
    }
}
