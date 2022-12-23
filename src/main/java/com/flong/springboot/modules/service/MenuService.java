package com.flong.springboot.modules.service;



import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flong.springboot.core.exception.BaseException;
import com.flong.springboot.modules.entity.Menu;
import com.flong.springboot.modules.mapper.MenuMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuService extends ServiceImpl<MenuMapper, Menu> {
        @Autowired
        MenuMapper menuMapper;


    public Menu getMenuById (String menuId){
        return menuMapper.selectById(menuId);
    }

        public int insert(Menu menu) {
            //如果插入的当前节点为根节点，parentId指定为0

            if(menu.getParentId().longValue() == 0){

                menu.setLevel(1);//根节点层级为1

                menu.setPath(null);//根节点路径为空

            }else{

                Menu parentMenu = menuMapper.selectById(menu.getParentId());

                if(parentMenu == null){

                    throw new BaseException("未查询到对应的父节点");

                }

                menu.setLevel(parentMenu.getLevel().intValue() + 1);

                if(StringUtils.isNotEmpty(parentMenu.getPath())){

                    menu.setPath(parentMenu.getPath() + "," + parentMenu.getId());

                }else{

                    menu.setPath(parentMenu.getId().toString());

                }

            }

            //可以使用雪花算法，生成ID
            return menuMapper.insert(menu);

        }

        public List<Menu> findAll() {

            List<Menu> allMenu = menuMapper.findAll();

            // 0L：表示根节点的父ID

            return transferMenuVo(allMenu, 0L);



        }



        public List<Menu> findMenuByRoleCode(String roleCode) {
            if (!StringUtils.isNotEmpty(roleCode)) {
                throw new BaseException("roleCode 为空");
            }

            try {
                List<Menu> list = menuMapper.findMenuByRoleCode(roleCode);

                return transferMenuVo(list, 0L);
            } catch (Exception e){
                return null;
            }


        }

    public List<Menu> queryMenusByUserId(String userId) {
        if (!StringUtils.isNotEmpty(userId)) {
            throw new BaseException("userid 为空");
        }

        if (userId.equals("admin")) {
            return findAll();
        }

        try {
            List<Menu> list = menuMapper.findMenuByUserId(userId);

            return transferMenuVo(list, 0L);
        } catch (Exception e){
            return null;
        }


    }



        private List<Menu> transferMenuVo(List<Menu> allMenu, Long parentId){

            List<Menu> resultList = new ArrayList<>();

            if(!CollectionUtils.isEmpty(allMenu)){

                for (Menu source : allMenu) {

                    if(parentId.longValue() == source.getParentId().longValue()){

                        Menu menu = new Menu();

                        BeanUtils.copyProperties(source, menu);

                        //递归查询子菜单，并封装信息

                        List<Menu> childList = transferMenuVo(allMenu, source.getId());

                        if(!CollectionUtils.isEmpty(childList)){


                            menu.setChildMenu(childList);

                        }

                        resultList.add(menu);

                    }

                }

            }

            return resultList;

        }


    //    @Override
    //    public ResultListModel queryMenus(String userId) {
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
    //            //Wrapper queryRoleMenuObj = new QueryWrapper<>().eq("role_id", userRoles.get(0).getRoleId());
    //
    //            List<RoleMenu> roleMenus = roleMenuMapper.select(new RoleMenu().setRoleId(userRoles.get(0).getRoleId()));
    //
    //            if(!CollectionUtils.isEmpty(roleMenus)){
    //
    //                Set<Long> menuIds = new HashSet<>();
    //
    //                for (RoleMenu roleMenu : roleMenus) {
    //
    //                    menuIds.add(roleMenu.getMenuId());
    //
    //                }
    //
    //                //查询对应的菜单
    //
    //                Wrapper queryMenuObj = new QueryWrapper<>().in("id", new ArrayList<>(menuIds));
    //
    //                List<Menu> menus = super.list(queryMenuObj);
    //
    //                if(!CollectionUtils.isEmpty(menus)){
    //
    //                    //将菜单下对应的父节点也一并全部查询出来
    //
    //                    Set<Long> allMenuIds = new HashSet<>();
    //
    //                    for (Menu menu : menus) {
    //
    //                        allMenuIds.add(menu.getId());
    //
    //                        if(StringUtils.isNotEmpty(menu.getPath())){
    //
    //                            String[] pathIds = StringUtils.split(",", menu.getPath());
    //
    //                            for (String pathId : pathIds) {
    //
    //                                allMenuIds.add(Long.valueOf(pathId));
    //
    //                            }
    //
    //                        }
    //
    //                    }
    //
    //                    //3、查询对应的所有菜单,并进行封装展示
    //
    //                    List<Menu> allMenus = super.list(new QueryWrapper<Menu>().in("id", new ArrayList<>(allMenuIds)));
    //
    //                    List<MenuVo> resultList = transferMenuVo(allMenus, 0L);
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

}
