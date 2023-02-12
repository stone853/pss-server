package com.flong.springboot.modules.service;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flong.springboot.core.exception.BaseException;
import com.flong.springboot.core.exception.CommMsgCode;
import com.flong.springboot.core.exception.ServiceException;
import com.flong.springboot.modules.entity.*;
import com.flong.springboot.modules.mapper.DictTreeMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DictTreeService extends ServiceImpl<DictTreeMapper, DictTree> {
        @Autowired
        DictTreeMapper dictTreeMapper;


        @Autowired
        CustomerService customerService;

        @Autowired
        SupplierService supplierService;

        @Autowired
        MaterialMgtService materialMgtService;

        public int insert(DictTree dictTree) {
                if (isExists(dictTree.getNote(),dictTree.getType())) {
                    throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"名称已存在");
                }

                //如果插入的当前节点为根节点，parentId指定为0

                if(dictTree.getParentId().longValue() == 0){

                    dictTree.setLevel(1);//根节点层级为1

                    dictTree.setPath(null);//根节点路径为空

                }else{

                    DictTree parentdictTree = dictTreeMapper.selectById(dictTree.getParentId());

                    if(parentdictTree == null){

                        throw new BaseException("未查询到对应的父节点");

                    }

                    dictTree.setLevel(parentdictTree.getLevel().intValue() + 1);

                    if(StringUtils.isNotEmpty(parentdictTree.getPath())){

                        dictTree.setPath(parentdictTree.getPath() + "," + parentdictTree.getId());

                    }else{

                        dictTree.setPath(parentdictTree.getId().toString());

                    }

                }

//                //获取maxcode
//                int maxCode = dictTreeMapper.getMaxCode(dictTree.getType());
//                dictTree.setCode(String.valueOf(maxCode+1));
//                //可以使用雪花算法，生成ID
//                return dictTreeMapper.insert(dictTree);
            return insertNodes(dictTree);

        }

        private boolean isExists (String note,String type) {
            QueryWrapper<DictTree> q = new QueryWrapper<>();
            q.eq("note",note);
            q.eq("type",type);
            q.last("limit 1");
            DictTree dt = this.getOne(q);
            if (dt == null) {
                return false;
            }
            return true;
        }

        private synchronized int insertNodes (DictTree dictTree) {
            int maxCode = dictTreeMapper.getMaxCode(dictTree.getType());
            dictTree.setCode(String.valueOf(maxCode+1));
            //可以使用雪花算法，生成ID
            return dictTreeMapper.insert(dictTree);

        }
        public List<DictTree> findAll() {

            List<DictTree> allDictTree = dictTreeMapper.selectList(new QueryWrapper<DictTree>());

            // 0L：表示根节点的父ID

            return transferDictTreeVo(allDictTree, 0L);



        }



        public List<DictTree> findByType(String type) {
            if (!StringUtils.isNotEmpty(type)) {
                throw new BaseException("字典类型type为空");
            }

            try {
                QueryWrapper q = new QueryWrapper<DictTree>();
                q.eq("type",type);
                List<DictTree> list = dictTreeMapper.selectList(q);

                return transferDictTreeVo(list, 0L);
            } catch (Exception e){
                throw e;
            }


        }





        private List<DictTree> transferDictTreeVo(List<DictTree> allDictTree, Long parentId){

            List<DictTree> resultList = new ArrayList<>();

            if(!CollectionUtils.isEmpty(allDictTree)){

                for (DictTree source : allDictTree) {

                    if(parentId.longValue() == source.getParentId().longValue()){

                        DictTree dictTree = new DictTree();

                        BeanUtils.copyProperties(source, dictTree);

                        //递归查询子菜单，并封装信息

                        List<DictTree> childList = transferDictTreeVo(allDictTree, source.getId());

                        if(!CollectionUtils.isEmpty(childList)){


                            dictTree.setChildDictTree(childList);

                        }

                        resultList.add(dictTree);

                    }

                }

            }

            return resultList;

        }

        public void deleteByIds (List<String> ids,String dictType) {
            try {
                if (ids == null || ids.size() == 0) {
                    return;
                }

                QueryWrapper<DictTree> q = new QueryWrapper<>();
                q.in("id",ids);
                List<DictTree> list = this.list(q);
                if (list ==null || list.size() == 0) {
                    return;
                }
                List<String> codes = list.stream().map(DictTree::getCode).distinct().collect(Collectors.toList());

                boolean bool = true;
                if (StringUtils.isNotEmpty(dictType)) {
                    if (dictType.equals("1")) {
                        bool=hasMaterial(codes);
                    } else if (dictType.equals("2")) {
                        bool=hasCust(codes);
                    } else if (dictType.equals("3")) {
                        bool=hasSupplier(codes);
                    }
                }
                if (!bool) {
                    throw new ServiceException(CommMsgCode.BIZ_INTERRUPT,"分类下有数据，不能删除");
                }
                this.removeByIds(ids);
            } catch (ServiceException s) {
                throw s;
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        private boolean hasCust (List<String> ids) {
            QueryWrapper<Customer> q = new QueryWrapper<>();
            q.in("type",ids);
            List<Customer> list = customerService.list(q);
            if (list != null && list.size() > 0) {
                return false;
            }
            return true;
        }

    private boolean hasSupplier (List<String> ids) {
        QueryWrapper<Supplier> q = new QueryWrapper<>();
        q.in("type",ids);
        List<Supplier> list = supplierService.list(q);
        if (list != null && list.size() > 0) {
            return false;
        }
        return true;
    }

    private boolean hasMaterial (List<String> ids) {
        QueryWrapper<MaterialMgt> q = new QueryWrapper<>();
        q.in("type",ids);
        List<MaterialMgt> list = materialMgtService.list(q);
        if (list != null && list.size() > 0) {
            return false;
        }
        return true;
    }

}
