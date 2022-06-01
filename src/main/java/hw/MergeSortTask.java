package hw;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;

public class MergeSortTask<T extends Comparable<? super T>> extends RecursiveAction {
    private List<T> elements;

    /**
     * @param elements {@link List<T>} that should be not final
     */
    public MergeSortTask(List<T> elements) {
        this.elements = elements;
    }

    @Override
    protected void compute() {
        if (elements.size() <= 1) {
            return;
        }
        List<T> left = new ArrayList<>(elements.subList(0, elements.size() / 2));
        List<T> right = new ArrayList<>(elements.subList(elements.size() / 2, elements.size()));

        MergeSortTask<T> leftTask = new MergeSortTask<>(left);
        MergeSortTask<T> rightTask = new MergeSortTask<>(right);
        leftTask.fork();
        rightTask.compute();
        leftTask.join();

        merge(left, right);
    }

    private void merge(List<T> left, List<T> right) {
        int lSize = 0, rSize = 0;

        while (lSize < left.size() && rSize < right.size()) {
            if (left.get(lSize).compareTo(right.get(rSize)) < 0) {
                elements.set(lSize + rSize, left.get(lSize++));
            } else {
                elements.set(lSize + rSize, right.get(rSize++));
            }
        }

        while (lSize < left.size()) {
            elements.set(lSize + rSize, left.get(lSize++));
        }
        while (rSize < right.size()) {
            elements.set(lSize + rSize, right.get(rSize++));
        }
    }
}
