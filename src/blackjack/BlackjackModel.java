package blackjack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import deckOfCards.*;

public class BlackjackModel {

	// Initializing the dealersCards and the playersCards
	private ArrayList<Card> dealerCards;
	private ArrayList<Card> playerCards;
	private Deck deck;

	// returns a deep copy of the dealers cards
	public ArrayList<Card> getDealerCards() {
		ArrayList<Card> dealerCopy = new ArrayList<>();
		for (Card card : dealerCards) {
			dealerCopy.add(card);
		}
		return dealerCopy;
	}

	// returns a deep copy of the players cards
	public ArrayList<Card> getPlayerCards() {
		ArrayList<Card> playerCopy = new ArrayList<>();
		for (Card card : playerCards) {
			playerCopy.add(card);
		}
		return playerCopy;
	}

	/*
	 * returns a deep copy of the cards given to the dealer
	 * 
	 * @playerCards is an array list of the cards in the dealers deck
	 */
	public void setDealerCards(ArrayList<Card> dealerCards) {
		ArrayList<Card> dealerCopy = new ArrayList<>();
		for (Card card : dealerCards) {
			dealerCopy.add(card);
		}
		this.dealerCards = dealerCopy;
	}
	/*
	 * returns a deep copy of the cards given to the dealer
	 * 
	 * @playerCards is an array list of the cards in the players deck
	 */

	public void setPlayerCards(ArrayList<Card> playerCards) {
		ArrayList<Card> playerCopy = new ArrayList<>();
		for (Card card : playerCards) {
			playerCopy.add(card);
		}
		this.playerCards = playerCopy;

	}

	/*
	 * This method creates a new deck of cards by intansiating a new deck and
	 * then shuffling the deck of cards randomly
	 * 
	 * @random the order in which the new deck of cards will be shuffled
	 */
	public void createAndShuffleDeck(Random random) {
		deck = new Deck();
		deck.shuffle(random);
	}

	// This method instantiates a new list of cards for the dealer, after the
	// cards have been instantiated two cards are dealt from the deck and added
	// to the dealers cards

	public void initialDealerCards() {
		dealerCards = new ArrayList<>();
		for (int index = 0; index < 2; index++) {
			dealerCards.add(deck.dealOneCard());
		}
	}

	/*
	 * This method instantiates a new list of cards for the player, after the
	 * cards have been instantiated two cards are dealt from the deck and added
	 * to the players cards
	 */

	public void initialPlayerCards() {
		playerCards = new ArrayList<>();
		for (int index = 0; index < 2; index++) {
			playerCards.add(deck.dealOneCard());
		}

	}

	// This method only deals one card from the deck and then adds that card to
	// the players cards
	public void playerTakeCard() {
		playerCards.add(deck.dealOneCard());

	}

	// This method deals on card from the deck and then adds that card to the
	// dealers cards
	public void dealerTakeCard() {
		dealerCards.add(deck.dealOneCard());
	}

	/*
	 * This method sums up the values in either the player or dealers hand
	 * 
	 * @The list of cards that are summed and then divided into two arrays
	 * depending on the cards i9n either the players or the dealers hand
	 */
	public static ArrayList<Integer> possibleHandValues(ArrayList<Card> hand) {
		ArrayList<Integer> cards = new ArrayList<>();
		int sum = 0;
		int check = 0;
		// if one of the cards in the dealer or players hand is an ace the
		// check increments depending on how many cards equal ace
		for (Card card : hand) {
			if (card.getRank() == Rank.ACE) {
				check++;
			}
		}
		// if there are no cards that are equal to ace the hand will be summed
		// and the total will be added to the array list which takes integers
		// and returns the sum of the hand

		if (check == 0) {
			for (Card card : hand) {
				sum += card.getRank().getValue();
			}
			cards.add(sum);
			return cards;
			/*
			 * But if the card has an ace then the array list that takes
			 * integers will have two integers
			 */
		} else {
			for (Card card : hand) {
				if (card.getRank() != Rank.ACE) {
					sum += card.getRank().getValue();
				}
			}
			int cardAsOne = sum + (1 * check);
			int cardAsEleven = sum + 11 + (check - 1);
			// If the hand has ace and one of the values of the ace is higher
			// than 21 then the array only returns one value in the array or if
			// both values re greater than 21 the array will return the smaller
			// value
			if (cardAsEleven > 21) {
				cards.add(cardAsOne);
				return cards;
			}
			// Else the array will return both of the values in the array list
			// of innteger
			cards.add(cardAsOne);
			cards.add(cardAsEleven);
			return cards;
		}
	}

	/*
	 * This method evaluates the cards in the player and the dealers hand and
	 * comes up with assessments depending on the values in the player or
	 * dealers hand
	 * 
	 * @Hand is an array list that is used to asses both the dealer and players
	 * hand
	 */
	public static HandAssessment assessHand(ArrayList<Card> hand) {
		// calling on the method possibleHandValues in order to assess the
		// player and dealers hands
		ArrayList<Integer> hands = possibleHandValues(hand);
		// returns insufficient cards if hand is null or the hand size is less
		// than 2
		if (hand.size() < 2 || hand == null) {
			return HandAssessment.INSUFFICIENT_CARDS;
			// returns natural blackjack if the hand.size is equal to two and
			// the hands possible hand value is 21
		} else if (hand.size() == 2 && hands.contains(21)) {
			return HandAssessment.NATURAL_BLACKJACK;
			// returns bust if the hand value is greater than 21
		} else if (Collections.max(hands) > 21) {
			return HandAssessment.BUST;
			// If none apply the deck will return normal
		} else {
			return HandAssessment.NORMAL;
		}
	}

	/*
	 * This is a method that determines the result of the game, whether the
	 * player won, lost or a tie
	 */
	public GameResult gameAssessment() {
		// Calling on to the possible hand values method and the asses hand
		// method for both the playersCards and the dealersCards
		HandAssessment playersCardss = assessHand(playerCards);
		HandAssessment dealersCardss = assessHand(dealerCards);
		ArrayList<Integer> playersCards = possibleHandValues(playerCards);
		ArrayList<Integer> dealersCards = possibleHandValues(dealerCards);

		// returns push if the player and the dealer both have a natural
		// blackjack meaning both there cards equal 21 and have 2 cards
		if (playersCardss.equals(HandAssessment.NATURAL_BLACKJACK)
				&& dealersCardss.equals(HandAssessment.NATURAL_BLACKJACK)) {
			
			return GameResult.PUSH;
			// returns natural blackjack if the hand assessment of the players
			// cards equals natural blackjack and the dealers cards dont
		} else if (playersCardss.equals(HandAssessment.NATURAL_BLACKJACK)
				&& !dealersCardss.equals(HandAssessment.NATURAL_BLACKJACK)) {
			return GameResult.NATURAL_BLACKJACK;
		} else {
			// If players cards equals bust the player loses and the dealers
			// doesn't
			if (playersCardss.equals(HandAssessment.BUST)) {
				return GameResult.PLAYER_LOST;
				// If dealers cards equals bust the player loses and the players
				// doesn't
			} else if (dealersCardss.equals(HandAssessment.BUST)
					&& !playersCardss.equals(HandAssessment.BUST)) {
				return GameResult.PLAYER_WON;
			} else {
				// if the dealerCards less than players cards and they both
				// don't equal bust then the player wins
				if (Collections.max(dealersCards) < Collections
						.max(playersCards)) {
					return GameResult.PLAYER_WON;
					//// else if the dealerCards greater than players cards and
					//// they both
					// don't equal bust then the player loses
				} else if (Collections.max(dealersCards) > Collections
						.max(playersCards)) {
					return GameResult.PLAYER_LOST;
					// If none apply then the game assessment will be a push
					// result
				} else {
					return GameResult.PUSH;
				}

			}
		}
	}

	// This method determines whether the dealer takes a card depending on what
	// cards the dealer currently has in their hand returning false if the
	// dealer should stop taking cards and true if the dealer should continue
	// taking cards
	public boolean dealerShouldTakeCard() {
		ArrayList<Integer> hands = possibleHandValues(dealerCards);
		ArrayList<Card> cards = new ArrayList<>(dealerCards);
		int check = 0;
		for (Card card : cards) {
			if (card.getRank() == Rank.ACE) {
				check++;
			}
		}
		// If the maximum possible hand value is less than or equals to 16 then
		// the dealer will continue to take cards
		if (Collections.max(hands) <= 16) {
			return true;
			// else if the max value in the dealers hand is greater than 18 or
			// equals to 18 the dealer will stop taking cards returning false
		} else if (Collections.max(hands) >= 18) {
			return false;

			// if the dealer has an ace that can be 7 or 17 then the dealer
			// should coninue taking cards
		} else if (check > 0 && (hands.contains(7) && hands.contains(17))) {
			return true;
			// else return false
		} else {

			return false;
		}

	}

}
