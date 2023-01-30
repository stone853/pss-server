package com.flong.springboot.modules.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flong.springboot.modules.entity.RoleMenu;
import com.flong.springboot.modules.mapper.RoleMenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleMenuService extends ServiceImpl<RoleMenuMapper, RoleMenu> {
        @Autowired
        RoleMenuMapper rolemenuMapper;


        @Transactional
        public boolean insertBatch (List<RoleMenu> t) {
                String roleCode = t.get(0).getRoleCode();
                //先删
                QueryWrapper<RoleMenu> q = new QueryWrapper<>();
                q.eq("role_code",roleCode);
                this.remove(q);
                //后插
                return this.saveBatch(t);
        }
}
