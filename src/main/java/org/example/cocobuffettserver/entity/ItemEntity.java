package org.example.cocobuffettserver.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.cocobuffettserver.converter.ItemTypeConverter;
import org.example.cocobuffettserver.enums.ItemType;

@Entity
@Table(name = "tb_item")
@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class ItemEntity {

    @Id
    @Column(name = "item_id")
    String itemId;

    @Column(nullable = false)
    @Convert(converter = ItemTypeConverter.class)
    ItemType type;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    Integer price;

    @Column(nullable = false)
    String color;
}
