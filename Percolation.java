// Out of boundaries java.lang.IndexOutOfBoundsException
public class Percolation {

	private int i, j, N, NN, p, q;
	private boolean[] grid;
	private WeightedQuickUnionPathCompressionUF uf;

	public Percolation(int N) { // create N-by-N grid, with all sites blocked
		grid = new boolean[N * N];
		this.N = N;
		NN = this.N * this.N;
		initGrid();
		uf = new WeightedQuickUnionPathCompressionUF(NN);
	}

	public void open(int i, int j) { // open site (row i, column j) if it is not
										// already // // already

		// Open the site
		// The given id is p var and if exists
		p = getId(i, j);

		grid[p] = true;

		// Union with the possibly four neighbours

		int[] neighbours = getNeighbours(p);

		for (int z = 0; z < 4; z++)
			if (neighbours[z] > 0)
				uf.union(p, neighbours[z]);

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

		
		for (int a = 0; a < N; N++)
			for (int s = NN - N; s < NN; s++){
				if(uf.connected(a, s)){
					return true;
				}
			}
				
				return false;
	}

	private int getId(int i, int j) {

		if ((i * j - 1) > NN || 0 > i || i > N || j < 0 || j > N) {
			throw new java.lang.IndexOutOfBoundsException();
		}

		return (((i - 1) * this.N) + j);

	}

	private void initGrid() {
		int k, m;
		for (k = 0; k < N; k++)
			for (m = 0; m < N; m++)
				grid[getId(k, m)] = false;

	}

	private int[] getNeighbours(int id) {

		/*
		 * UP: id=0
		 * 
		 * Left: id=1 Right: id=2
		 * 
		 * Down: id=3
		 * 
		 * The neighbour[id] == grid[id]
		 */

		int[] neigbour = new int[4];

		// Initialise
		for (int q = 0; q < 4; q++)
			neigbour[q] = -1;

		// UP
		if (id - this.N >= 0) {

			neigbour[0] = id - this.N;
		}
		// Left
		if (id % N != 0) {

			neigbour[1] = id - 1;
		}
		// Right
		if (id % N == N - 1) {

			neigbour[2] = id + 1;
		}
		// Down
		if (!((id + N) < NN)) {
			neigbour[3] = id + this.N;
		}

		return neigbour;
	}

}
