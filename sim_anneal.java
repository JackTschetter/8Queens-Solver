package logic;

/* sim_anneal class, performs simulated annealing to solve the 8 queens problem
 *
 *
 */
import java.lang.Math.*;

public class sim_anneal {

    Node start_node;
    int MAX_TIME = 1000;

    /** Constructor */
    public sim_anneal (Node node) {

        this.start_node = node;

    }

    public double schedule (double time) {
        if (time > this.MAX_TIME) {
            return 0;
        }

        if (time == 0) {
            time = 1e-10;
        }

        //return 1003 / (Math.pow(time, 3)); //Temperature
        return 1/time;
    }

    public Node solve() {
        Node current_node = this.start_node;
        int current_cost = current_node.get_cost();
        int next_cost = current_cost;
        int time = 0;
        int num1 = 0;
        while (true) {
            double T = schedule(time);
            if (T == 0) {
                break;
            }//Close if
            Node next_child = current_node.random_child();
            next_cost = next_child.get_cost();
            //next_child, next_cost = current_node.random_child();
            int dE = (current_cost - next_cost);
            if (dE > 0 || Math.random() < Math.exp(dE/T)) {
                num1 += 1;
                current_node = next_child;
                current_cost = next_cost;
                //current_cost = next_child, next_cost;
                if (next_cost == 0) {
                    break;
                }
            }
            time += 1;
        }//Closes while
        //System.out.println("Time : " + time);
        
        current_node.numMoves = time;

        return current_node;
    }//Closes solve()
}//Closes class