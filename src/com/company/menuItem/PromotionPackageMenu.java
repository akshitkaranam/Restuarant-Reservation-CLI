package com.company.menuItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This is the class that contains a static list of all the PromotionPackage Objects that are added
 */

public class PromotionPackageMenu {
	
	private static ArrayList <PromotionPackage> packageList = new ArrayList<>();
	Scanner sc = new Scanner(System.in);

	
	public PromotionPackageMenu(){
		packageList= new ArrayList<>();
	}

	/**
	 * add a new Promotion Package to the static list
	 */
	public void addPromotionPackage() {
		
		packageList.add(new PromotionPackage());
		packageList.get(packageList.size()-1).createPackage();
		
	}

	/**
	 * display the promotion package
	 */
	public void displayPackageMenu() {
		for(int i =0; i<packageList.size(); i++) {
			System.out.println("Package Number " + (i+1));
			packageList.get(i).displayPackage();
		}
	}

	/**
	 * remove a given promotion package based on user inputs
	 */
	public void removePromotionPackage() {
		int i;
		for(i =0;i<packageList.size();i++){
			System.out.println(i + " " + packageList.get(i).getPackageName());
		}

		while(true){
			int innerChosenOption = sc.nextInt();
			if(innerChosenOption <= i-1 && innerChosenOption >= 0){
				packageList.remove(innerChosenOption);
				break;
			}else{
				System.out.println("Please enter a valid choice!");
			}
		}
	}

	/**
	 * update a given promotion package based on user inputs
	 */
	public void updatePromotionPackage() {
		int i;
		for(i =0;i<packageList.size();i++){
			System.out.println(i + " " + packageList.get(i).getPackageName());
		}

		while(true){
			int innerChosenOption = sc.nextInt();
			if(innerChosenOption <= i-1 && innerChosenOption >= 0){
				packageList.get(innerChosenOption).updatePackage();
				break;
			}else{
				System.out.println("Please enter a valid choice!");
			}
		}


	}

	public static ArrayList<PromotionPackage> getPackageList() {
		return packageList;
	}
}
