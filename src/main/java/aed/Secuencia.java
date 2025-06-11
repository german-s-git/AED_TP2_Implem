package aed;

import aed.ListaEnlazada.Handle;

interface Secuencia<T> {

    /**
     * Devuelve el largo de la secuencia.
     * 
     */
    public int longitud();

    /**
     * Agrega un elemento al principio de la secuencia.
     * 
     */
    public Handle agregarAdelante(T elem);

    /**
     * Agrega un elemento al final de la secuencia.
     * 
     */
    public Handle agregarAtras(T elem);

    /**
     * Retorna el elemento en la i-esima posicion.
     * 
     */
    public T obtener(int indice);

    /**
     * Elimina el elemento en la i-esima posicion de la secuencia.
     * 
     */
    public void eliminar(int indice);

    /**
     * Cambia el valor de la i-esima posicion
     * por el valor dado por parametro.
     * 
     */
    public void modificarPosicion(int indice, T valor);

}