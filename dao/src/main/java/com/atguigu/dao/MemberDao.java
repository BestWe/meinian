package com.atguigu.dao;

import com.atguigu.pojo.Member;

public interface MemberDao {
    Member findByTelephone(String phone);

    void add(Member newMember);
}
