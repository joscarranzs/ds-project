package com.ds.core.datastructure;

import com.ds.core.services.DeliveryManager;
import java.util.List;

public class OrderHeap {
    /**
     * Almacenamiento interno del montón en orden de niveles. La raíz (máximo) está
     * en el índice 0.
     */
    private final List<Order> heap = new DeliveryManager().getOrders();

    /**
     * Construye un montón máximo vacío.
     */
    public OrderHeap() {
    }

    /**
     * Devuelve una copia de la lista interna del montón en orden de niveles.
     * Útil para inspección o dibujo del montón sin exponer la lista interna.
     *
     * @return nueva ArrayList con los elementos del montón
     */
    public List<Order> toList() {
        return new ArrayList<>(heap);
    }

    /**
     * Inserta un elemento en el montón.
     *
     * <p>Agrega el elemento al final de la lista y llama a {@link #siftUp(int)}
     * para remontarlo hacia la raíz si es mayor que sus ancestros, asegurando
     * que cada padre sea mayor o igual que sus hijos.</p>
     *
     * @param value elemento a insertar; no se valida explícitamente si es null
     */
    public void insert(Order value) {
        heap.add(value);
        siftUp(heap.size() - 1);
    }

    /**
     * Elimina y devuelve la raíz del montón (el elemento máximo).
     *
     * <p>Si el montón está vacío devuelve null. Si hay elementos, reemplaza la
     * raíz por el último elemento y aplica {@link #siftDown(int)} desde la raíz
     * para restaurar la propiedad de heap máximo.</p>
     *
     * @return el elemento máximo (raíz) o null si el montón está vacío
     */
    public Order delete() {
        if (heap.isEmpty()) return null;
        Order root = heap.get(0);
        int last = heap.size() - 1;
        if (last == 0) {
            heap.remove(last);
            return root;
        }
        heap.set(0, heap.get(last));
        heap.remove(last);
        siftDown(0);
        return root;
    }

    /**
     * Remonta el elemento en el índice proporcionado para restaurar la invariante
     * del montón máximo.
     *
     * <p>Se ejecutan primero {@link #siftUp(int)} (por si el elemento aumentó su
     * valor) y luego {@link #siftDown(int)} (por si disminuyó). De esta manera
     * la operación es segura independientemente del cambio que haya sufrido el
     * valor del elemento.</p>
     *
     * @param index índice del elemento a remontar; no hace nada si está fuera de rango
     */
    public void remont(int index) {
        if (index < 0 || index >= heap.size()) return;
        siftUp(index);
        siftDown(index);
    }

    /**
     * Devuelve el número de elementos en el montón.
     *
     * @return tamaño del montón
     */
    public int size() {
        return heap.size();
    }

    /**
     * Indica si el montón está vacío.
     *
     * @return true si no contiene elementos, false en caso contrario
     */
    public boolean isEmpty() {
        return heap.isEmpty();
    }

    /**
     * Devuelve la raíz (elemento máximo) sin eliminarla.
     *
     * @return el elemento máximo o null si el montón está vacío
     */
    public T peek() {
        return heap.isEmpty() ? null : heap.get(0);
    }

    /**
     * Sube el elemento en el índice indicado hasta que la propiedad de heap
     * máximo se cumpla respecto a sus ancestros.
     *
     * <p>Mientras el elemento sea mayor que su padre, se intercambian. Esto
     * garantiza que al terminar el método, el elemento no sea mayor que su padre
     * (o será la raíz).</p>
     *
     * @param idx índice del elemento a subir
     */
    private void siftUp(int idx) {
        int i = idx;
        while (i > 0) {
            int parent = (i - 1) / 2;
            T current = heap.get(i);
            T p = heap.get(parent);
            if (p.compareTo(current) < 0) {
                heap.set(i, p);
                heap.set(parent, current);
                i = parent;
            } else {
                break;
            }
        }
    }

    /**
     * Baja el elemento en el índice indicado hasta que la propiedad de heap máximo
     * se cumpla respecto a sus descendientes.
     *
     * <p>Selecciona el hijo mayor (izquierdo o derecho) y si este es mayor que
     * el elemento, los intercambia. Repite hasta que el elemento sea mayor o
     * igual que ambos hijos o alcance una hoja.</p>
     *
     * @param idx índice del elemento a bajar
     */
    private void siftDown(int idx) {
        int n = heap.size();
        int i = idx;
        while (true) {
            int left = 2 * i + 1;
            int right = left + 1;
            int largest = i;
            if (left < n && heap.get(left).compareTo(heap.get(largest)) > 0) largest = left;
            if (right < n && heap.get(right).compareTo(heap.get(largest)) > 0) largest = right;
            if (largest == i) break;
            T tmp = heap.get(i);
            heap.set(i, heap.get(largest));
            heap.set(largest, tmp);
            i = largest;
        }
    }
}

