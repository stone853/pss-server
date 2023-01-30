package com.flong.springboot.modules.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flong.springboot.modules.entity.RoleMenu;
import com.flong.springboot.modules.entity.UserRole;
import com.flong.springboot.modules.mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserRoleService extends ServiceImpl<UserRoleMapper, UserRole> {
        @Autowired
        UserRoleMapper userRoleMapper;


        @Transactional
        public boolean insertBatch (List<UserRole> t) {
                String userId = t.get(0).getUserId();
                //先删
                QueryWrapper<UserRole> q = new QueryWrapper<>();
                q.eq("user_id",userId);
                this.remove(q);
                //后插
                return this.saveBatch(t);
        }


}
