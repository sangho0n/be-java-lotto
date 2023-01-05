package kr.codesquad.utility;

import kr.codesquad.customException.AlreadyHasSameNumberException;
import kr.codesquad.customException.OutOfRangeException;

import java.rmi.AlreadyBoundException;
import java.util.ArrayList;

public class Util {

    public static ArrayList<Integer> splitTo6Integers(String beforeSplited)
    {
        String splitedNums[] = beforeSplited.split(", ");
        ArrayList<Integer> afterSplited = new ArrayList<Integer>();
        for(int i = 0; i < 6; i++)
        {
            afterSplited.add(Integer.parseInt(splitedNums[i]));
        }
        return afterSplited;
    }

    public static void bonusValidityCheck(String lastWinNum, int bonus)
    {
        ArrayList<Integer> splitedLastWinNum = splitTo6Integers(lastWinNum);
        if(splitedLastWinNum.contains(bonus))
            throw new AlreadyHasSameNumberException("이미 같은 수의 공이 뽑혔습니다. 다른 번호를 입력하세요");
    }

}
