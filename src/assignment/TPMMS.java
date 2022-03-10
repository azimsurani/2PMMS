package assignment;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class TPMMS {

	public static void twoPhaseMultiwayMergeSort(int n) {
		
		if(n==0) {
			System.out.println("Please generate the input file first.");
			return;
		}
		
		long startTime = System.currentTimeMillis();

		phase1(n);
		phase2(n);
		
		long endTime = System.currentTimeMillis();
		
		long totalTime = endTime - startTime;
		
		System.out.println("Total time taken : " + totalTime +" milliseconds\n");

	}

	private static void phase1(int n) {

		int memorySize = Constants.MEMORY_SIZE;

		int integerSize = 4; // Size of Integer in JAVA -- 4 Bytes

		int numberOfBlocks = n * integerSize / memorySize + ((n * integerSize / memorySize == 0) ? 0 : 1);
		
		System.out.println("Total number of Blocks : "+numberOfBlocks);

		int numberOfIntegers = memorySize/integerSize;

		try {

			Scanner fr = new Scanner(new FileInputStream("input.txt"));

			System.out.println("\n----------Phase1 (Using MergeSort)---------\n");
			
			int count=0;


			for(int i=1;i<numberOfBlocks;i++) {

				System.out.println("Proccesing Block : "+i);

				int arr[] = new int[numberOfIntegers];
				
				System.out.println("\nBefore Sorting : ");

				for(int j=0;j<arr.length;j++) {
					arr[j] = fr.nextInt();
					Logger.Log(arr[j]+" ");
					count++;
				}

				MergeSort.mergeSort(arr, 0, arr.length-1);

				PrintWriter pw = new PrintWriter(new FileOutputStream("sorted-"+i+".txt"));
				
				System.out.println("\n\nAfter Sorting : ");

				for(int j=0;j<arr.length;j++) {
					Logger.Log(arr[j]+" ");
					pw.println(arr[j]);
				}

				pw.close();		

				System.out.println("\n\nsorted-"+i+".txt was created.\n");

			}

			System.out.println("Proccesing Block : "+numberOfBlocks);

			int arr[] = new int[n-count];

			for(int j=0;j<arr.length;j++) {
				arr[j] = fr.nextInt();
				count++;
			}

			MergeSort.mergeSort(arr, 0, arr.length-1);

			PrintWriter pw = new PrintWriter(new FileOutputStream("sorted-"+numberOfBlocks+".txt"));

			for(int j=0;j<arr.length;j++) {
				Logger.Log(arr[j]+" ");
				pw.println(arr[j]);
			}

			pw.close();

			fr.close();

			System.out.println("\nsorted-"+numberOfBlocks+".txt was created.\n");

		} catch (FileNotFoundException e) {
			System.out.println("Input file not found!");
			System.exit(1);
		}

		System.out.println("\nPhase 1 was completed successfully");

	}

	private static void phase2(int n) {

		System.out.println("\n----------Phase2---------\n");

		int memorySize = Constants.MEMORY_SIZE;

		int integerSize = 4; // Size of Integer in JAVA -- 4 Bytes

		int numberOfBlocks = n * integerSize / memorySize + ((n * integerSize / memorySize == 0) ? 0 : 1);

		int numberOfIntegers = memorySize/integerSize;

		if(numberOfBlocks==0) {
			
			copyFile("sorted-0.txt","output.txt");
			
			System.out.println("Copying the contents from sorted-0.txt to output.txt");
			
			System.out.println("output.txt was created.");
			
			System.out.println("Total 1 pass was needed.");
			
		}else {

			for(int i=1;i<numberOfBlocks;i++) {

				String file1="combined-"+(i)+".txt";
				String file2="sorted-"+(i+1)+".txt";
				String targetFile="combined-"+(i+1)+".txt";

				if(i==1) {
					file1= "sorted-"+(i)+".txt";	
				}

				if(i==numberOfBlocks-1) {
					targetFile = "output.txt";
				}

				combineTwoFiles(file1, file2, targetFile, numberOfIntegers);

			}
			
			System.out.println("Total : "+(numberOfBlocks-1)+" passes were needed.");
			
		}
		
		

		System.out.println("\nPhase 2 was completed successfully\n");

	}
	
	private static void copyFile(String fromFile,String toFile) {
		
		try {
			
			Scanner sc = new Scanner(new FileInputStream(fromFile));
			PrintWriter pw = new PrintWriter(new FileOutputStream(toFile));
			
			while(sc.hasNextInt()) {
				pw.println(sc.nextInt());
			}
			
			sc.close();
			pw.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	private static void combineTwoFiles(String file1,String file2,String targetFile,int numberOfIntegers) {

		System.out.println("Combining "+file1+" and "+file2+" .");
		
		System.out.println("\nAfter combining the contents of file are : ");


		Queue queue1 = new Queue(numberOfIntegers/2);
		Queue queue2 = new Queue(numberOfIntegers/2);

		try {

			Scanner fs1 = new Scanner(new FileInputStream(file1));
			Scanner fs2 = new Scanner(new FileInputStream(file2));

			PrintWriter pw = new PrintWriter(new FileOutputStream(targetFile));

			while(!queue1.isFull() && fs1.hasNextInt()) {
				queue1.enqueue(fs1.nextInt());
			}

			while(!queue2.isFull() && fs2.hasNextInt()) {
				queue2.enqueue(fs2.nextInt());
			}

			int flag=0;

			while(true) {

				if(flag==1) {

					if(fs1.hasNextInt()) {
						queue1.enqueue(fs1.nextInt());
					}
					else if(queue1.isEmpty()) {

						while(!queue2.isEmpty()) {
							pw.println(queue2.dequeue());
						}

						while(fs2.hasNextInt()) {
							pw.println(fs2.nextInt());
						}

						break;
					}
				}

				if(flag==2) {

					if(fs2.hasNextInt()) {
						queue2.enqueue(fs2.nextInt());
					}

					else if(queue2.isEmpty()) {

						while(!queue1.isEmpty()) {
							pw.println(queue1.dequeue());
						}

						while(fs1.hasNextInt()) {
							pw.println(fs1.nextInt());
						}

						break;
					}
				}


				if(queue1.front()<queue2.front()) {
					int front = queue1.dequeue();
					Logger.Log(front+" ");
					pw.println(front);
					flag=1;
				}else {
					int front = queue2.dequeue();
					Logger.Log(front+" ");
					pw.println(front);
					flag=2;
				}
			}

			fs1.close();
			fs2.close();
			pw.close();

			System.out.println("\n\n"+targetFile+" was created.\n");


		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
