package glosowanie;

public class VotesMatrix{
	public enum Vote{
		Yes, No, Undecided;
		
		public static Vote vote(char c) {
			if (c=='T')
				return Yes;
			else if (c=='N')
				return No;
			return Undecided;
		}
	}
	
	private Vote[] matrix;
	
	public Vote getElem(int idx) {
		return matrix[idx];
	}

	public VotesMatrix() {
    	this.matrix = new Vote[0];
    }

	public VotesMatrix(int len) {
        this.matrix = new Vote[len];
    }
	
	public VotesMatrix(int len, int y, int n) {
		if ((y+n) > len) {
			if (y > len) y = len;
			n = len - y;
		}
        this.matrix = new Vote[len];
        for (int i = 0; i < y; i++) {
			matrix[i] = Vote.Yes;
		}
        for (int i = y; i < y+n; i++) {
			matrix[i] = Vote.No;
		}
        for (int i = y+n; i < len; i++) {
			matrix[i] = Vote.Undecided;
		}
        
    }

	public void stworzLosowaMacierz() {
		for (int i = 0; i < matrix.length; i++) {
				matrix[i] = Vote.vote(Losowanie.losuj());
		}
	}
	
	public void printMacierz() 
	{
		System.out.println("\n" + "Macierz:");
	    for (int i = 0; i < matrix.length; i++) 
	    {
	    		System.out.println(matrix[i]);
	    }
	}
	
	public void algorytmGlosowania()
	{
		Vote s1 = null,s2;
		int los1 = Losowanie.losujNumer(0, matrix.length-1);
		s1=matrix[los1];
		int los2 = Losowanie.losujNumer(0, matrix.length-1);
		s2=matrix[los2];
		while (los2==los1)
		{
			
			los2 = Losowanie.losujNumer(0, matrix.length-1);
			s2=matrix[los2];
		}
		//s2=matrix[Losowanie.losujNumer(0, rows-1)][0];
		
		/*matrix[1][0]="T";
		matrix[2][0]="T";
		matrix[3][0]="T";
		matrix[4][0]="T";*/
			if ((s1==Vote.Yes && s2==Vote.Undecided) || (s1==Vote.Undecided && s2==Vote.Yes))
			{
				matrix[los1]=Vote.Yes;
				matrix[los2]=Vote.Yes;
			}
			else if ((s1==Vote.Yes && s2==Vote.No) || (s1==Vote.No && s2==Vote.Yes))
			{
				matrix[los1]=Vote.Undecided;
				matrix[los2]=Vote.Undecided;
			}
			else if ((s1==Vote.No && s2==Vote.Undecided) || (s1==Vote.Undecided && s2==Vote.No))
			{
				matrix[los1]=Vote.No;
				matrix[los2]=Vote.No;
			}
		//if(matrix[Losowanie.losujNumer(0, rows)][0]=="T") {}
		
		//System.out.println("\n"+"wylosowa³em: " + string);
		
	}
	
	public boolean czyMacierzJednolita()
	{
		Vote v = matrix[0];
		for (int i = 1; i < matrix.length; i++) {
			if(matrix[i]!=v) {
				return false;
			}
		}
		return true;
	}

	@Override
	protected Object clone(){
		VotesMatrix cloned = new VotesMatrix(this.matrix.length);
		for (int i = 0; i < matrix.length; i++) {
			cloned.matrix[i] = matrix[i];
		}
		
		return cloned;
	}
	
	
	
	
	
	
}
