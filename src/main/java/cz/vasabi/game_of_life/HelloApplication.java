package cz.vasabi.game_of_life;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class HelloApplication extends GameApplication {
    Simulator simulator;
    Entity gameBoard;
    boolean update = true;
    Text text = new Text(update ? "running" : "paused");


    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(800);
        settings.setHeight(600);
        settings.setTitle("Basic Game App");
        settings.setClickFeedbackEnabled(true);
    }

    @Override
    protected void initGame() {
        simulator = new Simulator(100, 100);
        gameBoard = FXGL.entityBuilder()
                .at(0, 0)
                .buildAndAttach();

        simulator.grid[0][0] = true;
        simulator.grid[0][1] = true;

        FXGL.onKeyDown(KeyCode.SPACE, () -> {
            update = !update;
        });

        FXGL.entityBuilder()
                .at(50, 50)
                .view(text)
                .buildAndAttach();
    }

    @Override
    protected void onUpdate(double tpf) {
        if (update) simulator.simulate();
        text.setText(update ? "running" : "paused");

        var comp = gameBoard.getViewComponent();
        comp.clearChildren();

        var tileWidth = FXGL.getAppWidth() / simulator.getWidth();
        var tileHeight = FXGL.getAppHeight() / simulator.getHeight();

        for (int i = 0; i < simulator.getWidth(); i++) {
            var posX = tileWidth * i;
            for (int j = 0; j < simulator.getHeight(); j++) {
                var posY = tileHeight * j;

                var color = simulator.grid[i][j] ? Color.WHITE : Color.BLACK;

                var r = new Rectangle(posX, posY, tileWidth, tileHeight);
                int finalI = i;
                int finalJ = j;
                r.setOnMousePressed(e -> {
                    simulator.grid[finalI][finalJ] = !simulator.grid[finalI][finalJ];
                });
                r.setFill(color);
                comp.addChild(r);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}