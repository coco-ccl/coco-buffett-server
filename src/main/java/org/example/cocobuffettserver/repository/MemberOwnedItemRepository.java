package org.example.cocobuffettserver.repository;

import org.example.cocobuffettserver.entity.MemberOwnedItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberOwnedItemRepository extends JpaRepository<MemberOwnedItemEntity, Long> {

    boolean existsByMember_MemberIdAndItem_ItemId(String memberId, String itemId);

    List<MemberOwnedItemEntity> findByMember_MemberId(String memberId);
}
