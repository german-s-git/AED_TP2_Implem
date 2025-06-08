package aed;

interface Heap <T extends Comparable<T>>{
    public T    ConsultarMaximo();
    public void SacarMaximo();
    public void Agregar(T elem);
}
