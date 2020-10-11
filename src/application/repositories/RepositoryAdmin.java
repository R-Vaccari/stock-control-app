package application.repositories;

public class RepositoryAdmin {

    static final StockItemRepository stockItemRepository = new StockItemRepository();
    static final UserRepository userRepository = new UserRepository();

    public static StockItemRepository getStockItemRepository() { return stockItemRepository; }

    public static UserRepository getUserRepository() { return userRepository; }
}
