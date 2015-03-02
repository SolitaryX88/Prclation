
public class Percolation {

	private int N, NN, p;
	private boolean[] grid;
	private WeightedQuickUnionUF uf;

	public Percolation(int N) { // create N-by-N grid, with all sites blocked
		grid = new boolean[N * N];
		this.N = N;
		NN = this.N * this.N;
		initGrid();
		uf = new WeightedQuickUnionUF(NN);
	}

	public void open(int i, int j) {
		// open site (row i, column j) if it is not
		// already open

		// Open the site
		// The given id is p var and if exists
		p = getId(i, j);

		grid[p] = true;

		// Union with the possibly four neighbours

		int[] neighbours = getNeighbours(p);

		for (int z = 0; z < 4; z++)
			if (neighbours[z] > -1) {
				uf.union(p, neighbours[z]);

			}
	}

	public boolean isOpen(int i, int j) { // is site (row i, column j) open?
		return grid[getId(i, j)];
	}

	public boolean isFull(int i, int j) { // is site (row i, column j) full?
		return grid[getId(i, j)];
	}

	public boolean percolates() { // does the system percolate?
		// ids from 0 to (N-1) -> p
		// && ids from NN-N to NN-1
		// One of first ids with one of the
		// last ids must be connected to be percolated

		for (int i = 0; i < N; i++)
			for (int j = NN - N; j < NN; j++) {
				if (uf.connected(j, i)) {	
					return true;
				}
			}
		return false;
	}

	private int getId(int i, int j) {

		if ((i * j - 1) > NN || i < 1 || i > N || j < 1 || j > N) {
			throw new java.lang.IndexOutOfBoundsException();
		}

		return (((j - 1) * this.N) + i - 1);

	}

	private void initGrid() {

		for (int k = 0; k < NN; k++)
			grid[k] = false;
	}

	private int[] getNeighbours(int id) {

		int[] neigbour = new int[4];

		// Initialise
		for (int q = 0; q < 4; q++)
			neigbour[q] = -1;

		// Up
		if (id > N - 1)
			neigbour[0] = id - this.N;

		// Left
		if (id % N != 0)
			neigbour[1] = id - 1;

		// Right
		if ((id + 1) % N != 0)
			neigbour[2] = id + 1;

		// Down
		if (id < NN - N)
			neigbour[3] = id + this.N;

		return neigbour;
	}

}
