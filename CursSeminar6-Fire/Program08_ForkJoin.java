import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class Program08_ForkJoin {
    public static void main(String[] args) {

        final int SIZE = 100000000;
        int[] vector = new int[SIZE];
        for (int i = 0; i < vector.length; i++) {
            vector[i] = (int) (Math.random() * 10000000);
        }

        var start = System.nanoTime();

        var taskSortare = new TaskSortare(vector);
        var threadPool = new ForkJoinPool(4);
        threadPool.invoke(taskSortare);

        System.out.printf("Gata - %d ms", (System.nanoTime() - start) / 1000000);
    }

    private static class TaskSortare extends RecursiveAction {

        private int[] vector;

        TaskSortare(int[] vector) {
            this.vector = vector;
        }

        @Override
        protected void compute() {
            if (vector.length < 100) {
                java.util.Arrays.sort(vector);
            } else {

                int[] vectorA = new int[vector.length / 2];
                System.arraycopy(vector, 0, vectorA, 0, vector.length / 2);

                int[] vectorB = new int[vector.length - vector.length / 2];
                System.arraycopy(vector, vector.length / 2,
                        vectorB, 0, vectorB.length);

                invokeAll(new TaskSortare(vectorA),
                        new TaskSortare(vectorB));

                interclasare(vectorA, vectorB, vector);
            }
        }

        public static void interclasare(int[] vectorA, int[] vectorB, int[] rezultat) {
            int indexA = 0;
            int indexB = 0;
            int indexRez = 0;
            while (indexA < vectorA.length && indexB < vectorB.length) {
                if (vectorA[indexA] < vectorB[indexB]) {
                    rezultat[indexRez++] = vectorA[indexA++];
                } else {
                    rezultat[indexRez++] = vectorB[indexB++];
                }
            }
            while (indexA < vectorA.length)
                rezultat[indexRez++] = vectorA[indexA++];
            while (indexB < vectorB.length)
                rezultat[indexRez++] = vectorB[indexB++];
        }
    }
}
