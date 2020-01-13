package domain;

public class VertexNode {
	public String vertex; //节点的名称，net
	public String gateType; //门的类型，reference
	public String verType; //pin
	public EdgeNode firstIn; //指向的是一个边表
	public EdgeNode firstOut; //指向的是一个边表
	public String gateName;
}
