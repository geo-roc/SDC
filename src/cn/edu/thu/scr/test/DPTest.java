package cn.edu.thu.scr.test;

import cn.edu.thu.scr.DP;
import cn.edu.thu.scr.entity.TimeSeries;
import cn.edu.thu.scr.util.Assist;

/**
 * Created by Stoke on 2017/10/8.
 * E-mail address is zaqthss2009@gmail.com
 * Copyright © Stoke. All Rights Reserved.
 *
 * @author Stoke
 */
public class DPTest {

  public static void repair(String inputFileName) {
	Assist assist = new Assist();
    String splitOp = ",";

    TimeSeries dirtySeries = assist.readData(inputFileName, 1, splitOp);
    TimeSeries truthSeries = assist.readData(inputFileName, 2, splitOp);

    double rmsDirty = assist.calcRMS(truthSeries, dirtySeries);
    System.out.println("Repair " + inputFileName);
    System.out.println("Dirty RMS error is " + rmsDirty);

    double RES = 0.1;     // the resolution of the data
    int PARAM = 10;       // RES * PARAM = 1, the normalized parameter
    double THETA = 5;     // after normalized
    
    double delta = 1500;

    assist.buildVModel();
    DP dp = new DP(dirtySeries, THETA, delta);
    dp.normalizeParams(RES, PARAM);
    dp.normalizeProbability(assist);
    TimeSeries resultSeries = dp.mainDP();
    assist.writeResult(inputFileName+".res", truthSeries, resultSeries);
    double rms = assist.calcRMS(truthSeries, resultSeries);

    System.out.println("Repair RMS error is " + rms);
  }
  public static void main(String[] args) {
    String inputFileName = "stock1.2k.data";
//    String inputFileName = "stock10k.data"; may be out of memory under 10G
    int num = 11;
    for (int i = 0; i<num;i++) {
    	repair(Integer.toString(i)+".datax");
    	repair(Integer.toString(i)+".datay");
    	
    }
  }
}
