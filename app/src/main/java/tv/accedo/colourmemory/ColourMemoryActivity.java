package tv.accedo.colourmemory;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

public class ColourMemoryActivity extends AppCompatActivity implements PuzzleController.PuzzleListener {

    private PuzzleController puzzleController;
    private GridAdapter gridAdapter;
    private GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_colour_memory);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        puzzleController = new PuzzleController();
        gridAdapter = new GridAdapter( this, puzzleController.getPuzzleItems());
        gridView = (GridView) findViewById(R.id.puzzle_grid);
        gridView.setAdapter(gridAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                puzzleController.itemClick(position);

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        puzzleController.setListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        puzzleController.setListener(null);
    }

    @Override
    public void updatePuzzle() {
        gridAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateScore(int score) {
//        textViewScore.setText(String.valueOf(score));
    }
}
