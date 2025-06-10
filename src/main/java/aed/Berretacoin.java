package aed;

import java.util.ArrayList;

public class Berretacoin {
    private ListaEnlazada<ListaEnlazada<Transaccion>>       blockchain = new ListaEnlazada<>();
    private ListaEnlazada<Transaccion>                      ultBloque = new ListaEnlazada<>();
    private HeapSobreArrayList<ListaEnlazada<Transaccion>.Handle> heapUltBloque = new HeapSobreArrayList<>();
    private HeapSobreArrayList<Usuario>                     heapUsuarios = new HeapSobreArrayList<>();
    private ArrayList<HeapSobreArrayList<Usuario>.Handle>   refUsuarios = new ArrayList<>();


    public Berretacoin(int n_usuarios){ //O(n)
        throw new UnsupportedOperationException("Implementar!");
    }

    public void agregarBloque(Transaccion[] transacciones){
        throw new UnsupportedOperationException("Implementar!");
    }

    public Transaccion txMayorValorUltimoBloque(){
        throw new UnsupportedOperationException("Implementar!");
    }

    public Transaccion[] txUltimoBloque(){
        throw new UnsupportedOperationException("Implementar!");
    }

    public int maximoTenedor(){
        throw new UnsupportedOperationException("Implementar!");
    }

    public int montoMedioUltimoBloque(){
        throw new UnsupportedOperationException("Implementar!");
    }

    public void hackearTx(){
        throw new UnsupportedOperationException("Implementar!");
    }
}
