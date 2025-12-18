package org.example.cocobuffettserver.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

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
    String type;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    Integer price;
}
