// Given a boolean expression with the following symbols. 
// Symbols
//     'T' ---> true 
//     'F' ---> false 

import java.io.*;
import java.util.*;

class BooleanParenthesization {
	public static int countWays(int N, String S) {
		int dp[][][] = new int[N + 1][N + 1][2];

		for (int row[][] : dp)
			for (int col[] : row)
				Arrays.fill(col, -1);
		return parenthesis_count(S, 0, N - 1, 1, dp);
	}

	public static int parenthesis_count(String str, int i, int j, int isTrue, int[][][] dp) {
		if (i > j)
			return 0;

		if (i == j) {
			if (isTrue == 1) {
				return (str.charAt(i) == 'T') ? 1 : 0;
			}
			else {
				return (str.charAt(i) == 'F') ? 1 : 0;
			}
		}

		if (dp[i][j][isTrue] != -1)
			return dp[i][j][isTrue];

		int temp_ans = 0;

		int leftTrue, rightTrue, leftFalse, rightFalse;

		for (int k = i + 1; k <= j - 1; k = k + 2) {

			if (dp[i][k - 1][1] != -1)
				leftTrue = dp[i][k - 1][1];
			else {
				leftTrue = parenthesis_count(str, i, k - 1, 1, dp);
			}
			if (dp[i][k - 1][0] != -1)
				leftFalse = dp[i][k - 1][0];
			else {
				leftFalse = parenthesis_count(str, i, k - 1, 0, dp);
			}
			if (dp[k + 1][j][1] != -1)
				rightTrue = dp[k + 1][j][1];
			else {
				rightTrue = parenthesis_count(str, k + 1, j, 1, dp);
			}
			if (dp[k + 1][j][0] != -1)
				rightFalse = dp[k + 1][j][0];
			else {
				rightFalse = parenthesis_count(str, k + 1, j, 0, dp);
			}
			if (str.charAt(k) == '&') {
				if (isTrue == 1) {
					temp_ans = temp_ans + leftTrue * rightTrue;
				}
				else {
					temp_ans = temp_ans + leftTrue * rightFalse + leftFalse * rightTrue + leftFalse * rightFalse;
				}
			}
			else if (str.charAt(k) == '|') {
				if (isTrue == 1) {
					temp_ans = temp_ans + leftTrue * rightTrue + leftTrue * rightFalse + leftFalse * rightTrue;
				}
				else {
					temp_ans = temp_ans + leftFalse * rightFalse;
				}
			}
		
			else if (str.charAt(k) == '^') {
				if (isTrue == 1) {
					temp_ans = temp_ans + leftTrue * rightFalse + leftFalse * rightTrue;
				}
				else {
					temp_ans = temp_ans + leftTrue * rightTrue + leftFalse * rightFalse;
				}
			}
			dp[i][j][isTrue] = temp_ans;
		}
		return temp_ans;
	}
	public static void main(String[] args) {
		String symbols = "TTFT";
		String operators = "|&^";
		StringBuilder S = new StringBuilder();
		int j = 0;

		for (int i = 0; i < symbols.length(); i++) {
			S.append(symbols.charAt(i));
			if (j < operators.length())
				S.append(operators.charAt(j++));
		}
		int N = S.length();
		System.out.println(countWays(N, S.toString()));
	}
}

