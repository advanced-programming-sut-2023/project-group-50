package view;

import controller.Menus.TradeMenuController;
import controller.control.Commands;
import controller.control.Error;

import java.util.Scanner;
import java.util.regex.Matcher;

public class TradeMenu {
    private final TradeMenuController tradeMenuController;

    public TradeMenu(TradeMenuController tradeMenuController) {
        this.tradeMenuController = tradeMenuController;
    }

    public void run(Scanner scanner) {
        String line;
        Matcher matcher;

        while (true) {
            line = scanner.nextLine();

            if ((matcher = Commands.getMatcher(Commands.NEW_TRADE, line)).matches()) {
                System.out.println(newTrade(matcher));
            } else if ((matcher = Commands.getMatcher(Commands.TRADE_ACCEPT, line)).matches()) {
                System.out.println(acceptTrade(matcher));
            } else if (Commands.getMatcher(Commands.TRADE_LIST, line).matches()) {
                System.out.println(showAllTrades());
            } else if (Commands.getMatcher(Commands.TRADE_HISTORY, line).matches()) {
                System.out.println(showMyTradesHistory());
            } else {
                System.out.println("Invalid command!");
            }
        }
    }

    private String newTrade(Matcher matcher) {
        Error typeError = resourceTypeError(matcher.group("resourceType"));
        Error amountError = resourceAmountError(matcher.group("resourceAmount"));
        Error priceError = priceError(matcher.group("price"));

        if (!typeError.truth) return typeError.errorMassage;
        if (!amountError.truth) return typeError.errorMassage;
        if (!priceError.truth) return typeError.errorMassage;

        tradeMenuController.newTrade(matcher.group("resourceType"),
                                     Integer.parseInt(matcher.group("resourceAmount")),
                                     Integer.parseInt(matcher.group("price")),
                                     matcher.group("message")
        );

        return "Trade added successfully!";
    }

    private String acceptTrade(Matcher matcher) {
        Error idError = idError(matcher.group("id"));

        if (!idError.truth) return idError.errorMassage;

        tradeMenuController.acceptTrade(Integer.parseInt(matcher.group("id")),
                                        matcher.group("message"));

        return "Trade accepted successfully!";
    }

    private String showAllTrades() {
        return tradeMenuController.showTrades();
    }

    private String showMyTradesHistory() {
        return tradeMenuController.showTradeHistory();
    }

    private Error resourceTypeError(String resourceName) {
        return new Error("Resource name is invalid", TradeMenuController.resourceNameIsValid(resourceName));
    }

    private Error resourceAmountError(String resourceAmount) {
        return new Error("Resource amount is invalid", Integer.parseInt(resourceAmount) > 0);
    }

    private Error priceError(String price) {
        return new Error("price is invalid", Integer.parseInt(price) >= 0);
    }

    private Error idError(String id) {
        return new Error("Trade id does not exist", tradeMenuController.idIsValid(Integer.parseInt(id)));
    }
}
