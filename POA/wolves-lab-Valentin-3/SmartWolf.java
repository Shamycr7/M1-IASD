import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

// This wolf chases as follow:
// When he is not close to a prey (preysSight list is empty),
// he moves randomly (just as the RandomWolf).
// If there is first prey (first in the list) close to him
// then he should get closer to it.
// Once he stands next to the prey, he continues to follow it 
// until another wolf comes and together they can catch the prey.

public class SmartWolf implements Wolf{

    @Override
    public int[] moveAll(List<int[]> wolvesSight, List<int[]> preysSight) {

        int[] mymove = new int[2];

        if (preysSight.size()>0){
            int[] preyPos = preysSight.get(0);
            //System.out.println(Arrays.toString(preyPos));

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
            Random r = new Random();
            mymove[0] = 0;
            mymove[1] = 0;

            while(mymove[0]==0 && mymove[1]==0){
                mymove[0] = r.nextInt(3)-1;
                mymove[1] = r.nextInt(3)-1;
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
