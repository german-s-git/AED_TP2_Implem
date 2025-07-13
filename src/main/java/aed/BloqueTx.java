package aed;

import java.util.ArrayList;

public class BloqueTx implements Comparable<BloqueTx> {
    private ListaEnlazada<Transaccion>                            ultBloque;
    private HeapSobreArrayList<ListaEnlazada<Transaccion>.Handle> heapUltBloque;

    private int montoMedio;
    private int sumatoriaMontos;
    private int cantTxUltBloque;
    private int cantTxSinCreacion;

    //O(1)
    public BloqueTx(){
        heapUltBloque   = new HeapSobreArrayList<>();
        ultBloque       = new ListaEnlazada<>();

        montoMedio          = 0;
        sumatoriaMontos     = 0;
        cantTxUltBloque     = 0;
        cantTxSinCreacion   = 0;
    }

    //O(2*n) -> O(n)
    public BloqueTx(Transaccion[] transacciones){
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

            if(transacciones[i].id_comprador() != 0){
                sumatoriaMontos += transacciones[i].monto();    //O(1)
                cantTxSinCreacion++;                            //O(1)
            }
        }
        calcularMontoMedio(sumatoriaMontos, cantTxSinCreacion); //O(1)
        heapUltBloque.Heapify(handlesUltBloque);    //heapificar con algoritmo floyd -> O(n)
    }

    //O(1)
    private void calcularMontoMedio(int sum, int cant){
        if(cant != 0)
            montoMedio = sum/cant;  //O(1)
        else
            montoMedio = 0;         //O(1)
    }

    //O(1)
    public int verMontoMedio(){
        return montoMedio;
    }

    //O(1)
    public Transaccion txMayorValor(){
        if(heapUltBloque.ConsultarMaximo() != null)       
            return heapUltBloque.ConsultarMaximo().get(); //acceder a la raiz del heap -> O(1)
        else
            return null; //Si bloque esta vacio, devuelvo null -> O(1)
    }

    //O(2*n) -> O(n)
    public Transaccion[] toArray(){
        return ultBloque.toArray(Transaccion.class); // O(2*n), por un tema de Java le tengo que decir de que clase va a ser el array que tengo que crear
    }

    //O(log n)
    public Transaccion hackearTx(){
        ListaEnlazada<Transaccion>.Handle   handleTxEliminar = null;
        Transaccion                         txEliminar       = null;

        handleTxEliminar    = heapUltBloque.ConsultarMaximo();  //acceder a la raiz del heap -> O(1)
        txEliminar          = handleTxEliminar.get();           // O(1)

        handleTxEliminar.delete();   // O(1)
        heapUltBloque.SacarMaximo(); // reacomodar heap transacciones ult bloque -> O(log n)

        if(txEliminar.id_comprador() != 0){
            cantTxSinCreacion   -= 1; // O(1)
            sumatoriaMontos     -= txEliminar.monto(); // O(1)
        }
        cantTxUltBloque -= 1; // O(1)

        calcularMontoMedio(sumatoriaMontos, cantTxSinCreacion); // O(1)

        return txEliminar;
    }

    //en berretacoin no se utiliza, pero necesito que esté definido para que sea de tipo comparable
    @Override
    public int compareTo(BloqueTx otra) {
        return Integer.compare(this.ultBloque.longitud(), otra.ultBloque.longitud());
    }
}
