package com.haiyu.manager.dao;


import com.haiyu.manager.pojo.ChartTest;
import org.springframework.stereotype.Repository;
import tk.mapper.MyMapper;

@Repository
public interface ChartTestMapper extends MyMapper<ChartTest> {

    /*查询列表*/
    //List<AdminChartDTO> getChartList(ChartSearchDTO chartSearchDTO);

}
