package com.atguigu.service.impl;

import com.atguigu.dao.MemberDao;
import com.atguigu.dao.SetmealDao;
import com.atguigu.pojo.Member;
import com.atguigu.service.MemberService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@DubboService(interfaceClass = MemberService.class)
@Transactional
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private SetmealDao setmealDao;

    @Override
    public Member findByTelephone(String phone) {
        return memberDao.findByTelephone(phone);
    }

    @Override
    public void add(Member newMember) {
        memberDao.add(newMember);
    }

    @Override
    public List<Integer> findMemberCountByMonth(List<String> list) {
        List<Integer> counts = new ArrayList<>();
        for (String s : list) {
            Integer count = memberDao.findMemberCountByMonth(s);
            counts.add(count);
        }
        return counts;
    }

    @Override
    public List<Map<String, Object>> getSetmealCount() {
        return setmealDao.getSetmealCount();
    }
}
