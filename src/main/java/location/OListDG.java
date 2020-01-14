package location;

import domain.EdgeNode;
import domain.VertexNode;

import java.util.List;

/**
 * @author wen
 * @date 2019/11/29 0029-11:24
 */
public class OListDG {
    public int vlen; //顶点个数
    public int elen; //边个数
    public VertexNode[] vertexNodeList; //顶点列表

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
            vertexNodeList[i].gateName = vexs.get(i).get(4);//节点对应逻辑门的名称
        }

    }

    private void edgesCreat(List<List<String>> edges) {
        //初始化边,利用头插法建立十字链表
        for(int i = 0; i<elen ; i++) {
            EdgeNode edgeNode_1 = new EdgeNode(); //实例化边表edgeNode_1

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

}
