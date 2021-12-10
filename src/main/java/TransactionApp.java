import java.time.LocalDate;
import java.util.Scanner;

public class TransactionApp {

    private static final TransactionDAO DAO = new TransactionDAO();
    private static final String ADD_TRANSACTION = "1";
    private static final String MODIFY_TRANSACTION = "2";
    private static final String DELETE_TRANSACTION = "3";
    private static final String DISPLAY_INCOMES = "4";
    private static final String DISPLAY_EXPENSES = "5";
    private static final String EXIT = "0";


    private Scanner scanner;

    public TransactionApp() {
        this.scanner = new Scanner(System.in);
    }

    public void run() {

        while (true) {
            printMenu();
            switch (scanner.nextLine()) {
                case ADD_TRANSACTION:
                    addNewTransaction();
                    break;
                case MODIFY_TRANSACTION:
                    modifyTransaction();
                    break;
                case DELETE_TRANSACTION:
                    deleteTransaction();
                    break;
                case DISPLAY_INCOMES:
                    displaySumAmount(TransactionType.INCOME);
                    break;
                case DISPLAY_EXPENSES:
                    displaySumAmount(TransactionType.EXPENSE);
                    break;
                case EXIT:
                    DAO.close();
                    return;
                default:
                    System.out.println("Podałeś nieprawidłową opcję");
            }
        }
    }

    private void modifyTransaction() {
        System.out.println("Podaj id transakcji, którą chcesz zmodyfikować");
        Integer id = scanner.nextInt();
        scanner.nextLine();
        Transaction transaction = transactionCreator();
        transaction.setId(id);
        DAO.update(transaction);
    }


    private void deleteTransaction() {
        System.out.println("Podaj id transakcji");
        Integer id = scanner.nextInt();
        scanner.nextLine();
        DAO.delete(id);

    }

    private void addNewTransaction() {

        Transaction transaction = DAO.save(transactionCreator());
        System.out.printf("Obiekt został zapisany z id %s \n", transaction.getId());

    }

    private void displaySumAmount(TransactionType transactionType) {
        Double byTransactionType = DAO.findByTransactionType(transactionType);

        System.out.println("Suma to: " + byTransactionType);
    }


    private Transaction transactionCreator() {
        boolean doTheLoop = true;
        TransactionType type = null;

        while (doTheLoop) {
            System.out.println("Podaj typ transakcji");
            System.out.println("1. Przychód");
            System.out.println("2. Wydatek");
            String option = scanner.nextLine();
            if (option.equals("1")) {
                type = TransactionType.INCOME;
                doTheLoop = false;
            } else if (option.equals("2")) {
                type = TransactionType.EXPENSE;
                doTheLoop = false;
            } else {
                System.out.println("Podana wartość jest nieprawidłowa. Spróbuj ponownie");
            }
        }
        System.out.println("Podaj opis transakcji");
        String description = scanner.nextLine();
        System.out.println("Podaj kwotę transakcji");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        return new Transaction(type, description, amount, LocalDate.now());
    }

    private void printMenu() {
        System.out.println("1. Dodaj transakcję");
        System.out.println("2. Zmodyfikuj transakcję");
        System.out.println("3. Usuń transakcję");
        System.out.println("4. Wyświetl wszystkie przychody");
        System.out.println("5. Wyświetl wszystkie wydatki");
        System.out.println("0. Zakończ program");
    }

}