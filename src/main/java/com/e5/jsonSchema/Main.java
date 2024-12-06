package com.e5.jsonSchema;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import com.e5.jsonSchema.model.Group;
import com.e5.jsonSchema.model.Message;
import com.e5.jsonSchema.model.User;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;

public class Main {
    public static void main(String[] args) {

        User user1 = new User();
        user1.setId("89f8370c-04ed-4909-ba1f-b1eecb2873a8");
        user1.setName("Sathees");
        user1.setCountryCode(User.CountryCode._91);
        user1.setPhoneNumber("13459874311");

        Group group1 = new Group();
        group1.setId("89f8370c-04ed-4909-baf-b1eecb2873a8");
        group1.setName("Platform");
        group1.setUserCount(3);
        group1.setState(Group.State.PRIVATE);

        Message message1 = new Message();
        message1.setId("89f8370c-04ed-4909-ba1f-b1eecb2873a8");
        message1.setContent("Success");
        message1.setTimeAndDate("2023-10-01T14:30:00Z");
        message1.setUser(user1);

        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonString = mapper.writeValueAsString(group1);
            JsonNode node = mapper.readTree(new File("src/main/resources/Group.json"));
            JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7);
            JsonSchema jsonSchema = factory.getSchema(node);
            JsonNode jsonNode = mapper.readTree(jsonString);
            Set<ValidationMessage> errors = jsonSchema.validate(jsonNode);
            if (errors.isEmpty()) {
                System.out.println("Success");
            } else {
                for (ValidationMessage error : errors) {
                    System.out.println(error.getMessage());
                    if (error.getProperty() != null) {
                        System.out.println(error.getProperty().substring(2));
                        System.out.println(jsonNode.get(error.getProperty().substring(2)));
                    } else {
                        System.out.println(error.getInstanceLocation().toString().substring(2));
                        System.out.println(jsonNode.get(error.getInstanceLocation().toString().substring(2)));
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}