package org.example.cocobuffettserver.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "tb_member")
@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class MemberEntity {
    
    @Id
    @Column(name = "member_id")
    String memberId;
    
    @Column(nullable = false)
    String password;
    
    @Column(nullable = false)
    String nickname;

    @Column(nullable = false)
    Long balance;

    @Column
    String profileImageUrl;

    public void deductBalance(long amount) {
        this.balance -= amount;
    }

    public void addBalance(long amount) {
        this.balance += amount;
    }
}