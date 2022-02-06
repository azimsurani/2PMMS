package assignment;

public class MergeSort {
	
	public static void merge(int[] array,int left,int middle,int right) {
		int[] temp  = new int[right-left+1];
		
		int p= left;
		int q=middle+1;
		
		for(int i=0;i<temp.length;i++) {
			if((p<=middle && q<=right && array[p]<array[q]) || (q>right)) {
				temp[i]=array[p];
				p++;
			}else{
				temp[i]=array[q];
				q++;
			} 
		}
		
		for(int i=0;i<temp.length;i++) {
			array[left++]=temp[i];
		}
				
	}
	
	public static void mergeSort(int[] array,int left,int right) {
		
		if(right>left) {
			int middle = left + (right-left)/2;
			mergeSort(array,left,middle);
			mergeSort(array,middle+1,right);
			merge(array,left,middle,right);
		}
		
	}
	
	public static void printMyArray(int[] array) {
		
		for (int i = 0; i < array.length; i++) {
			System.out.println(array[i]);
		}
		
	}
	
	public static void main(String[] args) {
		int[] array = {1,2,3,4,5,6,3,1,4};
		
		mergeSort(array,0,array.length-1);
		
		printMyArray(array);
		
	}

}
