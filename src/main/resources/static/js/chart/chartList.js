/**
 * 用户管理
 */
var pageCurr;
var form;
$(function() {
    layui.use('table', function(){
        var table = layui.table;
        form = layui.form;

        tableIns=table.render({
            elem: '#chartList',
            url:'/chart/getChartList',
            method: 'post', //默认：get请求
            cellMinWidth: 80,
            page: true,
            request: {
                pageName: 'pageNum', //页码的参数名称，默认：pageNum
                limitName: 'pageSize' //每页数据量的参数名，默认：pageSize
            },
            response:{
                statusName: 'code', //数据状态的字段名称，默认：code
                statusCode: 200, //成功的状态码，默认：0
                countName: 'totals', //数据总数的字段名称，默认：count
                dataName: 'list' //数据列表的字段名称，默认：data
            },
            cols: [[
                {type:'numbers'}
                ,{field:'deviceName', title:'设备名',align:'center'}
                ,{field:'deviceId', title:'设备ID',align:'center'}
                ,{field:'regTime', title: '时间',align:'center'}
                ,{field:'temperature', title: '温度',align:'center'}
                ,{field:'humilevel', title: '湿度',align:'center'}
            ]],
            done: function(res, curr, count){
                //如果是异步请求数据方式，res即为你接口返回的信息。
                //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
                //console.log(res);
                //得到当前页码
                console.log(curr);
                $("[data-field='deviceStatus']").children().each(function(){
                    if($(this).text()=='1'){
                        $(this).text("有效")
                    }else if($(this).text()=='0'){
                        $(this).text("失效")
                    }
                });
                //得到数据总量
                //console.log(count);
                pageCurr=curr;
            }
        });

        //监听工具条
        table.on('tool(chartTable)', function(obj){
            var data = obj.data;
            if(obj.event === 'del'){
                //删除
                delDevice(data,data.id,data.sysUserName);
            } else if(obj.event === 'edit'){
                //编辑
                openDevice(data,"编辑");
            }else if(obj.event === 'recover'){
                //恢复
                recoverDevice(data,data.id);
            }
        });

        //监听提交
        form.on('submit(chartSubmit)', function(data){
            // TODO 校验
            formSubmit(data);
            return false;
        });
    });

    //搜索框
    layui.use(['form','laydate'], function(){
        var form = layui.form ,layer = layui.layer
            ,laydate = layui.laydate;
        //日期
        laydate.render({
            elem: '#startTime'
        });
        laydate.render({
            elem: '#endTime'
        });
        //TODO 数据校验
        //监听搜索框
        form.on('submit(searchSubmit)', function(data){
            //重新加载table
            load(data);
            return false;
        });
    });
});
//提交表单
function formSubmit(obj){
    console.log("提交表单！");
    console.log("获取的deviceName为："+$("#chartForm").serialize());
    console.log("获取的deviceName为："+$("#deviceName").serialize());
    $.ajax({
        type: "POST",
        data: JSON.stringify($("#deviceName").serialize()),
        contentType :'application/json',
        url: "/chart/createChart",
        async: true,
        dataType:'json',
        success: function (data) {
            console.log("获取到返回数据"+data);
            if(data!=null && data!=""){
                generationChart(data);
                layer.closeAll();
            } else {
                layer.alert("出错了");
            }
            /*刷新页面*/
            /*location.reload();*/
        },
        error: function () {
            layer.alert("操作请求错误，请您稍后再试",function(){
                layer.closeAll();
                //加载load方法
                load(obj);//自定义
            });
        }
    });
}

//开通用户
function addDevice(){
    openChart(null,"开通设备");
}

function createChart(){
    alert("创建图表");
    openChart(null,"创建图表");

}
function openChart(data,title){
    if(data==null || data==""){
        $("#id").val("");
    }else{
        $("#id").val(data.id);
        $("#deviceName").val(data.deviceName);
    }

    layer.open({
        type:1,
        title: title,
        fixed:false,
        resize :false,
        shadeClose: true,
        area: ['550px'],
        content:$('#setChart'),
        end:function(){
            cleanChart();
        }
    });
}

function load(obj){
    //重新加载table
    tableIns.reload({
        where: obj.field
        , page: {
            curr: pageCurr //从当前页码开始
        }
    });
}

function cleanChart(){
    $("#deviceName").val("");
}
function generationChart(result){
    //指定图表的配置项和数据
    var myChart2 = echarts.init(document.getElementById('chartmain'));
    // myChart2.showLoading();
    //console.log("获取的result信息："+result);
    /*将json数据转换为对象*/
    var obj = eval(result);
    var humilevel = new Array();
    var temperature = new Array();
    var regTime = new Array();
    for(var i in obj)
    {
        console.log("对象的温度"+obj[i].temperature);
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
    // myChart2.hideLoading();//隐藏加载
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
