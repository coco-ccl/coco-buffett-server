package org.example.cocobuffettserver.repository;

import org.example.cocobuffettserver.entity.MemberEquippedItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberEquippedItemRepository extends JpaRepository<MemberEquippedItemEntity, String> {
}
