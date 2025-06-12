
package aed;

import java.util.ArrayList;
import java.util.List;

public class HeapSobreArrayList<T extends Comparable<T>> implements Heap<T> {
    private ArrayList<Handle> arbol;

    public HeapSobreArrayList(){
        arbol = new ArrayList<>();
    }

    public T ConsultarMaximo(){     //Esto es O(1)
        T max = null;

        if(arbol.size() > 0)
            return arbol.get(0).valor;

        return max;
    }

    public Handle Agregar(T elem){    //Esto es O(log n)
        Handle h = new Handle(elem, arbol.size());
        arbol.add(h);               // O(1)
        Subir(arbol.size()-1);      // O(log n)

        return h;
    }

    private void Subir(int indice){
        while (indice > 0 && arbol.get(indice).valor.compareTo(arbol.get(Padre(indice)).valor) > 0) {
            Swap(indice, Padre(indice));
            indice = Padre(indice);
        }
    }

    private void Bajar(int indice){
        int hijoIzq = HijoIzq(indice);
        int hijoDer = HijoDer(indice);
        int mayor   = indice;

        if(indice > -1){
            if (hijoIzq < arbol.size() && arbol.get(hijoIzq).valor.compareTo(arbol.get(mayor).valor) > 0)
                mayor = hijoIzq;
            if (hijoDer < arbol.size() && arbol.get(hijoDer).valor.compareTo(arbol.get(mayor).valor) > 0)
                mayor = hijoDer;

            //si el mayor resultó ser el indice, entonces no hay que seguir bajando nada
            if (mayor != indice){
                Swap(indice, mayor);
                Bajar(mayor);
            }
        }
    }

    private void Swap(int indiceA, int indiceB){
        Handle hA = arbol.get(indiceA);
        Handle hB = arbol.get(indiceB);

        arbol.set(indiceA, hB);
        arbol.set(indiceB, hA);
        hA.indice = indiceB;
        hB.indice = indiceA;
    }

    private int Padre(int i){
        return (int) Math.floor((i-1)/2);
    }
    private int HijoIzq(int i){
        return 2*i + 1;
    }
    private int HijoDer(int i){
        return 2*i + 2;
    }

    public void SacarMaximo(){ 
        if(arbol.size() > 0){
            Handle ultimo = arbol.get(arbol.size() - 1);

            //"rompo" al primero
            arbol.get(0).valor  = null;
            arbol.get(0).indice = -1;
            arbol.set(0, ultimo);
            ultimo.indice = 0;
            arbol.remove(arbol.size() - 1);
            Bajar(0);
        }
    }

    public ArrayList<Handle> Heapify(List<T> elementos) {
        ArrayList<Handle> handlesHeapEnOrdenDeEntrada = new ArrayList<>(); 
        arbol = new ArrayList<>(elementos.size());

        //Crear handles para todos los elementos
        for (int i = 0; i < elementos.size(); i++) {
            Handle h = new Handle(elementos.get(i), i);
            arbol.add(h);
            handlesHeapEnOrdenDeEntrada.add(h);
        }

        //Empezamos desde el ultimo nodo que tiene hijos y vamos hacia arriba
        for (int i = (arbol.size() / 2) - 1; i >= 0; i--) {
            Bajar(i); //Reordenamos el subárbol con raiz en i
        }

        return handlesHeapEnOrdenDeEntrada; //PREGUNTAR
    }

    public class Handle {
        private T valor;
        private int indice;

        public Handle(T valor, int indice){
            this.valor = valor;
            this.indice = indice;
        }

        public T getValor() {
            return valor;
        }

        public int getIndice() {
            return indice;
        }

        public void setValor(T nuevoValor) {
            this.valor = nuevoValor;

            //Si tiene que subir, bajar no hace nada... y viceversa
            Subir(indice);
            Bajar(indice);
        }
    }
    
}
