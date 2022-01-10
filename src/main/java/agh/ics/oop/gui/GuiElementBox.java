package agh.ics.oop.gui;

import agh.ics.oop.Animal;
import agh.ics.oop.IMapElement;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.FileNotFoundException;

public class GuiElementBox {

    private Image img;
    private String labelText;

    public GuiElementBox(IMapElement element) {
        try {
            this.img = element.getImage();
            this.labelText = element instanceof Animal ? "Z " + element.getPosition() : "Trawa";
        } catch (FileNotFoundException e) {
            System.out.println("File not found");   // zamiana wyjątku na komunikat w konsoli, zwłaszcza w programie z GUI jest mało opłacalna
            this.img = null;    // czy program na pewno może poprawnie działać bez tego obrazka?
            this.labelText = "";    // czemu etykieta też jest pusta?
        }
    }

    public VBox mapElementView() {
        Label elementLabel = new Label(labelText);
        ImageView elementView = new ImageView(img);
        VBox elementVBox = new VBox();

        elementView.setFitWidth(10);
        elementView.setFitHeight(10);

        elementVBox.getChildren().addAll(elementView, elementLabel);
        elementVBox.setAlignment(Pos.CENTER);

        return elementVBox;

    }
}