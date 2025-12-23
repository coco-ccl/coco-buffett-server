package org.example.cocobuffettserver.constants;

import java.util.List;

public class DefaultConstants {

    public static final Long INITIAL_BALANCE = 5000000L;

    public static final String DEFAULT_FACE_ITEM_ID = "default";
    public static final String DEFAULT_HAIR_ITEM_ID = "short_brown";
    public static final String DEFAULT_TOP_ITEM_ID = "tshirt_white";
    public static final String DEFAULT_BOTTOM_ITEM_ID = "pants_black";
    public static final String DEFAULT_SHOES_ITEM_ID = "shoes_black";

    public static final List<String> DEFAULT_ITEM_IDS = List.of(
            DEFAULT_FACE_ITEM_ID,
            DEFAULT_HAIR_ITEM_ID,
            DEFAULT_TOP_ITEM_ID,
            DEFAULT_BOTTOM_ITEM_ID,
            DEFAULT_SHOES_ITEM_ID
    );

    private DefaultConstants() {
    }
}
