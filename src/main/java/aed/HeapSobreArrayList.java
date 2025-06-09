
package aed;

import java.util.ArrayList;

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

    public void Agregar(T elem){    //Esto es O(log n)
        Handle h = new Handle(elem, arbol.size());
        arbol.add(h);               // O(1)
        Subir(arbol.size()-1);      // O(log n)
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

            arbol.get(0).valor = null;
            arbol.set(0, ultimo);
            ultimo.indice = 0;
            arbol.remove(arbol.size() - 1);
            Bajar(0);
        }
    }


    private class Handle {
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
