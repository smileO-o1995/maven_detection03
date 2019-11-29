package util;

/**
 * @author wen
 * @date 2019/11/26 0026-21:41
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GateCCUtil {

    int CC0 = 0;
    int CC1 = 0;

    public GateCCUtil(List<List<Object>> gatePacket) {
        System.out.println("CC   " + gatePacket.get(0).get(0));

        switch ((String)gatePacket.get(0).get(0)) {

            //反相器
            case "INVX0" : INVforCC(gatePacket); break;
            case "INVX1" : INVforCC(gatePacket); break;
            case "INVX2" : INVforCC(gatePacket); break;
            case "INVX4" : INVforCC(gatePacket); break;
            case "INVX8" : INVforCC(gatePacket); break;
            case "INVX16" : INVforCC(gatePacket); break;
            case "INVX32" : INVforCC(gatePacket); break;

            //反向缓冲器
            case "IBUFFX2" : IBUFFforCC(gatePacket); break;
            case "IBUFFX4" : IBUFFforCC(gatePacket); break;
            case "IBUFFX8" : IBUFFforCC(gatePacket); break;
            case "IBUFFX16" : IBUFFforCC(gatePacket); break;
            case "IBUFFX32" : IBUFFforCC(gatePacket); break;

            //无反向缓冲器
            case "NBUFFX2" : NUBFFforCC(gatePacket); break;
            case "NBUFFX4" : NUBFFforCC(gatePacket); break;
            case "NBUFFX8" : NUBFFforCC(gatePacket); break;
            case "NBUFFX16" : NUBFFforCC(gatePacket); break;
            case "NBUFFX32" : NUBFFforCC(gatePacket); break;

            //无反向使能缓冲器
            case "TNBUFFX1" : TNBUFFforCC(gatePacket); break;
            case "TNBUFFX2" : TNBUFFforCC(gatePacket); break;
            case "TNBUFFX4" : TNBUFFforCC(gatePacket); break;
            case "TNBUFFX8" : TNBUFFforCC(gatePacket); break;
            case "TNBUFFX16" : TNBUFFforCC(gatePacket); break;
            case "TNBUFFX32" : TNBUFFforCC(gatePacket); break;

            // D触发器
            case "DFFX1" : DFFforCC(gatePacket); break;
            case "DFFX2" : DFFforCC(gatePacket); break;
            case "DFFNX1" : DFFforCC(gatePacket); break;
            case "DFFNX2" : DFFforCC(gatePacket); break;

            // 上升沿D触发器 异步置位AS
            case "DFFASX1" : DFFASforCC(gatePacket); break;
            case "DFFASX2" : DFFASforCC(gatePacket); break;

            //上升沿D触发器 异步复位AR
            case "DFFARX1" : DFFARforCC(gatePacket); break;
            case "DFFARX2" : DFFARforCC(gatePacket); break;

            // 扫描D触发器
            case "SDFFX1" : SDFFforCC(gatePacket); break;
            case "SDFFX2" : SDFFforCC(gatePacket); break;

            // 扫描D触发器 异步复位
            case "SDFFARX1" : SDFFARforCC(gatePacket);break;
            case "SDFFARX2" : SDFFARforCC(gatePacket);break;

            // 扫描D触发器 异步置位
            case "SDFFASX1" : SDFFASforCC(gatePacket);break;
            case "SDFFASX2" : SDFFASforCC(gatePacket);break;

            // 同步低电平使能 下降沿D触发器
            case "RDFFNX1" : RDFFNforCC(gatePacket);break;
            case "RDFFNX2" : RDFFNforCC(gatePacket);break;

            // 扫描D触发器 异步置位与复位
            case "SDFFASRX1" : SDFFASRforCC(gatePacket);break;
            case "SDFFASRX2" : SDFFASRforCC(gatePacket);break;

            //半加器HADD
            case "HADDX1" : HADDforCC(gatePacket); break;
            case "HADDX2" : HADDforCC(gatePacket); break;

            //全加器FADD
            case "FADDX1" : FADDforCC(gatePacket); break;
            case "FADDX2" : FADDforCC(gatePacket); break;

            //与门
            case "AND2X1" : ANDforCC(gatePacket); break;
            case "AND2X2" : ANDforCC(gatePacket); break;
            case "AND2X4" : ANDforCC(gatePacket); break;
            case "AND3X1" : ANDforCC(gatePacket); break;
            case "AND3X2" : ANDforCC(gatePacket); break;
            case "AND3X4" : ANDforCC(gatePacket); break;
            case "AND4X1" : ANDforCC(gatePacket); break;
            case "AND4X2" : ANDforCC(gatePacket); break;
            case "AND4X4" : ANDforCC(gatePacket); break;

            //与非门
            case "NAND2X0" : NANDforCC(gatePacket); break;
            case "NAND2X1" : NANDforCC(gatePacket); break;
            case "NAND2X2" : NANDforCC(gatePacket); break;
            case "NAND2X4" : NANDforCC(gatePacket); break;
            case "NAND3X0" : NANDforCC(gatePacket); break;
            case "NAND3X1" : NANDforCC(gatePacket); break;
            case "NAND3X2" : NANDforCC(gatePacket); break;
            case "NAND3X4" : NANDforCC(gatePacket); break;
            case "NAND4X0" : NANDforCC(gatePacket); break;
            case "NAND4X1" : NANDforCC(gatePacket); break;

            //或门
            case "OR2X1" : ORforCC(gatePacket); break;
            case "OR2X2" : ORforCC(gatePacket); break;
            case "OR2X4" : ORforCC(gatePacket); break;
            case "OR3X1" : ORforCC(gatePacket); break;
            case "OR3X2" : ORforCC(gatePacket); break;
            case "OR3X4" : ORforCC(gatePacket); break;
            case "OR4X1" : ORforCC(gatePacket); break;
            case "OR4X2" : ORforCC(gatePacket); break;
            case "OR4X4" : ORforCC(gatePacket); break;

            //或非门
            case "NOR2X0" : NORforCC(gatePacket); break;
            case "NOR2X1" : NORforCC(gatePacket); break;
            case "NOR2X2" : NORforCC(gatePacket); break;
            case "NOR2X4" : NORforCC(gatePacket); break;
            case "NOR3X0" : NORforCC(gatePacket); break;
            case "NOR3X1" : NORforCC(gatePacket); break;
            case "NOR3X2" : NORforCC(gatePacket); break;
            case "NOR3X4" : NORforCC(gatePacket); break;
            case "NOR4X0" : NORforCC(gatePacket); break;
            case "NOR4X1" : NORforCC(gatePacket); break;

            //二输入异或
            case "XOR2X1" : XOR2forCC(gatePacket); break;
            case "XOR2X2" : XOR2forCC(gatePacket); break;

            //三输入异或
            case "XOR3X1" : XOR3forCC(gatePacket); break;
            case "XOR3X2" : XOR3forCC(gatePacket); break;

            //二输入同或
            case "XNOR2X1" : XNOR2forCC(gatePacket); break;
            case "XNOR2X2" : XNOR2forCC(gatePacket); break;

            //三输入同或
            case "XNOR3X1" : XNOR3forCC(gatePacket); break;
            case "XNOR3X2" : XNOR3forCC(gatePacket); break;

            //与或门AO21
            case "AO21X1" : AO21forCC(gatePacket); break;
            case "AO21X2" : AO21forCC(gatePacket); break;

            //与或AO22
            case "AO22X1" : AO22forCC(gatePacket); break;
            case "AO22X2" : AO22forCC(gatePacket); break;

            //与或AO221
            case "AO221X1" : AO221forCC(gatePacket); break;
            case "AO221X2" : AO221forCC(gatePacket); break;

            //与或AO222
            case "AO222X1" : AO222forCC(gatePacket); break;
            case "AO222X2" : AO222forCC(gatePacket); break;

            //与或非门AOI21
            case "AOI21X1" : AOI21forCC(gatePacket); break;
            case "AOI21X2" : AOI21forCC(gatePacket); break;

            //与或非门AOI22
            case "AOI22X1" : AOI22forCC(gatePacket); break;
            case "AOI22X2" : AOI22forCC(gatePacket); break;

            //与或非AOI221
            case "AOI221X1" : AOI221forCC(gatePacket); break;
            case "AOI221X2" : AOI221forCC(gatePacket); break;

            //与或非AOI222
            case "AOI222X1" : AOI222forCC(gatePacket); break;
            case "AOI222X2" : AOI222forCC(gatePacket); break;

            //或与门OA21
            case "OA21X1" : OA21forCC(gatePacket); break;
            case "OA21X2" : OA21forCC(gatePacket); break;

            //或与门OA22
            case "OA22X1" : OA22forCC(gatePacket); break;
            case "OA22X2" : OA22forCC(gatePacket); break;

            //或与门OA221
            case "OA221X1" : OA221forCC(gatePacket); break;
            case "OA221X2" : OA221forCC(gatePacket); break;

            //或与门OA222
            case "OA222X1" : OA222forCC(gatePacket); break;
            case "OA222X2" : OA222forCC(gatePacket); break;

            //或与非门OAI21
            case "OAI21X1" : OAI21forCC(gatePacket); break;
            case "OAI21X2" : OAI21forCC(gatePacket); break;

            //或与非门OAI22
            case "OAI22X1" : OAI22forCC(gatePacket); break;
            case "OAI22X2" : OAI22forCC(gatePacket); break;

            //或与非门OAI221
            case "OAI221X1" : OAI221forCC(gatePacket); break;
            case "OAI221X2" : OAI221forCC(gatePacket); break;

            //或与非门OAI222
            case "OAI222X1" : OAI222forCC(gatePacket); break;
            case "OAI222X2" : OAI222forCC(gatePacket); break;

            //数据选择器MUX21
            case "MUX21X1" : MUX21forCC(gatePacket); break;
            case "MUX21X2" : MUX21forCC(gatePacket); break;

            //数据选择器MUX41
            case "MUX41X1" : MUX41forCC(gatePacket); break;
            case "MUX41X2" : MUX41forCC(gatePacket); break;

            //assign语句
            case "assign" : AssignforCC(gatePacket); break;

            //High to Low Level Shifter
            case "LSDNX1" : NUBFFforCC(gatePacket); break;
            case "LSDNX2" : NUBFFforCC(gatePacket); break;
            case "LSDNX4" : NUBFFforCC(gatePacket); break;
            case "LSDNX8" : NUBFFforCC(gatePacket); break;

            //hold 0 isolation cell(logic AND)
            case "ISOLANDX1" : ANDforCC(gatePacket); break;
            case "ISOLANDX2" : ANDforCC(gatePacket); break;
            case "ISOLANDX4" : ANDforCC(gatePacket); break;
            case "ISOLANDX8" : ANDforCC(gatePacket); break;

            //High to Low Level Shifter/ Active Low Enable
            case "LSDNENX1" : LSDNENforCC(gatePacket); break;
            case "LSDNENX2" : LSDNENforCC(gatePacket); break;
            case "LSDNENX4" : LSDNENforCC(gatePacket); break;
            case "LSDNENX8" : LSDNENforCC(gatePacket); break;

            // Hold 1 Isolation Cell (Logic OR):
            case "ISOLORX1" : ORforCC(gatePacket); break;
            case "ISOLORX2" : ORforCC(gatePacket); break;
            case "ISOLORX4" : ORforCC(gatePacket); break;
            case "ISOLORX8" : ORforCC(gatePacket); break;

            default:System.out.println((String)gatePacket.get(0).get(0)+":逻辑门还未录入");
                System.exit(0);
                break;
        }

        int old_CC0 = (int)gatePacket.get(0).get(3);
        int old_CC1 = (int)gatePacket.get(0).get(4);

//		if (CC0 > 10000) {
//			CC0 = 0;
//		}
//		if (CC1 > 10000) {
//			CC1 = 0;
//		}
//		if (old_CC0 != 0 && CC0 != 0) {
//			CC0 = CC0 < old_CC0 ? CC0 : old_CC0;
//		}else {
//			CC0 = old_CC0;
//		}
//		if (old_CC1 != 0 && CC1 != 0) {
//			CC1 = CC1 < old_CC1 ? CC1 : old_CC1;
//		}else {
//			CC1 = old_CC1;
//		}
        if(CC0 <= 0) {
            CC0 = old_CC0;
        }else if (old_CC0 != 0) {
            CC0 = CC0 < old_CC0 ? CC0 : old_CC0;
        }

        if(CC1 <= 0) {
            CC1 = old_CC1;
        }else if (old_CC1 != 0) {
            CC1 = CC1 < old_CC1 ? CC1 : old_CC1;
        }
    }

    private void LSDNENforCC(List<List<Object>> gatePacket) {

        int index;
        int indexD = -1;
        int indexEN = -1;

        for(int i = 1; i < gatePacket.size(); i++) {
            if (gatePacket.get(i).get(0).equals("D")) {
                indexD = i;
            }else {
                indexEN = i;
            }
        }

        int indexD_CC0 = (int)gatePacket.get(indexD).get(1);
        int indexD_CC1 = (int)gatePacket.get(indexD).get(2);

        int indexEN_CC0 = (int)gatePacket.get(indexEN).get(1);
        int indexEN_CC1 = (int)gatePacket.get(indexEN).get(2);

        List<Integer> y_cc1 = new ArrayList<>();

        if (indexEN_CC1 != 0) {
            index = indexEN_CC1 + 1;
            y_cc1.add(index);
        }
        if (indexEN_CC0 != 0) {
            if (indexD_CC1 != 0) {
                index = indexD_CC1 + 1;
                y_cc1.add(index);
            }
            if (indexD_CC0 != 0) {
                CC0 = indexEN_CC0 + indexD_CC0 + 1;
            }
        }

        if (y_cc1.size() != 0) {
            CC1 = Collections.min(y_cc1);
        }

    }

    private void AssignforCC(List<List<Object>> gatePacket) {

        int in_CC0 = (int)gatePacket.get(1).get(1);
        int in_CC1 = (int)gatePacket.get(1).get(2);
        if (in_CC0 != 0) {
            CC0 = in_CC0;
        }

        if (in_CC1 != 0) {
            CC1 = in_CC1;
        }

    }

    private void FADDforCC(List<List<Object>> gatePacket) {

        switch ((String)gatePacket.get(0).get(1)) {

            case "S" : XOR3forCC(gatePacket); break;

            case "CO" :
                List<Integer> CC0list = new ArrayList<>();
                List<Integer> CC1list = new ArrayList<>();

                int index;
                //计算CC0

                int in1_CC0 = (int)gatePacket.get(1).get(1);
                int in2_CC0 = (int)gatePacket.get(2).get(1);
                int in3_CC0 = (int)gatePacket.get(3).get(1);

                int in1_CC1 = (int)gatePacket.get(1).get(2);
                int in2_CC1 = (int)gatePacket.get(2).get(2);
                int in3_CC1 = (int)gatePacket.get(3).get(2);

                if (in1_CC0 != 0 && in2_CC0 != 0) {
                    index = in1_CC0 + in2_CC0 + 1;
                    CC0list.add(index);
                }
                if (in1_CC0 != 0 && in3_CC0 != 0) {
                    index = in1_CC0 + in3_CC0 + 1;
                    CC0list.add(index);
                }
                if (in2_CC0 != 0 && in3_CC0 != 0) {
                    index = in2_CC0 + in3_CC0 + 1;
                    CC0list.add(index);
                }
                //计算CC1
                if (in1_CC1 != 0 && in2_CC1 != 0) {
                    index = in1_CC1 + in2_CC1 + 1;
                    CC1list.add(index);
                }
                if (in1_CC1 != 0 && in3_CC1 != 0) {
                    index = in1_CC1 + in3_CC1 + 1;
                    CC1list.add(index);
                }
                if (in2_CC1 != 0 && in3_CC1 != 0) {
                    index = in2_CC1 + in3_CC1 + 1;
                    CC1list.add(index);
                }

                //CC0
                if (CC0list.size() != 0) {
                    CC0 = Collections.min(CC0list);
                }

                //CC1
                if (CC1list.size() != 0) {
                    CC1 = Collections.min(CC1list);
                }

                break;

            default:
                System.out.println("全加器出错");
                System.exit(0);
                break;
        }
    }

    private void HADDforCC(List<List<Object>> gatePacket) {

        switch ((String)gatePacket.get(0).get(1)) {

            case "SO": XOR2forCC(gatePacket); break;

            case "C1":
                List<Integer> CC0list = new ArrayList<>();
                int index;
                //计算CC0
                int in1_CC0 = (int)gatePacket.get(1).get(1);
                if (in1_CC0 != 0) {
                    index = in1_CC0 + 1;
                    CC0list.add(index);
                }
                int in2_CC0 = (int)gatePacket.get(2).get(1);
                if (in2_CC0 != 0) {
                    index = in2_CC0 + 1;
                    CC0list.add(index);
                }
                //计算CC1
                int in1_CC1 = (int)gatePacket.get(1).get(2);
                int in2_CC1 = (int)gatePacket.get(2).get(2);
                if (in1_CC1 != 0 && in2_CC1 != 0) {
                    CC1 = in1_CC1 + in2_CC1 + 1;
                }
                //CC0
                if (CC0list.size() != 0) {
                    CC0 = Collections.min(CC0list);
                }
                break;
            default:
                System.out.println("半加器出错");
                System.exit(0);
                break;
        }

    }

    private void MUX41forCC(List<List<Object>> gatePacket) {

        List<Integer> CC0list = new ArrayList<>();
        List<Integer> CC1list = new ArrayList<>();

        int indexS0 = -1;
        int indexS1 = -1;
        int indexA1 = -1;
        int indexA2 = -1;
        int indexA3 = -1;
        int indexA4 = -1;

        int index;

        for (int i = 1; i < gatePacket.size(); i++) {

            switch ((String)gatePacket.get(i).get(0)) {
                case "S0" : indexS0 = i; break;
                case "S1" : indexS1 = i; break;
                case "IN1" : indexA1 = i; break;
                case "IN2" : indexA2 = i; break;
                case "IN3" : indexA3 = i; break;
                case "IN4" : indexA4 = i; break;
                default: break;
            }
        }

        int indexA1_CC0 = (int)gatePacket.get(indexA1).get(1);
        int indexA2_CC0 = (int)gatePacket.get(indexA2).get(1);
        int indexA3_CC0 = (int)gatePacket.get(indexA3).get(1);
        int indexA4_CC0 = (int)gatePacket.get(indexA4).get(1);
        int indexS0_CC0 = (int)gatePacket.get(indexS0).get(1);
        int indexS1_CC0 = (int)gatePacket.get(indexS1).get(1);

        int indexA1_CC1 = (int)gatePacket.get(indexA1).get(2);
        int indexA2_CC1 = (int)gatePacket.get(indexA2).get(2);
        int indexA3_CC1 = (int)gatePacket.get(indexA3).get(2);
        int indexA4_CC1 = (int)gatePacket.get(indexA4).get(2);
        int indexS0_CC1 = (int)gatePacket.get(indexS0).get(2);
        int indexS1_CC1 = (int)gatePacket.get(indexS1).get(2);

        //计算CC0
        if (indexS0_CC0 != 0 && indexS1_CC0 != 0 && indexA1_CC0 != 0) {
            index = indexS0_CC0 + indexS1_CC0 + indexA1_CC0 + 1;
            CC0list.add(index);
        }
        if (indexS0_CC1 != 0 && indexS1_CC0 != 0 && indexA2_CC0 != 0) {
            index = indexS0_CC1 + indexS1_CC0 + indexA2_CC0 + 1;
            CC0list.add(index);
        }
        if (indexS0_CC0 != 0 && indexS1_CC1 != 0 && indexA3_CC0 != 0) {
            index = indexS0_CC0 + indexS1_CC1 + indexA3_CC0 + 1;
            CC0list.add(index);
        }
        if (indexS0_CC1 != 0 && indexS1_CC1 != 0 && indexA4_CC0 != 0) {
            index = indexS0_CC1 + indexS1_CC1 + indexA4_CC0 + 1;
            CC0list.add(index);
        }

        //计算CC1
        if (indexS0_CC0 != 0 && indexS1_CC0 != 0 && indexA1_CC1 != 0) {
            index = indexS0_CC0 + indexS1_CC0 + indexA1_CC1 + 1;
            CC1list.add(index);
        }
        if (indexS0_CC1 != 0 && indexS1_CC0 != 0 && indexA2_CC1 != 0) {
            index = indexS0_CC1 + indexS1_CC0 + indexA2_CC1 + 1;
            CC1list.add(index);
        }
        if (indexS0_CC0 != 0 && indexS1_CC1 != 0 && indexA3_CC1 != 0) {
            index = indexS0_CC0 + indexS1_CC1 + indexA3_CC1 + 1;
            CC1list.add(index);
        }
        if (indexS0_CC1 != 0 && indexS1_CC1 != 0 && indexA4_CC1 != 0) {
            index = indexS0_CC1 + indexS1_CC1 + indexA4_CC1 + 1;
            CC1list.add(index);
        }

        //CC0
        if (CC0list.size() != 0) {
            CC0 = Collections.min(CC0list);
        }

        //CC1
        if (CC1list.size() != 0) {
            CC1 = Collections.min(CC1list);
        }

    }

    private void MUX21forCC(List<List<Object>> gatePacket) {

        List<Integer> CC0list = new ArrayList<>();
        List<Integer> CC1list = new ArrayList<>();

        int indexS = -1;
        int indexA1 = -1;
        int indexA2 = -1;

        for (int i = 1; i < gatePacket.size(); i++) {
            if (gatePacket.get(i).get(0).equals("S")) {
                indexS = i;
            }else if (gatePacket.get(i).get(0).equals("IN1")) {
                indexA1 = i;
            }else {
                indexA2 = i;
            }
        }

        int indexA1_CC0 = (int)gatePacket.get(indexA1).get(1);
        int indexA2_CC0 = (int)gatePacket.get(indexA2).get(1);

        int indexA1_CC1 = (int)gatePacket.get(indexA1).get(2);
        int indexA2_CC1 = (int)gatePacket.get(indexA2).get(2);

        int indexS_CC0 = (int)gatePacket.get(indexS).get(1);
        int indexS_CC1 = (int)gatePacket.get(indexS).get(2);

        int index;

        //计算CC0
        if (indexS_CC0 != 0 && indexA1_CC0 != 0) {
            index = indexS_CC0 + indexA1_CC0 + 1;
            CC0list.add(index);
        }
        if (indexS_CC1 != 0 && indexA2_CC0 != 0) {
            index = indexS_CC1 + indexA2_CC0 + 1;
            CC0list.add(index);
        }

        //计算CC1
        if (indexS_CC0 != 0 && indexA1_CC1 != 0) {
            index = indexS_CC0 + indexA1_CC1 + 1;
            CC1list.add(index);
        }
        if (indexS_CC1 != 0 && indexA2_CC1 != 0) {
            index = indexS_CC1 + indexA2_CC1 + 1;
            CC1list.add(index);
        }

        //CC0
        if (CC0list.size() != 0) {
            CC0 = Collections.min(CC0list);
        }

        //CC1
        if (CC1list.size() != 0) {
            CC1 = Collections.min(CC1list);
        }
    }

    private void OAI222forCC(List<List<Object>> gatePacket) {

        OA222forCC(gatePacket);
        reverse();
    }

    private void OAI221forCC(List<List<Object>> gatePacket) {

        OA221forCC(gatePacket);
        reverse();
    }

    private void OAI22forCC(List<List<Object>> gatePacket) {

        OA22forCC(gatePacket);
        reverse();
    }

    private void OAI21forCC(List<List<Object>> gatePacket) {

        OA21forCC(gatePacket);
        reverse();
    }

    private void OA222forCC(List<List<Object>> gatePacket) {

        List<Integer> CC0list = new ArrayList<>();
        List<Integer> CC1_12 = new ArrayList<>();
        List<Integer> CC1_34 = new ArrayList<>();
        List<Integer> CC1_56 = new ArrayList<>();

        int indexA1 = -1;
        int indexA2 = -1;
        int indexA3 = -1;
        int indexA4 = -1;
        int indexA5 = -1;
        int indexA6 = -1;

        int index;

        for (int i = 1; i < gatePacket.size(); i++) {
            if (gatePacket.get(i).get(0).equals("IN1")) {
                indexA1 = i;
            }else if (gatePacket.get(i).get(0).equals("IN2")) {
                indexA2 = i;
            }else if (gatePacket.get(i).get(0).equals("IN3")) {
                indexA3 = i;
            }else if (gatePacket.get(i).get(0).equals("IN4")) {
                indexA4 = i;
            }else if (gatePacket.get(i).get(0).equals("IN5")) {
                indexA5 = i;
            }else {
                indexA6 = i;
            }
        }

        int indexA1_CC0 = (int)gatePacket.get(indexA1).get(1);
        int indexA2_CC0 = (int)gatePacket.get(indexA2).get(1);
        int indexA3_CC0 = (int)gatePacket.get(indexA3).get(1);
        int indexA4_CC0 = (int)gatePacket.get(indexA4).get(1);
        int indexA5_CC0 = (int)gatePacket.get(indexA5).get(1);
        int indexA6_CC0 = (int)gatePacket.get(indexA6).get(1);

        int indexA1_CC1 = (int)gatePacket.get(indexA1).get(2);
        int indexA2_CC1 = (int)gatePacket.get(indexA2).get(2);
        int indexA3_CC1 = (int)gatePacket.get(indexA3).get(2);
        int indexA4_CC1 = (int)gatePacket.get(indexA4).get(2);
        int indexA5_CC1 = (int)gatePacket.get(indexA5).get(2);
        int indexA6_CC1 = (int)gatePacket.get(indexA6).get(2);

        //计算CC0
        if (indexA1_CC0 != 0 && indexA2_CC0 != 0) {
            index = indexA1_CC0 + indexA2_CC0 + 1;
            CC0list.add(index);
        }
        if (indexA3_CC0 != 0 && indexA4_CC0 != 0) {
            index = indexA3_CC0 + indexA4_CC0 + 1;
            CC0list.add(index);
        }
        if (indexA5_CC0 != 0 && indexA6_CC0 != 0) {
            index = indexA5_CC0 + indexA6_CC0 + 1;
            CC0list.add(index);
        }

        //计算CC1
        if (indexA1_CC1 != 0) {
            CC1_12.add(indexA1_CC1);
        }
        if (indexA2_CC1 != 0) {
            CC1_12.add(indexA2_CC1);
        }
        if (indexA3_CC1 != 0) {
            CC1_34.add(indexA3_CC1);
        }
        if (indexA4_CC1 != 0) {
            CC1_34.add(indexA4_CC1);
        }
        if (indexA5_CC1 != 0) {
            CC1_56.add(indexA5_CC1);
        }
        if (indexA6_CC1 != 0) {
            CC1_56.add(indexA6_CC1);
        }

        //CC0
        if (CC0list.size() != 0) {
            CC0 = Collections.min(CC0list);
        }
        if (CC1_12.size() != 0 && CC1_34.size() != 0 && CC1_56.size() != 0) {
            CC1 = Collections.min(CC1_12) + Collections.min(CC1_34) + Collections.min(CC1_56) + 1;
        }
    }

    private void OA221forCC(List<List<Object>> gatePacket) {

        List<Integer> CC0list = new ArrayList<>();
        List<Integer> CC1_12 = new ArrayList<>();
        List<Integer> CC1_34 = new ArrayList<>();

        int indexA1 = -1;
        int indexA2 = -1;
        int indexA3 = -1;
        int indexA4 = -1;
        int indexA5 = -1;

        int index;

        for (int i = 1; i < gatePacket.size(); i++) {
            if (gatePacket.get(i).get(0).equals("IN1")) {
                indexA1 = i;
            }else if (gatePacket.get(i).get(0).equals("IN2")) {
                indexA2 = i;
            }else if (gatePacket.get(i).get(0).equals("IN3")) {
                indexA3 = i;
            }else if (gatePacket.get(i).get(0).equals("IN4")) {
                indexA4 = i;
            }else {
                indexA5 = i;
            }
        }

        int indexA1_CC0 = (int)gatePacket.get(indexA1).get(1);
        int indexA2_CC0 = (int)gatePacket.get(indexA2).get(1);
        int indexA3_CC0 = (int)gatePacket.get(indexA3).get(1);
        int indexA4_CC0 = (int)gatePacket.get(indexA4).get(1);
        int indexA5_CC0 = (int)gatePacket.get(indexA5).get(1);

        int indexA1_CC1 = (int)gatePacket.get(indexA1).get(2);
        int indexA2_CC1 = (int)gatePacket.get(indexA2).get(2);
        int indexA3_CC1 = (int)gatePacket.get(indexA3).get(2);
        int indexA4_CC1 = (int)gatePacket.get(indexA4).get(2);
        int indexA5_CC1 = (int)gatePacket.get(indexA5).get(2);

        //计算CC0
        if (indexA1_CC0 != 0 && indexA2_CC0 != 0) {
            index = indexA1_CC0 + indexA2_CC0 + 1;
            CC0list.add(index);
        }
        if (indexA3_CC0 != 0 && indexA4_CC0 != 0) {
            index = indexA3_CC0 + indexA4_CC0 + 1;
            CC0list.add(index);
        }
        if (indexA5_CC0 != 0) {
            index = indexA5_CC0 + 1;
            CC0list.add(index);
        }

        //计算CC1
        if (indexA1_CC1 != 0) {
            CC1_12.add(indexA1_CC1);
        }
        if (indexA2_CC1 != 0) {
            CC1_12.add(indexA2_CC1);
        }
        if (indexA3_CC1 != 0) {
            CC1_34.add(indexA3_CC1);
        }
        if (indexA4_CC1 != 0) {
            CC1_34.add(indexA4_CC1);
        }

        //CC0
        if (CC0list.size() != 0) {
            CC0 = Collections.min(CC0list);
        }
        if (CC1_12.size() != 0 && CC1_34.size() != 0 && indexA5_CC1 != 0) {
            CC1 = Collections.min(CC1_12) + Collections.min(CC1_34) + indexA5_CC1 + 1;
        }
    }

    private void OA22forCC(List<List<Object>> gatePacket) {

        List<Integer> CC0list = new ArrayList<>();
        List<Integer> CC1_12 = new ArrayList<>();
        List<Integer> CC1_34 = new ArrayList<>();

        int indexA1 = -1;
        int indexA2 = -1;
        int indexA3 = -1;
        int indexA4 = -1;

        int index;

        for (int i = 1; i < gatePacket.size(); i++) {
            if (gatePacket.get(i).get(0).equals("IN1")) {
                indexA1 = i;
            }else if (gatePacket.get(i).get(0).equals("IN2")) {
                indexA2 = i;
            }else if (gatePacket.get(i).get(0).equals("IN3")) {
                indexA3 = i;
            }else {
                indexA4 = i;
            }
        }

        int indexA1_CC0 = (int)gatePacket.get(indexA1).get(1);
        int indexA2_CC0 = (int)gatePacket.get(indexA2).get(1);
        int indexA3_CC0 = (int)gatePacket.get(indexA3).get(1);
        int indexA4_CC0 = (int)gatePacket.get(indexA4).get(1);

        int indexA1_CC1 = (int)gatePacket.get(indexA1).get(2);
        int indexA2_CC1 = (int)gatePacket.get(indexA2).get(2);
        int indexA3_CC1 = (int)gatePacket.get(indexA3).get(2);
        int indexA4_CC1 = (int)gatePacket.get(indexA4).get(2);

        //计算CC0
        if (indexA1_CC0 != 0 && indexA2_CC0 != 0) {
            index = indexA1_CC0 + indexA2_CC0 + 1;
            CC0list.add(index);
        }
        if (indexA3_CC0 != 0 && indexA4_CC0 != 0) {
            index = indexA3_CC0 + indexA4_CC0 + 1;
            CC0list.add(index);
        }

        //计算CC1
        if (indexA1_CC1 != 0) {
            CC1_12.add(indexA1_CC1);
        }
        if (indexA2_CC1 != 0) {
            CC1_12.add(indexA2_CC1);
        }
        if (indexA3_CC1 != 0) {
            CC1_34.add(indexA3_CC1);
        }
        if (indexA4_CC1 != 0) {
            CC1_34.add(indexA4_CC1);
        }

        //CC0
        if (CC0list.size() != 0) {
            CC0 = Collections.min(CC0list);
        }
        if (CC1_12.size() != 0 && CC1_34.size() != 0) {
            CC1 = Collections.min(CC1_12) + Collections.min(CC1_34) + 1;
        }
    }

    private void OA21forCC(List<List<Object>> gatePacket) {

        List<Integer> CC0list = new ArrayList<>();
        List<Integer> CC1_12 = new ArrayList<>();

        int indexA1 = -1;
        int indexA2 = -1;
        int indexA3 = -1;

        int index = 0;

        //找到三个输入的位置
        for (int i = 1; i < gatePacket.size(); i++) {
            if (gatePacket.get(i).get(0).equals("IN1")) {
                indexA1 = i;
            }else if (gatePacket.get(i).get(0).equals("IN2")) {
                indexA2 = i;
            }else {
                indexA3 = i;
            }
        }

        int indexA1_CC0 = (int)gatePacket.get(indexA1).get(1);
        int indexA2_CC0 = (int)gatePacket.get(indexA2).get(1);
        int indexA3_CC0 = (int)gatePacket.get(indexA3).get(1);

        int indexA1_CC1 = (int)gatePacket.get(indexA1).get(2);
        int indexA2_CC1 = (int)gatePacket.get(indexA2).get(2);
        int indexA3_CC1 = (int)gatePacket.get(indexA3).get(2);

        //计算CC0
        if (indexA1_CC0 != 0 && indexA2_CC0 != 0) {
            index = indexA1_CC0 + indexA2_CC0 + 1;
            CC0list.add(index);
        }
        if (indexA3_CC0 != 0) {
            index = indexA3_CC0 + 1;
            CC0list.add(index);
        }

        //计算CC1
        if (indexA1_CC1 != 0) {
            CC1_12.add(indexA1_CC1);
        }
        if (indexA2_CC1 != 0) {
            CC1_12.add(indexA2_CC1);
        }

        //CC0
        if (CC0list.size() != 0) {
            CC0 = Collections.min(CC0list);
        }

        //CC1
        if (CC1_12.size() != 0 && indexA3_CC1 != 0) {
            CC1 = Collections.min(CC1_12) + indexA3_CC1 + 1;
        }
    }

    private void AOI222forCC(List<List<Object>> gatePacket) {

        AO222forCC(gatePacket);
        reverse();
    }



    private void AOI221forCC(List<List<Object>> gatePacket) {

        AO221forCC(gatePacket);
        reverse();
    }



    private void AOI22forCC(List<List<Object>> gatePacket) {

        AO22forCC(gatePacket);
        reverse();
    }

    private void AOI21forCC(List<List<Object>> gatePacket) {

        AO21forCC(gatePacket);
        reverse();
    }

    private void reverse() {

        int b = CC0;
        CC0 = CC1;
        CC1 = b;
    }

    private void AO222forCC(List<List<Object>> gatePacket) {

        List<Integer> CC0_12 = new ArrayList<>();
        List<Integer> CC0_34 = new ArrayList<>();
        List<Integer> CC0_56 = new ArrayList<>();
        List<Integer> CC1list = new ArrayList<>();

        int indexA1 = -1;
        int indexA2 = -1;
        int indexA3 = -1;
        int indexA4 = -1;
        int indexA5 = -1;
        int indexA6 = -1;

        int index;

        for (int i = 1; i < gatePacket.size(); i++) {
            if (gatePacket.get(i).get(0).equals("IN1")) {
                indexA1 = i;
            }else if (gatePacket.get(i).get(0).equals("IN2")) {
                indexA2 = i;
            }else if (gatePacket.get(i).get(0).equals("IN3")) {
                indexA3 = i;
            }else if (gatePacket.get(i).get(0).equals("IN4")) {
                indexA4 = i;
            }else if (gatePacket.get(i).get(0).equals("IN5")) {
                indexA5 = i;
            }else {
                indexA6 = i;
            }
        }

        int indexA1_CC0 = (int)gatePacket.get(indexA1).get(1);
        int indexA2_CC0 = (int)gatePacket.get(indexA2).get(1);
        int indexA3_CC0 = (int)gatePacket.get(indexA3).get(1);
        int indexA4_CC0 = (int)gatePacket.get(indexA4).get(1);
        int indexA5_CC0 = (int)gatePacket.get(indexA5).get(1);
        int indexA6_CC0 = (int)gatePacket.get(indexA6).get(1);

        int indexA1_CC1 = (int)gatePacket.get(indexA1).get(2);
        int indexA2_CC1 = (int)gatePacket.get(indexA2).get(2);
        int indexA3_CC1 = (int)gatePacket.get(indexA3).get(2);
        int indexA4_CC1 = (int)gatePacket.get(indexA4).get(2);
        int indexA5_CC1 = (int)gatePacket.get(indexA5).get(2);
        int indexA6_CC1 = (int)gatePacket.get(indexA6).get(2);

        //计算CC0
        if (indexA1_CC0 != 0) {
            CC0_12.add(indexA1_CC0);
        }
        if (indexA2_CC0 != 0) {
            CC0_12.add(indexA2_CC0);
        }
        if (indexA3_CC0 != 0) {
            CC0_34.add(indexA3_CC0);
        }
        if (indexA4_CC0 != 0) {
            CC0_34.add(indexA4_CC0);
        }
        if (indexA5_CC0 != 0) {
            CC0_56.add(indexA5_CC0);
        }
        if (indexA6_CC0 != 0) {
            CC0_56.add(indexA6_CC0);
        }

        //计算CC1
        if (indexA1_CC1 != 0 && indexA2_CC1 != 0) {
            index = indexA1_CC1 + indexA2_CC1 + 1;
            CC1list.add(index);
        }
        if (indexA3_CC1 != 0 && indexA4_CC1 != 0) {
            index = indexA3_CC1 + indexA4_CC1 + 1;
            CC1list.add(index);
        }
        if (indexA5_CC1 != 0 && indexA6_CC1 != 0) {
            index = indexA5_CC1 + indexA6_CC1 + 1;
            CC1list.add(index);
        }

        //CC0
        if (CC0_12.size() != 0 && CC0_34.size() != 0 && CC0_56.size() != 0) {
            CC0 = Collections.min(CC0_12) + Collections.min(CC0_34) + Collections.min(CC0_56) + 1;
        }

        //CC1
        if (CC1list.size() != 0) {
            CC1 = Collections.min(CC1list);
        }
    }

    private void AO221forCC(List<List<Object>> gatePacket) {

        List<Integer> CC0_12 = new ArrayList<>();
        List<Integer> CC0_34 = new ArrayList<>();
        List<Integer> CC1list = new ArrayList<>();

        int indexA1 = -1;
        int indexA2 = -1;
        int indexA3 = -1;
        int indexA4 = -1;
        int indexA5 = -1;

        int index;

        for (int i = 1; i < gatePacket.size(); i++) {
            if (gatePacket.get(i).get(0).equals("IN1")) {
                indexA1 = i;
            }else if (gatePacket.get(i).get(0).equals("IN2")) {
                indexA2 = i;
            }else if (gatePacket.get(i).get(0).equals("IN3")) {
                indexA3 = i;
            }else if (gatePacket.get(i).get(0).equals("IN4")) {
                indexA4 = i;
            }else {
                indexA5 = i;
            }
        }

        int indexA1_CC0 = (int)gatePacket.get(indexA1).get(1);
        int indexA2_CC0 = (int)gatePacket.get(indexA2).get(1);
        int indexA3_CC0 = (int)gatePacket.get(indexA3).get(1);
        int indexA4_CC0 = (int)gatePacket.get(indexA4).get(1);
        int indexA5_CC0 = (int)gatePacket.get(indexA5).get(1);

        int indexA1_CC1 = (int)gatePacket.get(indexA1).get(2);
        int indexA2_CC1 = (int)gatePacket.get(indexA2).get(2);
        int indexA3_CC1 = (int)gatePacket.get(indexA3).get(2);
        int indexA4_CC1 = (int)gatePacket.get(indexA4).get(2);
        int indexA5_CC1 = (int)gatePacket.get(indexA5).get(2);

        //计算CC0
        if (indexA1_CC0 != 0) {
            CC0_12.add(indexA1_CC0);
        }
        if (indexA2_CC0 != 0) {
            CC0_12.add(indexA2_CC0);
        }
        if (indexA3_CC0 != 0) {
            CC0_34.add(indexA3_CC0);
        }
        if (indexA4_CC0 != 0) {
            CC0_34.add(indexA4_CC0);
        }

        //计算CC1
        if (indexA1_CC1 != 0 && indexA2_CC1 != 0) {
            index = indexA1_CC1 + indexA2_CC1 + 1;
            CC1list.add(index);
        }
        if (indexA3_CC1 != 0 && indexA4_CC1 != 0) {
            index = indexA3_CC1 + indexA4_CC1 + 1;
            CC1list.add(index);
        }
        if (indexA5_CC1 != 0 ) {
            index = indexA5_CC1 + 1;
            CC1list.add(index);
        }

        //CC0
        if (CC0_12.size() != 0 && CC0_34.size() != 0 && indexA5_CC0 != 0) {
            CC0 = Collections.min(CC0_12) + Collections.min(CC0_34) + indexA5_CC0 + 1;
        }

        //CC1
        if (CC1list.size() != 0) {
            CC1 = Collections.min(CC1list);
        }
    }

    private void AO22forCC(List<List<Object>> gatePacket) {

        List<Integer> CC0_12 = new ArrayList<>();
        List<Integer> CC0_34 = new ArrayList<>();
        List<Integer> CC1list = new ArrayList<>();

        int indexA1 = -1;
        int indexA2 = -1;
        int indexA3 = -1;
        int indexA4 = -1;

        int index;

        for (int i = 1; i < gatePacket.size(); i++) {
            if (gatePacket.get(i).get(0).equals("IN1")) {
                indexA1 = i;
            }else if (gatePacket.get(i).get(0).equals("IN2")) {
                indexA2 = i;
            }else if (gatePacket.get(i).get(0).equals("IN3")) {
                indexA3 = i;
            }else {
                indexA4 = i;
            }
        }

        //计算CC0
        int indexA1_CC0 = (int)gatePacket.get(indexA1).get(1);
        int indexA2_CC0 = (int)gatePacket.get(indexA2).get(1);
        int indexA3_CC0 = (int)gatePacket.get(indexA3).get(1);
        int indexA4_CC0 = (int)gatePacket.get(indexA4).get(1);
        int indexA1_CC1 = (int)gatePacket.get(indexA1).get(2);
        int indexA2_CC1 = (int)gatePacket.get(indexA2).get(2);
        int indexA3_CC1 = (int)gatePacket.get(indexA3).get(2);
        int indexA4_CC1 = (int)gatePacket.get(indexA4).get(2);

        if (indexA1_CC0 != 0) {
            CC0_12.add(indexA1_CC0);
        }
        if (indexA2_CC0 != 0) {
            CC0_12.add(indexA2_CC0);
        }
        if (indexA3_CC0 != 0) {
            CC0_34.add(indexA3_CC0);
        }
        if (indexA4_CC0 != 0) {
            CC0_34.add(indexA4_CC0);
        }

        //计算CC1
        if (indexA1_CC1 != 0 && indexA2_CC1 != 0) {
            index = indexA1_CC1 + indexA2_CC1 + 1;
            CC1list.add(index);
        }
        if (indexA3_CC1 != 0 && indexA4_CC1 != 0) {
            index = indexA3_CC1 + indexA4_CC1 + 1;
            CC1list.add(index);
        }

        //CC0
        if (CC0_12.size() != 0 && CC0_34.size() != 0) {
            CC0 = Collections.min(CC0_12) + Collections.min(CC0_34) + 1;
        }

        //CC1
        if (CC1list.size() != 0) {
            CC1 = Collections.min(CC1list);
        }
    }

    private void AO21forCC(List<List<Object>> gatePacket) {
        List<Integer> CC0list = new ArrayList<>();
        List<Integer> CC1list = new ArrayList<>();

        int indexA1 = -1;
        int indexA2 = -1;
        int indexA3 = -1;

        int index = 0;

        //找到三个输入的位置
        for (int i = 1; i < gatePacket.size(); i++) {
            if (gatePacket.get(i).get(0).equals("IN1")) {
                indexA1 = i;
            }else if (gatePacket.get(i).get(0).equals("IN2")) {
                indexA2 = i;
            }else {
                indexA3 = i;
            }
        }

        //计算CC0
        int indexA1_CC0 = (int)gatePacket.get(indexA1).get(1);
        int indexA2_CC0 = (int)gatePacket.get(indexA2).get(1);
        int indexA3_CC0 = (int)gatePacket.get(indexA3).get(1);
        int indexA1_CC1 = (int)gatePacket.get(indexA1).get(2);
        int indexA2_CC1 = (int)gatePacket.get(indexA2).get(2);
        int indexA3_CC1 = (int)gatePacket.get(indexA3).get(2);

        if (indexA1_CC0 != 0 && indexA3_CC0 != 0) {
            index = indexA1_CC0 + indexA3_CC0 + 1;
            CC0list.add(index);
        }
        if (indexA2_CC0 != 0 && indexA3_CC0 != 0) {
            index = indexA2_CC0 + indexA3_CC0 + 1;
            CC0list.add(index);
        }

        //计算CC1

        if (indexA1_CC1 != 0 && indexA2_CC1 != 0) {
            index = indexA1_CC1 + indexA2_CC1 + 1;
            CC1list.add(index);
        }
        if (indexA3_CC1 != 0) {
            index = indexA3_CC1 + 1;
            CC1list.add(index);
        }

        if (CC0list.size() != 0) {
            CC0 = Collections.min(CC0list);
        }

        if (CC1list.size() != 0) {
            CC1 = Collections.min(CC1list);
        }
    }

    private void XNOR3forCC(List<List<Object>> gatePacket) {

        XOR3forCC(gatePacket);
        int b = CC0;
        CC0 = CC1;
        CC1 = b;

    }

    private void XNOR2forCC(List<List<Object>> gatePacket) {

        XOR2forCC(gatePacket);
        int b = CC0;
        CC0 = CC1;
        CC1 = b;

    }

    private void XOR3forCC(List<List<Object>> gatePacket) {

        int CC0_1 = (int)gatePacket.get(1).get(1);
        int CC0_2 = (int)gatePacket.get(2).get(1);
        int CC0_3 = (int)gatePacket.get(3).get(1);

        int CC1_1 = (int)gatePacket.get(1).get(2);
        int CC1_2 = (int)gatePacket.get(2).get(2);
        int CC1_3 = (int)gatePacket.get(3).get(2);

        List<Integer> CC0list = new ArrayList<>();
        List<Integer> CC1list = new ArrayList<>();

        int index;

        if (CC0_1 != 0) {
            if (CC0_2 != 0) {
                if (CC0_3 != 0) {
                    index = CC0_1 + CC0_2 + CC0_3 + 1;
                    CC0list.add(index);
                }
                if (CC1_3 != 0) {
                    index = CC0_1 + CC0_2 + CC1_3 + 1;
                    CC1list.add(index);
                }
            }
            if (CC1_2 != 0) {
                if (CC0_3 != 0) {
                    index = CC0_1 + CC1_2 + CC0_3 + 1;
                    CC1list.add(index);
                }
                if (CC1_3 != 0) {
                    index = CC0_1 + CC1_2 + CC1_3 + 1;
                    CC0list.add(index);
                }
            }
        }

        if (CC1_1 != 0) {
            if (CC0_2 != 0) {
                if (CC0_3 != 0) {
                    index = CC1_1 + CC0_2 + CC0_3 + 1;
                    CC1list.add(index);
                }
                if (CC1_3 != 0) {
                    index = CC1_1 + CC0_2 + CC1_3 + 1;
                    CC0list.add(index);
                }
            }
            if (CC1_2 != 0) {
                if (CC0_3 != 0) {
                    index = CC1_1 + CC1_2 + CC0_3 + 1;
                    CC0list.add(index);
                }
                if (CC1_3 != 0) {
                    index = CC1_1 + CC1_2 + CC1_3 + 1;
                    CC1list.add(index);
                }
            }
        }

        if (CC0list.size() != 0) {
            CC0 = Collections.min(CC0list);
        }

        if (CC1list.size() != 0) {
            CC1 = Collections.min(CC1list);
        }
    }

    private void XOR2forCC(List<List<Object>> gatePacket) {
        int in1_CC1 = (int)gatePacket.get(1).get(2);
        int in1_CC0 = (int)gatePacket.get(1).get(1);
        int in2_CC0 = (int)gatePacket.get(2).get(1);
        int in2_CC1 = (int)gatePacket.get(2).get(2);

        List<Integer> all_CC0 = new ArrayList<>();
        List<Integer> all_CC1 = new ArrayList<>();

        if (in1_CC0 != 0) {
            if (in2_CC0 != 0) {
                all_CC0.add(in1_CC0 + in2_CC0);
            }
            if (in2_CC1 != 0) {
                all_CC1.add(in1_CC0 + in2_CC1);
            }
        }
        if (in1_CC1 != 0) {
            if (in2_CC0 != 0) {
                all_CC1.add(in1_CC1 + in2_CC0);
            }
            if (in2_CC1 != 0) {
                all_CC0.add(in1_CC1 + in2_CC1);
            }
        }

        if (all_CC0.size() != 0) {
            CC0 = Collections.min(all_CC0) + 1;
        }

        if (all_CC1.size() != 0) {
            CC1 = Collections.min(all_CC1) + 1;
        }
    }

    private void NORforCC(List<List<Object>> gatePacket) {

        ORforCC(gatePacket);
        int b = CC0;
        CC0 = CC1;
        CC1 = b;
    }

    private void ORforCC(List<List<Object>> gatePacket) {

        //输入节点的数量
        int inNetLen = gatePacket.size() - 1;

        int countCC0 = 0;//在所有CC0State = 'B'的节点中，CC0之和
        int numofCC0 = 0;
        List<Integer> all_CC1 = new ArrayList<>();

        // CC0
        for (int i = 1; i <= inNetLen; i++) {
            int in_CC0 = (int)gatePacket.get(i).get(1);
            if (in_CC0 == 0) {
                break;
            }
            countCC0 += in_CC0;
            numofCC0 ++;
        }

        if (numofCC0 == inNetLen) {
            CC0 = countCC0 + 1;
        }

        // CC1
        for (int i = 1; i <= inNetLen; i++) {
            int in_CC1 = (int)gatePacket.get(i).get(2);
            if (in_CC1 != 0) {
                all_CC1.add(in_CC1);
            }
        }

        if (all_CC1.size() != 0) {
            CC1 = Collections.min(all_CC1) + 1;
        }
    }

    private void NANDforCC(List<List<Object>> gatePacket) {

        ANDforCC(gatePacket);
        int b = CC0;
        CC0 = CC1;
        CC1 = b;

    }

    private void ANDforCC(List<List<Object>> gatePacket) {

        //输入节点的数量
        int inNetLen = gatePacket.size() - 1;

        List<Integer> all_CC0 = new ArrayList<>();
        int count_CC1 = 0; //计算输入节点的CC1的值
        int num = 0;

        //计算输出节点的CCO值
        for (int i = 1; i <= inNetLen; i++) {
            int in_CC0 = (int)gatePacket.get(i).get(1);
            if (in_CC0 != 0) {
                all_CC0.add(in_CC0);
            }
        }
        if (all_CC0.size() != 0) {
            CC0 = Collections.min(all_CC0) + 1;
        }

        // 计算CC1
        for (int i = 1; i <= inNetLen; i++) {
            int in_CC1 = (int)gatePacket.get(i).get(2);
            if (in_CC1 == 0) {
                break;
            }
            count_CC1 += in_CC1;
            num ++;
        }
        if (num == inNetLen) {
            CC1 = count_CC1 + 1;
        }
    }

    private void TNBUFFforCC(List<List<Object>> gatePacket) {
        //找到A\EN输入分别在哪个列表
        int orderA = -1;
        int orderEN = -1;
        if (((String)gatePacket.get(1).get(0)).equals("IN1")) {
            orderA = 1;
            orderEN = 2;
        }else {
            orderEN = 1;
            orderA = 2;
        }

        //计算CC0的值
        int inA_CC0 = (int)gatePacket.get(orderA).get(1);
        int inA_CC1 = (int)gatePacket.get(orderA).get(2);
        int inEN_CC1 = (int)gatePacket.get(orderEN).get(2);
        if (inA_CC0 != 0 && inEN_CC1 != 0) {
            CC0 = inA_CC0 + inEN_CC1 + 1;
        }

        //计算CC1的值
        if (inA_CC1 != 0 && inEN_CC1 != 0) {
            CC1 = inA_CC1 + inEN_CC1 + 1;
        }
    }

    private void NUBFFforCC(List<List<Object>> gatePacket) {
        //计算CC0的值
        int in_CC0 = (int)gatePacket.get(1).get(1);
        if (in_CC0 != 0) {
            CC0 = in_CC0 + 1;
        }

        //计算CC1的值
        int in_CC1 = (int)gatePacket.get(1).get(2);
        if (in_CC1 != 0) {
            CC1 = in_CC1 + 1;
        }
    }

    private void IBUFFforCC(List<List<Object>> gatePacket) {

        INVforCC(gatePacket);
    }

    private void INVforCC(List<List<Object>> gatePacket) {
        //计算CC0的值
        int in_CC1 = (int)gatePacket.get(1).get(2);
        if (in_CC1 != 0) {
            CC0 = in_CC1 + 1;
        }

        //计算CC1的值
        int in_CC0 = (int)gatePacket.get(1).get(1);
        if (in_CC0 != 0) {
            CC1 = in_CC0 + 1;
        }
    }

    private void SDFFASRforCC(List<List<Object>> gatePacket) {

        int indexD = -1;
        int indexCLK = -1;
        int indexSE = -1;
        int indexSI = -1;
        int indexSETB = -1;
        int indexRSTB = -1;

        for (int i = 1; i < gatePacket.size(); i++) {
            String pin = (String) gatePacket.get(i).get(0);
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

        int indexCLK_CC0 = (int)gatePacket.get(indexCLK).get(1);
        int indexCLK_CC1 = (int)gatePacket.get(indexCLK).get(2);

        int indexSETB_CC0 = (int)gatePacket.get(indexSETB).get(1);
        int indexSETB_CC1 = (int)gatePacket.get(indexSETB).get(2);

        int indexRSTB_CC0 = (int)gatePacket.get(indexRSTB).get(1);
        int indexRSTB_CC1 = (int)gatePacket.get(indexRSTB).get(2);

        int indexSE_CC0 = (int)gatePacket.get(indexSE).get(1);
        int indexSE_CC1 = (int)gatePacket.get(indexSE).get(2);

        int indexSI_CC0 = (int)gatePacket.get(indexSI).get(1);
        int indexSI_CC1 = (int)gatePacket.get(indexSI).get(2);

        int indexD_CC0 = (int)gatePacket.get(indexD).get(1);
        int indexD_CC1 = (int)gatePacket.get(indexD).get(2);

        List<Integer> Q_CC0 = new ArrayList<>();
        List<Integer> Q_CC1 = new ArrayList<>();

        if (indexRSTB_CC1 != 0 && indexCLK_CC0 != 0) {
            int value1 = indexRSTB_CC1 + indexCLK_CC0;
            if (indexSETB_CC0 != 0) {
                Q_CC1.add(value1 + indexSETB_CC0);
            }
            if (indexSETB_CC1 != 0 && indexCLK_CC1 != 0) {
                int value2 = value1 + indexSETB_CC1 + indexCLK_CC1;
                if (indexSE_CC0 != 0 && indexD_CC1 != 0) {
                    Q_CC1.add(value2 + indexSE_CC0 + indexD_CC1);
                }
                if (indexSE_CC1 != 0 && indexSI_CC1 != 0) {
                    Q_CC1.add(value2 + indexSE_CC1 + indexSI_CC1);
                }
            }
        }

        if (indexSETB_CC1 != 0 && indexCLK_CC0 != 0) {
            int value1 = indexSETB_CC1 + indexCLK_CC0;
            if (indexRSTB_CC0 != 0) {
                Q_CC0.add(value1 + indexRSTB_CC0);
            }
            if (indexRSTB_CC1 != 0 && indexCLK_CC1 != 0) {
                int value2 = value1 + indexRSTB_CC1 + indexCLK_CC1;
                if (indexSE_CC0 != 0 && indexD_CC0 != 0) {
                    Q_CC0.add(value2 + indexSE_CC0 + indexD_CC0);
                }
                if (indexSE_CC1 != 0 && indexSI_CC0 != 0) {
                    Q_CC0.add(value2 + indexSE_CC1 + indexSI_CC0);
                }
            }
        }

        switch ((String)gatePacket.get(0).get(1)) {
            case "Q":
                //计算CC0
                if (Q_CC0.size() != 0) {
                    CC0 = Collections.min(Q_CC0);
                }
                //计算CC1
                if (Q_CC1.size() != 0) {
                    CC1 = Collections.min(Q_CC1);
                }
                break;

            case "QN":
                //计算CC1
                if (Q_CC0.size() != 0) {
                    CC1 = Collections.min(Q_CC0);
                }
                //计算CC0
                if (Q_CC1.size() != 0) {
                    CC0 = Collections.min(Q_CC1);
                }
                break;

            default:
                break;
        }

    }

    private void RDFFNforCC(List<List<Object>> gatePacket) {

        int indexD = -1;
        int indexCLK = -1;
        int indexRETN = -1;

        for (int i = 1; i < gatePacket.size(); i++) {
            if (gatePacket.get(i).get(0).equals("D")) {
                indexD = i;
            }else if (gatePacket.get(i).get(0).equals("CLK")) {
                indexCLK = i;
            }else {
                indexRETN = i;
            }
        }

        int indexCLK_CC0 = (int)gatePacket.get(indexCLK).get(1);
        int indexCLK_CC1 =(int)gatePacket.get(indexCLK).get(2);

        int indexD_CC0 = (int)gatePacket.get(indexD).get(1);
        int indexD_CC1 = (int)gatePacket.get(indexD).get(2);

//		int indexRETN_CC0 = (int)gatePacket.get(indexRETN).get(1);
        int indexRETN_CC1 = (int)gatePacket.get(indexRETN).get(2);

        boolean CLKstate = (indexCLK_CC0 != 0) && (indexCLK_CC1 != 0) && (indexRETN_CC1 != 0);
        int countCLK = indexCLK_CC0 + indexCLK_CC1 + indexRETN_CC1;

        switch ((String)gatePacket.get(0).get(1)) {
            case "Q":
                //计算CC0
                if (CLKstate && indexD_CC0 != 0) {
                    CC0 = indexD_CC0 + countCLK;
                }
                //计算CC1
                if (CLKstate && indexD_CC1 != 0) {
                    CC1 = indexD_CC1 + countCLK;
                }
                break;

            case "QN":
                //计算CC1
                if (CLKstate && indexD_CC0 != 0) {
                    CC1 = indexD_CC0 + countCLK;
                }
                //计算CC0
                if (CLKstate && indexD_CC1 != 0) {
                    CC0 = indexD_CC1 + countCLK;
                }
                break;

            default:
                break;
        }

    }

    private void SDFFASforCC(List<List<Object>> gatePacket) {
        int indexD = -1;
        int indexCLK = -1;
        int indexSETB = -1;
        int indexSE = -1;
        int indexSI = -1;

        for (int i = 1; i < gatePacket.size(); i++) {
            if (gatePacket.get(i).get(0).equals("D")) {
                indexD = i;
            }else if (gatePacket.get(i).get(0).equals("CLK")) {
                indexCLK = i;
            }else if (gatePacket.get(i).get(0).equals("SE")) {
                indexSE = i;
            }else if (gatePacket.get(i).get(0).equals("SI")) {
                indexSI = i;
            }else {
                indexSETB = i;
            }
        }

        int indexCLK_CC0 = (int)gatePacket.get(indexCLK).get(1);
        int indexCLK_CC1 = (int)gatePacket.get(indexCLK).get(2);

        int indexD_CC0 = (int)gatePacket.get(indexD).get(1);
        int indexD_CC1 = (int)gatePacket.get(indexD).get(2);

        int indexSETB_CC0 = (int)gatePacket.get(indexSETB).get(1);
        int indexSETB_CC1 = (int)gatePacket.get(indexSETB).get(2);

        int indexSE_CC0 = (int)gatePacket.get(indexSE).get(1);
        int indexSE_CC1 = (int)gatePacket.get(indexSE).get(2);

        int indexSI_CC0 = (int)gatePacket.get(indexSI).get(1);
        int indexSI_CC1 = (int)gatePacket.get(indexSI).get(2);

        int index;
        List<Integer> CC0_Q = new ArrayList<>();
        List<Integer> CC1_Q = new ArrayList<>();

        boolean CLKstate = (indexCLK_CC0 != 0) && (indexCLK_CC1 != 0);
        int countclk = indexCLK_CC0 + indexCLK_CC1;

        //计算Q的CC1
        if (indexCLK_CC0 != 0 && indexSETB_CC0 != 0) {
            index = indexSETB_CC0 + indexCLK_CC0;
            CC1_Q.add(index);
        }
        if (CLKstate && indexSETB_CC1 != 0) {

            // 计算Q的CC0
            if (indexD_CC0 != 0 && indexSE_CC0 != 0) {
                index = indexSETB_CC1 + indexD_CC0 + indexSE_CC0 + countclk;
                CC0_Q.add(index);
            }
            if (indexSI_CC0 != 0 && indexSE_CC1 != 0) {
                index = indexSETB_CC1 + indexSI_CC0 + indexSE_CC1 + countclk;
                CC0_Q.add(index);
            }

            //计算Q的CC1
            if (indexD_CC1 != 0 && indexSE_CC0 != 0) {
                index = indexD_CC1 + indexSE_CC0 + indexSETB_CC1 + countclk;
                CC1_Q.add(index);
            }
            if (indexSI_CC1 != 0 && indexSE_CC1 != 0) {
                index = indexSI_CC1 + indexSE_CC1 + indexSETB_CC1 + countclk;
                CC1_Q.add(index);
            }
        }


        switch ((String)gatePacket.get(0).get(1)) {
            case "Q":
                //CC0
                if (CC0_Q.size() != 0) {
                    CC0 = Collections.min(CC0_Q);
                }
                //CC1
                if (CC1_Q.size() != 0) {
                    CC1 = Collections.min(CC1_Q);
                }
                break;

            case "QN":
                //CC0
                if (CC1_Q.size() != 0) {
                    CC0 = Collections.min(CC1_Q);
                }
                //CC1
                if (CC0_Q.size() != 0) {
                    CC1 = Collections.min(CC0_Q);
                }

            default:
                break;
        }

    }

    private void SDFFARforCC(List<List<Object>> gatePacket) {
        int indexD = -1;
        int indexCLK = -1;
        int indexRSTB = -1;
        int indexSE = -1;
        int indexSI = -1;

        for (int i = 1; i < gatePacket.size(); i++) {
            if (gatePacket.get(i).get(0).equals("D")) {
                indexD = i;
            }else if (gatePacket.get(i).get(0).equals("CLK")) {
                indexCLK = i;
            }else if (gatePacket.get(i).get(0).equals("SE")) {
                indexSE = i;
            }else if (gatePacket.get(i).get(0).equals("SI")) {
                indexSI = i;
            }else {
                indexRSTB = i;
            }
        }

        int indexCLK_CC0 = (int)gatePacket.get(indexCLK).get(1);
        int indexCLK_CC1 = (int)gatePacket.get(indexCLK).get(2);

        int indexD_CC0 = (int)gatePacket.get(indexD).get(1);
        int indexD_CC1 = (int)gatePacket.get(indexD).get(2);

        int indexRSTB_CC0 = (int)gatePacket.get(indexRSTB).get(1);
        int indexRSTB_CC1 = (int)gatePacket.get(indexRSTB).get(2);

        int indexSE_CC0 = (int)gatePacket.get(indexSE).get(1);
        int indexSE_CC1 = (int)gatePacket.get(indexSE).get(2);

        int indexSI_CC0 = (int)gatePacket.get(indexSI).get(1);
        int indexSI_CC1 = (int)gatePacket.get(indexSI).get(2);

        int index;
        List<Integer> CC0_Q = new ArrayList<>();
        List<Integer> CC1_Q = new ArrayList<>();

        boolean CLKstate = (indexCLK_CC0 != 0) && (indexCLK_CC1 != 0);
        int countclk = indexCLK_CC0 + indexCLK_CC1;

        //计算Q的CC0
        if (indexCLK_CC0 != 0 && indexRSTB_CC0 != 0) {
            index = indexRSTB_CC0 + indexCLK_CC0;
            CC0_Q.add(index);
        }
        if (CLKstate && indexRSTB_CC1 != 0) {
            if (indexD_CC0 != 0 && indexSE_CC0 != 0) {
                index = indexRSTB_CC1 + indexD_CC0 + indexSE_CC0 + countclk;
                CC0_Q.add(index);
            }
            if (indexSI_CC0 != 0 && indexSE_CC1 != 0) {
                index = indexRSTB_CC1 + indexSI_CC0 + indexSE_CC1 + countclk;
                CC0_Q.add(index);
            }

            //计算Q的CC1
            if (indexD_CC1 != 0 && indexSE_CC0 != 0) {
                index = indexD_CC1 + indexSE_CC0 + indexRSTB_CC1 + countclk;
                CC1_Q.add(index);
            }
            if (indexSI_CC1 != 0 && indexSE_CC1 != 0) {
                index = indexSI_CC1 + indexSE_CC1 + indexRSTB_CC1 + countclk;
                CC1_Q.add(index);
            }
        }


        switch ((String)gatePacket.get(0).get(1)) {
            case "Q":
                //CC0
                if (CC0_Q.size() != 0) {
                    CC0 = Collections.min(CC0_Q);
                }
                //CC1
                if (CC1_Q.size() != 0) {
                    CC1 = Collections.min(CC1_Q);
                }
                break;

            case "QN":
                //CC0
                if (CC1_Q.size() != 0) {
                    CC0 = Collections.min(CC1_Q);
                }
                //CC1
                if (CC0_Q.size() != 0) {
                    CC1 = Collections.min(CC0_Q);
                }

            default:
                break;
        }

    }

    private void SDFFforCC(List<List<Object>> gatePacket) {

        int index;
        int indexofD = -1;
        int indexofSI = -1;
        int indexofSE = -1;
        int indexofCLK = -1;

        for(int i = 1; i<gatePacket.size(); i++) {
            if (gatePacket.get(i).get(0).equals("D")) {
                indexofD = i;
            }else if (gatePacket.get(i).get(0).equals("SI")) {
                indexofSI = i;
            }else if (gatePacket.get(i).get(0).equals("SE")) {
                indexofSE = i;
            }else {
                indexofCLK = i;
            }
        }

        int indexD_CC0 = (int)gatePacket.get(indexofD).get(1);
        int indexD_CC1 = (int)gatePacket.get(indexofD).get(2);
        int indexCLK_CC0 = (int)gatePacket.get(indexofCLK).get(1);
        int indexCLK_CC1 = (int)gatePacket.get(indexofCLK).get(2);
        int indexSI_CC0 = (int)gatePacket.get(indexofSI).get(1);
        int indexSI_CC1 = (int)gatePacket.get(indexofSI).get(2);
        int indexSE_CC0 = (int)gatePacket.get(indexofSE).get(1);
        int indexSE_CC1 = (int)gatePacket.get(indexofSE).get(2);

        List<Integer> CC0_Q = new ArrayList<>();
        List<Integer> CC1_Q = new ArrayList<>();

        boolean CLKstate = (indexCLK_CC1 != 0 && indexCLK_CC0 != 0);
        int countclk = indexCLK_CC0 + indexCLK_CC1;

        if (CLKstate && indexSE_CC0 != 0) {
            if (indexD_CC0 != 0) {
                index = indexD_CC0 + indexSE_CC0 + countclk;
                CC0_Q.add(index);
            }
            if (indexD_CC1 != 0) {
                index = indexD_CC1 + indexSE_CC0 + countclk;
                CC1_Q.add(index);
            }
        }

        if (CLKstate && indexSE_CC1 != 0) {
            if (indexSI_CC0 != 0) {
                index = indexSI_CC0 + indexSE_CC1 + countclk;
                CC0_Q.add(index);
            }
            if (indexSI_CC1 != 0) {
                index = indexSI_CC1 + indexSE_CC1 + countclk;
                CC1_Q.add(index);
            }
        }

        switch ((String)gatePacket.get(0).get(1)) {
            case "Q":
                //CC0
                if (CC0_Q.size() != 0) {
                    CC0 = Collections.min(CC0_Q);
                }
                //CC1
                if (CC1_Q.size() != 0) {
                    CC1 = Collections.min(CC1_Q);
                }
                break;

            case "QN":
                //CC0
                if (CC1_Q.size() != 0) {
                    CC0 = Collections.min(CC1_Q);
                }
                //CC1
                if (CC0_Q.size() != 0) {
                    CC1 = Collections.min(CC0_Q);
                }

            default:
                break;
        }
    }

    private void DFFARforCC(List<List<Object>> gatePacket) {

        int indexD = -1;
        int indexCLK = -1;
        int indexRSTB = -1;

        for (int i = 1; i < gatePacket.size(); i++) {
            if (gatePacket.get(i).get(0).equals("D")) {
                indexD = i;
            }else if (gatePacket.get(i).get(0).equals("CLK")) {
                indexCLK = i;
            }else {
                indexRSTB = i;
            }
        }

        int indexCLK_CC0 = (int)gatePacket.get(indexCLK).get(1);
        int indexCLK_CC1 = (int)gatePacket.get(indexCLK).get(2);

        int indexD_CC0 = (int)gatePacket.get(indexD).get(1);
        int indexD_CC1 = (int)gatePacket.get(indexD).get(2);

        int indexRSTB_CC0 = (int)gatePacket.get(indexRSTB).get(1);
        int indexRSTB_CC1 = (int)gatePacket.get(indexRSTB).get(2);

        int index;
        List<Integer> CC0_Q = new ArrayList<>();
        List<Integer> CC1_Q = new ArrayList<>();

        boolean CLKstate = (indexCLK_CC0 != 0) && (indexCLK_CC1 != 0);
        int countclk = indexCLK_CC0 + indexCLK_CC1;

        //计算Q的CC0
        if (indexCLK_CC0 != 0 && indexRSTB_CC0 != 0) {
            index = indexRSTB_CC0 + indexCLK_CC0;
            CC0_Q.add(index);
        }
        if (CLKstate && indexRSTB_CC1 != 0 && indexD_CC0 != 0) {
            index = indexRSTB_CC1 + indexD_CC0 + countclk;
            CC0_Q.add(index);
        }

        //计算Q的CC1
        if (CLKstate && indexRSTB_CC1 != 0 && indexD_CC1 != 0) {
            index = indexD_CC1 +indexRSTB_CC1 + countclk;
            CC1_Q.add(index);
        }

        switch ((String)gatePacket.get(0).get(1)) {
            case "Q":
                //CC0
                if (CC0_Q.size() != 0) {
                    CC0 = Collections.min(CC0_Q);
                }
                //CC1
                if (CC1_Q.size() != 0) {
                    CC1 = Collections.min(CC1_Q);
                }
                break;

            case "QN":
                //CC0
                if (CC1_Q.size() != 0) {
                    CC0 = Collections.min(CC1_Q);
                }
                //CC1
                if (CC0_Q.size() != 0) {
                    CC1 = Collections.min(CC0_Q);
                }

            default:
                break;
        }

    }

    private void DFFASforCC(List<List<Object>> gatePacket) {

        int indexD = -1;
        int indexCLK = -1;
        int indexSETB = -1;

        for (int i = 1; i < gatePacket.size(); i++) {
            if (gatePacket.get(i).get(0).equals("D")) {
                indexD = i;
            }else if (gatePacket.get(i).get(0).equals("CLK")) {
                indexCLK = i;
            }else {
                indexSETB = i;
            }
        }

        int indexCLK_CC0 = (int)gatePacket.get(indexCLK).get(1);
        int indexCLK_CC1 = (int)gatePacket.get(indexCLK).get(2);

        int indexD_CC0 = (int)gatePacket.get(indexD).get(1);
        int indexD_CC1 = (int)gatePacket.get(indexD).get(2);

        int indexSETB_CC0 = (int)gatePacket.get(indexSETB).get(1);
        int indexSETB_CC1 = (int)gatePacket.get(indexSETB).get(2);

        int index;
        List<Integer> CC0_Q = new ArrayList<>();
        List<Integer> CC1_Q = new ArrayList<>();

        boolean CLKstate =((indexCLK_CC1 != 0) && (indexCLK_CC0 != 0));
        int countclk = indexCLK_CC1 + indexCLK_CC0;

        //计算Q的CC0
        if (CLKstate && indexSETB_CC1 != 0 && indexD_CC0 != 0) {
            index = indexSETB_CC1 + indexD_CC0 + countclk;
            CC0_Q.add(index);
        }
        //计算Q的CC1
        if (indexCLK_CC0 != 0 && indexSETB_CC0 != 0) {
            index = indexSETB_CC0 + indexCLK_CC0;
            CC1_Q.add(index);
        }
        if (CLKstate && indexSETB_CC1 != 0 && indexD_CC1 != 0) {
            index = indexSETB_CC1 + indexD_CC1 + countclk;
            CC1_Q.add(index);
        }

        switch ((String)gatePacket.get(0).get(1)) {
            case "Q":
                //CC0
                if (CC0_Q.size() != 0) {
                    CC0 = Collections.min(CC0_Q);
                }
                //CC1
                if (CC1_Q.size() != 0) {
                    CC1 = Collections.min(CC1_Q);
                }
                break;

            case "QN":
                //CC0
                if (CC1_Q.size() != 0) {
                    CC0 = Collections.min(CC1_Q);
                }
                //CC1
                if (CC0_Q.size() != 0) {
                    CC1 = Collections.min(CC0_Q);
                }

            default:
                break;
        }

    }

    private void DFFforCC(List<List<Object>> gatePacket) {

        int indexD = -1;
        int indexCLK = -1;

        for (int i = 1; i < gatePacket.size(); i++) {
            if (gatePacket.get(i).get(0).equals("D")) {
                indexD = i;
            }else {
                indexCLK = i;
            }
        }

        int indexCLK_CC0 = (int)gatePacket.get(indexCLK).get(1);
        int indexCLK_CC1 =(int)gatePacket.get(indexCLK).get(2);

        int indexD_CC0 = (int)gatePacket.get(indexD).get(1);
        int indexD_CC1 = (int)gatePacket.get(indexD).get(2);

        boolean CLKstate = (indexCLK_CC0 != 0) && (indexCLK_CC1 != 0);
        int countCLK = indexCLK_CC0 + indexCLK_CC1;

        switch ((String)gatePacket.get(0).get(1)) {
            case "Q":
                //计算CC0
                if (CLKstate && indexD_CC0 != 0) {
                    CC0 = indexD_CC0 + countCLK;
                }
                //计算CC1
                if (CLKstate && indexD_CC1 != 0) {
                    CC1 = indexD_CC1 + countCLK;
                }
                break;

            case "QN":
                //计算CC1
                if (CLKstate && indexD_CC0 != 0) {
                    CC1 = indexD_CC0 + countCLK;
                }
                //计算CC0
                if (CLKstate && indexD_CC1 != 0) {
                    CC0 = indexD_CC1 + countCLK;
                }
                break;

            default:
                break;
        }
    }
}

