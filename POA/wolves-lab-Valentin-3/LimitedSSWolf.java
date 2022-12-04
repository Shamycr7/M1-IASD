import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class LimitedSSWolf implements Wolf{

    // This wolf chases as follow:
    // Same as the Super Smart Wolf except his moves are limited

    @Override
    public int[] moveAll(List<int[]> wolvesSight, List<int[]> preysSight) {
        // Implement either moveAll or moveLim
        return null;
    }

    @Override
    public int moveLim(List<int[]> wolvesSight, List<int[]> preysSight) {
        
        int mymove = -1;

        if (wolvesSight.size()>0 && preysSight.size()>0){ // if wolf sees a wolf and a prey
            for (int i = 0; i < wolvesSight.size(); i++) {
                for(int j=0; j<preysSight.size(); j++){
                    int[] preyPos = preysSight.get(j);
                    // if wolf is next to one of the prey
                    if(Math.abs(wolvesSight.get(i)[0]-preysSight.get(j)[0]) <= 5 && Math.abs(wolvesSight.get(i)[1]-preysSight.get(j)[1]) <= 5){ 
                        // follow that prey
                        if (preyPos[0]>0 && preyPos[1]>0){ // if prey is top-left
                            mymove = chooseRandom(1,4); // go up or left
                        }else if (preyPos[0]>0 && preyPos[1]<0){ // if prey is top-right
                            mymove = chooseRandom(1,2); // go up or right
                        }else if (preyPos[0]>0 && preyPos[1]==0){ // if prey is top
                            mymove = 1; // go up
                        }else if (preyPos[0]==0 && preyPos[1]>0){ // if prey is left
                            mymove = 4; // go left
                        }else if (preyPos[0]==0 && preyPos[1]<0){ // if prey is right
                            mymove = 2; // go right
                        }else if (preyPos[0]==0 && preyPos[1]==0){
                            mymove = 0;
                        }else if (preyPos[0]<0 && preyPos[1]>0){ // if prey is down-left
                            mymove = chooseRandom(3,4); // go down or left
                        }else if (preyPos[0]<0 && preyPos[1]<0){ // if prey is down-right
                            mymove = chooseRandom(3,2); // go down or right
                        }else if (preyPos[0]<0 && preyPos[1]==0){ // if prey is down
                            mymove = 3; // go down
                        }
                        break;
                    }
                }
            }

        }else if(preysSight.size()>0){
            int[] preyPos = preysSight.get(0);

            if (preyPos[0]>0 && preyPos[1]>0){ // if prey is top-left
                mymove = chooseRandom(1,4); // go up or left
            }else if (preyPos[0]>0 && preyPos[1]<0){ // if prey is top-right
                mymove = chooseRandom(1,2); // go up or right
            }else if (preyPos[0]>0 && preyPos[1]==0){ // if prey is top
                mymove = 1; // go up
            }else if (preyPos[0]==0 && preyPos[1]>0){ // if prey is left
                mymove = 4; // go left
            }else if (preyPos[0]==0 && preyPos[1]<0){ // if prey is right
                mymove = 2; // go right
            }else if (preyPos[0]==0 && preyPos[1]==0){
                mymove = 0;
            }else if (preyPos[0]<0 && preyPos[1]>0){ // if prey is down-left
                mymove = chooseRandom(3,4); // go down or left
            }else if (preyPos[0]<0 && preyPos[1]<0){ // if prey is down-right
                mymove = chooseRandom(3,2); // go down or right
            }else if (preyPos[0]<0 && preyPos[1]==0){ // if prey is down
                mymove = 3; // go down
            }
        }else{ // no prey around
            Random r = new Random();
            mymove = r.nextInt((4-0)+1) + 0;
        }

		return mymove;
    }  

    public int chooseRandom(int choice1, int choice2) {
        List<Integer> choices = new ArrayList<>();
        choices.add(choice1);
        choices.add(choice2);

        Random r = new Random();
        int index = r.nextInt((1-0)+1)+0;

        return choices.get(index);
    }
}