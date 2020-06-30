package com.haiyu.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.haiyu.manager.common.utils.DateUtils;
import com.haiyu.manager.common.utils.NBIoTUtils;
import com.haiyu.manager.dao.BaseAdminChartMapper;
import com.haiyu.manager.dao.BaseAdminDataMapper;
import com.haiyu.manager.dao.BaseAdminUserMapper;
import com.haiyu.manager.dto.AdminChartDTO;
import com.haiyu.manager.dto.AdminDataDTO;
import com.haiyu.manager.dto.ChartSearchDTO;
import com.haiyu.manager.dto.DataSearchDTO;
import com.haiyu.manager.pojo.BaseAdminChart;
import com.haiyu.manager.pojo.BaseAdminData;
import com.haiyu.manager.pojo.BaseAdminUser;
import com.haiyu.manager.response.PageDataResult;
import com.haiyu.manager.service.AdminChartService;
import com.haiyu.manager.service.AdminDataService;
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
public class AdminChartServiceImpl implements AdminChartService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private BaseAdminChartMapper baseAdminChartMapper;

    @Autowired
    private BaseAdminUserMapper baseAdminUserMapper;
    private String APP_KEY = "OIUJOR846L4";
    private String APP_SECRET = "HJm58BlJgM";

    @Override
    public PageDataResult getChartList(ChartSearchDTO chartSearch, Integer pageNum, Integer pageSize) {
        PageDataResult pageDataResult = new PageDataResult();
        List<AdminChartDTO> baseAdminDatas = baseAdminChartMapper.getChartList(chartSearch);
        PageHelper.startPage(pageNum, pageSize);
        if (baseAdminDatas.size() != 0) {
            PageInfo<AdminChartDTO> pageInfo = new PageInfo<>(baseAdminDatas);
            pageDataResult.setList(baseAdminDatas);
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

}
