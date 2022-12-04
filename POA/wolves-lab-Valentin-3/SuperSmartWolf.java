import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class SuperSmartWolf implements Wolf{

    // This wolf chases as follow:
    // He moves randomly until he gets close to a prey
    // Once he is close to a prey, he 'calls' all the other wolves
    // Together around the prey they will be able to catch it quickly

    @Override
    public int[] moveAll(List<int[]> wolvesSight, List<int[]> preysSight) {
        
        int[] mymove = new int[2];

        if (wolvesSight.size()>0 && preysSight.size()>0){ // if wolf sees a wolf and a prey
            for (int i = 0; i < wolvesSight.size(); i++) {
                for(int j=0; j<preysSight.size(); j++){
                    // if wolf is next to one of the prey
                    if(Math.abs(wolvesSight.get(i)[0]-preysSight.get(j)[0]) <= 5 && Math.abs(wolvesSight.get(i)[1]-preysSight.get(j)[1]) <= 5){ 
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

        }else if(preysSight.size()>0){
            int[] preyPos = preysSight.get(0);

            if (preyPos[0]>0){
                mymove[0] = -1;
            }else if (preyPos[0] == 0){
                mymove[0] = 0;
            }else if(preyPos[0] < 0){
                mymove[0] = 1;
            }

            if(preyPos[1]>0){
                mymove[1] = -1;
            }else if(preyPos[1]==0){
                mymove[1] = 0;
            }else if(preyPos[1]<0){
                mymove[1] = 1;
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
