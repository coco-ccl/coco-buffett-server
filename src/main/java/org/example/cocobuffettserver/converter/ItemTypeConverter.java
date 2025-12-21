package org.example.cocobuffettserver.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.example.cocobuffettserver.enums.ItemType;

@Converter(autoApply = true)
public class ItemTypeConverter implements AttributeConverter<ItemType, String> {

    @Override
    public String convertToDatabaseColumn(ItemType itemType) {
        if (itemType == null) {
            return null;
        }
        return itemType.name().toLowerCase();
    }

    @Override
    public ItemType convertToEntityAttribute(String value) {
        if (value == null) {
            return null;
        }
        return ItemType.valueOf(value.toUpperCase());
    }
}
