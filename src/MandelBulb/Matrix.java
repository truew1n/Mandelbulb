package MandelBulb;

public class Matrix {
	
	public static double[][] multiplyMatrix(double[][] matrix0, double[][] matrix1) {
        int row0 = matrix0.length;
        int col0 = matrix0[0].length;
        int row1 = matrix1.length;
        int col1 = matrix1[0].length;
        if (row1 != col0) {
            return null;
        }
        double [][]C = new double[row0][col1];
 
        for (int i = 0; i < row0; i++) {
            for (int j = 0; j < col1; j++) {
                for (int k = 0; k < row1; k++)
                    C[i][j] += matrix0[i][k] * matrix1[k][j];
            }
        }
 
        return C;
    }
	
	public static void printMatrix(double[][] matrix) {
		for(int i = 0; i < matrix.length; ++i) {
			for(int j = 0; j < matrix[i].length; ++j) {
				System.out.print(matrix[i][j]);
			}
			System.out.println();
		}
	}
}
