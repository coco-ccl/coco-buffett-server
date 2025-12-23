package org.example.cocobuffettserver.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "tb_stock")
@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class StockEntity {

    @Id
    @Column(name = "ticker")
    String ticker;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    Long currentPrice;

    public void updatePrice(long newPrice) {
        this.currentPrice = newPrice;
    }
}
