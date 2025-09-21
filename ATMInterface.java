import java.util.Scanner;
public class ATMInterface{
    static Scanner sc = new Scanner(System.in);
    static User[] users = new User[5];
    static int userCount =0;
    static User loginUser(){
        int attempts = 3;
        while(attempts>0){
            System.out.print("Enter userId: ");
            String u = sc.nextLine().trim();

            System.out.print("Enter pin: ");
            String p = sc.nextLine().trim();

            for(int i=0;i<userCount;i++){
                if(users[i].userId.equals(u) && users[i].pin.equals(p)){
                    return users[i];
                }
                
            }
            attempts--;
            System.out.println("Invalid,You have attempts:"+attempts);
        }
        return null;
    }
    static void showHistory(User user){
        if(user.transactionsCount == 0){
            System.out.println("No Transactions yet");
        }
        else{
            System.out.println("\n---Transaction History");
            for(int i=0;i<user.transactionsCount;i++){
                System.out.println(user.transactions[i]);
            }
        }
    }
    static void withdraw(User user){
        System.out.print("Enter amount to withdraw: ");
        double amount = Double.parseDouble(sc.nextLine().trim());
        if(amount<=0){
            System.out.println("Invalid amount,must be greater than 0");
            return;
        }
        if(amount>user.balance){
            System.out.println("Insuffucient balance! Current Balance: "+user.balance);
        }
        //Deduct from amount
        user.balance = user.balance-amount;
        user.addTransaction("withdraw Succesfull.Remaining balance: "+user.balance);
        }
        static void deposit(User user){
            System.out.println("Enter amount to Deposit:");
            double depamt = Double.parseDouble(sc.nextLine().trim());
            if(depamt<=0){
                System.out.println("invalid amount,should be greater than 0");
                return ;
            }
            // Add deposit amount to balance
            user.balance = user.balance+depamt;
            user.addTransaction("Deposit: "+depamt+" balance :"+user.balance);
            System.out.println("Deposit succesful.New Balance: "+user.balance);
        }
        static void transfer(User user){
            System.out.println("Enter recipient User Id: ");
            String recId = sc.nextLine().trim();
            //Find recepient
            User recepient = null;
            for(int i=0;i<userCount;i++){
                if(users[i].userId.equals(recId)){
                    recepient = users[i];
                    break;
                }
            }
            if(recepient == null){
                System.out.println("Recipient not found.");
                return;
            }
            if(recepient.userId.equals(user.userId)){
                System.out.println("Cannot Transfer to same amount.");
                return;
            }
            //Enter amount
            System.out.print("Enter amount to transfer:");
            double recamt = Double.parseDouble(sc.nextLine().trim());
            if(recamt<=0){
                System.out.println("Invalid amount, should be greater than 0");
                return;
            }
            if(recamt>user.balance){
                System.out.println("Insufficient balance, Current balance: "+user.balance);
                return;
            }
            // Transfer amount
            user.balance = user.balance-recamt;
            recepient.balance = recepient.balance+recamt;
            //Log Transactions
            user.addTransaction("Transfer to "+recepient.userId+": "+recamt+" | Balance:"+user.balance);
            System.out.println("Transfer succesful.you new balance : "+user.balance);
        }
        public static void main(String[] args){
            // Adding Demo Users
            users[userCount++] = new User("user1","123",10000);
            users[userCount++] = new User("user2","2345",15000);
            System.out.println("<---Welcome to the ATM System--->");
            //Here User need to login First
            User current = loginUser();
            if(current==null){
                System.out.println("Exiting System");
                return;
            }
            System.out.println("Login Success, Welcome to the Menu "+current.userId);
            int choice;
            do{
                System.out.println("1.Transaction History");
                System.out.println("2. withdraw");
                System.out.println("3.Deposit");
                System.out.println("4.Transfer");
                System.out.println("5.Quit");
                System.out.println("Enter Choice:");
                choice = Integer.parseInt(sc.nextLine().trim());
                switch(choice){
                    case 1:
                        showHistory(current);
                        break;
                    case 2:
                        withdraw(current);
                        break;
                    case 3:
                        deposit(current);
                        break;
                    case 4:
                        transfer(current);
                        break;
                    case 5:
                        System.out.println("Quit");
                        break;
                    default:System.out.println("Invalid , Try again");
                }

            }
            while(choice != 5);
        }
}
// User class storing User id and pin
class User{
    String userId;
    String pin;
    double balance;
    String[] transactions;
    int transactionsCount;
    //Construcctor
    public User(String userId,String pin,double balance){
        this.userId = userId;
        this.pin = pin;
        this.balance = balance;
        this.transactions = new String[50];
        this.transactionsCount=0;
    }
    public void addTransaction(String message){
        if(transactionsCount<transactions.length){
            transactions[transactionsCount++]= message;
        }
        else{
            for(int i=1;i<transactions.length;i++){
                transactions[i-1] = transactions[i];
            }
            transactions[transactions.length-1] = message;
        }
    }
}