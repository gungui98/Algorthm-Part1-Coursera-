import java.util.ArrayList;
import java.util.List;

/**
 * Created by GUNGUI on 10/1/2017.
 */
    public class  Board {
        private final int[][] data;
        private int mahattan;
        private int hamming;
        private StringBuilder s;
        private int N;
        public Board(int[][] blocks)
        {
            mahattan = -1;
            hamming = -1;
            data = blocks.clone();
            N= data.length;
        }// construct a board from an n-by-n array of blocks
        // (where blocks[i][j] = block in row i, column j)
        public int dimension(){
            return N;
        }                 // board dimension n
        public int hamming(){
            if(hamming==-1){
            hamming =0;
            for(int i=0;i<N;i++)
                for (int j=0;j<N;j++){
                if(data[i][j] != 0 && data[i][j] != (i*N +j+1) )
                    hamming++;
                }
            }
            return hamming;
        }                   // number of blocks out of place
        public int manhattan(){
            if(mahattan==-1){
            mahattan = 0;
            for(int i=0;i<N;i++)
                for (int j=0;j<N;j++)
                if(data[i][j]!=0)
                {
                    int value = data[i][j];
                    int jloc = (value-1)%N;
                    int iloc = (value - jloc)/N;
                    mahattan+=Math.abs(iloc-i)+Math.abs(jloc-j);
                }
            }
                return mahattan;
        }                 // sum of Manhattan distances between blocks and goal
        public boolean isGoal(){
            
            return manhattan() == 0;
        }                // is this board the goal board?
        public Board twin(){
            int[][] twin = deepCopy(data);
            int count=0;
            int[] x = new int[2];
            int[] y = new int[2];
            for(int i=0;i<N;i++)
                for(int j=0;j<N;j++) {
                    if (data[i][j] != 0) {
                        x[count]=i;
                        y[count++]=j;
                    }
                    if(count==2){
                        swap(twin,x[0],y[0],x[1],y[1]);
                        return new Board(twin);
                    }
                }
            return new Board(twin);
        }// a board that is obtained by exchanging any pair of blocks
        public boolean equals(Object y){
            if (this == y) return true;
            if (y == null || getClass() != y.getClass()) return false;

            Board that = (Board) y;
            if (this.data.length != that.dimension()) return false;
            for (int i = 0; i < N; i++) {
                if (this.data[i].length != that.data[i].length) return false;
                for (int j = 0; j < data[i].length; j++) {
                    if (this.data[i][j] != that.data[i][j]) return false;
                }
            }

            return true;
        }// does this board equal y?
        public Iterable<Board> neighbors(){
            List<Board> neighboor= new ArrayList<>();
            for(int i =0;i< N;i++)
                for (int j=0;j<N;j++)
                    if(data[i][j] == 0){
                            if(i== 0){
                            addNewBoard(neighboor,i,j,i+1,j);
                        }
                        else if(i==N-1){
                            addNewBoard(neighboor,i,j,i-1,j);
                        }
                        else{
                            addNewBoard(neighboor,i,j,i+1,j);
                            addNewBoard(neighboor,i,j,i-1,j);
                        }
                        if(j== 0){
                            addNewBoard(neighboor,i,j,i,j+1);
                        }
                        else if(j==N-1){
                            addNewBoard(neighboor,i,j,i,j-1);
                        }
                        else{
                            addNewBoard(neighboor,i,j,i,j+1);
                            addNewBoard(neighboor,i,j,i,j-1);
                        }
                    }
            return neighboor;

        }     // all neighboring boards
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", data[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }               // string representation of this board (in the output format specified below)
        private void swap(int[][] newdata,int x0,int y0,int x1,int y1){
            int temp = newdata[x0][y0];
            newdata[x0][y0] = newdata[x1][y1];
            newdata[x1][y1] =temp;
        }
        private void addNewBoard(List<Board> neighboor,int x0,int y0,int x1,int y1){
            int[][] newdata = deepCopy(data);
            swap(newdata,x0,y0,x1,y1);
            Board newBoard = new Board(newdata);
            neighboor.add(newBoard);
        }
        private int[][] deepCopy(int[][] data){
            int[][] newdata = new int[N][N];
            for(int i =0 ;i<N;i++)
                for(int j = 0;j<N;j++)
                    newdata[i][j] = data[i][j];
            return newdata;
        }
        public static void main(String[] args){
        } // unit tests (not graded)
}

