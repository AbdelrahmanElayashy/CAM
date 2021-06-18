package edu.kit.stateManager.infrastructure.converter;

import edu.kit.stateManager.logic.model.permission.Command;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class CommandConverter implements AttributeConverter<Command, String> {
    @Override
    public String convertToDatabaseColumn(Command command) {
        if (command == null) {
            return null;
        }
        return command.toString();
    }

    @Override
    public Command convertToEntityAttribute(String command) {
        if (command == null) {
            return null;
        }

        return Stream.of(Command.values())
                .filter(c -> c.toString().equals(command))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
