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
            elem: '#dataList',
            url:'/data/getDataList',
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
                ,{field:'deviceId', title:'设备ID',align:'center'}
                ,{field:'datasetId', title:'数据名称',align:'center'}
                ,{field:'dataValue', title:'数据值',align:'center'}
                ,{field:'regTime', title: '注册时间',align:'center'}
                ,{field:'dataStatus', title: '是否有效',align:'center'}
                ,{title:'操作',align:'center', toolbar:'#optBar'}
            ]],
            done: function(res, curr, count){
                //如果是异步请求数据方式，res即为你接口返回的信息。
                //如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
                //console.log(res);
                //得到当前页码
                console.log(curr);
                $("[data-field='dataStatus']").children().each(function(){
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
        table.on('tool(dataTable)', function(obj){
            var data = obj.data;
            /*传递设备ID*/
            var deviceId = data.deviceId;
            console.log("sdfafdsa获取的设备ID"+deviceId);
            if(obj.event === 'del'){
                //删除
                delData(data,data.id,data.datasetId);
            } else if(obj.event === 'edit'){
                //编辑
                openData(data,"编辑");
            }else if(obj.event === 'recover'){
                //恢复
                recoverData(data,data.id);
            }
        });

        //监听提交
        form.on('submit(dataSubmit)', function(data){
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
    $.ajax({
        type: "POST",
        data: $("#dataForm").serialize(),
        url: "/data/setData",
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

//开通用户
function addData(){
    openData(null,"获取数据");
}
function openData(data,title){
    var datasetId = null;
    if(data==null || data==""){
        $("#id").val("");
    }else{
        $("#id").val(data.id);
        /*此处在打开新的添加栏，自动填入设备ID，#device对应输入栏（设备ID)的id*/
        $("#device").val(data.deviceId);
        $("#data").val(data.data);
        datasetId = data.datasetId;
    }
    var pageNum = $(".layui-laypage-skip").find("input").val();
    $("#pageNum").val(pageNum);
/*    $.ajax({
        url:'/role/getRoles',
        dataType:'json',
        async: true,
        success:function(data){
            $.each(data,function(index,item){
                if(!roleId){
                    var option = new Option(item.roleName,item.id);
                }else {
                    var option = new Option(item.roleName,item.id);
                    // // 如果是之前的parentId则设置选中
                    if(item.id == roleId) {
                        option.setAttribute("selected",'true');
                    }
                }
                $('#roleId').append(option);//往下拉菜单里添加元素
                form.render('select'); //这个很重要
            })
        }
    });*/
    $("#device").val('506de691ecbc45259914515bc39df452');
    layer.open({
        type:1,
        title: title,
        fixed:false,
        resize :false,
        shadeClose: true,
        area: ['550px'],
        content:$('#setData'),
        end:function(){
            cleanData();
        }
    });
}
/*删除数据*/
function delData(obj,id,datasetId) {
    /*var currentUser=$("#currentUser").html();*/
    if(null!=id){
        layer.confirm('您确定要删除'+datasetId+'数据吗？', {
            btn: ['确认','返回'] //按钮
        }, function(){
            $.post("/data/updateDataStatus",{"id":id,"status":0},function(data){
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
/*    if(null!=id){
        if(currentUser==id){
            layer.alert("对不起，您不能执行删除自己的操作！");
        }else{
            layer.confirm('您确定要删除'+name+'数据吗？', {
                btn: ['确认','返回'] //按钮
            }, function(){
                $.post("/data/updateDataStatus",{"id":id,"status":0},function(data){
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
    }*/
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
    $("#deviceId").val("");
    $("#datasetId").val("");
    $("#dataValue").val("");
    /*$('#roleId').html("");*/
}
