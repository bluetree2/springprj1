package com.example.prg1.member.service;

import com.example.prg1.board.Repository.BoardRepository;
import com.example.prg1.member.dto.MemberDto;
import com.example.prg1.member.dto.MemberForm;
import com.example.prg1.member.dto.MemberListInfo;
import com.example.prg1.member.entity.Member;
import com.example.prg1.member.repository.MemberRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    public void add(MemberForm data) {

        Optional<Member> db = memberRepository.findById(data.getId());

//        System.out.println("data = " + data);
        
        if (db.isEmpty()) {
            Optional<Member> byNickName = memberRepository.findByNickName(data.getNickName());
            if (byNickName.isEmpty()) {

            // 새 엔티티 객체 생성해서
            Member member = new Member();
            // data에 있는 것 entity에 옮겨 감고
            member.setId(data.getId());
            member.setPassword(data.getPassword());
            member.setNickName(data.getNickName());
            member.setInfo(data.getInfo());

            // repository.save()
            memberRepository.save(member);
            }else{
                throw new DuplicateKeyException(data.getNickName() + "는 이미 있는 별명입니다.");
            }
        }else {
            throw new DuplicateKeyException(data.getId() + "는 이미 있는 아이디입니다.");
        }
    }

    public List<MemberListInfo> list() {
        return memberRepository.findAllBy();
    }

    public MemberDto get(String id) {
        Member member = memberRepository.findById(id).get();

        MemberDto dto = new MemberDto();
        dto.setId(member.getId());
        dto.setNickName(member.getNickName());
        dto.setInfo(member.getInfo());
        dto.setCreatedAt(member.getCreatedAt());
        return dto;
    }

    public boolean remove(MemberForm data, MemberDto user) {
        if (user != null) {
            Member member = memberRepository.findById(data.getId()).get();
            if (member.getId().equals(user.getId())) {
                String dbPw = member.getPassword();
                String formPw = data.getPassword();

                if (dbPw.equals(formPw)) {
                    boardRepository.deleteByWriter(member);
                    memberRepository.delete(member);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean update(MemberForm data, MemberDto user, HttpSession session) {
        if (user != null) {
            Member member = memberRepository.findById(data.getId()).get();
            if (member.getId().equals(user.getId())) {
    // todo : if 문 수정
                String dbPw = member.getPassword();
                String formPw = data.getPassword();

                if (dbPw.equals(formPw)) {
                    member.setNickName(data.getNickName());
                    member.setInfo(data.getInfo());

                    memberRepository.save(member);

                    // memberDto를 session 에 넣기
                    addUserToSession(session, member);

                    return true;
                }
            }
        }
        return false;
    }

    private static void addUserToSession(HttpSession session, Member member) {
        MemberDto dto = new MemberDto();
        dto.setId(member.getId());
        dto.setNickName(member.getNickName());
        dto.setInfo(member.getInfo());
        dto.setCreatedAt(member.getCreatedAt());

        session.setAttribute("loggedInUser", dto);
    }



    public boolean updatePassword(String id, String oldPw, String newPw) {
        Member db = memberRepository.findById(id).get();

        String dbPw = db.getPassword();

        if (dbPw.equals(oldPw)) {
            db.setPassword(newPw);
            memberRepository.save(db);

            return true;
        }else {
            return false;
        }
    }

    public boolean login(String id, String password, HttpSession session) {
        Optional<Member> db = memberRepository.findById(id);
// todo  : 확인
        if (db.isPresent()) {
            String dbPassword = db.get().getPassword();
            if (dbPassword.equals(password)) {

                // memberDto를 session 에 넣기
                addUserToSession(session,db.get());
                return true;
            }
        }

        return false;
    }
}
