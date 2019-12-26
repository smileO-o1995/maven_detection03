//区别于oneStep.js的是：不使用同步的方式控制ajax请求的顺序，而是使用递归调用的方式
layui.use([ 'form', 'echarts', 'step', 'layer'], function () {
    var $ = layui.$,
        form = layui.form,
        echarts = layui.echarts,
        step = layui.step,
        layer = layui.layer;

    // $.ajaxSettings.async = false;
    /**
     * 1、按钮OneStepCommitBtn点击事件，首先判断是否可以开始进行检测
     * 判断有3：
     * （1）判断文件是否上传完成；
     * （2）判断特征选择是否满足后台需要
     * （3）判断检测方式是否满足后台需要
     */
    $("#OneStepCommitBtn").click(function (data) {
        //1、判断文件上传是否完成

        var fileName = $(".uploadNote").html();

        var formData = $("#OneStepForm").serialize();
        var index = formData.lastIndexOf("&");
        var characterName = formData.substring(0, index);
        var dctMethod = formData.substring(index + 1, formData.length);

        var count = 0;
        //1、检查是否上传了文件
        $.get("conformFileServlet",{fileName:fileName},function (data) {
            console.info("运行到1");
            var obj = myReplace(data);
            if(obj.existFile){
                count++;
                //2、判断特征选择是否在我们的能力范围内
                $.post("conformCharacterServlet", characterName, function (data1) {
                    console.info("执行到2");
                    var obj1 = myReplace(data1);
                    if(obj1.sta){
                        count++;
                        //3、判断检测方式是否可以测试
                        $.post("conformDctServlet", dctMethod, function (data2) {
                            var obj2 = myReplace(data2);
                            if(obj2.sta){
                                //可以开始检测了
                                console.info("可以开始检测了");
                                count++;
                                console.info(characterName);
                                console.info(dctMethod);

                                if(count == 3){
                                    $(".netTitleJs").html(fileName);
                                    $(".rstCardJs").show();
                                    var index = layer.msg("<i class='layui-icon layui-icon-loading'></i>正在努力检测中，请耐心等待",{time:-1});
                                    var iCount = setInterval(refresh(), 60000 * 4);

                                    //1、格式化网表
                                    $.get("formatServlet", function(data3) {
                                        var obj3 = myReplace(data3)
                                        format(obj3);
                                        //2、选择特征向量
                                        $.post("characterServlet", characterName, function (data4) {
                                            var obj4 = myReplace(data4);
                                            character(obj4);
                                            //3、选择检测方式
                                            $.post("detectionServlet", dctMethod, function (data5) {
                                                var obj5 = myReplace(data5);
                                                detection(obj5);
                                                //4、是否需要定位
                                                var note = $(".noteStep3").html();
                                                if(note == "pass"){
                                                    //定位
                                                    $.get("locationServlet", function (data6) {
                                                        var obj6 = myReplace(data6);
                                                        location(obj6);
                                                        layer.close(index);
                                                        clearInterval(iCount);
                                                    });
                                                }else{
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
                                                    layer.close(index);
                                                    clearInterval(iCount);
                                                }
                                            });
                                        });
                                    });

                                }else{
                                    console.info("count = " + count);
                                }

                            }else{
                                console.info("检测方式");
                                layer.alert(obj2.msg, {
                                    skin: 'layui-layer-molv' //样式类名
                                });
                                return;
                            }
                        });
                    }else{
                        console.info("特征选择判断");
                        layer.alert(obj1.msg, {
                            skin: 'layui-layer-molv' //样式类名
                        });
                        return;
                    }
                });
            }else{
                console.info("文件上传是否正确");
                layer.alert(obj.msg, {
                    skin: 'layui-layer-molv' //样式类名
                });
                return;
            }
        });

    });

    /**
     * 将返回数据转换为json格式
     */
    function myReplace(data) {
        if((typeof data=='object')&&data.constructor==Object){
            obj=data;
        }else{
            obj  = eval("("+data+")");
        }
        return obj;
    }

    function format(obj3) {
        var trojanNetSet = obj3.trojanNetSet;
        var netLength = trojanNetSet.length - 1;
        var trojanPayLoad = obj3.trojanPayLoad;
        var payLoadLength = trojanPayLoad.length - 1;
        if(netLength >= 0){
            $(".trStep1_2").show();//显示测试结果栏
            $(".netType").html("被木马感染");
            $(".trStep1_2_1").show();
            $(".trStep1_2_2").show();
            $(".tdStep1_2_1").html("");
            for(var key in trojanNetSet){
                if(key != netLength ){
                    $(".tdStep1_2_1").append(trojanNetSet[key] + ",  ");
                }else {
                    $(".tdStep1_2_1").append(trojanNetSet[key]);
                }
            }
            $(".tdStep1_2_2").html("");
            for(var key in trojanPayLoad){
                if(key != payLoadLength ){
                    $(".tdStep1_2_2").append(trojanPayLoad[key] + ",  ");
                }else {
                    $(".tdStep1_2_2").append(trojanPayLoad[key]);
                }
            }
        }else{//为无木马网表
            $(".trStep1_2").show();//显示测试结果栏
            $(".netType").html("无木马");
        }
    }

    function character(obj) {
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
    }

    function detection(obj) {
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
            $(".tdStep3_1").html(resData.msg);
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
                    $(".tdStep3_2").html("");
                    for(var key in netSet){
                        if(key != len ){
                            $(".tdStep3_2").append(netSet[key] + ",  ");
                        }else {
                            $(".tdStep3_2").append(netSet[key]);
                        }
                    }
                    $(".tdStep3_3").html(resData.successRate);
                    $(".tdStep3_4").html(resData.errorRate);
                    $(".noteStep3").html("pass");
                }
            }


        }
    }

    function location(obj) {
        $(".trStep4").show();
        $(".tdStep4").html("检测出" + obj.size + "个木马<br>");

        if(obj.size == 1){
            var str = '';
            for(var i = 0; i < obj.susTrojanNets[0].length; i++){
                str = str + '&emsp;&emsp;' + obj.susTrojanNets[0][i]+ '<br>';
            }
            $(".tdStep4").append(str);
        }else{
            var text;
            for(var i = 0; i < obj.size; i++){
                var rstData = obj.susTrojanNets[i];
                var str;
                for(var j = 0; j < obj.susTrojanNets[i].length; j++){
                    str = str + '&emsp;&emsp;' + obj.susTrojanNets[i][j] + '<br>';
                }
                text = '<p>' + "第"+ (i+1) + "个</p>" +
                    '<div >'+ str +'</div>'
            }
            $(".tdStep4").append(text);
        }

        layer.alert('诊断已完成。'+ '<br>'+ '可下拉页面，查看测试数据', {
            skin: 'layui-layer-molv' //样式类名
        });
        $("#myStep5").show();


        $(".uploadNote").html("");
    }

    function refresh(){
        console.info("执行刷新部分");
        $.get("refreshServlet",function (data) {
            console.info("刷新");
        });
    }

});