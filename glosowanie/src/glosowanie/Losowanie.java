package glosowanie;

import java.util.Random;

public class Losowanie {
	
	public static char losuj()
	{
		char wylosowana = ' ';
		Random r = new Random();
		String alphabet = "TNU";
	    for (int i = 0; i < 50; i++) {
	        wylosowana=alphabet.charAt(r.nextInt(alphabet.length()));
	    } // prints 50 random characters from alphabet
		
		return wylosowana;
		
	}
	
	public static int losujNumer(int min, int max){
	    int x = (int) ((Math.random()*((max-min)+1))+min);
	    return x;
	}
	
}
