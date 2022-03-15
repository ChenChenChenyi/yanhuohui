package com.chenyi.yanhuohui.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


class Solution {
    public static void main(String[] args) {
        int i = lengthOfLongestSubstring("pwwkew");
    }

    public static int lengthOfLongestSubstring(String s) {
        Map<Character,Integer> map = new HashMap<>();
        int i = 0;
        int max = 0;
        while(i<s.length()-1){
            for(int j=i;j<s.length();j++){
                if(map.containsKey(s.charAt(j))){
                    i = map.get(s.charAt(j))+1;
                    break;
                }else{
                    map.put(s.charAt(j),j);
                }
                i = j;
            }
            int size = map.keySet().size();
            if(max<size){
                max = size;
            }
            map.clear();
        }
        return max;
    }
}
