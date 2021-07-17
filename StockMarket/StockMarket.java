import java.util.Scanner;

/* A program which takes the transaction as input in the given forms below,
 * extracts how many shares were bought/sold for what price and outputs the 
 * total gain/loss for the transactions.
 */
public class StockMarket {
	public static void main(String[] args) {
		//Initialising the variables
		Scanner sc = new Scanner(System.in);
		Queue<Transaction> q = new LinkedQueue<Transaction>();
		int total = 0; //total number of shares
		int totalGain = 0; //total gain/loss
		
		//Setting the rules
		System.out.println("Please enter the transactions in the following format:");
		System.out.println("\"buy x share(s) at $y each\" or \"sell x share(s) at $y each\"");
		
		
		//Main loop
		int day = 1;
		char c = 'y';
		while (c == 'y') {
			System.out.println("Day " + day);
			
			System.out.println("Input your transaction:");
			String [] line = sc.nextLine().split(" ");
			
			//Extracting amount and price
			int amount = Integer.parseInt(line[1]);
			int price = Integer.parseInt(line[4]);
			
			//Either buy or sell shares
			switch (line[0]) {
				case "buy":
					//If buy, then increment total and enqueue a new transaction
					q.enqueue(new Transaction(amount, price));
					total += amount;
					totalGain -= amount * price;
				case "sell":
					//If the transaction requires more shares than total, repeat the day
					if (amount > total) {
						System.out.println("Cannot sell more than " + total + " shares");
						continue;
					}
					//Decrease the total
					total -= amount;
					
					//Calculate the gain/loss
					int gain = 0;
					while (amount > 0) {
						Transaction t = q.first();
						if (t.getAmount() > amount) {
							gain += amount * (t.getPrice() - price);
							t.setAmount(t.getAmount() - amount);
						} else {
							gain += t.getAmount() * (t.getPrice() - price);
							q.dequeue();
						}
						amount -= t.getAmount();
					}
					//Increase the total gain/loss
					totalGain += gain;
					System.out.println("Capital " + ((gain < 0)? "loss: -": "gain: ") +"$" + Math.abs(gain));
			}
			
			System.out.println("Total " + ((totalGain < 0)? "loss: -": "gain: ") +"$" + Math.abs(totalGain));
			
			System.out.println("Continue (y, n):");
			c = sc.next().charAt(0);

			//Increment the day
			day++;
		}
		
		//Closing the scanner
		sc.close();
	}
}
