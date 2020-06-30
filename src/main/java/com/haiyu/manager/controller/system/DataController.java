package com.haiyu.manager.controller.system;

import com.haiyu.manager.dto.DataSearchDTO;
import com.haiyu.manager.dto.DeviceSearchDTO;
import com.haiyu.manager.pojo.BaseAdminData;
import com.haiyu.manager.pojo.BaseAdminDevice;
import com.haiyu.manager.pojo.BaseAdminUser;
import com.haiyu.manager.response.PageDataResult;
import com.haiyu.manager.service.AdminDataService;
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
@RequestMapping("data")
public class DataController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AdminDataService adminDataService;
    /**
     *
     * 功能描述: 跳到数据管理列表
     */
    @RequestMapping("/dataManage")
    public String deviceManage() {
        return "/data/dataManage";
    }


    /**
     *
     * 功能描述: 分页查询设备列表
     */
    @RequestMapping(value = "/getDataList", method = RequestMethod.POST)
    @ResponseBody
    public PageDataResult getDeviceList(@RequestParam("pageNum") Integer pageNum,
                                        @RequestParam("pageSize") Integer pageSize,/*@Valid PageRequest page,*/ DataSearchDTO dataSearch) {
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
            pdr = adminDataService.getDataList(dataSearch, pageNum ,pageSize);
            logger.info("设备列表查询=pdr:" + pdr);

        } catch (Exception e) {
            e.printStackTrace();
            e.printStackTrace();
            logger.error("设备列表查询异常！", e);
        }
        return pdr;
    }


    /**
     *
     * 功能描述: 新增和更新用户设备
     *
     */
    @RequestMapping(value = "/setData", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> setData(BaseAdminData data) {
        logger.info("设置设备[新增或更新]！data:" + data);
        Map<String,Object> datas = new HashMap();
        if(data.getId() == null){
            datas = adminDataService.addData(data);
        }else{
            datas = adminDataService.updateData(data);
        }
        return datas;
    }

    /**
     * 功能描述: 删除/恢复设备
     */
    @RequestMapping(value = "/updateDataStatus", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateDataStatus(@RequestParam("id") Integer id,@RequestParam("status") Integer status) {
        logger.info("删除/恢复设备！id:" + id+" status:"+status);
        Map<String, Object> data = new HashMap<>();
        if(status == 0){
            //删除用户
            data = adminDataService.delData(id,status);
        }else{
            //恢复用户
            data = adminDataService.recoverData(id,status);
        }
        return data;
    }

}
