import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

// when using this wolf
// set limitMovement = true;
// in the class Wolves

// This wolf moves as follow:
// It moves randomly when there is no prey around it
// When it sees a prey, it follows the prey
// This wolf's moves are limited (it cannot move diagonally)

public class LimitedWolf implements Wolf{

    @Override
    public int[] moveAll(List<int[]> wolvesSight, List<int[]> preysSight) {
        // Implementer either moveAll or moveLim
        return null;
    }

    @Override
    public int moveLim(List<int[]> wolvesSight, List<int[]> preysSight) {
        // The method ”moveLim” should return an integer in 0,1,2,3,4, 
        // where 0 means no movement, 1 = up, 2 = right, 3 = down and 4 = left. 
        // Movement that makes the wolf move into something on the grid 
        // (prey or other wolf) is not executed.
        // Hence, no diagonal movement is possible (compared to moveAll)

        int mymove = -1;
        Random r = new Random();

        if (preysSight.size()>0){
            int[] preyPos = preysSight.get(0);
            //System.out.println(Arrays.toString(preyPos));

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
