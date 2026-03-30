package helpClasses;

public class Transform2DArrays {

	public static void swapLeftRight(int[][] array) {
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array[i].length/2; j++) {
				int temp = array[i][j];
				array[i][j] =array[i][array[i].length-1-j];
				array[i][array[i].length-1-j] = temp;
			}
		}
	}
	
	public static void swapTopBottom(int[][] array) {
		for (int i = 0; i < array.length/2; i++) {
			for (int j = 0; j < array[i].length; j++) {
				int temp = array[i][j];
				array[i][j] = array[array.length-1-i][j];
				array[array.length-1-i][j] = temp;
			}
		}
	}
	
	public static void swapAll(int[][] array) {
		swapLeftRight(array);
		swapTopBottom(array);
	}
}