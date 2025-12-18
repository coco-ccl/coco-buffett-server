package org.example.cocobuffettserver.repository;

import org.example.cocobuffettserver.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberEntity, String> {
}