package com.company.menuItem;

import java.util.ArrayList;
import java.util.Scanner;

public class PromotionPackageMenu {
	
	private static ArrayList <PromotionPackage> packageList;
	Scanner sc = new Scanner(System.in);

	
	public PromotionPackageMenu(){
		
		packageList= new ArrayList<PromotionPackage>();
	}
	
	public void addPromotionPackage() {
		
		packageList.add(new PromotionPackage());
		packageList.get(packageList.size()-1).createPackage();
		
	}
	public void displayPackageMenu() {
		
		for(int i =0; i<packageList.size(); i++) {
			System.out.println("Package Number " + (i+1));
			packageList.get(i).displayPackage();
		}
		
		
	}
	
	public void removePromotionPackage() {
		System.out.println("Enter Package Number to delete");
		int i = sc.nextInt();
		packageList.remove(i-1);
		
	}
	
	public void updatePromotionPackage() {
		System.out.println("Enter Package Number to update");
		int i  =sc.nextInt();
		packageList.get(i-1).updatePackageItem();
		
	}
	
	public void addPromotionItem() {
		System.out.println("Enter Package Number to add item");
		int i = sc.nextInt();
		packageList.get(i-1).addPackageItem();
	}
	
	public void removePromotionItem() {
		System.out.println("Enter Package Number to add item");
		int i = sc.nextInt();
		packageList.get(i-1).removePackageItem();
	}


}
