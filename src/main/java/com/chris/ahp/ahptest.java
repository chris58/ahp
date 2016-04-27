/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chris.ahp;

import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.EigenDecomposition;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

/**
 *
 * @author chris
 */
public class ahptest {

    public ahptest(double[][] bbt) {
        int dim = bbt.length;
        EigenDecomposition evd2;
        evd2 = new EigenDecomposition(
                new Array2DRowRealMatrix(bbt), 0);
        double[] eigenvalues = evd2.getRealEigenvalues();

        RealMatrix uHatrm = evd2.getV();
        double[][] uHat = new double[dim][];
        for (int i = 0; i < dim; i++) {
            uHat[i] = uHatrm.getRow(i);
        }
    }

    public static void main(String[] args) {
        int nrV = 4;
        double RI[] = {0.0, 0.0, 0.58, 0.9, 1.12, 1.24, 1.32, 1.41, 1.45, 1.49};
        
        double[][] matrix = new double[nrV][];
        for (int i = 0; i < nrV; i++) {
            matrix[i] = new double[nrV];
        }

        // diagonal
        for (int i = 0; i < nrV; i++) {
            matrix[i][i] = 1.0;
        }
        matrix[0][1] = 4;
        matrix[0][2] = 3;
        matrix[0][3] = 7;
        matrix[1][2] = 1.0 / 3.0;
        matrix[1][3] = 3.0;
        matrix[2][3] = 5.0;

        // (i,k) is 1/(k,i)
        for (int k = 0; k < nrV; k++) {
            for (int i = 0; i < nrV; i++) {
                matrix[i][k] = 1.0 / matrix[k][i];
            }
        }

        for (int k = 0; k < nrV; k++) {
            for (int i = 0; i < nrV; i++) {
                System.out.print(matrix[k][i] + "    ");
            }
            System.out.println("");

        }

        EigenDecomposition evd = new EigenDecomposition(new Array2DRowRealMatrix(matrix), 0);

        double sum = 0;
        for (int i = 0; i < 1; i++) {
            RealVector v = evd.getEigenvector(i);
            for (double d : v.toArray()) {
                sum += d;
            }
            //System.out.println(sum);
            for (double xx : v.toArray()) {
                System.out.println(xx / sum + "; ");
            }
            System.out.println();
            //System.out.println(v);
        };

        int evIdx = 0;
        System.out.println("\nEigenvalues");
        for (int i=0; i<evd.getRealEigenvalues().length; i++) {
            System.out.println(evd.getRealEigenvalues()[i]);
            evIdx = (evd.getRealEigenvalue(i) > evd.getRealEigenvalue(evIdx))? i : evIdx;
        }
        System.out.println("\n\nMax Eigenvalue = " + evd.getRealEigenvalue(evIdx));

        double ci = (evd.getRealEigenvalue(evIdx) - (double) nrV)/(double)(nrV-1);
        System.out.println("\nConsistency Index: " + ci);
        System.out.println(ci);
        
        System.out.println("\nConsistency Ratio: " + ci/RI[nrV] * 100 + "%");
    }
}
