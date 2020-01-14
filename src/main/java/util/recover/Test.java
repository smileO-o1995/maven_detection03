package util.recover;

import domain.EdgeNode;
import domain.VertexNode;
import location.OListDG;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wen
 * @date 2020/1/14 0014-15:40
 */
public class Test {
    public static void main(String arg[]){
        //[n159, MUX21X1, Q, noTrojan, U34]
        List<List<String>> vexs = new ArrayList<>();
        for(int i = 1; i < 8; i++){
            ArrayList<String> vex = new ArrayList<>();
            vex.add(String.valueOf(i));
            vex.add("gata");
            vex.add("gata");
            vex.add("gata");
            vex.add("gata");
            vexs.add(vex);
        }
        //[iRECEIVER_CTRL, iCTRL, ISO]
        List<List<String>> edges = new ArrayList<>();
        ArrayList<String> edge1 = new ArrayList<>();
        edge1.add("1");
        edge1.add("2");
        edge1.add("type");
        edges.add(edge1);
        ArrayList<String> edge2 = new ArrayList<>();
        edge2.add("1");
        edge2.add("3");
        edge2.add("type");
        edges.add(edge2);
        ArrayList<String> edge3 = new ArrayList<>();
        edge3.add("2");
        edge3.add("5");
        edge3.add("type");
        edges.add(edge3);
        ArrayList<String> edge4 = new ArrayList<>();
        edge4.add("3");
        edge4.add("5");
        edge4.add("type");
        edges.add(edge4);
        ArrayList<String> edge5 = new ArrayList<>();
        edge5.add("4");
        edge5.add("6");
        edge5.add("type");
        edges.add(edge5);
        ArrayList<String> edge6 = new ArrayList<>();
        edge6.add("4");
        edge6.add("2");
        edge6.add("type");
        edges.add(edge6);
        ArrayList<String> edge7 = new ArrayList<>();
        edge7.add("7");
        edge7.add("2");
        edge7.add("type");
        edges.add(edge7);

        OListDG oListDG = new OListDG(vexs, edges);
        for(VertexNode vertexNode : oListDG.vertexNodeList){
            System.out.println();
            System.out.print("node : " + vertexNode.vertex + "-----  :");
            if(vertexNode.firstOut != null){
                EdgeNode edgeNode = vertexNode.firstOut;
                System.out.print("  " + oListDG.vertexNodeList[edgeNode.headvex].vertex);
                while(edgeNode.taillink != null){
                    edgeNode = edgeNode.taillink;
                    System.out.print("  " + oListDG.vertexNodeList[edgeNode.headvex].vertex);
                }
            }

        }
    }
}
