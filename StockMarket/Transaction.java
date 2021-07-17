public class Transaction {
	private int amount;
	private int price;
	
	//Constructors, getters, setters
	
	public Transaction(int amount, int price) {
		this.amount = amount;
		this.price = price;
	}

	public int getAmount() { return amount; }
	public void setAmount(int amount) { this.amount = amount; }
	public int getPrice() { return price; }
	public void setPrice(int price) { this.price = price; }
}
