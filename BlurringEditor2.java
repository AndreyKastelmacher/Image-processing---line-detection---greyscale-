
	/* Assignment number:  5.2
	 * File Name:          BasicEditor.java 
	 * Name (First Last):  Andrey Kastelmacher
	 * Student ID :        303258537 
	 * Email :             Andrey Kastelmacher@post.idc.ac.il
	 */  
import java.util.Arrays;
	
    public class BlurringEditor2 {
        
		
		
	    /**
	    * BLURRING EDITOR - main
	    * takes 2 cmd line args, filename and operation. reads a ppm file from the project directory and manipulates it.
	    * @ filename - the image or text file to be edited.
	    * @pic - the file to manipulate
	    * @operation - selects the type of operation to be performed,
	    * "gr" for grey Scaling, 
	    * "fv" for a vertical flip.
	    * "fh" for a horizontal flip.
	    */  
	    public static void main(String[] args) {
	          String filename = args[0];
	          int blurCycle = Integer.parseInt(args[1]);
	          int[][][] pic = read(filename);
	          for (int i = 0; i < blurCycle; i++){
	              pic=(blur(pic));	
	          }
	          show(pic);	    
	          }
	    public static int[][][] blur(int[][][] source ){
	        int[][][] destination = new int[source.length][source[0].length][3];
	        for (int i = 1; i < source.length-1 ; i++ ){
	            for (int j = 1; j < source[0].length-1; j++ ){
	                destination[i][j][0] = getInnerAverage(source, i, j, 0);
	                destination[i][j][1] = getInnerAverage(source, i, j, 1);
	                destination[i][j][2] = getInnerAverage(source, i, j, 2);
	            }
	        }
	        // EDGES AND CORNER CASES QUICK EVALUATIONS:
	        //TOP LEFT CORNER
	        destination[0][0][0] = getTopLeft(source, 0, 0, 0);
	        destination[0][0][1] = getTopLeft(source, 0, 0, 1);
	        destination[0][0][2] = getTopLeft(source, 0, 0, 2);
	        //TOP RIGHT CORNER 
	        destination[0][source[0].length-1][0] = getTopRight(source, 0, source[0].length-1, 0);
	        destination[0][source[0].length-1][1] = getTopRight(source, 0, source[0].length-1, 1);
	        destination[0][source[0].length-1][2] = getTopRight(source, 0, source[0].length-1, 2);
	        //BOTTOM LEFT CORNER:
	        destination[source.length-1][0][0] = getBottomLeft(source, source.length-1, 0, 0);
	        destination[source.length-1][0][1] = getBottomLeft(source, source.length-1, 0, 1);
	        destination[source.length-1][0][2] = getBottomLeft(source, source.length-1, 0, 2);
            // LEFT EDGE
	        for (int x = 1; x < source.length-2; x++){
    	        destination[x][0][0] = getLeftLine(source, x, 0, 0);
    	        destination[x][0][1] = getLeftLine(source, x, 0, 1);
    	        destination[x][0][2] = getLeftLine(source, x, 0, 2);
            }
	        // TOP EDGE
            for (int y = 1; y < source[0].length-2; y++){
    	        destination[0][y][0] = getTopLine(source, 0, y, 0);
    	        destination[0][y][1] = getTopLine(source, 0, y, 1);
    	        destination[0][y][2] = getTopLine(source, 0, y, 2);
            }// BOTTOM EDGE
            for (int y = 1; y < source[0].length-2; y++){
    	        destination[source.length-1][y][0] = getBottomLine(source, source.length-1, y, 0);
    	        destination[source.length-1][y][1] = getBottomLine(source, source.length-1, y, 1);
    	        destination[source.length-1][y][2] = getBottomLine(source, source.length-1, y, 2);
            }// RIGHT EDGE
	        for (int x = 1; x < source.length-2; x++){
    	        destination[x][source[0].length-1][0] = getRightLine(source, x, source[0].length-1, 0);
    	        destination[x][source[0].length-1][1] = getRightLine(source, x, source[0].length-1, 1);
    	        destination[x][source[0].length-1][2] = getRightLine(source, x, source[0].length-1, 2);
            }
	        return destination;
	    }  
	    public static int getTopLeft(int[][][] source, int i , int j, int pigment ){   
	    	int avg = 0;
	    	for(int a = i; a < 2; a++){
	    		for(int b = j; b < 2; b++){
	    			avg = avg + source[a][b][pigment];
	    		}
	    	}
	    avg = avg/4;
	    return avg;
	    }  
	    public static int getTopRight(int[][][] source, int i , int j, int pigment ){   
	    	int avg = 0;
	    	for(int a = i; a < 2; a++){
	    		for(int b = j-1; b < j+1 ; b++){
	    			avg = avg + source[a][b][pigment];
	    		}
	    	}
	    avg = avg/4;
	    return avg;
	    }  
	    public static int getBottomLeft(int[][][] source, int i , int j, int pigment ){   
	    	int avg = 0;
	    	for(int a = i-1; a < i+1; a++){
	    		for(int b = j; b < j+2 ; b++){
	    			avg = avg + source[a][b][pigment];
	    		}
	    	}
	    avg = avg/4;
	    return avg;
	    }  
	    public static int getLeftLine(int[][][] source, int i , int j, int pigment ){   
	    	int avg = 0;
	    	for(int a = i-1 ; a < i+2; a++){
	    		for(int b = 0; b < 2; b++){
	    			avg = avg + source[a][b][pigment];
	    		}
	    	}
	        avg = avg/6;
	        return avg;
	    } 
	    public static int getRightLine(int[][][] source, int i , int j, int pigment ){   
	    	int avg = 0;
	    	for(int a = i-1 ; a < i+2; a++){
	    		for(int b = source[0].length-2; b < source[0].length; b++){
	    			avg = avg + source[a][b][pigment];
	    		}
	    	}
	        avg = avg/6;
	        return avg;
	    } 
	    public static int getTopLine(int[][][] source, int i , int j, int pigment ){    
	    	int avg = 0;
	    	for(int a = 0 ; a < 2; a++){
	    		for(int b = j-1; b <= j+1; b++){
	    			avg = avg + source[a][b][pigment];
	    		}
	    	}
		avg = avg/6;
		return avg;
		} 
	    public static int getBottomLine(int[][][] source, int i , int j, int pigment ){    
	    	int avg = 0;
	    	for(int a = i -1  ; a < i +1; a++){
	    		for(int b = j-1; b <= j+1; b++){
	    			avg = avg + source[a][b][pigment];
	    		}
	    	}
		avg = avg/6;
		return avg;
		} 
	    public static int getInnerAverage(int[][][] source, int i , int j, int pigment ){       
	    	int avg = 0;
	    	for(int a = i-1 ; a < i+2; a++){
	    		for(int b = j-1; b <= j+1; b++){
	    			avg = avg + source[a][b][pigment];
	    		}
	    	} 
		    avg = avg/9;
		    return avg;
		}  	
	    /**
	    * GREY SCALE
	    * this function an image matrix and returns the grey scale per pixel, pixel located at source[][][here].
	    * this function uses luminance to calculate greyscale by formulae.
	    * @destination = grey scaled matrix.
	    */   
	    
	    public static int[][][] greyScale(int[][][] source){
	        int[][][] destination = new int[source.length][source[0].length][3];
	        for (int i = 0; i < source.length ; i++ ){
	            for (int j = 0; j < source[0].length; j++ ){
	                destination[i][j] = luminance(source[i][j]);
	            }
	        }    
	        return destination;
	    }    
	    /**
	    * lUMINANCE
	    * this function takes RGB code and calculates the luminance by formulae.
	    * @r = red
	    * @g = green
	    * @b = blue
	    */        
	    public static int[] luminance (int [] pixel){
	        int r = pixel[0];
	        int g = pixel[1];
	        int b = pixel[2];
	        int[] greyPix = new int[3];
	        for(int i = 0; i < 3; i++){
	            greyPix[i] = (int) (0.299 * r + 0.587 * g + 0.114 * b);
	        }    
	        return greyPix;  
	    }      
	     /**
	    * FLIP VERTICALLY
	    * this function uses reflect matrix, standalone to keep reflect matrix available for other functions.
	    * @ destination is the destination matrix
	    * this retains the values of the inner matrix ( keeps the RGB in tact).
	    * @return a flipped 3D matrix
	    */                   
	    public static int[][][] flipVertically(int[][][] source){ 
	        int[][][] destination = new int[source.length][source.length][3];
	        destination = reflectMatrix(source);
	        return destination;
	    }     
	     /**
	    * FLIP HORIZONTALLY
	    * --this method uses the reflectMatrix method, and flipMatrix method to manipulate the matrix.
	    * reads a 3D matrix, 
	    * reflects the matrix ,and flips it so that values in, (3,3) become (0,0), values in (0,1) become (0,2).
	    * column 0 remains column 0 etc.... .
	    * @ destination is the destination matrix
	    * this retains the values of the inner matrix ( keeps the RGB in tact).
	    * @return a flipped 3D matrix
	    */               
	    public static int[][][] flipHorizontally(int[][][] source){ 
	        int[][][] destination = new int[source.length][source.length][3];
	        destination = reflectMatrix(source);
	        destination = flipMatrix(destination);
	        return destination;
	    } 
	    /**
	    * REFLECT MATRIX
	    * reads a 3D matrix, is a tool.
	    * reflects the matrix , folding it so value (0,4) (given matrix length = 5), becomess (0,0), 
	    * row 3 becomes 1 etc....
	    * this retains the values of the inner matrix ( keeps the RGB in tact).
	    * @return a flipped 3D matrix
	    */           
	    public static int[][][] reflectMatrix(int[][][] matrix){
	        int[][][] newMatrix = new int[matrix.length][matrix.length][3];
	        for (int i = 0; i < matrix.length ; i++){
	            int G = matrix.length-1-i;
	            newMatrix[G] = matrix[i];
	        }
	        return newMatrix;
	    }    
	    /**
	     * FLIP MATRIX
	    * reads a 3D matrix, is a tool.
	    * Flips the matrix , folding it so value (4,4) (given matrix length = 5).
	    * this retains the values of the inner matrix ( keeps the RGB in tact).
	    * @param highest - sets the highest value and traverses over array. -1 is added otherwise it will be out of bounds.
	    * @return a flipped 3D matrix
	    */   
	    public static int[][][] flipMatrix(int[][][] matrix){
	        int[][][] newMatrix = new int[matrix.length][matrix[0].length][3];
	        int height = matrix.length - 1;
	        int width = matrix[0].length - 1;
	        for (int i = 0; i < matrix.length ; i++ ){
	            for (int j = 0; j < matrix[0].length; j++ ){
	                newMatrix[i][j] = matrix[height-i][width-j];
	            }    
	        }
	        return newMatrix;
	    }    
	    /**
	     * DISPLAY
	    * Displays a 3d array with brackets
	    *@ ncols - number of columns in matrix
	    *@ nrows - number of rows in matrix 
	    *@ no return - just a printing method.
	    */   
	    public static void display (int[][][] source){
	        int ncols = source.length;
	        for (int i = 0; i < ncols; i++){
	            System.out.println(Arrays.deepToString(source[i])+"\n");   
	        }
	    }  
	    /**
	     * READ
	    * Reads a file containing integers through the standard input,
	    * and stores it inside an array.
	    * 
	    * @param filename - the name of the file where the data is stored
	    * @g - the ppm file PE3 denominator
	    * @n - number of columns
	    * @m - number of rows
	    * @arrayLim - reads the 255 value limit of the ppm Array
	    * @return - returns the read 3D matrix
	    */ 
	    public static int[][][] read( String filename ){        
	        StdIn.setInput(filename);
	        String g = StdIn.readString(); // has to be used to bump StdIn fwd.
	        int width = StdIn.readInt();
	        //System.out.println(width);
	        int height = StdIn.readInt();
	        //System.out.println(height);
	        int max = StdIn.readInt(); // has to be used to bump StdIn fwd.
	        int[][][] matrix = new int[height][width][3];
	        for (int i = 0; i < height; i++){
	            for (int j = 0; j < width; j++){ 
	                    matrix[i][j][0] = StdIn.readInt();  
	                    matrix[i][j][1] = StdIn.readInt(); 
	                    matrix[i][j][2] = StdIn.readInt();  
	                   
	            }
	        }      
	        return matrix;
	    }
	    
	    /*Documentation is provided by the teachers*/
	    public static void show(int[][][] pic){
	        StdDraw.setCanvasSize(pic[0].length, pic.length);
	        int height = pic.length;
	        int width = pic[0].length;
	        StdDraw.setXscale(0, width);
	        StdDraw.setYscale(0, height);
	        StdDraw.show(30);
	        for (int i = 0; i < height; i++){
	            for (int j = 0; j < width; j++){
	                StdDraw.setPenColor(pic[i][j][0], pic[i][j][1], pic[i][j][2] );
	                StdDraw.filledRectangle(j + 0.5, height - i - 0.5, 0.5, 0.5);
	            }
	        }
	        StdDraw.show();
	    }
}    
	      
	    

