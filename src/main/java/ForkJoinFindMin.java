import java.util.List;
import java.util.concurrent.RecursiveTask;

public class ForkJoinFindMin<T extends Comparable<? super T>> extends RecursiveTask<T> {
    private final List<T> elements;

    public ForkJoinFindMin(List<T> elements) {
        this.elements = elements;
    }


    @Override
    protected T compute() {
        if (elements.size() == 2) {
            return compareAndMin(elements.get(0), elements.get(1));
        }else if (elements.size() == 1){
            return elements.get(0);
        }
        ForkJoinFindMin<T> left = new ForkJoinFindMin<>(elements.subList(0, elements.size()/2));
        left.fork();
        ForkJoinFindMin<T> right = new ForkJoinFindMin<>(elements.subList(elements.size()/2, elements.size()));
        T rightResult = right.compute();
        T leftResult = left.join();
        return rightResult.compareTo(leftResult) < 0 ? rightResult : leftResult;
    }

    private T compareAndMin(T t1, T t2){
        return t1.compareTo(t2) < 0 ? t1 : t2;
    }

}
