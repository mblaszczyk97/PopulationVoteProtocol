package glosowanie;

public class Population {
	static final char sub0='\u2080';
	static final char sub1='\u2081';
	static final char sub2='\u2082';
	static final char sub3='\u2083';
	static final char sub4='\u2084';
	static final char sub5='\u2085';
	static final char sub6='\u2086';
	static final char sub7='\u2087';
	static final char sub8='\u2088';
	static final char sub9='\u2089';
	
	public static char[] subs = {sub0, sub1, sub2, sub3, sub4, sub5, sub6, sub7, sub8, sub9};
	
	public final boolean DEBUG = true;
	
	private int N = 0;
	private double matrix[][];
	private int sum;
	
	public Population(int n) {
		super();
		N = n;
		
		//number of P's 
		int s = ((N+2)*(N+1))/2;
		sum = s;
		
		matrix = new double[s][s];
	}

	public void calculateP() {
		int row =0;
		for (int i = 0; i <= N; i++) {
			for (int j = 0; j <= N-i; j++) {
				calculateP(row,i,j);
				row++;
			}
		}
	}
	
	public void calculateP(int row, int y1, int n1) {
		double a1 = 0;
		double a2 = 0;
		double a3 = 0;
		double a4 = 0;
		
		double y = y1;
		double n = n1;
		double u = N-y1-n1;
		
		boolean plus = false;
	
		//calculate P
		if ((y>0) && (n>0)) {
			a1=-2*(y/N)*(n/(N-1));
			putP(row, y1-1, n1-1, a1);
		}
		if (y1<N) {
			a4=-2*(y/N)*(u/(N-1));
			putP(row, y1+1, n1, a4);
		}
		
		if (n1<N) {
			a3=-2*(n/N)*(u/(N-1));
			putP(row, y1, n1+1, a3);
		}
		
		a2=1-(y*y-y)/(N*N-N)-(n*n-n)/(N*N-N)-(u*u-u)/(N*N-N);
		if ((a2 == 0) && ((y1==0) || (y1>=N)))
			a2 = 1;
		putP(row, y1, n1, a2);
		
		if (!DEBUG) return;
		//prepare debug string
		StringBuffer sb = new StringBuffer(indexedStr(row+": P",y1,n1)+":\t");
		if (a1!=0) {
			sb.append(a1+indexedStr(" P",y1-1,n1-1));
			plus = true;
		}
		if (a2!=0) {
			if (a2>0 && plus)
				sb.append('+');
			sb.append(a2+indexedStr(" P",y1,n1));
			plus = true;
		}
		if (a3!=0) {
			if (a3>0 && plus)
				sb.append('+');
			sb.append(a3+indexedStr(" P",y1,n1+1));
			plus = true;
		}
		if (a4!=0) {
			if (a4>0 && plus)
				sb.append('+');
			sb.append(a4+indexedStr(" P",y1+1,n1));
			plus = true;
		}
		if (a2==1 && y1==N)
			sb.append("=1");
		else
			sb.append("=0");
		
		System.out.println(sb.toString());
	}

	public void putP(int row, int y, int n, double val) {
		//int col = y*N+n;
		int d = ((2+N-y)*(N+1-y))/2;
		int col = sum-d+n;
		
		
		if ((col>=0) && (row>=0)) {
			if ((row< matrix.length) && (col<matrix[row].length)) {
				matrix[row][col] = val;

				if (DEBUG)
					System.out.println("matrix["+row+','+col+"]="+val);
			}
		}
	}

	public int getN() {
		return N;
	}

	public double[][] getMatrix() {
		return matrix;
	}

	public void printMatrix() {
		printMatrix(matrix);
	}
	
	public static void printMatrix(double [][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				System.out.format("%+1.3f\t", matrix[i][j]);
			}
			System.out.println();
		}
	}

	public static String indexedStr(String text, int i, int j) {
		return text+subs[i]+','+subs[j];
	}

	public static void main(String[] args) {

		int N = 3;
		
		
		
		Population p = new Population(N);
		p.calculateP();
		
		
		p.printMatrix();
		
	}

}
