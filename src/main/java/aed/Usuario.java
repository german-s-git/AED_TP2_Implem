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
        int comparacion = 0;

        if(this.saldo != otro.saldo)    //el mayor es el de saldo mayor
            comparacion = this.saldo - otro.saldo;
        else    //si hay empate de saldos, el mayor es el de menor id
            comparacion = (-1)*(this.id - otro.id);

        return comparacion;
    }


    public int getId(){
        return id;
    }

    public int getSaldo(){
        return saldo;
    }

    public void setSaldo(int saldo){
        this.saldo = saldo;
    }

    public void sumarSaldo(int saldo){
        this.saldo += saldo;
    }

    public void restarSaldo(int saldo){
        this.saldo -= saldo;
    }

}
