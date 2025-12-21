package org.example.cocobuffettserver.enums;

import lombok.Getter;

@Getter
public enum ItemType {
    FACE("face"),
    HAIR("hair"),
    TOP("top"),
    BOTTOM("bottom"),
    SHOES("shoes");

    private final String value;

    ItemType(String value) {
        this.value = value;
    }
}
