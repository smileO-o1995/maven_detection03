package servlet.nextStep;

import com.fasterxml.jackson.databind.ObjectMapper;
import service.FileExtract;

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
 * step2的“下一步”按钮提交的事件，只需要判断session中的permit值为true，则返回数据{stat:true,msg:"可以进入下一步"}
 * 否则返回数据{stat:false,msg:"请先选择特征向量"}
 * @author wen
 * @date 2019/11/27 0027-22:43
 */
@WebServlet("/step2PermitServlet")
public class Step2PermitServlet extends HttpServlet {
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

        //1、获取session中的数据permit
        HttpSession session = request.getSession();
        boolean permit = (Boolean) session.getAttribute("permit");

        Map<String, Object> map = new HashMap<>();
        //2、判断是否可以跳转
        if(permit){
            //{stat:true,msg:"可以进入下一步"}
            map.put("stat", true);
            map.put("msg", "可以进入下一步");
        }else{
            //{stat:false,msg:"请先选择特征向量"}
            map.put("stat", false);
            map.put("msg", "请先选择特征向量");
        }
        //再次将permit数据改为false，作为下一次跳转的条件
        session.setAttribute("permit", false);

        //3、返回给前端的数据
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(map);
        response.getWriter().write(json);
    }
}
