package com.zhupeiting.bisheproject.service;

import com.zhupeiting.bisheproject.dto.NotificationDto;
import com.zhupeiting.bisheproject.dto.PageDto;
import com.zhupeiting.bisheproject.enums.NotificationStatusEnum;
import com.zhupeiting.bisheproject.enums.NotificationTypeEnum;
import com.zhupeiting.bisheproject.exception.CustomizeErrorCode;
import com.zhupeiting.bisheproject.exception.CustomizeException;
import com.zhupeiting.bisheproject.mapper.NotificationMapper;
import com.zhupeiting.bisheproject.model.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class NotificationService {
    @Autowired
    private NotificationMapper notificationMapper;

    public PageDto list(Long id, Integer page, Integer size) {
        PageDto<NotificationDto> pageDto = new PageDto<>();
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria()
                .andReceiverEqualTo(id);
        Integer totalCount = (int) notificationMapper.countByExample(notificationExample);
        //计算总页数
        Integer totalPage;
        if(totalCount % size == 0){
            totalPage = totalCount / size;
        }else{
            totalPage = totalCount / size + 1;
        }
        //检查page
        if(page < 1){
            page = 1;
        }
        if(page > totalPage){
            page = totalPage;
        }
        /**
         * 分页时列表为空的特殊处理
         * */
        if(page == 0) {
            pageDto.setPageDto(1,1);
            List<NotificationDto>notificationList = new ArrayList<>();
            pageDto.setData(notificationList);
            return pageDto;
        }
        /***/
        pageDto.setPageDto(totalPage,page);
        Integer offset = size * (page-1);
        notificationExample = new NotificationExample();
        notificationExample.createCriteria()
                .andReceiverEqualTo(id);
        notificationExample.setOrderByClause("gmt_create desc");
        List<Notification> notificationList = notificationMapper.selectByExampleWithRowbounds(notificationExample,new RowBounds(offset,size));
        if(notificationList.size() == 0){
            return pageDto;
        }
        List<NotificationDto> notificationDtoList = new ArrayList<>();
        for (Notification notification : notificationList) {
            NotificationDto notificationDto = new NotificationDto();
            BeanUtils.copyProperties(notification,notificationDto);
            notificationDto.setTypeName(NotificationTypeEnum.nameOfType(notification.getNoteType()));
            notificationDtoList.add(notificationDto);
        }
        pageDto.setData(notificationDtoList);
        return pageDto;

    }

    public Long unreadCount(Long userId) {
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria()
                        .andReceiverEqualTo(userId)
                        .andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
        return notificationMapper.countByExample(notificationExample);
    }

    public NotificationDto read(Long id, Users users) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        if(notification == null){
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if(!Objects.equals(notification.getReceiver(),users.getId())){
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }

        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateByPrimaryKey(notification);

        NotificationDto notificationDto = new NotificationDto();
        BeanUtils.copyProperties(notification,notificationDto);
        notificationDto.setTypeName(NotificationTypeEnum.nameOfType(notification.getNoteType()));
        return notificationDto;
    }
}
