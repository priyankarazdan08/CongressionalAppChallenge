module com.example.wordgame {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.wordgame to javafx.fxml;
    exports com.example.wordgame;
}