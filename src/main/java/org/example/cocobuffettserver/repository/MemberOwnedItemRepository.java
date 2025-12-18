package org.example.cocobuffettserver.repository;

import org.example.cocobuffettserver.entity.MemberOwnedItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberOwnedItemRepository extends JpaRepository<MemberOwnedItemEntity, Long> {

    boolean existsByMember_MemberIdAndItem_ItemId(String memberId, String itemId);
}
