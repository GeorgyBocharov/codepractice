package ru.sbt.java.features;

import java.util.List;

//jep 441
public class SwitchPatternMatching {

    void main() {
        enhancedSwitch("My string");
        enhancedSwitch(12);
        enhancedSwitch(12.5);
        enhancedSwitch(List.of(1));
        enhancedSwitch(null);

        enhancedSwitchWithSealed(Suit.CLUBS);
    }

    // теперь можно вставлять в switch null, классы, sealed классы
    private static void enhancedSwitch(Object someObject) {
        switch (someObject) {
            case null -> System.out.println("OOps");
            case String s -> System.out.println(STR."string: \{s}");
            case Integer integer -> System.out.println(STR."integer \{2 + integer}");
            case Double doub -> System.out.println(STR."DOUBLE: \{doub}");
            case int[] intArr -> System.out.println("IntArray");
            default -> System.out.println(STR."DEFAULT: \{someObject}");
        }
    }

    // когда есть sealed, можно сделать case для каждого наследника и не добавлять дефолт
    // при этом switch Запрещает ставить сначала более общие кейсы, а потом конкретные (нельзя сначала сделать проверку на Suit, а потом на Suit.CLUBS)
    // интересно, что в данном случае кейс Suit s -> ... недосягаем , но компилятор не ругается и прога работает
    private static void enhancedSwitchWithSealed(CardClassification someObject) {
        switch (someObject) {
            case null -> System.out.println("OOps");
            case Suit s when  s == Suit.CLUBS -> System.out.println("clubs");  // должно быть так: case Suit.CLUBS -> ... но идея пока не вывозит
            case Suit s when  s == Suit.DIAMONDS -> System.out.println("Diamonds");
            case Suit s -> System.out.println(STR."Other suit \{s}");
            case Tarot t -> System.out.println(STR."Tarot: \{t}");
        }
    }



    //sealed классы появились в Java 15, нужны для ограничения наследников класса
    // наследники sealed класса или интерфейса должны быть final и находиться в том же пакете, что и sealed класс.
    // Если не указывать permits, любой файнал классч может наследдовать sealed
    sealed interface CardClassification permits Suit, Tarot {}
    public enum Suit implements CardClassification { CLUBS, DIAMONDS }
    final class Tarot implements CardClassification {}


    sealed class MySealedClass {}

    final class MySealedClassInheritorV1 extends MySealedClass {}

    final class MySealedClassInheritorV2 extends MySealedClass {}

    // compilation error, тк не файнал класс и не в перм
    //class MyNotFinalClass implements CardClassification {}


    // sealed классы не могут быть использованы, как функциональные интерфепйсы.
    // Анонимные классы  не могут наследовать sealed интерфейсы
    private static void sealedUsageFunc() {
//        MySealedClass mySealedClass = () -> {};
//        CardClassification cardClassification = new CardClassification() {}
    }


}
