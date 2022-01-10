package edu.up.cs301.shapefitter;

import java.util.Arrays;

/**
 * Solver: finds fit for a shape; completed solution by Vegdahl.
 *
 * @author Brynn Harrington
 * @version September 8, 2021
 */
public class MyShapeSolver extends ShapeSolver {
    /**
     * Creates a solver for a particular problem.
     *
     * @param parmShape the shape to fit
     * @param parmWorld the world to fit it into
     * @param acc to send notification messages to
     */
    public MyShapeSolver(boolean[][] parmShape, boolean[][] parmWorld, ShapeSolutionAcceptor acc) {
        // invoke superclass constructor
        super(parmShape, parmWorld, acc);
    }

    /**
     * Solves the problem by finding a fit, if possible. The last call to display tells where
     * the fit is. If there is no fit, no call to display should be made--alternatively, a call to
     * undisplay can be made.
     */
    public void solve() {
        // verify the shape is not larger then the world
        if (this.shape.length > this.world.length ) undisplay();
        // loop through the orientations and find if the shape can be solved
        for (Orientation or : Orientation.values()) {
            // rotate the shape accordingly
            rotate(or);
            // attempt to solve in this orientation
            if (placeable(or)) break;
            else undisplay();
        }
    }

    /**
     * Checks if the the place on the world is already filled.
     *
     * @return whether the space is filled or not
     */
    public boolean placeable(Orientation or) {
        // initialize a variable to store the length of the world and shape
        // note: the world and shape arrays are both squares
        int wL = this.world.length;
        int sL = this.shape.length;
         // loop through the world array
         // note: the world and shape arrays are both squares
         // so able to use row length for both conditions
         for(int wR = 0; wR < wL - sL; wR++) {
             for (int wC = 0; wC < wL - sL; wC++) {
                 // initialize the place boolean as true (assuming a match)
                 boolean place = true;
                 // loop through the shape array to determine if able to place
                 for (int sR = 0; sR < this.shape.length; sR++){
                     for (int sC = 0; sC < this.shape.length; sC++) {
                         if (this.shape[sR][sC] && this.world[wR + sR][wC + sC]) {
                             place = false;
                             break;
                         }
                     }
                 }
                 // if able to place return true
                 if (place) {
                     // display the shape as it traverses
                     display(wR, wC, or);
                     return true;
                 }
             }
         }
        // if no match is found, return false
        return false;
    }
    /**
     * Utilizes the a given orientation to determine if/how the shape should
     * be rotated.
     *
     * @param or - the given orientation to check
     */
    public void rotate(Orientation or) {
        // switch between the orientations to determine if a match can be found
        switch (or) {
            // none - do not rotate the shape
            case ROTATE_NONE:
                break;
            // rotate in reverse
            case ROTATE_NONE_REV:
                reverse();
                break;
            // rotate clockwise
            case ROTATE_CLOCKWISE:
                clockwise();
                break;
            // rotate clockwise in reverse
            case ROTATE_CLOCKWISE_REV:
                clockwise();
                reverse();
                break;
            // rotate counterclockwise
            case ROTATE_COUNTERCLOCKWISE:
                counterclockwise();
                break;
            // rotate counterclockwise in reverse
            case ROTATE_COUNTERCLOCKWISE_REV:
                counterclockwise();
                reverse();
                break;
            // rotate 180 degrees
            case ROTATE_180:
                rotate180();
                break;
            // rotate 180 degrees in reverse
            case ROTATE_180_REV:
                rotate180();
                reverse();
                break;
            // default - throw an exception;
            default:
                throw new IllegalStateException("Unexpected value: " + or);
        }
    }
    /**
     * Utilizes the global variable shape and reverses its contents.
     */
    public void reverse() {
        // initialize the length of the shape
        int sL = this.shape.length;
        // initialize a new boolean array to store original shape
        boolean[][] s = this.shape;
        // reverse the array
        for (int sR = sL - 1; sR >= 0; sR--) {
                for (int sC = sL - 1; sC >= 0; sC--) {
                    // swap the sRs of the array
                    this.shape[sL - 1 - sR][sL - 1 - sC] = s[sR][sC];
                }
        }
    }

    /**
     * Utilizes the global variable shape and rotates it clockwise.
     */
    public void clockwise() {
        // initialize the length of the shape
        int sL = this.shape.length;
        // initialize a new boolean array to store original shape
        boolean[][] s = this.shape;
        // loop through the shape and rotate it clockwise
        for (int sR = 0; sR < this.shape.length; sR++) {
            for (int sC = 0; sC < this.shape.length; sC++) {
                // rotate the shape clockwise
                 this.shape[sR][sC] = s[sC][sL - 1 - sR];
            }
        }
    }

    /**
     * Utilizes the global variable shape and rotates it counterclockwise.
     */
    public void counterclockwise() {
        // initialize the length of the shape
        int sL = this.shape.length;

        // initialize a new boolean array to store original shape
        boolean[][] s = this.shape;

        // loop through the shape and rotate it clockwise
        for (int sR = 0; sR < this.shape.length; sR++) {
            for (int sC = 0; sC < this.shape.length; sC++) {
                // rotate the shape clockwise
                 this.shape[sR][sC] = s[sL - 1 - sC][sR];
            }
        }
    }

    /**
     * Utilizes the global variable shape and rotates it 180 degrees
     */
    public void rotate180() {
        // initialize the length of the shape
        int sL = this.shape.length;
        // rotate the shape 180 degrees with even shape length
        if (sL % 2 == 0) {
            // loop through the length of the shape divided by 2
            for (int sR = 0; sR < this.shape.length / 2; sR++) {
                for (int sC = 0; sC < this.shape.length; sC++) {
                    // update the values so flipped by 180 degrees
                    boolean current = this.shape[sR][sC];
                    this.shape[sR][sC] = this.shape[sL - sR - 1][sL - sC - 1];
                    this.shape[sL - sR - 1][sL - sC - 1] = current;
                }
            }
        }
        // rotate the shape 180 degrees with odd shape length
        else {
            for (int sR = 0; sR < this.shape.length / 2; sR++) {
                for (int sC = 0; sC < this.shape.length / 2; sC++) {
                    // update the values so flipped by 180 degrees
                    boolean current = this.shape[sL / 2][sC];
                    this.shape[sL / 2][sC] = this.shape[sL / 2][sL - sC - 1];
                    this.shape[sL / 2][sL - sC - 1] = current;
                }
            }
        }
    }

    /**
     * Checks if the shape is well-formed: has at least one square, and has all squares connected.
     *
     * @return whether the shape is well-formed
     */
    public boolean check() {
        // store the length of the shape in a variable
        int sL = this.shape.length;
        // initialize a new boolean array to track possible paths
        boolean[][] connected = new boolean[sL][sL];
        // determine the first true instance of the shape
        connected = firstTrueShape(connected);
        // traverse through the shape to find if there is a valid/connected path
        for (int sR = 0; sR < sL; sR++) {
            for (int sC = 0; sC < sL; sC++) {
                // if this index is true, verify there is a surrounding index also true
                if (connected[sR][sC]) {
                    connected[sR][sC] = true;
                    // if there is a true index above, make the index of connected true
                    if (sR >= 1) connected[sR - 1][sC] = this.shape[sR - 1][sC];
                    // if there is a true index below, make the index of connected true
                    if (sR + 1 < sL) connected[sR + 1][sC] = this.shape[sR + 1][sC];
                    // if there is a true index to the left, make the index of connected true
                    if (sC >= 1) connected[sR][sC - 1] = this.shape[sR][sC - 1];
                    // if there is a true index to the right, make the index of connected true
                    if (sC + 1 < sL) connected[sR][sC + 1] = this.shape[sR][sC + 1];
                }
            }
        }
        // return whether there is a valid/connected path or not
        return Arrays.deepEquals(this.shape, connected);
    }

    /**
     * Checks if the shape has at least one square.
     *
     * @param connected - the initial connected array
     * @return connected - the first true index of the shape
     */
    public boolean[][] firstTrueShape(boolean[][] connected) {
        // initialize a new variable for this instance of the shape's length
        int sL = this.shape.length;
        // traverse through the shape to find the first true index if any
        for (int sR = 0; sR < sL; sR++) {
            for (int sC = 0; sC < sL; sC++) {
                if (this.shape[sR][sC]) {
                    // set the connected path to true at this index
                    connected[sR][sC] = true;
                    return connected;
                }
            }
        }
        // return the initial path of the array
        return connected;
    }
  }