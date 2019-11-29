package servlet.nextStep;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wen
 * @date 2019/11/25 0025-10:42
 */
@WebServlet("/conformFileServlet")
public class ConformFileServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*判断对应文件夹中是否有文件，如果没有则返回false；如果有则返回true
        如果有文件且上传的数据和文件名相同，则：{"existFile":true,"msg":可以进行下一步}
        如果有文件但是上传的数据和文件名不同，则：{"existFile":false,"msg":文件名不匹配，请刷新重试}
        如果无文件：{"existFile":false,"msg":请先选择需要测试的网表}
         */

        //设置返回的数据格式
        response.setContentType("application/json;charset=utf-8");
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = new HashMap<>();

        //get方法获得的数据
        String fileName1 = request.getParameter("fileName");
        if(fileName1.equals("")){
            map.put("existFile", false);
            map.put("msg","请先选择网表");
        }else{
            //session中获取的数据
            HttpSession session = request.getSession();
            String savePath = (String)session.getAttribute("savePath");

            //1、找到对应的路径文件夹
            File directory = new File(savePath);
            File[] listFiles = directory.listFiles();
            //2、查看文件夹中是否有文件，如果有，则为true
            if(listFiles.length == 0){
                //{"existFile":false,"msg":请先选择需要测试的网表}
                map.put("existFile", false);
                map.put("msg","请先选择网表");
            }else{
                File file = listFiles[0];
                String fileName = file.getName();
                if(fileName1.equals(fileName)){
                    //{"existFile":true,"msg":可以进行下一步}
                    map.put("existFile", true);
                    map.put("msg","可以进行下一步");
                }else{
                    //{"existFile":false,"msg":文件名不匹配，请刷新重试}
                    map.put("existFile", false);
                    map.put("msg","文件名不匹配，可能未选择网表文件");
                }
            }
        }

        String json = mapper.writeValueAsString(map);
        response.getWriter().write(json);
    }
}
