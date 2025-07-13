package aed;

import java.util.ArrayList;

public class BloqueTx implements Comparable<BloqueTx> {
    private ListaEnlazada<Transaccion>                            ultBloque;
    private HeapSobreArrayList<ListaEnlazada<Transaccion>.Handle> heapUltBloque;

    private int montoMedio;
    private int sumatoriaMontos;
    private int cantTxUltBloque;
    private int cantTxSinCreacion;

    public BloqueTx(){ //O(1)
        heapUltBloque   = new HeapSobreArrayList<>();
        ultBloque       = new ListaEnlazada<>();

        montoMedio          = 0;
        sumatoriaMontos     = 0;
        cantTxUltBloque     = 0;
        cantTxSinCreacion   = 0;
    }

    public BloqueTx(Transaccion[] transacciones){   //O(2*n) -> O(n)
        ArrayList<ListaEnlazada<Transaccion>.Handle> handlesUltBloque = new ArrayList<>(transacciones.length);

        heapUltBloque   = new HeapSobreArrayList<>();
        ultBloque       = new ListaEnlazada<>();

        //como es un nuevo bloque, reseteo todas las variables
        montoMedio          = 0;
        sumatoriaMontos     = 0;
        cantTxSinCreacion   = 0;
        cantTxUltBloque     = transacciones.length;

        for(int i = 0; i < cantTxUltBloque; i++){ // O(n)
            handlesUltBloque.add(ultBloque.agregarAtras(transacciones[i]));    // O(1)

            if(transacciones[i].id_comprador() != 0){ //txActual.id_comprador()
                sumatoriaMontos += transacciones[i].monto();       //O(1)
                cantTxSinCreacion++;            //O(1)
            }
        }
        calcularMontoMedio(sumatoriaMontos, cantTxSinCreacion);
        heapUltBloque.Heapify(handlesUltBloque);    //heapificar con algoritmo floyd -> O(n)
    }

    private void calcularMontoMedio(int sum, int cant){
        if(cant != 0)
            montoMedio = sum/cant;
        else
            montoMedio = 0;
    }

    public int verMontoMedio(){
        return montoMedio;
    }

    public Transaccion txMayorValor(){ //O(1)
        if(heapUltBloque.ConsultarMaximo() != null)       
            return heapUltBloque.ConsultarMaximo().get(); //acceder a la raiz del heap -> O(1)
        else
            return null; //Si bloque esta vacio, devuelvo null
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

        if(txEliminar.id_comprador() != 0){
            cantTxSinCreacion   -= 1;
            sumatoriaMontos     -= txEliminar.monto();
        }
        cantTxUltBloque -= 1;

        calcularMontoMedio(sumatoriaMontos, cantTxSinCreacion);

        return txEliminar;
    }

    //en berretacoin no se utiliza, pero necesito que esté definido para que sea comparable
    @Override
    public int compareTo(BloqueTx otra) {
        return Integer.compare(this.ultBloque.longitud(), otra.ultBloque.longitud());
    }
}
