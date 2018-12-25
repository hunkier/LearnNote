package cn.hunkier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Test {
    public static void main(String[] args) {


        System.out.println(Math.pow(10,0));
        System.out.println(getNarcissisticNumbers(0));
        System.out.println(getNarcissisticNumbers(1));
        System.out.println(getNarcissisticNumbers(2));
        System.out.println(getNarcissisticNumbers(3));
    }

    public static void rotateString(char[] str, int offset) {
        // write your code here
        if(str==null || str.length==0){
            return;
        }
        offset = offset % str.length;
        for (int i = 0; i < offset; i++) {
            char temp = str[str.length-1];
            for (int j = str.length-1; j >0 ; j--) {
                str[j]=str[j-1];
            }
            str[0] = temp;
        }

    }


    public static List<Integer> getNarcissisticNumbers(int n) {
        // write your code here
        List<Integer> result = new ArrayList<>();
        int max = new Double(Math.pow(10,n)).intValue();
        int start = new Double(Math.pow(10,n-1)).intValue();
        start = start==1?0 :start;
        for (int i = start ; i < max; i ++){
            List<Integer> ss = new ArrayList<>();
            int sss = i;
            while (sss>0){
                ss.add(sss%10);
                sss=sss/10;
            }
            double r = 0 ;
            for (Integer s : ss) {
                r += Math.pow(s,n);
            }
            if (i == new Double(r).intValue()){
                result.add(i);
            }
        }

        return result;

    }
}
