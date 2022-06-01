import hw.MergeSortTask;
import hw.MergeSortTaskByRecursiveTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

public class Main {

    public static void main(String[] args) {
        List<Integer> integers = new ArrayList<>(List.of(5, 2, 1, 6, 7, 3, 4, 10, 8, 9, 1));
        MergeSortTaskByRecursiveTask<Integer> integerMergeSortTask = new MergeSortTaskByRecursiveTask<>(integers);
        ForkJoinPool.commonPool().invoke(integerMergeSortTask);
        integers.forEach(System.out::println);

        List<String> strings = new ArrayList<>(List.of("L", "I", "A", "B", "D"));
        MergeSortTaskByRecursiveTask<String> stringMergeSortTask = new MergeSortTaskByRecursiveTask<>(strings);
        ForkJoinPool.commonPool().invoke(stringMergeSortTask);
        strings.forEach(System.out::println);
    }

    public static void main3(String[] args) {
        List<Integer> integers = new ArrayList<>(List.of(5, 2, 1, 6, 7, 3, 4, 10, 8, 9, 1));
        MergeSortTask<Integer> integerMergeSortTask = new MergeSortTask<>(integers);
        ForkJoinPool.commonPool().invoke(integerMergeSortTask);
        integers.forEach(System.out::println);

        List<String> strings = new ArrayList<>(List.of("L", "I", "A", "B", "D"));
        MergeSortTask<String> stringMergeSortTask = new MergeSortTask<>(strings);
        ForkJoinPool.commonPool().invoke(stringMergeSortTask);
        strings.forEach(System.out::println);
    }

    public static void main2(String[] args) {
        List<Integer> integers = List.of(5, 2, 5, 6, 1, 8, 3);
        ForkJoinFindMin<Integer> integerForkJoinFindMin = new ForkJoinFindMin<>(integers);
        System.out.println(new ForkJoinPool().invoke(integerForkJoinFindMin));

        List<String> strings = List.of("L", "I", "A", "B", "D");
        ForkJoinFindMin<String> stringForkJoinFindMin = new ForkJoinFindMin<>(strings);
        System.out.println(new ForkJoinPool().invoke(stringForkJoinFindMin));
    }


    public static void main1(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        System.out.println(forkJoinSum(1_000_000));
        System.out.println(System.currentTimeMillis() - start);
        long start2 = System.currentTimeMillis();
        System.out.println(computeSequentially(999999));
        System.out.println(System.currentTimeMillis() - start2);
    }

    public static long forkJoinSum(long n) {
        int[] numbers = getNumbers(n);
        ForkJoinTask<Long> task = new ForkJoinSumCalculator(numbers);
        return new ForkJoinPool().invoke(task);
    }

    private static int[] getNumbers(long n) {
        return new Random().ints(1, 10_000).limit(n).toArray();
    }

    private static Long computeSequentially(long n) {
        int[] numbers = getNumbers(n);
        Long result = 0L;
        for (int number : numbers) {
            result += number;
        }
        return result;
    }
}
