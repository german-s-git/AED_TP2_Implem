package aed;

import java.util.ArrayList;

public class Berretacoin {
    private ListaEnlazada<ListaEnlazada<Transaccion>>       blockchain;
    private ListaEnlazada<Transaccion>                      ultBloque;
    private HeapSobreArrayList<ListaEnlazada<Transaccion>.Handle> heapUltBloque;
    private GestionUsuario                                  gestionadorUsuarios;
    //private HeapSobreArrayList<Usuario>                     heapUsuarios;
    //private ArrayList<HeapSobreArrayList<Usuario>.Handle>   refUsuarios;
    private int montoMedio;
    private int sumatoriaMontos;
    private int cantTxUltBloque;
    private int cantTxSinCreacion;


    public Berretacoin(int n_usuarios){ // 3*P -> O(P)
        blockchain      = new ListaEnlazada<>();

        heapUltBloque   = new HeapSobreArrayList<>();

        /*
        ArrayList<Usuario> listaUsuarios = new ArrayList<>();
        heapUsuarios    = new HeapSobreArrayList<>();
        refUsuarios     = new ArrayList<>();
        */

        montoMedio          = 0;
        sumatoriaMontos     = 0;
        cantTxUltBloque     = 0;
        cantTxSinCreacion   = 0;

        /*
        //como queremos que el id usuario coincida con la posicion en el array, metemos al "usuario 0" pero es un null
        for(int i=1; i<=n_usuarios; i++){                   //O(P)
            Usuario u = new Usuario(i, 0);
            listaUsuarios.add(u);
        }
        */

        /*
        refUsuarios.add(null); //esto es para que coincida la posicion en el array con el ID del usuario
        refUsuarios.addAll(heapUsuarios.Heapify(listaUsuarios)); // heapify es O(P) y concatenar es O(P) = O(2*P)
        */
        gestionadorUsuarios = new GestionUsuario(n_usuarios);
    }

    public void agregarBloque(Transaccion[] transacciones){ // O(n*logP + n) -> O(n*logP)
        ultBloque   = new ListaEnlazada<>();
        ArrayList<ListaEnlazada<Transaccion>.Handle> handlesUltBloque = new ArrayList<>(transacciones.length);
        //HeapSobreArrayList<Usuario>.Handle handleHeapUsuarios = null;
        //Usuario usuarioModificarSaldo = null;
        Transaccion txActual = null;
        int monto = 0;
        int id_c  = 0; //comprador
        int id_v  = 0; //vendedor

        //como es un nuevo bloque, reseteo todas las variables (algunas no hace falta resetearlas pero se hace a modo de prolijidad)
        montoMedio          = 0;
        sumatoriaMontos     = 0;
        cantTxUltBloque     = 0;
        cantTxSinCreacion   = 0;

        cantTxUltBloque = transacciones.length;

        for(int i = 0; i < cantTxUltBloque; i++){ // n*(2*logP) -> n*logP
            txActual    = transacciones[i];
            monto       = txActual.monto();
            id_c        = txActual.id_comprador();
            id_v        = txActual.id_vendedor();

            handlesUltBloque.add(ultBloque.agregarAtras(txActual));    // O(1)

            if(txActual.id_comprador() != 0){
                //refUsuarios: accedo al array de referencias que apunta al heapUsuarios,
                //.get(id_c): que me devuelve un handle del heap
                //.getValor(): accedo al valor del handle, que es de Class Usuario
                //.restarSaldo(monto): al usuario que accedí, le resto saldo
                /*
                handleHeapUsuarios      = refUsuarios.get(id_c);
                usuarioModificarSaldo   = handleHeapUsuarios.getValor();

                handleHeapUsuarios.setValor(usuarioModificarSaldo.restarSaldo(monto));  //reacomodar heap usuarios -> O(log P)
                */

                gestionadorUsuarios.restarSaldoUsuario(id_c, monto);

                //Esto es para el monto medio
                sumatoriaMontos += monto;       //O(1)
                cantTxSinCreacion++;            //O(1)
                
            }

            /*
            handleHeapUsuarios      = refUsuarios.get(id_v);            //acceder ArrayList -> O(1)
            usuarioModificarSaldo   = handleHeapUsuarios.getValor();    //acceder ArrayList -> O(1)
            handleHeapUsuarios.setValor(usuarioModificarSaldo.sumarSaldo(monto));   //reacomodar heap usuarios -> O(log P)
            */
            gestionadorUsuarios.sumarSaldoUsuario(id_v, monto);
        }

        if(cantTxSinCreacion != 0)
            montoMedio = sumatoriaMontos/cantTxSinCreacion;
        else
            montoMedio = 0;

        heapUltBloque.Heapify(handlesUltBloque);    //heapificar con algoritmo floyd -> O(n)

        blockchain.agregarAtras(ultBloque);         //agregar al final de una lista enlazada -> O(1)

    }

    public Transaccion txMayorValorUltimoBloque(){ // O(1)
        return heapUltBloque.ConsultarMaximo().get(); //acceder a la raiz del heap -> O(1)
    }

    public Transaccion[] txUltimoBloque() { // O(n)
        return ultBloque.toArray(Transaccion.class); // O(2*n), por un tema de Java le tengo que decir de que clase va a ser el array que tengo que crear
    }


    public int maximoTenedor(){ // O(1)
        return gestionadorUsuarios.verMaximo(); // O(1)
    }

    public int montoMedioUltimoBloque(){ // O(1)
        return montoMedio;
    }

    public void hackearTx(){ // O(2*logP + log n) -> O(log P + log n)
        ListaEnlazada<Transaccion>.Handle handleTxEliminar = null;
        Transaccion txEliminar  = null;
        Usuario comprador       = null;
        Usuario vendedor        = null;
        int     id_c    = 0;   //comprador id
        int     id_v    = 0;   //vendedor id
        int     monto   = 0;

        handleTxEliminar    = heapUltBloque.ConsultarMaximo(); //acceder a la raiz del heap -> O(1)
        
        txEliminar          = handleTxEliminar.get(); // O(1)

        monto   = txEliminar.monto();
        id_c    = txEliminar.id_comprador();
        id_v    = txEliminar.id_vendedor();

        if(id_c != 0){
            /*
            comprador = refUsuarios.get(id_c).getValor();
            refUsuarios.get(id_c).setValor(comprador.sumarSaldo(monto)); //reacomodar heap usuarios -> O(log P)
            */
            gestionadorUsuarios.sumarSaldoUsuario(id_c, monto);
            cantTxSinCreacion   -= 1;
            sumatoriaMontos     -= monto;
        }
        /*
        vendedor = refUsuarios.get(id_v).getValor();
        refUsuarios.get(id_v).setValor(vendedor.restarSaldo(monto)); //reacomodar heap usuarios -> O(log P)
        */
        gestionadorUsuarios.restarSaldoUsuario(id_v, monto);
        
        handleTxEliminar.delete();   //O(1)
        heapUltBloque.SacarMaximo(); //reacomodar heap transacciones ult bloque -> O(log n)

        cantTxUltBloque -= 1;
        if(cantTxSinCreacion != 0)
            montoMedio = sumatoriaMontos/cantTxSinCreacion;
        else
            montoMedio = 0;
        
    }
}
