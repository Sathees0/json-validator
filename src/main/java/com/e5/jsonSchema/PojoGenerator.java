package com.e5.jsonSchema;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.jsonschema2pojo.*;
import org.jsonschema2pojo.rules.RuleFactory;

import com.sun.codemodel.JCodeModel;

public class PojoGenerator {
    public static void main(String[] args) throws IOException {
        JCodeModel codeModel = new JCodeModel();

        URL source = PojoGenerator.class.getResource("/Message.json");
        GenerationConfig config = new DefaultGenerationConfig() {
                @Override
                public boolean isGenerateBuilders() {
                    return true;
                }

                @Override
                public boolean isIncludeAdditionalProperties() {
                    return true;
                }
        };

        SchemaMapper schemaMapper = new SchemaMapper(new RuleFactory(config, new Jackson2Annotator(config), new SchemaStore()), new SchemaGenerator());

        schemaMapper.generate(codeModel, "Message", "com.e5.jsonSchema.model", source);
        codeModel.build(new File("C:/json-validator/src/main/java"));
    }

}
