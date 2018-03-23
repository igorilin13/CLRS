package igorilin13.com.github.main.greedy;

import igorilin13.com.github.main.datastructures.tree.BinarySearchTree;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class HuffmanCoding {
    public static <T extends Comparable<T>> Set<ElementCode<T>> buildCodes(Set<Element<T>> elements) {
        Set<ElementWrapper<T>> wrappers = createWrappers(elements);
        ElementWrapper<T> rootWrapper = null;

        BinarySearchTree<ElementWrapper<T>> untouched = new BinarySearchTree<>(wrappers);

        while (!untouched.isEmpty()) {
            ElementWrapper<T> firstMin = untouched.pollMinKey();
            ElementWrapper<T> secondMin = untouched.pollMinKey();
            if (secondMin == null) {
                break;
            }

            int sumFrequency = firstMin.element.getFrequency() + secondMin.element.getFrequency();
            ElementWrapper<T> sumElement = new ElementWrapper<>(new Element<T>(sumFrequency));
            untouched.insert(sumElement);

            sumElement.left = firstMin;
            firstMin.parent = sumElement;

            sumElement.right = secondMin;
            secondMin.parent = sumElement;

            rootWrapper = sumElement;
        }

        return buildResultCodes(rootWrapper);
    }

    private static <T extends Comparable<T>> Set<ElementWrapper<T>> createWrappers(Set<Element<T>> elements) {
        Set<ElementWrapper<T>> result = new HashSet<>();
        for (Element<T> element : elements) {
            result.add(new ElementWrapper<>(element));
        }
        return result;
    }

    private static <T extends Comparable<T>> Set<ElementCode<T>> buildResultCodes(ElementWrapper<T> current) {
        Set<ElementCode<T>> result = new HashSet<>();
        if (current == null) {
            return result;
        }
        if (current.parent != null) {
            if (current == current.parent.left) {
                current.code.append(current.parent.code).append("0");
            } else {
                current.code.append(current.parent.code).append("1");
            }
        }
        if (current.isLeaf()) {
            result.add(new ElementCode<>(current.element, current.code.toString()));
        } else {
            result.addAll(buildResultCodes(current.left));
            result.addAll(buildResultCodes(current.right));
        }
        return result;
    }

    public static class Element<T extends Comparable<T>> implements Comparable<Element<T>> {
        private T value;
        private int frequency;

        public Element(@NotNull T value, int frequency) {
            this.value = value;
            this.frequency = frequency;
        }

        Element(int frequency) {
            this.frequency = frequency;
        }

        @Override
        public int compareTo(@NotNull Element<T> oth) {
            if (frequency > oth.getFrequency()) {
                return 1;
            } else if (frequency < oth.getFrequency()) {
                return -1;
            }
            return value.compareTo(oth.getValue());
        }

        public T getValue() {
            return value;
        }

        int getFrequency() {
            return frequency;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Element<?> element = (Element<?>) o;
            return frequency == element.frequency &&
                    Objects.equals(value, element.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(value, frequency);
        }

        @Override
        public String toString() {
            return "Element{" +
                    "value=" + value +
                    ", frequency=" + frequency +
                    '}';
        }
    }

    private static class ElementWrapper<T extends Comparable<T>> implements Comparable<ElementWrapper<T>> {
        private Element<T> element;
        private StringBuilder code;
        private ElementWrapper<T> parent;
        private ElementWrapper<T> left;
        private ElementWrapper<T> right;

        ElementWrapper(Element<T> element) {
            this.element = element;
            code = new StringBuilder();
        }

        boolean isLeaf() {
            return left == null && right == null;
        }

        @Override
        public int compareTo(@NotNull ElementWrapper<T> oth) {
            return element.compareTo(oth.element);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            ElementWrapper<?> that = (ElementWrapper<?>) o;
            return Objects.equals(element, that.element);
        }

        @Override
        public int hashCode() {
            return Objects.hash(element);
        }
    }

    public static class ElementCode<T extends Comparable<T>> {
        private Element<T> element;
        private String code;

        ElementCode(Element<T> element, String code) {
            this.element = element;
            this.code = code;
        }

        public Element<T> getElement() {
            return element;
        }

        public String getCode() {
            return code;
        }
    }
}
