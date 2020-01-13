package util;

/**
 * @author wen
 * @date 2019/11/26 0026-21:38
 */

import domain.NetInfo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class OListDG {

    int vlen; //顶点个数
    int elen; //边个数
    public VertexNode[] vertexNodeList; //顶点列表
    EdgeNode edgeNode;

    /*	1、
     *	 有参构造函数，用对象实现十字链表
     */
    public OListDG(List<List<String>> vexs, List<List<String>> edges) {
        vlen = vexs.size();
        elen = edges.size();

        vertexCreat(vexs);
        edgesCreat(edges);

    }

    private void vertexCreat(List<List<String>> vexs) {
        //初始化顶点,建立十字链表顶点表的结构
        vertexNodeList = new VertexNode[vlen]; //初始化：建立顶点链表的个数
        for (int i = 0; i<vlen; i++) {
            vertexNodeList[i] = new VertexNode();
            vertexNodeList[i].vertex = vexs.get(i).get(0); //节点的名称
            vertexNodeList[i].gateType = vexs.get(i).get(1); //节点所对应的门类型
            vertexNodeList[i].verType = vexs.get(i).get(2); //节点在所对应逻辑门中的名称
            vertexNodeList[i].firstIn = null; //该节点作为 箭头的终点 即输出端口
            vertexNodeList[i].firstOut = null; //该节点作为 箭头的出发点 即输入端口
            vertexNodeList[i].trojanType = vexs.get(i).get(3);
            vertexNodeList[i].gateName = vexs.get(i).get(4);
//            //***测试运行位置的代码
//            System.out.println("gateName : " + vertexNodeList[i].gateName);
        }

    }

    private void edgesCreat(List<List<String>> edges) {
        //初始化边,利用头插法建立十字链表
        for(int i = 0; i<elen ; i++) {
            EdgeNode edgeNode_1 = new EdgeNode(); //实例化边表edgeNode_1
//			EdgeNode edgeNode_2 = new EdgeNode(); //实例化边表edgeNode_2

            int vi = getPosition(edges.get(i).get(0)); //边对中，第一个节点所对应的编号（出发节点编号）
            int vj = getPosition(edges.get(i).get(1)); //边对中，第二个节点所对应的编号（终止节点编号）

            edgeNode_1.tailvex = vi; //将出发节点编号填入边表中的第一个空 类型为int
            edgeNode_1.headvex = vj; //将终止节点编号填入边表中的第二个空 类型为int
            edgeNode_1.inStation = edges.get(i).get(2);

            System.out.println(i);

            edgeNode_1.taillink = vertexNodeList[vi].firstOut;
            vertexNodeList[vi].firstOut = edgeNode_1;
            edgeNode_1.headlink = vertexNodeList[vj].firstIn;
            vertexNodeList[vj].firstIn = edgeNode_1;

            System.out.println(i+"  "+edgeNode_1.tailvex + "  "+ edgeNode_1.headvex+"   "+edgeNode_1.inStation);
        }

    }

    /*	1、
     * VertexNode类
     * 	功能：十字链表的顶点结构
     * 	参数：vertex——顶点域，存储顶点信息
     * 		firstIn——入边表头指针，指向该顶点的入边表中的第一个节点
     * 		fistOut——出边表头指针，指向该顶点的出边表中的第一个节点
     *
     * 		CC0——0可控性 类型为int
     * 		CC1——1可控性 类型为int
     * 		CO——0可观察性 类型为int
     * 		gateType——逻辑门的类型 类型为int
     * 		id——该节点在链表中的层级 类型为int
     * 		CC0State---->0可控性的状态，类型为char
     * 		CC1State---->1可控性的状态，类型为char
     * 		COState----->可观察性的状态，类型为char
     * 		判断节点信息时，CC0state的优先级大于CC0
     *
     * 		verType---->表示该vertex在对应逻辑器件中的名称，类型为String
     *
     */

    private class VertexNode{
        String vertex; //节点的名称
        String gateType; //门的类型
        String verType;
        EdgeNode firstIn; //指向的是一个边表
        EdgeNode firstOut; //指向的是一个边表
        int CC0;
        int CC1;
        int CO;
        int id;
        String trojanType;
        int lgfi;
        int ffi;
        int ffo;
        int pi;
        int po;
        String gateName;
    }

    /*	1、
     *	功能:边表节点
     *	参数：tailvex——弧起点在顶点表的下标
     *		headvex——弧终点在顶点表的的下标
     *		headlink——入边表指针域，指向终点相同的下一条边
     *		taillink——出边表指针域，指向起点相同的下一条边
     *		inStation---->弧起点在逻辑门输入的位置，类型为String
     */
    private class EdgeNode{
        int tailvex;
        int headvex;
        EdgeNode headlink; //指向的是一个边表
        EdgeNode taillink; //指向的是一个边表
        String inStation;
    }

    /* 1、
     * getPosition 函数
     * 	功能：返回ch位置
     * 	在初始化构造函数时，创建十字链表边表时使用
     */
    private int getPosition(String verName) {
        for (int i=0 ; i<vlen ; i++) {
            if (vertexNodeList[i].vertex.equals(verName))
                return i;
        }
        return -1;
    }

    /*	2、
     * 	寻找强连通分支
     * 	返回ID数组：一个数组标记，同一连通分量的ID号相同
     * 	这一函数同时实现了对网表的拓扑结构进行排序，
     * 	其中：没有出度的节点的ID号越小
     * 		没有入度的节点的ID号越大
     * 	不是强连通分支（即无环）的节点的ID号皆不相同
     *
     * 	返回的是不同id值的个数，其大小为id最大值加1
     *
     * 	LinkedList与ArrayList一样实现List接口，只是ArrayList是List接口的大小可变数组的实现；
     * 	LinkedList是List接口链表的实现。
     *  LinkedList实现方式在插入和删除时更优于ArrayList,而随机访问则比ArrayList逊色些。
     *
     *  LinkedList中提供了两个基本属性size和header
     *  	size表示LinkedList的大小，header表示链表的表头，Entry表示节点对象
     *  	添加方法为 add 移除方法为remove
     *
     */
    public int ConnectBranch() {

        int count= 0; // 用于记录强连通分量的个数
        boolean[] marked = new boolean[vlen]; //用于标记对应索引的顶点是否已经被访问

        Stack<Integer> stack = reversePostOrder();
        while(!stack.isEmpty()) {
            int v = stack.pop();
            if(!marked[v]) {
                count = DFS(v,count,marked);
                count++;
            }
        }
        return count;

    }

    /*	2、
     * 	从节点v开始深度遍历
     * 	非递归深度遍历（后序表示）
     */

    private int DFS(int v, int count, boolean[] marked) {

        Stack<Integer> temp = new Stack<>(); //用于暂时存放顶点信息
        if (!marked[v]) {
            marked[v] = true;
            temp.push(v); //将顶点v入栈

            while (!temp.isEmpty()) {
                int index = temp.peek();  //查看栈顶元素，但并不取出
                /*
                 * 	遍历与栈顶TOP相邻的所有节点，即找到与对应索引为index的顶点的所有输出节点（顶点的firstOut，边表的taillink）
                 * 	如果该输出节点没有访问，则
                 * 		将该输出节点入栈temp
                 * 	直到该顶点节点没有满足条件的相邻节点（找完所有节点发现都已访问过或者没有临边），则将栈顶节点出栈
                 */
                int i = 0;
                if (vertexNodeList[index].firstOut != null) {

                    EdgeNode mEdgeNode = vertexNodeList[index].firstOut;
                    int head = mEdgeNode.headvex; //该index顶点对应的输出节点索引
                    if (!marked[head]) {
                        marked[head] = true;
                        temp.push(head);
                        i++;
                    }

                    while (mEdgeNode.taillink != null) {
                        mEdgeNode = mEdgeNode.taillink;
                        int head2 = mEdgeNode.headvex; //该index顶点对应的输出节点索引
                        if ((i==0) && (!marked[head2])) {
                            marked[head2] = true;
                            temp.push(head2);
                            i++;
                        }
                    }
                }
                if (i == 0) {
                    int current = temp.pop();
                    vertexNodeList[current].id = count;
                }
            }
        }

        return count;
    }

    /*	2、
     * 	从0节点开始逆后序遍历,返回逆后序的栈stack
     * 	逆后序链表：将原来的链表中输入节点当做输出节点，输出节点当做输入节点
     * 	这里的实现方法是：将入度指针域当做出度指针，查找弧尾节点，作为当前节点的邻接节点
     * 	函数返回：interger类型的堆栈stack,该堆栈先进后出
     * (wen)
     */
    private Stack<Integer> reversePostOrder(){
        Stack<Integer> stack = new Stack<Integer>();//stack是一种后进先出的列表，存放对象为整型
        boolean[] marked = new boolean[vlen];//初始化是否被遍历的标号
        for(int i = 0; i < vlen; i++){
            reversePostOrderTar(i, stack, marked);//对逆后序进行深度遍历
        }
        return stack;
    }

    /*	2、
     * 	用于逆后序的深度优先遍历,stack后进先出
     * 	此处是在递归调用函数dfs之后，将节点添加到堆栈中
     * (wen)
     */
    private void reversePostOrderTar(int i, Stack<Integer> stack, boolean[] marked){
        Stack<Integer> temp = new Stack<>(); //用于暂时存放顶点信息
        if (!marked[i]) {
            marked[i] = true;
            temp.push(i); //将顶点v入栈

            while(!temp.isEmpty()) {
                int index = temp.peek();  //查看栈顶元素，但并不取出
                /*
                 * 	遍历与栈顶TOP相邻的所有节点，即找到与对应索引为index的顶点的所有输出节点（顶点的firstIn，边表的headlink）
                 * 	如果该输出节点没有访问，则
                 * 		将该输出节点入栈temp
                 * 	直到该顶点节点没有满足条件的相邻节点（找完所有节点发现都已访问过或者没有临边），则将栈顶节点出栈
                 */
                int j = 0;
                if (vertexNodeList[index].firstIn != null) {

                    EdgeNode mEdgeNode = vertexNodeList[index].firstIn;
                    int tail = mEdgeNode.tailvex; //该index顶点对应的输出节点索引
                    if (!marked[tail]) {
                        marked[tail] = true;
                        temp.push(tail);
                        j++;
                    }

                    while (mEdgeNode.headlink != null) {
                        mEdgeNode = mEdgeNode.headlink;
                        int tail2 = mEdgeNode.tailvex; //该index顶点对应的输出节点索引
                        if ((j == 0) && (!marked[tail2])) {
                            marked[tail2] = true;
                            temp.push(tail2);
                            j++;
                        }
                    }
                }
                if (j == 0) {
                    int current = temp.pop();
                    stack.push(current);
                }
            }
        }
    }

    /*	3、
     * 	该函数的功能：初始化CC0、CC1、CO
     * 	实现原理为：
     * 		1、遍历顶点表中顶点的名称与输入列表inputNet中的元素是否存在相同的关系
     * 		若该顶点的名称与inputNet中的某一个值相同，则该顶点的CC0=CC1=1,CC0State=CC1State='B'
     * 		若该顶点的名称与inputNet中的任一元素值都不相同，则该顶点的CC0State=CC1State='A'
     * 		2、若该顶点的名称与outputNet中的某一个值相同，则该顶点的CO=0,COState='B'
     * 		若该顶点的名称与outputNet中的任一元素都不相同，则该顶点的COState='A'
     * 'A':表示该节点对应值为无穷大
     * 'B':表示该节点对应值已经经过了计算且大小不为无穷
     *
     *	调用参数：inputNet:记录网表中输入节点的列表
     *			outputNet：记录网表中输出界定啊的列表
     *	内部参数：inLen:表示输入节点的个数
     *			outLen:表示输出节点的个数
     */
    public void InitialC(List<String> inputNet , List<String> outputNet) {
        int inLen; //输入节点的数量
        int outLen;  //输出节点的数量

        inLen = inputNet.size();
        outLen = outputNet.size();

        for (int i = 0; i < vlen; i++) {

            int j = 0;
            int k = 0;

            while (j<inLen) {

                if (vertexNodeList[i].vertex.equals(inputNet.get(j))) {
                    vertexNodeList[i].CC0 = 1;
                    vertexNodeList[i].CC1 = 1;

                    //对state_at_0进行修改
                    if (inputNet.get(j).equals("state_at_0")) {
                        vertexNodeList[i].CC1 = 0;
                    }

                    //对state_at_1进行修改
                    if (inputNet.get(j).equals("state_at_1")) {
                        vertexNodeList[i].CC0 = 0;
                    }
                    break;
                }

                j++;
            }

            while (k<outLen) {

                if (vertexNodeList[i].vertex.equals(outputNet.get(k))) {
                    vertexNodeList[i].CO = 0;
                    break;
                }

                k++;
            }

            if(k == outLen) {
                vertexNodeList[i].CO = -1;
            }

        }
    }

    /*	4、
     *	该函数的功能：将相同id编号的节点放入同一列表中，再将所有链表放入一个列表中，id值从大到小
     *	返回参数：rankList:是一个将所有相同id节点的编号存放在一个列表中，
     *					rankList的外围list中以id从大到小排序
     *	内部参数：rankList
     */
    public List<List<Integer>> RankListforCC(int count) {

        List<List<Integer>> rankList = new ArrayList<>();
        int j = 0;

        for (int level = count - 1; level >= 0; level--) {
            rankList.add(new ArrayList<>());
            for (int i = 0; i < vlen; i++) {
                if(vertexNodeList[i].id == level) {
                    rankList.get(j).add(i);
                }
            }
            j++;
        }

        return rankList;

    }

    /*	5、
     * 	查看初始化后的net节点信息
     * 	使用写文件数据流将数据分行写入对应的文件中，文件存储的位置为data文件夹中
     */

    public void readOrthogonalList(String savePath, int index) throws IOException{

        String str = "\\OriginContent" + String.valueOf(index) + ".txt";
        File file = new File(savePath,str);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        PrintWriter link = new PrintWriter(file);

        for (VertexNode vertexNode : vertexNodeList) {

            String linkContent =vertexNode.vertex + "的id号为：" + vertexNode.id
                    + "  	对应门的类型为：" + vertexNode.gateType
                    + "     CC0 = " + vertexNode.CC0
                    + "     CC1 = " + vertexNode.CC1
                    + "     CO = " + vertexNode.CO
                    + "     verType = " + vertexNode.verType;
            link.println(linkContent);
            link.flush();
        }

        link.close();
    }

    /*
     * 6
     */
    private void datePacketforCC(int i) {
        //数据打包过程 将要传输的数据存放在gateCalculate列表中
        List<List<Object>> gateCalculate = new ArrayList<>();
        int j = 0;//保存gateCalculate列表的数量信息
        //存放当前节点的信息
        gateCalculate.add(new ArrayList<>());
//		gateCalculate.get(j).add(i);
        gateCalculate.get(j).add(vertexNodeList[i].gateType);
        gateCalculate.get(j).add(vertexNodeList[i].verType);
        gateCalculate.get(j).add(i);
        gateCalculate.get(j).add(vertexNodeList[i].CC0);
        gateCalculate.get(j).add(vertexNodeList[i].CC1);
//		gateCalculate.get(j).add(vertexNodeList[i].CC0State);
//		gateCalculate.get(j).add(vertexNodeList[i].CC1State);
        j++;
        //由firstIn指针和headlink指针找到当前节点的所有输入节点，并存入相应的信息
        EdgeNode mEdgeNode = new EdgeNode();//创建一个边表对象
        mEdgeNode = vertexNodeList[i].firstIn;

        gateCalculate.add(new ArrayList<>());
        int k = mEdgeNode.tailvex;//记录当前节点的输入节点的编号
//		gateCalculate.get(j).add(k);
        gateCalculate.get(j).add(mEdgeNode.inStation);
        gateCalculate.get(j).add(vertexNodeList[k].CC0);
        gateCalculate.get(j).add(vertexNodeList[k].CC1);
//		gateCalculate.get(j).add(vertexNodeList[k].CC0State);
//		gateCalculate.get(j).add(vertexNodeList[k].CC1State);

        //firstIn是顶点表指向边表的指针，headLink是边表指向边表的指针
        while (mEdgeNode.headlink != null) {
            mEdgeNode = mEdgeNode.headlink;
            gateCalculate.add(new ArrayList<>());
            j++;
            k = mEdgeNode.tailvex;//记录当前节点的输入节点的编号
//			gateCalculate.get(j).add(k);
            gateCalculate.get(j).add(mEdgeNode.inStation);
            gateCalculate.get(j).add(vertexNodeList[k].CC0);
            gateCalculate.get(j).add(vertexNodeList[k].CC1);
//			gateCalculate.get(j).add(vertexNodeList[k].CC0State);
//			gateCalculate.get(j).add(vertexNodeList[k].CC1State);
        }

//		//查看数据打包的内容
//		System.out.println("数据打包内容：");
//		System.out.println(gateCalculate);
//		System.out.println("");

        //将打包内容传送给逻辑门计算函数
        GateCCUtil gateMethod = new GateCCUtil(gateCalculate);
        vertexNodeList[i].CC0 = gateMethod.CC0;
        vertexNodeList[i].CC1 = gateMethod.CC1;
    }

    /*	6、
     *   	广度优先搜索（类似于树的层次遍历）
     *   	该函数的作用是：list中元素为整个顶点集合中的真子集的编号及index，使用广度优先搜索的方式遍历
     *   		使得操作一个节点之后，下一个处理的是该节点的输出节点，循环一圈之后，所有节点都被访问到
     *  	 注意：这里的应该可以理解为局部节点的广度优先搜索
     */
    private void BFS(List<Integer> list) {

        Queue<Integer> queue = new LinkedList<Integer>();//队列，先进先出
        int listlen = list.size();//列表的大小
        boolean[] visited = new boolean[listlen];  // 顶点访问标记

        for (int i = 0; i < listlen; i++) {
            visited[i] = false;
        }

        for (int i = 0; i < listlen; i++) {
            if (!visited[i]) {
                queue.offer(i);
                visited[i] = true;
//				System.out.println(list.get(i));
                datePacketforCC(list.get(i));

                while(!queue.isEmpty()) {
                    int row = queue.poll();
                    //下一步，找到row的所有输出节点编号，若该节点没有被访问，则输出它的信息，且入栈queue.add(k)
                    int content = list.get(row);

                    if(vertexNodeList[content].firstOut != null) {

                        EdgeNode mEdgeNode = new EdgeNode();
                        mEdgeNode =vertexNodeList[content].firstOut;

                        int index = mEdgeNode.headvex;//该节点指向的节点
                        int listStation = list.indexOf(index);//所指向节点在list中的位置，当返回-1，表示该节点不在list中，则不操作
                        //当所指向节点在list列表中
                        if (listStation != -1) {
                            if(!visited[listStation]) {
                                visited[listStation] = true;
                                queue.offer(listStation);
//								System.out.println(index);
                                datePacketforCC(index);
                            }
                        }
                        while(mEdgeNode.taillink != null) {
                            mEdgeNode = mEdgeNode.taillink;
                            index = mEdgeNode.headvex;
                            listStation = list.indexOf(index);
                            if (listStation != -1) {
                                if(!visited[listStation]) {
                                    visited[listStation] = true;
                                    queue.offer(listStation);
//									System.out.println(index);
                                    datePacketforCC(index);
                                }
                            }
                        }
                    }
                }
            }
        }

    }


    /*	6、
     * 	函数作用：对已经排序好的拓扑序列，依次打包逻辑门计算需要的数据，为调用逻辑门计算函数做准备（只对CCO和CC1）
     * 	此处rankList中存放的是列表list，依次遍历列表list
     *
     * list有两种情况（list中的元素对应顶点表的index）
     * 	1、list中的元素只有一个，在该情况下又做两种考虑
     * 		（1）当index对应节点不作为任何逻辑门的输出，即vertexNodeList[i].gateType.equals("null")，则不做任何计算
     * 		（2）当index对应的节点是逻辑门的输出，将该节点的index\gateType\CC0\CC1\CC0State\CC1State存放新的列表中的第一个列表
     * 			将该节点的入度节点的index\inStation\CC0\CC1\CCOState\CC1State依次存放在后序的列表中，一个输入节点对应一个列表
     * 			这一存放数据的功能在datePacketforCC中实现
     * 2、list中的元素有多个，在这种情况下做如下操作
     * 		（1）对list中的元素的index\CC0\CC1\CC0State\CC1State做一个标记列表state并初始化
     * 		（2）对list中的元素作广度优先遍历BFS
     * 		（3）将list中元素，经过依次基于BFS--->datePacketforCC--->gateMethod之后，判断现在元素信息与state表中的信息是否相同
     * 				若相同，则结束
     * 				若不同，则将元素现在的信息替代state表中的信息，然后重复步骤（2）（3）
     */
    public void rankEnter(List<List<Integer>> rankList) {
        for (List<Integer> list : rankList) {
            //考虑无环的情况
            if (list.size() == 1) {
                //判断条件为，当该节点作为一个逻辑门的输出时，执行if内部操作
                int i = list.get(0);
                if(!vertexNodeList[i].gateType.equals("null")) {

                    datePacketforCC(i);

                }
            }else {
                //考虑有环的情况
                int index;
                boolean twoSame;
                do{
                    List<List<Object>> state1 = new ArrayList<>();
                    List<List<Object>> state2 = new ArrayList<>();
                    for (int i = 0; i < list.size(); i++) {
                        state1.add(new ArrayList<>());
                        index = list.get(i);
                        state1.get(i).add(index);
                        state1.get(i).add(vertexNodeList[index].CC0);
                        state1.get(i).add(vertexNodeList[index].CC1);
                    }

                    BFS(list);

                    for (int i = 0; i < list.size(); i++) {
                        state2.add(new ArrayList<>());
                        index = list.get(i);
                        state2.get(i).add(index);
                        state2.get(i).add(vertexNodeList[index].CC0);
                        state2.get(i).add(vertexNodeList[index].CC1);
                    }

                    twoSame = getDiffrent(state1,state2);//判断两个列表中的元素是否相同，若相同返回true，不同则返回false
                }while(!twoSame);
            }
        }
    }

    /*	6、
     * 	函数作用：判断两个数组中的元素是否相同
     * 		若相同则返回true
     * 		不同则返回false
     */
    private boolean getDiffrent(List<List<Object>> state1,List<List<Object>> state2) {

        for (int i = 0; i < state1.size(); i++) {
            for (int j = 0; j < state1.get(i).size(); j++) {
                if (!state1.get(i).get(j).equals(state2.get(i).get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    /*
     *	该函数的功能：将相同id编号的节点放入同一列表中，再将所有链表放入一个列表中，id值从小到大
     *	返回参数：rankList:是一个将所有相同id节点的编号存放在一个列表中，
     *					rankList的外围list中以id从小到大排序
     *	内部参数：rankList
     */
    public List<List<Integer>> RankListforCO(int count) {
        List<List<Integer>> rankList = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            rankList.add(new ArrayList<>());
            for (int k = 0; k < vlen; k++) {
                if (vertexNodeList[k].id == i) {
                    rankList.get(i).add(k);
                }
            }
        }
        return rankList;
    }

    /*	8、
     *	 数据打包部分，此部分将当前节点计算所需要的数据打包为列表的形式
     * 	注意：这里对当前net节点i操作，想要改变的不是当前节点的CO\COState的值，而是改变的是当前net的输入net的CO\COState值
     * 	若当前节点的COState = 'A'则不需要进行后续的操作
     * 	需要打包的数据为：当前节点的index\CO\gateType,
     * 				当前节点的输入节点的index\inStation\CO\COState\CCO\CC1\CC0State\CC1State
     *
     *	 在调用函数方法时，需要返回的是当前节点的输入net节点的index\CO\COState，可以以链表的形式表示
     *
     *	以上的描述是最初的想法，下面是更新于2018.9.13的想法：
     *		1、其实CC0\CC1\CO的状态完全不需要使用字符CC0State\CC1State\COState来判断
     *		2、原因是：在初步考虑网表中的节点的时候，我们假设的是这些节点的可控性值和可观察性值为无穷大
     *		3、且可控性和可观察性的值在计算过程中是单调递增的
     *		4、所以，我们可以将CC0\CC1的初始值0作为无穷状态考虑
     *			将CO的初始值-1作为不可观察状态
     *		5、所以修改后传入的数据为：
     *			当前节点：gateType\CO\verType\CC0\CC1
     *			输入节点：index\inStation\CO\CC0\CC1
     */
    private void dataPacketforCO(int index) {
        if (vertexNodeList[index].CO != -1) {
            //初始化数据传输载体datePacket列表
            List<List<Object>> dataPacket = new ArrayList<>();
            int j = 0;//保存dataPacket中列表中的位置索引
            //存放当前节点的信息
            dataPacket.add(new ArrayList<>());
//			dataPacket.get(j).add(index);
            dataPacket.get(j).add(vertexNodeList[index].gateType);
            dataPacket.get(j).add(vertexNodeList[index].CO);
            dataPacket.get(j).add(vertexNodeList[index].verType);
            dataPacket.get(j).add(vertexNodeList[index].CC0);
            dataPacket.get(j).add(vertexNodeList[index].CC1);
//			dataPacket.get(j).add(vertexNodeList[index].CC0State);
//			dataPacket.get(j).add(vertexNodeList[index].CC1State);

            //由firstIn指针和headlink指针找到当前节点的所有输入节点，并存入相应的信息
            EdgeNode mEdgeNode = new EdgeNode();
            mEdgeNode = vertexNodeList[index].firstIn;

            dataPacket.add(new ArrayList<>());
            j++;
            int k = mEdgeNode.tailvex;//记录当前节点的输入节点在顶点表中的位置
            dataPacket.get(j).add(k);
            dataPacket.get(j).add(mEdgeNode.inStation);
            dataPacket.get(j).add(vertexNodeList[k].CO);
//			dataPacket.get(j).add(vertexNodeList[k].COState);
            dataPacket.get(j).add(vertexNodeList[k].CC0);
            dataPacket.get(j).add(vertexNodeList[k].CC1);
//			dataPacket.get(j).add(vertexNodeList[k].CC0State);
//			dataPacket.get(j).add(vertexNodeList[k].CC1State);

            while(mEdgeNode.headlink != null) {
                mEdgeNode = mEdgeNode.headlink;
                dataPacket.add(new ArrayList<>());
                j++;
                k = mEdgeNode.tailvex;//记录当前节点的输入节点在顶点表中的位置
                dataPacket.get(j).add(k);
                dataPacket.get(j).add(mEdgeNode.inStation);
                dataPacket.get(j).add(vertexNodeList[k].CO);
//				dataPacket.get(j).add(vertexNodeList[k].COState);
                dataPacket.get(j).add(vertexNodeList[k].CC0);
                dataPacket.get(j).add(vertexNodeList[k].CC1);
//				dataPacket.get(j).add(vertexNodeList[k].CC0State);
//				dataPacket.get(j).add(vertexNodeList[k].CC1State);
            }

//			//查看数据打包内容
//			System.out.println("");
//			System.out.println(dataPacket);
//			System.out.println("");

            //将打包的数据传送给逻辑门CO的计算函数
            GateCOUtil coMethod = new GateCOUtil(dataPacket);
            List<List<Integer>> verPacket = coMethod.verPacket;
            //数据包verPacket的内容：输入节点的index\CO\COState = 'B';
            for (List<Integer> list : verPacket) {
                int net = list.get(0);
                if (list.get(1) < 0) {
                    vertexNodeList[net].CO = -1;
                }else {
                    vertexNodeList[net].CO = list.get(1);
                }
//				vertexNodeList[net].CO = list.get(1);
//				vertexNodeList[net].COState = (char)list.get(2);
            }
        }
    }

    /*	8、
     * 	该函数作用：局部广度优先逆向遍历函数
     * 	方向为查找每个节点的输入节点，当遍历到某各节点时，则进行数据打包操作dataPacketforCO然后计算该节点输入节点的CO值
     * 	该函数面向的对象为list:某一环形局部电路的所有net，list中的末尾元素：enter表示遍历的入口
     */
    private void VBFS(List<Integer> list) {
        //初始化遍历时，用于存放数据的队列，先进先出
        Queue<Integer> queue = new LinkedList<>();
        int listLen = list.size();//列表的大小
        boolean[] visited = new boolean[listLen];//顶点访问标记

        for (int i = 0; i < listLen; i++) {
            visited[i] = false;
        }

        for (int i = listLen - 1; i >= 0; i--) {
            if (!visited[i]) {
                queue.offer(i);
                visited[i] = true;
//				System.out.println(list.get(i));//查看获得的节点信息
                dataPacketforCO(list.get(i));

                while(!queue.isEmpty()) {
                    int row = queue.poll();//取出堆栈的顶端数据
                    //下一步找到row的所有输入节点，若输入节点没有被访问则入栈
                    int content = list.get(row);

                    //当当前节点有输入的情况
                    if (vertexNodeList[content].firstIn != null) {
                        EdgeNode mEdgeNode = new EdgeNode();
                        mEdgeNode = vertexNodeList[content].firstIn;

                        int index = mEdgeNode.tailvex;//当前节点的输入节点
                        int listStation = list.indexOf(index);//查看输入节点在list中的位置，若不存在则返回-1
                        //当前节点的输入节点在list中时，才继续遍历元素
                        if (listStation != -1) {
                            if (!visited[listStation]) {
                                visited[listStation] = true;
                                queue.offer(listStation);
//								System.out.println(index);//查看获得节点的信息
                                dataPacketforCO(index);
                            }
                        }
                        while(mEdgeNode.headlink != null) {
                            mEdgeNode = mEdgeNode.headlink;
                            index = mEdgeNode.tailvex;
                            listStation = list.indexOf(index);
                            if (listStation != -1) {
                                if (!visited[listStation]) {
                                    visited[listStation] = true;
                                    queue.offer(listStation);
//									System.out.println(index);
                                    dataPacketforCO(index);
                                }
                            }
                        }

                    }
                }

            }
        }
    }

    /*	8、
     *	 函数作用是：通过遍历rankListforCO列表，作为计算网表CO值的索引条件
     * rankListforCO列表的内部列表有两种情况
     * 		1、当list中的个数为一个的时候，即为无环结构，直接打包数据dataPacketforCO(int i)
     * 			其中i表示对应list中的对应存放的顶点在顶点表中索引值，
     * 			若当前节点不为任何逻辑门的输出，则其前方没有可观察性值计算的必要
     *
     * 		2、当list中的个数为多个的时候，即为环形电路，
     * 			在环形电路中需要选择一个入口作为遍历的起始条件
     * 			这里采用的方法是在list中的所有net中选择第一个COStart状态为“B”即已标记状态的net作为入口
     * 			然后通过局部广度优先逆向遍历函数VBFS，每遍历到一个net时，我们就将调用dataPacketforCO函数
     * 			经过数据打包后，进入函数计算公式，
     * 			所改变的值为当前net的所有输入节点的CO值和COState状态值
     */
    public void rankOut(List<List<Integer>> rankListforCO) {
        for (List<Integer> list : rankListforCO) {
            //
            if (list.size() == 1) {
                int index = list.get(0);//找到list表存放数据，该数据表示顶点表的索引值

                if (vertexNodeList[index].gateType != "null") {
                    dataPacketforCO(index);//数据打包部分和调用方法类接口
                }
            } else {
                //寻找广度优先遍历的入口enter
                int enter = 0;
                int v = 0;
                for (Integer index : list) {
                    if (vertexNodeList[index].CO != -1) {
                        enter = index;
                        //将enter从list的原位置上删除，然后将enter添加到list的尾部
                        list.remove(v);//将station位置上的元素删除
                        list.add(enter);//将enter添加到list的尾部
                        break;
                    }
                    v++;
                }

                int index;
                boolean twoSame;
                do {
                    List<List<Object>> state1 = new ArrayList<>();
                    List<List<Object>> state2 = new ArrayList<>();
                    for (int i = 0; i < list.size(); i++) {
                        index = list.get(i);
                        state1.add(new ArrayList<>());
                        state1.get(i).add(index);
                        state1.get(i).add(vertexNodeList[index].CO);
                    }

                    VBFS(list);//以局部广度优先逆向遍历的顺序打包对应节点的数据，然后调用方法类接口

                    for (int i = 0; i < list.size(); i++) {
                        index = list.get(i);
                        state2.add(new ArrayList<>());
                        state2.get(i).add(index);
                        state2.get(i).add(vertexNodeList[index].CO);
                    }

                    twoSame = getDiffrent(state1, state2);
                }while(!twoSame);
            }
        }
    }

    /**
     * 该方法得到的得到一个文件，该文件剔除了孤立的不可测试点、常量点、没有使用的输入端口、没有使用的输出端口
     * 然后得到的是：节点名（节点的索引号是否会更加简单）、CC0\CC1\CO、节点类型
     */
    public ArrayList<NetInfo> readOrthogonalList1(String savePath) throws IOException {

        ArrayList<NetInfo> netInfos = new ArrayList<>();

        String str = savePath + "\\cValue.txt";
        File file = new File(str);
        PrintWriter link = new PrintWriter(file);

        List<Integer> list = removeIsolateNet();

        //随机数生成对象
        Random rand = new Random();

        VertexNode vertexNode;
        for (int i = 0; i < vertexNodeList.length; i++) {
            if(list.contains(i)) {
                continue;
            }
            vertexNode = vertexNodeList[i];
            boolean notuseInput = vertexNode.trojanType.equals("inputPin") && vertexNode.firstOut == null;//为输入引脚但是没有在后续的电路中使用
            boolean notuseOutput = vertexNode.trojanType.equals("outputPin") && vertexNodeList[vertexNode.firstIn.tailvex].trojanType.equals("constant");
            boolean constant = vertexNode.trojanType.equals("constant");
            if(notuseInput || notuseOutput || constant) {
                continue;
            }
            //符号表示
            String vertex = vertexNode.vertex;
            int CC0 = vertexNode.CC0;
            int CC1 = vertexNode.CC1;
            int CO = vertexNode.CO;
            String trojanType = vertexNode.trojanType;
            String gateName = vertexNode.gateName;

            //拼接存储数据
            String linkContent =vertex + "_" + i + "," + CC0 + "," + CC1 + "," + CO + "," + trojanType;

            //写入NetInfo对象
            NetInfo netInfo = new NetInfo();
            netInfo.setVerId(i);
            netInfo.setNetName(vertex);
            netInfo.setCC0(CC0);
            netInfo.setCC1(CC1);
            netInfo.setCO(CO);
            netInfo.setType(trojanType);
            netInfo.setGateName(gateName);
            /*
            计算netInfo中的CC值
            double CC;//在计算CC值的时候需要判断CC0与CC1是否为0 或者CO是否为-1
            double comform;//如果CC0与CC1是否为0 或者CO是否为-1，则需要将comform设定为true，表示提前将该节点设定为木马可疑节点
            同时，需要改变CC0/CC1/CO的值，让其在第三限位。
             */
            if( CC0 == 0 || CC1 == 0 || CO == - 1){
                netInfo.setComform(true);
                netInfo.setCC(-(rand.nextInt(65)+ 64));//生成区间 [-64,-128] 中随机值
                netInfo.setCO(-(rand.nextInt(65)+ 64));
            }else{
                netInfo.setComform(false);
                int CC = (int)Math.sqrt(Math.pow((double)CC0, 2) + Math.pow((double)CC1, 2));
                netInfo.setCC(CC);
            }
            netInfos.add(netInfo);

            link.println(linkContent);
            link.flush();
        }

        link.close();
        return netInfos;
    }

    /**
     *  4.9对孤立点的判断结果
     *    这些点具有以下特征
     * 	1、它的可观测性值为-1，
     * 	2、它前后的节点都是可观测且可控的
     * 	3、将这些节点不作为文件的输出
     */
    private List<Integer> removeIsolateNet() {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < vertexNodeList.length; i++) {
            int CO = vertexNodeList[i].CO;
            if(CO == -1) {
                boolean isolate = true;
                //查看该节点的所有输入节点
                if(vertexNodeList[i].firstIn != null) {
                    edgeNode = vertexNodeList[i].firstIn;
                    int tailvex = edgeNode.tailvex;
                    if(vertexNodeList[tailvex].CC0 == 0 || vertexNodeList[tailvex].CC1 == 0 || vertexNodeList[tailvex].CO == -1) {
                        isolate = false;
                    }

                    while(edgeNode.headlink != null) {
                        edgeNode = edgeNode.headlink;
                        tailvex = edgeNode.tailvex;
                        if(vertexNodeList[tailvex].CC0 == 0 || vertexNodeList[tailvex].CC1 == 0 || vertexNodeList[tailvex].CO == -1) {
                            isolate = false;
                        }
                    }
                }

                if(vertexNodeList[i].firstOut != null) {
                    edgeNode = vertexNodeList[i].firstOut;
                    int headvex = edgeNode.headvex;
                    if(vertexNodeList[headvex].CC0 == 0 || vertexNodeList[headvex].CC1 == 0 || vertexNodeList[headvex].CO == -1) {
                        isolate = false;
                    }

                    while(edgeNode.taillink != null) {
                        edgeNode = edgeNode.taillink;
                        headvex = edgeNode.headvex;
                    }
                    if(vertexNodeList[headvex].CC0 == 0 || vertexNodeList[headvex].CC1 == 0 || vertexNodeList[headvex].CO == -1) {
                        isolate = false;
                    }
                }

                //当isolate == true的时候，便是孤立的不可观测点
                if(isolate == true) {
                    list.add(i);
                }
            }
        }
        return list;
    }
}

