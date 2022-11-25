package com.atguigu.service;

import com.atguigu.pojo.Member;

import java.util.List;

public interface MemberService {
    Member findByTelephone(String phone);

    void add(Member newMember);

    List<Integer> findMemberCountByMonth(List<String> list);
}
