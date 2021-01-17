package app;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

public class Controller
{
    @FXML private TextField result;
    @FXML private TextField argument;
    @FXML private Label operator;

    @FXML protected void initialize()
    {
        refresh();
        System.out.println("Initialized");
    }
    @FXML protected void clickNumButton(ActionEvent event)
    {
        Button num = (Button) event.getSource();
        Main.calc.addCypher(num.getText());
        refresh();
    }
    @FXML protected void clickOpButton(ActionEvent event)
    {
        Button op = (Button) event.getSource();
        if (op.getText().contains("Newton")) Main.calc.setOp("N");
        else Main.calc.setOp(op.getText());
        refresh();
    }
    @FXML protected void clickCalc(ActionEvent event)
    {
        Main.calc.calculate();
        refresh();
    }
    @FXML protected void clickReset(ActionEvent event)
    {
        Main.calc.reset();
        refresh();
    }
    @FXML protected void clickDelete(ActionEvent event)
    {
        Main.calc.deleteCypher();
        refresh();
    }
    @FXML protected void clickInv(ActionEvent event)
    {
        Main.calc.invertArg();
        refresh();
    }
    private void refresh()
    {
        String a = Main.calc.getArg();
        String o = Main.calc.getOp();
        result.setText(Main.calc.getRes());
        if (a == "0" && o == "") argument.setText("");
        else argument.setText(Main.calc.getArg());
        if (Main.calc.getError()) argument.setText("Error");
        operator.setText(o);
    }
}
