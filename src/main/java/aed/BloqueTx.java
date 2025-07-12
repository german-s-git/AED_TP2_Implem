package aed;

import java.util.ArrayList;

public class BloqueTx implements Comparable<BloqueTx> {
    private ListaEnlazada<Transaccion>                            ultBloque;
    private HeapSobreArrayList<ListaEnlazada<Transaccion>.Handle> heapUltBloque;
    

    public BloqueTx(){
        heapUltBloque   = new HeapSobreArrayList<>();
        ultBloque       = new ListaEnlazada<>();
    }

    public BloqueTx(Transaccion[] transacciones){   //O(n)
        ArrayList<ListaEnlazada<Transaccion>.Handle> handlesUltBloque = new ArrayList<>(transacciones.length);

        heapUltBloque   = new HeapSobreArrayList<>();
        ultBloque       = new ListaEnlazada<>();

        for(int i = 0; i < transacciones.length; i++){ // O(n)
            handlesUltBloque.add(ultBloque.agregarAtras(transacciones[i]));    // O(1)
        }
        heapUltBloque.Heapify(handlesUltBloque);    //heapificar con algoritmo floyd -> O(n)
    }

    public Transaccion txMayorValor(){ //O(1)
        return heapUltBloque.ConsultarMaximo().get(); //acceder a la raiz del heap -> O(1)
    }

    public Transaccion[] toArray(){ //O(n)
        return ultBloque.toArray(Transaccion.class); // O(2*n), por un tema de Java le tengo que decir de que clase va a ser el array que tengo que crear
    }

    public Transaccion hackearTx(){ //O(log n)
        ListaEnlazada<Transaccion>.Handle handleTxEliminar = null;
        Transaccion txEliminar  = null;

        handleTxEliminar    = heapUltBloque.ConsultarMaximo(); //acceder a la raiz del heap -> O(1)
        txEliminar          = handleTxEliminar.get(); // O(1)

        handleTxEliminar.delete();   //O(1)
        heapUltBloque.SacarMaximo(); //reacomodar heap transacciones ult bloque -> O(log n)

        return txEliminar;
    }

    //en berretacoin no se utiliza, pero necesito que esté definido 
    @Override
    public int compareTo(BloqueTx otra) {
        return Integer.compare(this.ultBloque.longitud(), otra.ultBloque.longitud());
    }
}
