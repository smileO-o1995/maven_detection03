package location;

import domain.EdgeNode;
import domain.VertexNode;

import java.util.*;

/**
 * @author wen
 * @date 2019/11/29 0029-11:22
 */
public class VaryNormalMethod {
    /**
     * str字符串的格式为：iXMIT_N_CTRL_2__28
     *  最后出现“_”后连接的数字表示该节点在vertexNodeList中的索引号
     *  我们的目的解释获得"28"并将其转换为整型
     * @param str
     * @return
     */
    public int findIndex(String str) {
        int lastUnderline = str.lastIndexOf("_");
        String index = str.substring(lastUnderline + 1);
        return Integer.parseInt(index);
    }

    /**
     * str字符串的格式为：iXMIT_N_CTRL_2__28
     *  最后出现“_”后连接的前的字符表示该节点在vertexNodeList中的真实名称
     * @param str
     * @return
     */
    public String findStr(String str) {
        int lastUnderline = str.lastIndexOf("_");
        String index = str.substring(0,lastUnderline);
        return index;
    }

    /**
     * 找到一个节点的所有输入节点
     * 返回的是一个map对象，key为索引值，value为.IN2(iXMIT_state_1_temp)的类型
     *
     * 边界条件是：该节点没有输入节点，返回的值为null
     */
    public HashMap<Integer, String> inputList(int index, VertexNode[] vertexNodeList) {
        HashMap<Integer,String> map = new HashMap<>();

        if(vertexNodeList[index].firstIn != null) {
            EdgeNode edgeNode = vertexNodeList[index].firstIn;
            int tailvex = edgeNode.tailvex;
            map.put(tailvex, "." +edgeNode.inStation + "(" + vertexNodeList[tailvex].vertex + ")");
            while(edgeNode.headlink != null) {
                edgeNode = edgeNode.headlink;
                tailvex = edgeNode.tailvex;
                map.put(tailvex, "." +edgeNode.inStation + "(" + vertexNodeList[tailvex].vertex + ")");
            }
        }
        return map;
    }

    /**
     * 找到一个节点的输出节点
     * 返回格式为：iXMIT_N_CTRL_2__28
     * 边界条件是：该节点没有输出节点，返回值为null
     * @param index
     * @param vertexNodeList
     * @param outputAll
     * @return
     */
    public List<String> outputList(int index, VertexNode[] vertexNodeList, HashSet<String> outputAll) {
        List<String> list = new ArrayList<>();

        if(vertexNodeList[index].firstOut != null) {
            EdgeNode edgeNode = vertexNodeList[index].firstOut;
            int headvex = edgeNode.headvex;
            String output = vertexNodeList[headvex].vertex + "_" + headvex;
            list.add(output);
            outputAll.add(output);
            while(edgeNode.taillink != null) {
                edgeNode = edgeNode.taillink;
                headvex = edgeNode.headvex;
                output = vertexNodeList[headvex].vertex + "_" + headvex;
                list.add(output);
                outputAll.add(output);
            }
        }
        return list;
    }

    /**
     * 重构netIndex对应的逻辑单元
     * NAND3X4 TjPayload1 ( .IN1(WX547), .IN2(Tj_Trigger), .IN3(Stage4), .QN(Stage1_1));
     * @param netIndex
     * @param inputs
     * @param vertexNodeList
     * @return
     */
    public String refoundCell(int netIndex, HashMap<Integer, String> inputs, VertexNode[] vertexNodeList) {
        StringBuilder input = new StringBuilder();
        //添加一个实例的所有输入单元节点
        for(Integer key : inputs.keySet()) {
            input.append(inputs.get(key) + ", ");
        }
        //添加一个实例的输出节点
        input.append("." + vertexNodeList[netIndex].verType + "(" + vertexNodeList[netIndex].vertex + ")");

        //添加该实例的索引reference
        String cell = vertexNodeList[netIndex].gateType + " (" + input.toString() + ");";
        return cell;
    }

    /**
     *
     * @param susSet_copy
     * @param inputs：是一个Map，其格式为.IN2(iXMIT_state_1_temp)
     * @param outputs：是一个list，其格式与iXMIT_N_CTRL_2__28的格式一致
     * @param curQueue
     */
    public void addToCurQueue(HashMap<Integer, String> inputs, List<String> outputs, List<String> susSet_copy, Queue<String> curQueue) {
        for(int key : inputs.keySet()) {
            String str = inputs.get(key);
            str = str.substring(str.indexOf("(") + 1, str.indexOf(")")) + "_" + key;
            if(susSet_copy.contains(str)) {
                curQueue.offer(str);
                susSet_copy.remove(str);
            }
        }
        for(String string : outputs) {
            if(susSet_copy.contains(string)) {
                curQueue.offer(string);
                susSet_copy.remove(string);
            }
        }
    }

    /**
     * 向当前模块的感染源容器中添加信号
     * @param outputAll:当前模块中所有节点的输出节点
     * @param inTrojans：当前模块中的所有节点
     * @param inInjects：感染源信号，需要将outputAll中，不同于inTrojans的信号全部找出来
     */
    public void findInjects(HashSet<String> outputAll, ArrayList<String> inTrojans, ArrayList<String> inInjects) {
        for (String string : outputAll) {
            if(!inTrojans.contains(string)) {
                inInjects.add(string);
            }
        }
    }

    /**
     * 通过可疑木块和感染信号源查找 感染模块
     * @param injects
     * @param vertexNodeList
     * @return
     */
    public ArrayList<String> reConstructionInfected(ArrayList<String> injects, VertexNode[] vertexNodeList) {

        ArrayList<Integer> injectIndexs = new ArrayList<>();
        //将injects转换为ArrayList<Integer>的集合
        for (String str : injects) {
            injectIndexs.add(findIndex(str));
        }

        ArrayList<String> cells = BFS(injectIndexs, vertexNodeList);
        System.out.println("  感染模块：");
        for (String string : cells) {
            System.out.println(string);
        }

        return cells;
    }

    /**
     * 进行广度优先遍历BFS
     */
    private ArrayList<String> BFS(ArrayList<Integer> injects, VertexNode[] vertexNodeList) {

        ArrayList<String> list = new ArrayList<>();

        Queue<Integer> queue = new LinkedList<Integer>();//队列，先进先出
        int listlen = injects.size();//列表的大小
        boolean[] visited = new boolean[listlen];  // 顶点访问标记

        for (int i = 0; i < listlen; i++) {
            visited[i] = false;
        }

        boolean[] visited2 = new boolean[vertexNodeList.length];
        for (int i = 0; i < vertexNodeList.length; i++) {
            visited2[i] = false;
        }

        for (int i = 0; i < listlen; i++) {
            if (!visited[i]) {
                int content = injects.get(i); //injects.get(i)存放的是节点在vertexNodeList中的索引
                queue.offer(content);
                visited[i] = true;
                visited2[content] = true;

                while(!queue.isEmpty()) {
                    int row = queue.poll();

                    if(row == 18) {
                        System.out.println(vertexNodeList[row].vertex);
                    }
                    //还原节点row的逻辑块
                    //1.1找到row的所有输入
                    HashMap<Integer, String> inputsMap = inputList(row, vertexNodeList);
                    //1.2还原row的逻辑块
                    String cell = refoundCell(row, inputsMap, vertexNodeList);
                    list.add(cell);

                    //2、找到row的所有未被访问过的输出节点
                    if(vertexNodeList[row].firstOut != null) {
                        EdgeNode mEdgeNode = vertexNodeList[row].firstOut;
                        int headvex = mEdgeNode.headvex;//该节点指向的节点
                        //2.1查看节点是否在injects中
                        int inner = injects.indexOf(headvex);
                        if(inner != -1 && visited[inner] == false) {
                            visited[inner] = true;
                        }
                        //2.2标记该节点在vertexNodeList的访问标志
                        if(visited2[headvex] == false) {
                            visited2[headvex] = true;
                            queue.offer(headvex);
                        }
                        while(mEdgeNode.taillink != null) {
                            mEdgeNode = mEdgeNode.taillink;
                            headvex = mEdgeNode.headvex;
                            inner = injects.indexOf(headvex);
                            if(inner != -1 && visited[inner] == false) {
                                visited[inner] = true;
                            }
                            if(visited2[headvex] == false) {
                                visited2[headvex] = true;
                                queue.offer(headvex);
                            }
                        }
                    }
                }
            }
        }
        return list;
    }


}
