import java.util.List;
import java.util.Random;

// moves around a perimeter
// until he sees a prey
// when he sees it, starts chasing it

public class ZoningWolf implements Wolf {
	int perimeter;
	int[] positionInZone = {0,0};
	
	ZoningWolf(int perimeter) {
		this.perimeter = perimeter;
	}
	
	@Override
    public int[] moveAll(List<int[]> wolvesSight, List<int[]> preysSight) {

        int[] mymove = new int[2];

        if (preysSight.size()>0){
            int[] preyPos = preysSight.get(0);

            if (preyPos[0]>0){
                mymove[0] = -1; // go up
            }else if (preyPos[0] == 0){
                mymove[0] = 0;
            }else if(preyPos[0] < 0){
                mymove[0] = 1; // go down
            }

            if(preyPos[1]>0){
                mymove[1] = -1; // go left
            }else if(preyPos[1]==0){
                mymove[1] = 0;
            }else if(preyPos[1]<0){
                mymove[1] = 1; // go right
            }
        }else{ // no prey around
        	if(positionInZone[1] == 0 && positionInZone[0] < perimeter) {
        		positionInZone[0] = positionInZone[0]+1;
        		mymove[0] = 1;
        		mymove[1] = 0;
        	}
        	else {
        		if(positionInZone[0] == perimeter && positionInZone[1] < perimeter) {
	        		positionInZone[1] = positionInZone[1]+1;
	        		mymove[0] = 0;
	        		mymove[1] = 1;
        		}
	        	else {
	        		if(positionInZone[1] == perimeter && positionInZone[0] > 0) {
		        		positionInZone[0] = positionInZone[0]-1;
		        		mymove[0] = -1;
		        		mymove[1] = 0;
	        		}
		        	else {
		        		if(positionInZone[0] == 0 && positionInZone[1] > 0) {
			        		positionInZone[1] = positionInZone[1]-1;
			        		mymove[0] = 0;
			        		mymove[1] = -1;
		        		}
		        	}
	        	}
        	}
        }

		return mymove;
    }

    @Override
    public int moveLim(List<int[]> wolvesSight, List<int[]> preysSight) {
        // Implement either moveAll or moveLim
        return 0;

    }
}
