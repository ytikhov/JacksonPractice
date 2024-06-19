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
import com.ytikhov.dto.Diplom;
import com.ytikhov.dto.Person;

import java.io.IOException;

public class PersonDiplomDeserializer extends StdDeserializer<Person<Diplom>> {
    public PersonDiplomDeserializer() {
        this(TypeFactory
                .defaultInstance()
                .constructParametricType(Person.class, Diplom.class)
        );
    }

    protected PersonDiplomDeserializer(JavaType valueType) {
        super(valueType);
    }

    @Override
    public Person<Diplom> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        ObjectCodec codec = jsonParser.getCodec();
        JsonFactory jsonFactory = codec.getFactory();
        JsonNode tree = codec.readTree(jsonParser);
        JsonParser parser = jsonFactory.createParser(tree.toString());
        Person<Diplom> person = parser.getCodec().readValue(parser, Person.class);
        JsonNode documentNumberNode = tree.get("documentNumber");
        String documentNumber = null;
        if (documentNumberNode != null && documentNumberNode.isTextual()) {
            documentNumber = documentNumberNode.asText();
        }
        person.setDocument((documentNumber != null ? new Diplom(documentNumber) : null));
        return person;
    }
}
