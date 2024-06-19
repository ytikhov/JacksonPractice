package com.ytikhov.tests.deserialization;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.ytikhov.dto.Diplom;
import com.ytikhov.dto.Passport;
import com.ytikhov.dto.Person;
import com.ytikhov.jackson.deserializer.PersonPassportBaseDeserializer;
import com.ytikhov.tests.util.FileUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonDeserializationTests {
    private static final ObjectMapper om = new ObjectMapper();

    @BeforeAll
    static void init() {
        SimpleModule sm = new SimpleModule();
        sm.setDeserializers(new PersonPassportBaseDeserializer());
        om.registerModule(sm);
        om.findAndRegisterModules();
    }

    @Test
    void deserializeWithPassport() {
        String jsonString = FileUtils.readFromResources("json/person01.json");

        Person<Passport> passportPerson = null;
        try {
            JavaType passportType = TypeFactory.defaultInstance().constructParametricType(Person.class, Passport.class);
            passportPerson = om.readValue(jsonString, passportType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        assertNotNull(passportPerson);
        assertEquals("12345", passportPerson.getDocument().getNumber());
    }

    @Test
    void deserializeWithPassportAndWrongDocumentType() {
        String jsonString = FileUtils.readFromResources("json/person02.json");

        Person<Passport> passportPerson = null;
        try {
            JavaType passportType = TypeFactory.defaultInstance().constructParametricType(Person.class, Passport.class);
            passportPerson = om.readValue(jsonString, passportType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        assertNotNull(passportPerson);
        assertNull(passportPerson.getDocument());
    }

    @Test
    void deserializeWithPassportAndWrongFieldName() {
        String jsonString = FileUtils.readFromResources("json/person03.json");

        Person<Passport> passportPerson = null;
        try {
            JavaType passportType = TypeFactory.defaultInstance().constructParametricType(Person.class, Passport.class);
            passportPerson = om.readValue(jsonString, passportType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        assertNotNull(passportPerson);
        assertNull(passportPerson.getDocument());
    }

    @Test
    void deserializeWithDiplom() {
        String jsonString = FileUtils.readFromResources("json/person01.json");

        Person<Diplom> diplomPerson = null;
        try {
            JavaType diplomType = TypeFactory.defaultInstance().constructParametricType(Person.class, Diplom.class);
            diplomPerson = om.readValue(jsonString, diplomType);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        assertNotNull(diplomPerson);
        assertEquals("12345", diplomPerson.getDocument().getNumero());
    }
}
