package edu.kit.stateManager.infrastructure.converter;

import edu.kit.stateManager.logic.model.state.DefectPriority;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class DefectPriorityConverter implements AttributeConverter<DefectPriority, String> {

    @Override
    public String convertToDatabaseColumn(DefectPriority defectPriority) {
        if (defectPriority == null) {
            return null;
        }
        return defectPriority.getName();
    }

    @Override
    public DefectPriority convertToEntityAttribute(String name) {

        if (name == null) {
            return null;
        }

        return Stream.of(DefectPriority.values())
                .filter(c -> c.getName().equals(name))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

}
