
	/* Assignment number:  5.4
	 * File Name:          BlurToNoEdges.java 
	 * Name (First Last):  Andrey Kastelmacher
	 * Student ID :        303258537 
	 * Email :             Andrey Kastelmacher@post.idc.ac.il
	 */  
import java.util.Arrays;
	
    public class BlurToNoEdgesFIN {
	    /**
	    * BLUR TO NO EDGES 
	    * works with .ppm files
	    * shows how the images' edges get blurred in an animated view. 
	    * takes 2 cmd line args, filename, and cycle,  shows the edges of the picture.
	    * @ filename - the image or text file to be edited.
	    * @pic - the file to manipulate
	    * @cycle - the amount of thime to show animated blurring of the edges, 
	    * to begin to see the effect nicely (set at: cycle > 20), 50 recommended. 
	    */  
	    public static void main(String[] args) {
	          String filename = args[ 0 ];
	          int cycle = Integer.parseInt( args[ 1 ]);
	          int[][][] pic = read( filename );
	          for ( int i = 0; i < cycle; i++ ){
		          show( edges(( pic )));
	              pic = ( blur( pic ));	
	          }
	    }
	    /**
	     * EDGES
	     * takes a 3D array and returns its edges,
	     * finds the lines in the pic, line detector
	     * uses greyscale, calculate gradient, and matrix compiler
	     * @param source the target image array
	     * @return target: the modified array 
	     */
	    public static int[][][] edges(int[][][] source ){
	    	int[][] gradient = new int [ source.length ][ source[ 0 ].length ];
	    	int[][][] target = new int [ source.length ][ source[ 0 ].length ][ 3 ];
	    	gradient = calculateGradient( greyScale( source ));
	    	target = matrixCompiler( gradient, target );
	    	return target;	
	    }
	    /**
	     * CALCULATE GRADIENT
	     * calculates the horizontal gradient of a given array.
	     * @param source is the input array
	     * @return - destination - a 2D array of a normalized gradient at every index 
	     */
	    public static int[][]calculateGradient(int[][][] source ){
	    	//display(source);
	        int[][] destination = new int[ source.length ][ source[ 0 ].length ];
	        for (int i = 0; i < source.length ; i++ ){
	            for (int j = 1; j < source[ 0 ].length-1 ; j++ ){
	                destination[ i ][ j ] = (source[ i ][ j+1 ][ 0 ]) - (source[ i ][ j-1 ][ 0 ]);

	            }
	        } 
            normalize( destination );
            return destination;
	    }      
	    /**
	     * NORMALIZE
	     * takes a 2D array and returns the normalized value per array index (per pixel)
	     * uses getnorm for calculation
	     * findMax and findMin t find the array bounds.
	     * @param source - the input array
	     */
	    public static void normalize( int[][] source ){
	    	double max = findMax( source );
	    	double min = findMin( source );
	        for (int i = 0; i < source.length; i++ ){
	        	for (int j = 1; j <= source[0].length-2; j++ ){
	        		int pixel = source[ i ][ j ];
	        		source[ i ][ j ] = getNorm( pixel, min , max );
	        	}
	        }
	    }        
	    /**
	     * GET NORM 
	     * calculates the normalized value of an array cell, between a max and a min value.
	     * 
	     * @param pixel the pixel to normalize
	     * @param min the minimum value of the containing array
	     * @param max the maximum value of the pixel containing array.
	     * @return returns the normalized value
	     */
	    public static int getNorm( int pixel, double min , double max ){
	        int newMin = 0;
	        int newMax = 255;
	        double a = ( pixel - min ) * ( newMax - newMin );
	        double b = ( max - min );
	        double result = ( a/b );
	    	int out1 = Math.abs((int)Math.round( result ));
	    	return out1;		
	    }
	    /**
	     * FIND MAX
	     * finds the maximum value of a 2D array
	     * @param source the array to parse
	     * @return max  - the maximal value
	     */
	    public static int findMax( int[][]source ){
    	int max = source[ 0 ][ 0 ];
    	for (int i = 0; i < source.length; i++ ){
        	for (int j = 0; j < source[ 0 ].length; j++ ){
        	    if( max < source[ i ][ j ] ){
        	    	max = source[ i ][ j ]; 
        	    }
        	}    
    	}
    	return max;
	    }
	    /**
	     * FIND MIN
	     * finds the minimal value of a 2D array
	     * @param source - the array to parse
	     * @return min - the minimal value
	     */

	    public static int findMin(int[][]source){
	    	int min = source[ 0 ][ 0 ];
	    	for (int i1 = 0; i1 < source.length; i1++ ){
	        	for (int j1 =0; j1 < source[0].length; j1++ ){
	        	    if( min > source[ i1 ][ j1 ] ){
	        	    	min = source[ i1 ][ j1 ]; 
	        	    }
	        	}    
	    	}
	    return min;
		}
	    /**
	     * MATRIX COMPILER, 
	     * takes a double array of the gradient and compiles it into a 3D array.
	     * @param gradient - the gradient array
	     * @param target - the output array
	     * @return the target 3D array
	     */

	    public static int[][][] matrixCompiler(int[][] gradient , int[][][] target){
	        for (int i = target.length-1; i > -1 ; i-- ){
	            for (int j = target[0].length-1; j > -1 ; j-- ){
	                target[ i ][ j ][ 0 ] = gradient[ i ][ j ];
	                target[ i ][ j ][ 1 ] = gradient[ i ][ j ];
	                target[ i ][ j ][ 2 ] = gradient[ i ][ j ];
	            }
	        }
	        return target;
	    }    
	      /**
	       * BLUR
	       * blurs the image, 
	       * averages all values of surrounding pixels, the function is a routing tree
	       * and sorting stage, uses the functions : getTopLeft, getTopRight, getBottomLeft, 
	       * getLeftLine, getRightLine, getTopLine, getBottomLine & getInnerAverage.
	       * All these functions contain averaging facilities for the correct position
	       * in the matrix.
	       * @param source, is the input matrix.
	       * @return destination - the blur averaged matrix.
	       * for information regarding the math, see inside the get functions doc.
	       */
	    public static int[][][] blur( int[][][] source ){
	        int[][][] destination = new int[source.length][source[0].length][3];
	        for (int i = 1; i < source.length-1 ; i++ ){
	            for (int j = 1; j < source[0].length-1; j++ ){
	                destination[ i ][ j ][ 0 ] = getInnerAverage( source, i, j, 0 );
	                destination[ i ][ j ][ 1 ] = getInnerAverage( source, i, j, 1 );
	                destination[ i ][ j ][ 2 ] = getInnerAverage( source, i, j, 2 );
	            }
	        }
	        // EDGES AND CORNER CASES QUICK EVALUATIONS:
	        //TOP LEFT CORNER
	        destination[ 0][ 0 ][ 0 ] = getTopLeft( source, 0, 0, 0 );
	        destination[ 0][ 0 ][ 1 ] = getTopLeft( source, 0, 0, 1 );
	        destination[ 0][ 0 ][ 2 ] = getTopLeft( source, 0, 0, 2 );
	        //TOP RIGHT CORNER 
	        destination[ 0 ][ source[ 0 ].length-1 ][ 0 ] = getTopRight( source, 0, source[ 0 ].length-1, 0 );
	        destination[ 0 ][ source[ 0 ].length-1 ][ 1 ] = getTopRight( source, 0, source[ 0 ].length-1, 1 );
	        destination[ 0 ][ source[ 0 ].length-1 ][ 2 ] = getTopRight( source, 0, source[ 0 ].length-1, 2 );
	        //BOTTOM LEFT CORNER:
	        destination[ source.length-1 ][ 0 ][ 0 ] = getBottomLeft( source, source.length-1, 0, 0 );
	        destination[ source.length-1 ][ 0 ][ 1 ] = getBottomLeft( source, source.length-1, 0, 1 );
	        destination[ source.length-1 ][ 0 ][ 2 ] = getBottomLeft( source, source.length-1, 0, 2 );
            // LEFT LINE
	        for (int x = 1; x < source.length-1; x++){
    	        destination[ x ][ 0 ][ 0 ] = getLeftLine( source, x, 0, 0 );
    	        destination[ x ][ 0 ][ 1 ] = getLeftLine( source, x, 0, 1 );
    	        destination[ x ][ 0 ][ 2 ] = getLeftLine( source, x, 0, 2 );
            }
	        // TOP LINE
            for (int y = 1; y < source[0].length-1; y++){
    	        destination[ 0 ][ y ][ 0 ] = getTopLine( source, 0, y, 0 );
    	        destination[ 0 ][ y ][ 1 ] = getTopLine( source, 0, y, 1 );
    	        destination[ 0 ][ y ][ 2 ] = getTopLine( source, 0, y, 2 );
            }// BOTTOM LINE
            for (int y = 1; y < source[0].length-1; y++){
    	        destination[ source.length-1 ][ y ][ 0 ] = getBottomLine( source, source.length-1, y, 0 );
    	        destination[ source.length-1 ][ y ][ 1 ] = getBottomLine( source, source.length-1, y, 1 );
    	        destination[ source.length-1 ][ y ][ 2 ] = getBottomLine( source, source.length-1, y, 2 );
            }// RIGHT LINE
	        for (int x = 1; x < source.length-1; x++){
    	        destination[ x ][ source[0].length-1 ][ 0 ] = getRightLine( source, x, source[0].length-1, 0 );
    	        destination[ x ][ source[0].length-1 ][ 1 ] = getRightLine( source, x, source[0].length-1, 1 );
    	        destination[ x ][ source[0].length-1 ][ 2 ] = getRightLine( source, x, source[0].length-1, 2 );
            }
	        return destination;
	    }  
	    /**
	     * getTopLeft
	     * @param source , input matrix .
	     * @param i, is the input position for matrix in pos[i].
	     * @param j, is the input position for matrix in pos[i][j]
	     * @param pigment is the color pigment (RGB) to be averaged.
	     * @return the averaged value at the corner of the image (0,0)
	     */
	    public static int getTopLeft( int[][][] source, int i , int j, int pigment ){   
	    	int avg = 0;
	    	for(int a = i; a < 2; a++){
	    		for(int b = j; b < 2; b++){
	    			avg = avg + source[ a ][ b ][ pigment ];
	    		}
	    	}
	    avg = avg/4;
	    return avg;
	    }  
	    /**
	     * getTopRight
	     * @param source , input matrix .
	     * @param i , input matrix index at pos[i].
	     * @param j , input matrix index at pos[i][j]
	     * @param pigment pigment is the color pigment (RGB) to be averaged.
	     * @return the averaged value at the corner of the image (0,max)
	     */
	    public static int getTopRight( int[][][] source, int i , int j, int pigment ){   
	    	int avg = 0;
	    	for(int a = i; a < 2; a++){
	    		for(int b = j-1; b < j+1 ; b++){
	    			avg = avg + source[ a ][ b ][ pigment ];
	    		}
	    	}
	    avg = avg/4;
	    return avg;
	    }  
	    /**
	     * getBottomLeft
	     * @param source , input matrix .
	     * @param i , input matrix index at pos[i].
	     * @param j , input matrix index at pos[i][j]
	     * @param pigment pigment is the color pigment (RGB) to be averaged.
	     * @return the averaged value at the corner of the image (max, 0)
	     */
	    public static int getBottomLeft( int[][][] source, int i , int j, int pigment ){   
	    	int avg = 0;
	    	for(int a = i-1; a < i+1; a++){
	    		for(int b = j; b < j+2 ; b++){
	    			avg = avg + source[ a ][ b ][ pigment ];
	    		}
	    	}
	    avg = avg/4;
	    return avg;
	    }  
	    /**
	     * getLeftLine
	     * @param source , input matrix .
	     * @param i , input matrix index at pos[i].
	     * @param j , input matrix index at pos[i][j]
	     * @param pigment pigment is the color pigment (RGB) to be averaged.
	     * @return the averaged value at the line of the image at index [i][0]
	     */
	    public static int getLeftLine(int[][][] source, int i , int j, int pigment ){   
	    	int avg = 0;
	    	for(int a = i-1 ; a < i+2; a++){
	    		for(int b = 0; b < 2; b++){
	    			avg = avg + source[ a ][ b ][ pigment ];
	    		}
	    	}
	        avg = avg/6;
	        return avg;
	    } 
	    /**
	     * getRightLine
	     * @param source , input matrix .
	     * @param i , input matrix index at pos[i].
	     * @param j , input matrix index at pos[i][j]
	     * @param pigment pigment is the color pigment (RGB) to be averaged.
	     * @return the averaged value at the line of the image at index [max]
	     */
	    public static int getRightLine(int[][][] source, int i , int j, int pigment ){   
	    	int avg = 0;
	    	for( int a = i-1 ; a < i+2; a++ ){
	    		for( int b = source[ 0 ].length-2; b < source[ 0 ].length; b++ ){
	    			avg = avg + source[ a ][ b ][ pigment ];
	    		}
	    	}
	        avg = avg/6;
	        return avg;
	    } 
	    /**
	     * getTopLine
	     * @param source , input matrix .
	     * @param i , input matrix index at pos[i].
	     * @param j , input matrix index at pos[i][j]
	     * @param pigment pigment is the color pigment (RGB) to be averaged.
	     * @return the averaged value at the line of the image at index [0]
	     */
	    public static int getTopLine( int[][][] source, int i , int j, int pigment ){    
	    	int avg = 0;
	    	for( int a = 0 ; a < 2; a++ ){
	    		for(int b = j-1; b <= j+1; b++ ){
	    			avg = avg + source[ a ][ b ][ pigment ];
	    		}
	    	}
		avg = avg/6;
		return avg;
		} 
	    /**
	     * getBottomLine
	     * @param source , input matrix .
	     * @param i , input matrix index at pos[i].
	     * @param j , input matrix index at pos[i][j]
	     * @param pigment pigment is the color pigment (RGB) to be averaged.
	     * @return the averaged value at the line of the image at index [max]
	     */
	    public static int getBottomLine( int[][][] source, int i , int j, int pigment ){    
	    	int avg = 0;
	    	for( int a = i -1  ; a < i +1; a++ ){
	    		for( int b = j-1; b <= j+1; b++ ){
	    			avg = avg + source[ a ][ b ][ pigment ];
	    		}
	    	}
		avg = avg/6;
		return avg;
		} 
	    /**
	     * getInnerAverage
	     * @param source , input matrix .
	     * @param i , input matrix index at pos[i].
	     * @param j , input matrix index at pos[i][j]
	     * @param pigment pigment is the color pigment (RGB) to be averaged.
	     * @return the averaged value at inner "square" of the matrix (main work load)
	     */
	    public static int getInnerAverage( int[][][] source, int i , int j, int pigment ){       
	    	int avg = 0;
	    	for( int a = i-1 ; a < i+2; a++ ){
	    		for( int b = j-1; b <= j+1; b++ ){
	    			avg = avg + source[ a ][ b ][ pigment ];
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
	    
	    public static int[][][] greyScale( int[][][] source ){
	        int[][][] destination = new int[ source.length ][ source[ 0 ].length][ 3 ];
	        for ( int i = 0; i < source.length ; i++ ){
	            for ( int j = 0; j < source[0].length; j++ ){
	                destination[ i ][ j ] = luminance( source[i][j] );
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
	    public static int[] luminance ( int [] pixel ){
	        double r = pixel[ 0 ]; 
	        double g = pixel[ 1 ];
	        double b = pixel[ 2 ];
	        int[] greyPix = new int[ 3 ];
	        for( int i = 0; i < 3; i++ ){
	            greyPix[i] = ((int)( 0.299 * r + 0.587 * g + 0.114 * b ));
	            
	        }    
	        return greyPix;  
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
	            System.out.println( Arrays.deepToString( source[i] )+ "\n" );   
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
	        StdIn.setInput( filename );
	        String g = StdIn.readString(); // has to be used to bump StdIn fwd.
	        int width = StdIn.readInt();
	        int height = StdIn.readInt();
	        int max = StdIn.readInt(); // has to be used to bump StdIn fwd.
	        int[][][] matrix = new int[ height ][ width ][ 3 ];
	        for (int i = 0; i < height; i++){
	            for (int j = 0; j < width; j++){ 
	                    matrix[ i ][ j ][ 0 ] = StdIn.readInt();  
	                    matrix[ i ][ j ][ 1 ] = StdIn.readInt(); 
	                    matrix[ i ][ j ][ 2 ] = StdIn.readInt();  
	                   
	            }
	        }    
	        return matrix;
	    }
	    
	    /*Documentation is provided by the teachers*/
	    public static void show(int[][][] pic){
	        StdDraw.setCanvasSize(pic[0].length, pic.length);
	        int height = pic.length;
	        int width = pic[0].length;
	        StdDraw.setXscale( 0, width );
	        StdDraw.setYscale( 0, height );
	        StdDraw.show( 30 );
	        for ( int i = 0; i < height; i++ ){
	            for ( int j = 0; j < width; j++ ){
	                StdDraw.setPenColor(pic[ i ][ j ][ 0 ], pic[ i ][ j ][ 1 ], pic[ i ][ j ][ 2 ] );
	                StdDraw.filledRectangle( j + 0.5, height - i - 0.5, 0.5, 0.5 );
	            }
	        }
	        StdDraw.show();
	    }
}     

