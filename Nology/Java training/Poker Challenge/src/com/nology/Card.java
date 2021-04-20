package com.nology;

public class Card implements Comparable<Card>{
    private int value;
    private String symbol;
    private Character suit;

    public Card(int value, String symbol, Character suit) {
        this.value = value;
        this.symbol = symbol;
        this.suit = suit;
    }

    public int getValue() {
        return value;
    }

    public String getSymbol() {
        return symbol;
    }

    public Character getSuit() {
        return suit;
    }

    @Override
    public int compareTo(Card otherCard) {
        if (this.value<otherCard.getValue()){
            return -1;
        } else if (this.value==otherCard.getValue()){
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public String toString() {
        return symbol + suit;
    }
}
