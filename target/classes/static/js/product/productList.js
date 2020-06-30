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
            elem: '#productList',
            url:'/product/getProductList',
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
                ,{field:'productId', title:'产品ID',align:'center'}
                ,{field:'productName', title:'产品名称',align:'center'}
                ,{field:'apiKey', title:'apiKey',align:'center'}
                ,{field:'productType', title:'产品类型',align:'center'}
                ,{field:'regTime', title: '注册时间',align:'center'}
                ,{field:'productStatus', title: '是否有效',align:'center'}
                /*,{title:'操作',align:'center', toolbar:'#optBar'}*/
            ]],
            done: function(res, curr, count){
                //如果是异步请求数据方式，res即为你接口返回的信息。
                //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
                //console.log(res);
                //得到当前页码
                /*设置数据有效状态*/
                console.log(curr);
                $("[data-field='productStatus']").children().each(function(){
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
        table.on('tool(productTable)', function(obj){
            var data = obj.data;
            /*传递设备ID*/
        });

        //监听提交
        form.on('submit(productSubmit)', function(data){
            // TODO 校验
            formSubmit(data);
            return false;
        });

        form.on('submit(deleteSubmit)', function(data){
            // TODO 校验
            //var data = obj.data;
            delProduct(data);
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
    $.ajax({
        type: "POST",
        data: $("#productForm").serialize(),
        url: "/product/createProduct",
        success: function (data) {
            if (data.code == 1) {
                layer.alert(data.msg,function(){
                    layer.closeAll();
                    load(obj);

                });
            } else {
                layer.alert(data.msg);
            }
            /*刷新页面*/
            location.reload();
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


function getProduct(){
   /*alert("获取产品信息测试");*/
    /*刷新页面*/
   /* location.reload();*/
   //layer.alert("产品信息已更新！")
    $.ajax({
        type: "POST",
        data: $("#productForm").serialize(),
        url: "/product/getProduct",
        success: function (data) {
            if (data.code == 1) {
                layer.alert(data.msg,function(){
                    layer.closeAll();
                    load(obj);
                    layer.alert("获取到新的产品信息")
                });
            } else {
                layer.alert(data.msg);
            }
            /!*刷新页面*!/
            location.reload();
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
function addProduct(){
    openData(null,"创建产品");
}
function openData(data,title){

    /*此处在打开新的添加栏，自动填入设备ID，#device对应输入栏（设备ID)的id*/
    $("#productType").val("智能生活");
    $("#secondType").val("家居安防");
    $("#thirdType").val("温感报警器");

    layer.open({
        type:1,
        title: title,
        fixed:false,
        resize :false,
        shadeClose: true,
        area: ['550px'],
        content:$('#setProduct'),
        end:function(){
            cleanData();
        }
    });
}
function deleteProduct(){
    layer.open({
        type:1,
        title: "删除产品",
        fixed:false,
        resize :false,
        shadeClose: true,
        area: ['550px'],
        content:$('#productDelete'),
        end:function(){
            cleanData();
        }
    });
}

/*删除数据*/
function delProduct(data) {
    var str = $("#productForm2").serialize();
    var strs= new Array();
    strs=str.split("="); //字符分割
    var productId = strs[1];
    console.log(productId);
    layer.confirm('您确定要删除'+productId+'数据吗？', {
        btn: ['确认','返回'] //按钮
    }, function(){
        $.post("/product/deleteProduct",{"productId":productId},function(data){
            if (data.code == 1) {
                layer.alert(data.msg,function(){
                    layer.closeAll();
                    load(obj);
                });
            } else {
                layer.alert(data.msg);
            }
        });
    }, function(){
        layer.closeAll();

    });

}
//恢复
function recoverData(obj,id) {
    if(null!=id){
        layer.confirm('您确定要恢复吗？', {
            btn: ['确认','返回'] //按钮
        }, function(){
            $.post("/data/updateDataStatus",{"id":id,"status":1},function(data){
                if (data.code == 1) {
                    layer.alert(data.msg,function(){
                        layer.closeAll();
                        load(obj);
                    });
                    //location.reload();
                } else {
                    layer.alert(data.msg);
                }
            });
        }, function(){
            layer.closeAll();
        });
    }
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

function cleanData(){
/*    $("#productId").val("");
    $("#productName").val("");
    $("#deviceId").val("");*/
    /*$('#roleId').html("");*/
}
