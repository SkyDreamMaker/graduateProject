//提交表单
window.onload = function (){
    //指定图表的配置项和数据
    var myChart2 = echarts.init(document.getElementById('chartmain'));
    myChart2.showLoading();
    $.ajax({
        type: "POST",
        url: "/chart/getData",
        async: true,
        dataType: "json",
        success: function (result) {
            //console.log("获取的result信息："+result);
            /*将json数据转换为对象*/
            var obj = eval(result);
            var humilevel = new Array();
            var temperature = new Array();
            var regTime = new Array();
            for(var i in obj)
            {
                //console.log("对象的温度"+obj[i].temperature);
                temperature[i] = obj[i].temperature;
                //console.log("对象的湿度"+obj[i].humilevel);
                humilevel[i] = obj[i].humilevel;
                //console.log("对象的时间"+obj[i].regTime);
                regTime[i] = obj[i].regTime;
            }
            for (var j=0;j<humilevel.length;j++)
            {
                //console.log("humilevel数组值为："+humilevel[j]);
            }

            myChart2.hideLoading();//隐藏加载
            myChart2.setOption({
                title: {
                    text: '温湿度数据分析'
                },
                tooltip: {
                    trigger: 'axis'
                },
                legend: {
                    data: ['期望温度', '温度', '期望湿度', '湿度']
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                toolbox: {
                    feature: {
                        saveAsImage: {}
                    }
                },
                xAxis: {
                    type: 'category',
                    boundaryGap: false,
                    //data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日']
                     data: regTime
                },
                yAxis: {
                    type: 'value'
                },
                series: [
                    {
                        name: '期望温度',
                        type: 'line',
                        stack: '总量',
                        data: [25,27,23,20,26,24,28]
                    },
                    {
                        name: '温度',
                        type: 'line',
                        stack: '总量',
                        //data: [20,22,16,14,13,12,11]
                        data: temperature
                    },
                    {
                        name: '期望湿度',
                        type: 'line',
                        stack: '总量',
                        data: [30,32,29,35,27,38,34]
                    },
                    {
                        name: '湿度',
                        type: 'line',
                        stack: '总量',
                        data: humilevel
                    }
                ]
            });
            /*刷新页面*/
            //location.reload();
        }
    });
}

