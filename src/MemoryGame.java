/**
 * A Memory Game core to be adapted for an android application
 * 
 *  @author	kkangshawn
 *  @date	11-01-2016
 */
import java.util.Random;
import java.util.ArrayList;
import java.util.Scanner;

public class MemoryGame {
	/**
	 * @brief Make a deck by random number generation.
	 * @param nCards	The number of cards to be played. Input MUST be even.
	 * @return rawDeck	A deck generated.
	 */
	public ArrayList<Integer> makeDeck(int nCards) {
		if (nCards % 2 != 0 || nCards < 0)
		{
			System.err.println("The number of cards MUST be even and higher than 0.");
			nCards = 2;
		}
		
		int[] chosenNumbers = new int[nCards/2];
		ArrayList<Integer> rawDeck = new ArrayList<Integer>();
		
		// for DP, initialization
		boolean[] arrNumberTable = new boolean[100 + 1];
		for (int b = 0; b < 101; b++)
		{
			arrNumberTable[b] = false;
		}

		Random rand = new Random();
		int nTemp = 0;;
		for (int i = 0; i < nCards/2; i++)
		{
			// Re-pick a card if the card has already been taken
			do {
				// Range of cards is 1~100
				nTemp = rand.nextInt(100) + 1;
				chosenNumbers[i] = nTemp;
			} while (arrNumberTable[nTemp]);
			arrNumberTable[chosenNumbers[i]] = true;
		}
		
		// Organize a deck with cards chosen. Each number will be doubled for a pair.
		for (int j = 0; j < nCards; j++)
		{
			rawDeck.add(rand.nextInt(j + 1), chosenNumbers[j/2]);	
		}
		for (int a : rawDeck)
		{
			System.out.print(a + " ");
		}
		System.out.println();
		
		// Check result of random number generation & DP table
		/*
		for (int a : chosenNumbers)
		{
			System.out.println(a);	
		}
		int cnt = 0;
		for	(boolean b : arrNumberTable)
		{

			System.out.print(b + " ");
			if (cnt % 10 == 0)
				System.out.println();
			cnt++;
		}
		*/
		
		return rawDeck;
	}
	/**
	 * Play a game with the deck generated.
	 * 
	 * @param arr	A deck created by makeDeck().
	 */
	public void playGame(ArrayList<Integer> arr) {
		Scanner sc = new Scanner(System.in);
		int nFirst = 0;
		int nSecond = 0;
		int nRestOfCards = arr.size();
		
		while (nRestOfCards > 0)
		{
			// If a player chooses a card that is already revealed(marked as -1),
			// silently ignore and ask for a number input again
			do
			{
				System.out.println("Choose a first card to flip [1 ~ " + arr.size() + "]: ");
				nFirst = sc.nextInt();
				while (nFirst < 1 || nFirst > arr.size())
				{
					System.err.println("The value is out of range. Input a number again: ");
					nFirst = sc.nextInt();
				}
			} while (arr.get(nFirst - 1) == -1);
			// flip a card
			System.out.println(arr.get(nFirst - 1));
			do
			{
				System.out.println("Choose a second card to flip [1 ~ " + arr.size() + "]: ");
				nSecond = sc.nextInt();
				while (nSecond < 1 || nSecond > arr.size())
				{
					System.err.println("The value is out of range. Input a number again: ");
					nSecond = sc.nextInt();
				}		
			} while (arr.get(nSecond - 1) == -1);
			System.out.println(arr.get(nSecond - 1));
			
			if (arr.get(nFirst - 1) == arr.get(nSecond - 1))
			{
				arr.set(nFirst - 1, -1);
				arr.set(nSecond - 1, -1);
				System.out.println("Match found");
				nRestOfCards -= 2;
			}
		}
		System.out.println("Stage Clear");
		sc.close();
	}
	
	public static void main(String args[]) {
		MemoryGame memGame = new MemoryGame();
		ArrayList<Integer> arrDeck = new ArrayList<Integer>();
		
		/// The number of cards will increase by 2 for pairs.
		arrDeck = memGame.makeDeck(10);
		memGame.playGame(arrDeck);
	}
}
