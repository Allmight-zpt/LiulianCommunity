package com.zhupeiting.bisheproject.service;

import com.zhupeiting.bisheproject.mapper.UsersMapper;
import com.zhupeiting.bisheproject.model.Users;
import com.zhupeiting.bisheproject.model.UsersExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UsersMapper usersMapper;

    public void createOrUpdate(Users user) {
        UsersExample usersExample = new UsersExample();
        usersExample.createCriteria()
                .andAccountIdEqualTo(user.getAccountId());
        List<Users> dbUser = usersMapper.selectByExample(usersExample);
        if(dbUser.size() == 0){
            //插入
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            usersMapper.insertSelective(user);
        }else{
            //更新
            Users updateUser = new Users();
            updateUser.setGmtModified(user.getGmtModified());
            updateUser.setAvatarUrl(user.getAvatarUrl());
            updateUser.setName(user.getName());
            updateUser.setToken(user.getToken());
            usersExample = new UsersExample();
            usersExample.createCriteria()
                            .andIdEqualTo(dbUser.get(0).getId());
            usersMapper.updateByExampleSelective(updateUser,usersExample);
        }
    }
}
