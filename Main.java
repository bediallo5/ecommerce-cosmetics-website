import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;

public class Main extends Application {

    private GestionTache gestion = new GestionTacheImpl();
    private ObservableList<Tache> listeObservable;

    private ListView<Tache> listeTaches = new ListView<>();

    private ComboBox<String> filtre = new ComboBox<>();

    @Override
    public void start(Stage fenetre) {

        //CHAMPS
        TextField champTitre = new TextField();
        champTitre.setPromptText("Nom de la tâche");

        Slider progression = new Slider(0, 100, 0);
        progression.setShowTickLabels(true);

        Button boutonAjouter = new Button("Ajouter");
        Button boutonSupprimer = new Button("Supprimer");
        Button boutonModifier = new Button("Modifier");

        // FILTRE
        filtre.getItems().addAll("Toutes", "En cours", "Terminées");
        filtre.setValue("Toutes");

        // LISTE
        listeObservable = FXCollections.observableArrayList(gestion.getAll());
        listeTaches.setItems(listeObservable);

        // CASE A COCHER
        listeTaches.setCellFactory(param -> new ListCell<>() {

            CheckBox caseTerminee = new CheckBox();
            Label etiquette = new Label();
            HBox ligne = new HBox(10, caseTerminee, etiquette);

            {
                caseTerminee.setOnAction(e -> {
                    Tache t = getItem();
                    if (t != null) {
                        t.setTerminee(caseTerminee.isSelected());
                        rafraichirListe();
                    }
                });
            }

            @Override
            protected void updateItem(Tache tache, boolean vide) {
                super.updateItem(tache, vide);

                if (vide || tache == null) {
                    setGraphic(null);
                } else {

                    caseTerminee.setSelected(tache.isTerminee());

                    if (tache.isTerminee()) {
                        etiquette.setText("✔ " + tache.getTitre());
                    } else {
                        etiquette.setText(tache.getTitre() + " - " + tache.getProgression() + "%");
                    }

                    setGraphic(ligne);
                }
            }
        });

        // ===== ACTIONS =====

        boutonAjouter.setOnAction(e -> {
            Tache t = new Tache(champTitre.getText());
            t.setProgression((int) progression.getValue());

            gestion.ajouter(t);
            rafraichirListe();
        });

        boutonSupprimer.setOnAction(e -> {
            Tache selection = listeTaches.getSelectionModel().getSelectedItem();
            gestion.supprimer(selection);
            rafraichirListe();
        });

        boutonModifier.setOnAction(e -> {
            Tache selection = listeTaches.getSelectionModel().getSelectedItem();
            if (selection != null) {
                gestion.modifier(selection,
                        champTitre.getText(),
                        (int) progression.getValue());
                rafraichirListe();
            }
        });

        // ===== FILTRE LOGIQUE =====
        filtre.setOnAction(e -> appliquerFiltre());

        // ===== MISE EN PAGE =====
        VBox racine = new VBox(10,
                champTitre,
                progression,
                new HBox(10, boutonAjouter, boutonModifier, boutonSupprimer),
                filtre,
                listeTaches
        );

        racine.getStyleClass().add("racine");

        Scene scene = new Scene(racine, 500, 550);
        scene.getStylesheets().add("style.css");

        fenetre.setTitle("Gestion des tâches");
        fenetre.setScene(scene);
        fenetre.show();
    }

    // ===== RAFRAÎCHIR =====
    private void rafraichirListe() {
        appliquerFiltre();
    }

    // ===== FILTRE =====
    private void appliquerFiltre() {

        List<Tache> base = gestion.getAll();

        String choix = filtre.getValue();

        if (choix.equals("En cours")) {
            base = base.stream()
                    .filter(t -> !t.isTerminee())
                    .collect(Collectors.toList());
        }

        else if (choix.equals("Terminées")) {
            base = base.stream()
                    .filter(Tache::isTerminee)
                    .collect(Collectors.toList());
        }

        listeObservable.setAll(base);
    }

    public static void main(String[] args) {
        launch(args);
    }
}