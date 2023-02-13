module cz.vasabi.game_of_life {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;

    opens cz.vasabi.game_of_life to javafx.fxml;
    exports cz.vasabi.game_of_life;
}