package util.recover;

import com.sun.org.apache.bcel.internal.generic.RETURN;
import domain.EdgeNode;
import domain.NetInfo;
import domain.VertexNode;
import location.OListDG;
import location.VaryNormalMethod;

import java.util.*;

/**
 * @author wen
 * @date 2020/1/13 0013-15:13
 */
public class RecoverUtil {
    /**
     * 获取木马电路
     * @param oListDG：携带网表结构信息
     * @param suSet：被检测为木马的节点集
     * @return ：需要展示给前端的map集合
     */
    public Map<String, Object> findTrojan(OListDG oListDG, ArrayList<String> suSet){
        VaryNormalMethod myUtil = new VaryNormalMethod();
        /*
        1、复制一个可疑信号列表
         */
        List<String> susSet_copy = new LinkedList<>(suSet);

       /*
       2、初始化模块susTrojanNets：使用双层List，同一个模块网表结构的语句放在一个内部list中（触发模块）
       使用双层list，同一模块网表的感染源放在一起injects
       使用双层list，同一模块的可疑信号放在一块
        */
        ArrayList<ArrayList<String>> injects = new ArrayList<>();
        ArrayList<ArrayList<String>> trojans = new ArrayList<>();
        ArrayList<ArrayList<String>> starts = new ArrayList<>();

       /* 重新定义
        1、trojanCells：木马触发单元
        2、injectCells: 木马感染单元
        */
        ArrayList<HashMap<String, String>> trojanCells = new ArrayList<>();
        ArrayList<HashMap<String, String>> injectCells = new ArrayList<>();
       /*
       3、当susSet_copy不为空的时候，查找每个节点对应的逻辑器件实例块
        */
        while(susSet_copy.size() != 0) {
            /*
            4、为当前木马模块创建一个内部容器
            */
            ArrayList<String> inInjects = new ArrayList<>(); //感染源信号injects
            ArrayList<String> inTrojans = new ArrayList<>();//存放当前模块的可疑信号trojans
            HashSet<String> outputAll = new HashSet<>();
            ArrayList<String> startList = new ArrayList<>();//开始的木马节点
            HashMap<String, String> trojanCell = new HashMap<>();//木马感染电路
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
               查看input集合中的元素是否都在suSet，如果都不在，则将对应的net节点保存到startList中
                */
                myUtil.getStartList(inputs, netIndex, suSet, startList, oListDG.vertexNodeList);

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
               13、将cell_net保存到trojanCell容器中
                */
                trojanCell.put(myUtil.findStr(net), cell_net);

               /*
               14、判断input和output中的元素是否在list_copy中，如果存在，则将其加入curQueue中，并将元素从list_copy中删除
               同时，(判断output是否在suSet中，如果不在那么它将是木马激励电路的输出端口:未实现)
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
            injects.add(new ArrayList<>(inInjects));
            trojans.add(new ArrayList<>(inTrojans));
            starts.add(new ArrayList<>(startList));
            trojanCells.add(trojanCell);
        }

        /**
         * 下面需要通过，广度优先遍历将木马感染的逻辑门器件找出来，
         * 将感染的木马用写入文件中
         */
        for (ArrayList<String> arrayList : injects) {
            HashMap<String, String> inject = myUtil.reConstructionInfected(arrayList,oListDG.vertexNodeList);
            injectCells.add(new HashMap<>(inject));
        }

        /**
         * 现在的数据为已知susTrojanNets模块（元素为可疑模块语句）
         * 以及可疑信号列表suSet（元素格式为：iXMIT_N_CTRL_2__28）
         */
        Map<String, Object> rstData = new HashMap<>();
        rstData.put("size",trojans.size());//发现的木马个数
        rstData.put("trojans", trojans);//ArrayList<ArrayList<String>>的结构，trojan[i]:表示第i个模块中的木马节点名称
        rstData.put("trojanCells",trojanCells);//木马触发单元
        rstData.put("injectCells", injectCells);//木马感染单元
        rstData.put("starts", starts);//起始的木马触发点

        return rstData;
    }

    /**
     * 删除掉木马激励电路
     * @param vertexNodeList：携带网表结构信息
     * @param rtnData：携带网表的木马电路信息
     * @param netInfos：携带网表节点信息
     */
    public void recover(VertexNode[] vertexNodeList, Map<String, Object> rtnData, ArrayList<NetInfo> netInfos){
        /*
        1、获取rstData中的主要参数
        （1）starts：木马触发电路的起始节点
        （2）trojanCells：木马触发单元
        （3）injectCells：木马感染单元
         */
        ArrayList<ArrayList<String>> starts = (ArrayList<ArrayList<String>>) rtnData.get("starts");
        ArrayList<HashMap<String, String>> trojanCells = (ArrayList<HashMap<String, String>>) rtnData.get("trojanCells");
        ArrayList<HashMap<String, String>> injectCells = (ArrayList<HashMap<String, String>>) rtnData.get("injectCells");
        /*
        2、当前情况仅对只有一个木马的电路进行研究
         */
        ArrayList<String> start = starts.get(0);
        HashMap<String, String> trojanCell = trojanCells.get(0);
        HashMap<String, String> injectCell = injectCells.get(0);
        for(String cell : injectCell.keySet()){
            trojanCell.put(cell, injectCell.get(cell));
        }
        /*
        3、
        （1）获取start的netInfo信息
        （2）
         */
        ArrayList<NetInfo> startInfo = getInfo(start, netInfos);
        HashSet<String> removeCell = new HashSet<>();

        start = prune(startInfo, trojanCell, removeCell, vertexNodeList);

//        while(start != null){
//            startInfo = getInfo(start, netInfos);
//            /*
//            4、通过startInfo对trojanCell和injectCell进行修剪
//            */
//            prune(startInfo, trojanCell, removeCell, vertexNodeList);
//        }
    }

    /**
     * （1）遍历start-->net,在trojanCell中找到对应的cell
     * （2）获取该cell的gateName
     * （3）将该gateName添加入removeCell中
     * （4）在trojanCell中删除掉该cell
     * （5）由vertexNodeList获取cell的下一个节点，将该节点改为1'b0或1'b1
     * @param startInfo
     * @param trojanCell
     * @param removeCell
     */
    public ArrayList<String> prune(ArrayList<NetInfo> startInfo, HashMap<String, String> trojanCell,
                      HashSet<String> removeCell, VertexNode[] vertexNodeList){
        ArrayList<String> nextNets = null;
        //遍历start
        for(NetInfo netInfo : startInfo){
            //（1）找到该netInfo对应的cell
            String netName = netInfo.getNetName();//当前节点的名称
            String gateName = netInfo.getGateName();//对应cell的名称
            //（2）在trojanCell中删除netName对应的cell
            trojanCell.remove(netName);
            //（3）将cell（gateName标志）添加入removeCell中
            removeCell.add(gateName);
            //（4）修改net的值
            String constant = changeName(netInfo);
            //（5）找到netName的下一级节点
            int netId = netInfo.getVerId();
            nextNets = findNextNets(netId, vertexNodeList);
            //（6）在vertexNodeList中删除以net为输出端的边
            removeNet(netId, vertexNodeList);
            //（7）修改net在vertexNodeList中的名称
            vertexNodeList[netId].vertex = constant;
            //（8）重写nextNets的逻辑门，更新trojanCell
            upDataTrojanGate(nextNets, netName, trojanCell, constant);
            // （9）将nextNets转换为start

        }
        return nextNets;
    }

    /**
     * 重写nextNets的逻辑门，更新trojanCell
     * @param nextNets : 需要改变的cell的key
     * @param netName ：nextNets的输入节点
     * @param trojanCell ：木马cell集合
     * @param constant ：netName对应改变的常数类型
     */
    public void upDataTrojanGate(ArrayList<String> nextNets, String netName, HashMap<String, String> trojanCell, String constant){
        for(String nextNet : nextNets){
            String cell = trojanCell.get(nextNet);
            cell = cell.replace(netName, constant);
            trojanCell.put(nextNet, cell);
        }
    }

    /**
     * 修剪vertexNode的结构，删除以netId为输出端的结构
     * @param netId ：netId边的输出端
     * @param vertexNodeList：携带网表结构信息
     * return ：修改了vertexNodeList数组的结构
     */
    public void removeNet(int netId, VertexNode[] vertexNodeList){
        if(vertexNodeList[netId].firstIn != null){
            EdgeNode edgeNode = vertexNodeList[netId].firstIn;
            removeEdge(edgeNode.tailvex, vertexNodeList, netId);
            while(edgeNode.headlink != null){
                edgeNode = edgeNode.headlink;
                removeEdge(edgeNode.tailvex,vertexNodeList, netId);
            }
        }
    }

    /**
     * 删除以某个节点为输出端的边
     * @param netId:边的输入端
     * @param vertexNodeList：网表结构信息
     * @param nextId：边的输出端
     * return：修改vertexNodeList的结构
     */
    public void removeEdge(int netId, VertexNode[] vertexNodeList, int nextId){
        if(vertexNodeList[netId].firstOut != null){
            EdgeNode edgeNode = vertexNodeList[netId].firstOut;
            if(edgeNode.headvex == nextId){
                vertexNodeList[netId].firstOut = edgeNode.taillink;
            }else{
                EdgeNode edgeNode1 = null;
                while(edgeNode.taillink != null){
                    edgeNode1 = edgeNode.taillink;
                    if(edgeNode1.headvex == nextId){
                        edgeNode.taillink = edgeNode1.taillink;
                        return;
                    }else{
                        edgeNode = edgeNode1;
                    }
                }
            }
        }
    }

    /**
     * 将节点的名称转换为常数
     * @param netInfo
     * @return
     */
    public String changeName(NetInfo netInfo){
        int CC0 = netInfo.getCC0();
        int CC1 = netInfo.getCC1();
        String reStr = "";
        if(CC1 > 0 && CC0 > 0){
            reStr = CC1 < CC0 ? "state_at_1" : " state_at_0";
        }else if(CC0 < 0){
            reStr = "state_at_1";
        }else{
            reStr = "state_at_0";
        }
        return reStr;
    }

    /**
     * 通过当前节点的id找到该节点的所有下一级节点next，cur与next的关系为输入输出节点的关系
     * @param netId ：当前节点的id
     * @param vertexNodeList ：携带网表的结构信息
     * @return ：返回当前节点的下一级节点集合
     */
    public ArrayList<String> findNextNets(int netId, VertexNode[] vertexNodeList){
        ArrayList<String> nextNets = new ArrayList<>();

        if(vertexNodeList[netId].firstOut != null) {

            EdgeNode mEdgeNode = vertexNodeList[netId].firstOut;
            nextNets.add(vertexNodeList[mEdgeNode.headvex].vertex);
            //当所指向节点在list列表中
            while(mEdgeNode.taillink != null) {
                mEdgeNode = mEdgeNode.taillink;
                nextNets.add(vertexNodeList[mEdgeNode.headvex].vertex);
            }
        }
        return nextNets;
    }

    /**
     * 通过节点集nets获取节点的信息netInfo集合
     * @param nets:要查询的节点名称net所组成的集合nets
     * @param netInfos：携带所有节点的信息
     * @return 该节点集的NetInfo信息
     */
    public ArrayList<NetInfo> getInfo(ArrayList<String> nets, ArrayList<NetInfo> netInfos){
        ArrayList<NetInfo> list = new ArrayList<>();
        for(String net : nets){
            for(NetInfo netInfo: netInfos) {
                String netName = netInfo.getNetName();
                if (net.equals(netName)) {
                    list.add(netInfo);
                    break;
                }
            }
        }
        return list;
    }
}
