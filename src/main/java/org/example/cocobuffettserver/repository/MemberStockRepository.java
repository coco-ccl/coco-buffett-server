package org.example.cocobuffettserver.repository;

import org.example.cocobuffettserver.entity.MemberStockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberStockRepository extends JpaRepository<MemberStockEntity, Long> {

    Optional<MemberStockEntity> findByMember_MemberIdAndStock_Ticker(String memberId, String ticker);

    List<MemberStockEntity> findByMember_MemberId(String memberId);
}
