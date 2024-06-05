package communication.client;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        System.out.println("Lancement");
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            futures.add(lanceTraitement(i));
        }
        // A -> B -> C -> D -> E
        //                     | -> Programme principal
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).get();
        System.out.println("Fin");

        //
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        for (int i = 0; i < 10; i++) {
            executorService.submit(tacheAsynchrone(i));
        }
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);

    }

    private static CompletableFuture<Void> lanceTraitement(int numeroTache) {
        CompletableFuture<Void> promise = CompletableFuture.runAsync(tacheAsynchrone(numeroTache));
        return promise;
    }

    private static Runnable tacheAsynchrone(int numeroTache) {
        return () -> {
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(3) * 1000);

                System.out.println("Tache " + numeroTache + " : J'ai terminé");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };
    }
}
