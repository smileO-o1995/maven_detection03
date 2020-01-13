package servlet.commitForm;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.NetInfo;
import service.ReConstruction;
import util.NetListRead;
import util.ReverseNetList;

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
 * step4:木马定位
 * @author wen
 * @date 2019/11/29 0029-9:56
 */
@WebServlet("/locationServlet")
public class LocationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*
         * 中文乱码的问题以及返回response的样式
         */
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf-8");

        Map<String, Object> map = new HashMap<>();

        /*
        1、获取session中的数据
        （1）获取netSet判断为木马节点的ArrayList集合（因为进入到这一步，就已经说明有检测到木马节点，所有不用判断为空）
         */
        HttpSession session = request.getSession();
        ArrayList<String> netSet2 = (ArrayList<String>) session.getAttribute("netSet2");
        NetListRead netList = (NetListRead) session.getAttribute("netList");
        ArrayList<NetInfo> netInfos = (ArrayList<NetInfo>) session.getAttribute("netInfos");
        String fileName = (String)session.getAttribute("fileName");
        String savePath = (String)session.getAttribute("savePath");

        /*
        2、将netSet提交给ReConstruction的入口函数中
         */
        ReConstruction reConstruction = new ReConstruction();
        Map<String, Object> rstData = reConstruction.constructionEnter(netSet2, netList);

        /*
        3、设置修改原网表，消除木马的触发条件
        参数：
        （1）starts: ArrayList<ArrayList<String>>木马触发电路起始节点
        （1）将starts中的节点与netInfos中的木马节点一一对应，得到ArrayList<NetInfo> startInfos
        （2）将starts中的节点可能出现的几种输出情况标记出来
        （3）读取源.v文件，对比每一个逻辑单元，然后替换掉，写入新的文件中
         */
//        ArrayList<ArrayList<String>> starts = (ArrayList<ArrayList<String>>)rstData.get("starts");
        ReverseNetList reverseNetList = new ReverseNetList();
        reverseNetList.recover(rstData,netInfos);


//        ArrayList<ArrayList<NetInfo>> startInfos = reverseNetList.correspondInfos(starts, netInfos);

//        ArrayList<ArrayList<String[]>> startOutTypes = reverseNetList.findOutType(starts);

//        try{
//            String reverseFileName = reverseNetList.reverseFile(fileName, savePath, startInfos, startOutTypes);
//            session.setAttribute("reverseFileName", reverseFileName);
//        }catch (Exception e){
//            e.printStackTrace();
//        }

        /*
        4、设置返回的数据
         */
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(rstData);
        response.getWriter().write(json);
    }
}
