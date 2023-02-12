package com.flong.springboot.modules.service;



import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.flong.springboot.base.utils.UserHelper;
import com.flong.springboot.modules.entity.OptLog;
import com.flong.springboot.modules.entity.dto.OptLogDto;
import com.flong.springboot.modules.entity.vo.OptLogVo;
import com.flong.springboot.modules.mapper.OptLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class OptLogService extends ServiceImpl<OptLogMapper, OptLog> {

        @Autowired
        OptLogMapper optLogMapper;

        public void insertOptLog (String userId,String optId,String optModule,String content) {
            OptLog optLog = new OptLog();
            optLog.setUserId(userId);
            optLog.setOptIp(optId);
            optLog.setOptModule(optModule);
            optLog.setOptTime(UserHelper.getDateTime());
            optLog.setContent(content);
            this.save(optLog);
        }


        public IPage<OptLogVo> pageList (OptLogDto optLogDto) {
           return optLogMapper.pageList(optLogDto.getPage()==null ? new Page<>():optLogDto.getPage(),optLogDto);
        }
}
