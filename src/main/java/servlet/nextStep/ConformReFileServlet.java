package servlet.nextStep;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * step1_2点击下一步按钮，判断是否满足跳转条件
 * 需要查看“.noteStep1_2”中的内容和session中reFileName的内容，并且和session中的fileName前缀相同；才可以进行跳转。
 * 返回true或false
 * @author wen
 * @date 2019/11/26 0026-14:39
 */
@WebServlet("/conformReFileServlet")
public class ConformReFileServlet extends HttpServlet {
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

        boolean accord = false;
        //1、获取提交的数据
        String commitFileName = request.getParameter("reFileName");
        if(!commitFileName.equals("") && commitFileName != null){
            //2、查看session中的数据
            HttpSession session = request.getSession();
            String reFileName = (String)session.getAttribute("reFileName");
            String fileName = (String)session.getAttribute("fileName");
            //3、获取reFileName的前缀和fileName的前缀
            String preReFileName = reFileName.substring(0, reFileName.lastIndexOf("_re"));
            String prefileName = fileName.substring(0, fileName.lastIndexOf("."));
            //4、比较前缀和commitFileName和reFileName
            if(preReFileName.equals(prefileName) && reFileName.equals(commitFileName)){
                accord = true;
            }
            //***测试运行位置的代码
            System.out.println(reFileName);
            System.out.println(fileName);
            System.out.println(preReFileName);
            System.out.println(prefileName);

            //5、为下一步Step2的特征提取的“下一步”按钮设置判断条件
            session.setAttribute("permit", false);
        }

        //6、返回数据处理
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Boolean> map = new HashMap<>();
        map.put("accord", accord);
        String json = mapper.writeValueAsString(map);
        response.getWriter().write(json);
    }
}
