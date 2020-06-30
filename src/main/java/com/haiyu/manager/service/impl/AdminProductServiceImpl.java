package com.haiyu.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.haiyu.manager.common.utils.NBIoTUtils;
import com.haiyu.manager.common.utils.TimeStamp;
import com.haiyu.manager.dao.BaseAdminProductMapper;
import com.haiyu.manager.dto.AdminProductDTO;
import com.haiyu.manager.dto.ProductSearchDTO;
import com.haiyu.manager.nbpojo.QueryProductResult;
import com.haiyu.manager.pojo.BaseAdminProduct;
import com.haiyu.manager.response.PageDataResult;
import com.haiyu.manager.service.AdminProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: AdminDeviceServiceImpl
 */
@Service
public class AdminProductServiceImpl implements AdminProductService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String APP_KEY = "OIUJOR846L4";
    private String APP_SECRET = "HJm58BlJgM";
    @Autowired
    private BaseAdminProductMapper baseAdminProductMapper;

    @Override
    public PageDataResult getProductList(ProductSearchDTO productSearch, Integer pageNum, Integer pageSize) {
        PageDataResult pageDataResult = new PageDataResult();
        List<AdminProductDTO> baseAdminDatas = baseAdminProductMapper.getProductList(productSearch);
        PageHelper.startPage(pageNum, pageSize);
        if (baseAdminDatas.size() != 0) {
            //该数据类型决定了网页显示的数据
            PageInfo<AdminProductDTO> pageInfo = new PageInfo<>(baseAdminDatas);
            pageDataResult.setList(baseAdminDatas);
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    @Override
    public Map<String, Object> getProductInfo() {
        Map<String, Object> data = new HashMap<>();
        //向电信云请求查询创建的产品信息
        try {
            List<QueryProductResult> results = NBIoTUtils.QueryIotProductList(APP_KEY,APP_SECRET,"");
            for (int i=0;i<results.size();i++) {
                //将数据存入数据库中
                BaseAdminProduct product = new BaseAdminProduct();
                QueryProductResult result = results.get(i);
                String productName = result.getProductName();
                BaseAdminProduct productResult = baseAdminProductMapper.getProductByProductName(productName);
                //去除重复的产品名显示
                if (productResult == null) {
                  product.setProductId(result.getProductId());
                  product.setProductName(result.getProductName());
                  product.setApiKey(result.getApiKey());
                  //product.setProductType(result.getProductType());
                  product.setProductType(result.getProductTypeValue()+"/"+result.getSecondaryTypeValue()+"/"+result.getThirdTypeValue());
                  product.setProductStatus(1);
                  product.setRegTime(TimeStamp.stampToDate(result.getCreateTime()));
                  baseAdminProductMapper.insert(product);
                }
            }
        }catch (Exception e) {
           e.printStackTrace();
        }
        data.put("code",1);
        data.put("msg","产品信息已更新！");
        return data;
    }


 /*   @Override
    public Map<String, Object> addData(BaseAdminData data) {
        Map<String, Object> dataMap = new HashMap();
        try {
*//*            BaseAdminData old = baseAdminDataMapper.getDataByDatasetId(data.getDatasetId(), null);
            if (old != null) {
                dataMap.put("code", 0);
                dataMap.put("msg", "设备名已存在！");
                logger.error("[新增]，结果=设备已存在！");
                return dataMap;
            }*//*
            String deviceId = data.getDeviceId();
            //根据设备ID获取产品ID
            *//*NBIoTUtils.getIotDevice();*//*
            data.setDeviceId(deviceId);
            String datasetId = data.getDatasetId();
            data.setDatasetId(datasetId);
            logger.info(deviceId);
            //设备ID->产品ID->应用数据
            ChartData chartData = NBIoTUtils.getIotDataList(APP_KEY,APP_SECRET,"10045641",deviceId);
            //插入新的温度信息
            if (datasetId.equals("temperature")){
                data.setDataValue(chartData.getTemperature());
            }else if (datasetId.equals("humilevel")) {
                data.setDataValue(chartData.getHumilevel());
            }
            //补全数据
            data.setDataId(1);
            data.setRegTime(DateUtils.getCurrentDate());
            data.setDataStatus(1);
            int result = baseAdminDataMapper.insert(data);
            if (result == 0) {
                dataMap.put("code", 0);
                dataMap.put("msg", "新增失败！");
                logger.error("设备[新增]，结果=新增失败！");
                return dataMap;
            }
            dataMap.put("code", 1);
            dataMap.put("msg", "新增成功！");
            logger.info("设备[新增]，结果=新增成功！");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("设备[新增]异常！", e);
            return dataMap;
        }
        return dataMap;
    }*/

/*    @Override
    public Map<String, Object> updateData(BaseAdminData data) {
        Map<String,Object> dataMap = new HashMap();
        Integer id = data.getId();
        BaseAdminData old = baseAdminDataMapper.getDataByDatasetId(data.getDatasetId(),id);
        if(old != null){
            dataMap.put("code",0);
            dataMap.put("msg","设备名已存在！");
            logger.error("设备[更新]，结果=设备名已存在！");
            return dataMap;
        }
        String datasetId = data.getDatasetId();
        data.setDatasetId(datasetId);
*//*        if(device.getSysUserPwd() != null){
            String password = DigestUtils.Md5(username,user.getSysUserPwd());
            user.setSysUserPwd(password);
        }*//*

        int result = baseAdminDataMapper.updateData(data);
        if(result == 0){
            dataMap.put("code",0);
            dataMap.put("msg","更新失败！");
            logger.error("用户[更新]，结果=更新失败！");
            return dataMap;
        }
        dataMap.put("code",1);
        dataMap.put("msg","更新成功！");
        logger.info("用户[更新]，结果=更新成功！");
        return dataMap;
    }

    @Override
    public BaseAdminUser getDataById(Integer id) {
        return null;
    }

    @Override
    public BaseAdminUser findByDataName(String dataName) {
        return null;
    }

    @Override
    public Map<String, Object> delData(Integer id, Integer status) {
        Map<String, Object> dataMap = new HashMap<>();
        try {
            // 删除用户
            int result = baseAdminDataMapper.updateDataStatus(id, status);
            if (result == 0) {
                dataMap.put("code", 0);
                dataMap.put("msg", "删除数据失败");
                logger.error("删除数据失败");
                return dataMap;
            }
            dataMap.put("code", 1);
            dataMap.put("msg", "删除数据成功");
            logger.info("删除数据成功");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("删除数据异常！", e);
        }
        return dataMap;
    }

    @Override
    public Map<String, Object> recoverData(Integer id, Integer status) {

        Map<String, Object> data = new HashMap<>();
        try {
            int result = baseAdminDataMapper.updateDataStatus(id, status);
            if (result == 0) {
                data.put("code", 0);
                data.put("msg", "恢复数据失败");
            }
            data.put("code", 1);
            data.put("msg", "恢复数据成功");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("恢复数据异常！", e);
        }
        return data;
    }*/
}
