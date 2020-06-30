package com.haiyu.manager.dao;


import com.haiyu.manager.dto.AdminDataDTO;
import com.haiyu.manager.dto.AdminProductDTO;
import com.haiyu.manager.dto.DataSearchDTO;
import com.haiyu.manager.dto.ProductSearchDTO;
import com.haiyu.manager.pojo.BaseAdminData;
import com.haiyu.manager.pojo.BaseAdminProduct;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mapper.MyMapper;

import java.util.List;

@Repository
public interface BaseAdminProductMapper extends MyMapper<BaseAdminProduct> {

    /*查询列表*/
    List<AdminProductDTO> getProductList(ProductSearchDTO productSearchDTO);
    BaseAdminProduct getProductByProductId(@Param("productId") String productId);
    BaseAdminProduct getProductByProductName(@Param("productName") String productName);
    //BaseAdminProduct getProductByDeviceId(@Param("deviceId") String deviceId);
    //*查询当前用户设备列表*//*
    //List<AdminDeviceDTO> getCurrentDeviceList(String userName, DeviceSearchDTO deviceSearchDTO);

    //List<AdminDeviceDTO> getDeviceByUserName(String userName);

    //BaseAdminDevice getDataByDatasetId(@Param("deviceName") String deviceName, @Param("id") Integer id);

    int updateProductStatus(@Param("id") Integer id, @Param("status") Integer status);

    int updateProduct(BaseAdminProduct product);

    int deleteByProductId(@Param("productId") String productId);

     // BaseAdminDevice findByUserName(@Param("userName") String userName);

    //int updatePwd(@Param("userName") String userName,@Param("password") String password);

}
