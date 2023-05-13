package controller.Menus;

import controller.UserDatabase.User;
import controller.UserDatabase.Users;
import model.Map.Map;
import model.ObjectsPackage.Resource;
import model.Trade.Trade;
import model.Trade.TradeMarket;
import model.UserColor.UserColor;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class TradeMenuControllerTest {

    @Test
    void resourceNameIsValid() {
        assertTrue(TradeMenuController.resourceNameIsValid("Bread"));
        assertTrue(TradeMenuController.resourceNameIsValid("Wheat"));
        assertTrue(TradeMenuController.resourceNameIsValid("Wood"));
        assertTrue(TradeMenuController.resourceNameIsValid("Stone"));
        assertTrue(TradeMenuController.resourceNameIsValid("Ale"));

        assertFalse(TradeMenuController.resourceNameIsValid("Spaghetti"));
        assertFalse(TradeMenuController.resourceNameIsValid("Mineral Water"));
        assertFalse(TradeMenuController.resourceNameIsValid("Mojito"));
        assertFalse(TradeMenuController.resourceNameIsValid("Lasagna"));
        TradeMarket.clear();
    }

    @Test
    void newTrade() {
        User sahand = new User("shndap",
                               "haha12345",
                               "Sahand AP",
                               "Sahand@gmail.com",
                               "Suffer!",
                               20,
                               20,
                               UserColor.PURPLE, new Map(400, 400));

        TradeMenuController controller = new TradeMenuController(sahand);

        controller.newTrade("Ale", 45, 1300, "Oy! I'm selling ale.");
        assertEquals(1, sahand.getTrades().size());

        controller.newTrade("Wood", 544, 12, "Oy! I'm selling wood.");
        assertEquals(2, sahand.getTrades().size());

        ArrayList<Trade> trades = new ArrayList<>(sahand.getTrades().values());

        assertEquals(45, trades.get(0).getResourceAmount());
        assertSame(trades.get(0).getResourceType(), Resource.ALE);
        assertEquals(1300, trades.get(0).getPrice());
        assertEquals(trades.get(0).getFrom(), sahand);
        assertNull(trades.get(0).getTo());
        assertSame(trades.get(0).getMessage(), "Oy! I'm selling ale.");

        assertEquals(544, trades.get(1).getResourceAmount());
        assertSame(trades.get(1).getResourceType(), Resource.WOOD);
        assertEquals(12, trades.get(1).getPrice());
        assertEquals(trades.get(1).getFrom(), sahand);
        assertNull(trades.get(1).getTo());
        assertSame(trades.get(1).getMessage(), "Oy! I'm selling wood.");


        assertNotEquals(trades.get(0).getId(), trades.get(1).getId());
        TradeMarket.clear();
    }

    @Test
    void showTrades() {
        User sahand = new User("shndap",
                               "haha12345",
                               "Sahand AP",
                               "Sahand@gmail.com",
                               "Suffer!",
                               20,
                               20,
                               UserColor.PURPLE, new Map(400, 400));

        User alireza = new User("alirezaA",
                               "haha123456",
                               "AA",
                               "aa@gmail.com",
                               "Die in silence!",
                               30,
                               30,
                               UserColor.BLACK, new Map(400, 400));

        TradeMenuController SahandsController = new TradeMenuController(sahand);
        TradeMenuController AAsController = new TradeMenuController(alireza);

        SahandsController.newTrade("Ale", 45, 1300, "Oy! I'm selling ale.");
        SahandsController.newTrade("Wood", 544, 12, "Oy! I'm selling wood.");

        AAsController.newTrade("Wheat", 54, 125, "Hi! I'm selling fine wheat.");
        AAsController.newTrade("Stone", 5, 12000, "Hey! I'm selling stone.");

        ArrayList<String> trades = new ArrayList<>();
        for(Trade trade : sahand.getTrades().values()) trades.add(trade.toString());
        for(Trade trade : alireza.getTrades().values()) trades.add(trade.toString());
        String join = String.join("\n", trades);

        assertEquals(join, AAsController.showTrades());
        assertEquals(join, SahandsController.showTrades());

        User behroozeMadarKharab = new User("bhrz",
                               "mdrkhrb",
                               "bhrzzzzz",
                               "b@gmail.com",
                               "WTF!",
                               223,
                               212,
                               UserColor.WHITE, new Map(400, 400));

        assertEquals(new TradeMenuController(behroozeMadarKharab).showTrades(), join);

        TradeMarket.clear();
    }

    @Test
    void acceptTrade() {
        User sahand = new User("shndap",
                               "haha12345",
                               "Sahand AP",
                               "Sahand@gmail.com",
                               "Suffer!",
                               20,
                               20,
                               UserColor.PURPLE, new Map(400, 400));

        User alireza = new User("alirezaA",
                                "haha123456",
                                "AA",
                                "aa@gmail.com",
                                "Die in silence!",
                                30,
                                30,
                                UserColor.BLACK, new Map(400, 400));

        TradeMenuController SahandsController = new TradeMenuController(sahand);
        TradeMenuController AAsController = new TradeMenuController(alireza);

        SahandsController.newTrade("Ale", 45, 1300, "Oy! I'm selling ale.");
        SahandsController.newTrade("Wood", 544, 12, "Oy! I'm selling wood.");

        AAsController.newTrade("Wheat", 54, 125, "Hi! I'm selling fine wheat.");
        AAsController.newTrade("Stone", 5, 12000, "Hey! I'm selling stone.");

        alireza.getGovernment().setCoins(11);
        ArrayList<Trade> sahandTrades = new ArrayList<>(sahand.getTrades().values());
        assertThrows(AssertionError.class,
                     () -> AAsController.acceptTrade(sahandTrades.get(1).getId(), "Thank you for the wood!"));
        assertThrows(AssertionError.class,
                     () -> AAsController.acceptTrade(0, "Thank you for the wood!"));

        alireza.getGovernment().setCoins(100000);
        AAsController.acceptTrade(sahandTrades.get(1).getId(), "Thank you for the wood!");

        assertEquals(sahand.getGovernment().getCoins(), 12);
        assertEquals(alireza.getGovernment().getCoins(), 100000 - 12);
        assertSame(sahand.getTrades().get(sahandTrades.get(1).getId()).getMessage(), "Thank you for the wood!");
        assertSame(sahand.getTrades().get(sahandTrades.get(1).getId()).getTo(), alireza);
        assertTrue(alireza.getTrades().containsKey(sahandTrades.get(1).getId()));

        assertNotSame(AAsController.showTradeHistory(), SahandsController.showTradeHistory());

        TradeMarket.clear();
    }

    @Test
    void idIsValid() {
        User sahand = new User("shndap",
                               "haha12345",
                               "Sahand AP",
                               "Sahand@gmail.com",
                               "Suffer!",
                               20,
                               20,
                               UserColor.PURPLE, new Map(400, 400));

        TradeMenuController SahandsController = new TradeMenuController(sahand);

        SahandsController.newTrade("Ale", 45, 1300, "Oy! I'm selling ale.");
        SahandsController.newTrade("Wood", 544, 12, "Oy! I'm selling wood.");

        assertFalse(SahandsController.idIsValid(-12));
        assertTrue(SahandsController.idIsValid(new ArrayList<Trade>(sahand.getTrades().values()).get(0).getId()));
        TradeMarket.clear();
    }
}