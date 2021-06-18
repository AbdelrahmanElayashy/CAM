package edu.kit.stateManager.infrastructure.converter;

import edu.kit.stateManager.logic.model.state.StateNames;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class StateNameConverter implements AttributeConverter<StateNames, String> {

    @Override
    public String convertToDatabaseColumn(StateNames stateNames) {
        if (stateNames == null) {
            return null;
        }
        return stateNames.getName();
    }

    @Override
    public StateNames convertToEntityAttribute(String name) {
        if (name == null) {
            return null;
        }

        return Stream.of(StateNames.values())
                .filter(c -> c.getName().equals(name))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
