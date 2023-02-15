package com.flong.springboot.modules.service;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flong.springboot.core.exception.BaseException;
import com.flong.springboot.core.exception.CommMsgCode;
import com.flong.springboot.core.exception.ServiceException;
import com.flong.springboot.modules.entity.Dept;
import com.flong.springboot.modules.entity.MaterialDetailSend;
import com.flong.springboot.modules.entity.User;
import com.flong.springboot.modules.mapper.DeptMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeptService extends ServiceImpl<DeptMapper, Dept> {

        @Autowired
        DeptMapper deptMapper;

        @Autowired
        UserService userService;


        public Dept getDeptById (String DeptId){
            return deptMapper.selectById(DeptId);
        }

            public int insert(Dept dept) {
                if (dept.getParentId() == null) {
                    dept.setParentId(0L);
                }
                //如果插入的当前节点为根节点，parentId指定为0

                if(dept.getParentId().longValue() == 0){

                    dept.setLevel(1);//根节点层级为1

                    dept.setPath(null);//根节点路径为空

                }else{

                    Dept parentDept = deptMapper.selectById(dept.getParentId());

                    if(parentDept == null){

                        throw new BaseException("未查询到对应的父节点");

                    }

                    dept.setLevel(parentDept.getLevel().intValue() + 1);

                    if(StringUtils.isNotEmpty(parentDept.getPath())){

                        dept.setPath(parentDept.getPath() + "," + parentDept.getId());

                    }else{

                        dept.setPath(parentDept.getId().toString());

                    }

                }

                //可以使用雪花算法，生成ID
                return deptMapper.insert(dept);

            }

            public List<Dept> findAll() {

                List<Dept> allDept = deptMapper.findAll();

                // 0L：表示根节点的父ID

                return transferDeptVo(allDept, 0L);



            }









            private List<Dept> transferDeptVo(List<Dept> allDept, Long parentId){

                List<Dept> resultList = new ArrayList<>();

                if(!CollectionUtils.isEmpty(allDept)){

                    for (Dept source : allDept) {

                        if(parentId.longValue() == source.getParentId().longValue()){

                            Dept Dept = new Dept();

                            BeanUtils.copyProperties(source, Dept);

                            //递归查询子菜单，并封装信息

                            List<Dept> childList = transferDeptVo(allDept, source.getId());

                            if(!CollectionUtils.isEmpty(childList)){


                                Dept.setChildDept(childList);

                            }

                            resultList.add(Dept);

                        }

                    }

                }

                return resultList;

            }


        //    @Override
        //    public ResultListModel queryDepts(String userId) {
        //
        //        //1、先查询当前用户对应的角色
        //
        //        //Wrapper queryUserRoleObj = new QueryWrapper<>().eq("user_id", userId);
        //
        //        List<UserRole> userRoles = userRoleMapper.select(new UserRole().setUserId(userId));
        //
        //        if(!CollectionUtils.isEmpty(userRoles)){
        //
        //            //2、通过角色查询菜单（默认取第一个角色）
        //
        //            //Wrapper queryRoleDeptObj = new QueryWrapper<>().eq("role_id", userRoles.get(0).getRoleId());
        //
        //            List<RoleDept> roleDepts = roleDeptMapper.select(new RoleDept().setRoleId(userRoles.get(0).getRoleId()));
        //
        //            if(!CollectionUtils.isEmpty(roleDepts)){
        //
        //                Set<Long> DeptIds = new HashSet<>();
        //
        //                for (RoleDept roleDept : roleDepts) {
        //
        //                    DeptIds.add(roleDept.getDeptId());
        //
        //                }
        //
        //                //查询对应的菜单
        //
        //                Wrapper queryDeptObj = new QueryWrapper<>().in("id", new ArrayList<>(DeptIds));
        //
        //                List<Dept> Depts = super.list(queryDeptObj);
        //
        //                if(!CollectionUtils.isEmpty(Depts)){
        //
        //                    //将菜单下对应的父节点也一并全部查询出来
        //
        //                    Set<Long> allDeptIds = new HashSet<>();
        //
        //                    for (Dept Dept : Depts) {
        //
        //                        allDeptIds.add(Dept.getId());
        //
        //                        if(StringUtils.isNotEmpty(Dept.getPath())){
        //
        //                            String[] pathIds = StringUtils.split(",", Dept.getPath());
        //
        //                            for (String pathId : pathIds) {
        //
        //                                allDeptIds.add(Long.valueOf(pathId));
        //
        //                            }
        //
        //                        }
        //
        //                    }
        //
        //                    //3、查询对应的所有菜单,并进行封装展示
        //
        //                    List<Dept> allDepts = super.list(new QueryWrapper<Dept>().in("id", new ArrayList<>(allDeptIds)));
        //
        //                    List<DeptVo> resultList = transferDeptVo(allDepts, 0L);
        //
        //                    return resultList;
        //
        //                }
        //
        //            }
        //
        //        }
        //
        //        return null;
        //
        //    }


    public void removeDept (List<String> list) {
        QueryWrapper<Dept> q = new QueryWrapper<>();
        q.in("id",list);
        List<Dept> deptList = this.list(q);
        if (deptList ==null || deptList.size() == 0) {
            throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"找不到该部门");
        }

        List<String> deptCodes = deptList.stream().map(Dept::getDeptCode).distinct().collect(Collectors.toList());
        QueryWrapper<User> q1 = new QueryWrapper<>();
        q1.in("dept_code",deptCodes);
        List<User> userList = userService.list(q1);
        if (userList !=null && userList.size() > 0) {
            throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"该分类下有数据，无法删除");
        }

        this.removeByIds(list);
    }

}
