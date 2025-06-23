package com.example.prg1.member.repository;

import com.example.prg1.member.dto.MemberListInfo;
import com.example.prg1.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {
    Optional<Member> findByNickName(String nickName);

    List<MemberListInfo> findAllBy();
}