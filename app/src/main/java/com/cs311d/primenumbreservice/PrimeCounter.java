package com.cs311d.primenumbreservice;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adliano on 4/19/16.
 *
 */

public class PrimeCounter
{
    /******* isPrime ********/
    public static boolean isPrime(int number)
    {
        for(int i = 2; i <= Math.sqrt(number); i++)
        {
            if(number%i==0) return false;
        }
        return true;
    }
    /************** getPrimes *************/
    public static Integer[] getPrimes(int from, int to)
    {
        List<Integer> list = new ArrayList<>();

        for(int n = from; n<=to; n++)
        {
            if(isPrime(n)) list.add(n);
        }
        return (list.toArray(new Integer[list.size()]));
    }
    /*********** getTotalOfPrimes *********/
    public static int getTotalOfPrimes(int from , int to)
    {
        return (getPrimes(from,to).length);
    }
}
