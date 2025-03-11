public class Grid {
    public static void main(String[] args) {
    
        // String[][] ans = randomGenerator(3);
        String[][] ans = randomGenerator(Integer.parseInt(args[0]));
        removeNumbers(ans);
        displayGrid(ans);
    }
    public static void displayGrid(String [][] layout) {
        int size=layout.length;
        int row = 0, col = 0;
        int digits = calculateDigits(layout);

        for(int i = 0; i < 2*size+1; i++) {
            if(i % 2 == 0) {
                for(int j = 0; j < 2*size+1; j++) {
                    if(j % 2 == 0) {
                        System.out.print(" ");
                    } else {
                        String s = "-";
                        s = s.repeat(digits);
                        System.out.print("-" + s +"-");
                    }
                }
            } else {
                for(int j = 0; j < 2*size+1; j++) {
                    if(j % 2 == 0) {
                        System.out.print("|");
                    } else {
                        String value = (layout[row][col++]);                        
                        String ans = String.format("%"+digits+"s",value);


                        System.out.print(" "+ans+" ");
                        if(col >= size) {
                            row++;
                            col = 0;
                        }
                    }
                }
            }
            System.out.println();
       }
    }

    public static String[][] randomGenerator(int size) {
        String [][] arr = new String[size][size];
        int [] checkRandom = new int[size];
        
        for(int i = 0;i < size; i++) {
            int randomStart = (int) (System.nanoTime() % size);
            while(checkRandom[randomStart] == 1) {
                randomStart = (randomStart+1)%size ;
            }
            checkRandom[randomStart++] = 1;
            for(int j = 0; j < size; j++) {
                
                arr[i][j] = randomStart++ +"";
                if(randomStart > size) {
                    randomStart = 1;
                }
            }
        }

        return arr;
        
    }

    public static void removeNumbers(String[][] layout) {
        int oneThird = (layout.length * layout.length)/3;
        int size = layout.length;
        
        for(int i = 0; i < oneThird; i++) {
            int randoRow = (int)(System.nanoTime() % size); 
            int randomColumn = (int)(System.nanoTime() % size);
            layout[randoRow][randomColumn] = " ";
        }

    }

    public static int calculateDigits(String[][] layout) {
        int size = layout.length;
        int count = 0;
        while(size>0) {
            count++;
            size/=10;
        }
        return count;
    }

    public static int calculateDigits(int size) {
        int count = 0;
        while(size > 0) {
            count++;
            size /= 10;
        }
        return count;
    }
}
