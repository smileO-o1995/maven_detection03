package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ColModule {

	// 链表referModule用于存储每个module中的内容；每个子元素表示一个完整的verilog语句
	// 链表moduleName用于存储每个module的名字

	List<String> moduleName = new ArrayList<>();
	List<List<String>> refModule = new ArrayList<>();
	int number = 0;

	/**
	 * 	该函数的作用是将文件中的内容重新整合，使该程序不仅适用于单个moduel的情况，也适用于多个moduel的情况
	 * 	处理后的到一个新的文件，该文件中去掉了空格
	 * 	且每个完整的语句适用一行数据表示，而结尾的分号则已经去掉
	 *	moduleName对应每个module的名称
	 *	refModule对应每个module中的语句
	 * @param file
	 * @throws IOException
	 */
	public void readModule(File file) throws IOException{

		// 读文件流
		BufferedReader bReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));


		/*读取文件
		（1）去掉特殊字符，除空格外
		（2）去除注释
		（3）一个content表示逻辑门实例语句
		 */
		String line1; // 该容器存储每次读取（读取一行）的数据
		String content = ""; // 该容器用于存储每一个完整的语句（使用“;”作为隔离）
		int endLabel = -1; // 用于标记content中是否出现语句结束标志
		while ((line1 = bReader.readLine()) != null) {

			String line = line1.trim(); // 去掉首尾的空格符
			Pattern pattern = Pattern.compile("\\t|\\r|\\n"); // 暂时保留空格符，去掉其他特殊转义字符
			Matcher matcher = pattern.matcher(line);
			String dest = matcher.replaceAll("");//dest为该行数据去掉其他特殊字符后得到的字符串

			int note = dest.indexOf("//");
			// 读到注释行时，清空注释符以及注释符以后的数据
			if (note != -1) {
				dest = dest.substring(0, note);
			}

			// 当dest为endmodule语句的时候，直接将dest清空
			if (dest.indexOf("endmodule") != -1) {
				dest = "";
			}

			content += dest;

			endLabel = dest.indexOf(";");
			if (endLabel != -1) {
				/*
				 *  当出现content结束标志的时候，需要做如下事情：
				 *  	第一步：将content中，除了第一个出现的空格以外，其余的空格都删除掉；且第一个空格仅保留一个，得到一个符合相应格式的字符串content，
				 *  		它是一个完整的verilog语句
				 *  	第二步：判断content语句是否以module开头,若是refModule数组重开一组
				 *
				 */
				Pattern p1 = Pattern.compile("\\s+"); // '\s'表示空格符
				Matcher m1 = p1.matcher(content);
				String subStr = "";
				if(m1.find()) {
					subStr = content.substring(m1.end(),content.indexOf(";"));
					subStr = p1.matcher(subStr).replaceAll("");
					content = content.substring(0, m1.start()) + " " + subStr;
					content = content.replaceAll("1'b0", "state_at_0");
					content = content.replaceAll("1'b1", "state_at_1");
					content = content.replaceAll("\\\\test_point/", "test_point_");

				}

				int moduleSize = refModule.size();

				if (content.startsWith("module")) {
					refModule.add(new ArrayList<>());
					moduleName.add(subStr.substring(0,subStr.indexOf("(")) + " ");
					refModule.get(moduleSize).add(content);
				}else {
					refModule.get(moduleSize - 1).add(content);
				}

				content = "";
			}
		}

		bReader.close();

		explorePin();

	}

	private void explorePin() {

		for (int k = 0; k < refModule.size(); k++) {

			List<String> OneModule = refModule.get(k);

			int moduleSize = refModule.get(k).size();
			List<List<Integer>> large = new ArrayList<>();
			List<String> pinList = new ArrayList<>();

			for (int i = 1; i < moduleSize; i++) {

				String content = OneModule.get(i);
				Pattern p1 = Pattern.compile("^(input|output|wire|assign)");
				Matcher m1 = p1.matcher(content);
				Matcher m2 = Pattern.compile("\\[\\d+:\\d+\\]").matcher(content);
				Matcher m3 = p1.matcher(content);

				if (m1.find() && m2.find()) {
					int listSize = large.size();
					large.add(new ArrayList<>());
					pinList.add(content.substring(content.indexOf("]") + 1) + " "); //存储的过程中，为了避免类似n1和n11之间的混淆，以空格符作为字符结尾标志符
					large.get(listSize).add(Integer.parseInt(content.substring(content.indexOf("[") + 1, content.indexOf(":")))); //left
					large.get(listSize).add(Integer.parseInt(content.substring(content.indexOf(":") + 1, content.indexOf("]")))); //right
				}

				if (!m3.find()) { //当语句为逻辑器件语句的时候，进入if语句

					// 以INVX0 P2_P4(.IN(IR_8_),.QN(n66))为例
					String str1[] = Pattern.compile("\\s").split(content);

					if (moduleName.indexOf(str1[0] + " ") != -1) { // 只对调用module设计的语句进行展开

						String reStr = ""; //用于暂时存放主调方的语句

						// 如果运行到这一步，说明当前语句为主调语句
						reStr = reStr + str1[0] + " "; //这里恢复了INVX0包括了后面的空格符
						String cellpri = str1[1].substring(0, str1[1].indexOf("(") + 1);
						reStr = reStr + cellpri; // 这里恢复了INVX0 P2_P4(

						String inerContent = str1[1].substring(str1[1].indexOf("(")+1, str1[1].lastIndexOf(")")); //完整的语句为：reStr+inerContent+")"

						if (inerContent.indexOf("(") == -1) { //如果是位置调用的时候，进入if语句

							//运行到这里是因为这里的调用语句适用的是位置调用
							String t3[] = Pattern.compile(",").split(inerContent);
							String string3 = "";
							for (String string : t3) {
								int index = pinList.indexOf(string + " ");
								if (index != -1) {
									for (int k2 = large.get(index).get(0); k2 >= large.get(index).get(1); k2--) {
										string3 = string3 + string + "[" + k2 + "]" + ",";
									}
								}else {
									string3 = string3 + string + ",";
								}
							}
							reStr =reStr + string3;

						} else {

							// content的完整格式为reStr + "." + inerContent + ")"
							inerContent = str1[1].substring(str1[1].indexOf("(") + 2, str1[1].lastIndexOf(")")); // 这里的样式为IN(IR_8_),.QN(n66)
							// 对inerContent进行分割，以"\\,\\."为分割符号
							// content的完整格式为reStr + "." + t3[0] + ",." + t3[1] + ",." + …… + ",." + t3[t3.length - 1])
							String t3[] = Pattern.compile("\\,\\.").split(inerContent); // t3[j] = IN(IR_8_)

							/*
							 * t3[j]有下面的7种格式
							 * 		1、A({N13,N12,N11}) //这3个引脚为单个引脚
							 * 		2、B({N10[12:3],N9[4:1]}) //这为两个数组引脚
							 * 		3、C({N13,N9}) //其中N9为数组引脚
							 * 		4、D(N9)  //N9为数组引脚
							 * 		5、E(N13) //N13为单引脚
							 * 		6、A(N10[12:3])
							 * 		7、B() //没有对应的引脚
							 */
							for (int j = 0; j < t3.length; j++) {

								// 首先对第七种情况（没有对应的引脚）进行判断
								Matcher m4 = Pattern.compile("\\w+\\(\\)").matcher(t3[j]);
								if (m4.find()) {
									t3[j] = t3[j].substring(0, t3[j].indexOf("(") + 1) + "abandon_" + number + ")";
									number ++;
								}else {
									// content的完整格式为：reStr + "." + pinName + "(" + t4 + ")" + ",." + t3[1] + ",." + …… + ",." + t3[t3.length - 1])
									String t4 = t3[j].substring(t3[j].indexOf("(")+1, t3[j].lastIndexOf(")")); //IR_8_

									// 首先将第二种和第六种格式转换为第一种形式
									Matcher m5 = Pattern.compile("\\w+\\[\\d+:\\d+\\]").matcher(t4);
									while (m5.find()) {
										String nameArr = m5.group(); // nameArr的形式为：in[23:0]
										String namePri = nameArr.substring(0,nameArr.indexOf("["));

										// 查看中括号中的长度
										String lift = nameArr.substring(nameArr.indexOf("[") + 1, nameArr.indexOf(":"));
										String right = nameArr.substring(nameArr.indexOf(":") + 1, nameArr.indexOf("]"));


										// 遍历，将nameArr中的数组元素展开保存在临时字符串string中，然后用它替换掉nameArr
										String string = "";
										for(int k2 = Integer.parseInt(lift); k2 >= Integer.parseInt(right); k2--) {
											string = string + namePri + "[" + k2 + "]" + ",";
										}

										t4 = t4.replace(nameArr, string.substring(0,string.lastIndexOf(",")));
									}

									String inerStr = "";
									if (t4.indexOf("{") != -1) { //对第二种和第一种情况讨论
										// 现在对第二种情况进行Pin引脚替换
										inerStr = t4.substring(t4.indexOf("{")+1, t4.lastIndexOf("}")); // state_at_1,state_at_1,n453
									}else {
										inerStr = t4;
									}

									String string2[] = Pattern.compile("\\,").split(inerStr);
									String string3 = "";
									for (String string : string2) {
										int index = pinList.indexOf(string + " ");
										if (index != -1) {
											for (int k2 = large.get(index).get(0); k2 >= large.get(index).get(1); k2--) {
												string3 = string3 + string + "[" + k2 + "]" + ",";
											}
										}else {
											string3 = string3 + string + ",";
										}
									}
									t3[j] = t3[j].substring(0, t3[j].indexOf("(") + 1) + string3.substring(0, string3.lastIndexOf(",")) + ")";
								}
								reStr = reStr + "." + t3[j] + ",";
							}
						}

						reStr = reStr.substring(0,reStr.lastIndexOf(",")) + ")";
						refModule.get(k).remove(i);
						refModule.get(k).add(i, reStr);
					}
				}
			}

		}

	}

	public String ResetFile(File file, String savePath)throws IOException {
		// 文件写入流
		String reFile = file.getName().substring(0, file.getName().lastIndexOf(".")) + "_re.txt";
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

		/*
		 * 将源文件中的内容以平面的形式写入到reContent文件中
		 * 	遍历refModule中的最后一个Arraylist
		 * 	若遇到以moduleName开头的信号，则执行替换操作
		 * 		替换操作函数的输入参数包括moduleName的索引值index，
		 * 		函数的返回值，是一个一维的ArrayList，用于存放底层的实例
		 * 否则，执行写入操作
		 *
		 * 	遍历最后一个module：
		 * 	对每个语句进行判断：是否调用了其他的设计（module）
		 * 		首先对每个content进行分割，以"\\s"为分割依据
		 * 		判断的依据为：conArray[0]+" "是否有moduleName中有对应的元素，使用的函数为indexOf
		 */

		int i = 0;
		int moduleSize = refModule.size();
		List<String> topModule = refModule.get(moduleSize - 1);

		while(i < topModule.size()) {
			String content = topModule.get(i);
			String conArray[] = Pattern.compile("\\s").split(content);
			int index = moduleName.indexOf(conArray[0] + " ");
			if (index == -1) {
				pWriter.println(content);
				pWriter.flush();
			}else {
				List<String> cellContents = reCallCell(content, index);
				topModule.addAll(i+1, cellContents);
			}

			i++;
		}

//		for (String content : refModule.get(moduleSize - 1)) {
//			int i = 0;
//			for (String name : moduleName) {
//				i++;
//				if (content.startsWith(name)) {
//					List<String> cellContents = reCallCell(content, i-1);
//					for (String string : cellContents) {
//						pWriter.println(string);
//						pWriter.flush();
//					}
//					break;
//				}
//			}
//			// 当语句没有调用底层设计的判定条件为：i==moduleSize
//			if (i == moduleSize) {
//
//				pWriter.println(content);
//				pWriter.flush();
//			}
//
//		}
		pWriter.close();

		return reFile; // 重新写入的文件
	}

	/*
	 *  第一步：将content的cell名字提取出来，改名字使用cellName
	 *  第二步：将content括号中的内容intContent，用",."隔开，得到netStr[]数组
	 *  第三步：遍历数组netStr[] netStr[]的元素用nets表示
	 *  	将每个nets括号外面的内容先保存在points中 points为一个二维的ArrayList
	 *  	每个nets会有只会有一种格式，并用小括号括起来
	 *
	 */
	private List<String> reCallCell(String content, int index) {

		List<String> cellContent = new ArrayList<>();

		String cellName = content.substring(content.indexOf(" ")+1, content.indexOf("(")); //表示该实例的真正名字
		List<List<String>> netPoints = new ArrayList<>(); //用于存放该设计的所有输入输出引脚，一系列的引脚占用一个list

		String intContent = content.substring(content.indexOf("(")+1, content.lastIndexOf(")"));

		if (intContent.indexOf("(") == -1) {

			netPoints.add(new ArrayList<>());
			String netStr[] = Pattern.compile(",").split(intContent);
			netPoints.get(0).add("placeCall");
			for (String string : netStr) {
				netPoints.get(0).add(string); //以空格符作为结尾符
			}

		} else {
			intContent = content.substring(content.indexOf(".")+1,content.lastIndexOf(")"));
			String netStr[] = Pattern.compile("\\,\\.").split(intContent); //netStr中的每个元素，nets大概是这样：A(rx_eq0_crc_rx_31_, rx_eq0_crc_rx_28_)

			for (String nets : netStr) {
				int pointSize = netPoints.size();
				netPoints.add(new ArrayList<>());
				netPoints.get(pointSize).add(nets.substring(0,nets.indexOf("(")));// 将每个nets括号外面的内容先保存在points中，作为实例索引，存放的大概是这样：A

				String interStr = nets.substring(nets.indexOf("(")+1,nets.lastIndexOf(")"));

				String items[] = Pattern.compile(",").split(interStr);
				for (String item : items) {
					netPoints.get(pointSize).add(item);
				}
			}
		}


		/*
		 *  下面是对底层对应的module进行处理
		 *  首先创建临时列表，用于存储底层设计的pin引脚，首先需要读取underContent中的第一行数据，将括号中的内容，以“，”作为分割点，然后将数组中的内容保存到underContent中去
		 * 	第一步：找到对应的module根据索引index，记为underContent
		 * 	第二步：提取当前underContent的引脚信息，首先做第一个遍历underContent，对所有以input/output开头的语句做处理
		 * 		input（同output）有两种表现形式：（两种形式的判断依据为：有没有中括号）
		 * 		第一种，input [31:0]A;
		 * 		第二种，output LT,GT,EQ,LE,GE,NE
		 *
		 *  第三步：现在已有的信息包括：
		 *  	顶层设计的引脚调用信息部分：netPoints
		 *  	底层设计的引脚使用信息部分：pinPoint
		 *  	该设计的所有verilog语句信息：underContent
		 *
		 *  我们将读取逻辑门实例部分，将逻辑门中的net信息全部替换为顶层的设计中的引脚，且保证他的唯一性
		 */
		List<List<String>> pinPoint = new ArrayList<>(); // 创建临时list存储引脚信息
		List<String> underContent = refModule.get(index); //找到对应的底层设计

		if (netPoints.size() == 1) {
			// 位置调用时，netPoints.size() == 1
			String headName = underContent.get(0);
			String headPoint[] = Pattern.compile(",").split(headName.substring(headName.indexOf("(")+1, headName.lastIndexOf(")")));
			for (int i = 0; i < headPoint.length; i++) {
				pinPoint.add(new ArrayList<>());
				pinPoint.get(i).add(headPoint[i] + " ");
			}
		}else {
			for (int i = 0; i < netPoints.size(); i++) {
				pinPoint.add(new ArrayList<>());
				pinPoint.get(i).add(netPoints.get(i).get(0) + " ");
			}
		}

		for (int i = 1; i < underContent.size(); i++) {
			String str1 = underContent.get(i); //str1的形式可能是这样：input [31:0]A
			if (str1.startsWith("input") || str1.startsWith("output")) {

				String str2[] = Pattern.compile(" ").split(str1);// str2的可能形式为：str2中含有两个元素，
				// 一个为input,另一个为[31:0]A
				int bracket = str2[1].indexOf("[");

				if (bracket == -1) {
					// str1为非数组类型
					Pattern pattern = Pattern.compile(",");
					String[] notes = pattern.split(str2[1]);
					for (String str : notes) {
						for (int j = 0; j < pinPoint.size(); j++) {
							if ((str + " ").equals(pinPoint.get(j).get(0))) {
								pinPoint.get(j).add(str + " ");
								break;
							}
						}
					}
				}else {
					//输入为数组的形式
					//查找数组的名称
					String namePri = str2[1].substring(str2[1].indexOf("]") + 1);

					// 查找数组的长度
					String left = str2[1].substring(bracket + 1, str2[1].indexOf(":"));
					String right = str2[1].substring(str2[1].indexOf(":") + 1, str2[1].indexOf("]"));

					// 遍历，将数组中的元素依次存放到InputInfo列表和vertexInfo列表中
					for (int k = 0; k < pinPoint.size(); k++) {
						if ((namePri + " ").equals(pinPoint.get(k).get(0))) {
							for(int j = Integer.parseInt(left); j >= Integer.parseInt(right); j--) {
								String str = namePri + "[" + String.valueOf(j) + "]";
								pinPoint.get(k).add(str + " ");
							}

							break;
						}
					}
				}
			}
		}

		if (netPoints.size() == 1) {
			for (int i = 1; i < pinPoint.size(); i++) {
				int len = pinPoint.get(i).size();
				for (int j = 1; j < len; j++) {
					pinPoint.get(0).add(pinPoint.get(i).get(j));
				}
			}
			while(pinPoint.size() > 1) {
				pinPoint.remove(pinPoint.get(pinPoint.size() - 1));
			}

			System.out.println(pinPoint.size());
		}

		/*
		 *  开始第三步
		 *  处理对象的格式大概是这样的：INVX0 U1(.IN(B[1]),.QN(n2))
		 *  重组的格式str2[0]+" "+cellName+"_"+t1+"("+t2+")"
		 *
		 *  这里出现问题，因为这里大概的格式会有以下几种：
		 *  	1、这种没有调用任何的设计：INVX0 U1(.IN(B[1]),.QN(n2))
		 *  	2、expand_key_128_0 a1(.clk(clk),.in(k0),.out_1(rk1),.out_2(k0b),.rcon({state_at_1,state_at_1}))
		 *  		这种调用是主调用的最常规格式
		 *  	3、S4_0 S4_0(.clk(clk),.in({in[23:0],in[31:24]}),.out(k4a))
		 *  		这种是存在数组调用
		 *  	4、b15_DW01_add_1 add_0_root(.SUM({N3098,N3097,N3096,N3095,N3094,N3093}),.CO())
		 *  		这种调用存在空引脚，需要将空引脚保留下来
		 */

		for (int i = 1; i < underContent.size(); i++) {
			String str1 = underContent.get(i);
			String t1 = "";
			String t2 = "";
			if (!str1.startsWith("input") && !str1.startsWith("output") && !str1.startsWith("wire") && !str1.startsWith("assign")) {
				String str2[] = Pattern.compile("\\s").split(str1);
				System.out.println(str1);

				t1 = str2[1].substring(0,str2[1].indexOf("("));// U1
				t2 = str2[1].substring(str2[1].indexOf("(") + 1, str2[1].lastIndexOf(")")); // .IN(B[1]),.QN(n2)
				String t3[] = Pattern.compile("\\,\\.").split(t2.substring(t2.indexOf(".") + 1)); //IN(B[1])  QN(n2)
				for (int j = 0; j < t3.length; j++) {  //t3[j]的形式为：IN(B[1])

					Matcher m4 = Pattern.compile("\\w+\\(\\)").matcher(t3[j]);
					if (!m4.find()) {
						String t4 = t3[j].substring(t3[j].indexOf("(")+1, t3[j].lastIndexOf(")")); //B[1]

						String string2[] = Pattern.compile("\\,").split(t4);
						String string3 = "";
						for (String string : string2) { // n453
							if (string.equals("state_at_0") || string.equals("state_at_1")) {
								string3 = string3 + string + ",";
							}else {
								boolean find = false;
								for (int k = 0; k < pinPoint.size(); k++) {
									for (int l = 1; l < pinPoint.get(k).size(); l++) {
										if ((string+" ").equals(pinPoint.get(k).get(l))) {

											string3 = string3 + netPoints.get(k).get(l) + ",";
											find = true;
											break;
										}
									}
									if (find) {
										break;
									}
								}
								if (!find) {
									string3 = string3 + cellName + "_" +string + ",";
								}
							}
						}

						t4 = string3.substring(0,string3.lastIndexOf(","));

						t3[j] = t3[j].substring(0, t3[j].indexOf("(")+1) + t4 + ")";
					}
				}

				String reStr = ""; //要将t3恢复成.IN(B[1]),.QN(n2)的样式
				for (int k = 0; k < t3.length; k++) {
					reStr = reStr + "." + t3[k] + ",";
				}

				// 将reStr恢复成INVX0 U1(.IN(B[1]),.QN(n2))的样式
				reStr = str2[0]+" "+cellName+"_"+t1+"("+reStr.substring(0,reStr.lastIndexOf(","))+")";

				cellContent.add(reStr);
			}

			if (str1.startsWith("assign")) { // assign DIFF[0]=B[0]
				String str2[] = Pattern.compile("\\s").split(str1); // 一个元素为assign，另一个元素为DIFF[0]=B[0]
				String str3[] = Pattern.compile("=").split(str2[1]);
				for (int j = 0; j < str3.length; j++) {
					boolean find = false;
					for (int k = 0; k < pinPoint.size(); k++) {
						for (int k2 = 1; k2 < pinPoint.get(k).size(); k2++) {
							if ((str3[j] + " ").equals(pinPoint.get(k).get(k2))) {

								str2[1] = str2[1].replace(str3[j], netPoints.get(k).get(k2));
								find = true;
								break;
							}
						}
						if (find) {
							break;
						}
					}
					if (!find && !(str3[j].equals("state_at_1") && !(str3[j].equals("state_at_0")))) {
						str2[1] = str2[1].replaceAll(str3[j], cellName+"_"+str3[j]);
					}
				}

				cellContent.add(str2[0]+" "+str2[1]);
			}
		}

		return cellContent;
	}

}

