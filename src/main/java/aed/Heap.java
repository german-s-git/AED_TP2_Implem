package aed;

import aed.HeapSobreArrayList.Handle;

interface Heap <T extends Comparable<T>>{
    public T    ConsultarMaximo();
    public void SacarMaximo();
    public Handle Agregar(T elem);
}
