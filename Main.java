package logic;

import java.lang.Math.*;

public class Main {

    /** Driver method*/
    public static void main(String[] args) {

        int first_choice_success = 0;
        int steepest_success = 0;
        int simulated_success = 0;
        int first_choice_moves = 0;
        int steepest_moves = 0;
        int simulated_moves = 0;

        for (int i = 0; i<1000; i++) {

            int[] randomState = new int[8];

            for (int k=0; k<8; k++) {
                randomState[k] = (int)Math.floor(Math.random() * 8);
            }

            Node node = new Node(randomState);

            hillclimb_fc fc = new hillclimb_fc(node);
            hillclimb_sa sa = new hillclimb_sa(node);
            sim_anneal simAnneal = new sim_anneal(node);

            Node result0 = simAnneal.solve();
            Node result1 = fc.first_choice(100);
            Node result2 = sa.steepest_ascent(100);

            if (result0.get_cost()==0) {
                simulated_success++;
                simulated_moves += result0.numMoves;
            }
            if (result1.get_cost()==0) {
                first_choice_success++;
                first_choice_moves += result1.numMoves;
            }
            if (result2.get_cost()==0) {
                steepest_success++;
                steepest_moves += result2.numMoves;
            }//Closes if
        }//Closes for loop
        int first_choice_average = first_choice_moves/first_choice_success;
        System.out.println("First Choice Average : " + first_choice_average);
        int steepest_average = steepest_moves/steepest_success;
        System.out.println("Steepest Ascent Average : " + steepest_average);
        int simulated_average = simulated_moves/simulated_success;
        System.out.println("Simulated Annealing Average : " + simulated_average);

        /*
        Node node = new Node(new int[] {0, 1, 2, 3, 4, 5, 6, 7});
        hillclimb_fc hillclimb = new hillclimb_fc(node);
        Node result = hillclimb.first_choice(100);
        System.out.println("Cost : " + result.get_cost());
        result.drawBoard();
        System.out.println();
        for (int i=0; i<result.state.length; i++) {
            System.out.println(result.state[i]);
        }
         */
	    //Node best_child = node.lowest_cost_child();
        //Node node = new Node(new int[] {0, 1, 2, 3, 4, 5, 6, 7});
	    //System.out.println(best_child.get_cost());
	    //drawBoard();

        //System.out.println("Cost : " + result.get_cost());
       //result.drawBoard();

    }
}