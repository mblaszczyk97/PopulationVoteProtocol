package glosowanie;

public class Test {
	
	static int N = 3;
	static int y = 0;
	static int n = 0;
	
	
	public static void main(String[] args) {
		Population p = new Population(N);
		p.calculateP();
		testCzesciowyGauss(p);
		testMonteCarlo();
		testJacobs(p);
		testGaussSeidel(p);
			
	}	
	
	public static void testMonteCarlo() {
		System.out.println("\n"+"Monte Carlo: ");
		int ile=1;
		for(y=0; y<=N; y++) {
			for(n=0;n<=N-y; n++)
			{
				System.out.println("P["+ile++ +"]: "+ MonteCarlo.prawdMonteCarlo(N, y, n, 10000));
				//MonteCarlo.prawdMonteCarlo(N, y, n, 10000);
			}
		}
	}
	
	public static void testCzesciowyGauss(Population p) {
		MyMatrix vector = new MyMatrix(p.getMatrix().length,1);
		vector.wypelnianieWynikami();
		MyMatrix matrix1 = new MyMatrix();
		matrix1.wczytajDane(p);
		System.out.println("\n"+"Czêœciowy Gauss: ");
		matrix1.czesciowyWybor(matrix1, vector).rzutujMacierz();
	}
	
	public static void testJacobs(Population p)
	{
		MyMatrix vector1 = new MyMatrix(p.getMatrix().length,1);
		vector1.wypelnianieWynikami();
		MyMatrix matrix2 = new MyMatrix(N);
		matrix2.wczytajDane(p);
        matrix2.jacobs(matrix2, vector1);
	}
	
	public static void testGaussSeidel(Population p)
	{
		MyMatrix vector2 = new MyMatrix(p.getMatrix().length,1);
		vector2.wypelnianieWynikami();
		MyMatrix matrix3 = new MyMatrix(N);
		matrix3.wczytajDane(p);
        matrix3.gaussSeidel(matrix3, vector2);
	}
}
