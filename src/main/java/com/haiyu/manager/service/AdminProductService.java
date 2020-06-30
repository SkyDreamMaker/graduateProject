package com.haiyu.manager.service;

import com.haiyu.manager.dto.DataSearchDTO;
import com.haiyu.manager.dto.ProductSearchDTO;
import com.haiyu.manager.pojo.BaseAdminData;
import com.haiyu.manager.pojo.BaseAdminUser;
import com.haiyu.manager.response.PageDataResult;

import java.util.Map;


/**
 * @Title: AdminUserService
 * @Description:
 * @author: youqing
 * @version: 1.0
 * @date: 2018/11/21 11:04
 */
public interface AdminProductService {

    PageDataResult getProductList(ProductSearchDTO productSearch, Integer pageNum, Integer pageSize);

/*    Map<String,Object> addData(BaseAdminData data);

    Map<String,Object> updateData(BaseAdminData data);

    BaseAdminUser getDataById(Integer id);

    BaseAdminUser findByDataName(String dataName);

    //int updatePwd(String userName, String password);

    Map<String, Object> delData(Integer id, Integer status);

    Map<String, Object> recoverData(Integer id, Integer status);*/
    Map<String, Object> getProductInfo();

}
