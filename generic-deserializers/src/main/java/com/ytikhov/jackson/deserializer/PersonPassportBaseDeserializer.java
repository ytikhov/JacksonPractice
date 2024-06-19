package com.ytikhov.jackson.deserializer;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleDeserializers;
import com.ytikhov.dto.Person;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class PersonPassportBaseDeserializer extends SimpleDeserializers {
    private final Map<JavaType, StdDeserializer<? extends Person<?>>> deserializers;
    public PersonPassportBaseDeserializer() {
        deserializers = Stream.of(
                new PersonPassportDeserializer(),
                new PersonDiplomDeserializer()
        ).collect(toMap(StdDeserializer::getValueType, Function.identity()));
    }

    @Override
    public boolean hasDeserializerFor(DeserializationConfig config, Class<?> valueType) {
        return super.hasDeserializerFor(config, valueType);
    }

    @Override
    public JsonDeserializer<?> findBeanDeserializer(JavaType type, DeserializationConfig config, BeanDescription beanDesc) throws JsonMappingException {
        return deserializers.get(type);
    }
}
