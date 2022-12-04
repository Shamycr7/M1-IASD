import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class CaptureWolf implements Wolf{

    @Override
    public int[] moveAll(List<int[]> wolvesSight, List<int[]> preysSight) {

        // This wolf's job is to follow a prey
        // whenever a wolf is already next to it
        // so that together they catch the prey

        int[] mymove = new int[2];

        if (wolvesSight.size()>0 && preysSight.size()>0){ // if wolf sees a wolf and a prey
            for (int i = 0; i < wolvesSight.size(); i++) {
                for(int j=0; j<preysSight.size(); j++){
                    // if wolf is next to one of the prey
                    if(Math.abs(wolvesSight.get(i)[0]-preysSight.get(j)[0]) <= 1 && Math.abs(wolvesSight.get(i)[1]-preysSight.get(j)[1]) <= 1){ 
                        // follow that prey
                        if (preysSight.get(j)[0]>0){
                            mymove[0] = -1;
                        }else if (preysSight.get(j)[0] == 0){
                            mymove[0] = 0;
                        }else if(preysSight.get(j)[0] < 0){
                            mymove[0] = 1;
                        }
            
                        if(preysSight.get(j)[1]>0){
                            mymove[1] = -1;
                        }else if(preysSight.get(j)[1]==0){
                            mymove[1] = 0;
                        }else if(preysSight.get(j)[1]<0){
                            mymove[1] = 1;
                        }
                        break;
                    }
                }
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
        // TODO Auto-generated method stub
        Random r = new Random();
		return r.nextInt(4) + 1;
    }
    
}
