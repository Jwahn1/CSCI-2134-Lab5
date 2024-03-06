/**
 *  File: Matrix.java
 *  Author: Alex Brodsky
 *  Date: October 1, 2019
 *  Purpose: CSCI 2134, Lab 4
 *
 *  Description: This class implements Matrix objects for manipulating matrices.
 */

import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.PrintStream;

/**
 * This is the Matrix class for loading and manipulatinx matricexs.
 * It stores the matrix in a 2D array for a dense representation that
 * is easy to manipulate.
 */
public class Matrix {
    private double [][] matrix;  // 2D array stores matrix of size height x width
    private int height;          // number of rows in the matrix
    private int width;           // number of columns in the matrix

    /**
     * Constructor creates a zero matrix of size m x n.
     * Parameters:  m: height of the matrix
     *              n: width of the matrix
     * Exceptions: if m < 0 or n < 0 thrown an IndexOutOfBoundsException
     */
    public Matrix(int m, int n) throws IndexOutOfBoundsException {
        // Instantiate 2D array and initialize height and width fields.
        if ((m < 0) || (n < 0)) {
            throw new IndexOutOfBoundsException("m = " + m + ", n = " + n);
        }
        matrix = new double[m][n];
        height = m;
        width = n;
    }

    /**
     * Constructor duplicates the passed matrix
     * Parameters:  mtx: matrix to be cloned
     * Exceptions: if mtx == null thrown an IndexOutOfBoundsException
     */
    public Matrix(Matrix mtx) throws NullPointerException {
        if (mtx == null) {
            throw new NullPointerException("mtx == null");
        }

        // Get dimensions of matrix to be cloned and instantiate array.
        height = mtx.getHeight();
        width = mtx.getWidth();
        matrix = new double[height][width];

        // Loop over all array elements and and copy each one.
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                matrix[i][j] = mtx.matrix[i][j];
            }
        }
    }

    /**
     * Constructor reads in a matrix from the Scanner object
     * Parameters:  s: Scanner object used to read in the matrix
     */
    public Matrix(Scanner s) {
        // Read in dimensions of the matrix and instantiate 2D array
        height = s.nextInt();
        width = s.nextInt();
        matrix = new double[height][width];

        // Loop over array elements in row major order to read in the entries.
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                matrix[i][j] = s.nextDouble();
            }
        }
    }

    /**
     * Return true if this matrix equals the one passed to this method.
     * Parameters:  a:   matrix to be compared against the current matrix
     * Return: returns true if and only if the matrices are element-wise the same
     */
    public boolean equals(Matrix a) {
        // if a is not allocated or not the same size as this matrix, they cannot be equal
        if ((a == null) || (a.getHeight() != height) || (a.getWidth() != width) ) {
            return false;
        }

        // Compare each element in both matrices
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (matrix[i][j] != a.matrix[i][j]) {
                    // at least one element is not the same in these matrices
                    return false;
                };
            }
        }

        // Matrices are equal
        return true;
    }

    /**
     * Negate all entries in this matrix
     * Parameters:  none
     * Return: none
     */
    public void negate() {
        // Negate each element in the matrix
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                matrix[i][j] = -matrix[i][j];
            }
        }
    }

    /**
     * Add Matrix b with the current matrix and store the results in res.
     * Parameters:  b:   matrix to be added with the current matrix
     *              res: destination matrix where the result is placed.
     * Return: returns res matrix for programming convenience.
     * Exceptions: if b == null or res == null thrown an NullPointerException
     * Exceptions: if dimensions don't match up thrown an InputMismatchException
     */
    public Matrix add(Matrix b, Matrix res) throws NullPointerException, InputMismatchException {

        //if b or res are not allocated. throw InputMismatchException
        if((res == null) || (b == null)){
            throw new NullPointerException("res or b are null");
        }

        // if b or res are not the same size, throw nullPointerException
        if (    (b.getHeight() != height) || (b.getWidth() != width) ||
                (res.getHeight() != height) || (res.getWidth() != width)) {
           throw new InputMismatchException("b's Height = " + b.getHeight() + "b's Width = " + b.getWidth() +
           "res's Height = " + res.getHeight() + "res's Width = " + res.getWidth());
        }

        // Add each entry of this matrix to the corresponding entry in b and
        // store it in res.
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                res.matrix[i][j] = matrix[i][j] + b.matrix[i][j];
            }
        }

        // On success returns res.
        return res;
    }

    /**
     * Multiply scalar s with the current matrix and store the results in res.
     * Parameters:  b:   matrix to be added with the current matrix
     *              res: destination matrix where the result is placed.
     * Return: returns res matrix for programming convenience.
     * Exceptions: if res == null thrown an NullPointerException
     */
    public Matrix multiplyWithScalar(double s, Matrix res) throws NullPointerException  {

       //if res == null
        if(res == null){
            throw new NullPointerException("res is null");
        }


        // if res is not allocated return null
        if (res != null) {
            // if matrices are not the same size, return null
            if ((res.getHeight() != height) || (res.getWidth() != width)) {
                return null;
            }

            // Multiply each entry of this matrix by s and store it in res.
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    res.matrix[i][j] = s * matrix[i][j];
                }
            }
        }

        // On success res != null.
        return res;
    }

    /**
     * Multiply the current matrix with matrix b and store the results in res.
     * Parameters:  b:   matrix to be multiplied with the current matrix
     *              res: destination matrix where the result is placed.
     * Return: returns res matrix for programming convenience.
     * Exceptions: if b == null or res == null thrown an NullPointerException
     * Exceptions: if dimensions don't match up thrown an InputMismatchException
     */
    public Matrix multiplyWithMatrix(Matrix b, Matrix res) throws NullPointerException,InputMismatchException{
        // Ensure all matrices are allocated
        if ((res == null) || (b == null)) {
           throw new NullPointerException("res or b is null");
        }

        // Ensure all matrices are properly sized.
        int n = b.getWidth();
        if ((b.getHeight() != width) || (res.getHeight() != height) ||
                (res.getWidth() != n)) {
           throw new InputMismatchException("b's height = " +
           b.getHeight() + "res's height  = " + res.getHeight()+ "res's witdh = " + res.getWidth());
        }

        // Perform matrix multiplication using the formula
        // E[i,j] = Sum_k A[i,k] * B[k,j]
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < n; j++) {
                res.matrix[i][j] = 0;
                for (int k = 0; k < width; k++) {
                    res.matrix[i][j] += matrix[i][k] * b.matrix[k][j];
                }
            }
        }
        // On success return res.
        return res;
    }

    /**
     * Write the matrix out to the PrintStream, (as if using System.out)
     * The format is:
     *   First line has 2 integers: height and width, separated by a space
     *   Next 'height' lines contain `width` dobules, separated by spaces
     * Parameters:  out: The PrintStream where the output should go.
     */
    public void write(PrintStream out) {
        assert height >= 0 : "Height is negative";
        assert width >= 0 : "Width is negative";

        // Output matrix dimensions: height and width
        out.println(height + " " + width);

        // Output 'height' rows
        for (int i = 0; i < height; i++) {
            // Add first entry to the string representing the row
            String row = "";
            if (width > 0) {
                row += matrix[i][0];
            }

            // Add remaining entries to the string, separated by a space
            for (int j = 1; j < width; j++) {
                row += " " + matrix[i][j];
            }

            // Output the row.
            out.println(row);
        }
    }

    /**
     * Return element E{i,j] of the matrix.
     * Parameters:  i: row of entry
     *              j: column of entry
     * Exceptions: if i < 1, j < 1 i > height, j > height,  thrown an
     *             IndexOutOfBoundsException
     */
    public double getElem(int i, int j) throws IndexOutOfBoundsException {
        if ((i < 1) || (j < 1) || (i > height) || (j > width)) {
            throw new IndexOutOfBoundsException("i = " + i + ", j = " + j);
        }
        // return corresponding entry in matrix.
        return matrix[i-1][j-1];
    }

    /**
     * Set element E{i,j] of the matrix to value v.
     * Parameters:  i: row of entry
     *              j: column of entry
     *              v: new value of the entry
     * Exceptions: if i < 1, j < 1 i > height, j > height,  thrown an
     *             IndexOutOfBoundsException
     */
    public void setElem(int i, int j, double v) throws IndexOutOfBoundsException {

        if( (i < 1) || (j < 1) ||  (i > height) || (j > height)){
            throw new IndexOutOfBoundsException("i = " + i + ", j = " + j);
        }

        // Set the corresponding entry in matrix to v.
        matrix[i-1][j-1] = v;
    }

    /**
     * Return the number of rows in the matrix.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Return the number of columns in the matrix.
     */
    public int getWidth() {
        return width;
    }
}
