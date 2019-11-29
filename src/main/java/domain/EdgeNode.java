package domain;

public class EdgeNode {
	/*	1、
	 *	功能:边表节点
	 *	参数：tailvex——弧起点在顶点表的下标，A-->B中的A
	 *		headvex——弧终点在顶点表的的下标，A-->B中的B
	 *		headlink——入边表指针域，指向终点相同的下一条边，C-->B,E-->B类似
	 *		taillink——出边表指针域，指向起点相同的下一条边 ,A-->D,A-->F类似
	 *		inStation---->弧起点在逻辑门输入的位置，类型为String，pin
	 */
	public int tailvex;
	public int headvex;
	public EdgeNode headlink; //指向的是一个边表
	public EdgeNode taillink; //指向的是一个边表
	public String inStation;
}
