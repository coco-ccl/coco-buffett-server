package org.example.cocobuffettserver.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "tb_member_equipped_item")
@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class MemberEquippedItemEntity {

    @Id
    @Column(name = "member_id")
    String memberId;

    @Column(name = "face_item_id")
    String faceItemId;

    @Column(name = "hair_item_id")
    String hairItemId;

    @Column(name = "top_item_id")
    String topItemId;

    @Column(name = "bottom_item_id")
    String bottomItemId;

    @Column(name = "shoes_item_id")
    String shoesItemId;

    public void equipItem(String itemType, String itemId) {
        switch (itemType) {
            case "face":
                this.faceItemId = itemId;
                break;
            case "hair":
                this.hairItemId = itemId;
                break;
            case "top":
                this.topItemId = itemId;
                break;
            case "bottom":
                this.bottomItemId = itemId;
                break;
            case "shoes":
                this.shoesItemId = itemId;
                break;
        }
    }
}
