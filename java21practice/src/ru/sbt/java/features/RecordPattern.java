package ru.sbt.java.features;

import static java.lang.StringTemplate.STR;

//jep 440
public class RecordPattern {


    void main() {
        Human george = new Human("George", 25);
        Human otherGeorge = new Human("George", 23);

        System.out.println(STR."George: \{george}, Other: \{otherGeorge}, checkEquals: \{george.equals(otherGeorge)}, checkHashCode: \{george.hashCode()}, \{otherGeorge.hashCode()}");
        System.out.println(STR."Jep 440 improvement: можно обращаться напрямую к полям: \{george.age}, \{george.name}");
    }

    //records появились в 14й джаве. - по сути это final класс, с final полями, с геттерами, equalsAndHashCode, с toString.
    //кроме того record не может экстендить другие классы, но может имплементить интерфейсы
    public record Human(String name, int age) {}


}
