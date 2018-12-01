package glosowanie;

import java.io.IOException;

import glosowanie.VotesMatrix.Vote;

public class Main
{
	
	static VotesMatrix matrix = new VotesMatrix(10, 4, 2);
	static VotesMatrix matrixTEST = new VotesMatrix(10);
	public static double epsylon = 0.0000000000000001;
	
	public static void main(String[] args) throws IOException 
    {
		int licznik=1000;
		int max_kroki = 100;
		int kroki = 0;
		int powtorzeniaTAK=0;
				
		matrix.printMacierz();
		for(int j = 0; j<licznik; j++)
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
			

		}
		System.out.println("kroki:"+kroki+" iloœæ macierzy TTTTTT: "+powtorzeniaTAK+" iloœæ ruchów wype³nienia macierzy: "+licznik);
		double prawd = (1.0d*powtorzeniaTAK)/licznik;
		System.out.println("Prawdopodobieñstwo: "+prawd);
    }
	
	public void sprawdzanie() 
	{
		
		for(int i = 0; i<100; i++) {matrix.algorytmGlosowania();}
		//if (matrix1[i][0]=="T") {}
		
	}
    

}
