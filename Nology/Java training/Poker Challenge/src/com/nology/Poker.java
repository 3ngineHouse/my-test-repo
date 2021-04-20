package com.nology;

import com.nology.Card;

import java.lang.reflect.Array;
import java.util.*;

import static java.util.Arrays.asList;

public class Poker {
    private static final List<Character> suits = asList('\u2665', '\u2663', '\u2664', '\u2666');
    private static final List<String> symbols = asList("2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A");
    private static final List<Integer> values = asList(2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14);

    // make a fresh deck of cards (since it creates an ArrayList, I changed the return type from List to ArrayList)
    public static ArrayList<Card> freshDeck() {
        ArrayList<Card> tempDeck = new ArrayList<>();
        for (Character suit:suits) {
            for (int i = 0; i < symbols.size(); i++) {
                tempDeck.add(new Card(values.get(i), symbols.get(i), suit));
            }
        }
        return tempDeck;
    }

    public static ArrayList<Card> shuffled(ArrayList<Card> deck) {
        Collections.shuffle(deck);
        return deck;
    }

    public static ArrayList<Card> deal(ArrayList<Card> deck, int numberOfCards){
        ArrayList<Card> hand = new ArrayList<>();
        for (int i=0; i<numberOfCards; i++){
            if (deck.size()>0) {
                // Arraylist indexes update as soon as you remove an element, so the card at index 0 changes as we draw
                hand.add(deck.get(0));
                deck.remove(0);
            } else {
                System.out.println("The deck has been emptied; only "+i+" cards could be drawn");
                break;
            }
        }
        return hand;
    };

    public static int[] findHighCards(ArrayList<Card> hand){
        // Returns an array of the five highest cards in the hand, from highest to lowest
        int[] output = {0,0,0,0,0};

        // reverse order is highest to lowest; default is lowest to highest
        hand.sort(Collections.reverseOrder());

        // Once sorted, we can simply pick the first five values out of the list
        for (int i=0; i<5 && i<hand.size(); i++){
            output[i]=hand.get(i).getValue();
        }
        return output;
    }

    public static int[] findOnePair(ArrayList<Card> hand){
        // Returns an array of four integers:
        //     - the value the pair in the hand
        //     - the values of the three highest cards not used in the pair, for use in "tiebreakers"
        // All will be zero if there's no pair in the hand

        Card[] cardsInThePair = new Card[2];
        int[] output = {0, 0, 0, 0};

        if (hand.size()<2){
            // There are no pairs in a hand of 0 or 1 cards
            return output;
        }

        // Sort from lowest to highest
        Collections.sort(hand);

        for (int i = 0; i<hand.size()-1; i++){
            // If the value of the next card is the same as the value of the current card...
            if (hand.get(i).getValue()==hand.get(i+1).getValue()){
                // ...the current card and the next card form a pair
                output[0]=hand.get(i).getValue();
                // We note that the two cards are used in the pair
                cardsInThePair[0]=hand.get(i);
                cardsInThePair[1]=hand.get(i+1);
                // and skip the next card so that three of a kind doesn't register as two pairs
                i++;
            }
            // Because the hand is ordered from smallest to largest, a lower pair will be overwritten by a higher pair
            // This is desirable; we want to find the highest value pair
        }

        // Once we've found the highest pair, we need to find the three most valuable single cards for tie-breaking
        // We can again use the fact that the cards are sorted in order of value:

        // If we didn't find a pair, we don't do any of this - we return an array of 0s:
        if (output[0]>0) {
            // Position 0 is for the value of the pair - we start from position 1
            int positionInArray = 1;
            // Because the highest values are at the bottom of the ArrayList, i counts down rather than up
            for (int i = hand.size() - 1; i >= 0; i--) {
                // We check the card ISN'T part of the pair
                if (hand.get(i) != cardsInThePair[0] && hand.get(i) != cardsInThePair[1]) {
                    output[positionInArray] = hand.get(i).getValue();
                    positionInArray++;
                    // Once we've filled the array, we stop
                    if (positionInArray == 4) {
                        break;
                    }
                }
            }
        }
        return output;
    }

    public static int[] findTwoPairs(ArrayList<Card> hand){
        // Returns an array of three integers:
        //     - the values of the two most valuable pairs in the hand (second number might be zero)
        //     - the values of the highest card not used in pairs, for use in "tiebreakers"
        // All will be zero if there's less than two pairs in the hand

        // This operates on a similar principle to findOnePair, except the cards in the pairs are in a list not an array
        ArrayList<Card> cardsInThePairs = new ArrayList<>();
        ArrayList<Integer> pairs = new ArrayList<>();
        int[] output = {0, 0, 0};

        if (hand.size()<2){
            // There are no pairs in a hand of 0 or 1 cards
            return output;
        }

        Collections.sort(hand);

        for (int i = 0; i<hand.size()-1; i++){
            if (hand.get(i).getValue()==hand.get(i+1).getValue()){
                // the current card and the next card form a pair
                pairs.add(hand.get(i).getValue());
                // If there are more than two pairs in the list, the oldest (i.e. smallest) pair is ejected
                if (pairs.size()>2){
                    pairs.remove(0);
                }
                // Add the two cards under consideration to list of cards in pairs
                cardsInThePairs.add(hand.get(i));
                cardsInThePairs.add(hand.get(i+1));
                // If there are more than four cards in that list, the oldest pair is ejected
                if (cardsInThePairs.size()>4){
                    cardsInThePairs.remove(0);
                    cardsInThePairs.remove(0);
                }
                // skip the next card so that three of a kind doesn't register as two pairs
                i++;
            }
        }

        // We can now load the two pairs (if we have them) into output
        if (pairs.size()>1){
            output[0]=pairs.get(1);
            output[1]=pairs.get(0);
        } else {
            // We return all zeros
            return output;
        }

        // If we're still here, we need a high card for the third position in the output array
        for (int i = hand.size() - 1; i >= 0; i--) {
            if (!cardsInThePairs.contains(hand.get(i))) {
                output[2] = hand.get(i).getValue();
                break;
            }
        }
        return output;
    }

    public static int findThreeOfAKind(ArrayList<Card> hand){
        return findXOfAKind(hand, 3);
        // Four of a kind doesn't require tiebreakers; there aren't 6 cards of the same value
    }

    public static int[] findFullHouse(ArrayList<Card> hand){
        // Returns an array of two integers:
        //  - The value of the three
        //  - The value of the pair
        // Both will be zero if there is no full house

        // findThreeOfAKind will find the most valuable three
        int valueOfTheThree = findThreeOfAKind(hand);
        // findTwoPairs will use the three as one of the two pairs
        int[] pairsArray = findTwoPairs(hand);
        int valueOfThePair = 0;

        if (pairsArray[0]!=valueOfTheThree){
            valueOfThePair = pairsArray[0];
        } else {
            valueOfThePair = pairsArray[1];
        };

        if (valueOfTheThree != 0 && valueOfThePair != 0){
            return new int[] {valueOfTheThree, valueOfThePair};
        }
        return new int[] {0,0};
    }

    public static int findFourOfAKind(ArrayList<Card> hand){
        return findXOfAKind(hand, 4);
        // Four of a kind doesn't require tiebreakers; there aren't 8 cards of the same value
    }

    public static int[] findFlush(ArrayList<Card> hand){
        int[] flushValue = {0,0,0,0,0};
        sortCardsBySuit(hand);

        if (hand.size()<5){
            return flushValue;
        }

        for (int i=0; i<hand.size()-4; i++){
            // We load the values we WOULD return if we're about to find a flush
            int[] newValue = {0,0,0,0,0};
            for (int j=0; j<5; j++){
                newValue[4-j] = hand.get(j+i).getValue();
            }

            // We now look forward 1, 2, 3 and 4 cards and check they're all the same suit
            for (int j=i; j<i+5; j++){
                if (!hand.get(i).getSuit().equals(hand.get(j).getSuit())){
                    // If any aren't, our values drop back to 0
                    Arrays.fill(newValue, 0);
                    break;
                }
            }
            if (newValue[4]>flushValue[4]){
                for (int j=0; j<5; j++){
                    flushValue[j]=newValue[j];
                }
            }
        }
        return flushValue;
    }

    public static int findStraight(ArrayList<Card> hand){
        int straightValue = 0;
        Collections.sort(hand);

        if (hand.size()<5){
            return 0;
        }

        // We now look 1, 2, 3 and 4 cards ahead of each card and check they're 1, 2, 3, and 4 higher respectively
        for (int i=0; i<hand.size()-4; i++){
            int newValue = hand.get(i+4).getValue();

            for (int j=1; j<5; j++){
                if (hand.get(i).getValue()!=hand.get(i+j).getValue()-j){
                    newValue=0;
                }
            }
            // If we got to the end of the checks and newValue is nonzero, we have a straight!
            if (newValue>straightValue){
                straightValue=newValue;
            }
        }
        return straightValue;
    }

    public static int findStraightFlush(ArrayList<Card> hand){
        // We combine the checks on find straight and find flush
        int straightFlushValue = 0;
        Collections.sort(hand);

        if (hand.size()<5){
            return 0;
        }

        // We now look 1, 2, 3 and 4 cards ahead of each card and check they're 1, 2, 3, and 4 higher respectively
        for (int i=0; i<hand.size()-4; i++){
            int newValue = hand.get(i+4).getValue();

            for (int j=1; j<5; j++){
                if ( hand.get(i).getValue() != hand.get(i+j).getValue()-j ||    // Check it's still a straight
                     !hand.get(i).getSuit().equals(hand.get(i+j).getSuit())){    // Check it's still a flush
                    newValue=0;
                }
            }
            // If we got to the end of the checks and newValue is nonzero, we have a straight!
            if (newValue>straightFlushValue){
                straightFlushValue=newValue;
            }
        }
        return straightFlushValue;
    }

    public static boolean findRoyalFlush(ArrayList<Card> hand){
        // There is no tie breaker for royal flushes, so returning a boolean is sufficient
        return findStraightFlush(hand)==14;
    }

    public static int findXOfAKind(ArrayList<Card> hand, int X){
        int setValue = 0;
        Collections.sort(hand);

        if (hand.size()<X){
            return 0;
        }

        for (int i=0; i<hand.size()-(X-1); i++){
            int newValue = hand.get(i).getValue();
            for (int j=i; j<i+X; j++){
                if (hand.get(i).getValue()!=hand.get(j).getValue()){
                    newValue = 0;
                }
            }
            if (newValue>setValue){
                setValue=newValue;
            }
        }
        return setValue;
    }

    public static void sortCardsBySuit(ArrayList<Card> hand){
        hand.sort((card1, card2)->{
            int card1SuitValue = card1.getSuit();
            int card2SuitValue = card2.getSuit();

            if (card1SuitValue-card2SuitValue!=0) {
                return card1SuitValue - card2SuitValue;
            } else {
                return card1.getValue()-card2.getValue();
            }
        });
    }

    public static int[] handRank (ArrayList<Card> hand){
        // We're going to start at the top and work our way down
        // That way there's no need to continue checking after we find a hand that works

        if (findRoyalFlush(hand)) {
            return new int[] {10};
        }

        if (findStraightFlush(hand)>0) {
            return new int[]{9, findStraightFlush(hand)};
        }

        if (findFourOfAKind(hand)>0) {
            return new int[]{8, findFourOfAKind(hand)};
        }

        if (findFullHouse(hand)[0]>0){
            return new int[]{7, findFullHouse(hand)[0], findFullHouse(hand)[1]};
        }

        int[] findFlushResult = findFlush(hand);
        if (findFlushResult[0]>0){
            return new int[]{6,
                                findFlushResult[0],
                                findFlushResult[1],
                                findFlushResult[2],
                                findFlushResult[3],
                                findFlushResult[4]};
        }

        if (findStraight(hand)>0){
            return new int[]{5, findStraight(hand)};
        }

        if (findThreeOfAKind(hand)>0){
            return new int[]{4, findThreeOfAKind(hand)};
        }

        int[] findTwoPairsResult = findTwoPairs(hand);
        if (findTwoPairsResult[0]>0){
            return new int[]{3,
                                findTwoPairsResult[0],
                                findTwoPairsResult[1],
                                findTwoPairsResult[2]};
        }

        int[] findOnePairResult = findOnePair(hand);
        if (findOnePairResult[0]>0){
            return new int[]{2,
                                findOnePairResult[0],
                                findOnePairResult[1],
                                findOnePairResult[2],
                                findOnePairResult[3]};
        }

        int[] highCardOutput = {1,0,0,0,0,0};
        int[] findHighCardResult = findHighCards(hand);
        for (int i=0; i<5; i++){
            highCardOutput[i+1]=findHighCardResult[i];
        }
        return highCardOutput;
    }

    public static String nameHand(int[] handRank){
        StringBuilder output = new StringBuilder();

        switch (handRank[0]){
            case 10:
                output.append("a Royal Flush");
                break;
            case 9:
                output.append("a Straight Flush, ");
                output.append(nameCardByValue(handRank[1]));
                output.append(" high");
                break;
            case 8:
                output.append("four of a kind of ");
                output.append(nameCardByValue(handRank[1]));
                output.append("s");
                break;
            case 7:
                output.append("a Full House - three ");
                output.append(nameCardByValue(handRank[1]));
                output.append("s and two ");
                output.append(nameCardByValue(handRank[2]));
                output.append("s");
                break;
            case 6:
                output.append("a Flush, ");
                output.append(nameCardByValue(handRank[1]));
                output.append(" high");
                break;
            case 5:
                output.append("a Straight, ");
                output.append(nameCardByValue(handRank[1]));
                output.append(" high");
                break;
            case 4:
                output.append("three of a kind of ");
                output.append(nameCardByValue(handRank[1]));
                output.append("s");
                break;
            case 3:
                output.append("two pairs - ");
                output.append(nameCardByValue(handRank[1]));
                output.append("s and ");
                output.append(nameCardByValue(handRank[2]));
                output.append("s");
                break;
            case 2:
                output.append("a pair of ");
                output.append(nameCardByValue(handRank[1]));
                output.append("s");
                break;
            case 1:
                output.append("a high card - ");
                output.append(nameCardByValue(handRank[1]));
                break;
            default:
                return "Invalid rank given";
        }
        return output.toString();
    }

    public static String nameCardByValue(int value){
        if (value<11){
            return Integer.toString(value);
        }
        switch (value){
            case 11:
                return "Jack";
            case 12:
                return "Queen";
            case 13:
                return "King";
            case 14:
                return "Ace";
            default:
                return "";
        }
    }

    public static void compareTwoHands(ArrayList<Card> handOne, ArrayList<Card> handTwo){
        int[] handOneRank = handRank(handOne);
        int[] handTwoRank = handRank(handTwo);

        System.out.println("Player 1 has "+nameHand(handOneRank));
        System.out.println("Player 2 has "+nameHand(handTwoRank));

        for (int i=0; i<handOneRank.length && i<handTwoRank.length; i++){
            if (handOneRank[i]>handTwoRank[i]){
                if (i>0){
                    System.out.println("Player 1 wins - their cards were higher value");
                } else {
                    System.out.println("Player 1 wins!");
                }
                return;
            } else if (handOneRank[i]<handTwoRank[i]){
                if (i>0){
                    System.out.println("Player 2 wins - their cards were higher value");
                } else {
                    System.out.println("Player 2 wins!");
                }
                return;
            }
        }
        System.out.println("Both players have cards of equal value - the game is a draw");
    }
}

