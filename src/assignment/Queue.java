package assignment;

class Queue {
	
    private int front;
    private int rear;
    private int capacity;
    private int array[];

    public Queue(int capacity)
    {
        this.capacity = capacity+1;
        rear = front = 0;
        array = new int[this.capacity];
    }

    boolean isEmpty() { 
    	return front==rear; }
    
    int size() {
    	if(rear>=front) {
    		return rear - front;
    	}else {
    		return (capacity-front+rear);
    	}
    }

    boolean isFull() { return (size()==capacity-1); }

    void enqueue(int item) {
        if (isFull())
            return;
        this.array[this.rear] = item;
        this.rear = (this.rear + 1) % (this.capacity);
        
    }

    int dequeue() {
        if (isEmpty())
            return Integer.MIN_VALUE;
        int item = this.array[this.front];
        this.front = (this.front + 1)  % this.capacity;
        return item;
    }

    int front() {
        if (isEmpty())
            return -1;
        return this.array[this.front];
    }

    int rear() {
        if (isEmpty())
            return -1;
        return this.array[ (this.rear-1) == -1 ? this.capacity-1:this.rear-1];
    }
    void print(){
        for(int i = front;i<rear;i++){
            System.out.print(" "+ this.array[i]);
        }
    }
}