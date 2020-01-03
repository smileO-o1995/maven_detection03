package util;

import domain.NetInfo;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wen
 * @date 2020/1/3 0003-15:36
 */
public class ReverseNetList {
    /**
     * 将ArrayList<String> starts中的所有节点与ArrayList<NetInfo> netInfos一一对应，得到ArrayList<NetInfo> startsInfos
     */
    public ArrayList<NetInfo> correspondInfos(ArrayList<String> starts, ArrayList<NetInfo> netInfos){
        ArrayList<NetInfo> startInfos = new ArrayList<>();
        for(String str : starts){
            for(NetInfo netInfo: netInfos) {
                String netName = netInfo.getNetName();
                if (str.equals(netName)) {
                    startInfos.add(netInfo);
                    break;
                }
            }
        }
        return startInfos;
    }

    /**
     * 节点作为输出节点可能有以下几种情况：Q \ QN \ SO \ C1 \ S \ CO
     * @param starts
     * @return
     */
    public ArrayList<String[]> findOutType(ArrayList<String> starts){
        ArrayList<String[]> startOutTypes = new ArrayList<>();
        for(String str : starts){
            String[] sets = new String[6];
            sets[0] = ".Q(" + str + ")";
            sets[1] = ".QN(" + str + ")";
            sets[2] = ".SO(" + str + ")";
            sets[3] = ".C1(" + str + ")";
            sets[4] = ".S(" + str + ")";
            sets[5] = ".CO(" + str + ")";
            startOutTypes.add(sets);
        }
        return startOutTypes;
    }

    /**
     * 重新读取一遍网表文件，通过对比网表文件的每条逻辑单元语句
     * （1）如果该语句是以startInfos中的元素作为输出，则删除该语句
     * （2）如果该语句有startInfos中的元素作为输入，则将该元素用1'b0或者1'b1替换
     * @param fileName：网表的名称
     * @param savePath：网表的存储路径
     * @param startInfos：木马触发电路的起始节点
     * @param startOutType：这些起始节点可能对应的输出模式
     */
    public void reverseFile(String fileName, String savePath,
                            ArrayList<NetInfo> startInfos, ArrayList<String[]> startOutType) throws Exception{
        //1、读文件流
        BufferedReader bReader = new BufferedReader(new InputStreamReader(new FileInputStream(savePath + fileName)));
        //2、写文件
        // 文件写入流
        String reFile = fileName.substring(0, fileName.lastIndexOf(".")) + "_reverse.txt";
        File reCont = new File(savePath, "\\" + reFile);
        //***测试运行位置的代码
        System.out.println(savePath + "\\" + reFile);
        try {
            if (!reCont.exists()) {
                reCont.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        PrintWriter pWriter = new PrintWriter(reCont);

		/*3、读取文件
		（1）去掉特殊字符，除空格外
		（2）去除注释
		（3）一个content表示逻辑门实例语句
		 */
        String line1; // 该容器存储每次读取（读取一行）的数据
        String content = ""; // 该容器用于存储每一个完整的语句（使用“;”作为隔离）
        int endLabel = -1; // 用于标记content中是否出现语句结束标志
        while ((line1 = bReader.readLine()) != null) {
            //（1）去掉特殊字符
            String line = line1.trim(); // 去掉首尾的空格符
            Pattern pattern = Pattern.compile("\\t|\\r|\\n"); // 暂时保留空格符，去掉其他特殊转义字符
            Matcher matcher = pattern.matcher(line);
            String dest = matcher.replaceAll("");//dest为该行数据去掉其他特殊字符后得到的字符串
            //（2）去掉注释
            int note = dest.indexOf("//");
            // 读到注释行时，清空注释符以及注释符以后的数据
            if (note != -1) {
                dest = dest.substring(0, note);
            }

            //（3）当dest为endmodule语句的时候，直接将该语句写入到新的文件中
            if (dest.indexOf("endmodule") != -1) {
                pWriter.println(dest);
                pWriter.flush();
                continue;
            }
            //（4）拼装一个完整的逻辑门语句
            content += dest;

            //（5）进行对比和替换
            endLabel = dest.indexOf(";");
            if (endLabel != -1) {
                /*
                 * 当出现content结束标志的时候，需要做如下事情：
                 * 1）判断content的输出是否是startOutType中的元素，如果有，则忽略该content
                 * 1）if如果出现startOutType中的元素，则将该语句删除
                 * 2）else{
                 *      if出现startInfos.getName()中的元素，则将该元素替换为1'b0或者1'b1两种常数的形式
                 * }
                 */
                boolean isCorrespond = false;
                for(int i = 0; i < startOutType.size(); i++){
                    String[] set = startOutType.get(i);
                    for(int j = 0; j < set.length; j++){
                        isCorrespond = isCorrespond || content.contains(set[j]);
                    }
                }

                if(!isCorrespond){
                    for(NetInfo netInfo : startInfos){
                        String netName = "(" + netInfo.getNetName() + ")";
                        if(content.contains(netName)){
                            int CC0 = netInfo.getCC0();
                            int CC1 = netInfo.getCC1();
                            String reStr = "";
                            if(CC1 > 0 && CC0 > 0){
                                reStr = CC1 < CC0 ? "1'b1" : "1'b0";
                            }else if(CC0 < 0){
                                reStr = "1'b1";
                            }else{
                                reStr = "1'b0";
                            }
                            reStr = "(" + reStr + ")";
                            content.replace(netName, reStr);
                        }
                    }
                    pWriter.println(content);
                    pWriter.flush();
                }
                content = "";
            }
        }

        bReader.close();
        pWriter.close();
    }

}
