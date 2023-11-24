package ru.sbt.java.features;

import java.time.LocalDateTime;

import static java.util.FormatProcessor.FMT;

//preview ferature
public class StringTemplates {
    private final String name = "George";
    private final double doubleValue = 5.12345;
    private final double otherDouble = 1.4345;

    void main() {
        System.out.println(STR."My name is \{name.toUpperCase()}. I wrote this code at \{LocalDateTime.of(2023, 11, 23, 0, 45, 0)}");
        System.out.println(multilineTemplate());
        System.out.println(formattedTemplate());
    }

    private String multilineTemplate() {
        return STR
                ."""
                {
                    "name": "george",
                    "line": 1
                    "substruct: {
                            "value": 3.0
                     }
                }
                """;
    }

    private String formattedTemplate() {
        return FMT."I can print formatted data. name: %2s\{name}, value: %.2f\{doubleValue + otherDouble}, 16ричное значение: 0x%04x\{10}";
    }
}
