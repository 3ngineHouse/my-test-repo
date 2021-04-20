package com.nology;

import com.nology.Card;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PokerTest {

    private static final Card club2 = new Card(2, "2", '\u2663');
    private static final Card club3 = new Card(3, "3", '\u2663');
    private static final Card club4 = new Card(4, "4", '\u2663');
    private static final Card club5 = new Card(5, "5", '\u2663');
    private static final Card club6 = new Card(6, "6", '\u2663');
    private static final Card club7 = new Card(7, "7", '\u2663');
    private static final Card club8 = new Card(8, "8", '\u2663');
    private static final Card club9 = new Card(9, "9", '\u2663');
    private static final Card club10 = new Card(10, "10", '\u2663');
    private static final Card clubJ = new Card(11, "J", '\u2663');
    private static final Card clubQ = new Card(12, "Q", '\u2663');
    private static final Card clubK = new Card(13, "K", '\u2663');
    private static final Card clubA = new Card(14, "A", '\u2663');

    private static final Card spade2 = new Card(2, "2", '\u2664');
    private static final Card spade3 = new Card(3, "3", '\u2664');
    private static final Card spade4 = new Card(4, "4", '\u2664');
    private static final Card spade5 = new Card(5, "5", '\u2664');
    private static final Card spade6 = new Card(6, "6", '\u2664');
    private static final Card spade7 = new Card(7, "7", '\u2664');
    private static final Card spade8 = new Card(8, "8", '\u2664');
    private static final Card spade9 = new Card(9, "9", '\u2664');
    private static final Card spade10 = new Card(10, "10", '\u2664');
    private static final Card spadeJ = new Card(11, "J", '\u2664');
    private static final Card spadeQ = new Card(12, "Q", '\u2664');
    private static final Card spadeK = new Card(13, "K", '\u2664');
    private static final Card spadeA = new Card(14, "A", '\u2664');

    private static final Card heart2 = new Card(2, "2", '\u2665');
    private static final Card heart3 = new Card(3, "3", '\u2665');
    private static final Card heart4 = new Card(4, "4", '\u2665');
    private static final Card heart5 = new Card(5, "5", '\u2665');
    private static final Card heart6 = new Card(6, "6", '\u2665');
    private static final Card heart7 = new Card(7, "7", '\u2665');
    private static final Card heart8 = new Card(8, "8", '\u2665');
    private static final Card heart9 = new Card(9, "9", '\u2665');
    private static final Card heart10 = new Card(10, "10", '\u2665');
    private static final Card heartJ = new Card(11, "J", '\u2665');
    private static final Card heartQ = new Card(12, "Q", '\u2665');
    private static final Card heartK = new Card(13, "K", '\u2665');
    private static final Card heartA = new Card(14, "A", '\u2665');

    private static final Card diamond2 = new Card(2, "2", '\u2666');
    private static final Card diamond3 = new Card(3, "3", '\u2666');
    private static final Card diamond4 = new Card(4, "4", '\u2666');
    private static final Card diamond5 = new Card(5, "5", '\u2666');
    private static final Card diamond6 = new Card(6, "6", '\u2666');
    private static final Card diamond7 = new Card(7, "7", '\u2666');
    private static final Card diamond8 = new Card(8, "8", '\u2666');
    private static final Card diamond9 = new Card(9, "9", '\u2666');
    private static final Card diamond10 = new Card(10, "10", '\u2666');
    private static final Card diamondJ = new Card(11, "J", '\u2666');
    private static final Card diamondQ = new Card(12, "Q", '\u2666');
    private static final Card diamondK = new Card(13, "K", '\u2666');
    private static final Card diamondA = new Card(14, "A", '\u2666');


    @Test
    void highCards_findsHighestFive() {
        ArrayList<Card> handToTest = new ArrayList<>();
        handToTest.add(diamond2);
        handToTest.add(diamond3);
        handToTest.add(heart10);
        handToTest.add(club8);
        handToTest.add(heartK);
        handToTest.add(heart5);
        handToTest.add(heart6);
        int[] expectedArray = {13, 10, 8, 6, 5};
        assertArrayEquals(expectedArray,Poker.findHighCards(handToTest));
    }

    @Test
    void findOnePair_returnsNegative(){
        ArrayList<Card> handToTest = new ArrayList<>();
        handToTest.add(heart8);
        handToTest.add(heart9);
        handToTest.add(club10);
        handToTest.add(clubQ);
        handToTest.add(diamond2);
        handToTest.add(diamond3);
        handToTest.add(diamondK);
        int[] expectedArray = {0, 0, 0, 0};
        assertArrayEquals(expectedArray, Poker.findOnePair(handToTest));
    }

    @Test
    void findOnePair_findsOnePair() {
        ArrayList<Card> handToTest = new ArrayList<>();
        handToTest.add(heart8);
        handToTest.add(heart9);
        handToTest.add(club9);
        handToTest.add(clubQ);
        handToTest.add(diamond2);
        handToTest.add(diamond3);
        handToTest.add(diamondK);
        int[] expectedArray = {9, 13, 12, 8};
        assertArrayEquals(expectedArray, Poker.findOnePair(handToTest));
    }

    @Test
    void findOnePair_handlesAThree() {
        ArrayList<Card> handToTest = new ArrayList<>();
        handToTest.add(diamond9);
        handToTest.add(heart9);
        handToTest.add(club9);
        handToTest.add(clubQ);
        handToTest.add(diamond2);
        handToTest.add(diamond3);
        handToTest.add(diamondK);
        int[] expectedArray = {9, 13, 12, 9};
        assertArrayEquals(expectedArray, Poker.findOnePair(handToTest));
    }

    @Test
    void findTwoPairs_findsTwoPairs() {
        ArrayList<Card> handToTest = new ArrayList<>();
        handToTest.add(heart8);
        handToTest.add(heart9);
        handToTest.add(club9);
        handToTest.add(clubQ);
        handToTest.add(diamond2);
        handToTest.add(diamond3);
        handToTest.add(diamondQ);
        int[] expectedArray = {12, 9, 8};
        assertArrayEquals(expectedArray, Poker.findTwoPairs(handToTest));
    }

    @Test
    void findPairs_findsBestTwoOfThreePairs() {
        ArrayList<Card> handToTest = new ArrayList<>();
        handToTest.add(heart8);
        handToTest.add(heart9);
        handToTest.add(club9);
        handToTest.add(clubQ);
        handToTest.add(clubK);
        handToTest.add(spadeK);
        handToTest.add(diamondQ);
        int[] expectedArray = {13, 12, 9};
        assertArrayEquals(expectedArray, Poker.findTwoPairs(handToTest));
    }

    @Test
    void findThrees_findsAThree(){
        ArrayList<Card> handToTest = new ArrayList<>();
        handToTest.add(heart4);
        handToTest.add(club2);
        handToTest.add(spade7);
        handToTest.add(spade4);
        handToTest.add(heartQ);
        handToTest.add(diamond4);
        assertEquals(4, Poker.findThreeOfAKind(handToTest));
    }

    @Test
    void findThrees_findsTheBestThree(){
        ArrayList<Card> handToTest = new ArrayList<>();
        handToTest.add(heart2);
        handToTest.add(clubA);
        handToTest.add(spadeA);
        handToTest.add(spade2);
        handToTest.add(heartA);
        handToTest.add(club4);
        handToTest.add(diamond2);
        assertEquals(14, Poker.findThreeOfAKind(handToTest));
    }

    @Test
    void findFullHouse_findsAFullHouse(){
        ArrayList<Card> handToTest = new ArrayList<>();
        handToTest.add(heart7);
        handToTest.add(spade7);
        handToTest.add(clubQ);
        handToTest.add(clubJ);
        handToTest.add(club7);
        handToTest.add(diamondQ);
        handToTest.add(club6);
        int[] expectedArray = {7, 12};
        assertArrayEquals(expectedArray, Poker.findFullHouse(handToTest));
    }

    @Test
    void findFullHouse_IsNotConfusedByMultiplePairs(){
        ArrayList<Card> handToTest = new ArrayList<>();
        handToTest.add(club9);
        handToTest.add(spade9);
        handToTest.add(club10);
        handToTest.add(spade10);
        handToTest.add(clubA);
        handToTest.add(spadeA);
        handToTest.add(diamond5);
        int[] expectedArray = {0, 0};
        assertArrayEquals(expectedArray, Poker.findFullHouse(handToTest));
    }

    @Test
    void findFullHouse_PicksTheRightPairWhenThreeIsHigh(){
        ArrayList<Card> handToTest = new ArrayList<>();
        handToTest.add(club9);
        handToTest.add(spade9);
        handToTest.add(club10);
        handToTest.add(spade10);
        handToTest.add(clubA);
        handToTest.add(spadeA);
        handToTest.add(diamondA);
        int[] expectedArray = {14, 10};
        assertArrayEquals(expectedArray, Poker.findFullHouse(handToTest));
    }

    @Test
    void findFullHouse_PicksTheRightPairWhenThreeIsLow(){
        ArrayList<Card> handToTest = new ArrayList<>();
        handToTest.add(club9);
        handToTest.add(spade9);
        handToTest.add(club10);
        handToTest.add(spade10);
        handToTest.add(clubA);
        handToTest.add(spadeA);
        handToTest.add(diamond9);
        int[] expectedArray = {9, 14};
        assertArrayEquals(expectedArray, Poker.findFullHouse(handToTest));
    }

    @Test
    void findFourOfAKind_FindsAFour(){
        ArrayList<Card> handToTest = new ArrayList<>();
        handToTest.add(heart7);
        handToTest.add(spade7);
        handToTest.add(clubQ);
        handToTest.add(clubJ);
        handToTest.add(club7);
        handToTest.add(diamond7);
        handToTest.add(club6);
        assertEquals(7, Poker.findFourOfAKind(handToTest));
    }

    @Test
    void findFourOfAKind_FindsTheBestFour(){
        ArrayList<Card> handToTest = new ArrayList<>();
        handToTest.add(heart2);
        handToTest.add(spade2);
        handToTest.add(diamond2);
        handToTest.add(club2);
        handToTest.add(heart3);
        handToTest.add(spade3);
        handToTest.add(diamond3);
        handToTest.add(club3);
        assertEquals(3, Poker.findFourOfAKind(handToTest));
    }

    @Test
    void findFlush_FindsAFlush(){
        ArrayList<Card> handToTest = new ArrayList<>();
        handToTest.add(spade3);
        handToTest.add(spade6);
        handToTest.add(spade10);
        handToTest.add(spadeJ);
        handToTest.add(spadeK);
        handToTest.add(diamondQ);
        handToTest.add(club5);
        int[] expected = {13,11,10,6,3};
        assertArrayEquals(expected, Poker.findFlush(handToTest));
    }

    @Test
    void findFlush_FindsTheBestFlush(){
        ArrayList<Card> handToTest = new ArrayList<>();
        handToTest.add(spade3);
        handToTest.add(spade6);
        handToTest.add(spade10);
        handToTest.add(spadeJ);
        handToTest.add(spadeK);
        handToTest.add(spadeQ);
        handToTest.add(spade5);
        int[] expected = {13,12,11,10,6};
        assertArrayEquals(expected, Poker.findFlush(handToTest));
    }

    @Test
    void findStraight_FindsAStraight(){
        ArrayList<Card> handToTest = new ArrayList<>();
        handToTest.add(heartA);
        handToTest.add(diamond6);
        handToTest.add(diamond7);
        handToTest.add(diamond8);
        handToTest.add(club2);
        handToTest.add(diamond9);
        handToTest.add(diamond10);
        assertEquals(10, Poker.findStraight(handToTest));
    }


    @Test
    void findStraight_FindsBestStraight(){
        ArrayList<Card> handToTest = new ArrayList<>();
        handToTest.add(heartJ);
        handToTest.add(diamond6);
        handToTest.add(diamond7);
        handToTest.add(diamond8);
        handToTest.add(clubQ);
        handToTest.add(diamond9);
        handToTest.add(diamond10);
        assertEquals(12, Poker.findStraight(handToTest));
    }

    @Test
    void findStraightFlush_findsAStraightFlush(){
        ArrayList<Card> handToTest = new ArrayList<>();
        handToTest.add(heartA);
        handToTest.add(diamond6);
        handToTest.add(diamond7);
        handToTest.add(diamond8);
        handToTest.add(club2);
        handToTest.add(diamond9);
        handToTest.add(diamond10);
        assertEquals(10, Poker.findStraightFlush(handToTest));
    }

    @Test
    void findStraightFlush_findsStraightFlushWhenThereIsHigherNonFlushStraight(){
        ArrayList<Card> handToTest = new ArrayList<>();
        handToTest.add(diamondJ);
        handToTest.add(diamond6);
        handToTest.add(diamond7);
        handToTest.add(diamond8);
        handToTest.add(clubQ);
        handToTest.add(diamond9);
        handToTest.add(diamond10);
        assertEquals(11, Poker.findStraightFlush(handToTest));
    }

    @Test
    void findRoyalFlush_findsRoyalFlush(){
        ArrayList<Card> handToTest = new ArrayList<>();
        handToTest.add(spadeA);
        handToTest.add(spadeK);
        handToTest.add(spadeQ);
        handToTest.add(spadeJ);
        handToTest.add(spade10);
        handToTest.add(spade9);
        handToTest.add(spade8);
        assertTrue(Poker.findRoyalFlush(handToTest));
    }
}