package ru.sbt.java.features;

import java.time.LocalDateTime;

public class UnnamedMain {
    private final String greeting = "Hi";

//    public Main(String param) {
//        System.out.println("Если в конструкторе есть параметры, нестатические методы запуска не работают, тк на самом деле вызов происходит так: new Main().main()"
//    }

    //    public static void main(String[] args) {
//        System.out.println("First priority");
//    }

//    public static void main() {
//        System.out.println("Second priority");
//    }

//    void main(String[] args) {
//        System.out.println("Third priority");
//    }

    void main() {
        //    new Main().main(); так получим stack overflow error
        System.out.println("Fourth priority");
        System.out.println(greeting);
        System.out.println(getGreetingWithDate());
    }

    String getGreetingWithDate() {
        return STR."\{greeting} \{LocalDateTime.now()}";
    }
}