import java.util.List;
import java.util.Random;

// does not move (sleep) until he sees a prey
// when he sees a prey, he starts chasing it

public class SleepingWolf implements Wolf {
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
            mymove[0] = 0;
            mymove[1] = 0;
        }
		return mymove;
	}
	
	public int moveLim(List<int[]> wolvesSight, List<int[]> preysSight) {
		return 0;
	}
}
