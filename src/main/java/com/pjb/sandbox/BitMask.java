package com.pjb.sandbox;

public class BitMask {

	public static void main(String[] args) {
		int num_stores = 10;
		int mattress = 12;
		int mask = 1;
		for(int i = 0; i < num_stores; ++i) {
		    if((mask & mattress) != 0) {
		    	System.out.println("mask :"  + mask);
		        System.out.println("Store "+i+" has mattresses!");
		    } 
		    mask = mask << 1;
		    //System.out.println("mask :"  + mask);
		}
	}

}
