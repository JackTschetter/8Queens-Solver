package logic;

import java.lang.Math;
import java.util.*;
import javafx.util.Pair;

public class Node {

    private int state_cost = 0;
    int [] state = {0, 1, 2, 3, 4, 5, 6, 7};
    int numMoves = 0;

    /** Constructor */
    public Node(int[] column_positions) {
        this.state = column_positions;
    }

    public int get_cost() {
        return this.state_cost(state);
    }

    /** Helper functions, not directly used in solving 8 queens problem */

    public void drawBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j=0; j < 8; j++) {
                if (state[j] == i) {
                    System.out.print("| ♛ ");
                }
                else {
                    System.out.print("|   ");
                }
            }
            System.out.println("|");
        }
    }

    public int count_nonzero(boolean[] thisArray) {
        int count = 0;
        for (int i=0; i<thisArray.length; i++) {
            if(thisArray[i] == true) {
                count ++;
            }
        }
        return count;
    }

    public boolean[] isQueen(int queen) {
        boolean[] queens = new boolean[8];
        for (int i=0; i<state.length; i++) {
            if(state[i]==queen) {
                queens[i] = true;
            }
            else {
                queens[i] = false;
            }
        }
        return queens;
    }

    public int[] absoluteValue(int[] arranged, int queen) {
        int[] temp = new int[8];
        for (int i=0; i<arranged.length; i++) {
            temp[i] = Math.abs(arranged[i]-queen);
        }
        return temp;
    }

    public boolean[] equality(int[] queen, int[] arranged) {
        boolean[] queens = new boolean[8];
        for (int i=0; i<queen.length; i++) {
            if(queen[i]==arranged[i]) {
                queens[i] = true;
            }
            else {
                queens[i] = false;
            }
        }
        return queens;
    }

    public int[][] product(int[] arr1, int[] arr2) {
        int[][] product = new int[64][2];
        int counter = 0;
        for (int i=0; i<arr1.length; i++) {
            int firstElement = arr1[i];
            for (int j=0; j<arr2.length; j++) {
                int secondElement = arr2[j];
                product [counter][0] = firstElement;
                product [counter][1] = secondElement;
                counter++;
            }
        }
        return product;
    }

    public int[] convert(List<Integer> a) {

        int[] arr = new int[a.size()];
        for (int i = 0; i < a.size(); i++) {
            arr[i] = a.get(i).intValue();
        }
        return arr;
    }

    /** Functions below this are directly used in solving the 8 queens problem */

    /** state_cost() */
    public int state_cost(int[] arr1) {

        /** Return the number of queens being attacked */
        float cost = 0;

        for (int index=0; index<arr1.length; index++) {
            int queen = arr1[index];
            cost += count_nonzero(this.isQueen(queen))-1;
            //System.out.println(count_nonzero(this.isQueen(queen))-1);
            cost += count_nonzero(equality(this.absoluteValue(arr1, queen), this.absoluteValue(new int[] {0, 1, 2, 3, 4, 5, 6, 7}, index)))-1;
        }
        return (int)(cost/2);
    } //Closes state_cost

    public List<List<Integer>> get_child_states() {
        //int[] children = {0, 1, 2, 3, 4, 5, 6, 7}; //Might need to change this later
        List<List<Integer>> children = new ArrayList<>();
        int[][] cartProduct = product(new int[] {0, 1, 2, 3, 4, 5, 6, 7}, new int[] {0, 1, 2, 3, 4, 5, 6, 7});
        for (int i = 0; i < cartProduct.length; i++) {
            int index = cartProduct[i][0];
            int queen = cartProduct[i][1];
            if (queen != this.state[index]) {
                ArrayList<Integer> new_state = new ArrayList<Integer>();
                for (int j : this.state)
                    new_state.add(j);
                new_state.set(index, queen);
                children.add(new_state);
            }
        } //Closes for loop
        return children;
    } //Closes get_child_states()

    public Node random_child() {

       int[] new_state = this.state.clone();
       int counter = 0;
       int index = (int) Math.floor(Math.random()*(7-0+1)+0);
       int[] choices = new int[7];
       for (int i = 0; i<8; i++) {
           if (i != this.state[index]) {
               choices[counter++] = i;
           }
       }
       int r = (int) Math.floor(Math.random()*7);
       new_state[index] = choices[r];
       return new Node(new_state);
    }

    public Node first_choice_child() {

        int cost = this.get_cost();
        List<List<Integer>> children = this.get_child_states();
        Collections.shuffle(children);
        Node side_state = null;
        int side_cost = 0;
        Node child = null;
        for (int i= 0; i<children.size(); i++) {
            child = new Node(convert(children.get(i)));
            int child_cost = child.get_cost();
            //int child_cost = state_cost(convert(children.get(i)));
            if (child_cost < cost) {
                //System.out.print("Line reached 0!");
                return child;
            } if (side_state == null && child_cost == cost) {
                side_state = child;
                side_cost = child_cost;
            }
        }

        if (side_state != null) {
            //System.out.print("Line reached 1!");
            return side_state;
        }
        //System.out.println("Line Reached 2!");
        return child;
    }

    public Node lowest_cost_child() {
        List<List<Integer>> children = this.get_child_states();
        int[] costs = new int[children.size()];
        int min = 0;
        int minIndex = 0;
        for (int i=0; i<children.size(); i++) {
            costs[i] = state_cost(convert(children.get(i)));
            if(i==0 || costs[i]<min) {
                min = costs[i];
                minIndex = i;
            }//Closes if
        }//Closes for loop
        //Compute the state cost inside the function that this is returned to
        return new Node(convert(children.get(minIndex)));
    }//Closes lowest_cost_child
}