/**
 * Created by GUNGUI on 10/1/2017.
 */
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;


import java.util.ArrayList;


public class Solver {
    private boolean solvable;
    private Stack<Board> solu;
    public Solver(Board initial){

        if(initial == null)
            throw new java.lang.IllegalArgumentException("Initial State must not be null");
        MinPQ<Step> pq  = new MinPQ<>();
        Step init = new Step(initial,null,false);
        Step twin = new Step(initial.twin(),null,true);
        pq.insert(init);
        pq.insert(twin);

        while(!pq.isEmpty()){

            Step current = pq.delMin();
            if(current.getBoard().isGoal()) {

                if(current.isTwin()){
                    solvable=false;
                    return;
                }
                solvable = true;
                solu = new Stack<>();
                solu.push(current.getBoard());
                while(current.getPrev()!=null){
                    current = current.getPrev();
                    solu.push(current.getBoard());
                }
                return;
            }

            Iterable<Board> neighboors = current.getBoard().neighbors();

            for(Board i: neighboors)
            {
                boolean b=check(current,i);
                if(b) {
                    Step neighboor = new Step(i, current,current.isTwin());
                    pq.insert(neighboor);
                }
            }
        }

        solvable =false;
    }           // find a solution to the initial board (using the A* algorithm)
    private boolean check(Step current,Board node){
        Step cursor = current;
        while(cursor.getPrev()!=null){
            if(cursor.getBoard().equals(node))
                return false;
            cursor = cursor.getPrev();
        }
        return true;
    }
    public boolean isSolvable(){
        return solvable;
    }// is the initial board solvable?
    public int moves(){
        if(solvable)
            return solu.size()-1;
        else
            return -1;
    }                     // min number of moves to solve initial board; -1 if unsolvable
    public Iterable<Board> solution(){
        return solu;
    }      // sequence of boards in a shortest solution; null if unsolvable
    private class Step implements Comparable<Step>{
        private boolean isTwin;
        private Board board;
        private int priority;
        private int move;
        Step prev;
        Step(Board board,Step prev,boolean isTwin){
            this.isTwin = isTwin;
            this.board = board;
            if(prev!=null)
            {
                move =prev.getMove()+1;
                priority = move+board.manhattan();
                this.prev =prev;
            }
            else{
                priority = board.manhattan();
                move = 0;
                this.prev = null;
            }
        }
        public Board getBoard(){
            return board;
        }
        public Step getPrev(){
            return prev;
        }

        public int getPriority() {
            return priority;
        }

        public void setPriority(int priority) {
            this.priority = priority;
        }

        public int getMove() {
            return move;
        }
        public boolean isTwin(){
            return this.isTwin;
        }
        public void setMove(int move) {
            this.move = move;
        }

        public void setPrev(Step prev) {
            this.prev = prev;
        }
        @Override
        public int compareTo(Step that){
            if(priority == that.getPriority())
                return 0;
            if(priority > that.getPriority())
                return 1;
            if(priority < that.getPriority())
                return -1;
            return 0;
        }
    }
    public static void main(String[] args){

    } // solve a slider puzzle (given below)
}