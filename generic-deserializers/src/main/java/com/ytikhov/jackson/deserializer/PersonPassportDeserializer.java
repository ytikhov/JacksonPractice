package com.ytikhov.jackson.deserializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.ytikhov.dto.Passport;
import com.ytikhov.dto.Person;

import java.io.IOException;

public class PersonPassportDeserializer extends StdDeserializer<Person<Passport>> {
    public PersonPassportDeserializer() {
        this(TypeFactory
                .defaultInstance()
                .constructParametricType(Person.class, Passport.class)
        );
    }

    protected PersonPassportDeserializer(JavaType valueType) {
        super(valueType);
    }

    @Override
    public Person<Passport> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        ObjectCodec codec = jsonParser.getCodec();
        JsonFactory jsonFactory = codec.getFactory();
        JsonNode tree = codec.readTree(jsonParser);
        JsonParser parser = jsonFactory.createParser(tree.toString());
        Person<Passport> person = parser.getCodec().readValue(parser, Person.class);
        JsonNode documentNumberNode = tree.get("documentNumber");
        String documentNumber = null;
        if (documentNumberNode != null && documentNumberNode.isTextual()) {
            documentNumber = documentNumberNode.asText();
        }
        person.setDocument((documentNumber != null ? new Passport(documentNumber) : null));
        return person;
    }
}
