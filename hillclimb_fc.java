package logic;

public class hillclimb_fc {

    Node start_node;

    /** Constructor */
    public hillclimb_fc(Node node) {

        this.start_node = node;

    }

    /** Method to perform first_choice hill climbing */
    public Node first_choice(int max_sidesteps) {
        Node current_node = this.start_node;
        int current_cost = current_node.get_cost();
        int moves = 0;
        int side_steps = 0;
        while (true) {
            Node next_child = current_node.first_choice_child();
            int next_cost = next_child.get_cost();

            /** Checks for max */
            if (next_cost > current_cost) {
                //System.out.println("Local maximum reached");
                //System.out.println("Moves : " + moves);
                current_node.numMoves = moves;
                return current_node;
            }

            /** Checks for plateaus */
            if (next_cost == current_cost) {
                side_steps += 1;
                if (side_steps > max_sidesteps) {
                    //System.out.println("Plateau reached");
                    //System.out.println("Moves " + moves);
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
}//Closes Class