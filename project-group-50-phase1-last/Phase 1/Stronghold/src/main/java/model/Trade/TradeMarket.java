package model.Trade;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class TradeMarket {
    private static LinkedHashMap<Integer, Trade> trades;
    private static int nextId;

    public TradeMarket() {
        trades = new LinkedHashMap<>();
        nextId = 0xABCDEF;
    }

    public static void addTrade(Trade trade) {
        trades.put(nextId++, trade);
    }

    public static void removeTrade(int id) {
        trades.remove(id);
    }

    public static ArrayList<String> getTradesAsString() {
        ArrayList<String> list = new ArrayList<>();

        for (Trade trade : trades.values())
            list.add(trade.toString());

        return list;
    }

    public static int getNextId() {
        return nextId;
    }

    public static void setNextId(int nextId) {
        TradeMarket.nextId = nextId;
    }

    public static boolean idIsValid(int id) {
        return trades.containsKey(id);
    }

    public static Trade getTrade(int id) {
        return trades.get(id);
    }

    public static LinkedHashMap<Integer, Trade> getTrades() {
        return trades;
    }

    public static void setTrades(LinkedHashMap<Integer, Trade> trades) {
        TradeMarket.trades = trades;
    }
}
