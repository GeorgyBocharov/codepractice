package ru.sbt.java.features;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
        var DB = new QueryBuilder(connection);
        return DB."SELECT * FROM Person p WHERE p.last_name = \{name}";
    }

    record QueryBuilder(Connection conn)
            implements StringTemplate.Processor<PreparedStatement, SQLException> {

        public PreparedStatement process(StringTemplate st) throws SQLException {
            // 1. Replace StringTemplate placeholders with PreparedStatement placeholders
            String query = String.join("?", st.fragments());

            // 2. Create the PreparedStatement on the connection
            PreparedStatement ps = conn.prepareStatement(query);

            // 3. Set parameters of the PreparedStatement
            int index = 1;
            for (Object value : st.values()) {
                switch (value) {
                    case Integer i -> ps.setInt(index++, i);
                    case Float f   -> ps.setFloat(index++, f);
                    case Double d  -> ps.setDouble(index++, d);
                    case Boolean b -> ps.setBoolean(index++, b);
                    default        -> ps.setString(index++, String.valueOf(value));
                }
            }

            return ps;
        }
    }
}
