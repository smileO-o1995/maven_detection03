package service;

import location.OListDG;
import location.VaryNormalMethod;
import util.NetListRead;

import java.util.*;

/**
 * @author wen
 * @date 2019/11/29 0029-10:12
 */
public class ReConstruction {
   public Map<String, Object> constructionEnter(ArrayList<String> suSet, NetListRead netList){
        VaryNormalMethod myUtil = new VaryNormalMethod();
        OListDG oListDG = new OListDG(netList.vertexInfo, netList.edgesInfo);
        /*
        1、复制一个可疑信号列表
         */
       List<String> susSet_copy = new LinkedList<>(suSet);

       /*
       2、初始化模块susTrojanNets：使用双层List，同一个模块网表结构的语句放在一个内部list中（触发模块）
       使用双层list，同一模块网表的感染源放在一起injects
       使用双层list，同一模块的可疑信号放在一块
        */
       List<List<String>> susTrojanNets = new ArrayList<>();
       ArrayList<ArrayList<String>> injects = new ArrayList<>();
       ArrayList<ArrayList<String>> trojans = new ArrayList<>();

       /*
       3、当susSet_copy不为空的时候，查找每个节点对应的逻辑器件实例块
        */
       while(susSet_copy.size() != 0) {
            /*
            4、为当前木马模块创建一个内部容器
            */
            List<String> inNets = new ArrayList<>(); //存放当前模块的cell
            ArrayList<String> inInjects = new ArrayList<>(); //激励模块
            ArrayList<String> inTrojans = new ArrayList<>();//存放当前模块的可疑信号
            HashSet<String> outputAll = new HashSet<>();

            /*
            5、从suSet集合中取出又放回suSet集合中，使用marked数组实现，然后将取到的点放到curQueue队列中
             */
           Queue<String> curQueue = new LinkedList<>();
           curQueue.offer(susSet_copy.get(0));
           susSet_copy.remove(0);

           /*
           6、curQueue不为空的时候
            */
           while(!curQueue.isEmpty()) {
               /*
               9、从curQueue中取出不放回一个元素net
                */
               String net = curQueue.poll();
               inTrojans.add(net);
               //找到当前对象的索引号netIndex
               int netIndex = myUtil.findIndex(net);

               /*
               10、求net元素的输入节点集合inputs
                */
               HashMap<Integer, String> inputs = myUtil.inputList(netIndex, oListDG.vertexNodeList);

               /*
               11、求net元素的输出节点集合outputs
                */
               List<String> outputs = myUtil.outputList(netIndex,oListDG.vertexNodeList, outputAll);

               /*
               12、求net元素对应的逻辑单元cell_m
                */
               String cell_net = myUtil.refoundCell(netIndex, inputs, oListDG.vertexNodeList);
               System.out.println(cell_net);

               /*
               13、将cell_net保存到inNets容器中
                */
               inNets.add(cell_net);

               /*
               14、判断input和output中的元素是否在list_copy中，如果存在，则将其加入curQueue中，并将元素从list_copy中删除
               同时，判断output是否在suSet中，如果不在那么它将是木马激励电路的输出端口
                */
               myUtil.addToCurQueue(inputs, outputs, susSet_copy, curQueue);
           }

           /*
           15、判断outputAll集合中不存在于inTrojans的信号就是感染源信号
            */
           myUtil.findInjects(outputAll, inTrojans, inInjects);

           /*
           16、将感染源和感染模块加入外部容器中
            */
           susTrojanNets.add(new ArrayList<>(inNets));
           injects.add(new ArrayList<>(inInjects));
           trojans.add(new ArrayList<>(inTrojans));
       }

       /**
        * 下面需要通过，广度优先遍历将木马感染的逻辑门器件找出来，
        * 将感染的木马用写入文件中
        */
       ArrayList<ArrayList<String>> injectedCell = new ArrayList<>();
       for (ArrayList<String> arrayList : injects) {
           ArrayList<String> inject = myUtil.reConstructionInfected(arrayList,oListDG.vertexNodeList);
           injectedCell.add(new ArrayList<>(inject));
       }

       /**
        * 现在的数据为已知susTrojanNets模块（元素为可疑模块语句）
        * 以及可疑信号列表suSet（元素格式为：iXMIT_N_CTRL_2__28）
        */
       Map<String, Object> rstData = new HashMap<>();
       rstData.put("size",susTrojanNets.size());//发现的木马个数
       rstData.put("trojans", trojans);//ArrayList<ArrayList<String>>的结构，trojan[i]:表示第i个模块中的木马节点名称
       rstData.put("susTrojanNets",susTrojanNets);//ArrayList<ArrayList<String>>的结构,susTrojanNets[i]:表示第i个模块中感染模块

       //16、查看susTrojanNets的大小，可以找到木马模块的个数
       System.out.println("  可疑模块的个数：" + susTrojanNets.size());

       for (int i = 0; i < susTrojanNets.size(); i++) {
           System.out.println("  可疑模块" + (i+1) + " ——可疑信号:");
           for (int j = 0; j < trojans.get(i).size(); j++) {
               String string = trojans.get(i).get(j);
               System.out.print(myUtil.findStr(string) + ",");
           }
           System.out.println();
           System.out.println("  可疑模块" + (i+1) + " ——可疑模块:");
           for (int j = 0; j < susTrojanNets.get(i).size(); j++) {
               System.out.println(susTrojanNets.get(i).get(j));
           }
           System.out.println("  可疑模块" + (i+1) + " ——感染源:");
           for (int j = 0; j < injects.get(i).size(); j++) {
               String string = injects.get(i).get(j);
               System.out.print(myUtil.findStr(string) + ",");
           }
           System.out.println();
           System.out.println("  可疑模块" + (i+1) + " ——感染模块:");
           for (int j = 0; j < injectedCell.get(i).size(); j++) {
               System.out.println(injectedCell.get(i).get(j));
           }
           System.out.println();
       }
       System.out.println();

       return rstData;
   }

}
