package aed;

import java.util.ArrayList;

public class GestionUsuario {
    private HeapSobreArrayList<Usuario>                     heapUsuarios;
    private ArrayList<HeapSobreArrayList<Usuario>.Handle>   refUsuarios;

    //O(3*P) -> O(P)
    public GestionUsuario(int n_usuarios){
        ArrayList<Usuario> listaUsuarios = new ArrayList<>();
        heapUsuarios    = new HeapSobreArrayList<>();
        refUsuarios     = new ArrayList<>();

        //como queremos que el id usuario coincida con la posicion en el array, metemos al "usuario 0" pero es un null
        for(int i=1; i<=n_usuarios; i++){   //O(P)
            Usuario u = new Usuario(i, 0);
            listaUsuarios.add(u);
        }

        refUsuarios.add(null); //esto es para que coincida la posicion en el array con el ID del usuario
        refUsuarios.addAll(heapUsuarios.Heapify(listaUsuarios)); // heapify es O(P) y concatenar es O(P) = O(2*P)
    }

    //O(n*(2*logP)) -> O(n*logP)
    public void actualizarSaldos(Transaccion[] transacciones){
        for(int i = 0; i < transacciones.length; i++){ // n*(logP + logP)
            int monto       = transacciones[i].monto();
            int id_c        = transacciones[i].id_comprador();
            int id_v        = transacciones[i].id_vendedor();

            if(id_c != 0){ //txActual.id_comprador()
                this.restarSaldoUsuario(id_c, monto); //O(log P)
            }
            this.sumarSaldoUsuario(id_v, monto);      //O(log P)
        }
    }

    //O(2*logP) -> O(logP)
    public void devolverSaldo(Transaccion txDevolver){
        int     id_c    = txDevolver.id_comprador();  //comprador id
        int     id_v    = txDevolver.id_vendedor();   //vendedor id
        int     monto   = txDevolver.monto();;

        if(id_c != 0){
            sumarSaldoUsuario(id_c, monto); //O(log P)
        }
        restarSaldoUsuario(id_v, monto);    //O(log P)
    }

    //O(log P)
    public void sumarSaldoUsuario(int id_usuario, int monto){
        modificarSaldoUsuario(id_usuario, monto, 1);  //O(log P)
    }

    //O(log P)
    public void restarSaldoUsuario(int id_usuario, int monto){
        modificarSaldoUsuario(id_usuario, monto, 0);  //O(log P)
    }

    //O(log P)
    private void modificarSaldoUsuario(int id_usuario, int monto, int operacion){
        HeapSobreArrayList<Usuario>.Handle handleHeapUsuarios = null;
        Usuario usuarioModificarSaldo = null;

        //refUsuarios: accedo al array de referencias que apunta al heapUsuarios,
        //.get(id_c): que me devuelve un handle del heap
        //.getValor(): accedo al valor del handle, que es de Class Usuario
        //.restarSaldo(monto): al usuario que accedí, le resto saldo
        handleHeapUsuarios      = refUsuarios.get(id_usuario);      //acceder ArrayList -> O(1)
        usuarioModificarSaldo   = handleHeapUsuarios.getValor();    //acceder ArrayList -> O(1)
        if(operacion == 1)
            handleHeapUsuarios.setValor(usuarioModificarSaldo.sumarSaldo(monto));   //reacomodar heap usuarios -> O(log P)
        else if (operacion == 0)
            handleHeapUsuarios.setValor(usuarioModificarSaldo.restarSaldo(monto));  //reacomodar heap usuarios -> O(log P)
    }

    //O(1)
    public int verMaximo(){
        return heapUsuarios.ConsultarMaximo().getId(); //acceder a la raiz del heap -> O(1)
    }

}
