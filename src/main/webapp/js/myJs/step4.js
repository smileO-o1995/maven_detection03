layui.use([ 'form', 'step', 'layer'], function () {
    var $ = layui.$,
        form = layui.form,
        step = layui.step,
        layer = layui.layer;

    /**wen编辑
     * Step4步骤条
     * 1、点击“开始木马定位”之后，向后端发送ajax get请求，请求开始木马定位
     * rstData中的内容
     * rstData.put("size",susTrojanNets.size());//发现的木马个数
     rstData.put("trojans", trojans);//ArrayList<ArrayList<String>>的结构，trojan[i]:表示第i个模块中的木马节点名称
     rstData.put("susTrojanNets",susTrojanNets);//ArrayList<ArrayList<String>>的结构,susTrojanNets[i]:表示第i个模块中感染模块

     页面显示区域
     <P></P>
     <pre class="layui-code" style="width: 500px;">
     </pre>
     */
    $(".step4CommitBtn").click(function () {
        var index = layer.msg("<i class='layui-icon layui-icon-loading'></i>正在定位木马位置，请耐心等待",{time:-1});
        $.get("locationServlet", function (data) {
            var obj;
            //判断是否为json格式
            if((typeof data=='object')&&data.constructor==Object){
                obj=data;
            }else{
                obj  = eval("("+data+")");
            }
            $(".myStep4").show();
            $("#numberTro").append("检测出" + obj.size + "个木马");

            if(obj.size == 1){
                var str = '';
                for(var i = 0; i < obj.susTrojanNets[0].length; i++){
                    str = str + obj.susTrojanNets[0][i]+ '<br>';
                }
                $(".locationPre").append(str);
            }else{
                var text;
                for(var i = 0; i < obj.size; i++){
                    var rstData = obj.susTrojanNets[i];
                    var str;
                    for(var j = 0; j < obj.susTrojanNets[i].length; j++){
                        str = str + obj.susTrojanNets[i][j] + '<br>';
                    }
                    text = '<p>' + "第"+ (i+1) + "个</p>" +
                        '<div >'+ str +'</div>'
                }
                $(".locationPre").append(text);
            }
            //关闭等待提示框
            layer.close(index);

            layer.alert('诊断已完成。'+ '<br>'+ '可下拉页面，选择"再试一次"或者"保存数据"', {
                skin: 'layui-layer-molv' //样式类名
            });
            $("#mystep5").show();
        });
    });
});