package logic;

public class hillclimb_sa {

    Node start_node;

    /** Constructor */
    public hillclimb_sa (Node node) {
        this.start_node = node;
    }

    public Node steepest_ascent(int max_sidesteps) {
        Node current_node = this.start_node;
        int current_cost = current_node.get_cost();
        int moves = 0;
        int side_steps = 0;
        while (true) {
            Node next_child = current_node.lowest_cost_child();
            int next_cost = next_child.get_cost();

            /** Checks for max */
            if (next_cost > current_cost) {
                //System.out.println(moves);
                current_node.numMoves = moves;
                return current_node;
            }

            /** Checks for plateaus */
            if (next_cost == current_cost) {
                side_steps += 1;
                if (side_steps > max_sidesteps) {
                    //System.out.println(moves);
                    current_node.numMoves = moves;
                    return current_node;
                }
            } else {
                side_steps = 0;
            }
            current_node = next_child;
            current_cost = next_cost;
            moves += 1;
        } //Closes while loop
    } //Closes steepest_ascent()
}