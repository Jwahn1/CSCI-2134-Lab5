/**
 *  File: MatrixTester.java
 *  Author: Alex Brodsky
 *  Date: October 1, 2019
 *  Purpose: CSCI 2134, Lab 4
 *
 *  Description: This class implements Matrix objects for manipulating matrices.
 */

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Scanner;

class MatrixTest {
    private final static String simpleMatrix = "2 2 1 2 3 4"; // [ 1 2 ]
    // [ 3 4 ]
    private final static String nonSqMatrix = "3 2 1 2 3 4 5 6"; // [ 1 2 ]
    // [ 3 4 ]

    @Test
    void getElemBasicTest() {
        Matrix m = new Matrix(new Scanner(simpleMatrix));
        assertEquals(2, m.getElem(1,2),
                "getElem() did not return correct value");
    }

    @Test
    void getElemIndexOutOfBounds() {
        /* Example test for throwing exceptions
         */
        Matrix m = new Matrix(new Scanner(simpleMatrix));
        try {
            m.getElem(0, 0);
            assertTrue(false, "getElem() should have thrown Exception");
        } catch (IndexOutOfBoundsException e) {
            assertEquals(e.getMessage(), "i = 0, j = 0");
        }
    }

    @Test
    void setElem() {
        Matrix m = new Matrix(new Scanner(simpleMatrix));
        m.setElem(2, 1, 5);
        assertEquals(5, m.getElem(2,1),
                "setElem() may not have set correct value");
    }

    @Test
    void negate() {
        Matrix m = new Matrix(new Scanner(simpleMatrix));
        m.negate();
        assertEquals(-1, m.getElem(1,1),
                "negate() did not negate element (1,1)");
        assertEquals(-2, m.getElem(1,2),
                "negate() did not negate element (1,2)");
        assertEquals(-3, m.getElem(2,1),
                "negate() did not negate element (2,1)");
        assertEquals(-4, m.getElem(2,2),
                "negate() did not negate element (2,2)");
    }

    @Test
    void add() {
        Matrix m = new Matrix(new Scanner(simpleMatrix));
        Matrix n = new Matrix(new Scanner(simpleMatrix));
        Matrix res = new Matrix(2, 2);
        n.negate();
        m.add(n, res);
        assertEquals(0, res.getElem(1,1),
                "add() did not add element (1,1))");
        assertEquals(0, res.getElem(1,2),
                "add() did not add element (1,2)");
        assertEquals(0, res.getElem(2,1),
                "add() did not add element (2,1)");
        assertEquals(0, res.getElem(2,2),
                "add() did not add element (2,2)");
    }

    @Test
    void equals() {
        Matrix m = new Matrix(new Scanner(simpleMatrix));
        Matrix n = new Matrix(m);
        assertTrue(m.equals(n), "equals() has a false negative");
    }

    @Test
    void equal2s() {
        Matrix m = new Matrix(new Scanner(simpleMatrix));
        Matrix n = new Matrix(m);
        n.negate();
        assertFalse(m.equals(n), "equals() has a false positive");
    }

    @Test
    void multiplyWithScalar() {
        Matrix m = new Matrix(new Scanner(simpleMatrix));
        Matrix n = new Matrix(m);
        Matrix res = new Matrix(m);
        n.negate();
        m.multiplyWithScalar(-1, res);
        assertTrue(res.equals(n), "matrix not scaled by -1");
    }

    @Test
    void multiplyWithMatrix() {
        Matrix m = new Matrix(new Scanner(simpleMatrix));
        Matrix n = new Matrix(m);
        Matrix res = new Matrix(m);
        m.multiplyWithMatrix(n, res);
        assertEquals(7, res.getElem(1,1),
                "matrix multiplication failed on element (1,1))");
        assertEquals(10, res.getElem(1,2),
                "matrix multiplication failed on element (1,2))");
        assertEquals(15, res.getElem(2,1),
                "matrix multiplication failed on element (2,1))");
        assertEquals(22, res.getElem(2,2),
                "matrix multiplication failed on element (2,2))");
    }

    @Test
    void getHeight() {
        Matrix m = new Matrix(new Scanner(nonSqMatrix));
        assertEquals(3, m.getHeight(),
                "getHeight() did not return correct height");
    }

    @Test
    void getWidth() {
        Matrix m = new Matrix(new Scanner(nonSqMatrix));
        assertEquals(2, m.getWidth(),
                "getWidth() did not return correct width");
    }
}