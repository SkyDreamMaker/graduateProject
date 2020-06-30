package com.haiyu.manager.service;

import com.haiyu.manager.dto.ChartSearchDTO;
import com.haiyu.manager.dto.DataSearchDTO;
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
public interface AdminChartService {

    PageDataResult getChartList(ChartSearchDTO chartSearch, Integer pageNum, Integer pageSize);


}
