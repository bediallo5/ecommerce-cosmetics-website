import java.util.ArrayList;
import java.util.List;

// INTERFACE
interface GestionTache {
    void ajouter(Tache t);
    void supprimer(Tache t);
    void modifier(Tache t, String titre, int progression);
    List<Tache> getAll();
}

// IMPLEMENTATION
class GestionTacheImpl implements GestionTache {

    private List<Tache> taches = new ArrayList<>();

    public void ajouter(Tache t) {
        taches.add(t);
    }

    public void supprimer(Tache t) {
        taches.remove(t);
    }

    public void modifier(Tache t, String titre, int progression) {
        t.setTitre(titre);
        t.setProgression(progression);
    }

    public List<Tache> getAll() {
        return taches;
    }
}