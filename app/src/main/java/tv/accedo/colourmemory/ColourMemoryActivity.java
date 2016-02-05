package tv.accedo.colourmemory;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

public class ColourMemoryActivity extends AppCompatActivity implements PuzzleController.PuzzleListener {

    private PuzzleController puzzleController;
    private GridAdapter gridAdapter;
    private GridView gridView;
    private TextView textViewScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_colour_memory);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        gridView = (GridView) findViewById(R.id.puzzle_grid);
        textViewScore = (TextView) findViewById(R.id.textview_score);

        puzzleController = new PuzzleController();
        gridAdapter = new GridAdapter( this, puzzleController.getPuzzleItems());
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
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        puzzleController.setListener(null);
    }

    @Override
    public void updatePuzzle() {
        gridAdapter.notifyDataSetChanged();

        if (puzzleController.isComplete()) {
            final int score = puzzleController.getScore();
            puzzleController.resetPuzzle();
            updateScore();
            updatePuzzle();

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Add High Score (" + score + ")");
            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
            builder.setView(input);
            builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String name = input.getText().toString();
                    Intent intent = new Intent(ColourMemoryActivity.this, ScoreActivity.class);
                    intent.putExtra(ScoreActivity.NAME, name);
                    intent.putExtra(ScoreActivity.SCORE, score);
                    startActivity(intent);
                    dialog.cancel();
                }
            });
            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
        }
    }

    @Override
    public void updateScore() {
        textViewScore.setText(String.valueOf(puzzleController.getScore()));
    }

    public void highScores(View view) {
        Intent intent = new Intent(ColourMemoryActivity.this, ScoreActivity.class);
        startActivity(intent);
    }
}
