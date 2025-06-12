package aed;

import java.util.ArrayList;

public class Berretacoin {
    private ListaEnlazada<ListaEnlazada<Transaccion>>       blockchain;
    private ListaEnlazada<Transaccion>                      ultBloque;
    private HeapSobreArrayList<ListaEnlazada<Transaccion>.Handle> heapUltBloque;
    private HeapSobreArrayList<Usuario>                     heapUsuarios;
    private ArrayList<HeapSobreArrayList<Usuario>.Handle>   refUsuarios;
    private int montoMedio;
    private int sumatoriaMontos;
    private int cantTxUltBloque;
    private int cantTxSinCreacion;


    public Berretacoin(int n_usuarios){ //O(n)
        blockchain      = new ListaEnlazada<>();

        heapUltBloque   = new HeapSobreArrayList<>();

        heapUsuarios    = new HeapSobreArrayList<>();
        refUsuarios     = new ArrayList<>(n_usuarios+1);

        //como queremos que el id usuario coincida con la posicion en el array, metemos al "usuario 0" pero es un null
        for(int i=0; i<=n_usuarios; i++){
            Usuario u = new Usuario(i, 0);
            if(i == 0)
                refUsuarios.add(null);
            else
                refUsuarios.add(heapUsuarios.Agregar(u));
        }
    }

    public void agregarBloque(Transaccion[] transacciones){ //O(n*log P)
        ultBloque   = new ListaEnlazada<>();
        ArrayList<ListaEnlazada<Transaccion>.Handle> handlesUltBloque = new ArrayList<>(transacciones.length);
        HeapSobreArrayList<Usuario>.Handle handleHeapUsuarios = null;
        Usuario usuarioModificarSaldo = null;
        Transaccion txActual = null;
        int monto = 0;
        int id_c  = 0; //comprador
        int id_v  = 0; //vendedor

        cantTxUltBloque = transacciones.length;

        for(int i = 0; i < cantTxUltBloque; i++){ //n*(2*logP) -> n*logP
            txActual    = transacciones[i];
            monto       = txActual.monto();
            id_c        = txActual.id_comprador();
            id_v        = txActual.id_vendedor();

            handlesUltBloque.add(ultBloque.agregarAtras(txActual));    //O(1)

            if(txActual.id_comprador() != 0){
                //refUsuarios: accedo al array de referencias que apunta al heapUsuarios,
                //.get(id_c): que me devuelve un handle del heap
                //.getValor(): accedo al valor del handle, que es de Class Usuario
                //.restarSaldo(monto): al usuario que accedí, le resto saldo
                handleHeapUsuarios      = refUsuarios.get(id_c);
                usuarioModificarSaldo   = handleHeapUsuarios.getValor();

                handleHeapUsuarios.setValor(usuarioModificarSaldo.restarSaldo(monto));  //O(log P)

                //Esto es para el monto medio
                sumatoriaMontos += monto;
                cantTxSinCreacion++;
            }

            handleHeapUsuarios      = refUsuarios.get(id_v);
            usuarioModificarSaldo   = handleHeapUsuarios.getValor();
            handleHeapUsuarios.setValor(usuarioModificarSaldo.sumarSaldo(monto));       //O(log P)
        }

        if(cantTxSinCreacion != 0)
            montoMedio = sumatoriaMontos/cantTxSinCreacion;
        else
            montoMedio = 0;

        heapUltBloque.Heapify(handlesUltBloque);    //O(n)

        blockchain.agregarAtras(ultBloque);         //O(1)

    }

    public Transaccion txMayorValorUltimoBloque(){
        return heapUltBloque.ConsultarMaximo().get();
    }

    public Transaccion[] txUltimoBloque() {
        Transaccion[] transacciones = new Transaccion[cantTxUltBloque]; //O(1)

        for (int i = 0; i < cantTxUltBloque; i++) {    //O(n)
            transacciones[i] = ultBloque.obtener(i);
        }
        return transacciones;
    }


    public int maximoTenedor(){
        return heapUsuarios.ConsultarMaximo().getId();
    }

    public int montoMedioUltimoBloque(){
        return montoMedio;
    }

    public void hackearTx(){
        ListaEnlazada<Transaccion>.Handle   handleTxEliminar    = null;
        Transaccion txEliminar = null;
        Usuario comprador   = null;
        Usuario vendedor    = null;
        int     id_c = 0;   //comprador id
        int     id_v = 0;   //vendedor id
        int     monto  = 0;

        handleTxEliminar = heapUltBloque.ConsultarMaximo();
        
        txEliminar = handleTxEliminar.get();

        monto = txEliminar.monto();
        id_c = txEliminar.id_comprador();
        id_v = txEliminar.id_vendedor();

        if(id_c != 0){
            comprador = refUsuarios.get(id_c).getValor();
            refUsuarios.get(id_c).setValor(comprador.sumarSaldo(monto));
            cantTxSinCreacion   -= 1;
            sumatoriaMontos     -= monto;
        }
        vendedor = refUsuarios.get(id_v).getValor();
        refUsuarios.get(id_v).setValor(vendedor.restarSaldo(monto));
        
        handleTxEliminar.delete();
        heapUltBloque.SacarMaximo();

        cantTxUltBloque -= 1;
        if(cantTxSinCreacion != 0)
            montoMedio = sumatoriaMontos/cantTxSinCreacion;
        else
            montoMedio = 0;
        
    }
}
