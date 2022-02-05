package assignment;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Driver {
	
	private static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		
		System.out.println("**** Welcome to 2PMMS System ****\n");
		
		while(true) {
		
			System.out.println("1. Create a random list of integers");
			System.out.println("2. Display the random list");
			System.out.println("3. Run 2PMMS");
			System.out.println("4. Exit");
			
			System.out.print("\nPlease specify your choice: ");
			
			int choice = sc.nextInt();
			
			switch(choice) {
			
				case 1:	
						generateFile();
						break;
				default:
					sc.close();
					System.out.println("\n**** Thanks for using 2PMMS System ****");
					System.exit(0);
					
			}		
			
		}
	
	}

	private static void generateFile() {
		
		System.out.print("\nPlease enter number of integers that you would like to generate : ");
		
		int n = sc.nextInt();
		
		System.out.print("Please enter the minmum number : ");
		
		int min = sc.nextInt();
		
		System.out.print("Please enter the maximum number : ");
		
		int max = sc.nextInt();
		
		if(max<min) {
			System.out.println("Maximum number can't be smaller than minimum number. Please start again.");
			return;
		}
		
		try {
			
			PrintWriter pw = new PrintWriter(new FileOutputStream("input.txt"));
			
			for(int i=0;i<n;i++) {		
				int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
				pw.println(randomNum);
			}
			
			pw.close();
			
			System.out.println("\n----File was created successfully-----\n");
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Please try again");
		}
				
	}

}
