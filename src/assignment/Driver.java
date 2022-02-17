package assignment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Driver {

	private static Scanner sc = new Scanner(System.in);

	private static int n;

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
			case 2:
				displayFile();
				break;
			case 3:
				TPMMS.twoPhaseMultiwayMergeSort(n);
				break;
			default:
				deleteIntermediateFiles();
				sc.close();
				System.out.println("\n**** Thanks for using 2PMMS System ****");
				System.exit(0);

			}		

		}

	}

	private static void deleteIntermediateFiles() {

		int memorySize = 2 * 1024; // Default size set to 2 MB

		int integerSize = 4; // Size of Integer in JAVA -- 4 Bytes

		int numberOfBlocks = n * integerSize / memorySize + ((n * integerSize / memorySize == 0) ? 0 : 1);

		File file;

		for(int i=1;i<numberOfBlocks;i++) {
			file = new File("sorted-"+i+".txt");
			file.delete();
			file = new File("combined-"+(i+1)+".txt");
			file.delete();
		}

		file = new File("sorted-"+numberOfBlocks+".txt");
		file.delete();
		
		System.out.println("All the intermediate files were deleted.");

	}


	private static void displayFile() {

		if(n==0) {
			System.out.println("Please generate the file first.");
			return;
		}

		try {

			Scanner fr = new Scanner(new FileInputStream("input.txt"));

			System.out.println("\nThe randomly generated list is as follows: \n");

			while(fr.hasNextInt()) {
				System.out.print(fr.nextInt()+" ");
			}

			System.out.println("\n\n----File was read successfully-----\n");

			fr.close();

		} catch (FileNotFoundException e) {
			System.out.println("Please generate the file first.");
		}

	}

	private static void generateFile() {

		System.out.print("\nPlease enter number of integers that you would like to generate : ");

		n = sc.nextInt();

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

			System.out.println("\n----input.txt was created successfully-----\n");


		} catch (FileNotFoundException e) {
			System.out.println("Please try again");
		}

	}

}
