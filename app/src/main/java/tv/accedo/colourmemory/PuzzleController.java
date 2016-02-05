package tv.accedo.colourmemory;

import android.os.Handler;

import java.util.Random;

public class PuzzleController {

    private static final int NUM_PIECES = 16;
    private static final int NO_SELECTION = -1;

    private int selection = NO_SELECTION;
    private int secondselection = NO_SELECTION;
    private int score = 0;
    private Puzzle[] puzzleItems;
    private PuzzleListener listener;
    private Handler showHandler = new Handler();
    private Runnable showItems = new Runnable() {
        @Override
        public void run() {
            compareSelection();
        }
    };

    public PuzzleController() {
        resetPuzzle();
    }

    private void resetPuzzle() {
        puzzleItems = new Puzzle[NUM_PIECES];
        // Create unshuffled puzzle
        for (int p = 0; p < puzzleItems.length; p++) {
            puzzleItems[p] = new Puzzle();
            puzzleItems[p].type = 1 + p % Puzzle.TYPE_MAX;
        }

        shuffleArray(puzzleItems);
    }

    // Implementing Fisher–Yates shuffle
    private static void shuffleArray(Object[] ar)
    {
        Random rnd = new Random();
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            Object temp = ar[index];
            ar[index] = ar[i];
            ar[i] = temp;
        }
    }

    public Puzzle[] getPuzzleItems() {
        return puzzleItems;
    }

    public void itemClick(int position) {
        if (puzzleItems[position].removed) {
            return;
        }
        if (selection == NO_SELECTION) {
            selection = position;
            puzzleItems[position].revealed = true;
            if (listener != null) {
                listener.updatePuzzle();
            }
            return;
        }
        if (selection == position) {
            return;
        }

        if (secondselection == NO_SELECTION) {
            secondselection = position;
            puzzleItems[position].revealed = true;
            if (listener != null) {
                listener.updatePuzzle();
            }
            showHandler.removeCallbacks(showItems);
            showHandler.postDelayed(showItems, 1000);
        }
    }

    private void compareSelection() {
        if (puzzleItems[selection].type == puzzleItems[secondselection].type) {
            puzzleItems[selection].removed = true;
            puzzleItems[secondselection].removed = true;
            score++;
        } else {
            puzzleItems[selection].revealed = false;
            puzzleItems[secondselection].revealed = false;
            score--;
        }

        selection = NO_SELECTION;
        secondselection = NO_SELECTION;
        if (listener != null) {
            listener.updatePuzzle();
            listener.updateScore(score);
        }
    }

    public void setListener(PuzzleListener listener) {
        this.listener = listener;
    }

    public interface PuzzleListener {
        void updatePuzzle();
        void updateScore(int score);
    }

}
