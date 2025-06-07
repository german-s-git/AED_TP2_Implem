package aed;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class HeapTest {
    @Test
    void nuevoHeapVacio(){
        Heap<Integer> miHeap = new Heap<>();

        assertEquals(null, miHeap.ConsultarMaximo());
    }

    @Test
    void agregarUnElem(){
        Heap<Integer> miHeap = new Heap<>();

        miHeap.Agregar(5);

        assertEquals(5, miHeap.ConsultarMaximo());
    }

    @Test
    void agregarDosElem(){
        Heap<Integer> miHeap1 = new Heap<>();
        Heap<Integer> miHeap2 = new Heap<>();

        miHeap1.Agregar(5);
        miHeap1.Agregar(10);

        assertEquals(10, miHeap1.ConsultarMaximo());

        miHeap2.Agregar(10);
        miHeap2.Agregar(5);

        assertEquals(10, miHeap2.ConsultarMaximo());
    }

    @Test
    void agregarTresElem(){
        Heap<Integer> miHeap1 = new Heap<>();
        Heap<Integer> miHeap2 = new Heap<>();
        Heap<Integer> miHeap3 = new Heap<>();

        miHeap1.Agregar(10);
        miHeap1.Agregar(5);
        miHeap1.Agregar(1);

        assertEquals(10, miHeap1.ConsultarMaximo());

        miHeap2.Agregar(5);
        miHeap2.Agregar(10);
        miHeap2.Agregar(1);

        assertEquals(10, miHeap2.ConsultarMaximo());

        miHeap3.Agregar(5);
        miHeap3.Agregar(1);
        miHeap3.Agregar(10);

        assertEquals(10, miHeap3.ConsultarMaximo());
    }

    @Test
    void agregarMuchitos(){
        Heap<Integer> miHeap = new Heap<>();

        for(int i = 2; i < 1000; i++){
            int signo     = (int) Math.pow(-1, i);
            int numArmado = signo * i*2;
            int numArmadoAnt = (-1*signo) * (i-1)*2;

            miHeap.Agregar(numArmado);

            if(signo == 1)
                assertEquals(numArmado, miHeap.ConsultarMaximo());
            else
                assertEquals(numArmadoAnt, miHeap.ConsultarMaximo());

        }
    }

    @Test
    void agregarSacarUnElem(){
        Heap<Integer> miHeap = new Heap<>();

        miHeap.Agregar(5);

        assertEquals(5, miHeap.ConsultarMaximo());

        miHeap.SacarMaximo();

        assertEquals(null, miHeap.ConsultarMaximo());
    }
    
}
