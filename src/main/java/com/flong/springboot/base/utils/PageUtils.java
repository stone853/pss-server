package com.flong.springboot.base.utils;

import com.flong.springboot.modules.entity.dto.UserDto;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PageUtils {
    public List<String> getOptButtons (String userId,String createUser,String[] userRoles,String createUserButton,
    String checkRoleCode,String checkUserButton) {
        List<String> optButton = new ArrayList<>();
        if (StringUtils.isEmpty(createUserButton)) {
            createUserButton = "详情";
        }
        //返回基本的详情按钮
        if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(createUser)) {
            optButton.add("详情");
            return optButton;
        }

        StringBuilder sb = new StringBuilder();
        if (createUser != null && createUser.equals(userId)) { //用户操作按钮
            sb.append(createUserButton+";");
        }
        if (userRoles !=null && userRoles.length >0) {  //返回角色按钮
            List<String> roleList = Arrays.asList(userRoles);
            if (roleList.contains(checkRoleCode)) {
                sb.append(checkUserButton+";");
            }
        }
        if (StringUtils.isEmpty(sb.toString())){
            sb.append("详情");
        }

        String [] s = sb.toString().split(";");
        optButton = Arrays.asList(s);

        //非创建用户过滤 重新发起、删除、编辑
        if (!userId.equals(createUser)) {
            optButton.remove("编辑");
            optButton.remove("删除");
            optButton.remove("重新发起");
            optButton.remove("重新提交");
            optButton.remove("重新发货");
        }
        return optButton;
    }
}
