/**
 * @author Arkady Koplyarov (arkad.k@gmail.com)
 */

package com.actionnodes.pokerhandcomparator.test;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * MyTestRunner
 */
public class MyTestRunner
{
  public static void main(String[] args)
  {
    Result result1 = JUnitCore.runClasses(PokerHandFewTests.class);
    for (Failure failure : result1.getFailures()) {
      System.out.println(failure.toString());
    }
    
    Result result2 = JUnitCore.runClasses(PokerHandComprehensiveTests.class);
    for (Failure failure : result2.getFailures()) {
      System.out.println(failure.toString());
    }
    
  }
} 