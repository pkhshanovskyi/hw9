import java.util.concurrent.RecursiveTask;

public class ForkJoinSumCalculator extends RecursiveTask<Long> {
    private final int[] numbers;
    private final int start;
    private final int end;
    public static final long THRESHOLD = 1_000_000;

    public ForkJoinSumCalculator(int[] numbers) {
        this(numbers, 0, numbers.length);
    }

    private ForkJoinSumCalculator(int[] numbers, int start, int end) {
        this.numbers = numbers;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        int length = end - start;
        if (length <= THRESHOLD){
            return computeSequentially();
        }
        ForkJoinSumCalculator left = new ForkJoinSumCalculator(numbers, start, start + length / 2);
        left.fork();
        ForkJoinSumCalculator right = new ForkJoinSumCalculator(numbers, start + length / 2, end);
        Long rightResult = right.compute();
        Long leftResult = left.join();
        return leftResult + rightResult;
    }

    private Long computeSequentially() {
        Long result = 0l;
        for (int i = start; i < end; i++) {
            result += numbers[i];
        }
        return result;
    }
}
