package deckOfCards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

public class Deck {

	private ArrayList<Card> listOfCards;

	/*
	 * Deck constructor that initializes 52 card objects from the first card ace
	 * of spades to the last card king diamonds
	 */
	public Deck() {
		listOfCards = new ArrayList<Card>(
				Rank.values().length * Suit.values().length);
		for (Suit suit : Suit.values()) {
			for (Rank rank : Rank.values()) {
				listOfCards.add(new Card(rank, suit));
			}
		}

	}

	/*
	 * Shuffle method that takes one parameter which is the order in which the
	 * cards should be shuffled
	 * 
	 * @randomNumberGenerator, this is the specific order in which the cards are
	 * going to be shuffled
	 */

	public void shuffle(Random randomNumberGenerator) {
		Collections.shuffle(listOfCards, randomNumberGenerator);

	}

	// This method removes the first card from a list of cards and returns the
	// card that was removed
	public Card dealOneCard() {
		Card removed = listOfCards.get(0);
		listOfCards.remove(0);
		return removed;

	}

}
