layui.use([ 'form', 'echarts', 'step', 'layer'], function () {
    var $ = layui.$,
        form = layui.form,
        echarts = layui.echarts,
        step = layui.step,
        layer = layui.layer;

    /**wen编辑2019_11_26
     Step2步骤条中，
     1、通过点击“提交检测方式”按钮之后，将#myStep3Form表单中的内容提交到后端：detectionServlet中
        在detectionServlet的主要作用是：
        （1）如果单选框内容为空，返回{stat：false, msg:"请选择检测方式"}
        （2）根据提交的内容，进入到TrojanDetection模块中
     2、点击“下一步”按钮跳转步骤条
     3、点击“上一步”按钮，回到上一步页面
     */
    //1、“提交数据”操作
    //监听提交
    $(".step3CommitBtn").click(function () {
        //弹出层
        var index = layer.msg("<i class='layui-icon layui-icon-loading'></i>正在进行木马检测，请耐心等待",{time:-1});
        //发送ajax请求
        $.post("detectionServlet", $("#myStep3Form").serialize(), function (data) {
            var obj;
            //判断是否为json格式
            if((typeof data=='object')&&data.constructor==Object){
                obj=data;
            }else{
                obj  = eval("("+data+")");
            }
            if(!obj.sta){
                //因为用户的原因导致，后台不能检测数据
                layer.alert(obj.msg, {
                    skin: 'layui-layer-molv' //样式类名
                });
            }else{
                var resData = obj.resData;
                //只要后台执了数据检测，就展示图
                //数据展示： 展示的区域：mystep3；展示的数据obj.netInfos是一个数组，元素为对象
                $(".trStep3").show();

                //显示图表内容
                var dom1 = document.getElementById("echarts-detect"); //*
                var myChart1 = echarts.init(dom1);//*
                option1 = null;
                option1 = {
                    title : { //**
                        // text: '特征向量分布图'
                    },
                    grid: {
                        left: '3%',
                        right: '7%',
                        bottom: '3%',
                        containLabel: true
                    },
                    tooltip : { //**当鼠标放在数据上的时候，会出现提示信息
                        // trigger: 'axis'
                        showDelay : 0,
                        formatter : function (params) {
                            if (params.value.length > 1) {
                                return params.seriesName + ' :<br/>'
                                    + params.value[0] + '   '
                                    + params.value[1];
                            }
                            else {
                                return params.seriesName + ' :<br/>'
                                    + params.name + ' : '
                                    + params.value;
                            }
                        },
                        axisPointer:{
                            show: true,
                            type : 'cross',
                            lineStyle: {
                                type : 'dashed',
                                width : 1
                            }
                        }
                    },
                    toolbox: {//**Echarts的工具栏，内置有导出图片、数据视图、动态类型切换、数据区域缩放、重置五个工具
                        feature: { //各工具配置项
                            dataZoom: {},
                            brush: {
                                type: ['rect', 'polygon', 'clear']
                            }
                        }
                    },
                    brush: {
                    },
                    legend: { //后续需要修改，可以做动态数据传输
                        data: resData.legend,
                        orient: 'vertical',
                        x: 'right',
                        y: 'center'
                    },
                    xAxis : [
                        {
                            name:obj.xAxisName,//后续需要修改，可以做动态数据传输
                            type : 'value',
                            scale:true,
                            axisLabel : {
                                formatter: '{value}'
                            },
                            splitLine: {
                                show: false
                            }
                        }
                    ],
                    yAxis : [
                        {
                            name: obj.yAxisName,//后续需要修改，可以做动态数据传输
                            type : 'value',
                            scale:true,
                            axisLabel : {
                                formatter: '{value}'
                            },
                            splitLine: {
                                show: false
                            }
                        }
                    ],
                    series : [
                        {
                            name:resData.legend[0],
                            type:'scatter',
                            data: resData.sets[0]
                        },
                        {
                            name:resData.legend[1],
                            type:'scatter',
                            data: resData.sets[1]
                        }
                    ]
                };
                if (option1 && typeof option1 === "object") {
                    myChart1.setOption(option1, true);
                }

                $(".trStep3_1").show();
                $(".tdStep3_1").append(resData.msg);
                console.info(resData.msg);
                //判断是否定性检查正确
                if(!resData.stat){//定性检查错误
                    $(".noteStep3").html("error");
                }else{//定性检查正确
                    //网表是否为木马网表
                    if(resData.normal){//为普通网表
                        $(".noteStep3").html("right");
                    }else{
                        $(".trStep3_2").show();
                        $(".trStep3_3").show();
                        $(".trStep3_4").show();
                        var netSet = resData.netSet;
                        var len = netSet.length - 1;
                        for(var key in netSet){
                            if(key != len ){
                                $(".tdStep3_2").append(netSet[key] + ",  ");
                            }else {
                                $(".tdStep3_2").append(netSet[key]);
                            }
                        }
                        $(".tdStep3_3").append(resData.successRate);
                        $(".tdStep3_4").append(resData.errorRate);
                        $(".noteStep3").html("pass");
                    }
                }


            }
            //关闭弹出层
            layer.close(index);
        });
    });

    // 2、点击“下一步”按钮跳转步骤条
    /*
    1、判断$(".noteStep3")中的内容为"error"\"right"\"pass"
    (1)"error"显示弹窗："抱歉，该网表检测出现反向判断错误，可下拉页面\\"再试一次\\"或者\\"保存数据\\""
    (2)"right"显示弹窗："good，该网表检测正确，为无木马网表，可下拉页面\\"再试一次\\"或者\\"保存数据\\""
    (3)"pass"提交ajax数据，判断是否可以跳转
     */
    form.on('submit(formStep3)', function (data) {

        var note = $(".noteStep3").html();

        if(note == "pass"){
            $.get("step3PermitServlet",function (data) {
                var obj;
                //判断是否为json格式
                if((typeof data=='object')&&data.constructor==Object){
                    obj=data;
                }else{
                    obj  = eval("("+data+")");
                }
                if(obj.stat){
                    step.next('#stepForm');
                }else{
                    // console.info("点击下一步运行这里");
                    layer.alert(obj.msg, {
                        skin: 'layui-layer-molv' //样式类名
                    });
                }
            });
        }else {
            if("error" == note){
                layer.alert('抱歉，该网表检测出现反向判断错误。'+ '<br>'+'可下拉页面，查看测试数据', {
                    skin: 'layui-layer-molv' //样式类名
                });
            }else if("right" == note){
                layer.alert('good，该网表检测正确，为无木马网表。'+ '<br>'+ '可下拉页面，查看测试数据', {
                    skin: 'layui-layer-molv' //样式类名
                });
            }
            $("#myStep5").show();
        }

        return false;
    });
});