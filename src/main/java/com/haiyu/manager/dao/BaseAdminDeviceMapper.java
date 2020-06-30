package com.haiyu.manager.dao;


import com.haiyu.manager.dto.AdminDeviceDTO;
import com.haiyu.manager.dto.AdminUserDTO;
import com.haiyu.manager.dto.DeviceSearchDTO;
import com.haiyu.manager.dto.UserSearchDTO;
import com.haiyu.manager.pojo.BaseAdminDevice;
import com.haiyu.manager.pojo.BaseAdminUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mapper.MyMapper;

import java.util.List;

@Repository
public interface BaseAdminDeviceMapper extends MyMapper<BaseAdminDevice> {

    /*查询列表*/
    List<AdminDeviceDTO> getDeviceList(DeviceSearchDTO deviceSearchDTO);

    //*查询当前用户设备列表*//*
    List<AdminDeviceDTO> getCurrentDeviceList(String userName,DeviceSearchDTO deviceSearchDTO);

    List<AdminDeviceDTO> getDeviceByUserName(String userName);

    BaseAdminDevice getDeviceByDeviceName(@Param("deviceName")String deviceName,@Param("id") Integer id);

    BaseAdminDevice getDeviceByDeviceId(@Param("deviceId")String deviceId);

    int updateDeviceStatus(@Param("id") Integer id,@Param("status") Integer status);

    int updateDevice(BaseAdminDevice device);
    BaseAdminDevice getDeviceById(@Param("id") Integer id);
    BaseAdminDevice findByUserName(@Param("userName") String userName);

    int destroyDevice(@Param("id") Integer id);

    //int updatePwd(@Param("userName") String userName,@Param("password") String password);

}
