layui.use([ 'form', 'echarts', 'step', 'layer'], function () {
    var $ = layui.$,
        form = layui.form,
        echarts = layui.echarts,
        step = layui.step,
        layer = layui.layer;

    /**wen编辑2019_11_26
     Step2步骤条中，
     1、通过点击“提交数据”按钮提交表格数据，将选择的特征向量提交到后台，
     一种：没有提交特征向量，返回{state：false，msg:"请先选择特征向量"}
     返回的结果分为量中，一种：提交的特征向量没有对应的方法处理，则返回会{state：false，msg:"抱歉，还没有实现对应的检测方法"}
     一种为：提交的特征向量有对应的方法处理，且处理成功，则返回{state：true，msg:"特征向量提取完成"}
     在点击按钮之后，提交数据之前，需要添加一个弹出层，提示用户正在进行特征提取请耐性等待
     2、点击“下一步”按钮跳转步骤条
     3、点击“上一步”按钮，回到上一步页面
     */
    //1、“提交数据”操作
    //监听提交
    $(".step2CommitBtn").click(function () {
        //弹出层
        var index = layer.msg("<i class='layui-icon layui-icon-loading'></i>正在提取特征向量，请耐心等待",{time:-1});
        //发送ajax请求
        $.post("characterServlet", $("#myStep2Form").serialize(), function (data) {
            var obj;
            //判断是否为json格式
            if((typeof data=='object')&&data.constructor==Object){
                obj=data;
            }else{
                obj  = eval("("+data+")");
            }
            if(!obj.sta){
                layer.alert(obj.msg, {
                    skin: 'layui-layer-molv' //样式类名
                });
            }else{
                //数据展示： 展示的区域：mystep2；展示的数据obj.netInfos是一个数组，元素为对象
                $(".trStep2").show();
                //展示数据准备
                var resData = obj.resData;
                // console.info(resData);

                var dom = document.getElementById("echarts-feature"); //*
                var myChart = echarts.init(dom);//*
                option = null;
                option = {
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
                        },
                        {
                            name:resData.legend[2],
                            type:'scatter',
                            data: resData.sets[2]
                        },
                        {
                            name:resData.legend[3],
                            type:'scatter',
                            data: resData.sets[3]
                        },
                        {
                            name:resData.legend[4],
                            type:'scatter',
                            data: resData.sets[4]
                        }
                    ]
                };
                if (option && typeof option === "object") {
                    myChart.setOption(option, true);
                }

            }

            //关闭弹出层
            layer.close(index);
        });
    });

    //2、点击“下一步”按钮跳转步骤条
    form.on('submit(formStep2)', function (data) {
        $.get("step2PermitServlet",function (data) {
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
        return false;
    });
});