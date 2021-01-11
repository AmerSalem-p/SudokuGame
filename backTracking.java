package Project4;

public class backTracking {

	public boolean isSafeRow(int[][] arr, int row, int num) {

		for (int i = 0; i < arr.length; i++)
			if (arr[row][i] == num)
				return false;
		return true;
	}

	public boolean isSafeColumn(int[][] arr, int column, int num) {

		for (int i = 0; i < arr.length; i++)
			if (arr[i][column] == num)
				return false;
		return true;
	}

	public boolean isSafeSquare(int[][] arr, int row, int column, int num) {

		row = row - row % 3;
		column = column - column % 3;

		for (int i = row; i < row + 3; i++)
			for (int j = column; j < column + 3; j++)
				if (arr[i][j] == num)
					return false;
		return true;

	}

	public boolean isSafe(int[][] arr, int row, int column, int num) {

		if (isSafeRow(arr, row, num) == true && isSafeColumn(arr, column, num) == true
				&& isSafeSquare(arr, row, column, num) == true)
			return true;
		return false;
	}

	public boolean isEmpty(int[][] arr, int pos[]) {

		for (int i = 0; i < arr.length; i++)
			for (int j = 0; j < arr.length; j++)
				if (arr[i][j] == 0) {
					pos[0] = i;
					pos[1] = j;
					return true;
				}

		return false;

	}

	public boolean solveSudoku(int[][] arr) {

		int pos[] = new int[2];
		int row = 0;
		int column = 0;

		if (!isEmpty(arr, pos))
			return true;

		else {
			row = pos[0];
			column = pos[1];
		}
		for (int num = 1 ; num <= 9; num++)
			if (isSafe(arr, row, column, num)) {
				arr[row][column] = num;

				if (solveSudoku(arr))
					return true;
				
				arr[row][column] = 0;

			}

		return false;

	}

}
