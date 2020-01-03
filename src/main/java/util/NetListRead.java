package util;

import java.sql.Savepoint;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * 测试版本
 */
public class NetListRead {

	//定义一个成员属性
	public List<List<String>> vertexInfo = new ArrayList<>(); //顶点信息，每个逻辑门的输出，节点名称，逻辑门的名称
	public List<List<String>> edgesInfo = new ArrayList<>(); // 边信息，存放的是，输入节点名称、输出节点名称，输入节点在对应逻辑门中的编号
	public List<String> inputInfo = new ArrayList<>(); // /输入节点信息，只需要存放输入节点的名称
	public List<String> outputInfo = new ArrayList<>(); // 输出节点信息，只需要存放输出节点的名称

	public int count0 = 0; // 统计常量1'b0的个数
	public int count1 = 0; // 统计常量1'b1的个数

	public NetListRead(String reFileName, String savePath) throws IOException{

		String preName = reFileName.substring(0, reFileName.lastIndexOf("_re"));
		//文件读取流
		File file = new File(savePath + "\\" + reFileName);
		BufferedReader bReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

		//读取文件
		String line;
		while ((line = bReader.readLine()) != null) {
			ReadContent(line, preName);
		}
		bReader.close();
//		System.out.println(inputInfo);

		// 将这些数据保存到txt文件中，以便后期校验
		writeInfo(savePath);
	}


	private void writeInfo(String savePath) throws IOException{

		savePath = savePath + "\\verAndEdge";
		File realPath = new File(savePath);
		if(realPath.exists()){
			DealFile.delAllFile(savePath);
		}
		realPath.mkdir();

		File inputInfotxt = new File(savePath,"inputInfo.txt");
		File outputInfotxt = new File(savePath,"outputInfo.txt");
		File vertexInfotxt = new File(savePath,"vertexInfo.txt");
		File edgesInfotxt = new File(savePath,"edgesInfo.txt");

		PrintWriter inputInfoIw = new PrintWriter(inputInfotxt);
		PrintWriter outputInfoIw = new PrintWriter(outputInfotxt);
		PrintWriter vertexInfoIw = new PrintWriter(vertexInfotxt);
		PrintWriter edgesInfoIw = new PrintWriter(edgesInfotxt);

		//将列表写入文件中
		for (List<String> list : edgesInfo) {
			edgesInfoIw.println(list);
			edgesInfoIw.flush();
		}

		for (List<String> list : vertexInfo) {
			vertexInfoIw.println(list);
			vertexInfoIw.flush();
		}

		for (String str : inputInfo) {
			inputInfoIw.println(str);
			inputInfoIw.flush();
		}

		for (String str : outputInfo) {
			outputInfoIw.println(str);
			outputInfoIw.flush();
		}

		outputInfoIw.close();
		inputInfoIw.close();
		vertexInfoIw.close();
		edgesInfoIw.close();
	}

	/*
	 * 逻辑语句的基本样式为：input [7:0]xmit_dataH
	 * 那么，现在对每个语句的处理将是这样：
	 * 		1、首先以空格符作为分界线，得到的是两个元素的字符串数组
	 * 		2、第一个字符串数组的内容用于：input/output/wire/module/与逻辑单元的reference
	 */
	private void ReadContent(String content, String preName) {

		Pattern p1 = Pattern.compile("\\s");
		String[] dest = p1.split(content);
		System.out.println(content);
//		System.out.println(dest[0]);

		// 以下对每句逻辑语句进行分类处理
		// 1、当该语句以“module”或者“wire”开头则不做处理
		if (dest[0].equals("module") || dest[0].equals("wire")) {
			System.out.println("该项为无关项");

		}else if (dest[0].equals("input")) {
			//2、当该语句以"input"开头的时候，需要进行输入节点的信息采集，以及顶点节点的信息采集
			// 去掉input标志符，然后传入InoutExtract()函数进行信息采集
			InputExtract(dest[1]);

		}else if (dest[0].equals("output")) {
			//3、当语句以"output"开头，仅需要进行输出节点的信息采集
			// 去掉output标志符，然后传入OutputExtract()函数进行信息采集
			OutputExtract(dest[1]);

		}else {
			//5、最后一种情况，程序运行到这里，仅有一种可能，即为网表的逻辑门描述部分
			// 在将数据传入GateExtract()函数中前需要对常数的情况进行处理
			// 1'b1 替代为 state_at_1和1b'0 替代为 state_at_0，将这两种节点保存到输入信息列表inputInfo中
			if (dest[1].indexOf("state_at_0") != -1) {
				count0++;
				if(count0 == 1) {
					inputInfo.add("state_at_0");

					int verLen = vertexInfo.size();
					vertexInfo.add(new ArrayList<>());
					vertexInfo.get(verLen).add("state_at_0");
					vertexInfo.get(verLen).add("null");
					vertexInfo.get(verLen).add("null");
					vertexInfo.get(verLen).add("constant");
				}
			}
			if (dest[1].indexOf("state_at_1") != -1) {
				count1++;
//				System.out.println(content);
				if (count1 == 1) {
					inputInfo.add("state_at_1");

					int verLen = vertexInfo.size();
					vertexInfo.add(new ArrayList<>());
					vertexInfo.get(verLen).add("state_at_1");
					vertexInfo.get(verLen).add("null");
					vertexInfo.get(verLen).add("null");
					vertexInfo.get(verLen).add("constant");
				}
			}

			if (dest[0].equals("assign")) {
				//4、语句以"assign"开头，将assign作为一个器件
				// 去掉assign标识符，然后传入AssignExtract()函数中进行边信息和顶点信息采集
				AssignExtract(dest[1]);
			}else {

				GateExtract(dest[1],dest[0], preName);
			}
		}
	}

	/**
	 *	4、逻辑器件名的命名规则有为：输出的表达形式为Q或者为QN，所以当遍历到该符号的时候将该节点保存到headvex列表中
	 *   	其余节点则保存到tailvex列表中
	 *   1）在获取逻辑门的类型的时候，我们将单独把第一个大括号前面的数据提取出来，然后再在该子字符串中提取我们想要的逻辑器件类型
	 *   2）headvex中的所有节点需要保留的数据为，headvex\referenceName\vexType
	 *   3) tailvex中的所有节点需要保留的数据为，tailvex\headvex\edgeType
	 */
	private void GateExtract(String content, String referenceName, String preName) {

		// 1)首先需要获取逻辑门的器件类型名称
		// SDFFX1 DFF_603_Q_reg(.D(g27257),.SI(g996),.SE(test_se),.CLK(CK),.Q(g1041),.QN());
		String cellName = content.substring(0, content.indexOf("("));

		// 2)创建输入输出列表
		List<String> tailvex = new ArrayList<>(); // 输入节点列表
		List<String> headvex = new ArrayList<>(); // 输出节点列表

		// 3)匹配端口和名称
		/*
		 * 以SDFFX1DFF_603_Q_reg(.D(g27257),.SI(g996),.SE(test_se),.CLK(CK),.Q(g1041),.QN());为例
		 *  匹配的结果可以记为如下形式：D(g27257) SI(g996) SI(g996) CLK(CK) Q(g1041)
		 *  这里每一个匹配形式具有如下共同点
		 *  	首先，括号外面的内容，一定是由“一个或多个的字母” 与 “0个或多个的数字”组成
		 *  	其次，括号中的内容，一定是由“一个或多个字符”与 “0个或多个数字组成”
		 *  也有可能出现以下三种情况：
		 *  	\test_point/TM
		 *  	iRECEIVER_state_1_
		 *  	rec_dataH_rec[5] 这种情况是：在节点的末尾可能会出现中括号
		 *  所以，我认为上述的共同点已经不再适用，可以使用以下的共同点
		 *  	首先，括号外面的内容使用："\\w+"
		 *  		括号外面的内容暂时没有改变，因为括号外面的内容主要是随着器件规格改变的
		 *  	其次，括号中的内容分为两种情况：
		 *  		一种情况是："\\w+"
		 *  		另一种情况是："\\test_point/\\w+"
		 *  所以给定的正则表式为："\\w+\\((\\test_point/)?\\w+(\\[([0-9])+\\])?\\)"
		 *
		 *  同时还需要注意的是：逻辑器件输出口的逻辑名称
		 */
		Pattern pattern = Pattern.compile("\\w+\\(\\w+(\\[([0-9])+\\])?\\)");
		Matcher matcher = pattern.matcher(content);

		// 3.1)查看该语句的特性(这里对提供的参考样本进行标记前的类别判断)
		boolean tjTriggerIs = false;
		boolean tjPayloadIs = false;

		Matcher tjTrigger = null;
		Matcher tjPayload = null;
		int index = 0;
		//木马标记情况
		if(preName.contains("s38417")){
			tjTrigger = Pattern.compile("Trojan(?!EnableGATE|_Payload)").matcher(cellName);
			tjPayload = Pattern.compile("Payload|TrojanEnableGATE|Ring_").matcher(cellName);
		}else if(preName.contains("RS232-T1000")){
			tjTrigger = Pattern.compile("U29[3-9]|U30[0-2]").matcher(cellName);
			tjPayload = Pattern.compile("U30[3-5]").matcher(cellName);
		}else if(preName.contains("RS232-T1200")){
			tjTrigger = Pattern.compile("U29[2-7]|U30[0-2]|iDatasend_reg_[1-4]").matcher(cellName);
			tjPayload = Pattern.compile("U303").matcher(cellName);
		}else if(preName.contains("RS232-T1300")){
			tjTrigger = Pattern.compile("U29[2-7]|U302").matcher(cellName);
			tjPayload = Pattern.compile("U30[3-4]").matcher(cellName);
		}else if(preName.contains("RS232-T1400")){
			tjTrigger = Pattern.compile("U29[2-9]|U30[0-2]|iDatasend_reg").matcher(cellName);
			tjPayload = Pattern.compile("U303").matcher(cellName);
		}else if (preName.contains("RS232-T1500")){
			tjTrigger = Pattern.compile("U29[3-9]|U30[0-2]|iDatasend_reg_2_").matcher(cellName);
			tjPayload = Pattern.compile("U30[3-5]").matcher(cellName);
		}else if (preName.contains("RS232-T1600")){
			tjTrigger = Pattern.compile("U29[3-7]|U30[0-2]|iDatasend_reg_[1-2]").matcher(cellName);
			tjPayload = Pattern.compile("U30[3-4]").matcher(cellName);
		}else if (preName.contains("RS232-T1100")){
			tjTrigger = Pattern.compile("U29[3-9]|U30[0-2]|iDatasend_reg_2").matcher(cellName);
			tjPayload = Pattern.compile("U305").matcher(cellName);
		}else if(preName.contains("EthernetMAC10GE") || preName.contains("b19_T200")
				|| preName.contains("wb_conmax") || preName.contains("vga_enh") || preName.contains("s35932")) {
			tjTrigger = Pattern.compile("Trojan|Counter_BIT1|INV_test_se|Trigger").matcher(cellName);
			index = cellName.indexOf("Payload");
		}else if(preName.contains("s15850")){
			tjTrigger = Pattern.compile("Tg1|Tg2|INV_test_se|Trojan_Trigger").matcher(cellName);
			index = cellName.indexOf("Payload");
		}else if(preName.contains("s38584_T100") || preName.contains("s38584_T200")){
			tjTrigger = Pattern.compile("Trojan").matcher(cellName);
			index = cellName.indexOf("Payload");
		}else {
			//b19_T100/s38584_scan_T300_Tjin以及其他
			tjTrigger = Pattern.compile("Trigger").matcher(cellName);
			index = cellName.indexOf("Payload");
		}

		//判断第二步
		if(preName.contains("RS232") || preName.contains("s38417")){
			if(tjTrigger != null){
				if (tjTrigger.find()) {
					tjTriggerIs = true; // 为木马触发器
				}
			}
			if(tjPayload != null){
				if (tjPayload.find()) {
					tjPayloadIs = true; // 为木马有效载荷
				}
			}
		}else if(preName.contains("EthernetMAC10GE") || preName.contains("b19_T200") || preName.contains("s38584_T100")
				|| preName.contains("s38584_T200") || preName.contains("wb_conmax") || preName.contains("vga_enh")
				|| preName.contains("s35932")){
			if(tjTrigger != null){
				if (tjTrigger.find() && index == -1) {
					tjTriggerIs = true; // 为木马触发器
				}
			}
			if (index != -1) {
				tjPayloadIs = true; // 为木马有效载荷
			}
		}else {
			//适用基准：b19_T100 以及b19_T200 / s38584_scan_T300_Tjin以及其他
			if(tjTrigger != null){
				if (tjTrigger.find()) {
					tjTriggerIs = true; // 为木马触发器
				}
			}
			if (index != -1) {
				tjPayloadIs = true; // 为木马有效载荷
			}
		}

		// 4)将读到的数据存放到不同的输入输出列表中
		while(matcher.find()) {
			String str = matcher.group();
//			System.out.println(str);
			String type = str.substring(0, str.indexOf("("));

			if (referenceName.indexOf("HADDX") != -1) {
				if (type.equals("C1") || type.equals("SO")) {
					headvex.add(str);
				}else {
					tailvex.add(str);
				}
			}else if (referenceName.indexOf("FADDX") != -1 ) {
				if (type.equals("CO") || type.equals("S")) {
					headvex.add(str);
				}else {
					tailvex.add(str);
				}

			}else {
				if (type.equals("Q") || type.equals("QN")||type.equals("ZN")) {
					headvex.add(str);
				}else {
					tailvex.add(str);
				}
			}
		}

		// 5)使用二重循环将列表中的数据导入到vertexInfo和edgesInfo中
		for (String string : headvex) {

			int verLen = vertexInfo.size();
			String verType = string.substring(0, string.indexOf("("));
			String verName = string.substring(string.indexOf("(") + 1, string.indexOf(")"));

			// 判断是否为输出节点
			boolean outputPinIs = false;
			for (String str2 : outputInfo) {
				if (str2.equals(verName)) {
					outputPinIs = true;
					break;
				}
			}

			//存入顶点表中
			vertexInfo.add(new ArrayList<>());
			vertexInfo.get(verLen).add(verName);
			vertexInfo.get(verLen).add(referenceName);
			vertexInfo.get(verLen).add(verType);
			if (tjTriggerIs) {
				vertexInfo.get(verLen).add("TjTrigger");
			}else if (tjPayloadIs) {
				vertexInfo.get(verLen).add("TjPayload");
			}else if(outputPinIs) {
				vertexInfo.get(verLen).add("outputPin");
			}else {
				vertexInfo.get(verLen).add("noTrojan");
			}

			//存入边表中
			for (String string2 : tailvex) {

				int edgeLen = edgesInfo.size();
				String edgeType = string2.substring(0, string2.indexOf("("));
				String edgeName = string2.substring(string2.indexOf("(") + 1, string2.indexOf(")"));

				edgesInfo.add(new ArrayList<>());
				edgesInfo.get(edgeLen).add(edgeName);
				edgesInfo.get(edgeLen).add(verName);
				edgesInfo.get(edgeLen).add(edgeType);
			}
		}

	}

	/**
	 * assign看作一个逻辑，器件的名称为assign，器件的输入为等式右边的部分，器件的输出为等式左边的部分
	 */
	private void AssignExtract(String assignNet) {

		// 找到该逻辑门的输入输出节点
		String outVer = assignNet.substring(0, assignNet.indexOf("=")); //等号左边
		String inVer = assignNet.substring(assignNet.indexOf("=") + 1); //等号右边

		// 判断是否为输出节点
		boolean outputPinIs = false;
		for (String str2 : outputInfo) {
			if (str2.equals(outVer)) {
				outputPinIs = true;
				break;
			}
		}

		// 将逻辑门的输出记录至顶点列表vertexInfo中
		int verLen = vertexInfo.size();
		vertexInfo.add(new ArrayList<>());
		vertexInfo.get(verLen).add(outVer);
		vertexInfo.get(verLen).add("assign");
		vertexInfo.get(verLen).add("Q");
		if (outputPinIs) {
			vertexInfo.get(verLen).add("outputPin");
		}else{
			vertexInfo.get(verLen).add("noTrojan");
		}

		// 将逻辑门的输入输出记录至边信息表edgesInfo中
		int edgeLen = edgesInfo.size();
		edgesInfo.add(new ArrayList<>());
		edgesInfo.get(edgeLen).add(inVer);
		edgesInfo.get(edgeLen).add(outVer);
		edgesInfo.get(edgeLen).add("IN1");

	}

	/**
	 * 	2、输出节点的处理也有两种形式，一种为数组类型，一种为无括号类型的
	 * 		1）对数组类型的处理方式与对输入节点的处理方式完全相同
	 *
	 */
	private void OutputExtract(String outNet) {

		int bracket = outNet.indexOf("[");

		if (bracket == -1) {
			// 为非数组类型
			Pattern pattern = Pattern.compile(",");
			String[] notes = pattern.split(outNet);
			for (String str : notes) {
				outputInfo.add(str);
			}
		}else {
			//输入为数组的形式
			//查找数组的名称
			String outGroupName = outNet.substring(outNet.indexOf("]") + 1);

			// 查找数组的长度
			String lift = outNet.substring(bracket + 1, outNet.indexOf(":"));
			String right = outNet.substring(outNet.indexOf(":") + 1, outNet.indexOf("]"));

//			// 将数组长度由字符串转换为数字
//			int inGroupLen = Integer.parseInt(lift) - Integer.parseInt(right);

			// 遍历，将数组中的元素依次存放到InputInfo列表和vertexInfo列表中
			for(int i = Integer.parseInt(right); i <= Integer.parseInt(lift); i++) {
				String str = outGroupName + "[" + String.valueOf(i) + "]";
				outputInfo.add(str);
			}
		}
	}

	/**
	 *  1、 输入节点的处理有两种形式，一种为数组类型的，一种为无括号类型的
	 *    1）、对数组类型的处理方法为，首先读取数组的长度，然后将数组的中的名称添加编号记录下来
	 *    2）、对无括号类型的使用pattern.split()做切分
	 */
	private void InputExtract(String inNet) {

		int bracket = inNet.indexOf("[");

		if (bracket == -1) {
			// 为非数组类型
			Pattern pattern = Pattern.compile(",");
			String[] notes = pattern.split(inNet);
			for (String str : notes) {
				inputInfo.add(str);
				int vertexLen = vertexInfo.size();
				vertexInfo.add(new ArrayList<>());

				vertexInfo.get(vertexLen).add(str);
				vertexInfo.get(vertexLen).add("null");
				vertexInfo.get(vertexLen).add("null");
				vertexInfo.get(vertexLen).add("inputPin");
			}

		}else {
			//输入为数组的形式
			//查找数组的名称
			String inGroupName = inNet.substring(inNet.indexOf("]") + 1);

			// 查找数组的长度
			String lift = inNet.substring(bracket + 1, inNet.indexOf(":"));
			String right = inNet.substring(inNet.indexOf(":") + 1, inNet.indexOf("]"));

//			// 将数组长度由字符串转换为数字
//			int inGroupLen = Integer.parseInt(lift) - Integer.parseInt(right);

			// 遍历，将数组中的元素依次存放到InputInfo列表和vertexInfo列表中
			for(int i = Integer.parseInt(right); i <= Integer.parseInt(lift); i++) {
				String str = inGroupName + "[" + String.valueOf(i) + "]";
				inputInfo.add(str);

				int vertexLen = vertexInfo.size();
				vertexInfo.add(new ArrayList<>());

				vertexInfo.get(vertexLen).add(str);
				vertexInfo.get(vertexLen).add("null");
				vertexInfo.get(vertexLen).add("null");
				vertexInfo.get(vertexLen).add("inputPin");
			}
		}
	}

}
