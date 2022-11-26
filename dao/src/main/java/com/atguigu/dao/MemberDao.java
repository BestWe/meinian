package com.atguigu.dao;

import com.atguigu.pojo.Member;
import org.apache.ibatis.annotations.Param;

public interface MemberDao {
    Member findByTelephone(String phone);

    void add(Member newMember);

    Integer findMemberCountByMonth(String list);

    Integer findMemberCountByDay(String time);

    Integer findMemberCount();

    Integer findMemberCountByTimes(@Param("begin") String begin, @Param("end") String end);
}
