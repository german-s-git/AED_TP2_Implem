package aed;

import java.util.ArrayList;

public class GestionUsuario {
    private HeapSobreArrayList<Usuario>                     heapUsuarios;
    private ArrayList<HeapSobreArrayList<Usuario>.Handle>   refUsuarios;

    public GestionUsuario(int n_usuarios){ //O(3*P) -> O(P)
        ArrayList<Usuario> listaUsuarios = new ArrayList<>();
        heapUsuarios    = new HeapSobreArrayList<>();
        refUsuarios     = new ArrayList<>();

        //como queremos que el id usuario coincida con la posicion en el array, metemos al "usuario 0" pero es un null
        for(int i=1; i<=n_usuarios; i++){                   //O(P)
            Usuario u = new Usuario(i, 0);
            listaUsuarios.add(u);
        }

        refUsuarios.add(null); //esto es para que coincida la posicion en el array con el ID del usuario
        refUsuarios.addAll(heapUsuarios.Heapify(listaUsuarios)); // heapify es O(P) y concatenar es O(P) = O(2*P)
    }

    public void sumarSaldoUsuario(int id_usuario, int monto){   //O(log P)
        modificarSaldoUsuario(id_usuario, monto, 1);  //O(log P)
    }

    public void restarSaldoUsuario(int id_usuario, int monto){  //O(log P)
        modificarSaldoUsuario(id_usuario, monto, 0);  //O(log P)
    }

    private void modificarSaldoUsuario(int id_usuario, int monto, int operacion){ //O(log P)
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

    public int verMaximo(){ //O(1)
        return heapUsuarios.ConsultarMaximo().getId(); //acceder a la raiz del heap -> O(1)
    }

}
