package util;

/**
 * @author wen
 * @date 2019/11/26 0026-21:42
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GateCOUtil {

    List<List<Integer>> verPacket = new ArrayList<>();

    public GateCOUtil(List<List<Object>> gatePacket) {

        System.out.println("CO   " + gatePacket.get(0).get(0));

        switch ((String)gatePacket.get(0).get(0)) {

            //反相器
            case "INVX0" : INVforCO(gatePacket); break;
            case "INVX1" : INVforCO(gatePacket); break;
            case "INVX2" : INVforCO(gatePacket); break;
            case "INVX4" : INVforCO(gatePacket); break;
            case "INVX8" : INVforCO(gatePacket); break;
            case "INVX16" : INVforCO(gatePacket); break;
            case "INVX32" : INVforCO(gatePacket); break;

            //反向缓冲器
            case "IBUFFX2" : IBUFFforCO(gatePacket); break;
            case "IBUFFX4" : IBUFFforCO(gatePacket); break;
            case "IBUFFX8" : IBUFFforCO(gatePacket); break;
            case "IBUFFX16" : IBUFFforCO(gatePacket); break;
            case "IBUFFX32" : IBUFFforCO(gatePacket); break;

            //无反向缓冲器
            case "NBUFFX2" : NUBFFforCO(gatePacket); break;
            case "NBUFFX4" : NUBFFforCO(gatePacket); break;
            case "NBUFFX8" : NUBFFforCO(gatePacket); break;
            case "NBUFFX16" : NUBFFforCO(gatePacket); break;
            case "NBUFFX32" : NUBFFforCO(gatePacket); break;

            //无反向使能缓冲器
            case "TNBUFFX1" : TNBUFFforCO(gatePacket); break;
            case "TNBUFFX2" : TNBUFFforCO(gatePacket); break;
            case "TNBUFFX4" : TNBUFFforCO(gatePacket); break;
            case "TNBUFFX8" : TNBUFFforCO(gatePacket); break;
            case "TNBUFFX16" : TNBUFFforCO(gatePacket); break;
            case "TNBUFFX32" : TNBUFFforCO(gatePacket); break;

            // D触发器
            case "DFFX1" : DFFforCO(gatePacket); break;
            case "DFFX2" : DFFforCO(gatePacket); break;
            case "DFFNX1" : DFFforCO(gatePacket); break;
            case "DFFNX2" : DFFforCO(gatePacket); break;

            // 上升沿D触发器 异步置位AS
            case "DFFASX1" : DFFASforCO(gatePacket); break;
            case "DFFASX2" : DFFASforCO(gatePacket); break;

            //上升沿D触发器 异步复位AR
            case "DFFARX1" : DFFARforCO(gatePacket); break;
            case "DFFARX2" : DFFARforCO(gatePacket); break;

            // 扫描D触发器
            case "SDFFX1" : SDFFforCO(gatePacket); break;
            case "SDFFX2" : SDFFforCO(gatePacket); break;

            // 扫描D触发器 异步复位
            case "SDFFARX1" : SDFFARforCO(gatePacket);break;
            case "SDFFARX2" : SDFFARforCO(gatePacket);break;

            // 扫描D触发器 异步置位
            case "SDFFASX1" : SDFFASforCO(gatePacket);break;
            case "SDFFASX2" : SDFFASforCO(gatePacket);break;

            // 同步低电平使能 下降沿D触发器
            case "RDFFNX1" : RDFFNforCO(gatePacket);break;
            case "RDFFNX2" : RDFFNforCO(gatePacket);break;

            // 扫描D触发器 异步置位与复位
            case "SDFFASRX1" : SDFFASRforCO(gatePacket);break;
            case "SDFFASRX2" : SDFFASRforCO(gatePacket);break;

            //半加器HADD
            case "HADDX1" : HADDforCO(gatePacket); break;
            case "HADDX2" : HADDforCO(gatePacket); break;

            //全加器FADD
            case "FADDX1" : FADDforCO(gatePacket); break;
            case "FADDX2" : FADDforCO(gatePacket); break;

            //与门
            case "AND2X1" : ANDforCO(gatePacket); break;
            case "AND2X2" : ANDforCO(gatePacket); break;
            case "AND2X4" : ANDforCO(gatePacket); break;
            case "AND3X1" : ANDforCO(gatePacket); break;
            case "AND3X2" : ANDforCO(gatePacket); break;
            case "AND3X4" : ANDforCO(gatePacket); break;
            case "AND4X1" : ANDforCO(gatePacket); break;
            case "AND4X2" : ANDforCO(gatePacket); break;
            case "AND4X4" : ANDforCO(gatePacket); break;

            //与非门
            case "NAND2X0" : NANDforCO(gatePacket); break;
            case "NAND2X1" : NANDforCO(gatePacket); break;
            case "NAND2X2" : NANDforCO(gatePacket); break;
            case "NAND2X4" : NANDforCO(gatePacket); break;
            case "NAND3X0" : NANDforCO(gatePacket); break;
            case "NAND3X1" : NANDforCO(gatePacket); break;
            case "NAND3X2" : NANDforCO(gatePacket); break;
            case "NAND3X4" : NANDforCO(gatePacket); break;
            case "NAND4X0" : NANDforCO(gatePacket); break;
            case "NAND4X1" : NANDforCO(gatePacket); break;

            //或门
            case "OR2X1" : ORforCO(gatePacket); break;
            case "OR2X2" : ORforCO(gatePacket); break;
            case "OR2X4" : ORforCO(gatePacket); break;
            case "OR3X1" : ORforCO(gatePacket); break;
            case "OR3X2" : ORforCO(gatePacket); break;
            case "OR3X4" : ORforCO(gatePacket); break;
            case "OR4X1" : ORforCO(gatePacket); break;
            case "OR4X2" : ORforCO(gatePacket); break;
            case "OR4X4" : ORforCO(gatePacket); break;

            //或非门
            case "NOR2X0" : NORforCO(gatePacket); break;
            case "NOR2X1" : NORforCO(gatePacket); break;
            case "NOR2X2" : NORforCO(gatePacket); break;
            case "NOR2X4" : NORforCO(gatePacket); break;
            case "NOR3X0" : NORforCO(gatePacket); break;
            case "NOR3X1" : NORforCO(gatePacket); break;
            case "NOR3X2" : NORforCO(gatePacket); break;
            case "NOR3X4" : NORforCO(gatePacket); break;
            case "NOR4X0" : NORforCO(gatePacket); break;
            case "NOR4X1" : NORforCO(gatePacket); break;

            //二输入异或
            case "XOR2X1" : XORforCO(gatePacket); break;
            case "XOR2X2" : XORforCO(gatePacket); break;

            //三输入异或
            case "XOR3X1" : XORforCO(gatePacket); break;
            case "XOR3X2" : XORforCO(gatePacket); break;

            //二输入同或
            case "XNOR2X1" : XNORforCO(gatePacket); break;
            case "XNOR2X2" : XNORforCO(gatePacket); break;

            //三输入同或
            case "XNOR3X1" : XNORforCO(gatePacket); break;
            case "XNOR3X2" : XNORforCO(gatePacket); break;

            //与或门AO21
            case "AO21X1" : AO21forCO(gatePacket); break;
            case "AO21X2" : AO21forCO(gatePacket); break;

            //与或AO22
            case "AO22X1" : AO22forCO(gatePacket); break;
            case "AO22X2" : AO22forCO(gatePacket); break;

            //与或AO221
            case "AO221X1" : AO221forCO(gatePacket); break;
            case "AO221X2" : AO221forCO(gatePacket); break;

            //与或AO222
            case "AO222X1" : AO222forCO(gatePacket); break;
            case "AO222X2" : AO222forCO(gatePacket); break;

            //与或非门AOI21
            case "AOI21X1" : AOI21forCO(gatePacket); break;
            case "AOI21X2" : AOI21forCO(gatePacket); break;

            //与或非门AOI22
            case "AOI22X1" : AOI22forCO(gatePacket); break;
            case "AOI22X2" : AOI22forCO(gatePacket); break;

            //与或非AOI221
            case "AOI221X1" : AOI221forCO(gatePacket); break;
            case "AOI221X2" : AOI221forCO(gatePacket); break;

            //与或非AOI222
            case "AOI222X1" : AOI222forCO(gatePacket); break;
            case "AOI222X2" : AOI222forCO(gatePacket); break;

            //或与门OA21
            case "OA21X1" : OA21forCO(gatePacket); break;
            case "OA21X2" : OA21forCO(gatePacket); break;

            //或与门OA22
            case "OA22X1" : OA22forCO(gatePacket); break;
            case "OA22X2" : OA22forCO(gatePacket); break;

            //或与门OA221
            case "OA221X1" : OA221forCO(gatePacket); break;
            case "OA221X2" : OA221forCO(gatePacket); break;

            //或与门OA222
            case "OA222X1" : OA222forCO(gatePacket); break;
            case "OA222X2" : OA222forCO(gatePacket); break;

            //或与非门OAI21
            case "OAI21X1" : OAI21forCO(gatePacket); break;
            case "OAI21X2" : OAI21forCO(gatePacket); break;

            //或与非门OAI22
            case "OAI22X1" : OAI22forCO(gatePacket); break;
            case "OAI22X2" : OAI22forCO(gatePacket); break;

            //或与非门OAI221
            case "OAI221X1" : OAI221forCO(gatePacket); break;
            case "OAI221X2" : OAI221forCO(gatePacket); break;

            //或与非门OAI222
            case "OAI222X1" : OAI222forCO(gatePacket); break;
            case "OAI222X2" : OAI222forCO(gatePacket); break;

            //数据选择器MUX21
            case "MUX21X1" : MUX21forCO(gatePacket); break;
            case "MUX21X2" : MUX21forCO(gatePacket); break;

            //数据选择器MUX41
            case "MUX41X1" : MUX41forCO(gatePacket); break;
            case "MUX41X2" : MUX41forCO(gatePacket); break;

            //assign语句
            case "assign" : AssignforCO(gatePacket); break;

            //High to Low Level Shifter
            case "LSDNX1" : NUBFFforCO(gatePacket); break;
            case "LSDNX2" : NUBFFforCO(gatePacket); break;
            case "LSDNX4" : NUBFFforCO(gatePacket); break;
            case "LSDNX8" : NUBFFforCO(gatePacket); break;

            //hold 0 isolation cell(logic AND)
            case "ISOLANDX1" : ANDforCO(gatePacket); break;
            case "ISOLANDX2" : ANDforCO(gatePacket); break;
            case "ISOLANDX4" : ANDforCO(gatePacket); break;
            case "ISOLANDX8" : ANDforCO(gatePacket); break;

            //High to Low Level Shifter/ Active Low Enable
            case "LSDNENX1" : LSDNENforCO(gatePacket); break;
            case "LSDNENX2" : LSDNENforCO(gatePacket); break;
            case "LSDNENX4" : LSDNENforCO(gatePacket); break;
            case "LSDNENX8" : LSDNENforCO(gatePacket); break;

            // Hold 1 Isolation Cell (Logic OR):
            case "ISOLORX1" : ORforCO(gatePacket); break;
            case "ISOLORX2" : ORforCO(gatePacket); break;
            case "ISOLORX4" : ORforCO(gatePacket); break;
            case "ISOLORX8" : ORforCO(gatePacket); break;

            default:System.out.println((String)gatePacket.get(0).get(0)+":逻辑门还未录入");
                System.exit(0);
                break;
        }

    }

    private void LSDNENforCO(List<List<Object>> gatePacket) {

        int CO;
        int out_CO = (int)gatePacket.get(0).get(1);

        int cc0_1 = (int)gatePacket.get(1).get(3);
        int CO_1 = (int)gatePacket.get(1).get(2);

        int cc0_2 = (int)gatePacket.get(2).get(3);
        int CO_2 = (int)gatePacket.get(2).get(2);

        verPacket.add(new ArrayList<>());
        verPacket.get(0).add((int)gatePacket.get(1).get(0));
        verPacket.add(new ArrayList<>());
        verPacket.get(1).add((int)gatePacket.get(2).get(0));

        if (cc0_2 != 0) {
            CO = out_CO + cc0_2 + 1;
            if (CO_1 != -1) {
                CO = CO < CO_1 ? CO : CO_1;
            }
            verPacket.get(0).add(CO);
        }else {
            verPacket.get(0).add(CO_1);
        }

        if (cc0_1 != 0) {
            CO = out_CO + cc0_1 + 1;
            if (CO_2 != -1) {
                CO = CO < CO_2 ? CO : CO_2;
            }
            verPacket.get(1).add(CO);
        }else {
            verPacket.get(1).add(CO_2);
        }
    }

    private void AssignforCO(List<List<Object>> gatePacket) {

        int out_CO = (int)gatePacket.get(0).get(1);
        int in_CO = (int)gatePacket.get(1).get(2);

        verPacket.add(new ArrayList<>());
        verPacket.get(0).add((int)gatePacket.get(1).get(0));
        if (in_CO != -1) {
            out_CO = out_CO < in_CO ? out_CO : in_CO;
        }
        verPacket.get(0).add(out_CO);

    }

    private void MUX41forCO(List<List<Object>> gatePacket) {

        int indexIN1 = -1;
        int indexIN2 = -1;
        int indexIN3 = -1;
        int indexIN4 = -1;
        int indexS0 = -1;
        int indexS1 = -1;

        for (int i = 1; i < gatePacket.size(); i++) {
            String type = (String)gatePacket.get(i).get(1);
            if (type.equals("IN1")) {
                indexIN1 = i;
            }else if (type.equals("IN2")) {
                indexIN2 = i;
            }else if (type.equals("IN3")) {
                indexIN3 = i;
            }else if (type.equals("IN4")) {
                indexIN4 = i;
            }else if (type.equals("S0")) {
                indexS0 = i;
            }else {
                indexS1 = i;
            }
        }

        int CO;

        int out_CO = (int)gatePacket.get(0).get(1);

        int CC0_S0 = (int)gatePacket.get(indexS0).get(3);
        int CC1_S0 = (int)gatePacket.get(indexS0).get(4);

        int CC0_S1 = (int)gatePacket.get(indexS1).get(3);
        int CC1_S1 = (int)gatePacket.get(indexS1).get(4);

        //计算A1的CO值
        verPacket.add(new ArrayList<>());
        verPacket.get(0).add((int)gatePacket.get(indexIN1).get(0));
        int in1_CO = (int)gatePacket.get(indexIN1).get(2);

        if (CC0_S0 != 0 && CC0_S1 != 0) {
            CO = out_CO + CC0_S0 + CC0_S1 + 1;
            if (in1_CO != -1) {
                CO = CO < in1_CO ? CO : in1_CO;
            }
            verPacket.get(0).add(CO);
        }else {

            verPacket.get(0).add(in1_CO);
        }

        //计算A2的CO值
        verPacket.add(new ArrayList<>());
        verPacket.get(1).add((int)gatePacket.get(indexIN2).get(0));
        int in2_CO = (int)gatePacket.get(indexIN2).get(2);

        if (CC1_S0 != 0 && CC0_S1 != 0) {
            CO = out_CO + CC1_S0 + CC0_S1 + 1;
            if (in2_CO != -1) {
                CO = CO < in2_CO ? CO : in2_CO;
            }
            verPacket.get(1).add(CO);
        }else {

            verPacket.get(1).add(in2_CO);
        }

        //计算A3的CO值
        verPacket.add(new ArrayList<>());
        verPacket.get(2).add((int)gatePacket.get(indexIN3).get(0));
        int in3_CO = (int)gatePacket.get(indexIN3).get(2);

        if (CC0_S0 != 0 && CC1_S1 != 0) {
            CO = out_CO + CC0_S0 + CC1_S1 + 1;
            if (in3_CO != -1) {
                CO = CO < in3_CO ? CO : in3_CO;
            }
            verPacket.get(2).add(CO);
        }else {

            verPacket.get(2).add(in3_CO);
        }

        //计算A4的CO值
        verPacket.add(new ArrayList<>());
        verPacket.get(3).add((int)gatePacket.get(indexIN4).get(0));
        int in4_CO = (int)gatePacket.get(indexIN4).get(2);

        if (CC1_S0 != 0 && CC1_S1 != 0) {
            CO = out_CO + CC1_S0 + CC1_S1 + 1;
            if (in4_CO != -1) {
                CO = CO < in4_CO ? CO : in4_CO;
            }
            verPacket.get(3).add(CO);
        }else {

            verPacket.get(3).add(in4_CO);
        }

        int CC0_A1 = (int)gatePacket.get(indexIN1).get(3);
        int CC1_A1 = (int)gatePacket.get(indexIN1).get(4);

        int CC0_A2 = (int)gatePacket.get(indexIN2).get(3);
        int CC1_A2 = (int)gatePacket.get(indexIN2).get(4);

        int CC0_A3 = (int)gatePacket.get(indexIN3).get(3);
        int CC1_A3 = (int)gatePacket.get(indexIN3).get(4);

        int CC0_A4 = (int)gatePacket.get(indexIN4).get(3);
        int CC1_A4 = (int)gatePacket.get(indexIN4).get(4);

        List<Integer> cc_S0 = new ArrayList<>();
        List<Integer> cc_S1 = new ArrayList<>();

        //计算SO
        if (CC0_S1 != 0 && CC0_A1 != 0 && CC1_A2 != 0) {
            cc_S0.add(CC0_S1 + CC0_A1 + CC1_A2);
        }
        if (CC0_S1 != 0 && CC1_A1 != 0 && CC0_A2 != 0) {
            cc_S0.add(CC0_S1 + CC1_A1 + CC0_A2);
        }
        if (CC1_S1 != 0 && CC0_A3 != 0 && CC1_A4 != 0) {
            cc_S0.add(CC1_S1 + CC0_A3 + CC1_A4);
        }
        if (CC1_S1 != 0 && CC1_A3 != 0 && CC0_A4 != 0) {
            cc_S0.add(CC1_S1 + CC1_A3 + CC0_A4);
        }

        verPacket.add(new ArrayList<>());
        verPacket.get(4).add((int)gatePacket.get(indexS0).get(0));
        int S0_CO = (int)gatePacket.get(indexS0).get(2);
        if (cc_S0.size() != 0) {
            CO = out_CO + Collections.min(cc_S0) + 1;
            if (S0_CO != -1) {
                CO = CO < S0_CO ? CO : S0_CO;
            }
            verPacket.get(4).add(CO);
        }else {

            verPacket.get(4).add(S0_CO);
        }

        //计算S1的CO值
        if (CC0_S0 != 0 && CC0_A1 != 0 && CC1_A3 != 0) {
            cc_S1.add(CC0_S0 + CC0_A1 + CC1_A3);
        }
        if (CC0_S0 != 0 && CC1_A1 != 0 && CC0_A3 != 0) {
            cc_S1.add(CC0_S0 + CC1_A1 + CC0_A3);
        }
        if (CC1_S0 != 0 && CC0_A2 != 0 && CC1_A4 != 0) {
            cc_S1.add(CC1_S0 + CC0_A2 + CC1_A4);
        }
        if (CC1_S0 != 0 && CC1_A2 != 0 && CC0_A4 != 0) {
            cc_S1.add(CC1_S0 + CC1_A2 + CC0_A4);
        }

        verPacket.add(new ArrayList<>());
        verPacket.get(5).add((int)gatePacket.get(indexS1).get(0));
        int S1_CO = (int)gatePacket.get(indexS1).get(2);
        if (cc_S1.size() != 0) {
            CO = out_CO + Collections.min(cc_S1) + 1;
            if (S1_CO != -1) {
                CO = CO < S1_CO ? CO : S1_CO;
            }
            verPacket.get(5).add(CO);
        }else {

            verPacket.get(5).add(S1_CO);
        }
    }


    private void MUX21forCO(List<List<Object>> gatePacket) {

        int indexIN1 = -1;
        int indexIN2 = -1;
        int indexS = -1;

        for (int i = 1; i < gatePacket.size(); i++) {
            if (gatePacket.get(i).get(1).equals("IN1")) {
                indexIN1 = i;
            }else if (gatePacket.get(i).get(1).equals("IN2")) {
                indexIN2 = i;
            }else {
                indexS = i;
            }
        }

        int CO;
        int out_CO = (int)gatePacket.get(0).get(1);

        int in1cc0 = (int)gatePacket.get(indexIN1).get(3);
        int in1cc1 = (int)gatePacket.get(indexIN1).get(4);
        int in1_CO = (int)gatePacket.get(indexIN1).get(2);

        int in2cc0 = (int)gatePacket.get(indexIN2).get(3);
        int in2cc1 = (int)gatePacket.get(indexIN2).get(4);
        int in2_CO = (int)gatePacket.get(indexIN2).get(2);

        int scc0 = (int)gatePacket.get(indexS).get(3);
        int scc1 = (int)gatePacket.get(indexS).get(4);
        int s_CO = (int)gatePacket.get(indexS).get(2);

        //计算IN1的CO值
        verPacket.add(new ArrayList<>());
        verPacket.get(0).add((int)gatePacket.get(indexIN1).get(0));
        if (scc0 != 0) {
            CO = out_CO + scc0 + 1;
            if (in1_CO != -1) {
                CO = CO < in1_CO ? CO : in1_CO;
            }
            verPacket.get(0).add(CO);
        }else {

            verPacket.get(0).add(in1_CO);
        }

        //计算IN2的值
        verPacket.add(new ArrayList<>());
        verPacket.get(1).add((int)gatePacket.get(indexIN2).get(0));
        if (scc1 != 0) {
            CO = out_CO + scc1 + 1;
            if (in2_CO != -1) {
                CO = CO < in2_CO ? CO : in2_CO;
            }
            verPacket.get(1).add(CO);
        }else {

            verPacket.get(1).add(in2_CO);
        }

        //计算SO的CO值
        List<Integer> cc = new ArrayList<>();
        if (in1cc0 != 0 && in2cc1 != 0) {
            cc.add( in1cc0 + in2cc1 );
        }
        if (in1cc1 != 0 && in2cc0 != 0) {
            cc.add( in1cc1 + in2cc0 );
        }
        verPacket.add(new ArrayList<>());
        verPacket.get(2).add((int)gatePacket.get(indexS).get(0));
        if (cc.size() != 0) {
            CO = out_CO + Collections.min(cc) + 1;
            if (s_CO != -1) {
                CO = CO < s_CO ? CO : s_CO;
            }
            verPacket.get(2).add(CO);
        }else {

            verPacket.get(2).add(s_CO);
        }
    }

    /*
     * 	六输入或与非门
     */
    private void OAI222forCO(List<List<Object>> gatePacket) {

        OA222forCO(gatePacket);

    }

    /*
     * 	五输入或与非门
     */
    private void OAI221forCO(List<List<Object>> gatePacket) {

        OA221forCO(gatePacket);

    }

    /*
     * 	四输入或与非门
     */
    private void OAI22forCO(List<List<Object>> gatePacket) {

        OA22forCO(gatePacket);

    }

    /*
     * 	三输入或与非门
     */
    private void OAI21forCO(List<List<Object>> gatePacket) {

        OA21forCO(gatePacket);

    }

    private void OA222forCO(List<List<Object>> gatePacket) {

        int CO;
        int indexIN1 = -1;
        int indexIN2 = -1;
        int indexIN3 = -1;
        int indexIN4 = -1;
        int indexIN5 = -1;
        int indexIN6 = -1;

        int out_CO = (int)gatePacket.get(0).get(1);

        for (int i = 1; i < gatePacket.size(); i++) {
            if (gatePacket.get(i).get(1).equals("IN1")) {
                indexIN1 = i;
            }else if (gatePacket.get(i).get(1).equals("IN2")) {
                indexIN2 = i;
            }else if (gatePacket.get(i).get(1).equals("IN3")) {
                indexIN3 = i;
            }else if (gatePacket.get(i).get(1).equals("IN4")) {
                indexIN4 = i;
            }else if (gatePacket.get(i).get(1).equals("IN5")) {
                indexIN5 = i;
            }else {
                indexIN6 = i;
            }
        }

        List<Integer> cc_12 = new ArrayList<>();
        List<Integer> cc_34 = new ArrayList<>();
        List<Integer> cc_56 = new ArrayList<>();

        int in1cc0 = (int)gatePacket.get(indexIN1).get(3);
        int in1cc1 = (int)gatePacket.get(indexIN1).get(4);
        int in1_CO = (int)gatePacket.get(indexIN1).get(2);

        int in2cc0 = (int)gatePacket.get(indexIN2).get(3);
        int in2cc1 = (int)gatePacket.get(indexIN2).get(4);
        int in2_CO = (int)gatePacket.get(indexIN2).get(2);

        int in3cc0 = (int)gatePacket.get(indexIN3).get(3);
        int in3cc1 = (int)gatePacket.get(indexIN3).get(4);
        int in3_CO = (int)gatePacket.get(indexIN3).get(2);

        int in4cc0 = (int)gatePacket.get(indexIN4).get(3);
        int in4cc1 = (int)gatePacket.get(indexIN4).get(4);
        int in4_CO = (int)gatePacket.get(indexIN4).get(2);

        int in5cc0 = (int)gatePacket.get(indexIN5).get(3);
        int in5cc1 = (int)gatePacket.get(indexIN5).get(4);
        int in5_CO = (int)gatePacket.get(indexIN5).get(2);

        int in6cc0 = (int)gatePacket.get(indexIN6).get(3);
        int in6cc1 = (int)gatePacket.get(indexIN6).get(4);
        int in6_CO = (int)gatePacket.get(indexIN6).get(2);

        if (in1cc1 != 0) {
            cc_12.add(in1cc1);
        }
        if (in2cc1 != 0) {
            cc_12.add(in2cc1);
        }

        if (in3cc1 != 0) {
            cc_34.add(in3cc1);
        }
        if (in4cc1 != 0) {
            cc_34.add(in4cc1);
        }

        if (in5cc1 != 0) {
            cc_56.add(in5cc1);
        }
        if (in6cc1 != 0) {
            cc_56.add(in6cc1);
        }

        //计算IN1的CO值
        verPacket.add(new ArrayList<>());
        verPacket.get(0).add((int)gatePacket.get(indexIN1).get(0));
        if (in2cc0 != 0 && cc_34.size() != 0 && cc_56.size() != 0) {
            CO = out_CO + in2cc0 + Collections.min(cc_34) + Collections.min(cc_56) + 1;
            if (in1_CO != -1) {
                CO = CO < in1_CO ? CO : in1_CO;
            }
            verPacket.get(0).add(CO);
        }else {

            verPacket.get(0).add(in1_CO);
        }

        //计算IN2的CO值
        verPacket.add(new ArrayList<>());
        verPacket.get(1).add((int)gatePacket.get(indexIN2).get(0));
        if (in1cc0 != 0 && cc_34.size() != 0 && cc_56.size() != 0) {
            CO = out_CO + in1cc0 + Collections.min(cc_34) + Collections.min(cc_56) + 1;
            if (in2_CO != -1) {
                CO = CO < in2_CO ? CO : in2_CO;
            }
            verPacket.get(1).add(CO);
        }else {

            verPacket.get(1).add(in2_CO);
        }

        //计算IN3的CO值
        verPacket.add(new ArrayList<>());
        verPacket.get(2).add((int)gatePacket.get(indexIN3).get(0));
        if (in4cc0 != 0 && cc_12.size() != 0 && cc_56.size() != 0) {
            CO = out_CO + in4cc0 + Collections.min(cc_12) + Collections.min(cc_56) + 1;
            if (in3_CO != -1) {
                CO = CO < in3_CO ? CO : in3_CO;
            }
            verPacket.get(2).add(CO);
        }else {

            verPacket.get(2).add(in3_CO);
        }

        //计算IN4的CO值
        verPacket.add(new ArrayList<>());
        verPacket.get(3).add((int)gatePacket.get(indexIN4).get(0));
        if (in3cc0 != 0 && cc_12.size() != 0 && cc_56.size() != 0) {
            CO = out_CO + in3cc0 + Collections.min(cc_12) + Collections.min(cc_56) + 1;
            if (in4_CO != -1) {
                CO = CO < in4_CO ? CO : in4_CO;
            }
            verPacket.get(3).add(CO);
        }else {

            verPacket.get(3).add(in4_CO);
        }

        //计算IN5的CO值
        verPacket.add(new ArrayList<>());
        verPacket.get(4).add((int)gatePacket.get(indexIN5).get(0));
        if (in6cc0 != 0 && cc_12.size() != 0 && cc_34.size() != 0) {
            CO = out_CO + in6cc0 + Collections.min(cc_12) + Collections.min(cc_34) + 1;
            if (in5_CO != -1) {
                CO = CO < in5_CO ? CO : in5_CO;
            }
            verPacket.get(4).add(CO);
        }else {

            verPacket.get(4).add(in5_CO);
        }

        //计算IN6的CO值
        verPacket.add(new ArrayList<>());
        verPacket.get(5).add((int)gatePacket.get(indexIN6).get(0));
        if (in5cc0 != 0 && cc_12.size() != 0 && cc_34.size() != 0) {
            CO = out_CO + in5cc0 + Collections.min(cc_12) + Collections.min(cc_34) + 1;
            if (in6_CO != -1) {
                CO = CO < in6_CO ? CO : in6_CO;
            }
            verPacket.get(5).add(CO);
        }else {

            verPacket.get(5).add(in6_CO);
        }
    }

    private void OA221forCO(List<List<Object>> gatePacket) {

        int CO;
        int indexIN1 = -1;
        int indexIN2 = -1;
        int indexIN3 = -1;
        int indexIN4 = -1;
        int indexIN5 = -1;

        int out_CO = (int)gatePacket.get(0).get(1);

        for (int i = 1; i < gatePacket.size(); i++) {
            if (gatePacket.get(i).get(1).equals("IN1")) {
                indexIN1 = i;
            }else if (gatePacket.get(i).get(1).equals("IN2")) {
                indexIN2 = i;
            }else if (gatePacket.get(i).get(1).equals("IN3")) {
                indexIN3 = i;
            }else if (gatePacket.get(i).get(1).equals("IN4")) {
                indexIN4 = i;
            }else {
                indexIN5 = i;
            }
        }
        List<Integer> cc_12 = new ArrayList<>();
        List<Integer> cc_34 = new ArrayList<>();

        int in1cc0 = (int)gatePacket.get(indexIN1).get(3);
        int in1cc1 = (int)gatePacket.get(indexIN1).get(4);
        int in1_CO = (int)gatePacket.get(indexIN1).get(2);

        int in2cc0 = (int)gatePacket.get(indexIN2).get(3);
        int in2cc1 = (int)gatePacket.get(indexIN2).get(4);
        int in2_CO = (int)gatePacket.get(indexIN2).get(2);

        int in3cc0 = (int)gatePacket.get(indexIN3).get(3);
        int in3cc1 = (int)gatePacket.get(indexIN3).get(4);
        int in3_CO = (int)gatePacket.get(indexIN3).get(2);

        int in4cc0 = (int)gatePacket.get(indexIN4).get(3);
        int in4cc1 = (int)gatePacket.get(indexIN4).get(4);
        int in4_CO = (int)gatePacket.get(indexIN4).get(2);

        int in5cc1 = (int)gatePacket.get(indexIN5).get(4);
        int in5_CO = (int)gatePacket.get(indexIN5).get(2);

        if (in1cc1 != 0) {
            cc_12.add(in1cc1);
        }
        if (in2cc1 != 0) {
            cc_12.add(in2cc1);
        }

        if (in3cc1 != 0) {
            cc_34.add(in3cc1);
        }
        if (in4cc1 != 0) {
            cc_34.add(in4cc1);
        }

        //计算IN1的CO值
        verPacket.add(new ArrayList<>());
        verPacket.get(0).add((int)gatePacket.get(indexIN1).get(0));
        if (in2cc0 != 0 && cc_34.size() != 0 && in5cc1 != 0) {
            CO = out_CO + in2cc0 + Collections.min(cc_34) + in5cc1 + 1;
            if (in1_CO != -1) {
                CO = CO < in1_CO ? CO : in1_CO;
            }
            verPacket.get(0).add(CO);
        }else {

            verPacket.get(0).add(in1_CO);
        }

        //计算IN2的CO值
        verPacket.add(new ArrayList<>());
        verPacket.get(1).add((int)gatePacket.get(indexIN2).get(0));
        if (in1cc0 != 0 && cc_34.size() != 0 && in5cc1 != 0) {
            CO = out_CO + in1cc0 + Collections.min(cc_34) + in5cc1 + 1;
            if (in2_CO != -1) {
                CO = CO < in2_CO ? CO : in2_CO;
            }
            verPacket.get(1).add(CO);
        }else {

            verPacket.get(1).add(in2_CO);
        }

        //计算IN3的CO值
        verPacket.add(new ArrayList<>());
        verPacket.get(2).add((int)gatePacket.get(indexIN3).get(0));
        if (in4cc0 != 0 && cc_12.size() != 0 && in5cc1 != 0) {
            CO = out_CO + in4cc0 + Collections.min(cc_12) + in5cc1 + 1;
            if (in3_CO != -1) {
                CO = CO < in3_CO ? CO : in3_CO;
            }
            verPacket.get(2).add(CO);
        }else {

            verPacket.get(2).add(in3_CO);
        }

        //计算IN4的CO值
        verPacket.add(new ArrayList<>());
        verPacket.get(3).add((int)gatePacket.get(indexIN4).get(0));
        if (in3cc0 != 0 && cc_12.size() != 0 && in5cc1 != 0) {
            CO = out_CO + in3cc0 + Collections.min(cc_12) + in5cc1 + 1;
            if (in4_CO != -1) {
                CO = CO < in4_CO ? CO : in4_CO;
            }
            verPacket.get(3).add(CO);
        }else {

            verPacket.get(3).add(in4_CO);
        }

        //计算IN5的CO值
        verPacket.add(new ArrayList<>());
        verPacket.get(4).add((int)gatePacket.get(indexIN5).get(0));
        if (cc_12.size() != 0 && cc_34.size() != 0) {
            CO = out_CO + Collections.min(cc_12) + Collections.min(cc_34) + 1;
            if (in5_CO != -1) {
                CO = CO < in5_CO ? CO : in5_CO;
            }
            verPacket.get(4).add(CO);
        }else {

            verPacket.get(4).add(in5_CO);
        }
    }

    private void OA22forCO(List<List<Object>> gatePacket) {

        int CO;
        int indexIN1 = -1;
        int indexIN2 = -1;
        int indexIN3 = -1;
        int indexIN4 = -1;

        int out_CO = (int)gatePacket.get(0).get(1);

        for (int i = 1; i < gatePacket.size(); i++) {
            if (gatePacket.get(i).get(1).equals("IN1")) {
                indexIN1 = i;
            }else if (gatePacket.get(i).get(1).equals("IN2")) {
                indexIN2 = i;
            }else if (gatePacket.get(i).get(1).equals("IN3")) {
                indexIN3 = i;
            }else {
                indexIN4 = i;
            }
        }

        List<Integer> cc_12 = new ArrayList<>();
        List<Integer> cc_34 = new ArrayList<>();

        int in1cc0 = (int)gatePacket.get(indexIN1).get(3);
        int in1cc1 = (int)gatePacket.get(indexIN1).get(4);
        int in1_CO = (int)gatePacket.get(indexIN1).get(2);

        int in2cc0 = (int)gatePacket.get(indexIN2).get(3);
        int in2cc1 = (int)gatePacket.get(indexIN2).get(4);
        int in2_CO = (int)gatePacket.get(indexIN2).get(2);

        int in3cc0 = (int)gatePacket.get(indexIN3).get(3);
        int in3cc1 = (int)gatePacket.get(indexIN3).get(4);
        int in3_CO = (int)gatePacket.get(indexIN3).get(2);

        int in4cc0 = (int)gatePacket.get(indexIN4).get(3);
        int in4cc1 = (int)gatePacket.get(indexIN4).get(4);
        int in4_CO = (int)gatePacket.get(indexIN4).get(2);

        if (in1cc1 != 0) {
            cc_12.add(in1cc1);
        }
        if (in2cc1 != 0) {
            cc_12.add(in2cc1);
        }

        if (in3cc1 != 0) {
            cc_34.add(in3cc1);
        }
        if (in4cc1 != 0) {
            cc_34.add(in4cc1);
        }

        //计算IN1的CO值
        verPacket.add(new ArrayList<>());
        verPacket.get(0).add((int)gatePacket.get(indexIN1).get(0));
        if (in2cc0 != 0 && cc_34.size() != 0) {
            CO = out_CO + in2cc0 + Collections.min(cc_34) + 1;
            if (in1_CO != -1) {
                CO = CO < in1_CO ? CO : in1_CO;
            }
            verPacket.get(0).add(CO);
        }else {
            verPacket.get(0).add(in1_CO);
        }

        //计算IN2的CO值
        verPacket.add(new ArrayList<>());
        verPacket.get(1).add((int)gatePacket.get(indexIN2).get(0));
        if (in1cc0 != 0 && cc_34.size() != 0) {
            CO = out_CO + in1cc0 + Collections.min(cc_34) + 1;
            if (in2_CO != -1) {
                CO = CO < in2_CO ? CO : in2_CO;
            }
            verPacket.get(1).add(CO);
        }else {
            verPacket.get(1).add(in2_CO);
        }

        //计算IN3的CO值
        verPacket.add(new ArrayList<>());
        verPacket.get(2).add((int)gatePacket.get(indexIN3).get(0));
        if (in4cc0 != 0 && cc_12.size() != 0) {
            CO = out_CO + in4cc0 + Collections.min(cc_12) + 1;
            if (in3_CO != -1) {
                CO = CO < in3_CO ? CO : in3_CO;
            }
            verPacket.get(2).add(CO);
        }else {
            verPacket.get(2).add(in3_CO);
        }

        //计算IN4的CO值
        verPacket.add(new ArrayList<>());
        verPacket.get(3).add((int)gatePacket.get(indexIN4).get(0));
        if (in3cc0 != 0 && cc_12.size() != 0) {
            CO = out_CO + in3cc0 + Collections.min(cc_12) + 1;
            if (in4_CO != -1) {
                CO = CO < in4_CO ? CO : in4_CO;
            }
            verPacket.get(3).add(CO);
        }else {
            verPacket.get(3).add(in4_CO);
        }
    }

    private void OA21forCO(List<List<Object>> gatePacket) {

        int indexIN1 = -1;
        int indexIN2 = -1;
        int indexIN3 = -1;
        int CO;
        int out_CO = (int)gatePacket.get(0).get(1);

        for (int i = 1; i < gatePacket.size(); i++) {
            if (gatePacket.get(i).get(1).equals("IN1")) {
                indexIN1 = i;
            }else if (gatePacket.get(i).get(1).equals("IN2")) {
                indexIN2 = i;
            }else {
                indexIN3 = i;
            }
        }

        int in3cc1 = (int)gatePacket.get(indexIN3).get(4);
        int in2cc0 = (int)gatePacket.get(indexIN2).get(3);
        int in1cc0 = (int)gatePacket.get(indexIN1).get(3);
        int in1cc1 = (int)gatePacket.get(indexIN1).get(4);
        int in2cc1 = (int)gatePacket.get(indexIN2).get(4);

        //计算A1的CO值
        verPacket.add(new ArrayList<>());
        verPacket.get(0).add((int)gatePacket.get(indexIN1).get(0));
        int in1_CO = (int)gatePacket.get(indexIN1).get(2);

        if (in3cc1 != 0 && in2cc0 != 0) {
            CO = out_CO + in2cc0 + in3cc1 + 1;
            if (in1_CO != -1) {
                CO = CO < in1_CO ? CO : in1_CO;
            }
            verPacket.get(0).add(CO);
        }else {
            verPacket.get(0).add(in1_CO);
        }

        //计算A2的CO值
        verPacket.add(new ArrayList<>());
        verPacket.get(1).add((int)gatePacket.get(indexIN2).get(0));
        int in2_CO = (int)gatePacket.get(indexIN2).get(2);

        if (in3cc1 != 0 && in1cc0 != 0) {
            CO = out_CO + in1cc0 + in3cc1 + 1;
            if (in2_CO != -1) {
                CO = CO < in2_CO ? CO : in2_CO;
            }
            verPacket.get(1).add(CO);
        }else {

            verPacket.get(1).add(in2_CO);
        }

        //计算IN3的CO值
        verPacket.add(new ArrayList<>());
        verPacket.get(2).add((int)gatePacket.get(indexIN3).get(0));
        int in3_CO = (int)gatePacket.get(indexIN3).get(2);
        List<Integer> cc = new ArrayList<>();

        if (in1cc1 != 0) {
            cc.add(in1cc1);
        }
        if (in2cc1 != 0) {
            cc.add(in2cc1);
        }
        if (cc.size() != 0) {
            CO = out_CO + Collections.min(cc) + 1;
            if (in3_CO != -1) {
                CO = CO < in3_CO ? CO : in3_CO;
            }
            verPacket.get(2).add(CO);
        }else {

            verPacket.get(2).add(in3_CO);
        }
    }

    /*
     * 	六输入与或非门
     */
    private void AOI222forCO(List<List<Object>> gatePacket) {

        AO222forCO(gatePacket);

    }


    /*
     * 	五输入与或非门
     */
    private void AOI221forCO(List<List<Object>> gatePacket) {

        AO221forCO(gatePacket);

    }

    /*
     * 	四输入与或非门
     *
     */
    private void AOI22forCO(List<List<Object>> gatePacket) {

        AO22forCO(gatePacket);

    }

    /*
     * 	三输入与或非门
     */
    private void AOI21forCO(List<List<Object>> gatePacket) {

        AO21forCO(gatePacket);

    }

    private void AO222forCO(List<List<Object>> gatePacket) {

        int CO;
        int indexIN1 = -1;
        int indexIN2 = -1;
        int indexIN3 = -1;
        int indexIN4 = -1;
        int indexIN5 = -1;
        int indexIN6 = -1;

        int out_CO = (int)gatePacket.get(0).get(1);

        for (int i = 1; i < gatePacket.size(); i++) {
            if (gatePacket.get(i).get(1).equals("IN1")) {
                indexIN1 = i;
            }else if (gatePacket.get(i).get(1).equals("IN2")) {
                indexIN2 = i;
            }else if (gatePacket.get(i).get(1).equals("IN3")) {
                indexIN3 = i;
            }else if (gatePacket.get(i).get(1).equals("IN4")) {
                indexIN4 = i;
            }else if (gatePacket.get(i).get(1).equals("IN5")) {
                indexIN5 = i;
            }else {
                indexIN6 = i;
            }
        }

        List<Integer> cc_12 = new ArrayList<>();
        List<Integer> cc_34 = new ArrayList<>();
        List<Integer> cc_56 = new ArrayList<>();

        int in1cc0 = (int)gatePacket.get(indexIN1).get(3);
        int in2cc0 = (int)gatePacket.get(indexIN2).get(3);
        int in1cc1 = (int)gatePacket.get(indexIN1).get(4);
        int in2cc1 = (int)gatePacket.get(indexIN2).get(4);

        int in3cc0 = (int)gatePacket.get(indexIN3).get(3);
        int in3cc1 = (int)gatePacket.get(indexIN3).get(4);
        int in4cc0 = (int)gatePacket.get(indexIN4).get(3);
        int in4cc1 = (int)gatePacket.get(indexIN4).get(4);

        int in5cc0 = (int)gatePacket.get(indexIN5).get(3);
        int in5cc1 = (int)gatePacket.get(indexIN5).get(4);
        int in6cc0 = (int)gatePacket.get(indexIN6).get(3);
        int in6cc1 = (int)gatePacket.get(indexIN6).get(4);

        if (in5cc0 != 0) {
            cc_56.add(in5cc0);
        }
        if (in6cc0 != 0) {
            cc_56.add(in6cc0);
        }

        //计算IN1和IN2的CO值
        if (in3cc0 != 0) {
            cc_34.add(in3cc0);
        }
        if (in4cc0 != 0) {
            cc_34.add(in4cc0);
        }

        verPacket.add(new ArrayList<>());
        verPacket.get(0).add((int)gatePacket.get(indexIN1).get(0));
        int in1_CO = (int)gatePacket.get(indexIN1).get(2);
        if (cc_34.size() != 0 && in2cc1 != 0 && cc_56.size() != 0) {
            CO = out_CO + in2cc1 + Collections.min(cc_34) + Collections.min(cc_56) + 1;
            if (in1_CO != -1) {
                CO = CO < in1_CO ? CO : in1_CO;
            }
            verPacket.get(0).add(CO);
        }else {

            verPacket.get(0).add(in1_CO);
        }

        verPacket.add(new ArrayList<>());
        verPacket.get(1).add((int)gatePacket.get(indexIN2).get(0));
        int in2_CO = (int)gatePacket.get(indexIN2).get(2);
        if (cc_34.size() != 0 && in1cc1 != 0 && cc_56.size() != 0) {
            CO = out_CO + in1cc1 + Collections.min(cc_34) + Collections.min(cc_56) + 1;
            if (in2_CO != -1) {
                CO = CO < in2_CO ? CO : in2_CO;
            }
            verPacket.get(1).add(CO);
        }else {

            verPacket.get(1).add(in2_CO);
        }

        //计算IN3和IN4的CO值
        if (in1cc0 != 0) {
            cc_12.add(in1cc0);
        }
        if (in2cc0 != 0) {
            cc_12.add(in2cc0);
        }

        verPacket.add(new ArrayList<>());
        verPacket.get(2).add((int)gatePacket.get(indexIN3).get(0));
        int in3_CO = (int)gatePacket.get(indexIN3).get(2);
        if (cc_12.size() != 0 && in4cc1 != 0 && cc_56.size() != 0) {
            CO = out_CO + in4cc1 + Collections.min(cc_12) + Collections.min(cc_56) + 1;
            if (in3_CO != -1) {
                CO = CO < in3_CO ? CO : in3_CO;
            }
            verPacket.get(2).add(CO);
        }else {

            verPacket.get(2).add(in3_CO);
        }

        verPacket.add(new ArrayList<>());
        verPacket.get(3).add((int)gatePacket.get(indexIN4).get(0));
        int in4_CO = (int)gatePacket.get(indexIN4).get(2);
        if (cc_12.size() != 0 && in3cc1 != 0 && cc_56.size() != 0) {
            CO = out_CO + in3cc1 + Collections.min(cc_12) + Collections.min(cc_56) + 1;
            if (in4_CO != -1) {
                CO = CO < in4_CO ? CO : in4_CO;
            }
            verPacket.get(3).add(CO);
        }else {

            verPacket.get(3).add(in4_CO);
        }

        verPacket.add(new ArrayList<>());
        verPacket.get(4).add((int)gatePacket.get(indexIN5).get(0));
        int in5_CO = (int)gatePacket.get(indexIN5).get(2);
        if (cc_12.size() != 0 && cc_34.size() != 0 && in6cc1 != 0) {
            CO = out_CO + in6cc1 + Collections.min(cc_12) + Collections.min(cc_34) + 1;
            if (in5_CO != -1) {
                CO = CO < in5_CO ? CO : in5_CO;
            }
            verPacket.get(4).add(CO);
        }else {

            verPacket.get(4).add(in5_CO);
        }

        verPacket.add(new ArrayList<>());
        verPacket.get(5).add((int)gatePacket.get(indexIN6).get(0));
        int in6_CO = (int)gatePacket.get(indexIN6).get(2);
        if (cc_12.size() != 0 && cc_34.size() != 0 && in5cc1 != 0) {
            CO = out_CO + in5cc1 + Collections.min(cc_12) + Collections.min(cc_34) + 1;
            if (in6_CO != -1) {
                CO = CO < in6_CO ? CO : in6_CO;
            }
            verPacket.get(5).add(CO);
        }else {

            verPacket.get(5).add(in6_CO);
        }
    }

    private void AO221forCO(List<List<Object>> gatePacket) {

        int CO;
        int indexIN1 = -1;
        int indexIN2 = -1;
        int indexIN3 = -1;
        int indexIN4 = -1;
        int indexIN5 = -1;

        int out_CO = (int)gatePacket.get(0).get(1);

        for (int i = 1; i < gatePacket.size(); i++) {
            if (gatePacket.get(i).get(1).equals("IN1")) {
                indexIN1 = i;
            }else if (gatePacket.get(i).get(1).equals("IN2")) {
                indexIN2 = i;
            }else if (gatePacket.get(i).get(1).equals("IN3")) {
                indexIN3 = i;
            }else if (gatePacket.get(i).get(1).equals("IN4")) {
                indexIN4 = i;
            }else {
                indexIN5 = i;
            }
        }

        List<Integer> cc_12 = new ArrayList<>();
        List<Integer> cc_34 = new ArrayList<>();

        int in1cc0 = (int)gatePacket.get(indexIN1).get(3);
        int in2cc0 = (int)gatePacket.get(indexIN2).get(3);
        int in1cc1 = (int)gatePacket.get(indexIN1).get(4);
        int in2cc1 = (int)gatePacket.get(indexIN2).get(4);

        int in3cc0 = (int)gatePacket.get(indexIN3).get(3);
        int in3cc1 = (int)gatePacket.get(indexIN3).get(4);

        int in4cc0 = (int)gatePacket.get(indexIN4).get(3);
        int in4cc1 = (int)gatePacket.get(indexIN4).get(4);

        int in5cc0 = (int)gatePacket.get(indexIN5).get(3);

        //计算IN1和IN2的CO值
        if (in3cc0 != 0) {
            cc_34.add(in3cc0);
        }
        if (in4cc0 != 0) {
            cc_34.add(in4cc0);
        }

        verPacket.add(new ArrayList<>());
        verPacket.get(0).add((int)gatePacket.get(indexIN1).get(0));
        int in1_CO = (int)gatePacket.get(indexIN1).get(2);
        if (cc_34.size() != 0 && in2cc1 != 0 && in5cc0 != 0) {
            CO = out_CO + in2cc1 + Collections.min(cc_34) + in5cc0 + 1;
            if (in1_CO != -1) {
                CO = CO < in1_CO ? CO : in1_CO;
            }
            verPacket.get(0).add(CO);
        }else {
            verPacket.get(0).add(in1_CO);
        }

        verPacket.add(new ArrayList<>());
        verPacket.get(1).add((int)gatePacket.get(indexIN2).get(0));
        int in2_CO = (int)gatePacket.get(indexIN2).get(2);
        if (cc_34.size() != 0 && in1cc1 != 0 && in5cc0 != 0) {
            CO = out_CO + in1cc1 + Collections.min(cc_34) + in5cc0 + 1;
            if (in2_CO != -1) {
                CO = CO < in2_CO ? CO : in2_CO;
            }
            verPacket.get(1).add(CO);
        }else {

            verPacket.get(1).add(in2_CO);
        }

        //计算IN3和IN4的CO值
        if (in1cc0 != 0) {
            cc_12.add(in1cc0);
        }
        if (in2cc0 != 0) {
            cc_12.add(in2cc0);
        }

        verPacket.add(new ArrayList<>());
        verPacket.get(2).add((int)gatePacket.get(indexIN3).get(0));
        int in3_CO = (int)gatePacket.get(indexIN3).get(2);
        if (cc_12.size() != 0 && in4cc1 != 0 && in5cc0 != 0) {
            CO = out_CO + in4cc1 + Collections.min(cc_12) + in5cc0 + 1;
            if (in3_CO != -1) {
                CO = CO < in3_CO ? CO : in3_CO;
            }
            verPacket.get(2).add(CO);
        }else {

            verPacket.get(2).add(in3_CO);
        }

        verPacket.add(new ArrayList<>());
        verPacket.get(3).add((int)gatePacket.get(indexIN4).get(0));
        int in4_CO = (int)gatePacket.get(indexIN4).get(2);
        if (cc_12.size() != 0 && in3cc1 != 0 && in5cc0 != 0) {
            CO = out_CO + in3cc1 + Collections.min(cc_12) + in5cc0 + 1;
            if (in4_CO != -1) {
                CO = CO < in4_CO ? CO : in4_CO;
            }
            verPacket.get(3).add(CO);
        }else {

            verPacket.get(3).add(in4_CO);
        }

        //计算IN5的CO值
        verPacket.add(new ArrayList<>());
        verPacket.get(4).add((int)gatePacket.get(indexIN5).get(0));
        int in5_CO = (int)gatePacket.get(indexIN5).get(2);
        if (cc_12.size() != 0 && cc_34.size() != 0) {
            CO = out_CO + Collections.min(cc_12) + Collections.min(cc_34) + 1;
            if (in5_CO != -1) {
                CO = CO < in5_CO ? CO : in5_CO;
            }
            verPacket.get(4).add(CO);
        }else {

            verPacket.get(4).add(in5_CO);
        }
    }

    private void AO22forCO(List<List<Object>> gatePacket) {

        int CO;
        int indexIN1 = -1;
        int indexIN2 = -1;
        int indexIN3 = -1;
        int indexIN4 = -1;

        int out_CO = (int)gatePacket.get(0).get(1);

        for (int i = 1; i < gatePacket.size(); i++) {
            if (gatePacket.get(i).get(1).equals("IN1")) {
                indexIN1 = i;
            }else if (gatePacket.get(i).get(1).equals("IN2")) {
                indexIN2 = i;
            }else if (gatePacket.get(i).get(1).equals("IN3")) {
                indexIN3 = i;
            }else {
                indexIN4 = i;
            }
        }

        List<Integer> cc_12 = new ArrayList<>();
        List<Integer> cc_34 = new ArrayList<>();

        int in1cc0 = (int)gatePacket.get(indexIN1).get(3);
        int in2cc0 = (int)gatePacket.get(indexIN2).get(3);
        int in1cc1 = (int)gatePacket.get(indexIN1).get(4);
        int in2cc1 = (int)gatePacket.get(indexIN2).get(4);

        int in1_CO = (int)gatePacket.get(indexIN1).get(2);
        int in2_CO = (int)gatePacket.get(indexIN2).get(2);

        int in3cc0 = (int)gatePacket.get(indexIN3).get(3);
        int in3cc1 = (int)gatePacket.get(indexIN3).get(4);
        int in4cc0 = (int)gatePacket.get(indexIN4).get(3);
        int in4cc1 = (int)gatePacket.get(indexIN4).get(4);

        int in3_CO = (int)gatePacket.get(indexIN3).get(2);
        int in4_CO = (int)gatePacket.get(indexIN4).get(2);

        //计算IN1和IN2的CO值
        if (in3cc0 != 0) {
            cc_34.add(in3cc0);
        }
        if (in4cc0 != 0) {
            cc_34.add(in4cc0);
        }

        verPacket.add(new ArrayList<>());
        verPacket.get(0).add((int)gatePacket.get(indexIN1).get(0));
        if (cc_34.size() != 0 && in2cc1 != 0) {
            CO = out_CO + in2cc1 + Collections.min(cc_34) + 1;
            if (in1_CO != -1) {
                CO = CO < in1_CO ? CO : in1_CO;
            }
            verPacket.get(0).add(CO);
        }else {

            verPacket.get(0).add(in1_CO);
        }

        verPacket.add(new ArrayList<>());
        verPacket.get(1).add((int)gatePacket.get(indexIN2).get(0));
        if (cc_34.size() != 0 && in1cc1 != 0) {
            CO = out_CO + in1cc1 + Collections.min(cc_34) + 1;
            if (in2_CO != -1) {
                CO = CO < in2_CO ? CO : in2_CO;
            }
            verPacket.get(1).add(CO);
        }else {

            verPacket.get(1).add(in2_CO);
        }

        //计算IN3和IN4的CO值
        if (in1cc0 != 0) {
            cc_12.add(in1cc0);
        }
        if (in2cc0 != 0) {
            cc_12.add(in2cc0);
        }

        verPacket.add(new ArrayList<>());
        verPacket.get(2).add((int)gatePacket.get(indexIN3).get(0));
        if (cc_12.size() != 0 && in4cc1 != 0) {
            CO = out_CO + in4cc1 + Collections.min(cc_12) + 1;
            if (in3_CO != -1) {
                CO = CO < in3_CO ? CO : in3_CO;
            }
            verPacket.get(2).add(CO);
        }else {
            verPacket.get(2).add(in3_CO);
        }

        verPacket.add(new ArrayList<>());
        verPacket.get(3).add((int)gatePacket.get(indexIN4).get(0));
        if (cc_12.size() != 0 && in3cc1 != 0) {
            CO = out_CO + in3cc1 + Collections.min(cc_12) + 1;
            if (in4_CO != -1) {
                CO = CO < in4_CO ? CO : in4_CO;
            }
            verPacket.get(3).add(CO);
        }else {

            verPacket.get(3).add(in4_CO);
        }
    }

    private void AO21forCO(List<List<Object>> gatePacket) {

        int indexIN1 = -1;
        int indexIN2 = -1;
        int indexIN3 = -1;
        int CO;
        int out_CO = (int)gatePacket.get(0).get(1);

        for (int i = 1; i < gatePacket.size(); i++) {
            if (gatePacket.get(i).get(1).equals("IN1")) {
                indexIN1 = i;
            }else if (gatePacket.get(i).get(1).equals("IN2")) {
                indexIN2 = i;
            }else {
                indexIN3 = i;
            }
        }
        int in1_CC1 = (int)gatePacket.get(indexIN1).get(4);
        int in1_CC0 = (int)gatePacket.get(indexIN1).get(3);
        int in1_CO = (int)gatePacket.get(indexIN1).get(2);

        int in2_CC1 = (int)gatePacket.get(indexIN2).get(4);
        int in2_CC0 = (int)gatePacket.get(indexIN2).get(3);
        int in2_CO = (int)gatePacket.get(indexIN2).get(2);

        int in3_CC0 = (int)gatePacket.get(indexIN3).get(3);
        int in3_CO = (int)gatePacket.get(indexIN3).get(2);
        //计算IN1的CO值
        verPacket.add(new ArrayList<>());
        verPacket.get(0).add((int)gatePacket.get(indexIN1).get(0));

        if (in2_CC1 != 0 && in3_CC0 != 0) {
            CO = out_CO + in2_CC1 + in3_CC0 + 1;
            if (in1_CO != -1) {
                CO = CO < in1_CO ? CO : in1_CO;
            }
            verPacket.get(0).add(CO);
        }else {

            verPacket.get(0).add(in1_CO);

        }

        //计算IN2的CO值
        verPacket.add(new ArrayList<>());
        verPacket.get(1).add((int)gatePacket.get(indexIN2).get(0));

        if (in1_CC1 != 0 && in3_CC0 != 0) {
            CO = out_CO + in1_CC1 + in3_CC0 + 1;
            if (in2_CO != -1) {
                CO = CO < in2_CO ? CO : in2_CO;
            }
            verPacket.get(1).add(CO);
        }else {

            verPacket.get(1).add(in2_CO);

        }

        //计算IN3的CO值
        verPacket.add(new ArrayList<>());
        verPacket.get(2).add((int)gatePacket.get(indexIN3).get(0));
        List<Integer> cc0 = new ArrayList<>();

        if (in1_CC0 != 0) {
            cc0.add(in1_CC0);
        }
        if (in2_CC0 != 0) {
            cc0.add(in2_CC0);
        }
        if (cc0.size() != 0) {
            CO = out_CO + Collections.min(cc0) + 1;
            if (in3_CO != -1) {
                CO = CO < in3_CO ? CO : in3_CO;
            }
            verPacket.get(2).add(CO);
        }else {

            verPacket.get(2).add(in3_CO);
        }
    }

    private void XNORforCO(List<List<Object>> gatePacket) {

        XORforCO(gatePacket);

    }

    private void XORforCO(List<List<Object>> gatePacket) {

        int CO = -1;
        int gateLen = gatePacket.size();
        int out_CO = (int)gatePacket.get(0).get(1); // 输出节点的CO值

        for (int i = 1; i < gateLen; i++) {

            int count = 0;
            verPacket.add(new ArrayList<>());
            verPacket.get(i-1).add((int)gatePacket.get(i).get(0));
            int allowLen = 0;
            int in_CO = (int)gatePacket.get(i).get(2);

            for (int j = 1; j < gateLen; j++) {
                if (j != i) {
                    List<Integer> cc = new ArrayList<>();
                    int cc0 = (int)gatePacket.get(j).get(3);
                    int cc1 = (int)gatePacket.get(j).get(4);
                    if (cc0 != 0) {
                        cc.add(cc0);
                    }
                    if (cc1 != 0) {
                        cc.add(cc1);
                    }
                    if (cc.size() == 0) {
                        break;
                    }else {
                        allowLen++;
                        count += Collections.min(cc);
                    }
                }
            }

            if (allowLen == (gateLen - 2)) {
                CO = out_CO + count + 1;
                if (in_CO != -1) {
                    CO = CO < in_CO ? CO : in_CO;
                }
                verPacket.get(i-1).add(CO);
            }else {

                verPacket.get(i-1).add(in_CO);
            }
        }
    }

    private void NORforCO(List<List<Object>> gatePacket) {

        ORforCO(gatePacket);
    }

    private void ORforCO(List<List<Object>> gatePacket) {

        int CO = -1;
        int out_CO = (int)gatePacket.get(0).get(1); // 输出节点的CO值
        int gateLen = gatePacket.size();//逻辑门的大小

        for (int i = 1; i < gateLen; i++) {

            verPacket.add(new ArrayList<>());
            verPacket.get(i-1).add((int)gatePacket.get(i).get(0));// 记录了输入节点在顶点表中的索引号

            int in_CO = (int)gatePacket.get(i).get(2);
            int count = 0;
            int allowLen = 0;

            for (int j = 1; j < gateLen; j++) {
                if (j != i) {
                    int in_CC0 = (int)gatePacket.get(j).get(3);
                    if (in_CC0 == 0) {
                        break;
                    }
                    allowLen++;
                    count += in_CC0;
                }
            }

            if (allowLen == (gateLen - 2)) {
                CO = out_CO + count + 1;
                if (in_CO != -1) {
                    CO = CO < in_CO ? CO : in_CO;
                }
                verPacket.get(i-1).add(CO);
            }else {

                verPacket.get(i-1).add(in_CO);
            }
        }

    }

    private void NANDforCO(List<List<Object>> gatePacket) {

        ANDforCO(gatePacket);
    }

    private void ANDforCO(List<List<Object>> gatePacket) {

        int CO;
        int out_CO = (int)gatePacket.get(0).get(1);//输出节点的CO值
        int gateLen = gatePacket.size();

        for (int i = 1; i < gateLen; i++) {

            verPacket.add(new ArrayList<>());
            verPacket.get(i-1).add((int)gatePacket.get(i).get(0));
            int in_CO = (int)gatePacket.get(i).get(2); //当前的输入节点的CO值
            int count = 0;

            // 在计算和的同时，将可计算的输入节点的个数记录在allowLen
            // 当allowLen的长度小于gateLen - 2 的时候，表示该输入的可观察性值不可以由输出节点获得，只能吃老本
            int allowLen = 0;
            for (int j = 1; j < gateLen; j++) {
                if (j != i) {
                    int in_CC1 = (int)gatePacket.get(j).get(4);
                    if (in_CC1 == 0) {
                        break;
                    }
                    allowLen++;
                    count += in_CC1;
                }
            }

            if (allowLen == (gateLen - 2)) {
                CO = out_CO + count + 1;
                if (in_CO != -1) {
                    CO = CO < in_CO ? CO : in_CO;
                }
                verPacket.get(i-1).add(CO);
            }else {
                verPacket.get(i-1).add(in_CO);
            }
        }
    }

    private void TNBUFFforCO(List<List<Object>> gatePacket) {

        int CO = 0;
        int out_CO = (int)gatePacket.get(0).get(1);

        int indexEN = -1;
        int indexA = -1;

        //找到逻辑门输入的符号，并获取其对应的CO值
        for (int i = 1; i < gatePacket.size(); i++) {
            if (gatePacket.get(i).get(1).equals("EN")) {
                indexEN = i;
            }else {
                indexA = i;
            }
        }

        int EN_CO = (int)gatePacket.get(indexEN).get(2);
        int en_CC1 = (int)gatePacket.get(indexEN).get(4);
        int A_CO = (int)gatePacket.get(indexA).get(2);

        //计算EN的CO值
        verPacket.add(new ArrayList<>());
        verPacket.get(0).add((int)gatePacket.get(indexEN).get(0));

        CO = out_CO + 1;
        if (EN_CO != -1) {
            CO = CO < EN_CO ? CO : EN_CO;
        }
        verPacket.get(0).add(CO);


        //计算A的CO值
        verPacket.add(new ArrayList<>());
        verPacket.get(1).add((int)gatePacket.get(indexA).get(0));

        //判断EN节点的CC1可控性值为有效值
        if (en_CC1 != 0) {
            CO = out_CO + en_CC1 + 1;
            if (A_CO != -1) {
                CO = CO < A_CO ? CO : A_CO;
            }
            verPacket.get(1).add(CO);

        }else {
            verPacket.get(1).add(A_CO);
        }
    }

    private void NUBFFforCO(List<List<Object>> gatePacket) {

        INVforCO(gatePacket);

    }

    private void IBUFFforCO(List<List<Object>> gatePacket) {

        INVforCO(gatePacket);

    }

    private void INVforCO(List<List<Object>> gatePacket) {

        verPacket.add(new ArrayList<>());
        verPacket.get(0).add((int)gatePacket.get(1).get(0));//加入index

        int CO = (int)gatePacket.get(0).get(1) + 1; //当前节点的CO值
        int in_CO = (int)gatePacket.get(1).get(2); //输入节点的CO值
        if (in_CO != -1) {
            CO = in_CO > CO ? CO : in_CO;
        }
        verPacket.get(0).add(CO);

    }

    private void FADDforCO(List<List<Object>> gatePacket) {

        int out_CO = (int)gatePacket.get(0).get(1);
        int[] in_CO = new int[3];

        int[] CC0 = new int[3];
        int[] CC1 = new int[3];
        for (int i = 1; i < gatePacket.size(); i++) {
            verPacket.add(new ArrayList<>());
            verPacket.get(i-1).add((int)gatePacket.get(i).get(0));
            CC0[i-1] = (int)gatePacket.get(i).get(3);
            CC1[i-1] = (int)gatePacket.get(i).get(4);
            in_CO[i-1] = (int)gatePacket.get(i).get(2);

        }

        int[] CO = new int[3];
        for (int i = 0; i < CO.length; i++) {
            CO[i] = 0;
        }

        switch ((String)gatePacket.get(0).get(2)) {
            case "CO":
                //1
                List<Integer> cc1 = new ArrayList<>();
                if (CC0[1] != 0 && CC1[2] != 0) {
                    cc1.add(CC0[1] + CC1[2]);
                }
                if (CC0[2] != 0 && CC1[1] != 0) {
                    cc1.add(CC1[1] + CC0[2]);
                }
                if (cc1.size() != 0) {
                    CO[0] = out_CO + Collections.min(cc1) + 1;
                }

                //2
                List<Integer> cc2 = new ArrayList<>();
                if (CC0[0] != 0 && CC1[2] != 0) {
                    cc2.add(CC0[0] + CC1[2]);
                }
                if (CC1[0] != 0 && CC0[2] != 0) {
                    cc2.add(CC1[0] + CC0[2]);
                }
                if (cc2.size() != 0) {
                    CO[1] = out_CO + Collections.min(cc2) + 1;
                }

                //3
                List<Integer> cc3 = new ArrayList<>();
                if (CC0[0] != 0 && CC1[1] != 0) {
                    cc3.add(CC0[0] + CC1[1]);
                }
                if (CC1[0] != 0 && CC0[1] != 0) {
                    cc3.add(CC1[0] + CC0[1]);
                }
                if (cc3.size() != 0) {
                    CO[2] = out_CO + Collections.min(cc3) + 1;
                }
                break;

            case "S":
                for (int i = 0; i < CO.length; i++) {
                    int count = 0;
                    int allowLen = 0;
                    for (int j = 0; j < CO.length; j++) {
                        if (j != i) {
                            List<Integer> cc = new ArrayList<>();
                            if (CC0[j] != 0) {
                                cc.add(CC0[j]);
                            }
                            if (CC1[j] != 0) {
                                cc.add(CC1[j]);
                            }
                            if (cc.size() != 0) {
                                count += Collections.min(cc);
                                allowLen++;
                            }else {
                                break;
                            }
                        }
                    }
                    if (allowLen == 2) {
                        CO[i] = out_CO + count + 1;
                    }
                }
                break;

            default:
                myPrint("FADD_CO出错");
                break;
        }

        for (int i = 0; i < CO.length; i++) {
            if (CO[i] != 0) {
                if (in_CO[i] != -1) {
                    CO[i] = CO[i] < in_CO[i] ? CO[i] : in_CO[i];
                }
                verPacket.get(i).add(CO[i]);
            }else {
                verPacket.get(i).add(in_CO[i]);
            }
        }
    }

    private void HADDforCO(List<List<Object>> gatePacket) {

        int out_CO = (int)gatePacket.get(0).get(1);

        int CC0_1 = (int)gatePacket.get(1).get(3);
        int CC1_1 = (int)gatePacket.get(1).get(4);
        int in1_CO = (int)gatePacket.get(1).get(2);

        int CC0_2 = (int)gatePacket.get(2).get(3);
        int CC1_2 = (int)gatePacket.get(2).get(4);
        int in2_CO = (int)gatePacket.get(2).get(2);

        verPacket.add(new ArrayList<>());
        verPacket.get(0).add((int)gatePacket.get(1).get(0));
        verPacket.add(new ArrayList<>());
        verPacket.get(1).add((int)gatePacket.get(2).get(0));

        int CO_1 = 0;
        int CO_2 = 0;
        switch ((String)gatePacket.get(0).get(2)) {
            case "C1":
                //1
                if (CC1_2 != 0) {
                    CO_1 = out_CO + CC1_2 + 1;
                }
                //2
                if (CC1_1 != 0) {
                    CO_2 = out_CO + CC1_1 + 1;
                }
                break;

            case "SO":
                //1
                List<Integer> cc2 = new ArrayList<>();
                if (CC0_2 != 0) {
                    cc2.add(CC0_2);
                }
                if (CC1_2 != 0) {
                    cc2.add(CC1_2);
                }
                if (cc2.size() != 0) {
                    CO_1 =  out_CO + Collections.min(cc2) + 1;
                }

                //2
                List<Integer> cc1 = new ArrayList<>();
                if (CC0_1 != 0) {
                    cc1.add(CC0_1);
                }
                if (CC1_1 != 0) {
                    cc1.add(CC1_1);
                }
                if (cc1.size() != 0) {
                    CO_2 = out_CO + Collections.min(cc1) + 1;
                }
                break;
            default:
                myPrint("HADD_CO出错");
                break;
        }

        if (CO_1 != 0) {
            if (in1_CO != -1) {
                CO_1 = CO_1 < in1_CO ? CO_1 : in1_CO;
            }
            verPacket.get(0).add(CO_1);
        }else {
            verPacket.get(0).add(in1_CO);
        }

        if (CO_2 != 0) {
            if (in2_CO != -1) {
                CO_2 = CO_2 < in2_CO ? CO_2 : in2_CO;
            }
            verPacket.get(1).add(CO_2);
        }else {
            verPacket.get(1).add(in2_CO);
        }
    }

    private void SDFFASRforCO(List<List<Object>> gatePacket) {

        int out_CO = (int)gatePacket.get(0).get(1);
        int CO;

        int indexD = -1;
        int indexCLK = -1;
        int indexSE = -1;
        int indexSI = -1;
        int indexSETB = -1;
        int indexRSTB = -1;

        for (int i = 1; i < gatePacket.size(); i++) {
            String pin = (String) gatePacket.get(i).get(1);
            if (pin.equals("D")) {
                indexD = i;
            }else if (pin.equals("CLK")) {
                indexCLK = i;
            }else if (pin.equals("SE")) {
                indexSE = i;
            }else if (pin.equals("SI")) {
                indexSI = i;
            }else if (pin.equals("SETB")) {
                indexSETB = i;
            }else {
                indexRSTB = i;
            }
        }

        int out_CC0 = (int)gatePacket.get(0).get(3);
        int out_CC1 = (int)gatePacket.get(0).get(4);

        int indexCLK_CO = (int)gatePacket.get(indexCLK).get(2);
        int indexCLK_CC0 = (int)gatePacket.get(indexCLK).get(3);
        int indexCLK_CC1 = (int)gatePacket.get(indexCLK).get(4);

        int indexSETB_CC1 = (int)gatePacket.get(indexSETB).get(4);
        int indexSETB_CO = (int)gatePacket.get(indexSETB).get(2);

        int indexRSTB_CC1 = (int)gatePacket.get(indexRSTB).get(4);
        int indexRSTB_CO = (int)gatePacket.get(indexRSTB).get(2);

        int indexSE_CC0 = (int)gatePacket.get(indexSE).get(3);
        int indexSE_CC1 = (int)gatePacket.get(indexSE).get(4);
        int indexSE_CO = (int)gatePacket.get(indexSE).get(2);

        int indexSI_CC0 = (int)gatePacket.get(indexSI).get(3);
        int indexSI_CC1 = (int)gatePacket.get(indexSI).get(4);
        int indexSI_CO = (int)gatePacket.get(indexSI).get(2);

        int indexD_CC0 = (int)gatePacket.get(indexD).get(3);
        int indexD_CC1 = (int)gatePacket.get(indexD).get(4);
        int indexD_CO = (int)gatePacket.get(indexD).get(2);

        // 计算D的CO值
        verPacket.add(new ArrayList<>());
        verPacket.get(0).add((int)gatePacket.get(indexD).get(0));

        // 计算SI的CO值
        verPacket.add(new ArrayList<>());
        verPacket.get(1).add((int)gatePacket.get(indexSI).get(0));

        // 计算SE的CO值
        verPacket.add(new ArrayList<>());
        verPacket.get(2).add((int)gatePacket.get(indexSE).get(0));

        int value1 = 0;
        if ((indexCLK_CC0 != 0) && (indexCLK_CC1 != 0) && (indexRSTB_CC1 != 0) && (indexSETB_CC1 != 0)) {
            value1 = indexCLK_CC0 + indexCLK_CC1 + indexRSTB_CC1 + indexSETB_CC1 + out_CO;
        }

        if (value1 != 0 && indexSE_CC0 != 0) {
            CO = value1 + indexSE_CC0;
            if (indexD_CO != -1) {
                CO = CO < indexD_CO ? CO :indexD_CO;
            }
            verPacket.get(0).add(CO);
        }else {
            verPacket.get(0).add(indexD_CO);
        }

        if (value1 != 0 && indexSE_CC1 != 0) {
            CO = value1 + indexSE_CC1;
            if (indexSI_CO != -1) {
                CO = CO < indexSI_CO ? CO :indexSI_CO;
            }
            verPacket.get(1).add(CO);
        }else {
            verPacket.get(1).add(indexSI_CO);
        }

        List<Integer> COforSE = new ArrayList<>();

        if (value1 != 0) {
            if (indexD_CC0 != 0 && indexSI_CC1 != 0) {
                COforSE.add(value1 + indexD_CC0 + indexSI_CC1);
            }
            if (indexD_CC1 != 0 && indexSI_CC0 != 0) {
                COforSE.add(value1 + indexD_CC1 + indexSI_CC0);
            }
        }

        if (COforSE.size() != 0) {
            CO = Collections.min(COforSE);
            if (indexSE_CO != -1) {
                CO = CO < indexSE_CO ? CO :indexSE_CO;
            }
            verPacket.get(2).add(CO);
        }else {
            verPacket.get(2).add(indexSE_CO);
        }

        // 计算CLK的CO值
        verPacket.add(new ArrayList<>());
        verPacket.get(3).add((int)gatePacket.get(indexCLK).get(0));

        if (out_CC0 != 0 && out_CC1 != 0) {
            CO = out_CO + out_CC0 + out_CC1;
            if (indexCLK_CO != -1) {
                CO = CO < indexCLK_CO ? CO :indexCLK_CO;
            }
            verPacket.get(3).add(CO);
        }else {
            verPacket.get(3).add(indexCLK_CO);
        }

        //计算SETB
        verPacket.add(new ArrayList<>());
        verPacket.get(4).add((int)gatePacket.get(indexSETB).get(0));

        switch ((String)gatePacket.get(0).get(2)) {
            case "Q":
                if (out_CC0 != 0 && indexCLK_CC0 != 0) {
                    CO = out_CO + out_CC0 + indexCLK_CC0;
                    if (indexSETB_CO != -1) {
                        CO = CO < indexSETB_CO ? CO : indexSETB_CO;
                    }
                    verPacket.get(4).add(CO);
                }else {
                    verPacket.get(4).add(indexSETB_CO);
                }
                break;
            case "QN":
                if (out_CC1 != 0 && indexCLK_CC0 != 0) {
                    CO = out_CO + out_CC1 + indexCLK_CC0;
                    if (indexSETB_CO != -1) {
                        CO = CO < indexSETB_CO ? CO : indexSETB_CO;
                    }
                    verPacket.get(4).add(CO);
                }else {
                    verPacket.get(4).add(indexSETB_CO);
                }
                break;

            default:
                myPrint("SDFFASR_CO出错");
                break;
        }

        //计算RSTB
        verPacket.add(new ArrayList<>());
        verPacket.get(5).add((int)gatePacket.get(indexRSTB).get(0));

        switch ((String)gatePacket.get(0).get(2)) {
            case "Q":
                if (out_CC1 != 0 && indexCLK_CC0 != 0) {
                    CO = out_CO + out_CC1 + indexCLK_CC0;
                    if (indexRSTB_CO != -1) {
                        CO = CO < indexRSTB_CO ? CO : indexRSTB_CO;
                    }
                    verPacket.get(5).add(CO);
                }else {
                    verPacket.get(5).add(indexRSTB_CO);
                }
                break;
            case "QN":
                if (out_CC0 != 0 && indexCLK_CC1 != 0) {
                    CO = out_CO + out_CC0 + indexCLK_CC1;
                    if (indexRSTB_CO != -1) {
                        CO = CO < indexRSTB_CO ? CO : indexRSTB_CO;
                    }
                    verPacket.get(5).add(CO);
                }else {
                    verPacket.get(5).add(indexRSTB_CO);
                }
                break;

            default:
                myPrint("SDFFAR_CO出错");
                break;
        }
    }

    private void RDFFNforCO(List<List<Object>> gatePacket) {
        int out_CO = (int)gatePacket.get(0).get(1);
        int CO;

        int indexD = -1;
        int indexCLK = -1;
        int indexRETN = -1;

        for (int i = 1; i < gatePacket.size(); i++) {
            if (gatePacket.get(i).get(1).equals("D")) {
                indexD = i;
            }else if (gatePacket.get(i).get(1).equals("CLK")) {
                indexCLK = i;
            }else {
                indexRETN = i;
            }
        }

        int clkcc0 = (int)gatePacket.get(indexCLK).get(3);
        int clkcc1 = (int)gatePacket.get(indexCLK).get(4);
        int clk_CO = (int)gatePacket.get(indexCLK).get(2);

        int RETN_CC1 = (int)gatePacket.get(indexRETN).get(4);
        int RETN_CO = (int)gatePacket.get(indexRETN).get(2);

        int out_cc0 = (int)gatePacket.get(0).get(3);
        int out_cc1 = (int)gatePacket.get(0).get(4);

        int D_CC1 = (int)gatePacket.get(indexD).get(4);
        int D_CC0 = (int)gatePacket.get(indexD).get(3);
        int D_CO = (int)gatePacket.get(indexD).get(2);

        int cc_clk = 0;
        if (clkcc0 != 0 && clkcc1 != 0 && RETN_CC1 != 0) {
            cc_clk = clkcc0 + clkcc1 + RETN_CC1;
        }

        //计算D
        verPacket.add(new ArrayList<>());
        verPacket.get(0).add((int)gatePacket.get(indexD).get(0));

        if (cc_clk != 0) {
            CO = out_CO + cc_clk;
            if (D_CO != -1) {
                CO = CO < D_CO ? CO : D_CO;
            }
            verPacket.get(0).add(CO);
        }else {
            verPacket.get(0).add(D_CO);
        }

        //计算CLK
        verPacket.add(new ArrayList<>());
        verPacket.get(1).add((int)gatePacket.get(indexCLK).get(0));

        if (out_cc0 != 0 && out_cc1 != 0) {
            CO = out_CO + out_cc0 + out_cc1;
            if (clk_CO != -1) {
                CO = CO < clk_CO ? CO :clk_CO;
            }
            verPacket.get(1).add(CO);
        }else {
            verPacket.get(1).add(clk_CO);
        }

        // 计算RETN的CO值
        verPacket.add(new ArrayList<>());
        verPacket.get(2).add((int)gatePacket.get(indexRETN).get(0));

        int clk_count = 0;
        if (clkcc1 != 0 && clkcc0 != 0) {
            clk_count = clkcc0 + clkcc1;
        }

        List<Integer> indexRETNforCO = new ArrayList<>();

        switch ((String)gatePacket.get(0).get(2)) {
            case "Q":
                if (out_cc0 != 0 && D_CC1 !=0 && clk_count != 0) {
                    CO = out_CO + out_cc0 + D_CC1 + clk_count;
                    indexRETNforCO.add(CO);
                }
                if (out_cc1 != 0 && D_CC0 != 0 && clk_count != 0) {
                    CO = out_CO + out_cc1 + D_CC0 + clk_count;
                    indexRETNforCO.add(CO);
                }

                break;
            case "QN":
                if (out_cc1 != 0 && D_CC1 !=0 && clk_count != 0) {
                    CO = out_CO + out_cc1 + D_CC1 + clk_count;
                    indexRETNforCO.add(CO);
                }
                if (out_cc0 != 0 && D_CC0 != 0 && clk_count != 0) {
                    CO = out_CO + out_cc0 + D_CC0 + clk_count;
                    indexRETNforCO.add(CO);
                }
                break;

            default:
                myPrint("RDFFN_CO出错");
                break;
        }

        if (indexRETNforCO.size() != 0) {
            CO = Collections.min(indexRETNforCO);
            if (RETN_CO != -1) {
                CO = CO < RETN_CO ? CO :RETN_CO;
            }
            verPacket.get(2).add(CO);
        }else {
            verPacket.get(2).add(RETN_CO);
        }

    }

    private void SDFFASforCO(List<List<Object>> gatePacket) {
        int out_CO = (int)gatePacket.get(0).get(1);
        int CO;

        int indexD = -1;
        int indexCLK = -1;
        int indexSETB = -1;
        int indexSE = -1;
        int indexSI = -1;

        for (int i = 1; i < gatePacket.size(); i++) {
            if (gatePacket.get(i).get(1).equals("D")) {
                indexD = i;
            }else if (gatePacket.get(i).get(1).equals("CLK")) {
                indexCLK = i;
            }else if (gatePacket.get(i).get(1).equals("SE")) {
                indexSE = i;
            }else if (gatePacket.get(i).get(1).equals("SI")) {
                indexSI = i;
            }else {
                indexSETB = i;
            }
        }

        int outcc0 = (int)gatePacket.get(0).get(3);
        int outcc1 = (int)gatePacket.get(0).get(4);

        int CLK_CC0 = (int)gatePacket.get(indexCLK).get(3);
        int CLK_CC1 = (int)gatePacket.get(indexCLK).get(4);
        int CLK_CO = (int)gatePacket.get(indexCLK).get(2);

        int SETB_CC1 = (int)gatePacket.get(indexSETB).get(4);
        int SETB_CO = (int)gatePacket.get(indexSETB).get(2);

        int SE_CC1 = (int)gatePacket.get(indexSE).get(4);
        int SE_CC0 = (int)gatePacket.get(indexSE).get(3);
        int SE_CO = (int)gatePacket.get(indexSE).get(2);

        int SI_CC0 = (int)gatePacket.get(indexSI).get(3);
        int SI_CC1 = (int)gatePacket.get(indexSI).get(4);
        int SI_CO = (int)gatePacket.get(indexSI).get(2);

        int D_CC0 = (int)gatePacket.get(indexD).get(3);
        int D_CC1 = (int)gatePacket.get(indexD).get(4);
        int D_CO = (int)gatePacket.get(indexD).get(2);

        int cc_clk = 0;

        if (CLK_CC0 != 0 && CLK_CC1 != 0 && SETB_CC1 != 0) {
            cc_clk = CLK_CC0 + CLK_CC1 + SETB_CC1;
        }

        //计算D
        verPacket.add(new ArrayList<>());
        verPacket.get(0).add((int)gatePacket.get(indexD).get(0));

        if (cc_clk != 0 && SE_CC0 != 0) {
            CO = out_CO + cc_clk + SE_CC0;
            if (D_CO != -1) {
                CO = CO < D_CO ? CO : D_CO;
            }
            verPacket.get(0).add(CO);
        }else {
            verPacket.get(0).add(D_CO);
        }

        //计算CLK的CO值
        verPacket.add(new ArrayList<>());
        verPacket.get(1).add((int)gatePacket.get(indexCLK).get(0));

        if (outcc0 != 0 && outcc1 != 0) {
            CO = out_CO + outcc0 + outcc1;
            if (CLK_CO != -1) {
                CO = CO < CLK_CO ? CO : CLK_CO;
            }
            verPacket.get(1).add(CO);
        }else {
            verPacket.get(1).add(CLK_CO);
        }

        //计算SETB
        verPacket.add(new ArrayList<>());
        verPacket.get(2).add((int)gatePacket.get(indexSETB).get(0));

        switch ((String)gatePacket.get(0).get(2)) {
            case "Q":
                if (outcc1 != 0 && CLK_CC0 != 0) {
                    CO = out_CO + outcc0 + CLK_CC0;
                    if (SETB_CO != -1) {
                        CO = CO < SETB_CO ? CO : SETB_CO;
                    }
                    verPacket.get(2).add(CO);
                }else {
                    verPacket.get(2).add(SETB_CO);
                }
                break;
            case "QN":
                if (outcc0 != 0 && CLK_CC0 != 0) {
                    CO = out_CO + outcc1 + CLK_CC0;
                    if (SETB_CO != -1) {
                        CO = CO < SETB_CO ? CO : SETB_CO;
                    }
                    verPacket.get(2).add(CO);
                }else {
                    verPacket.get(2).add(SETB_CO);
                }
                break;

            default:
                myPrint("SDFFAS_CO出错");
                break;
        }

        // 计算SE
        verPacket.add(new ArrayList<>());
        verPacket.get(3).add((int)gatePacket.get(indexSE).get(0));
        List<Integer> forSE = new ArrayList<>();

        if (cc_clk != 0) {
            if (D_CC0 != 0 && SI_CC1 != 0) {
                forSE.add(out_CO + cc_clk + D_CC0 + SI_CC1);
            }
            if (D_CC1 != 0 && SI_CC0 != 0) {
                forSE.add(out_CO + cc_clk + D_CC1 + SI_CC0);
            }
        }

        if (forSE.size() != 0) {
            CO = Collections.min(forSE);
            if (SE_CO != -1) {
                CO = CO < SE_CO ? CO : SE_CO;
            }
            verPacket.get(3).add(CO);
        }else {
            verPacket.get(3).add(SE_CO);
        }

        // 计算SI
        verPacket.add(new ArrayList<>());
        verPacket.get(4).add((int)gatePacket.get(indexSI).get(0));


        if (cc_clk != 0 && SE_CC1 != 0) {
            CO = out_CO + cc_clk + SE_CC1;
            if (SI_CO != -1) {
                CO = CO < SI_CO ? CO : SI_CO;
            }
            verPacket.get(4).add(CO);
        }else {
            verPacket.get(4).add(SI_CO);
        }

    }

    private void SDFFARforCO(List<List<Object>> gatePacket) {
        int out_CO = (int)gatePacket.get(0).get(1);
        int CO;

        int indexD = -1;
        int indexCLK = -1;
        int indexRSTB = -1;
        int indexSE = -1;
        int indexSI = -1;

        for (int i = 1; i < gatePacket.size(); i++) {
            if (gatePacket.get(i).get(1).equals("D")) {
                indexD = i;
            }else if (gatePacket.get(i).get(1).equals("CLK")) {
                indexCLK = i;
            }else if (gatePacket.get(i).get(1).equals("SE")) {
                indexSE = i;
            }else if (gatePacket.get(i).get(1).equals("SI")) {
                indexSI = i;
            }else {
                indexRSTB = i;
            }
        }

        int CLK_CC0 = (int)gatePacket.get(indexCLK).get(3);
        int CLK_CC1 = (int)gatePacket.get(indexCLK).get(4);
        int CLK_CO = (int)gatePacket.get(indexCLK).get(2);

        int outcc0 = (int)gatePacket.get(0).get(3);
        int outcc1 = (int)gatePacket.get(0).get(4);

        int RSTB_CC1 = (int)gatePacket.get(indexRSTB).get(4);
        int RSTB_CO = (int)gatePacket.get(indexRSTB).get(2);

        int SE_CC1 = (int)gatePacket.get(indexSE).get(4);
        int SE_CC0 = (int)gatePacket.get(indexSE).get(3);
        int SE_CO = (int)gatePacket.get(indexSE).get(2);

        int SI_CC0 = (int)gatePacket.get(indexSI).get(3);
        int SI_CC1 = (int)gatePacket.get(indexSI).get(4);
        int SI_CO = (int)gatePacket.get(indexSI).get(2);

        int D_CC0 = (int)gatePacket.get(indexD).get(3);
        int D_CC1 = (int)gatePacket.get(indexD).get(4);
        int D_CO = (int)gatePacket.get(indexD).get(2);

        int cc_clk = 0;

        if (CLK_CC0 != 0 && CLK_CC1 != 0 && RSTB_CC1 != 0) {
            cc_clk = CLK_CC0 + CLK_CC1 + RSTB_CC1;
        }

        //计算D
        verPacket.add(new ArrayList<>());
        verPacket.get(0).add((int)gatePacket.get(indexD).get(0));

        if (cc_clk != 0 && SE_CC0 != 0) {
            CO = out_CO + cc_clk + SE_CC0;
            if (D_CO != -1) {
                CO = CO < D_CO ? CO : D_CO;
            }
            verPacket.get(0).add(CO);
        }else {
            verPacket.get(0).add(D_CO);
        }

        //计算CLK的CO值
        verPacket.add(new ArrayList<>());
        verPacket.get(1).add((int)gatePacket.get(indexCLK).get(0));

        if (outcc0 != 0 && outcc1 != 0) {
            CO = out_CO + outcc0 + outcc1;
            if (CLK_CO != -1) {
                CO = CO < CLK_CO ? CO : CLK_CO;
            }
            verPacket.get(1).add(CO);
        }else {
            verPacket.get(1).add(CLK_CO);
        }

        //计算RSTB
        verPacket.add(new ArrayList<>());
        verPacket.get(2).add((int)gatePacket.get(indexRSTB).get(0));

        switch ((String)gatePacket.get(0).get(2)) {
            case "Q":
                if (outcc1 != 0 && CLK_CC0 != 0) {
                    CO = out_CO + outcc1 + CLK_CC0;
                    if (RSTB_CO != -1) {
                        CO = CO < RSTB_CO ? CO : RSTB_CO;
                    }
                    verPacket.get(2).add(CO);
                }else {
                    verPacket.get(2).add(RSTB_CO);
                }
                break;
            case "QN":
                if (outcc0 != 0 && CLK_CC1 != 0) {
                    CO = out_CO + outcc0 + CLK_CC1;
                    if (RSTB_CO != -1) {
                        CO = CO < RSTB_CO ? CO : RSTB_CO;
                    }
                    verPacket.get(2).add(CO);
                }else {
                    verPacket.get(2).add(RSTB_CO);
                }
                break;

            default:
                myPrint("SDFFAR_CO出错");
                break;
        }

        // 计算SE
        verPacket.add(new ArrayList<>());
        verPacket.get(3).add((int)gatePacket.get(indexSE).get(0));
        List<Integer> forSE = new ArrayList<>();

        if (cc_clk != 0) {
            if (D_CC0 != 0 && SI_CC1 != 0) {
                forSE.add(out_CO + cc_clk + D_CC0 + SI_CC1);
            }
            if (D_CC1 != 0 && SI_CC0 != 0) {
                forSE.add(out_CO + cc_clk + D_CC1 + SI_CC0);
            }
        }

        if (forSE.size() != 0) {
            CO = Collections.min(forSE);
            if (SE_CO != -1) {
                CO = CO < SE_CO ? CO : SE_CO;
            }
            verPacket.get(3).add(CO);
        }else {
            verPacket.get(3).add(SE_CO);
        }

        // 计算SI
        verPacket.add(new ArrayList<>());
        verPacket.get(4).add((int)gatePacket.get(indexSI).get(0));

        if (cc_clk != 0 && SE_CC1 != 0) {
            CO = out_CO + cc_clk + SE_CC1;
            if (SI_CO != -1) {
                CO = CO < SI_CO ? CO : SI_CO;
            }
            verPacket.get(4).add(CO);
        }else {
            verPacket.get(4).add(SI_CO);
        }

    }

    private void SDFFforCO(List<List<Object>> gatePacket) {

        int CO;
        int out_CO = (int)gatePacket.get(0).get(1);

        int indexD = -1;
        int indexSI = -1;
        int indexSE = -1;
        int indexCLK = -1;

        for (int i = 1; i < gatePacket.size(); i++) {
            String str = (String)gatePacket.get(i).get(1);
            if (str.equals("D")) {
                indexD = i;
            }else if (str.equals("SI")) {
                indexSI = i;
            }else if (str.equals("SE")) {
                indexSE = i;
            }else {
                indexCLK =i;
            }
        }

        int clkcc0 = (int)gatePacket.get(indexCLK).get(3);
        int clkcc1 = (int)gatePacket.get(indexCLK).get(4);
        int clk_CO = (int)gatePacket.get(indexCLK).get(2);

        int dcc0 = (int)gatePacket.get(indexD).get(3);
        int dcc1 = (int)gatePacket.get(indexD).get(4);
        int d_CO = (int)gatePacket.get(indexD).get(2);

        int sicc0 = (int)gatePacket.get(indexSI).get(3);
        int sicc1 = (int)gatePacket.get(indexSI).get(4);
        int si_CO = (int)gatePacket.get(indexSI).get(2);

        int secc0 = (int)gatePacket.get(indexSE).get(3);
        int secc1 = (int)gatePacket.get(indexSE).get(4);
        int se_CO = (int)gatePacket.get(indexSE).get(2);

        int outcc0 = (int)gatePacket.get(0).get(3);
        int outcc1 = (int)gatePacket.get(0).get(4);

        int cc_clk = 0;
        if (clkcc0 != 0 && clkcc1 != 0) {
            cc_clk = clkcc0 + clkcc1;
        }

        //计算D的CO值
        verPacket.add(new ArrayList<>());
        verPacket.get(0).add((int)gatePacket.get(indexD).get(0));
        if (secc0 != 0 && cc_clk != 0) {
            CO = secc0 + cc_clk + out_CO;
            if (d_CO != -1) {
                CO = CO < d_CO ? CO : d_CO;
            }
            verPacket.get(0).add(CO);
        }else {
            verPacket.get(0).add(d_CO);
        }

        //计算SI的CO值
        verPacket.add(new ArrayList<>());
        verPacket.get(1).add((int)gatePacket.get(indexSI).get(0));
        if (secc1 != 0 && cc_clk != 0) {
            CO = secc1 + cc_clk + out_CO;
            if (si_CO != -1) {
                CO = CO < si_CO ? CO : si_CO;
            }
            verPacket.get(1).add(CO);
        }else {
            verPacket.get(1).add(si_CO);
        }

        //计算SE的CO值
        verPacket.add(new ArrayList<>());
        verPacket.get(2).add((int)gatePacket.get(indexSE).get(0));
        List<Integer> cc_se = new ArrayList<>();
        if (dcc0 != 0 && sicc1 != 0 ) {
            cc_se.add(dcc0 + sicc1);
        }
        if (dcc1 != 0 && sicc0 != 0) {
            cc_se.add(dcc1 + sicc0);
        }
        if (cc_se.size() != 0 && cc_clk != 0) {
            CO = out_CO + Collections.min(cc_se) + cc_clk;
            if (se_CO != -1) {
                CO = CO < se_CO ? CO : se_CO;
            }
            verPacket.get(2).add(CO);
        }else {
            verPacket.get(2).add(se_CO);
        }

        //计算CLK的CO值
        verPacket.add(new ArrayList<>());
        verPacket.get(3).add((int)gatePacket.get(indexCLK).get(0));
        List<Integer> cc_1 = new ArrayList<>();
        List<Integer> cc_2 = new ArrayList<>();

        if (dcc0 != 0 && secc0 != 0) {
            cc_1.add(dcc0 + secc0);
        }
        if (sicc0 != 0 && secc1 != 0) {
            cc_1.add(sicc0 + secc1);
        }

        if (dcc1 != 0 && secc0 != 0) {
            cc_2.add(dcc1 + secc0);
        }
        if (sicc1 != 0 && secc1 != 0) {
            cc_2.add(sicc1 + secc1);
        }

        List<Integer> cc_3 = new ArrayList<>();
        switch ((String)gatePacket.get(0).get(2)) {
            case "Q":
                if (outcc1 != 0 && cc_1.size() != 0) {
                    cc_3.add(outcc1 + Collections.min(cc_1));
                }
                if (outcc0 != 0 && cc_2.size() != 0) {
                    cc_3.add(outcc0 + Collections.min(cc_2));
                }
                break;
            case "QN":
                if (outcc0 != 0 && cc_1.size() != 0) {
                    cc_3.add(outcc0 + Collections.min(cc_1));
                }
                if (outcc1 != 0 && cc_2.size() != 0) {
                    cc_3.add(outcc1 + Collections.min(cc_2));
                }
                break;

            default:
                myPrint("SDFF_CO错误");
                break;
        }

        if (cc_3.size() != 0) {
            CO = out_CO + Collections.min(cc_3);
            if (clk_CO != -1) {
                CO = CO < clk_CO ? CO : clk_CO;
            }
            verPacket.get(3).add(CO);
        }else {
            verPacket.get(3).add(clk_CO);
        }
    }

    private void DFFARforCO(List<List<Object>> gatePacket) {

        int out_CO = (int)gatePacket.get(0).get(1);
        int CO;

        int indexD = -1;
        int indexCLK = -1;
        int indexRSTB = -1;

        for (int i = 1; i < gatePacket.size(); i++) {
            if (gatePacket.get(i).get(1).equals("D")) {
                indexD = i;
            }else if (gatePacket.get(i).get(1).equals("RSTB")) {
                indexRSTB = i;
            }
            else {
                indexCLK = i;
            }
        }

        int clkcc0 = (int)gatePacket.get(indexCLK).get(3);
        int clkcc1 = (int)gatePacket.get(indexCLK).get(4);
        int RSTB_cc1 = (int)gatePacket.get(indexRSTB).get(4);
        int cc_clk = 0;

        if (clkcc0 != 0 && clkcc1 != 0 && RSTB_cc1 != 0) {
            cc_clk = clkcc0 + clkcc1 + RSTB_cc1;
        }

        //计算D
        verPacket.add(new ArrayList<>());
        verPacket.get(0).add((int)gatePacket.get(indexD).get(0));
        int d_CO = (int)gatePacket.get(indexD).get(2);

        if (cc_clk != 0) {
            CO = out_CO + cc_clk;
            if (d_CO != -1) {
                CO = CO < d_CO ? CO : d_CO;
            }
            verPacket.get(0).add(CO);
        }else {
            verPacket.get(0).add(d_CO);
        }

        //计算CLK的CO值
        verPacket.add(new ArrayList<>());
        verPacket.get(1).add((int)gatePacket.get(indexCLK).get(0));
        int clk_CO = (int)gatePacket.get(indexCLK).get(2);

        List<Integer> cc = new ArrayList<>();
        int outcc0 = (int)gatePacket.get(0).get(3);
        int outcc1 = (int)gatePacket.get(0).get(4);
        int dcc0 = (int)gatePacket.get(indexD).get(3);
        int dcc1 = (int)gatePacket.get(indexD).get(4);
        switch ((String)gatePacket.get(0).get(2)) {
            case "Q":
                if (outcc0 != 0 && dcc1 != 0) {
                    cc.add(outcc0 + dcc1);
                }
                if (outcc1 != 0 && dcc0 != 0) {
                    cc.add(outcc1 + dcc0);
                }
                break;

            case "QN":
                if (outcc0 != 0 && dcc0 != 0) {
                    cc.add(outcc0 + dcc0);
                }
                if (outcc1 != 0 && dcc1 != 0) {
                    cc.add(outcc1 + dcc1);
                }
                break;

            default:
                myPrint("DFFAR输出端口匹配错误");
                break;
        }

        if (cc.size() != 0 && RSTB_cc1 != 0) {
            CO = out_CO + RSTB_cc1 + Collections.min(cc);
            if (clk_CO != -1) {
                CO = CO < clk_CO ? CO : clk_CO;
            }
            verPacket.get(1).add(CO);
        }else {
            verPacket.get(1).add(clk_CO);
        }

        //计算RSTB
        verPacket.add(new ArrayList<>());
        verPacket.get(2).add((int)gatePacket.get(indexRSTB).get(0));
        int RSTB_CO = (int)gatePacket.get(indexRSTB).get(2);

        switch ((String)gatePacket.get(0).get(2)) {
            case "Q":
                if (outcc1 != 0 && clkcc0 != 0) {
                    CO = out_CO + outcc1 + clkcc0;
                    if (RSTB_CO != -1) {
                        CO = CO < RSTB_CO ? CO : RSTB_CO;
                    }
                    verPacket.get(2).add(CO);
                }else {
                    verPacket.get(2).add(RSTB_CO);
                }
                break;
            case "QN":
                if (outcc0 != 0 && clkcc1 != 0) {
                    CO = out_CO + outcc0 + clkcc1;
                    if (RSTB_CO != -1) {
                        CO = CO < RSTB_CO ? CO : RSTB_CO;
                    }
                    verPacket.get(2).add(CO);
                }else {
                    verPacket.get(2).add(RSTB_CO);
                }
                break;

            default:
                myPrint("DFFAR输出端口匹配错误");
                break;
        }
    }

    private void DFFASforCO(List<List<Object>> gatePacket) {

        int out_CO = (int)gatePacket.get(0).get(1);
        int CO;

        int indexD = -1;
        int indexCLK = -1;
        int indexSETB = -1;

        for (int i = 1; i < gatePacket.size(); i++) {
            if (gatePacket.get(i).get(1).equals("D")) {
                indexD = i;
            }else if (gatePacket.get(i).get(1).equals("SETB")) {
                indexSETB = i;
            }
            else {
                indexCLK = i;
            }
        }

        int clkcc0 = (int)gatePacket.get(indexCLK).get(3);
        int clkcc1 = (int)gatePacket.get(indexCLK).get(4);
        int setbcc1 = (int)gatePacket.get(indexSETB).get(4);
        int cc_clk = 0;

        if (clkcc0 != 0 && clkcc1 != 0 && setbcc1 != 0) {
            cc_clk = clkcc0 + clkcc1 + setbcc1;
        }

        //计算D
        verPacket.add(new ArrayList<>());
        verPacket.get(0).add((int)gatePacket.get(indexD).get(0));
        int d_CO = (int)gatePacket.get(indexD).get(2);
        if (cc_clk != 0) {
            CO = out_CO + cc_clk;
            if (d_CO != -1) {
                CO = CO < d_CO ? CO : d_CO;
            }
            verPacket.get(0).add(CO);
        }else {
            verPacket.get(0).add(d_CO);
        }

        //计算CLK
        verPacket.add(new ArrayList<>());
        verPacket.get(1).add((int)gatePacket.get(indexCLK).get(0));
        int clk_CO = (int)gatePacket.get(indexCLK).get(2);

        List<Integer> cc = new ArrayList<>();
        int outcc0 = (int)gatePacket.get(0).get(3);
        int outcc1 = (int)gatePacket.get(0).get(4);
        int dcc0 = (int)gatePacket.get(indexD).get(3);
        int dcc1 = (int)gatePacket.get(indexD).get(4);
        switch ((String)gatePacket.get(0).get(2)) {
            case "Q":
                if (outcc0 != 0 && dcc1 != 0) {
                    cc.add(outcc0 + dcc1);
                }
                if (outcc1 != 0 && dcc0 != 0) {
                    cc.add(outcc1 + dcc0);
                }
                break;

            case "QN":
                if (outcc0 != 0 && dcc0 != 0) {
                    cc.add(outcc0 + dcc0);
                }
                if (outcc1 != 0 && dcc1 != 0) {
                    cc.add(outcc1 + dcc1);
                }
                break;

            default:

                myPrint("DFFAS输出端口出现错误匹配");
                break;
        }

        if (cc.size() != 0 && setbcc1 != 0) {
            CO = out_CO + setbcc1 + Collections.min(cc);
            if (clk_CO != -1) {
                CO = CO < clk_CO ? CO : clk_CO;
            }
            verPacket.get(1).add(CO);
        }else {
            verPacket.get(1).add(clk_CO);
        }

        //计算SETB
        verPacket.add(new ArrayList<>());
        verPacket.get(2).add((int)gatePacket.get(indexSETB).get(0));
        int setb_CO = (int)gatePacket.get(indexSETB).get(2);

        switch ((String)gatePacket.get(0).get(2)) {
            case "Q":
                if (outcc0 != 0 && clkcc0 != 0) {
                    CO = out_CO + outcc0 + clkcc0;
                    if (setb_CO != -1) {
                        CO = CO < setb_CO ? CO : setb_CO;
                    }
                    verPacket.get(2).add(CO);
                }else {
                    verPacket.get(2).add(setb_CO);
                }
                break;
            case "QN":
                if (outcc1 != 0 && clkcc1 != 0) {
                    CO = out_CO + outcc1 + clkcc1;
                    if (setb_CO != -1) {
                        CO = CO < setb_CO ? CO : setb_CO;
                    }
                    verPacket.get(2).add(CO);
                }else {
                    verPacket.get(2).add(setb_CO);
                }
                break;

            default:
                myPrint("DFFAS输出端口出现错误匹配");
                break;
        }
    }

    private void myPrint(String string) {
        System.out.println(string);
        System.exit(0);

    }

    private void DFFforCO(List<List<Object>> gatePacket) {

        int out_CO = (int)gatePacket.get(0).get(1);
        int CO;

        int indexD = -1;
        int indexCLK = -1;

        for (int i = 1; i < gatePacket.size(); i++) {
            if (gatePacket.get(i).get(1).equals("D")) {
                indexD = i;
            }else {
                indexCLK = i;
            }
        }
        int clkcc0 = (int)gatePacket.get(indexCLK).get(3);
        int clkcc1 = (int)gatePacket.get(indexCLK).get(4);
        int clk_CO = (int)gatePacket.get(indexCLK).get(2);

        int cc_clk = 0;
        if (clkcc0 != 0 && clkcc1 != 0) {
            cc_clk = clkcc0 + clkcc1;
        }

        //计算D
        verPacket.add(new ArrayList<>());
        verPacket.get(0).add((int)gatePacket.get(indexD).get(0));
        int D_CO = (int)gatePacket.get(indexD).get(2);

        if (cc_clk != 0) {
            CO = out_CO + cc_clk;
            if (D_CO != -1) {
                CO = CO < D_CO ? CO : D_CO;
            }
            verPacket.get(0).add(CO);
        }else {
            verPacket.get(0).add(D_CO);
        }

        //计算CLK
        verPacket.add(new ArrayList<>());
        verPacket.get(1).add((int)gatePacket.get(indexCLK).get(0));

        List<Integer> cc = new ArrayList<>();
        int out_cc0 = (int)gatePacket.get(0).get(3);
        int out_cc1 = (int)gatePacket.get(0).get(4);
        int dcc1 = (int)gatePacket.get(indexD).get(4);
        int dcc0 = (int)gatePacket.get(indexD).get(3);

        switch ((String)gatePacket.get(0).get(2)) {
            case "Q":
                if (out_cc0 != 0 && dcc1 != 0) {
                    cc.add(out_cc0 + dcc1);
                }
                if (out_cc1 != 0 && dcc0 != 0) {
                    cc.add(out_cc1 + dcc0);
                }
                break;
            case "QN":
                if (out_cc0 != 0 && dcc0 != 0) {
                    cc.add(out_cc0 + dcc0);
                }
                if (out_cc1 != 0 && dcc1 != 0) {
                    cc.add(out_cc1 + dcc1);
                }
                break;

            default:
                break;
        }
        if (cc.size() != 0) {
            CO = out_CO + Collections.min(cc) + 1;
            if (clk_CO != -1) {
                CO = CO < clk_CO ? CO :clk_CO;
            }
            verPacket.get(1).add(CO);
        }else {
            verPacket.get(1).add(clk_CO);
        }
    }
}

