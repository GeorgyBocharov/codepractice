package ru.sbt.java.features;

import java.util.concurrent.*;
import java.util.function.Supplier;

//jep 453
public class StructuredConcurrency {


    void main() throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();
        Response re = handle();
        long end = System.currentTimeMillis();
        System.out.println(STR."result: \{re}, duration: \{end - start}");
    }

    Response handleNew() throws ExecutionException, InterruptedException {
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            Supplier<String>  user  = scope.fork(() -> findUser());
            Supplier<Integer> order = scope.fork(() -> fetchOrder());

            scope.join()            // Join both subtasks
                    .throwIfFailed();  // ... and propagate errors

            // Here, both subtasks have succeeded, so compose their results
            return new Response(user.get(), order.get());
        }
    }

    Response handle() throws ExecutionException, InterruptedException {
        Future<String> user;
        Future<Integer> order;
        try (ExecutorService esvc = Executors.newFixedThreadPool(2)) {
            user = esvc.submit(this::findUser);
            order = esvc.submit(this::fetchOrder);
        }
        String theUser  = user.get();   // Join findUser
        int    theOrder = order.get();  // Join fetchOrder
        return new Response(theUser, theOrder);
    }

    String findUser() {
        System.out.println(STR."finding user. thread \{Thread.currentThread()}");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "user";
    }

    int fetchOrder() {
        System.out.println(STR."fetching order. thread \{Thread.currentThread()}");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return 5;
    }

    public record Response(String user, int order) {}

}
