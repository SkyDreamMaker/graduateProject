package com.haiyu.manager.controller.system;

import com.haiyu.manager.dto.DeviceSearchDTO;
import com.haiyu.manager.dto.UserSearchDTO;
import com.haiyu.manager.pojo.BaseAdminDevice;
import com.haiyu.manager.pojo.BaseAdminUser;
import com.haiyu.manager.response.PageDataResult;
import com.haiyu.manager.service.AdminDeviceService;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("device")
public class DeviceController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AdminDeviceService adminDeviceService;
    /**
     *
     * 功能描述: 跳到设备管理列表
     */
    @RequestMapping("/deviceManage")
    public String deviceManage() {
        return "/device/deviceManage";
    }

    @RequestMapping("/alldeviceManage")
    public String allDeviceManage() {
        return "/device/alldeviceManage";
    }

    /**
     *
     * 功能描述: 新增和更新用户设备
     *
     */
    @RequestMapping(value = "/setDevice", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> setDevice(BaseAdminDevice device) {
        logger.info("设置设备[新增或更新]！device:" + device);
        Map<String,Object> data = new HashMap();
        if(device.getId() == null){
            data = adminDeviceService.addDevice(device);
        }else{
            data = adminDeviceService.updateDevice(device);
        }
        return data;
    }

    /**
     *
     * 功能描述: 分页查询设备列表
     */
    @RequestMapping(value = "/getDeviceList", method = RequestMethod.POST)
    @ResponseBody
    public PageDataResult getDeviceList(@RequestParam("pageNum") Integer pageNum,
                                      @RequestParam("pageSize") Integer pageSize,/*@Valid PageRequest page,*/ DeviceSearchDTO deviceSearch) {
        /*logger.info("分页查询用户列表！搜索条件：userSearch：" + userSearch + ",pageNum:" + page.getPageNum()
                + ",每页记录数量pageSize:" + page.getPageSize());*/
        //获取当前登录的用户名
        BaseAdminUser user = (BaseAdminUser) SecurityUtils.getSubject().getPrincipal();
        String currentUserName = user.getSysUserName();
        logger.info("当前获取的用户名为:" + currentUserName);
        PageDataResult pdr = new PageDataResult();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            // 获取用户列表
            //pdr = adminDeviceService.getDeviceList(deviceSearch, pageNum ,pageSize);
            //pdr = adminDeviceService.getCurrentDeviceList(currentUserName,deviceSearch, pageNum ,pageSize);
            pdr = adminDeviceService.getDeviceByUserName(currentUserName,deviceSearch, pageNum ,pageSize);
            logger.info("设备列表查询=pdr:" + pdr);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("设备列表查询异常！", e);
        }
        return pdr;
    }

    /**
     *
     * 功能描述: 分页查询设备列表
     */
    @RequestMapping(value = "/getAllDeviceList", method = RequestMethod.POST)
    @ResponseBody
    public PageDataResult getAllDeviceList(@RequestParam("pageNum") Integer pageNum,
                                        @RequestParam("pageSize") Integer pageSize,/*@Valid PageRequest page,*/ DeviceSearchDTO deviceSearch) {
        /*logger.info("分页查询用户列表！搜索条件：userSearch：" + userSearch + ",pageNum:" + page.getPageNum()
                + ",每页记录数量pageSize:" + page.getPageSize());*/
        //获取当前登录的用户名
        BaseAdminUser user = (BaseAdminUser) SecurityUtils.getSubject().getPrincipal();
        String currentUserName = user.getSysUserName();
        logger.info("当前获取的用户名为:" + currentUserName);
        PageDataResult pdr = new PageDataResult();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            // 获取用户列表
            pdr = adminDeviceService.getDeviceList(deviceSearch, pageNum ,pageSize);
            logger.info("设备列表查询=pdr:" + pdr);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("设备列表查询异常！", e);
        }
        return pdr;
    }

    /**
     * 功能描述: 删除/恢复设备
     */
    @RequestMapping(value = "/updateDeviceStatus", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateUserStatus(@RequestParam("id") Integer id,@RequestParam("status") Integer status) {
        logger.info("删除/恢复设备！id:" + id+" status:"+status);
        Map<String, Object> data = new HashMap<>();
        if(status == 0){
            //删除用户
            data = adminDeviceService.delDevice(id,status);
        }else{
            //恢复用户
            data = adminDeviceService.recoverDevice(id,status);
        }
        return data;
    }

    //彻底删除设备（包括云端）
    /**
     * 功能描述: 删除/恢复设备
     */
    @RequestMapping(value = "/deleteDevice", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deleteDevice(@RequestParam("id") Integer id) {
        logger.info("销毁设备！id:" + id);
        Map<String, Object> data = new HashMap<>();
            //删除用户
        data = adminDeviceService.destroyDevice(id);
        return data;
    }

}
