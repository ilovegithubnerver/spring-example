package com.example;

import org.jooq.util.GenerationTool;
import org.jooq.util.jaxb.*;
import org.junit.Test;

public class JooqGenerator {

    @Test
    public void generate() throws Exception {
        Configuration configuration = new Configuration()
                .withJdbc(new Jdbc()
                        .withDriver("com.mysql.jdbc.Driver")
                        .withUrl("jdbc:mysql://localhost:3306/test")
                        .withUser("root")
                        .withPassword("123456"))
                .withGenerator(new Generator()
                        .withDatabase(new Database()
                                .withName("org.jooq.util.mysql.MySQLDatabase")
                                .withIncludes("job|job_param|job_history|contact")
                                .withUnsignedTypes(false)
                                .withDateAsTimestamp(false)
                                .withForcedTypes(
                                        new ForcedType().withName("BOOLEAN").withTypes("(?i:TINYINT\\(3,\\s*0\\)?)"))
                                .withInputSchema("test")
                                .withOutputSchema(""))
                        .withTarget(new Target()
                                .withPackageName("com.example.jooq_generated")
                                .withDirectory("src/main/java")));

        GenerationTool.generate(configuration);
    }
}
