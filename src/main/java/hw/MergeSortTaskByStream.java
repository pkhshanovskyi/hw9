package hw;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class MergeSortTaskByStream<T extends Comparable<? super T>> extends RecursiveTask<List<T>> {
    private List<T> elements;

    public MergeSortTaskByStream(List<T> elements) {
        this.elements = elements;
    }

    @Override
    protected List<T> compute() {
        if (elements.size() > 1) {
            List<List<T>> lists = MergeSortTaskByStream.invokeAll(divide())
                    .stream()
                    .map(MergeSortTaskByStream::join)
                    .toList();
            return merge(lists);
        } else {
            return elements;
        }
    }

    private List<MergeSortTaskByStream<T>> divide() {
        List<MergeSortTaskByStream<T>> tasks = new ArrayList<>();
        tasks.add(new MergeSortTaskByStream<>(elements.subList(0, elements.size() / 2)));
        tasks.add(new MergeSortTaskByStream<>(elements.subList(elements.size() / 2, elements.size())));
//        tasks.get(0).fork();
//        tasks.get(1).compute();
//        tasks.get(0).join();
        return tasks;
    }

    private  List<T> merge(List<List<T>> lists){
        if (lists.size() == 2){
//            while ()
        }else if (lists.size() == 1){
            return lists.get(0);
        }

        return elements;
    }

}
