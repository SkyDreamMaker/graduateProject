package com.haiyu.manager.dao;


import com.haiyu.manager.dto.AdminDataDTO;
import com.haiyu.manager.dto.AdminDeviceDTO;
import com.haiyu.manager.dto.DataSearchDTO;
import com.haiyu.manager.dto.DeviceSearchDTO;
import com.haiyu.manager.pojo.BaseAdminData;
import com.haiyu.manager.pojo.BaseAdminDevice;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mapper.MyMapper;

import java.util.List;

@Repository
public interface BaseAdminDataMapper extends MyMapper<BaseAdminData> {

    /*查询列表*/
    List<AdminDataDTO> getDataList(DataSearchDTO dataSearchDTO);
    BaseAdminData getDataByDatasetId(@Param("datasetId")String datasetId,@Param("id") Integer id);

    //*查询当前用户设备列表*//*
    //List<AdminDeviceDTO> getCurrentDeviceList(String userName, DeviceSearchDTO deviceSearchDTO);

    //List<AdminDeviceDTO> getDeviceByUserName(String userName);

    //BaseAdminDevice getDataByDatasetId(@Param("deviceName") String deviceName, @Param("id") Integer id);

    int updateDataStatus(@Param("id") Integer id, @Param("status") Integer status);

    int updateData(BaseAdminData data);

     // BaseAdminDevice findByUserName(@Param("userName") String userName);

    //int updatePwd(@Param("userName") String userName,@Param("password") String password);

}
