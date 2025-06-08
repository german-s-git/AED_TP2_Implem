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
        int     indiceHijoIzq   = hijoIzq(indice);
        int     indiceHijoDer   = hijoDer(indice);
        int     tamanio         = arbol.size();
        boolean hayElem         = false;
        boolean hayHijoIzq      = false;
        boolean hayHijoDer      = false;

        //es info redundate pero aporta a la legibilidad del codigo
        if(indice < tamanio)
            hayElem = true;

        //si los potenciales hijos estan en una posicion mas grandes que el array...
        //quiere decir que no hay hijos
        if(indiceHijoIzq < tamanio && indiceHijoDer < tamanio){
            hayHijoIzq = true;
            hayHijoDer = true;
        }
        else if(indiceHijoIzq >= tamanio && indiceHijoDer < tamanio)
            hayHijoDer = true;
        else if(indiceHijoIzq < tamanio && indiceHijoDer >= tamanio)
            hayHijoIzq = true;

        if(hayElem && hayHijoIzq && hayHijoDer){
            T hijoIzq = arbol.get(indiceHijoIzq);
            T hijoDer = arbol.get(indiceHijoDer);
            T elemT   = arbol.get(indice);

            if(hijoIzq.compareTo(hijoDer) > 0){ //hijoIzq mayor
                if(hijoIzq.compareTo(elemT) > 0){
                    Swap(indice, indiceHijoIzq);
                    Bajar(indiceHijoIzq);
                }
            }
            else if (hijoDer.compareTo(hijoIzq) > 0){ //hijoDer mayor
                if(hijoDer.compareTo(elemT) > 0){
                    Swap(indice, indiceHijoDer);
                    Bajar(indiceHijoDer);
                }
            }
        }
        else if(hayElem && hayHijoIzq && !hayHijoDer){
            T hijoIzq = arbol.get(indiceHijoIzq);
            T elemT   = arbol.get(indice);

            if(hijoIzq.compareTo(elemT) > 0){
                Swap(indice, indiceHijoIzq);
                Bajar(indiceHijoIzq);
            }
        }
        else if(hayElem && !hayHijoIzq && hayHijoDer){
            T hijoDer = arbol.get(indiceHijoDer);
            T elemT   = arbol.get(indice);

            if(hijoDer.compareTo(elemT) > 0){
                Swap(indice, indiceHijoDer);
                Bajar(indiceHijoDer);
            }
        }
    }

    private void Swap(int indiceA, int indiceB){
        T elemA   = arbol.get(indiceA);
        T elemB   = arbol.get(indiceB);

        arbol.set(indiceA, elemB);
        arbol.set(indiceB, elemA);
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
        if (arbol.size() == 1) {
            arbol.remove(0);
        }
        else if(arbol.size() > 1){
            T ultimoElem = arbol.get(arbol.size()-1);

            arbol.remove(arbol.size()-1);
            arbol.set(0, ultimoElem);

            Bajar(0);
        }
    }

    //public void Heapify() //preguntar
}
