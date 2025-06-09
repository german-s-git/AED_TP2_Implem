package aed;

public class Berretacoin {
    private ListaEnlazada<ListaEnlazada<Transaccion>> blockchain = new ListaEnlazada<>();
    private HeapSobreArrayList<ListaEnlazada<Transaccion>.Handle> heapTx = new HeapSobreArrayList<>();

    public Berretacoin(int n_usuarios){
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
