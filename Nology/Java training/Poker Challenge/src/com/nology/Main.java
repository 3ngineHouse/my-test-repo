package com.nology;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import static com.nology.Poker.*;
import static com.nology.Poker.deal;


public class Main {

    public static void main(String[] args) {


        // lets build Poker!
        // a player has a hand of 5 cards
        // from those 5 cards, they must make the hand with the highest possible score
        // different hand types are worth different scores
        // link to rules

        // class called Poker (holds the engines)
        // class called Card (simple Card structure)

        Poker pokerEngine = new Poker();
        ArrayList<Card> shuffledDeck = shuffled(freshDeck());

        // SevenCardStud(shuffledDeck);

        TexasHoldEm(shuffledDeck);

    }

    public static void SevenCardStud(ArrayList<Card> deck){
        ArrayList<Card> handOne = deal(deck, 7);
        ArrayList<Card> handTwo = deal(deck, 7);

        Collections.sort(handOne);
        Collections.sort(handTwo);

        System.out.println("\nPlayer 1's hand:");
        handOne.forEach(System.out::println);
        System.out.println("\nPlayer 2's hand:");
        handTwo.forEach(System.out::println);

        System.out.println("");
        compareTwoHands(handOne, handTwo);
    }

    public static void TexasHoldEm(ArrayList<Card> deck){
        ArrayList<Card> table = deal(deck, 5);
        ArrayList<Card> handOne = deal(deck, 2);
        ArrayList<Card> handTwo = deal(deck, 2);

        System.out.println("\nCards on the table:");
        table.forEach(System.out::println);
        System.out.println("\nPlayer 1's hand:");
        handOne.forEach(System.out::println);
        System.out.println("\nPlayer 2's hand:");
        handTwo.forEach(System.out::println);

        handOne.addAll(table);
        handTwo.addAll(table);

        System.out.println("");
        compareTwoHands(handOne, handTwo);
    }
}

