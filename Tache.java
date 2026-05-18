public class Tache {

    private String titre;
    private boolean terminee;
    private int progression;

    public Tache(String titre) {
        this.titre = titre;
        this.terminee = false;
        this.progression = 0;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public boolean isTerminee() {
        return terminee;
    }

    public void setTerminee(boolean terminee) {
        this.terminee = terminee;
    }

    public int getProgression() {
        return progression;
    }

    public void setProgression(int progression) {
        this.progression = progression;
    }

    @Override
    public String toString() {
        if (terminee) {
            return "✔ " + titre;
        }
        return titre + " - " + progression + "%";
    }
}