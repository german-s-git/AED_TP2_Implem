package aed;

import java.util.ArrayList;

public class Heap<T extends Comparable<T>> {
    private ArrayList<T> arbol;
    
    public Heap(){
        arbol = new ArrayList<>();
    }

    public T ConsultarMaximo(){     //Esto es O(1)
        T maxElem;
        if(arbol.size() == 0)
            maxElem = null;         //Esto es O(1)
        else
            maxElem = arbol.get(0); //Esto es O(1)
        return maxElem;
    }

    public void Agregar(T elem){    //Esto es O(log n)
        arbol.add(elem);            //Si no se redimensiona el array, esto es O(1)
        Subir(arbol.size()-1);      //log(n), donde n es la cant elem en el arbol
    }

    private void Subir(int indice){
        T elem      = arbol.get(indice);
        T elemPadre = arbol.get(Padre(indice));

        while (indice > 0 && elem.compareTo(elemPadre) > 0) { //log(n), donde n es la cant elem en el arbol
            Swap(indice, Padre(indice));

            indice      = Padre(indice);
            elem        = arbol.get(indice);
            elemPadre   = arbol.get(Padre(indice));            
        }
    }

    private void Bajar(int indice){
        T   elem        = arbol.get(indice);
        T   elemHijo    = null;
        int indiceHijo  = 0;
        int indiceHijoDer = 0;
        int indiceHijoIzq = 0;
        int size = arbol.size();

        indiceHijoDer = hijoDer(indice);
        indiceHijoIzq = hijoIzq(indice);

        if(indiceHijoDer >= size && indiceHijoIzq >= size)

        if(arbol.get(hijoIzq(indice)).compareTo(arbol.get(hijoDer(indice))) > 0)
            indiceHijo = hijoIzq(indice);
        else
            indiceHijo = hijoDer(indice);

        elemHijo = arbol.get(indiceHijo);

         while(indice > 0 && elem.compareTo(elemHijo) < 0){
            Swap(indice, indiceHijo);

            indice = indiceHijo;

            if(arbol.get(hijoIzq(indice)).compareTo(arbol.get(hijoDer(indice))) > 0)
                indiceHijo = hijoIzq(indice);
            else
                indiceHijo = hijoDer(indice);

            elemHijo = arbol.get(indiceHijo);
         }
    }

    private void Swap(int indiceA, int indiceB){
        T elemA   = arbol.get(indiceA);
        T elemB   = arbol.get(indiceB);

        arbol.add(indiceA, elemB);
        arbol.add(indiceB, elemA);
    }

    private int Padre(int indice){
        return (int) Math.floor( (indice-1)/2 );
    }

    private int hijoIzq(int indice){
        return 2*indice + 1;
    }

    private int hijoDer(int indice){
        return 2*indice + 2;
    }

    public void SacarMaximo(){
        if(arbol.size() > 0){
            T ultimoElem = arbol.get(arbol.size()-1);

            arbol.remove(arbol.size()-1);
            arbol.add(0, ultimoElem);

            Bajar(0);
        }
    }

    //public void Heapify() //preguntar
}
