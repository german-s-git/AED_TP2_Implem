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

    //O(n*logP)
    public void actualizarSaldos(Transaccion[] transacciones){
        for(int i = 0; i < transacciones.length; i++){ // O(n*logP)
            actualizarSaldo(transacciones[i], false);    // O(logP)
        }
    }

    // O(2*logP) -> O(logP)
    private void actualizarSaldo(Transaccion tx, boolean reintegrarSaldo){
        int monto       = tx.monto();
        int id_c        = tx.id_comprador();
        int id_v        = tx.id_vendedor();

        //reintegrarSaldo == false -> resta al comprador (si corresponde), suma el vendedor
        //reintegrarSaldo == trues -> suma al comprador (si corresponde), resta al vendedor
        if (id_c != 0)
            modificarSaldoUsuario(id_c, monto, reintegrarSaldo);    // O(logP)

        modificarSaldoUsuario(id_v, monto, !reintegrarSaldo);       // O(logP)
    }

    //O(logP)
    public void devolverSaldo(Transaccion txDevolver){
        actualizarSaldo(txDevolver, true); // O(logP)
    }

    //O(log P)
    private void modificarSaldoUsuario(int id_usuario, int monto, boolean sumar){
        HeapSobreArrayList<Usuario>.Handle handleHeapUsuarios = null;
        Usuario usuarioModificarSaldo = null;

        //refUsuarios: accedo al array de referencias que apunta al heapUsuarios,
        //.get(id_c): que me devuelve un handle del heap
        //.getValor(): accedo al valor del handle, que es de Class Usuario
        //.restarSaldo(monto): al usuario que accedí, le resto saldo
        handleHeapUsuarios      = refUsuarios.get(id_usuario);      //acceder ArrayList -> O(1)
        usuarioModificarSaldo   = handleHeapUsuarios.getValor();    //acceder ArrayList -> O(1)
        if(sumar == true)
            handleHeapUsuarios.setValor(usuarioModificarSaldo.sumarSaldo(monto));   //reacomodar heap usuarios -> O(log P)
        else if (sumar == false)
            handleHeapUsuarios.setValor(usuarioModificarSaldo.restarSaldo(monto));  //reacomodar heap usuarios -> O(log P)
    }

    //O(1)
    public int verMaximo(){
        return heapUsuarios.ConsultarMaximo().getId(); //acceder a la raiz del heap -> O(1)
    }

}
