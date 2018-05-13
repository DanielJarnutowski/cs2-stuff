class LLStackADT implements LLStack{
	private Node top;
	private int size;
	
	public LLStackADT() {
		top = null;
		size = 0;
	}
	
	public boolean empty() {
		return (top == null);
	}
	
	public int size () {
		return size;
	}
	
	public void push (String number) {
		Node newNode = new Node();
		newNode.setData(number);
		newNode.setNext(top);;
		top = newNode;
		size++;
	}
	
	public String pop() {
		String i;
		i = top.getData();
		top = top.getNext();
		size--;
		return i;
	}
	
	public String onTop() {
		String i = pop();
		push(i);
		return i;
	}
}