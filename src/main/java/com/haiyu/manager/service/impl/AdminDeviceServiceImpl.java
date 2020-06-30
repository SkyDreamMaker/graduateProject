package com.haiyu.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.haiyu.manager.common.utils.DateUtils;
import com.haiyu.manager.common.utils.DigestUtils;
import com.haiyu.manager.common.utils.NBIoTUtils;
import com.haiyu.manager.dao.BaseAdminDeviceMapper;
import com.haiyu.manager.dao.BaseAdminProductMapper;
import com.haiyu.manager.dao.BaseAdminUserMapper;
import com.haiyu.manager.dto.AdminDeviceDTO;
import com.haiyu.manager.dto.AdminUserDTO;
import com.haiyu.manager.dto.DeviceSearchDTO;
import com.haiyu.manager.dto.UserSearchDTO;
import com.haiyu.manager.nbpojo.CreateDeviceResult;
import com.haiyu.manager.nbpojo.DeviceResult;
import com.haiyu.manager.nbpojo.QueryProductResult;
import com.haiyu.manager.pojo.BaseAdminDevice;
import com.haiyu.manager.pojo.BaseAdminProduct;
import com.haiyu.manager.pojo.BaseAdminUser;
import com.haiyu.manager.response.PageDataResult;
import com.haiyu.manager.service.AdminDeviceService;
import com.haiyu.manager.service.AdminUserService;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: AdminDeviceServiceImpl
 */
@Service
public class AdminDeviceServiceImpl implements AdminDeviceService{

    private Logger logger = LoggerFactory.getLogger(this.getClass());
/*    private String APP_KEY = "OIUJOR846L4";
    private String APP_SECRET = "HJm58BlJgM";*/
    private String MasterKey = "c24ccca89ea74111a360f925c95d3af2";
    @Autowired
    private BaseAdminUserMapper baseAdminUserMapper;
    @Autowired
    private BaseAdminDeviceMapper baseAdmindeviceMapper;
    @Autowired
    private BaseAdminProductMapper baseAdminProductMapper;
    @Override
    public PageDataResult getDeviceList(DeviceSearchDTO deviceSearch, Integer pageNum, Integer pageSize) {
        PageDataResult pageDataResult = new PageDataResult();
        List<AdminDeviceDTO> baseAdminDevices = baseAdmindeviceMapper.getDeviceList(deviceSearch);
        PageHelper.startPage(pageNum, pageSize);
        if(baseAdminDevices.size() != 0){
            PageInfo<AdminDeviceDTO> pageInfo = new PageInfo<>(baseAdminDevices);
            pageDataResult.setList(baseAdminDevices);
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    @Override
    public PageDataResult getCurrentDeviceList(String userName,DeviceSearchDTO deviceSearch, Integer pageNum, Integer pageSize) {
        PageDataResult pageDataResult = new PageDataResult();
        List<AdminDeviceDTO> baseAdminDevices = baseAdmindeviceMapper.getCurrentDeviceList(userName,deviceSearch);
        PageHelper.startPage(pageNum, pageSize);
        if(baseAdminDevices.size() != 0){
            PageInfo<AdminDeviceDTO> pageInfo = new PageInfo<>(baseAdminDevices);
            pageDataResult.setList(baseAdminDevices);
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    @Override
    public PageDataResult getDeviceByUserName(String userName,DeviceSearchDTO deviceSearch, Integer pageNum, Integer pageSize) {
        PageDataResult pageDataResult = new PageDataResult();
        List<AdminDeviceDTO> baseAdminDevices = baseAdmindeviceMapper.getDeviceByUserName(userName);
        PageHelper.startPage(pageNum, pageSize);
        if(baseAdminDevices.size() != 0){
            PageInfo<AdminDeviceDTO> pageInfo = new PageInfo<>(baseAdminDevices);
            pageDataResult.setList(baseAdminDevices);
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    @Override
    public Map<String, Object> addDevice(BaseAdminDevice device) {
        Map<String,Object> data = new HashMap();
        try {
            BaseAdminDevice old = baseAdmindeviceMapper.getDeviceByDeviceName(device.getDeviceName(),null);
            if(old != null){
                data.put("code",0);
                data.put("msg","设备名已存在！");
                logger.error("[新增]，结果=设备已存在！");
                return data;
            }
            //String deviceId = device.getDeviceId();

            if(device.getImei().length() != 15){
                data.put("code",0);
                data.put("msg","imei位数不对！,imei必须为数字且为15位");
                logger.error("置用户[新增或更新]，结果=手机号位数不对！");
                return data;
            }
            String userame = device.getSysUserName();
            device.setSysUserName(userame);
            //获取APP_KEY
            BaseAdminUser user = (BaseAdminUser) SecurityUtils.getSubject().getPrincipal();
            String currentUserName = user.getSysUserName();
            BaseAdminUser userResult = baseAdminUserMapper.getUserByUserName(currentUserName,null);
            String APP_KEY = userResult.getAppKey();
            String APP_SECRET = userResult.getAppSecret();
            String productId = device.getProductId();
            BaseAdminProduct product = baseAdminProductMapper.getProductByProductId(productId);
            String MasterKey = product.getApiKey();
            //向云端申请注册设备
            CreateDeviceResult createDeviceResult = NBIoTUtils.CreateIotDevice(APP_KEY,APP_SECRET,productId,MasterKey,
                    device.getDeviceName(),device.getImei());
            //补充设备信息
            device.setDeviceName(createDeviceResult.getDeviceName());
            device.setDeviceId(createDeviceResult.getDeviceId());
            device.setImei(createDeviceResult.getImei());
            device.setProductId(createDeviceResult.getProductId());
            device.setRegTime(DateUtils.getCurrentDate());
            device.setDeviceStatus(1);
            int result = baseAdmindeviceMapper.insert(device);
            if(result == 0){
                data.put("code",0);
                data.put("msg","新增失败！");
                logger.error("设备[新增]，结果=新增失败！");
                return data;
            }
            data.put("code",1);
            data.put("msg","新增成功！");
            logger.info("设备[新增]，结果=新增成功！");
            /*
            //更改产品表内容，添加新的设备
            BaseAdminProduct product = new BaseAdminProduct();
            product.setProductId(createDeviceResult.getProductId());
            //根据产品ID查询产品名称
            //BaseAdminProduct productResult = baseAdminProductMapper.getProductByProductId(createDeviceResult.getProductId());
            QueryProductResult productResult = NBIoTUtils.QueryIotProduct(APP_KEY,APP_SECRET,productId);
            product.setProductName(productResult.getProductName());
            product.setDeviceId(createDeviceResult.getDeviceId());
            product.setDeviceName(createDeviceResult.getDeviceName());
            product.setRegTime(DateUtils.getNowDateString());
            product.setProductStatus(1);
            baseAdminProductMapper.insert(product);*/
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("设备[新增]异常！", e);
            return data;
        }
        return data;
    }

    @Override
    public Map<String, Object> updateDevice(BaseAdminDevice device) {
        Map<String,Object> data = new HashMap();
        Integer id = device.getId();
        logger.info("获取的ID"+id);
        //根据ID获取设备ID
        BaseAdminDevice resultDevice = baseAdmindeviceMapper.getDeviceById(id);
        logger.info(resultDevice.getDeviceId());
        BaseAdminDevice old = baseAdmindeviceMapper.getDeviceByDeviceName(device.getSysUserName(),id);
        if(old != null){
            data.put("code",0);
            data.put("msg","设备名已存在！");
            logger.error("设备[更新]，结果=设备名已存在！");
            return data;
        }
        String username = device.getSysUserName();
        device.setSysUserName(username);
        String deviceId = resultDevice.getDeviceId();
        String deviceName = device.getDeviceName();

        //获取APP_KEY
        BaseAdminUser user = (BaseAdminUser) SecurityUtils.getSubject().getPrincipal();
        String currentUserName = user.getSysUserName();
        BaseAdminUser userResult = baseAdminUserMapper.getUserByUserName(currentUserName,null);
        String APP_KEY = userResult.getAppKey();
        String APP_SECRET = userResult.getAppSecret();

        try{
        //获取必须数据
            QueryProductResult queryProductResult = NBIoTUtils.QueryIotProduct(APP_KEY,APP_SECRET,"10045641");
            String MasterKey = queryProductResult.getApiKey();
            logger.info("获取的MasterKey:"+MasterKey);
            String productId = queryProductResult.getProductId();
            logger.info("获取的productId:"+productId);
            logger.info("获取的deviceId:"+deviceId);
            //向云端做出修改
            String updateResult = NBIoTUtils.UpdateIotDevice(APP_KEY,APP_SECRET,productId,MasterKey,deviceId,deviceName,0,"1");
            if (updateResult.equals("false")) {
                data.put("code",0);
                data.put("msg","更新失败！");
                logger.error("设备[更新]，结果=更新失败！");
                return data;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
/*        if(device.getSysUserPwd() != null){
            String password = DigestUtils.Md5(username,user.getSysUserPwd());
            user.setSysUserPwd(password);
        }*/
        int result = baseAdmindeviceMapper.updateDevice(device);
        if(result == 0){
            data.put("code",0);
            data.put("msg","更新失败！");
            logger.error("用户[更新]，结果=更新失败！");
            return data;
        }
        data.put("code",1);
        data.put("msg","更新成功！");
        logger.info("用户[更新]，结果=更新成功！");
        return data;
    }

    @Override
    public BaseAdminUser getDeviceById(Integer id) {
        return null;
    }

    @Override
    public BaseAdminUser findByDeviceName(String deviceName) {
        return null;
    }

    /*删除设备*/
    @Override
    public Map<String, Object> delDevice(Integer id, Integer status) {
        Map<String, Object> data = new HashMap<>();
        try {
            // 删除用户
            int result = baseAdmindeviceMapper.updateDeviceStatus(id,status);
            if(result == 0){
                data.put("code",0);
                data.put("msg","删除设备失败");
                logger.error("删除设备失败");
                return data;
            }
            data.put("code",1);
            data.put("msg","删除设备成功");
            logger.info("删除设备成功");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("删除设备异常！", e);
        }
        return data;
    }

    @Override
    public Map<String, Object> destroyDevice(Integer id) {
        Map<String, Object> data = new HashMap<>();
        try {
            //删除云端设备
            //获取设备ID
            BaseAdminDevice device = baseAdmindeviceMapper.getDeviceById(id);
            String deviceId = device.getDeviceId();
            logger.info("获取的设备ID:"+deviceId);
            //根据设备ID获取产品
            //BaseAdminProduct product = baseAdminProductMapper.getProductByDeviceId(deviceId);
            BaseAdminDevice product = baseAdmindeviceMapper.getDeviceByDeviceId(deviceId);
            String productId = product.getProductId();
            logger.info("获取的产品ID:"+productId);
            //根据产品ID获取MasterKey
            //QueryProductResult result = NBIoTUtils.QueryIotProduct(APP_KEY,APP_SECRET,product.getProductId());
            //String MasterKey = result.getApiKey();
            //删除云端设备
            logger.info("删除云端设备");

            //获取APP_KEY
            BaseAdminUser user = (BaseAdminUser) SecurityUtils.getSubject().getPrincipal();
            String currentUserName = user.getSysUserName();
            BaseAdminUser userResult = baseAdminUserMapper.getUserByUserName(currentUserName,null);
            String APP_KEY = userResult.getAppKey();
            String APP_SECRET = userResult.getAppSecret();

            String result = NBIoTUtils.DeleteIotDevice(APP_KEY,APP_SECRET,productId,MasterKey,deviceId);
            //删除数据库设备
            baseAdmindeviceMapper.destroyDevice(id);
            if(result.equals("false")){
                data.put("code",0);
                data.put("msg","删除设备失败");
                logger.error("删除设备失败");
                return data;
            }
            data.put("code",1);
            data.put("msg","删除设备成功");
            logger.info("删除设备成功");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("删除设备异常！", e);
        }
        return data;
    }

    /*恢复设备*/
    @Override
    public Map<String, Object> recoverDevice(Integer id, Integer status) {
        Map<String, Object> data = new HashMap<>();
        try {
            int result = baseAdmindeviceMapper.updateDeviceStatus(id,status);
            if(result == 0){
                data.put("code",0);
                data.put("msg","恢复设备失败");
            }
            data.put("code",1);
            data.put("msg","恢复设备成功");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("恢复设备异常！", e);
        }
        return data;
    }
}
