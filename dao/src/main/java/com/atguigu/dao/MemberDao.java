package com.atguigu.dao;

import com.atguigu.pojo.Member;

import java.util.List;

public interface MemberDao {
    Member findByTelephone(String phone);

    void add(Member newMember);

    Integer findMemberCountByMonth(String list);
}
