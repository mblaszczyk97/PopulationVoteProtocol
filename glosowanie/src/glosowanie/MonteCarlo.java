package glosowanie;

import glosowanie.VotesMatrix.Vote;

public class MonteCarlo {
	
	public static double prawdMonteCarlo(int len, int y, int n, long ilosc) 
	{
		VotesMatrix matrix = new VotesMatrix(len, y, n);
		
		long licznik=ilosc;
		int max_kroki = 100;
		int kroki = 0;
		int powtorzeniaTAK=0;
				
		//matrix.printMacierz();
		for(long j = 0; j<licznik; j++)
		{
			kroki = 0;
			VotesMatrix matrix1 = (VotesMatrix)matrix.clone();
			for(int i = 0; i<max_kroki; i++) 
			{
				matrix1.algorytmGlosowania();
				kroki++;
				if (matrix1.czyMacierzJednolita())
					break;
			}
			
			if(matrix1.czyMacierzJednolita() && matrix1.getElem(0)==Vote.Yes)
			{
				powtorzeniaTAK++;
				//System.out.println("TAK");
			}
			else if(matrix1.czyMacierzJednolita()&& matrix1.getElem(0)==Vote.No)
			{
				//System.out.println("NIE");
			}
			else if(matrix1.czyMacierzJednolita()&& matrix1.getElem(0)==Vote.Undecided)
			{
				//System.out.println("UND");
			}
			if (j==1000000)
				System.out.println(" 10^6");
			else if (j==10000000)
					System.out.println(" 10^7");
			else if (j==100000000)
				System.out.println(" 10^8");
			else if (j==10000000000L)
				System.out.println(" 10^10");

		}
		//System.out.println("kroki:"+kroki+" iloœæ macierzy TTTTTT: "+powtorzeniaTAK+" iloœæ ruchów wype³nienia macierzy: "+licznik);
		double prawd = (1.0d*powtorzeniaTAK)/licznik;
		//System.out.println(""+prawd);
		
		return prawd;
    }

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		prawdMonteCarlo(10, 4, 2, 10000);
	}
}
