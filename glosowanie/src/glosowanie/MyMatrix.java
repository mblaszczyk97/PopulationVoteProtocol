package glosowanie;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


/**
 * 
 *
 *  klasa macierzy
 */
public class MyMatrix {
    protected double[][] matrix;
    protected int rows;
    private int columns;
	public MyMatrix() {
    	this.matrix = new double[0][0];
    }

    public MyMatrix( int length) {
    	this(length, length);
    }
   
	public MyMatrix( int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.matrix = new double[rows][columns];
    }

	public MyMatrix dodawanie(MyMatrix secondMatrix) 
    {

        MyMatrix sumMatrix = new MyMatrix(this.rows, this.columns);


        if (!(secondMatrix.columns == this.columns && secondMatrix.rows == this.rows)) 
        {
            return null;
        }

        for (int i = 0; i < rows; i++) 
        {
            for (int j = 0; j < columns; j++) 
            {

                        Double sum = this.matrix[i][j] + secondMatrix.matrix[i][j];
                        sumMatrix.matrix[i][j] =  sum;
            }
        }
        return sumMatrix;
    }

	public MyMatrix czesciowyWybor(MyMatrix matrix, MyMatrix vector) 
    {

        int n = vector.rows;
        for (int p = 0; p < n; p++) 
        {

            int max = p;
            przemiescNajwiekszyWiersz(matrix, vector, n, p, max);
            for (int i = p + 1; i < n; i++) {
                tworzTrojkatMacierzy(matrix, vector, n, p, i);
            }
        }

        MyMatrix resultVector = new MyMatrix(vector.rows, 1);
        
        liczUkladyRownanOdTylu(matrix, vector, n, resultVector);
        return resultVector;
    }
    
    public void matrixTimeElapsedCzesciowyWybor(MyMatrix matrix, MyMatrix vector) 
    {
    	long start = System.nanoTime();
    	czesciowyWybor(matrix, vector);
    	long elapsedTimeMillis = System.nanoTime() -start;
        System.out.println("czas: " + elapsedTimeMillis + " nanosekund");
    	start = 0;
    	elapsedTimeMillis = 0;
    }

    private void przemiescNajwiekszyWiersz(MyMatrix matrix, MyMatrix vector, int n, int p, int max) 
    {
        for (int i = p + 1; i < n; i++) 
        {	

                    if (Math.abs(matrix.matrix[i][p]) > Math.abs(matrix.matrix[max][p])) 
                    {
                        max = i;
                    }
        }

        zamienWiersze(matrix, p, max);
        zamienWiersze(vector, p, max);
    }
    
	private void tworzTrojkatMacierzy(MyMatrix matrix, MyMatrix vector, int n, int p, int i) 
    {
                double alpha = matrix.matrix[i][p] / matrix.matrix[p][p];
                vector.matrix[i][0] = (vector.matrix[i][0] - alpha * vector.matrix[p][0]);
                for (int j = p; j < n; j++) 
                {
                    matrix.matrix[i][j] = (matrix.matrix[i][j] - alpha * matrix.matrix[p][j]);
                }
    }

	private void liczUkladyRownanOdTylu(MyMatrix matrix, MyMatrix vector, int n, MyMatrix resultVector) 
    {
                for (int i = n - 1; i >= 0; i--) 
                {
                    double sum = 0.0;
                    for (int j = i + 1; j < n; j++) 
                    {
                        sum += matrix.matrix[i][j] * resultVector.matrix[j][0];
                    }
                    resultVector.matrix[i][0] = ((vector.matrix[i][0] - sum) / matrix.matrix[i][i]);

                }
    }

    public void znajdzUstawNajwiekszaWartoscMacierzy(MyMatrix matrix, MyMatrix vector, int p, ArrayList<Integer> queue) 
    {

        double maxValue = matrix.matrix[p][p];
        int rowIndex = p;
        int columnIndex = p;
        for (int ii = p; ii < matrix.rows; ii++) 
        {
            for (int jj = p; jj < matrix.columns; jj++) 
            {
                if (Math.abs(matrix.matrix[ii][jj]) > maxValue) 
                {
                    maxValue = Math.abs(matrix.matrix[ii][jj]);
                    rowIndex = ii;
                    columnIndex = jj;
                }
            }
        }

        zamienWiersze(matrix, p, rowIndex);
        zamienWiersze(vector, p, rowIndex);
        zamienKolumny(matrix, p, columnIndex, queue);
    }

	public MyMatrix zamienWynikiWedlugListy(MyMatrix vectorMatrix, ArrayList<Integer> queue) 
    {

        MyMatrix tmp = new MyMatrix(vectorMatrix.rows, 1);
        for (int ii = 0; ii < vectorMatrix.rows; ii++) 
        {
            for (int jj = 0; jj < vectorMatrix.columns; jj++) 
            {
                tmp.matrix[ii][jj] = vectorMatrix.matrix[ii][jj];
            }
        }
        for (int i = 0; i < vectorMatrix.rows; i++) 
        {
            vectorMatrix.matrix[queue.get(i)][0] = tmp.matrix[i][0];
        }
                
        return vectorMatrix;
    }

    public MyMatrix zamienWiersze(MyMatrix finalMatrix, int row1, int row2) 
    {
        if (row1 == row2) 
        {
            return finalMatrix;
        }
        for (int i = 0; i < finalMatrix.columns; i++) 
        {
            double tmp = finalMatrix.matrix[row1][i];
            finalMatrix.matrix[row1][i] = finalMatrix.matrix[row2][i];
            finalMatrix.matrix[row2][i] = tmp;
        }
        return finalMatrix;
    }

    public MyMatrix zamienKolumny(MyMatrix finalMatrix, int column1, int column2, ArrayList<Integer> queue) 
    {
        if (column1 == column2) 
        {
            return finalMatrix;
        }
        int tmp = queue.get(column1);
        queue.set(column1, queue.get(column2));
        queue.set(column2, tmp);

        for (int i = 0; i < finalMatrix.rows; i++) 
        {
            double tmp2 = finalMatrix.matrix[i][column1];
            finalMatrix.matrix[i][column1] = finalMatrix.matrix[i][column2];
            finalMatrix.matrix[i][column2] = tmp2;
        }
        return finalMatrix;
    }
    
    public MyMatrix jacobs(MyMatrix A, MyMatrix b) {
    	int i = 0, j = 0, k=0;
    	int num = rows;
    	MyMatrix M = new MyMatrix(rows);
    	MyMatrix N = new MyMatrix(rows);
    	MyMatrix x1 = new MyMatrix(rows, 1);
    	MyMatrix x2 = new MyMatrix(rows, 1);
    	
    	
    	//N = D^-1
    	for (i=0; i<num; i++)
    	N.matrix[i][0] = 1/A.matrix[i][i];
    	 
    	//M = -D^-1 (L + U)
    	for (i=0; i<num; i++)
    	for (j=0; j<num; j++)
    	if (i == j)
    	M.matrix[i][j] = 0;
    	else
    	M.matrix[i][j] = -1*(A.matrix[i][j] * N.matrix[i][0]);
    	 
    	//x
    	for (i=0; i<num; i++)
    	x1.matrix[i][0] = 0;
    	 
    	int iter = 100;
    	 
    	for (k=0; k<iter; k++) {
    	for (i=0; i<num; i++) {
    	x2.matrix[i][0] = N.matrix[i][0]*b.matrix[i][0];
    	for (j=0; j<num; j++)
    	x2.matrix[i][0] += M.matrix[i][j]*x1.matrix[j][0];
    	}
    	for (i=0; i<num; i++)
    	x1.matrix[i][0] = x2.matrix[i][0];
    	}
    	 
    	System.out.println("\n"+"Metoda Jacobsa: ");
    	for (i=0; i<num; i++)
    	System.out.println("P["+(i+1)+"] = " + x1.matrix[i][0]);
    	 
    	return x1;
    }
    
    public MyMatrix gaussSeidel(MyMatrix A, MyMatrix b) 
    {
    	
    	int i = 0, j = 0, k=0;
    	int num = rows;
    	MyMatrix U = new MyMatrix(rows);
    	MyMatrix L = new MyMatrix(rows);
    	MyMatrix D = new MyMatrix(rows);
    	MyMatrix x = new MyMatrix(rows, 1);
    	
    	// Divide A into L + D + U
    	for (i=0; i<num; i++)
    	for (j=0; j<num; j++) {
    	if (i < j) {
    	U.matrix[i][j] = A.matrix[i][j];
    	}
    	else if (i > j) {
    	L.matrix[i][j] = A.matrix[i][j];
    	}
    	else {
    	D.matrix[i][j] = A.matrix[i][j];
    	}
    	}
    	 
    	//D^-1
    	for (i=0; i<num; i++)
    	D.matrix[i][i] = 1/D.matrix[i][i];
    	 
    	//D^-1 * b
    	for (i=0; i<num; i++)
    	b.matrix[i][0] *= D.matrix[i][i];
    	 
    	//D^-1 * L
    	for (i=0; i<num; i++)
    	for (j=0; j<i; j++)
    	L.matrix[i][j] *= D.matrix[i][i];
    	 
    	//D^-1 * U
    	for (i=0; i<num; i++)
    	for (j=i+1; j<num; j++)
    	U.matrix[i][j] *= D.matrix[i][i];
    	 
    	//Initialize x
    	for (i=0; i<num; i++)
    	x.matrix[i][0] = 0;

    	int iter=100;
    	 
    	for (k=0; k<iter; k++)
    	for (i=0; i<num; i++) {
    	x.matrix[i][0] = b.matrix[i][0];                    // x = D^-1*b -
    	for (j=0; j<i; j++)
    	x.matrix[i][0] -= L.matrix[i][j]*x.matrix[j][0];    // D^-1*L * x -
    	for (j=i+1; j<num; j++)
    	x.matrix[i][0] -= U.matrix[i][j]*x.matrix[j][0];    // D^-1*U * x
    	}
    	 
    	System.out.println("\n"+"Metoda Gaussa-Seidela");
    	for (i=0; i<num; i++)
    	System.out.println("P["+(i+1)+"] = " + x.matrix[i][0]);
    	
    	
		return x;
    }

    public void rzutujMacierz() 
    {
        System.out.println("\n" + "Macierz:");
        for (int i = 0; i < rows; i++) 
        {
            for (int j = 0; j < columns; j++) 
            {
                System.out.printf("%26.26s  ", matrix[i][j]);
            }
            System.out.println("");
        }
    }

	public void wypelnianieWynikami() 
    {
		int val=0;
        for (int i = 0; i < rows-1; i++) 
        {
        	val++;
            for (int j = 0; j < columns; j++) 
            {
                        matrix[i][j] =  0.0d;
                    
            }
        }
        matrix[val][0]= 1.0d;
        
    }

    public void wczytajPlik(String suffix) throws IOException 
    {
        FileInputStream fstream = new FileInputStream("macierz" + suffix + ".txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
        wczytajDanePliku(br);
        br.close();
        fstream.close();
    }

	private void wczytajDanePliku(BufferedReader br) throws IOException 
    {
        String strLine;
        rows = Integer.valueOf(br.readLine());
        columns = Integer.parseInt(br.readLine());
        
        matrix = new double[rows][columns];

        for (int i = 0; i < rows; i++) 
        {
            for (int j = 0; j < columns; j++) 
            {

                strLine = br.readLine();
                        matrix[i][j] = Double.valueOf(strLine);
            }
        }
    }
	
	public void wczytajDane(Population p) 
    {
        rows = p.getMatrix().length;
        columns =p.getMatrix()[0].length;
        matrix = new double[rows][columns];
        
        
        for (int i = 0; i < rows; i++) 
        {
            for (int j = 0; j < columns; j++) 
            {
            	matrix[i][j] = p.getMatrix()[i][j];
            }
        }
    }
 
}