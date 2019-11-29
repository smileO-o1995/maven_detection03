package service;

import kMeansUtil.DistanceCompute;
import util.ColModule;
import util.NetListRead;
import util.NetUtil;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wen
 * @date 2019/11/25 0025-19:27
 */
public class FileExtract {

    //将网表文件的节点、门、连接信息提取到文件夹中，返回的是：格式化后的文件名字
    public Map<String, Object> gateExtract(String fileName, String savePath) throws IOException {
        //1、获取filePath文件夹中的唯一文件名
        //***测试运行位置的代码
        File file = new File(savePath + "\\" + fileName);
        System.out.println("运行到service层");

        //2、调整网表文件的格式
        ColModule cModule = new ColModule();
        cModule.readModule(file);
        System.out.println("完成对网表文件的调整");

        //3、对网表文件进行预处理，使得到的网表的格式为：平铺格式，每行数据表示一个完整的语句，（没有分号）
        String reFile = cModule.ResetFile(file, savePath);
        //***测试运行位置的代码
        System.out.print(reFile);

        //4、提取网表文件的节点、门、连接信息到saePath文件夹中
        NetListRead netList = new NetListRead(reFile, savePath);
        //***测试运行位置的代码
        System.out.println(netList);
        HashMap<String, Object> upData = new HashMap<>();
        upData.put("reFile", reFile);
        upData.put("netList", netList);

        //5、测试版本专有：获取网表中的木马触发节点
//        List<List<String>> vertexInfo = netList.vertexInfo;
        List<String> trojanNetSet = NetUtil.exactTrojanNet(netList.vertexInfo);
        upData.put("trojanNetSet", trojanNetSet);
        //6、测试版本专有：获取网表中的木马有效载荷节点
        List<String> trojanPayLoad = NetUtil.exactPayLoadNet(netList.vertexInfo);
        upData.put("trojanPayLoad", trojanPayLoad);
        return upData;
    }
}
