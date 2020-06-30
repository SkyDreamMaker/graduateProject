package com.haiyu.manager.service;

import com.haiyu.manager.dto.AdminDeviceDTO;
import com.haiyu.manager.dto.DeviceSearchDTO;
import com.haiyu.manager.dto.UserSearchDTO;
import com.haiyu.manager.pojo.BaseAdminDevice;
import com.haiyu.manager.pojo.BaseAdminUser;
import com.haiyu.manager.response.PageDataResult;

import java.util.List;
import java.util.Map;


/**
 * @Title: AdminUserService
 * @Description:
 * @author: youqing
 * @version: 1.0
 * @date: 2018/11/21 11:04
 */
public interface AdminDeviceService {

    PageDataResult getDeviceList(DeviceSearchDTO deviceSearch, Integer pageNum, Integer pageSize);

    PageDataResult getCurrentDeviceList(String userName,DeviceSearchDTO deviceSearch, Integer pageNum, Integer pageSize);

    PageDataResult getDeviceByUserName(String userName,DeviceSearchDTO deviceSearch, Integer pageNum, Integer pageSize);

    Map<String,Object> addDevice(BaseAdminDevice device);

    Map<String,Object> updateDevice(BaseAdminDevice device);

    BaseAdminUser getDeviceById(Integer id);

    BaseAdminUser findByDeviceName(String deviceName);

    //int updatePwd(String userName, String password);
    //此删除只有改变设备状态
    Map<String, Object> delDevice(Integer id, Integer status);
    //完全删除设备
    Map<String, Object> destroyDevice(Integer id);

    Map<String, Object> recoverDevice(Integer id, Integer status);
}
