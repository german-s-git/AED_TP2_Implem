package aed;

import java.util.*;

//el "T extends Comparable<T>" es necesario por la implementación del compareTo del Handle de la ListaEnlazada
//el Comparable<ListaEnlazada<T>> es porque al hacer T comparable, como queremos hacer una listaEnlazada de listaEnlazada, listaEnlazada necesita ser comparable
public class ListaEnlazada<T extends Comparable<T>> implements Secuencia<T>, Comparable<ListaEnlazada<T>> {
    // Completar atributos privados
    private Nodo primerito;
    private Nodo ultimito;

    private class Nodo {
        T elemento;
        Nodo ant;
        Nodo sig;

        public Nodo(T e, Nodo antt, Nodo sigg){
            elemento = e;
            ant = antt;
            sig = sigg;
        }

        public Nodo(Nodo original){
            elemento = original.elemento;
            ant = null;
            sig = null;
        }
    }

    public ListaEnlazada() {
        primerito   = null;
        ultimito    = null;
    }

    public int longitud() {
        Nodo actual = primerito;
        int cant_elem = 0;

        if (!(primerito == null && ultimito == null)) {
            cant_elem = 1;
            while (actual.sig != null) {
                cant_elem++;
                actual = actual.sig;
            }
        }

        return cant_elem;
    }

    public void agregarAdelante(T elem) {
        Nodo nuevo;

        if (primerito == null && ultimito == null) {
            nuevo = new Nodo(elem, null, null);
            primerito = nuevo;
            ultimito  = nuevo;
        }
        else{
            nuevo = new Nodo(elem, null, primerito);
            primerito.ant = nuevo;
            
            primerito = nuevo;
        }
    }

    public void agregarAtras(T elem) {
        Nodo nuevo;

        if (primerito == null && ultimito == null) {
            nuevo = new Nodo(elem, null, null);
            primerito = nuevo;
            ultimito  = nuevo;
        }
        else{
            nuevo = new Nodo(elem, ultimito, null);
            ultimito.sig = nuevo;
            
            ultimito = nuevo;
        }
    }

    public T obtener(int i) {
        T   elem = null;
        Nodo actual = primerito;

        for(int numNodo = 0; numNodo < i; numNodo++)
        {
            if(actual != null)
                actual = actual.sig;
        }

        elem = actual.elemento;

        return elem;
    }

    public void eliminar(int i) {
        Nodo actual = primerito;
        Nodo nodoAnterior;
        Nodo nodoSiguiente;

        for(int numNodo = 0; numNodo < i; numNodo++)
        {
            if(actual != null)
                actual = actual.sig;
        }

        nodoAnterior    = actual.ant;
        nodoSiguiente   = actual.sig;

        if(nodoAnterior != null)
            nodoAnterior.sig    = nodoSiguiente;
        else //quiero borrar el primer elemento
            primerito   = nodoSiguiente;
        if(nodoSiguiente != null)
            nodoSiguiente.ant   = nodoAnterior;
        else //quiero borrar el ultimo elemento
            ultimito   = nodoAnterior;
    }

    public void modificarPosicion(int indice, T elem) {
        Nodo actual = primerito;
        
        //recorro mi lista
        for(int numNodo = 0; numNodo < indice; numNodo++)
        {
            if(actual != null)
                actual = actual.sig;
        }

        actual.elemento = elem;
    }

    public ListaEnlazada(ListaEnlazada<T> lista) {
        Nodo actualOriginal = lista.primerito;
        Nodo actualCopia    = null;
        Nodo copia = null;

        if (actualOriginal != null)
            actualCopia = new Nodo(actualOriginal.elemento, null, null);

        primerito   = actualCopia;
        
        while (actualOriginal.sig != null) { //hay algo más para copiar por delante
            //avanzo el "cursor" en mi lista original
            actualOriginal = actualOriginal.sig;

            //copio el elemento
            copia       = new Nodo(actualOriginal.elemento, null, null);

            //linkeo los nodos de la lista copia
            actualCopia.sig = copia;
            copia.ant    = actualCopia;

            //avanzo el "cursos" en mi lista copia
            actualCopia = actualCopia.sig;
        }

        ultimito = actualCopia;
    }
    
    @Override
    public String toString() {
        String cadena = "";
        Nodo actual = primerito;

        cadena += "[";
        while (actual != null) {
            cadena += actual.elemento;

            actual = actual.sig;

            if (actual != null)
                cadena += ", ";
        }
        cadena += "]";

        return cadena;
    }

    private class ListaIterador implements Iterador<T> {
    	// Completar atributos privados
        Nodo dedito;
        T    elemento;

        public ListaIterador() {
            dedito = new Nodo(null, null, primerito);
        }

        public boolean haySiguiente() {
	        return dedito.sig != null;
        }
        
        public boolean hayAnterior() {
	        return dedito.ant != null;
        }

        public T siguiente() {
            elemento = null;

            if(dedito.sig != null) {
                elemento = dedito.sig.elemento;

                dedito.ant  = dedito.sig;
                dedito.sig  = dedito.ant.sig;
            }

	        return elemento;
        }
        
        public T anterior() {
            elemento = null;

            if(dedito.ant != null) {
                elemento = dedito.ant.elemento;

                dedito.sig  = dedito.ant;
                dedito.ant  = dedito.sig.ant;
            }

	        return elemento;
        }
    }

    public Iterador<T> iterador() {
        /*
         * Observar que estamos devolviendo una instancia que cumple con la
         * interface "Iterador<T>" que está definida en el archivo Iterador.java
         * 
         * Retornamos ListaIterador que es el costructor de la clase (class) privada (es 
         * privada pues solo se puede acceder dentro de la clase "ListaEnlazada") que
         * implementa la interface "Iterador<T>" y es el "tipo de dato" que esta esperando
         * el test Iterador<Integer> it
         */
	    return new ListaIterador();
    }

    public class Handle implements Comparable<Handle>{
        private Nodo nodoApuntado;
        public Handle ( Nodo n ) {
            nodoApuntado = n;
        }

        public void delete(){
            Nodo nodoAnterior    = nodoApuntado.ant;
            Nodo nodoSiguiente   = nodoApuntado.sig;

            if(nodoAnterior != null)
                nodoAnterior.sig    = nodoSiguiente;
            else //quiero borrar el primer elemento
                primerito   = nodoSiguiente;
            if(nodoSiguiente != null)
                nodoSiguiente.ant   = nodoAnterior;
            else //quiero borrar el ultimo elemento
                ultimito   = nodoAnterior;
        }

        @Override
        public int compareTo(Handle otro) {
            return this.nodoApuntado.elemento.compareTo(otro.nodoApuntado.elemento);
        }
    }

    //en berretacoin no se utiliza, pero necesito que esté definido 
    @Override
    public int compareTo(ListaEnlazada<T> otra) {
        return Integer.compare(this.longitud(), otra.longitud());
    }

}
