package service;

import domain.NetInfo;
import location.OListDG;
import location.VaryNormalMethod;
import util.NetListRead;
import util.recover.RecoverUtil;

import java.util.*;

/**
 * @author wen
 * @date 2019/11/29 0029-10:12
 */
public class ReConstruction {
   public Map<String, Object> constructionEnter(
           ArrayList<String> suSet, NetListRead netList, ArrayList<NetInfo> netInfos){

       OListDG oListDG = new OListDG(netList.vertexInfo, netList.edgesInfo);

       RecoverUtil recoverUtil = new RecoverUtil();
       /*
       1、找到木马触发单元和木马感染电路
        */
       Map<String, Object> rtnData = recoverUtil.findTrojan(oListDG, suSet);
       /*
       2、删除掉木马触发单元
        */
       recoverUtil.recover(oListDG.vertexNodeList, rtnData, netInfos);

       return rtnData;
   }

}
