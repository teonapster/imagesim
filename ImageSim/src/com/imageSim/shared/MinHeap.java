package com.imageSim.shared;

public class MinHeap {
	public static int MAX_HEAP_SIZE = 2000;
    public Distance[] Heap;
    private int maxsize;
    private int size;

    public MinHeap(int max) {
	maxsize = max;
	Heap = new Distance[maxsize];
	size = -1;
    }

    private int leftchild(int pos) {
	return 2*pos;
    }
    private int rightchild(int pos) {
	return 2*pos + 1;
    }

    private int parent(int pos) {
	return  pos / 2;
    }
    
    private boolean isleaf(int pos) {
	return ((pos > size/2) && (pos <= size));
    }

    private void swap(int pos1, int pos2) {
	Distance tmp;

	tmp = Heap[pos1];
	Heap[pos1] = Heap[pos2];
	Heap[pos2] = tmp;
    }

    public void insert(Distance elem) {
    if(size>=0){
		size++;
		Heap[size] = elem;
		int current = size;
		
		while (Heap[current].getDist() < Heap[parent(current)].getDist()) {
		    swap(current, parent(current));
		    current = parent(current);
		}	
    }
    else{
    	size++;
    	Heap[0]=elem;
    }
    }

    public void print() {
	int i;
	for (i=1; i<=size;i++)
	    System.out.print(Heap[i] + " ");
	System.out.println();
    }

    public Distance removemin() {
	swap(1,size);
	size--;
	if (size != 0)
	    pushdown(1);
	return Heap[size+1];
    }

    private void pushdown(int position) {
	int smallestchild;
	while (!isleaf(position)) {
	    smallestchild = leftchild(position);
	    if ((smallestchild < size) && (Heap[smallestchild].getDist() > Heap[smallestchild+1].getDist()))
		smallestchild = smallestchild + 1;
	    if (Heap[position].getDist() <= Heap[smallestchild].getDist()) return;
	    swap(position,smallestchild);
	    position = smallestchild;
	}
    }

}
