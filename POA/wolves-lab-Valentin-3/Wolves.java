import javax.swing.*;
import java.util.*;

public class Wolves {

    public int getNumWolves() {
		return numWolves;
	}

	public void setNumWolves(int numWolves) {
		this.numWolves = numWolves;
	}

	public int getNumPreys() {
		return numPreys;
	}

	public void setNumPreys(int numPreys) {
		this.numPreys = numPreys;
	}

	private int numWolves;
    private int numPreys;
    private int visibility;
    private int minCaptured;
    private int min_surround;
    public int[][] grid;
    private int rows, cols;
    private int[] wolfRow = new int[numWolves];
    private int[] wolfCol = new int[numWolves];
    private int[] preyRow = new int[numPreys];
    private int[] preyCol = new int[numPreys];
    private Wolf[] wolves = new Wolf[numWolves];
    private List<Integer> capturedList = new ArrayList<>();
    private Random r = new Random();
    private WolvesUI visuals;
    private long tickcounter = 0;
    private int strategy = 0;
    
    public Wolves() {
    	
    }
    public Wolves(int rows, int cols, int numWolves, int numPreys, int visibility, int minCaptured, int min_surround,int test) {
        this.rows = rows;
        this.cols = cols;
        this.numWolves = numWolves;
        this.numPreys = numPreys;
        
        //System.out.printf("Wolves : NumPreys = %d%n",this.getNumPreys());
		//System.out.printf("Wolves : NumWolves = %d%n",this.getNumWolves());
		//System.out.println();
		
        this.visibility = visibility;
        this.minCaptured = minCaptured;
        this.min_surround = min_surround;
        grid = new int[rows][cols];

        wolfRow = new int[numWolves];
        wolfCol = new int[numWolves];
        preyRow = new int[numPreys];
        preyCol = new int[numPreys];
        wolves = new Wolf[numWolves];

        for (int i = 0; i < numWolves; i++) {
            do {
                wolfRow[i] = r.nextInt(rows);
                wolfCol[i] = r.nextInt(cols);
            } while (!empty(wolfRow[i], wolfCol[i]));
            grid[wolfRow[i]][wolfCol[i]] = i * 2 + 1;
        }
        for (int i = 0; i < numPreys; i++) {

            int preyR;
            int preyC;
            do {
                preyR = r.nextInt(rows);
                preyC = r.nextInt(cols);
            } while (!empty(preyR, preyC)
                    || captured(preyR, preyC));
            preyRow[i] = preyR;
            preyCol[i] = preyC;
            grid[preyR][preyC] = i * 2 + 2;
        }
        initWolves(test);
    }

    private boolean empty(int row, int col) {
        return (grid[row][col] == 0);
    }

    private void initWolves(int test) {
        // You should put your own wolves in the array here!!
    	//"Super Smart Wolf", "Hungry Super Smart Wolf", "Random Wolf", "Sleeping Wolf", "Zoning Wolf"
    	if(test==0) {
	        Wolf[] wolvesPool = new Wolf[numWolves];
	        for(int i = 0 ; i < numWolves ; i++) {
	        	wolvesPool[i] = new SuperSmartWolf();
	        }
	        strategy = 1;
	        /*
	        wolvesPool[0] = new SuperSmartWolf();
	        wolvesPool[1] = new SuperSmartWolf();
	        wolvesPool[2] = new SuperSmartWolf();
	        wolvesPool[3] = new SuperSmartWolf();
	        wolvesPool[4] = new SuperSmartWolf();
	        */
	
	        // Below code will select three random wolves from the pool.
	        // Make the pool as large as you want, but not < numWolves
	        Set<Integer> generated = new LinkedHashSet<Integer>();
	        while (generated.size() < numWolves) {
	            Integer next = r.nextInt(wolvesPool.length);
	            generated.add(next);
	            //generated.add(0);
	            //generated.add(1);
	            //generated.add(2);
	        }
	
	        int i = 0;
	        for (Integer index : generated) {
	            wolves[i++] = wolvesPool[index];
	        }
        }
    	if(test==1) {
    		Wolf[] wolvesPool = new Wolf[numWolves];
	        for(int i = 0 ; i < numWolves ; i++) {
	        	wolvesPool[i] = new HungrySSWolf();
	        }
	        strategy = 2;
	        /*
	        Wolf[] wolvesPool = new Wolf[5]; 
	        wolvesPool[0] = new HungrySSWolf();
	        wolvesPool[1] = new HungrySSWolf();
	        wolvesPool[2] = new HungrySSWolf();
	        wolvesPool[3] = new HungrySSWolf();
	        wolvesPool[4] = new HungrySSWolf();
	        */
	        // Below code will select three random wolves from the pool.
	        // Make the pool as large as you want, but not < numWolves
	        Set<Integer> generated = new LinkedHashSet<Integer>();
	        while (generated.size() < numWolves) {
	            Integer next = r.nextInt(wolvesPool.length);
	            generated.add(next);
	            //generated.add(0);
	            //generated.add(1);
	            //generated.add(2);
	        }
	
	        int i = 0;
	        for (Integer index : generated) {
	            wolves[i++] = wolvesPool[index];
	        }
        }
    	if(test==2) {
    		Wolf[] wolvesPool = new Wolf[numWolves];
	        for(int i = 0 ; i < numWolves ; i++) {
	        	wolvesPool[i] = new RandomWolf();
	        }
	        strategy = 3;
    		/*
	        Wolf[] wolvesPool = new Wolf[5];
	        wolvesPool[0] = new RandomWolf();
	        wolvesPool[1] = new RandomWolf();
	        wolvesPool[2] = new RandomWolf();
	        wolvesPool[3] = new RandomWolf();
	        wolvesPool[4] = new RandomWolf();
    		*/
	        // Below code will select three random wolves from the pool.
	        // Make the pool as large as you want, but not < numWolves
	        Set<Integer> generated = new LinkedHashSet<Integer>();
	        while (generated.size() < numWolves) {
	            Integer next = r.nextInt(wolvesPool.length);
	            generated.add(next);
	            //generated.add(0);
	            //generated.add(1);
	            //generated.add(2);
	        }
	
	        int i = 0;
	        for (Integer index : generated) {
	            wolves[i++] = wolvesPool[index];
	        }
        }
    	if(test==3) {
    		Wolf[] wolvesPool = new Wolf[numWolves];
	        for(int i = 0 ; i < numWolves ; i++) {
	        	wolvesPool[i] = new SleepingWolf();
	        }
	        strategy = 4;
    		/*
	        Wolf[] wolvesPool = new Wolf[5];
	        wolvesPool[0] = new SleepingWolf();
	        wolvesPool[1] = new SleepingWolf();
	        wolvesPool[2] = new SleepingWolf();
	        wolvesPool[3] = new SleepingWolf();
	        wolvesPool[4] = new SleepingWolf();
    		*/
	        // Below code will select three random wolves from the pool.
	        // Make the pool as large as you want, but not < numWolves
	        Set<Integer> generated = new LinkedHashSet<Integer>();
	        while (generated.size() < numWolves) {
	            Integer next = r.nextInt(wolvesPool.length);
	            generated.add(next);
	            //generated.add(0);
	            //generated.add(1);
	            //generated.add(2);
	        }
	
	        int i = 0;
	        for (Integer index : generated) {
	            wolves[i++] = wolvesPool[index];
	        }
        }
    	if(test==4) {
    		Wolf[] wolvesPool = new Wolf[numWolves];
	        for(int i = 0 ; i < numWolves ; i++) {
	        	wolvesPool[i] = new ZoningWolf(0);
	        }
	        strategy = 5;
	        /*
	        Wolf[] wolvesPool = new Wolf[5];
	        wolvesPool[0] = new ZoningWolf(0);
	        wolvesPool[1] = new ZoningWolf(0);
	        wolvesPool[2] = new ZoningWolf(0);
	        wolvesPool[3] = new ZoningWolf(0);
	        wolvesPool[4] = new ZoningWolf(0);
	        */
	        // Below code will select three random wolves from the pool.
	        // Make the pool as large as you want, but not < numWolves
	        Set<Integer> generated = new LinkedHashSet<Integer>();
	        while (generated.size() < numWolves) {
	            Integer next = r.nextInt(wolvesPool.length);
	            generated.add(next);
	            //generated.add(0);
	            //generated.add(1);
	            //generated.add(2);
	        }
	
	        int i = 0;
	        for (Integer index : generated) {
	            wolves[i++] = wolvesPool[index];
	        }
        }
    }

    public void tick() {
        int[][] moves = new int[numWolves][2];

        int cntr = 0;
        for (int i = 0; i < numPreys; i++) {
            int rowMove, colMove;
            do {
                rowMove = r.nextInt(3) - 1;
                colMove = r.nextInt(3) - 1;
                cntr++;
            } while (!empty(rowWrap(preyRow[i], rowMove), colWrap(preyCol[i], colMove))
                    || (cntr < 100 && captured(rowWrap(preyRow[i], rowMove), colWrap(preyCol[i], colMove))));
            grid[preyRow[i]][preyCol[i]] = 0;
            preyRow[i] = rowWrap(preyRow[i], rowMove);
            preyCol[i] = colWrap(preyCol[i], colMove);
            grid[preyRow[i]][preyCol[i]] = i * 2 + 2;

        }

        // here we ask for the wolves moves; to change the movement style, change the limitMovement variable
        boolean limitMovement = false;

        int[][] safetyGrid;
        if (!limitMovement) {
            // Wolves can move diagonally
            for (int i = 0; i<numWolves; i++) {
                safetyGrid = new int[grid.length][grid[0].length];
                for (int r=0; r<grid.length; r++)
                    for (int s=0; s<grid[0].length; s++)
                        safetyGrid[r][s] = grid[r][s];
                moves[i] = wolves[i].moveAll(getWolfViewW(i), getWolfViewP(i));
            }
        } else {
            // Wolves can not move diagonally
            for (int i = 0; i < numWolves; i++) {
                safetyGrid = new int[grid.length][grid[0].length];
                for (int r = 0; r < grid.length; r++)
                    for (int s = 0; s < grid[0].length; s++)
                        safetyGrid[r][s] = grid[r][s];

                int dir = wolves[i].moveLim(getWolfViewW(i), getWolfViewP(i));

                switch (dir) {
                    case 0:
                        moves[i][0] = 0;
                        moves[i][1] = 0;
                        break;
                    case 1:
                        // left 
                        moves[i][0] = -1;
                        moves[i][1] = 0;
                        break;
                    case 2:
                        // down
                        moves[i][0] = 0;
                        moves[i][1] = 1;
                        break;
                    case 3:
                        // right
                        moves[i][0] = 1;
                        moves[i][1] = 0;
                        break;
                    case 4:
                        // up
                        moves[i][0] = 0;
                        moves[i][1] = -1;
                        break;
                }
            }
        }

        // and here we move everybody
        for (int i = 0; i < numWolves; i++) {
            if (empty(rowWrap(wolfRow[i], moves[i][0]), colWrap(wolfCol[i], moves[i][1]))) {
                grid[wolfRow[i]][wolfCol[i]] = 0;
                wolfRow[i] = rowWrap(wolfRow[i], moves[i][0]);
                wolfCol[i] = colWrap(wolfCol[i], moves[i][1]);
                grid[wolfRow[i]][wolfCol[i]] = i * 2 + 1;
            }
        }

        visuals.update();
        tickcounter++;

        for (int i = 0; i < numPreys; i++) { //add new captured to list
            if (capturedList.contains(i))
                continue;
            if (captured(preyRow[i], preyCol[i]))
                capturedList.add(i);
        }

        //check whether enough preys have been captured
        if (capturedList.size() >= minCaptured) {
            JOptionPane.showMessageDialog(null, "Les loups ont gagn?? en " + tickcounter + " ??tapes !");
            System.out.println("Winners");
            System.exit(0);
            //ESSAI.ESSAI(null);
        }
    }

    public boolean captured(int r, int c) {
        int count = 0;
        if (grid[rowWrap(r, -1)][colWrap(c, -1)] % 2 == 1) count++;
        if (grid[rowWrap(r, -1)][colWrap(c, 0)] % 2 == 1) count++;
        if (grid[rowWrap(r, -1)][colWrap(c, 1)] % 2 == 1) count++;
        if (grid[rowWrap(r, 0)][colWrap(c, -1)] % 2 == 1) count++;
        if (grid[rowWrap(r, 0)][colWrap(c, 1)] % 2 == 1) count++;
        if (grid[rowWrap(r, 1)][colWrap(c, -1)] % 2 == 1) count++;
        if (grid[rowWrap(r, 1)][colWrap(c, 0)] % 2 == 1) count++;
        if (grid[rowWrap(r, 1)][colWrap(c, 1)] % 2 == 1) count++;
        return (count >= min_surround);
    }

    public int rowWrap(int x, int inc) {
        int tmp = x + inc;
        if (tmp < 0) tmp += rows;
        if (tmp >= rows) tmp -= rows;
        return tmp;
    }

    public int colWrap(int x, int inc) {
        int tmp = x + inc;
        if (tmp < 0) tmp += cols;
        if (tmp >= cols) tmp -= cols;
        return tmp;
    }

    public int getNumbCols() {
        return cols;
    }

    public int getNumbRows() {
        return rows;
    }

    public boolean isWolf(int i, int j) { //Odd numbers are wolves
        return grid[i][j] % 2 == 1;
    }

    public boolean isPrey(int i, int j) { //Even numbers are sheeps
        return grid[i][j] != 0 & grid[i][j] % 2 == 0;
    }

    public void attach(WolvesUI wolvesUI) {
        visuals = wolvesUI;
    }

    public int manhattanDistance(int x0, int y0, int x1, int y1) {
        return Math.abs(x1 - x0) + Math.abs(y1 - y0);
    }

    public List<int[]> getWolfViewW(int wolf) {
        List<int[]> wolves = new ArrayList<>();
        for (int i = 0; i < numWolves; i++) {
            int relX = wolfRow[wolf] - wolfRow[i];
            int relY = wolfCol[wolf] - wolfCol[i];

            int[] agent = new int[]{relX, relY};
            wolves.add(agent);
        }

        return wolves;
    }

    public List<int[]> getWolfViewP(int wolf) {
        List<int[]> preys = new ArrayList<>();
        for (int i = 0; i < numPreys; i++) {
            if (manhattanDistance(wolfRow[wolf], wolfCol[wolf], preyRow[i], preyCol[i]) > visibility) {
                continue;
            }

            int relX = wolfRow[wolf] - preyRow[i];
            int relY = wolfCol[wolf] - preyCol[i];
            int[] agent = new int[]{relX, relY};
            preys.add(agent);
        }

        return preys;
    }
    
    public int getStrategy() {
    	return strategy;
    }

}
