package aed;

public class Usuario implements Comparable<Usuario>{
    private int id;
    private int saldo;

    public Usuario(int id, int saldo){
        this.id     = id;
        this.saldo  = saldo;
    }

    @Override
    public int compareTo(Usuario otro) {
        throw new UnsupportedOperationException("Implementar!");
    }


    public int getId(){
        return id;
    }

    public int getSaldo(){
        return saldo;
    }


}
