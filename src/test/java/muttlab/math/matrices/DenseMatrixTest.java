package muttlab.math.matrices;

import org.junit.Assert;
import org.junit.Test;

public class DenseMatrixTest {

    @Test
    public void getTest() {
        // toString(0, 0) should return 1 when the matrix == "[ 1 2 3 ]"
        try {
            Matrix m = new DenseMatrix().from("[ 1 2 3 ]");
            Assert.assertTrue(m.get(0, 0) == 1);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Assert.fail();
        }
        // toString(1, 0) should throws an exception when the matrix == "[ 1 2 3 ]"
        try {
            Matrix m = new DenseMatrix().from("[ 1 2 3 ]");
            m.get(1, 0);
            Assert.fail();
        } catch (Exception e) {}
        // toString(0, 1) should return 2 when the matrix == "[ 1 2 3 ]"
        try {
            Matrix m = new DenseMatrix().from("[ 1 2 3 ]");
            Assert.assertTrue(m.get(0, 1) == 2);
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void setTest() {
        // set(0, 0, 11F) should change transform the matrix as follow: "[ 11 2 3 ]"
        try {
            Matrix m = new DenseMatrix().from("[ 1 2 3 ]");
            m.set(0, 0, 11F);
            Assert.assertTrue(m.get(0, 0) == 11);
        } catch (Exception e) {
            Assert.fail();
        }
        // set(1, 0) should throws an exception when the matrix == "[ 1 2 3 ]"
        try {
            Matrix m = new DenseMatrix().from("[ 1 2 3 ]");
            m.set(1, 0, 11F);
            Assert.fail();
        } catch (Exception e) {}
        // set(0, 1, 11F) should change transform the matrix as follow: "[ 1 11 3 ]"
        try {
            Matrix m = new DenseMatrix().from("[ 1 2 3 ]");
            m.set(0, 1, 11F);
            Assert.assertTrue(m.get(0, 1) == 11);
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void newEmptyMatrixTest() {
        // newEmptyMatrix(3, 1) should return the following matrix: "[ 0; 0; 0 ]"
        try {
            Matrix m1 = new DenseMatrix();
            Matrix m2 = m1.newEmptyMatrix(3, 1);
            Assert.assertTrue(m2.get(0, 0) == 0);
            Assert.assertTrue(m2.get(1, 0) == 0);
            Assert.assertTrue(m2.get(2, 0) == 0);
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void getNumberOfRowsTest() {
        // getHeight should return 3 when the matrix is : "[ 1; 2; 3 ]"
        try {
            Matrix m = new DenseMatrix().from("[ 1; 2; 3 ]");
            Assert.assertTrue(m.getHeight() == 3);
        } catch (Exception e) {
            Assert.fail();
        }
        // getHeight should return 1 when the matrix is : "[ 1 ]"
        try {
            Matrix m = new DenseMatrix().from("[ 1 ]");
            Assert.assertTrue(m.getHeight() == 1);
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void getNumberOfColumnsTest() {
        // getWidth should return 1 when the matrix is : "[ 1; 2; 3 ]"
        try {
            Matrix m = new DenseMatrix().from("[ 1; 2; 3 ]");
            Assert.assertTrue(m.getWidth() == 1);
        } catch (Exception e) {
            Assert.fail();
        }
        // getWidth should return 1 when the matrix is : "[ 1; 2 ]"
        try {
            Matrix m = new DenseMatrix().from("[ 1 2 ]");
            Assert.assertTrue(m.getWidth() == 2);
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void fromTest() {
        // from should not throws an exception when the input string is : "[ 1; 2; 3 ]"
        try {
            Matrix m = new DenseMatrix().from("[ 1; 2; 3 ]");
            Assert.assertTrue(m.get(0, 0) == 1);
            Assert.assertTrue(m.get(1, 0) == 2);
            Assert.assertTrue(m.get(2, 0) == 3);
        } catch (Exception e) {
            Assert.fail();
        }
        // from should throws an exception when the input string is : "[ 1; 2 4; 3 ]"
        try {
            Matrix m = new DenseMatrix().from("[ 1; 2 4; 3 ]");
            Assert.fail();
        } catch (Exception e) {}
        // from should throws an exception when the input string is : "[ 1 2; 2; 3 ]"
        try {
            Matrix m = new DenseMatrix().from("[ 1 2; 2; 3 ]");
            Assert.fail();
        } catch (Exception e) {}
        // from should throws an exception when the input string is : "[ ]"
        try {
            Matrix m = new DenseMatrix().from("[ ]");
            Assert.fail();
        } catch (Exception e) {}
        // from should throws an exception when the input string is : "[ a ]"
        try {
            Matrix m = new DenseMatrix().from("[ a ]");
            Assert.fail();
        } catch (Exception e) {}
    }

    @Test
    public void asStringTest() {
        // asString should return [ 1; 2; 3 ] when the matrix is : "[ 1; 2; 3"
        try {
            Assert.assertEquals(new DenseMatrix().from("[ 1; 2; 3").asString(), "[ 1.0; 2.0; 3.0 ]");
        } catch (Exception e) {
            Assert.fail();
        }
        // asString should return [ 1; 2 ] when the matrix is : "[    1  ;    2      "
        try {
            Assert.assertEquals(new DenseMatrix().from("[    1  ;    2      ").asString(), "[ 1.0; 2.0 ]");
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void pointWiseMulTest() {
        // pointWiseMul should return [ 2; 4; 6 ] when the matrices are : "[ 1; 2; 3" and  "[ 2; 2; 2"
        try {
            Matrix m1 = new DenseMatrix().from("[ 1; 2; 3");
            Matrix m2 = new DenseMatrix().from("[ 2; 2; 2");
            m1.pointWiseMul(m2);
            Assert.assertTrue(m1.get(0, 0) == 2);
            Assert.assertTrue(m1.get(1, 0) == 4);
            Assert.assertTrue(m1.get(2, 0) == 6);
        } catch (Exception e) {
            Assert.fail();
        }
        // pointWiseMul should throws an exception when the matrices are : "[ 1; 2" and "[ 1; 2; 3"
        try {
            Matrix m1 = new DenseMatrix().from("[ 1; 2");
            Matrix m2 = new DenseMatrix().from("[ 1; 2; 3");
            m1.pointWiseMul(m2);
            Assert.fail();
        } catch (Exception e) {}
    }

    @Test
    public void addTest() {
        // add should return [ 3; 4; 5 ] when the matrices are : "[ 1; 2; 3" and  "[ 2; 2; 2"
        try {
            Matrix m1 = new DenseMatrix().from("[ 1; 2; 3");
            Matrix m2 = new DenseMatrix().from("[ 2; 2; 2");
            m1.add(m2);
            Assert.assertTrue(m1.get(0, 0) == 3);
            Assert.assertTrue(m1.get(1, 0) == 4);
            Assert.assertTrue(m1.get(2, 0) == 5);
        } catch (Exception e) {
            Assert.fail();
        }
        // add should throws an exception when the matrices are : "[ 1; 2" and "[ 1; 2; 3"
        try {
            Matrix m1 = new DenseMatrix().from("[ 1; 2");
            Matrix m2 = new DenseMatrix().from("[ 1; 2; 3");
            m1.add(m2);
            Assert.fail();
        } catch (Exception e) {}
    }

    @Test
    public void subTest() {
        // sub should return [ -1; 0; 1 ] when the matrices are : "[ 1; 2; 3" and  "[ 2; 2; 2"
        try {
            Matrix m1 = new DenseMatrix().from("[ 1; 2; 3");
            Matrix m2 = new DenseMatrix().from("[ 2; 2; 2");
            m1.sub(m2);
            Assert.assertTrue(m1.get(0, 0) == -1);
            Assert.assertTrue(m1.get(1, 0) == 0);
            Assert.assertTrue(m1.get(2, 0) == 1);
        } catch (Exception e) {
            Assert.fail();
        }
        // sub should throws an exception when the matrices are : "[ 1; 2" and "[ 1; 2; 3"
        try {
            Matrix m1 = new DenseMatrix().from("[ 1; 2");
            Matrix m2 = new DenseMatrix().from("[ 1; 2; 3");
            m1.sub(m2);
            Assert.fail();
        } catch (Exception e) {}
    }

    @Test
    public void mulTest() {
        // mul should return [ 12 ] when the matrices are : "[ 1; 2; 3" and  "[ 2 2 2"
        try {
            Matrix m1 = new DenseMatrix().from("[ 2 2 2");
            Matrix m2 = new DenseMatrix().from("[ 1; 2; 3");
            m1.mul(m2);
            Assert.assertTrue(m1.get(0, 0) == 12);
        } catch (Exception e) {
            Assert.fail();
        }
        // mul should throws an exception when the matrices are : "[ 1; 2" and "[ 1; 2"
        try {
            Matrix m1 = new DenseMatrix().from("[ 1; 2");
            Matrix m2 = new DenseMatrix().from("[ 1; 2");
            m1.mul(m2);
            Assert.fail();
        } catch (Exception e) {}
    }

    @Test
    public void mulScalarTest() {
        // mul should return [ 2; 4; 6 ] when the matrix is "[ 1; 2; 3" and the scalar is "2"
        try {
            Matrix m1 = new DenseMatrix().from("[ 1; 2; 3");
            m1.mul(2F);
            Assert.assertTrue(m1.get(0, 0) == 2);
            Assert.assertTrue(m1.get(1, 0) == 4);
            Assert.assertTrue(m1.get(2, 0) == 6);
        } catch (Exception e) {
            Assert.fail();
        }
    }
}