package aed;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class HeapTest {
    @Test
    void nuevoHeapVacio(){
        HeapSobreArrayList<Integer> miHeap = new HeapSobreArrayList<>();

        assertEquals(null, miHeap.ConsultarMaximo());
    }

    @Test
    void agregarUnElem(){
        HeapSobreArrayList<Integer> miHeap = new HeapSobreArrayList<>();

        miHeap.Agregar(5);

        assertEquals(5, miHeap.ConsultarMaximo());
    }

    @Test
    void agregarDosElem(){
        HeapSobreArrayList<Integer> miHeap1 = new HeapSobreArrayList<>();
        HeapSobreArrayList<Integer> miHeap2 = new HeapSobreArrayList<>();

        miHeap1.Agregar(5);
        miHeap1.Agregar(10);

        assertEquals(10, miHeap1.ConsultarMaximo());

        miHeap2.Agregar(10);
        miHeap2.Agregar(5);

        assertEquals(10, miHeap2.ConsultarMaximo());
    }

    @Test
    void agregarTresElem(){
        HeapSobreArrayList<Integer> miHeap1 = new HeapSobreArrayList<>();
        HeapSobreArrayList<Integer> miHeap2 = new HeapSobreArrayList<>();
        HeapSobreArrayList<Integer> miHeap3 = new HeapSobreArrayList<>();

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
        HeapSobreArrayList<Integer> miHeap = new HeapSobreArrayList<>();

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
        HeapSobreArrayList<Integer> miHeap = new HeapSobreArrayList<>();

        miHeap.Agregar(5);

        assertEquals(5, miHeap.ConsultarMaximo());

        miHeap.SacarMaximo();

        assertEquals(null, miHeap.ConsultarMaximo());
    }

    @Test
    void agregarSacarDosElem(){
        HeapSobreArrayList<Integer> miHeap = new HeapSobreArrayList<>();

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
        HeapSobreArrayList<Integer> miHeap = new HeapSobreArrayList<>();

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

    @Test
    void handlePocos(){
        HeapSobreArrayList<Integer> miHeap = new HeapSobreArrayList<>();
        ArrayList<HeapSobreArrayList<Integer>.Handle> referencias = new ArrayList<>(7);

        /*
         *      10
         *    8    7
         *   3 2  1 4
         */
        referencias.add(miHeap.Agregar(10));
        referencias.add(miHeap.Agregar(8));
        referencias.add(miHeap.Agregar(7));
        referencias.add(miHeap.Agregar(3));
        referencias.add(miHeap.Agregar(2));
        referencias.add(miHeap.Agregar(1));
        referencias.add(miHeap.Agregar(4));

        assertEquals(10, miHeap.ConsultarMaximo());
        referencias.get(1).setValor(12);
        assertEquals(12, miHeap.ConsultarMaximo());
        referencias.get(1).setValor(8);
        assertEquals(10, miHeap.ConsultarMaximo());

        assertEquals(4, referencias.get(6).getValor());
        assertEquals(6, referencias.get(6).getIndice());
        miHeap.SacarMaximo();
        assertEquals(null, referencias.get(0).getValor());
        assertEquals(-1, referencias.get(0).getIndice());
        assertEquals(4, referencias.get(6).getValor());
        assertEquals(1, referencias.get(6).getIndice());

        assertEquals(2, referencias.get(4).getValor());
        referencias.get(4).setValor(100);
        assertEquals(100, miHeap.ConsultarMaximo());
        referencias.get(4).setValor(6);
        assertEquals(8, miHeap.ConsultarMaximo());

        miHeap.SacarMaximo();
        assertEquals(7, miHeap.ConsultarMaximo());
        miHeap.SacarMaximo();
        assertEquals(6, miHeap.ConsultarMaximo());

    }

    @Test
    void heapificar(){
        List<Integer> listaNumeros = Arrays.asList(2, 1, 3, 5, 4);
        HeapSobreArrayList<Integer> miHeap = new HeapSobreArrayList<>();

        miHeap.Heapify(listaNumeros);

        assertEquals(5, miHeap.ConsultarMaximo());
        miHeap.SacarMaximo();
        assertEquals(4, miHeap.ConsultarMaximo());
        miHeap.SacarMaximo();
        assertEquals(3, miHeap.ConsultarMaximo());
        miHeap.SacarMaximo();
        assertEquals(2, miHeap.ConsultarMaximo());
        miHeap.SacarMaximo();
        assertEquals(1, miHeap.ConsultarMaximo());
        miHeap.SacarMaximo();
        assertEquals(null, miHeap.ConsultarMaximo());
    }
}
