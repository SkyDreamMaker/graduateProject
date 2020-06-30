package com.haiyu.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.haiyu.manager.common.utils.DateUtils;
import com.haiyu.manager.common.utils.NBIoTUtils;
import com.haiyu.manager.dao.*;
import com.haiyu.manager.dto.AdminDataDTO;
import com.haiyu.manager.dto.DataSearchDTO;
import com.haiyu.manager.pojo.*;
import com.haiyu.manager.response.PageDataResult;
import com.haiyu.manager.service.AdminDataService;
import org.apache.shiro.SecurityUtils;
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
public class AdminDataServiceImpl implements AdminDataService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

/*    private String APP_KEY = "OIUJOR846L4";
    private String APP_SECRET = "HJm58BlJgM";*/
    @Autowired
    private BaseAdminUserMapper baseAdminUserMapper;
    @Autowired
    private BaseAdminDataMapper baseAdminDataMapper;
    @Autowired
    private BaseAdminDeviceMapper baseAdminDeviceMapper;
    @Autowired
    private BaseAdminProductMapper baseAdminProductMapper;
    @Autowired
    private BaseAdminChartMapper baseAdminChartMapper;
    @Override
    public PageDataResult getDataList(DataSearchDTO dataSearch, Integer pageNum, Integer pageSize) {
        PageDataResult pageDataResult = new PageDataResult();
        List<AdminDataDTO> baseAdminDatas = baseAdminDataMapper.getDataList(dataSearch);
        PageHelper.startPage(pageNum, pageSize);
        if (baseAdminDatas.size() != 0) {
            PageInfo<AdminDataDTO> pageInfo = new PageInfo<>(baseAdminDatas);
            pageDataResult.setList(baseAdminDatas);
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    @Override
    public Map<String, Object> addData(BaseAdminData data) {
        Map<String, Object> dataMap = new HashMap();
        try {
/*            BaseAdminData old = baseAdminDataMapper.getDataByDatasetId(data.getDatasetId(), null);
            if (old != null) {
                dataMap.put("code", 0);
                dataMap.put("msg", "设备名已存在！");
                logger.error("[新增]，结果=设备已存在！");
                return dataMap;
            }*/
            String deviceId = data.getDeviceId();
            //根据设备ID获取产品ID
            //BaseAdminProduct product = baseAdminProductMapper.getProductByDeviceId(deviceId);
            BaseAdminDevice product = baseAdminDeviceMapper.getDeviceByDeviceId(deviceId);
            String productId = product.getProductId();
            String deviceName = product.getDeviceName();
            /*NBIoTUtils.getIotDevice();*/
            data.setDeviceId(deviceId);
            String datasetId = data.getDatasetId();
            data.setDatasetId(datasetId);
            logger.info(deviceId);

            //获取APP_KEY
            BaseAdminUser user = (BaseAdminUser) SecurityUtils.getSubject().getPrincipal();
            String currentUserName = user.getSysUserName();
            BaseAdminUser userResult = baseAdminUserMapper.getUserByUserName(currentUserName,null);
            String APP_KEY = userResult.getAppKey();
            String APP_SECRET = userResult.getAppSecret();

            //设备ID->产品ID->应用数据,一次性获取全部数据
            BaseAdminChart chartData = NBIoTUtils.getIotDataList(APP_KEY,APP_SECRET,productId,deviceId);
            //补全chart表数据(设备名称和ID)
/*            chartData.setDeviceId(deviceId);
            chartData.setDeviceName(deviceName);
            //向chart表中插入数据对象
            baseAdminChartMapper.insert(chartData);   */
            BaseAdminChart chart = new BaseAdminChart();
            chart.setTemperature(chartData.getTemperature());
            chart.setHumilevel(chartData.getHumilevel());
            chart.setDeviceId(deviceId);
            chart.setDeviceName(deviceName);
            chart.setRegTime(chartData.getRegTime());
            //向chart表中插入数据对象
            //根据数据时间去重
            String reg_time = chartData.getRegTime();
            BaseAdminChart chart1 = baseAdminChartMapper.getChartByTime(reg_time);
            if (chart1 == null) {
                baseAdminChartMapper.insert(chart);
            }
            //插入新的温度信息
            if (datasetId.equals("temperature")){
                data.setDataValue(chartData.getTemperature());
            }else if (datasetId.equals("humilevel")) {
                data.setDataValue(chartData.getHumilevel());
            }else if (datasetId.equals("message")) {
                data.setDataValue(chartData.getMessage());
            }else if (datasetId.equals("battery")) {
                data.setDataValue(chartData.getBattery());
            }else if (datasetId.equals("signalStrength")) {
                data.setDataValue(chartData.getSignalStrength());
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
    }

    @Override
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
/*        if(device.getSysUserPwd() != null){
            String password = DigestUtils.Md5(username,user.getSysUserPwd());
            user.setSysUserPwd(password);
        }*/

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
    }
}
