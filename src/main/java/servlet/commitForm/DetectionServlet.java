package servlet.commitForm;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.NetInfo;
import domain.NetResult;
import service.ResultService;
import service.ResultServiceImpl;
import service.TrojanDetection;
import sun.nio.ch.Net;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * step3:木马检测
 * （1）如果单选框内容为空，返回{stat：false, msg:"请选择检测方式"}
 * （2）根据提交的内容，进入到TrojanDetection模块中
 * @author wen
 */
@WebServlet("/detectionServlet")
public class DetectionServlet extends HttpServlet {
    /**
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*
         * 中文乱码的问题以及返回response的样式
         */
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf-8");

        //1、设置返回数据格式
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = new HashMap<>();

        //2、获取提交的表单数据，即多选框对象
        String dctMethod = request.getParameter("dctMethod");
        if(dctMethod == null || dctMethod == ""){
            //***测试运行位置的代码
            System.out.println("提交的数据为空");

            //（1）选择特征向量{sta：false，msg:"请先选择特征向量"}
            map.put("sta", false);
            map.put("msg", "请先选择检测方法");
            String json = mapper.writeValueAsString(map);
            response.getWriter().write(json);
            return;
        }
        //***测试运行位置的代码
        System.out.println(dctMethod);

        //3、获取session中的数据
        HttpSession session = request.getSession();
        ArrayList<NetInfo> netInfos = (ArrayList<NetInfo>) session.getAttribute("netInfos");
        String fileName = (String)session.getAttribute("fileName");

        //4、根据characterNames的不同，进行不同的数据处理（这里需要后序再来完善，这里只有一种，即提取CC0/CC1/CO的值）
        if("kmeans".equals(dctMethod)){
            //4.1将数据netInfos提交给TrojanDetection类中
            TrojanDetection detection = new TrojanDetection();
            Map<String,Object> resData = detection.detectEnter(netInfos);

            //(4.4)将检测数据传入数据库，首先在这里封装对象
            NetResult netResult = (NetResult)resData.get("netResult");
            resData.remove("netResult");
            netResult.setNetlistName(fileName);
            netResult.setMethod("kmeans");
            ResultService save = new ResultServiceImpl();
            save.saveNetResult(netResult, "test_count");

            //再次封装修改后的netInfos数据
            session.setAttribute("netInfos",netInfos);
            //并将netSet中的数据也写入到session中
            session.setAttribute("netSet2",resData.get("netSet2"));
            resData.remove("netSet2");

            //封装返回的数据{sta：true，特征向量的数据集合}
            map.put("sta", true);
            //（4.2）封装横纵坐标轴名称
            map.put("xAxisName", "CC");
            map.put("yAxisName", "CO");
            //（4.3）封装legend数据和seriesData数据
            map.put("resData", resData);



        }else{

            //放回数据给用户，告诉用户所选择的特征向量所对应的检测功能还未完善{sta：false，msg:"抱歉，还没有实现对应的检测方法"}
            map.put("sta", false);
            map.put("msg","抱歉，还没有实现对应的检测方法");
            //***测试运行位置的的代码
            System.out.println("没有进入到service层");
        }
        //打开“下一步”的跳转限制
        session.setAttribute("permit", true);

        String json = mapper.writeValueAsString(map);
        response.getWriter().write(json);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
