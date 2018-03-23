package igorilin13.com.github.main.datastructures.hash;

import java.util.function.BiFunction;
import java.util.function.Function;

class FunctionsProvider {
    static Function<Integer, Integer> divisionHash(int size) {
        return key -> Math.floorMod(key, size);
    }

    static Function<Integer, Integer> multiplicationHash(int size) {
        final double c = 0.62;
        return key -> (int) Math.floor(size * (Math.abs(key * c) % 1));
    }

    static BiFunction<Integer, Integer, Integer> linearProbing(int size) {
        Function<Integer, Integer> aux = divisionHash(size);
        return (k, i) -> (aux.apply(k) + i) % size;
    }

    static BiFunction<Integer, Integer, Integer> quadraticProbing(int size) {
        Function<Integer, Integer> aux = divisionHash(size);
        double c1 = 1;
        double c2 = 1;
        return (k, i) -> (int) (aux.apply(k) + c1 * i + c2 * i * i) % size;
    }

    static BiFunction<Integer, Integer, Integer> doubleProbing(int size) {
        Function<Integer, Integer> aux1 = divisionHash(size);
        Function<Integer, Integer> aux2 = k -> 1 + (Math.floorMod(k, size - 1));
        return (k, i) -> (aux1.apply(k) + i * aux2.apply(k)) % size;
    }
}
