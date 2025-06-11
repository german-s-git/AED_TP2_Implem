package aed;

public class Transaccion implements Comparable<Transaccion> {
    private int id;
    private int id_comprador;
    private int id_vendedor;
    private int monto;

    public Transaccion(int id, int id_comprador, int id_vendedor, int monto) {
        this.id = id;
        this.id_comprador = id_comprador;
        this.id_vendedor = id_vendedor;
        this.monto = monto;
    }

    @Override
    public int compareTo(Transaccion otro) {
        int comparacion = 0;

        if(this.monto != otro.monto)
            comparacion = this.monto - otro.monto;
        else
            comparacion = this.id - otro.id;

        return comparacion;
    }

    @Override
    public boolean equals(Object obj) {
        Transaccion otra = (Transaccion) obj;

        return id == otra.id &&
            id_comprador == otra.id_comprador &&
            id_vendedor == otra.id_vendedor &&
            monto == otra.monto;
    }

    public int monto() {
        return monto;
    }

    public int id_comprador() {
        return id_comprador;
    }
    
    public int id_vendedor() {
        return id_vendedor;
    }
}