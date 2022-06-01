package hw;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class MergeSortTaskByRecursiveTask<T extends Comparable<? super T>> extends RecursiveTask<List<T>> {
    private List<T> elements;

    public MergeSortTaskByRecursiveTask(List<T> elements) {
        this.elements = elements;
    }

    @Override
    protected List<T> compute() {
        if (elements.size() > 1) {
            MergeSortTaskByRecursiveTask<T> leftTask = new MergeSortTaskByRecursiveTask<>(new ArrayList<>(elements.subList(0, elements.size()/2)));
            MergeSortTaskByRecursiveTask<T> rightTask = new MergeSortTaskByRecursiveTask<>(new ArrayList<>(elements.subList(elements.size()/2, elements.size())));
            leftTask.fork();
            return merge(leftTask.join(), rightTask.compute());
        }
        return elements;
    }

    private List<T> merge(List<T> left, List<T> right) {
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
        return elements;
    }
}
