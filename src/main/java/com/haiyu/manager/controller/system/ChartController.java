package com.haiyu.manager.controller.system;

import com.haiyu.manager.dao.BaseAdminChartMapper;
import com.haiyu.manager.dao.ChartTestMapper;
import com.haiyu.manager.dto.ChartSearchDTO;
import com.haiyu.manager.iot.JsonUtils;
import com.haiyu.manager.pojo.*;
import com.haiyu.manager.response.PageDataResult;
import com.haiyu.manager.service.AdminChartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("chart")
public class ChartController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AdminChartService adminChartService;
    @Autowired
    private ChartTestMapper chartTestMapper;
    @Autowired
    private BaseAdminChartMapper baseAdminChartMapper;

    /**
     *
     * 功能描述: 跳到数据管理列表
     */
    @RequestMapping("/chartManage")
    public String chartManage() {
        return "/chart/chartManage";
    }

    /**
     *
     * 功能描述: 分页查询设备列表
     */
    @RequestMapping(value = "/getChartList", method = RequestMethod.POST)
    @ResponseBody
    public PageDataResult getDeviceList(@RequestParam("pageNum") Integer pageNum,
                                        @RequestParam("pageSize") Integer pageSize,/*@Valid PageRequest page,*/ ChartSearchDTO chartSearch) {
        /*logger.info("分页查询用户列表！搜索条件：userSearch：" + userSearch + ",pageNum:" + page.getPageNum()
                + ",每页记录数量pageSize:" + page.getPageSize());*/
        //获取当前登录的用户名
/*        BaseAdminUser user = (BaseAdminUser) SecurityUtils.getSubject().getPrincipal();
        String currentUserName = user.getSysUserName();
        logger.info("当前获取的用户名为:" + currentUserName);*/
        PageDataResult pdr = new PageDataResult();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            // 获取用户列表
            pdr = adminChartService.getChartList(chartSearch, pageNum ,pageSize);
            logger.info("数据列表查询=pdr:" + pdr);

        } catch (Exception e) {
            e.printStackTrace();
            e.printStackTrace();
            logger.error("数据列表查询异常！", e);
        }
        return pdr;
    }

    @RequestMapping(value = "/createChart", method = RequestMethod.POST)
    @ResponseBody
    public String createChart(@RequestBody String data) {
        logger.info("safdsfadadfad获取到的设备为:" + data);
        //截取指定字符 原来字符"deviceName=device3" 需要 device3
        String s[] = data.split("=");
        String s1 = s[1];
        String deviceName = s1.substring(0,s1.length()-1);
        logger.info("safdsfadadfad获取到的设备为:" + deviceName);
        List<BaseAdminChart> result = baseAdminChartMapper.getChartListByDeviceName(deviceName);
        logger.info("safdsfadadfad获取到的resultList为:" + result);
        for (BaseAdminChart chart:result
        ) {
            logger.info("获取的resultList："+chart);
        }
        List list = new ArrayList();
        for (int i=0;i<result.size();i++) {
            ChartRequest chart = new ChartRequest(result.get(i).getTemperature(),result.get(i).getHumilevel(),result.get(i).getRegTime());
            logger.info("获取的chart为："+chart);
            list.add(chart);
        }
        String resultData = JsonUtils.objectToJson(list);
        System.out.println("resultData数据为："+resultData);
        return resultData;
    }



    /**
     * 图表显示数据库中元素
     * @param
     * @return
     */


    @RequestMapping(value = "/getData", method = RequestMethod.POST)
    @ResponseBody
    public String getData() {
        logger.info("设置图表数据显示！");
        List<BaseAdminChart> resultList = baseAdminChartMapper.selectAll();
/*        Map<String, Object> dataMap = new HashMap();
        for (int i=0;i<resultList.size();i++) {
            dataMap.put("temperature",resultList.get(i).getTemperature());
            dataMap.put("humilevel",resultList.get(i).getHumilevel());
            dataMap.put("regTime",resultList.get(i).getRegTime());
        }*/
        List list = new ArrayList();
        for (int i=0;i<resultList.size();i++) {
            ChartRequest chart = new ChartRequest(resultList.get(i).getTemperature(),resultList.get(i).getHumilevel(),resultList.get(i).getRegTime());
            list.add(chart);
        }
        String data = JsonUtils.objectToJson(list);
        System.out.println("data数据为："+data);
        return data;
    }


}
