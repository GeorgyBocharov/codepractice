package ru.sbt.java.features;

import java.util.*;

//jep 431
public class SequentialCollections {
    private final SequencedCollection<Integer> list = new ArrayList<>(List.of(1, 2, 3));
    void main() {
        System.out.println(STR."Original: \{list}");
        System.out.println(STR."First elem \{list.getFirst()}");
        System.out.println(STR."Last elem \{list.getLast()}");
        list.removeFirst();
        System.out.println(STR."After 1st removal: \{list}");
        list.removeLast();
        System.out.println(STR."After last removal: \{list}");
        list.addFirst(-1);
        System.out.println(STR."After -1 added first: \{list}");
        list.addLast(-3);
        System.out.println(STR."After -3 added last: \{list}");
        System.out.println(STR."Reversed: \{list.reversed()}");

        System.out.println("benefits");
        benefitToAccessLinkedHashSet();
    }

    void benefitToAccessLinkedHashSet() {
        var set = new LinkedHashSet<Integer>();
        set.add(3);
        set.add(2);
        set.add(1);
        System.out.println(set);

        //old way:
        Iterator<Integer> iterator = set.iterator();
        Integer last = null;
        while (iterator.hasNext()) {
            last = iterator.next();
        }
        System.out.println(STR."last elem oldway : \{last}");

        //now
        System.out.println(STR."last elem new way : \{set.getLast()}");
    }
}
