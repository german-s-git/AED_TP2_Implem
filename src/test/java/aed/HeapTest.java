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

    @Test
    void agregarSacarDosElem(){
        Heap<Integer> miHeap = new Heap<>();

        miHeap.Agregar(5);
        miHeap.Agregar(10);

        assertEquals(10, miHeap.ConsultarMaximo());
        miHeap.SacarMaximo();
        assertEquals(5, miHeap.ConsultarMaximo());
        miHeap.SacarMaximo();
        assertEquals(null, miHeap.ConsultarMaximo());
    }
    
    Integer N_ELEM = 1000;

    @Test
    void agregarSacarMuchitos(){
        Heap<Integer> miHeap = new Heap<>();

        int[] numArmado     = new int[N_ELEM];
        int[] numArmadoPos  = new int[N_ELEM/2];
        int[] numArmadoNeg  = new int[N_ELEM/2];

        for(int i = 0; i < N_ELEM; i++){
            int numArmadoAux = (int) Math.pow(-1, i) * i * 2;

            numArmado[i] = numArmadoAux;
            if (i%2 == 0)
                numArmadoPos[i/2] = numArmadoAux;
            else
                numArmadoNeg[(i-1)/2] = numArmadoAux;
        }

        for(int i = 0; i < N_ELEM; i++){
            miHeap.Agregar(numArmado[i]);

            if(i%2 == 0)
                assertEquals(numArmado[i], miHeap.ConsultarMaximo());
            else
                assertEquals(numArmado[i-1], miHeap.ConsultarMaximo());
        }

        for(int i = 0; i < N_ELEM; i++){
            int max = 0;

            miHeap.SacarMaximo();
            if(miHeap.ConsultarMaximo() != null)
                max = miHeap.ConsultarMaximo();

            if(i < (N_ELEM/2 - 1))
                assertEquals(numArmadoPos[N_ELEM/2 - i - 2], max);
            else if(i < N_ELEM - 1)
                assertEquals(numArmadoNeg[i + 1 - N_ELEM/2], max);
            else
                assertEquals(null, miHeap.ConsultarMaximo());
        }
    }
}
