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
                        .withDatabase(new Database()
                                .withName("org.jooq.util.mysql.MySQLDatabase")
                                .withIncludes("user")
                                .withInputSchema("test")
                                .withOutputSchema("schema"))
                        .withTarget(new Target()
                                .withPackageName("com.example.dao")
                                .withDirectory("src/main/java")));

        GenerationTool.generate(configuration);
    }
}
