package com.example;

import org.jooq.util.GenerationTool;
import org.jooq.util.jaxb.*;
import org.junit.Test;

public class JooqGenerator {

    @Test
    public void test() throws Exception {
        Configuration configuration = new Configuration()
                .withJdbc(new Jdbc()
                        .withDriver("com.mysql.jdbc.Driver")
                        .withUrl("jdbc:mysql://localhost:3306/test")
                        .withUser("root")
                        .withPassword("123456"))
                .withGenerator(new Generator()
                        .withGenerate(new Generate()
                                .withImmutablePojos(true))
                        .withDatabase(new Database()
                                .withName("org.jooq.util.mysql.MySQLDatabase")
                                .withIncludes("user")
                                .withUnsignedTypes(false)
                                .withDateAsTimestamp(false)
                                .withForcedTypes(
                                        new ForcedType().withName("BOOLEAN").withTypes("(?i:TINYINT\\(3,\\s*0\\)?)"),
                                        new ForcedType().withName("DATE").withTypes("(?i:DATE|TIME|DATETIME|TIMESTAMP?)"))
                                .withInputSchema("test")
                                .withOutputSchema("schema"))
                        .withTarget(new Target()
                                .withPackageName("com.example.dao")
                                .withDirectory("src/main/java")));

        GenerationTool.generate(configuration);
    }
}
